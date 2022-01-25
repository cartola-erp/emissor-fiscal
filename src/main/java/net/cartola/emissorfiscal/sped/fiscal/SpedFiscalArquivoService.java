package net.cartola.emissorfiscal.sped.fiscal;

import static net.cartola.emissorfiscal.sped.fiscal.Status.ERRO;
import static net.cartola.emissorfiscal.sped.fiscal.Status.SALVOU_DIR_TEMP;
import static net.cartola.emissorfiscal.util.SpedFiscalWriterUtil.criarNomeArquivo;
import static net.cartola.emissorfiscal.util.SpedFiscalWriterUtil.getDiretorioTemporarioAbsoluto;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coffeepot.bean.wr.writer.DelimitedWriter;
import net.cartola.emissorfiscal.contador.ContadorService;
import net.cartola.emissorfiscal.loja.Loja;
import net.cartola.emissorfiscal.loja.LojaService;
import net.cartola.emissorfiscal.util.SpedFiscalWriterUtil;


/**
 * @date 21 de jan. de 2021
 * @author robson.costa
 */
@Service
public class SpedFiscalArquivoService {

	private static final Logger LOG = Logger.getLogger(SpedFiscalArquivoService.class.getName());
	
	@Autowired
	private SpedFiscalService spedFiscalService;
	
	@Autowired
	private LojaService lojaService;
	
	@Autowired
	private MovimentoMensalIcmsIpiService moviMensalIcmsIpiService;
	
	@Autowired
	private ContadorService contadorService;
	
	@Autowired
	private SpedFiscalArquivoRepository spedFiscalArquiRepository;

	public List<SpedFiscalArquivo> findAll() {
		return spedFiscalArquiRepository.findAll();
	}
	
	private Optional<SpedFiscalArquivo> save(SpedFiscalArquivo spedFiscalArquivo) {
		return Optional.ofNullable(spedFiscalArquiRepository.saveAndFlush(spedFiscalArquivo));
	}
		
	public Optional<SpedFiscalArquivo> findOne(Long id) {
		return spedFiscalArquiRepository.findById(id);
	}

	public void gerarAquivoSpedFiscal(MovimentoMensalParametrosBusca paramBuscaSped) {
		List<Loja> listLojas = buscaLojas(paramBuscaSped); 		

		for (Loja loja : listLojas) {
			LocalDateTime dataHoraInicioGeracao = LocalDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()), ZoneId.systemDefault());
			LOG.log(Level.INFO, "Inicio da geração do arquivo SPED FISCAL ICMS IPI, ás {0} para a LOJA {1} " , new Object[]{dataHoraInicioGeracao, loja});

			SpedFiscalArquivo spedFiscalArquivo = new SpedFiscalArquivo();
			spedFiscalArquivo.setLoja(loja);
			spedFiscalArquivo.setDataInicioSped(paramBuscaSped.getDataInicioSped());
			spedFiscalArquivo.setDataFimSped(paramBuscaSped.getDataFimSped());
			spedFiscalArquivo.setDataHoraInicioGeracao(dataHoraInicioGeracao);
			
			Optional<SpedFiscalArquivo> opSpedFiscalArqu = save(spedFiscalArquivo);
			if (opSpedFiscalArqu.isPresent()) {
				spedFiscalArquivo = opSpedFiscalArqu.get();
			}
			
			MovimentoMensalIcmsIpi moviMensalIcmsIpi = moviMensalIcmsIpiService.buscarMovimentacoesDoPeriodo(paramBuscaSped, loja);

			/***
			 * APENAS para eu ter definido a URL que será gerado.
			 * Mas provavelmente irei devolver um arquivo para dowload, depois que terminar de gerar. 
			 * Ou irei ficar mostrando para o usuário que está sendo gerado o arquivo no MOMENTO;
			 * 
			 */
			SpedFiscal spedFiscal = spedFiscalService.criarSpedFiscal(moviMensalIcmsIpi);
			
