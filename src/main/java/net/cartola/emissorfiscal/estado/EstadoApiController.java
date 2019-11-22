package net.cartola.emissorfiscal.estado;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *	22 de nov de 2019
 *	@author robson.costa
 */

@RequestMapping("api/v1/estado")
@RestController
public class EstadoApiController {
	
	@Autowired
	private EstadoService estadoService;
	
	@GetMapping
	public List<Estado> list() {
		return estadoService.findAll();
	}

}


