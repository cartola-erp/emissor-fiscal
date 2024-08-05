package net.cartola.emissorfiscal.cfop;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cfop")
public class CfopController {
	
	@Autowired
	private CfopService cfopService;
	
	@GetMapping("/cadastro")
	public ModelAndView loadNcm() {
		ModelAndView mv = new ModelAndView("cfop/cadastro");
		Cfop cfop = new Cfop();
		mv.addObject("cfop", cfop);
		// mv.addObject("textBtnCadastrarEditar", "Cadastrar");
		return mv;
	}
	
	@PostMapping("/cadastro")
	public ModelAndView save(@Valid Cfop cfop, BindingResult result, RedirectAttributes attributes) {
//		boolean existeNumeroEExecao = cfopService.existeNumeroEExcecao(cfop);
		if (result.hasErrors()){
			ModelAndView mv = new ModelAndView("cfop/cadastro");
			mv.addObject("cfop", cfop);
//			mv.addObject("mensagemErro", cfopService.getMensagensErros(result, existeNumeroEExecao));
				
			return mv;
		}
		ModelAndView mv = new ModelAndView("redirect:/cfop/cadastro");
		
		try {
			cfopService.save(cfop);
		} catch (Exception ex) {
			mv.addObject("mensagemErro", "Erro ao tentar salvar/editar o NCM! " +cfop.getNumero());
		}
		
		attributes.addFlashAttribute("mensagemSucesso", "CFOP alterado/cadastrado com sucesso!");
		return mv;
	}

	@GetMapping("/consulta")
	public ModelAndView findAll(@RequestParam(defaultValue="0") int page) {
		ModelAndView mv = new ModelAndView("cfop/consulta");
		int pageSize = 20;
		Page<Cfop> cfopPage = cfopService.rtnTodos(PageRequest.of(page, pageSize));

		int totalPages = cfopPage.getTotalPages();
		int startPage = Math.max(0, Math.min(page - 1, totalPages - 3));
		int endPage = Math.min(startPage + 2, totalPages - 1);

		mv.addObject("listCfop", cfopPage.getContent());
		mv.addObject("paginaAtual", page);
		mv.addObject("totalPages", totalPages);
		mv.addObject("startPage", startPage);
		mv.addObject("endPage", endPage);

		return mv;
	}

	@PostMapping("/consulta")
	public ModelAndView findByNumero(@RequestParam String numeroCfop, Model model) {
		ModelAndView mv = new ModelAndView("cfop/consulta");
		try {
			mv.addObject("listCfop", cfopService.findByNumero(Integer.parseInt(numeroCfop)));
		} catch (NumberFormatException ex) {
			mv.addObject("mensagemErro", "Erro ao tentar buscar o NCM com o número informado ");
		}

		return mv;
	}

	// Método que irá carregar na tela de cadastro, os valores cadastrados de um NCM (para poder editar)
	@GetMapping("/editar/{id}")
	public ModelAndView edit(@PathVariable long id) {
		ModelAndView mv = new ModelAndView("cfop/cadastro");
		Cfop cfop = cfopService.findOne(id).get();
		mv.addObject("cfop", cfop);
		// mv.addObject("textBtnCadastrarEditar", "Editar");

		return mv;
	}

	@PostMapping("/deletar/{id}")
	public ModelAndView delete(@PathVariable("id") long id, RedirectAttributes attributes, Model model) {
		try {
			cfopService.deleteById(id);
		} catch (Exception ex) {
			model.addAttribute("mensagemErro", "Houve um erro ao tentar deletar a CFOP de ID = " +id);
		}
		attributes.addFlashAttribute("mensagemSucesso", "Cfop deletado com sucesso!");
		return new ModelAndView("redirect:/cfop/consulta");
	}
}
