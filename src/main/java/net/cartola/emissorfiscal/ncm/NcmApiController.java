package net.cartola.emissorfiscal.ncm;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.cartola.emissorfiscal.response.Response;

/**
 *	22 de nov de 2019
 *	@author robson.costa
 */

@RequestMapping("api/v1/ncm/")
@RestController
public class NcmApiController {
	
	@Autowired
	private NcmService ncmService;
	
	@GetMapping
	public ResponseEntity<List<Ncm>> list() {
		List<Ncm> list = ncmService.findAll();
		if(list.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("numero/{numero}/excecao/{excecao}")
	public ResponseEntity<Optional<Ncm>> findNcmByNumeroAndExcecao(@PathVariable int numero, @PathVariable int excecao) {
		Optional<Ncm> opNcm = ncmService.findNcmByNumeroAndExcecao(numero, excecao);
		if (!opNcm.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(opNcm);
	}
	
	@GetMapping("numero/{numero}")
	public ResponseEntity<Response<List<Ncm>>> findByNumero(@PathVariable int numero) {
		Response<List<Ncm>> response = new Response<>();
		List<Ncm> listNcm = ncmService.findByNumero(numero);
		if (listNcm.isEmpty()){
			response.setErrors(Arrays.asList("Não existe nenhum NCM com o número informado!!!"));
			return ResponseEntity.badRequest().body(response);
		} else {
			response.setData(listNcm);
			return ResponseEntity.ok().body(response);
		}
	}
	
}


