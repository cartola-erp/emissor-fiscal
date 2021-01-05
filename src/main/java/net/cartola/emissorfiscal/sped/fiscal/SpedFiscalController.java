package net.cartola.emissorfiscal.sped.fiscal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.remote.Augmentable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import net.cartola.emissorfiscal.loja.Loja;

@Controller
@RequestMapping("/sped")
public class SpedFiscalController {

	@Autowired
	private SpedFiscalService spedFiscalService;
	
	
	@GetMapping("/icms-ipi/gerar")
	public ModelAndView loadTelaGerarSpedFiscal() {
		ModelAndView mv = new ModelAndView("sped/gerar-icms-ipi");
		addObjetosNaView(mv, new MovimentacoesMensalIcmsIpi());
		return mv;
	}
	
	@PostMapping("/icms-ipi/gerar")
	public ModelAndView gerarSpedFiscalIcmsIpi(String dataInicio, String dataFim) {
		ModelAndView mv = new ModelAndView("sped/gerar-icms-ipi");
		/***
		 * APENAS para eu ter definido a URL que será gerado.
		 * Mas provavelmente irei devolver um arquivo para dowload, depois que terminar de gerar. 
		 * Ou irei ficar mostrando para o usuário que está sendo gerado o arquivo no MOMENTO;
		 * 
		 */
		spedFiscalService.criarArquivoSpedFiscal(new MovimentacoesMensalIcmsIpi());
		
		return mv;
	}
	
	private void addObjetosNaView(ModelAndView mv, MovimentacoesMensalIcmsIpi moviMensalIcmsIpi) {
		// PS: Tenho que inserir para buscar as informações das lojas;
		List<Loja> listLojas = new ArrayList<>();
		
		mv.addObject("moviMensalIcmsIpi", moviMensalIcmsIpi);
		mv.addObject("listLojas", listLojas);
	}
	
}
