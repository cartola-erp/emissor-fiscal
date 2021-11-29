package net.cartola.emissorfiscal.sped.fiscal;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.cartola.emissorfiscal.response.Response;

/**
 * 22/09/2020
 * @author robson.costa
 */
@RestController
@RequestMapping("api/v1/sped-fiscal")
public class SpedFiscalArquivoApiController {
	
	private static final Logger LOG = Logger.getLogger(SpedFiscalArquivoApiController.class.getName());

	@Autowired
	private SpedFiscalArquivoService spedFiscalArquService;

	private static DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	@PostMapping(value = "/gerar-arquivo")
	public ResponseEntity<Response<SpedFiscalArquivo>> gerarArquivoSpedFiscal(Long lojaId, Long contadorId, String dtInicio, String dtFim) {
		LOG.log(Level.INFO, "Gerando Arquivo SPED FISCAL (ICMS IPI) ");
		Response<SpedFiscalArquivo> response = new Response<>();
		
		LocalDate dataInicio = LocalDate.parse(dtInicio, DTF);
		LocalDate dataFim = LocalDate.parse(dtFim, DTF);
		
		/**
		 * TODO 
		 * Chama as validações que forem necessarias
		 * E retorne a resposta HTTP correta
		 * 
		 */
		
		
		spedFiscalArquService.gerarAquivoSpedFiscal(lojaId, contadorId, dataInicio, dataFim);
		
		return null;
	}
	
}
