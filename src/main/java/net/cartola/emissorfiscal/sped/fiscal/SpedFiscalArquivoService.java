package net.cartola.emissorfiscal.sped.fiscal;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
import net.cartola.emissorfiscal.sped.fiscal.typeHandler.CustomEnumHandler;
import net.cartola.emissorfiscal.sped.fiscal.typeHandler.LocalDateHandler;
import net.cartola.emissorfiscal.sped.fiscal.typeHandler.LocalDateTimeHandler;
import net.cartola.emissorfiscal.sped.fiscal.typeHandler.LocalTimeHandler;


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

	public void gerarAquivoSpedFiscal(Long lojaId, Long contadorId, LocalDate dataInicio, LocalDate dataFim) {
		List<Loja> listLojas = buscaLojas(lojaId); 		

		for (Loja loja : listLojas) {
			LocalDateTime dataHoraInicioGeracao = LocalDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()), ZoneId.systemDefault());
			LOG.log(Level.INFO, "Inicio da geração do arquivo SPED FISCAL ICMS IPI, ás {0} para a LOJA {1} " , new Object[]{dataHoraInicioGeracao, loja});

			SpedFiscalArquivo spedFiscalArquivo = new SpedFiscalArquivo();
			spedFiscalArquivo.setLoja(loja);
			spedFiscalArquivo.setDataInicioSped(dataInicio);
			spedFiscalArquivo.setDataFimSped(dataFim);
			spedFiscalArquivo.setDataHoraInicioGeracao(dataHoraInicioGeracao);
			
			Optional<SpedFiscalArquivo> opSpedFiscalArqu = save(spedFiscalArquivo);
			if (opSpedFiscalArqu.isPresent()) {
				spedFiscalArquivo = opSpedFiscalArqu.get();
			}
			
			MovimentoMensalIcmsIpi moviMensalIcmsIpi = moviMensalIcmsIpiService.buscarMovimentacoesDoPeriodo(loja, contadorId, dataInicio, dataFim);
			
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
	
	
	private void gerarArquivoSped(SpedFiscal spedFiscal, SpedFiscalArquivo spedFiscArqu) {
		LOG.log(Level.INFO, "Gerando arquivo .txt da EFD ");
		Path path = criarDiretorioTemporario();

		if (path != null) {
			String dirArquivoSpedIcmsIpi = path.toAbsolutePath() + "//" + spedFiscArqu.getNomeArquivo();
			LOG.log(Level.INFO, "Diretorio temporário do arquivo criado em -> {0} ", dirArquivoSpedIcmsIpi);
			File fileSpedFiscal = new File(dirArquivoSpedIcmsIpi);
			fileSpedFiscal.deleteOnExit();

			DelimitedWriter dw = criarDelimitedWriterSped(fileSpedFiscal);
		
			try {
				dw.write(spedFiscal);
				dw.flush();
				dw.close();
			} catch (IOException ex) {
				spedFiscArqu.setStatus(Status.ERRO);
				ex.printStackTrace();
				LOG.log(Level.WARNING, "Houve algum erro, ao tentar criar o arquivo .txt: {0} " ,ex);
			}
//			try {
//				SpedFiscalWriter spedFiscWriter = new SpedFiscalWriter(fileSpedFiscal);
//				spedFiscWriter.flush();
//				spedFiscWriter.close();
//				spedFiscWriter.cleanParsers();
//			} catch (IOException ex) {
//				// TODO Auto-generated catch block
//				ex.printStackTrace();
//			}
			
			try {
				spedFiscArqu.setArquivo(Files.readAllBytes(Paths.get(dirArquivoSpedIcmsIpi)));
				spedFiscArqu.setStatus(Status.SUCESSO);
			} catch (IOException ex) {
				ex.printStackTrace();
				spedFiscArqu.setStatus(Status.ERRO);
				LOG.log(Level.WARNING, "Houve algum erro, ao tentar ler o arquivo gerado: {0} " ,ex);
			}
			LOG.log(Level.INFO, "Geração do arquivo .txt/bytes da EFD, terminada ");
		}
	}
	

	private String criarNomeArquivo(SpedFiscalArquivo spedFiscArqu) {
		StringBuilder nome = new StringBuilder();
		nome.append(spedFiscArqu.getLoja().getId())
			.append("_SpedEFD_ICMS_IPI_")
			.append(spedFiscArqu.getLoja().getCnpj())
			.append("__")
			.append(spedFiscArqu.getDataInicioSped())
			.append("__")
			.append(spedFiscArqu.getDataFimSped())
			.append(".txt");
		return nome.toString();
	}
	
	private Path criarDiretorioTemporario() {
		Path path = null;
		try {
			path = Files.createTempDirectory("java-");
		} catch (IOException ex) {
			ex.printStackTrace();
			LOG.log(Level.SEVERE, "Erro ao tentar criar o diretório temporário para salvar o ,txt do SPED ICMS IPI: {0} ", ex);
		} 
		return path;
	}
	
	private DelimitedWriter criarDelimitedWriterSped(File fileSpedFiscal) {
		Writer w = null;
		DelimitedWriter dw = null;
		try {
			w = new FileWriter(fileSpedFiscal);
			dw = new DelimitedWriter(w);
			dw.setDelimiter('|');
			dw.setEscape('\\');
			dw.setRecordInitializator("|");
			dw.setRecordTerminator("|\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		}

		dw.getObjectMapperFactory().getHandlerFactory().registerTypeHandlerClassFor(LocalDate.class,LocalDateHandler.class);
		dw.getObjectMapperFactory().getHandlerFactory().registerTypeHandlerClassFor(LocalTime.class, LocalTimeHandler.class);
		dw.getObjectMapperFactory().getHandlerFactory().registerTypeHandlerClassFor(LocalDateTime.class,LocalDateTimeHandler.class);
		dw.getObjectMapperFactory().getHandlerFactory().registerTypeHandlerClassFor(Enum.class,CustomEnumHandler.class);
		return dw;
	}
	
	private List<Loja> buscaLojas(Long lojaId) {
		List<Loja> listLojas = new ArrayList<>();
		if (lojaId == null || lojaId.equals(0L)) {
			listLojas.addAll(lojaService.findAll());
		} else {
			lojaService.findOne(lojaId).ifPresent(loja -> listLojas.add(loja));
		}
		return listLojas;
	}
	

}
