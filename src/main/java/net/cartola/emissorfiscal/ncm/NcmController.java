package net.cartola.emissorfiscal.ncm;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
		if (result.hasErrors() || existeNumeroEExecao && ncm.getId() == null){
			ModelAndView mv = new ModelAndView("ncm/cadastro");
			mv.addObject("ncm", ncm);
			mv.addObject("mensagemErro", ncmService.getMensagensErros(result, existeNumeroEExecao));
				
			return mv;
		}
		ModelAndView mv = new ModelAndView("redirect:/ncm/cadastro");
		
		try {
			ncmService.save(ncm);
		} catch (ConstraintViolationException ex) {
			mv.addObject("mensagemErro", "Essa combinação de NCM e EXCEÇÃO, já EXISTE!");
		} catch (Exception ex) {
			mv.addObject("mensagemErro", "Erro ao tentar salvar/editar o NCM! " +ncm.getNumero());
		}
		
		attributes.addFlashAttribute("mensagemSucesso", "NCM alterado/cadastrado com sucesso!");
		return mv;
	}

	@GetMapping("/consulta")
	public ModelAndView findAll(Model model, @RequestParam(defaultValue="0") int page) {
		ModelAndView mv = new ModelAndView("ncm/consulta");
//		mv.addObject("listNcm", ncmService.findAll());
		mv.addObject("listNcm", ncmService.findAll(PageRequest.of(page, 20)));
		model.addAttribute("paginaAtual",page);

		int pageSize = 20;
		Page<Ncm> ncmPage = ncmService.findAll(PageRequest.of(page, pageSize));

		int totalPages = ncmPage.getTotalPages();
		int startPage = Math.max(0, Math.min(page - 1, totalPages - 3));
		int endPage = Math.min(startPage + 2, totalPages - 1);

		mv.addObject("listNcm", ncmPage);
		model.addAttribute("paginaAtual", page);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);

		return mv;
	}

	@PostMapping("/consulta")
	public ModelAndView findByNumero(@RequestParam("numeroNcm") String numeroNcm, Model model, @RequestParam(defaultValue="0") int page) {
		ModelAndView mv = new ModelAndView("ncm/consulta");
		try {
			mv.addObject("listNcm", ncmService.findNcmByNumero(Integer.parseInt(numeroNcm), PageRequest.of(page, 20)));
		} catch (NumberFormatException ex) {
			mv.addObject("mensagemErro", "Erro ao tentar buscar o NCM com o número informado ");
		}

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
	public ModelAndView delete(@PathVariable("id") long id, RedirectAttributes attributes, Model model) {
		try {
			ncmService.deleteById(id);
		} catch (Exception ex) {
			model.addAttribute("mensagemErro", "Houve um erro ao tentar deletar o NCM de ID = " +id);
		}
		attributes.addFlashAttribute("mensagemSucesso", "Ncm deletado com sucesso!");
		return new ModelAndView("redirect:/ncm/consulta");
	}
}
