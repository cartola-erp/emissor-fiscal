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

import net.cartola.emissorfiscal.loja.Loja;

@Controller
@RequestMapping("/sped")
public class SpedFiscalArquivoController {

	@Autowired
	private SpedFiscalArquivoService spedFiscalArquService;

	
	private static final Logger LOG = Logger.getLogger(SpedFiscalArquivoController.class.getName());

	
	private static DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	@GetMapping("/icms-ipi/gerar")
	public ModelAndView loadTelaGerarSpedFiscal() {
		ModelAndView mv = new ModelAndView("sped/gerar-icms-ipi");
		addObjetosNaView(mv, new MovimentoMensalIcmsIpi());
		return mv;
	}
	
	@PostMapping("/icms-ipi/gerar")
	public ModelAndView gerarSpedFiscalIcmsIpi(Long lojaId, Long contadorId, String dtInicio, String dtFim) {
		ModelAndView mv = new ModelAndView("sped/gerar-icms-ipi");
		LocalDate dataInicio = LocalDate.parse(dtInicio, DTF);
		LocalDate dataFim = LocalDate.parse(dtFim, DTF);
		
		
		spedFiscalArquService.gerarAquivoSpedFiscal(lojaId, contadorId, dataInicio, dataFim);

		
		return mv;
	}
	
	private void addObjetosNaView(ModelAndView mv, MovimentoMensalIcmsIpi moviMensalIcmsIpi) {
		// PS: Tenho que inserir para buscar as informações das lojas;
		List<Loja> listLojas = new ArrayList<>();
		
		mv.addObject("moviMensalIcmsIpi", moviMensalIcmsIpi);
		mv.addObject("listLojas", listLojas);
	}
	
	
}
