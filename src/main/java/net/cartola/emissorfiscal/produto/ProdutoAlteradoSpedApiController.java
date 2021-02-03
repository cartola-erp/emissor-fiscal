package net.cartola.emissorfiscal.produto;

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


/**
 * @date 27 de jan. de 2021
 * @author robson.costa
 */
@RestController
@RequestMapping("api/v1/produto-alterado-sped")
public class ProdutoAlteradoSpedApiController {
	
	private static final Logger LOG = Logger.getLogger(ProdutoAlteradoSpedApiController.class.getName());

	@Autowired
	private ProdutoAlteradoSpedService prodAlterSpedService;
	
	@PostMapping()
	public ResponseEntity<Response<ProdutoAlteradoSped>> save (@Valid @RequestBody ProdutoAlteradoSped prodAlterSped) {
		LOG.log(Level.INFO, "Salvando o ProdutoAlteradoSped {0} " ,prodAlterSped);
		if (prodAlterSpedService.isDescAntEqualsDescNova(prodAlterSped)) {
			LOG.log(Level.INFO, "O produto não foi salvo pois a descrição é a mesma que a anterior {0} " ,prodAlterSped);
			return ResponseEntity.noContent().build();
		}
		return saveOrEditProdutoAlteradoSped(prodAlterSped);
	}
	
	
	private ResponseEntity<Response<ProdutoAlteradoSped>> saveOrEditProdutoAlteradoSped(ProdutoAlteradoSped prodAlterSped) {
		Response<ProdutoAlteradoSped> response = new Response<>();
		Optional<ProdutoAlteradoSped> opProdAlterSped = prodAlterSpedService.save(prodAlterSped);

		if (opProdAlterSped.isPresent()) {
			response.setData(opProdAlterSped.get());
			return ResponseEntity.ok(response);
		} else {
			return ResponseEntity.noContent().build();
		}
	}
	
}
