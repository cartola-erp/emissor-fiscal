package net.cartola.emissorfiscal.operacao;

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

@RequestMapping("/operacao")
@RestController
public class OperacaoController {
	
	@Autowired
	private OperacaoService operacaoService;
	
	
	@GetMapping("/cadastro")
	public ModelAndView loadOperacao() {
		ModelAndView mv = new ModelAndView("operacao/cadastro");
		Operacao operacao = new Operacao();
		mv.addObject("operacao", operacao);
		// mv.addObject("textBtnCadastrarEditar", "Cadastrar");
		return mv;
	}
	
	@PostMapping("/cadastro")
	public ModelAndView save(@Valid Operacao operacao, BindingResult result, RedirectAttributes attributes) {
		boolean isOperacaoExistente = operacaoService.isOperacaoExistente(operacao.getDescricao());
		if (result.hasErrors() || isOperacaoExistente ){
			ModelAndView mv = new ModelAndView("operacao/cadastro");
			mv.addObject("operacao", operacao);
			mv.addObject("mensagemErro", operacaoService.getMensagensErros(result, isOperacaoExistente));
				
			return mv;
		}
		ModelAndView mv = new ModelAndView("redirect:/operacao/cadastro");
	
		operacaoService.save(operacao);
		attributes.addFlashAttribute("mensagemSucesso", "OPERAÇÃO alterado/cadastrado com sucesso!");
		return mv;
	}
		
	@GetMapping("/consulta")
	public ModelAndView findAll() {
		ModelAndView mv = new ModelAndView("operacao/consulta");
		mv.addObject("listOperacao", operacaoService.findAll());
		
		return mv;
	}

	@PostMapping("/consulta")
	public ModelAndView findByDescricao(@RequestParam("descricaoOperacao") String descricaoOperacao, Model model) {
		ModelAndView mv = new ModelAndView("operacao/consulta");
		mv.addObject("listOperacao", operacaoService.findByParteDaDescricao(descricaoOperacao));

		return mv;
	}

	// Método que irá carregar na tela de cadastro, os valores cadastrados de uma Operacao (para poder editar)
	@GetMapping("/editar/{id}")
	public ModelAndView edit(@PathVariable long id) {
		ModelAndView mv = new ModelAndView("operacao/cadastro");
		Operacao operacao = operacaoService.findOne(id).get();
		mv.addObject("operacao", operacao);
		// mv.addObject("textBtnCadastrarEditar", "Editar");

		return mv;
	}

	@PostMapping("/deletar/{id}")
	public ModelAndView delete(@PathVariable("id") long id, RedirectAttributes attributes) {
		operacaoService.deleteById(id);
		attributes.addFlashAttribute("mensagemSucesso", "Operacao deletado com sucesso!");
		return new ModelAndView("redirect:/operacao/consulta");
	}
}
