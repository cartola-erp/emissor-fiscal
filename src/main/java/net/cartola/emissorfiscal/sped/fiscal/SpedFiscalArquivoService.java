package net.cartola.emissorfiscal.sped.fiscal;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.contador.ContadorService;
import net.cartola.emissorfiscal.loja.Loja;
import net.cartola.emissorfiscal.loja.LojaService;


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
			
			MovimentoMensalIcmsIpi moviMensalIcmsIpi = moviMensalIcmsIpiService.buscaMovimentacoesIcmsIpi(loja, contadorId, dataInicio, dataFim);
			
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
//			spedFiscalArquivo.setArquivo(new Bytes[]);
			LocalDateTime dataHoraFim = LocalDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()), ZoneId.systemDefault());
			spedFiscalArquivo.setDataHoraFimGeracao(dataHoraFim);
			save(spedFiscalArquivo);
			
			LOG.log(Level.INFO, "Terminado a geração do arquivo SPED FISCAL ICMS IPI, ás {0} para a LOJA {1} " , new Object[]{dataHoraInicioGeracao, loja});
		}

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
