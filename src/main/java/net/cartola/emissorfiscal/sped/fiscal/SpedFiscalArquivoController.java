package net.cartola.emissorfiscal.sped.fiscal;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.cartola.emissorfiscal.contador.ContadorService;
import net.cartola.emissorfiscal.inventario.Inventario;
import net.cartola.emissorfiscal.inventario.InventarioService;
import net.cartola.emissorfiscal.loja.Loja;
import net.cartola.emissorfiscal.loja.LojaService;

@Controller
@RequestMapping("/sped")
public class SpedFiscalArquivoController {

	@Autowired
	private SpedFiscalArquivoService spedFiscalArquService;

	@Autowired
	private LojaService lojaService;
	
	@Autowired
	private ContadorService contadorService;
	
	@Autowired
	private InventarioService inventarioService;
	
	private static final Logger LOG = Logger.getLogger(SpedFiscalArquivoController.class.getName());

	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(new MovimentoMensalParametrosBuscaValidator());
	}

	
	@GetMapping("/icms-ipi/gerar")
	public ModelAndView loadTelaGerarSpedFiscal() {
		ModelAndView mv = new ModelAndView("sped/gerar-icms-ipi");
		addObjetosNaView(mv, new MovimentoMensalParametrosBusca());
		return mv;
	}
	
	@PostMapping("/icms-ipi/gerar")
	public ModelAndView gerarSpedFiscalIcmsIpi(@Valid MovimentoMensalParametrosBusca paramBuscaSped, BindingResult result, RedirectAttributes redirectAttributes) {
		ModelAndView mv = new ModelAndView("sped/gerar-icms-ipi");
		
		spedFiscalArquService.gerarAquivoSpedFiscal(paramBuscaSped);
		addObjetosNaView(mv, paramBuscaSped);
		return mv;
	}
	
	private void addObjetosNaView(ModelAndView mv, MovimentoMensalParametrosBusca paramBuscaSped) {
		// PS: Tenho que inserir para buscar as informações das lojas;
		List<Loja> listLojas = new ArrayList<>();
		listLojas.addAll(lojaService.findAll());
		
		List<Inventario> listInventario =  new ArrayList<>();
		listInventario.add(new Inventario());
		
		mv.addObject("moviParametrosBusca", paramBuscaSped);
		mv.addObject("listLojas", listLojas);
		mv.addObject("listContador", contadorService.findAll());
	}
	
	
}
