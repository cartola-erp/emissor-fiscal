package net.cartola.emissorfiscal.ncm;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
		// mv.addObject("textBtnCadastrarEditar", "Cadastrar");
		return mv;
	}
	
	@PostMapping("/cadastro")
	public ModelAndView save(@Valid Ncm ncm, BindingResult result, RedirectAttributes attributes) {
		boolean existeNumeroEExecao = ncmService.existeNumeroEExcecao(ncm);
		if (result.hasErrors() || existeNumeroEExecao ){
			ModelAndView mv = new ModelAndView("ncm/cadastro");
			mv.addObject("ncm", ncm);
			mv.addObject("mensagemErro", ncmService.getMensagensErros(result, existeNumeroEExecao));
				
			return mv;
		}
		ModelAndView mv = new ModelAndView("redirect:/ncm/cadastro");
	
		ncmService.save(ncm);
		attributes.addFlashAttribute("mensagemSucesso", "NCM alterado/cadastrado com sucesso!");
		return mv;
	}
		
	@GetMapping("/consulta")
	public ModelAndView findAll() {
		ModelAndView mv = new ModelAndView("ncm/consulta");
		mv.addObject("listNcm", ncmService.findAll());
		
		return mv;
	}

	@PostMapping("/consulta")
	public ModelAndView findByNumero(@RequestParam("numeroNcm") String numeroNcm, Model model) {
		ModelAndView mv = new ModelAndView("ncm/consulta");
		mv.addObject("listNcm", ncmService.findByNumero(Integer.parseInt(numeroNcm)));

		return mv;
	}

	// Método que irá carregar na tela de cadastro, os valores cadastrados de um NCM (para poder editar)
	@GetMapping("/editar/{id}")
	public ModelAndView edit(@PathVariable long id) {
		ModelAndView mv = new ModelAndView("ncm/cadastro");
		Ncm ncm = ncmService.findOne(id).get();
		mv.addObject("ncm", ncm);
		// mv.addObject("textBtnCadastrarEditar", "Editar");

		return mv;
	}

	@PostMapping("/deletar/{id}")
	public ModelAndView delete(@PathVariable("id") long id, RedirectAttributes attributes) {
		ncmService.deleteById(id);
		attributes.addFlashAttribute("mensagemSucesso", "Ncm deletado com sucesso!");
		return new ModelAndView("redirect:/ncm/consulta");
	}
}