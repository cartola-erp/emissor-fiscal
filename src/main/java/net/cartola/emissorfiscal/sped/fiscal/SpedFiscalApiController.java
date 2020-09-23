package net.cartola.emissorfiscal.sped.fiscal;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.cartola.emissorfiscal.response.Response;

/**
 * 22/09/2020
 * @author robson.costa
 */
@RestController
@RequestMapping("api/v1/sped-fiscal")
public class SpedFiscalApiController {
	
	private static final Logger LOG = Logger.getLogger(SpedFiscalApiController.class.getName());

	@Autowired
	private SpedFiscalService spedFiscalService;
	
	
	@PostMapping(value = "/gerar-arquivo")
	public ResponseEntity<Response<SpedFiscalArquivo>> gerarArquivoSpedFiscal(@RequestBody MovimentacoesMensalIcmsIpi movimentacoesMensalIcmsIpi) {
		LOG.log(Level.INFO, "Gerando Arquivo SPED FISCAL (ICMS IPI) ");
		Response<SpedFiscalArquivo> response = new Response<>();
		
		/**
		 * TODO 
		 * Chama as validações que forem necessarias
		 * E retorne a resposta HTTP correta
		 * 
		 */
		
		
		SpedFiscal spedFiscal = spedFiscalService.criarArquivoSpedFiscal(movimentacoesMensalIcmsIpi);
		
		return null;
	}
	
}
