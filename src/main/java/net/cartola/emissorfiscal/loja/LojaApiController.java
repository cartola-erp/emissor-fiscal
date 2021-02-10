package net.cartola.emissorfiscal.loja;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.cartola.emissorfiscal.response.Response;
import net.cartola.emissorfiscal.util.StringUtil;

@RestController
@RequestMapping("api/v1/loja")
public class LojaApiController {

	private static final Logger LOG = Logger.getLogger(LojaApiController.class.getName());

	@Autowired
	private LojaService lojaService;
	
	@PostMapping
	public ResponseEntity<Response<Loja>> save (@Valid @RequestBody Loja loja) {
		LOG.log(Level.INFO, "Salvando a Loja {0} " ,loja); 
		Optional<Loja> opLoja = lojaService.findByCnpj(StringUtil.somenteNumeros(loja.getCnpj()));
		
		if (opLoja.isPresent()) {
			return ResponseEntity.noContent().build();
		}
		
		return saveOrEditLoja(loja);
	}

	private ResponseEntity<Response<Loja>> saveOrEditLoja(@Valid Loja loja) {
		Response<Loja> response = new Response<>();
		Optional<Loja> opLoja = lojaService.save(loja);
		
		if (opLoja.isPresent()) {
			response.setData(opLoja.get());
			LOG.log(Level.INFO, "Loja Salvo/Alterado, com sucesso {0} " ,opLoja);
			return ResponseEntity.ok(response);
		} else {
			return ResponseEntity.noContent().build();	
		}
	}
	
	
	
}
