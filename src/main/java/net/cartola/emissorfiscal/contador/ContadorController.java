package net.cartola.emissorfiscal.contador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @date 13 de jan. de 2021
 * @author robson.costa
 */
@Controller
public class ContadorController {
	
	@Autowired
	private ContadorService contadorService;
	
	/**
	 * Fazer a porra toda para cadastrar, editar, deletar etc etc um CONTADOR. 
	 * Preciso das informações dele/dela, para informar no SPED.
	 * 
	 * Portanto ainda terei que fazer a telinha  para isso
	 */
	
}
