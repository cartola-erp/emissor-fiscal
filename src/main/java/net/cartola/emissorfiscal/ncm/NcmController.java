package net.cartola.emissorfiscal.ncm;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequestMapping("/ncm")
@RestController
public class NcmController {
	
	@Autowired
	private NcmService ncmService;
	
	
	@GetMapping("/cadastro")
	public ModelAndView loadNcm() {
		ModelAndView mv = new ModelAndView("ncm/cadastro");
		Ncm ncm = new Ncm();
		
		mv.addObject("ncm", ncm);
		
		return mv;
	}
	
	@PostMapping("/cadastro")
	public ModelAndView save(@Valid Ncm ncm, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			// Terminar de implementar a validação
			ModelAndView mv = new ModelAndView("ncm/cadastro");
						
			return mv;
		}
		ModelAndView mv = new ModelAndView("ncm/cadastro");
	
		ncmService.save(ncm);
		return mv;
	}
		
	@GetMapping("/consulta")
	public ModelAndView findAll() {
		ModelAndView mv = new ModelAndView("ncm/consulta");
		mv.addObject("listNcm", ncmService.findAll());
		
		
		return mv;
	}
}
