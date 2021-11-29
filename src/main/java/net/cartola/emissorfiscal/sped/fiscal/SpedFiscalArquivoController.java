package net.cartola.emissorfiscal.sped.fiscal;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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

	
	private static DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	@GetMapping("/icms-ipi/gerar")
	public ModelAndView loadTelaGerarSpedFiscal() {
		ModelAndView mv = new ModelAndView("sped/gerar-icms-ipi");
		addObjetosNaView(mv, new MovimentoMensalIcmsIpi());
		return mv;
	}
	
	@PostMapping("/icms-ipi/gerar")
	public ModelAndView gerarSpedFiscalIcmsIpi(Long lojaId, Long contadorId, String dataInicio, String dataFim) {
		ModelAndView mv = new ModelAndView("sped/gerar-icms-ipi");
		LocalDate dtInicio = LocalDate.parse(dataInicio, DTF);
		LocalDate dtFim = LocalDate.parse(dataFim, DTF);
		
		spedFiscalArquService.gerarAquivoSpedFiscal(lojaId, contadorId, dtInicio, dtFim);

		
		return mv;
	}
	
	private void addObjetosNaView(ModelAndView mv, MovimentoMensalIcmsIpi moviMensalIcmsIpi) {
		// PS: Tenho que inserir para buscar as informações das lojas;
		List<Loja> listLojas = new ArrayList<>();
		listLojas.addAll(lojaService.findAll());
		
		List<Inventario> listInventario =  new ArrayList<>();
		listInventario.add(new Inventario());
		
		mv.addObject("moviMensalIcmsIpi", moviMensalIcmsIpi);
		mv.addObject("listLojas", listLojas);
		mv.addObject("listContador", contadorService.findAll());
	}
	
	
}