			// TODO --> Gerar o Arquivo
			/** O Objeto retornado acima, "spedFiscal", é de todos os blocos montados, agora eu tenho:
			 * 1 - Que gerar o arquivo (SpedFiscalArquivo)
			 * 2 - Salvar o arquivo Gerado*/
			LocalDateTime dataHoraFim = LocalDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()), ZoneId.systemDefault());
			
			spedFiscalArquivo.setDataHoraFimGeracao(dataHoraFim);
			spedFiscalArquivo.setNomeArquivo(criarNomeArquivo(spedFiscalArquivo));
			gerarArquivoSped(spedFiscal, spedFiscalArquivo);

			save(spedFiscalArquivo);
			
			LOG.log(Level.INFO, "Terminado a geração do arquivo SPED FISCAL ICMS IPI, ás {0} para a LOJA {1} " , new Object[]{dataHoraInicioGeracao, loja});
		}

	}
	
	
	/**
	 * 
	 * @param spedFiscal -> 
	 * @param spedFiscArqu -> 
	 */
	private void gerarArquivoSped(SpedFiscal spedFiscal, SpedFiscalArquivo spedFiscArqu) {
		LOG.log(Level.INFO, "Gerando arquivo .txt da EFD ");
		Path path = SpedFiscalWriterUtil.criarDiretorioTemporario();

		if (path != null && gerouBloco9(spedFiscal, spedFiscArqu)) {
			String dirArquivoSpedIcmsIpi = getDiretorioTemporarioAbsoluto(path, spedFiscArqu.getNomeArquivo());
//			String dirArquivoSpedIcmsIpi = spedFiscArqu.getNomeArquivo();
			LOG.log(Level.INFO, "Diretorio temporário do arquivo criado em -> {0} ", dirArquivoSpedIcmsIpi);
			File fileSpedFiscal = new File(dirArquivoSpedIcmsIpi);
			fileSpedFiscal.deleteOnExit();

			DelimitedWriter dw = SpedFiscalWriterUtil.criarDelimitedWriterSped(fileSpedFiscal);
			
			Status status =  gerarArquivoSped(spedFiscal, dw);
			spedFiscArqu.setStatus(status);
			
			// Se salvou o arquivo no DIRETÓRIO temporário, então leia ele
			if (status.equals(Status.SALVOU_DIR_TEMP)) {
//				Status statusLeitura = lerBytesDoArquivoGerado(spedFiscArqu, dirArquivoSpedIcmsIpi);
				byte[] bytesArquGerado = SpedFiscalWriterUtil.lerBytesDoArquivoGerado(dirArquivoSpedIcmsIpi);

				Status statusLeitura = (bytesArquGerado != null && bytesArquGerado.length > 0) ? Status.SUCESSO : ERRO;
				spedFiscArqu.setArquivo(bytesArquGerado);
				spedFiscArqu.setStatus(statusLeitura);
			}
				
			LOG.log(Level.INFO, "Geração do arquivo .txt/bytes da EFD, terminada ");
		}
	}
	
	/**
	 * @param spedFiscal - Layout do SPED com os Registros preenchidos
	 * @param dw - Escritor que será usado para gerar o arquivo (Nele tem as formatações de delimitador de campo, dos LocalDate/LocalDateTime etc...)
	 * @return {@link	Status.ERRO} -> Houve algum erro ao tentar gravar o arquivo no diretório temporário (dw) </br> 
	 * 		   {@link	Status.GERANDO} -> Conseguiu gerar e gravar na pasta temporária, o arquivo
	 */
	private Status gerarArquivoSped(SpedFiscal spedFiscal, DelimitedWriter dw) {
		Status status = SALVOU_DIR_TEMP;
		try {
			dw.write(spedFiscal);
			dw.flush();
		} catch (IOException ex) {
//			spedFiscArqu.setStatus(Status.ERRO);
			LOG.log(Level.SEVERE, "Houve algum erro, ao tentar criar o arquivo .txt: {0} " ,ex.getStackTrace());
			status = ERRO;
		} finally {
			try {
				dw.close();
				dw.clearMappers();
			} catch (IOException ex) {
				LOG.log(Level.SEVERE, "Erro ao tentar fechar o 'escritor', do arquivo SPED {0} " ,ex.getStackTrace());
				status = ERRO;
			}
		}
		return status;
	}

	private boolean gerouBloco9(SpedFiscal spedFiscal, SpedFiscalArquivo spedFiscArqu) {
		if (spedFiscal.getBloco9() != null) {
			return true;
		}
		spedFiscArqu.setStatus(ERRO);
		return false;
	}
	
	private List<Loja> buscaLojas(MovimentoMensalParametrosBusca paramBuscaSped) {
		List<Loja> listLojas = new ArrayList<>();
		if (paramBuscaSped.isExportarSpedTodasLojas()) {
			return lojaService.findAll();
		} else if (paramBuscaSped.getLojaId() != null && !paramBuscaSped.getLojaId().equals(0L)) {
			lojaService.findOne(paramBuscaSped.getLojaId()).ifPresent(loja -> listLojas.add(loja));
		}
		return listLojas;
	}
	

}
