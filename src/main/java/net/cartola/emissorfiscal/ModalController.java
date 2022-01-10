package net.cartola.emissorfiscal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import net.cartola.emissorfiscal.inventario.Inventario;
import net.cartola.emissorfiscal.inventario.InventarioService;

/**
 * 
 * Essa controller é responsável por devolver todos os "MODAIS" que estão sendo usados no sistema; <br>
 * OBS: O modal de fato é o que está nos HTMLs: "modal-template.html" <br>
 * 
 * Nessa controller basicamente será devolvido o "body" (corpo), do MODAL que será inserido no modal-template, acima; <br>
 * 
 * 
 * @date 23 de dez. de 2021
 * @author robson.costa
 */
@Controller
@RequestMapping("/modals")
public class ModalController {

	@Autowired
	private InventarioService inventarioService;
	
	private String modalTitulo = "Aviso";
	
	
	/**
	 * TODO 
	 * Aqui terei que receber a loja SELECIONADA na tela do SPED (pois tenho que mostrar somente os inventário daquela
	 * 
	 * @return
	 */
	@GetMapping("/inventarios-sped-icms-ipi")
	public ModelAndView modalInventariosSpedIcmsIpi() {
		this.modalTitulo = "Selecione o inventário!";
		ModelAndView mv = new ModelAndView("sped/modal-add-inventario");
		List<Inventario> inventarios = inventarioService.findAll();
		
		mv.addObject("titulo", modalTitulo);
		mv.addObject("listInventarios", inventarios);
		return mv;
	}
	
	@GetMapping("/inventarios-sped-icms-ipi/por-periodo")
	public ModelAndView modalInventariosSpedIcmsIpiPorPeriodo() {
		this.modalTitulo = "Informe o período do inventário!";
		ModelAndView mv = new ModelAndView("sped/modal-add-inventario-periodo");
		return mv;
	}
	
	
	
}
