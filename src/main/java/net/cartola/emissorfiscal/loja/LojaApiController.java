package net.cartola.emissorfiscal.loja;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.cartola.emissorfiscal.response.Response;

@RestController
@RequestMapping("api/v1/loja")
public class LojaApiController {

	private static final Logger LOG = Logger.getLogger(LojaApiController.class.getName());

	@Autowired
	private LojaService lojaService;

	@PostMapping()
	public ResponseEntity<Response<Loja>> save(@Valid @RequestBody Loja loja) {
		LOG.log(Level.INFO, "Salvando uma nova loja {0} ", loja);
		Response<Loja> response = new Response<>();

		Optional<Loja> opOldLoja = lojaService.findOne(Integer.toUnsignedLong(loja.getCodigoLoja()));

		if (opOldLoja.isPresent()) {
			response.setData(opOldLoja.get());
			return ResponseEntity.ok(response);
		} else {
			Optional<Loja> opLojaSaved = lojaService.save(loja);
			LOG.log(Level.INFO, "Loja salva {0} ", opLojaSaved);
			response.setData(opLojaSaved.get());
			return ResponseEntity.ok(response);
		}
	}

	@PutMapping()
	public ResponseEntity<Response<Loja>> update(@Valid @RequestBody Loja loja) {
		LOG.log(Level.INFO, "Atualizando a loja {0} ", loja);
		Response<Loja> response = new Response<>();

//		Optional<Loja> opOldLoja = lojaService.findOne(Integer.toUnsignedLong(loja.getCodigoLoja()));
		loja.setId(Integer.toUnsignedLong(loja.getCodigoLoja()));
		Optional<Loja> opLoja = lojaService.save(loja);

		if (opLoja.isPresent()) {
			loja.setId(opLoja.get().getId());
//			Optional<Loja> opLojaUpdated = lojaService.save(loja);
			LOG.log(Level.INFO, "Loja atualizada {0} ", opLoja);
			response.setData(opLoja.get());
			return ResponseEntity.ok(response);
		} else {
			List<String> listError = Arrays.asList("Loja não encontrada no EMISSOR-FISCAL");
			response.setErrors(listError);
			return ResponseEntity.noContent().build();
		}
	}

}
