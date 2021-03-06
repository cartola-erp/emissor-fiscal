package net.cartola.emissorfiscal.pessoa;

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
@RequestMapping("api/v1/pessoa-alterado-sped")
public class PessoaAlteradoSpedApiController {
	
	private static final Logger LOG = Logger.getLogger(PessoaAlteradoSpedApiController.class.getName());
	
	@Autowired
	private PessoaAlteradoSpedService pessAlterSpedService;
	
	
	@PostMapping()
	public ResponseEntity<Response<PessoaAlteradoSped>> save (@Valid @RequestBody PessoaAlteradoSped newPessAlterSped) {
		LOG.log(Level.INFO, "Salvando a PessoaAlteradoSped {0} " ,newPessAlterSped);
		PessoaAlteradoSped pessAlterSpedValidada = pessAlterSpedService.validaPessoaAlteradoSped(newPessAlterSped);

		return saveOrEditPessoaAlteradoSped(pessAlterSpedValidada);
	}
	
	@PostMapping(value = "/endereco")
	public ResponseEntity<Response<PessoaAlteradoSped>> saveEndereco (@Valid @RequestBody PessoaAlteradoSped pessAlterSped) {
		LOG.log(Level.INFO, "Salvando a alteração de ENDERECO em PessoaAlteradoSped {0} " ,pessAlterSped);
		PessoaAlteradoSped pessEndAlterSpedValidada = pessAlterSpedService.validaEnderecoPessoaAlteradaSped(pessAlterSped);

		return saveOrEditPessoaAlteradoSped(pessEndAlterSpedValidada);
	}
	
	private ResponseEntity<Response<PessoaAlteradoSped>> saveOrEditPessoaAlteradoSped(PessoaAlteradoSped pessAlterSped) {
		Response<PessoaAlteradoSped> response = new Response<>();
		Optional<PessoaAlteradoSped> opPessAlterSped = pessAlterSpedService.save(pessAlterSped);
		
		if (opPessAlterSped.isPresent()) {
			response.setData(opPessAlterSped.get());
			LOG.log(Level.INFO, "PessoaAlteradoSped, salvo com sucesso {0} " ,opPessAlterSped);
			return ResponseEntity.ok(response);
		} else {
			return ResponseEntity.noContent().build();
		}
	}
	
}
