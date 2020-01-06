package net.cartola.emissorfiscal.estado;

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

@RequestMapping("/estado")
@RestController
public class EstadoController {

	@Autowired
	private EstadoService estadoService;

	@GetMapping("/cadastro")
	public ModelAndView loadEstado() {
		ModelAndView mv = new ModelAndView("estado/cadastro");
		Estado estado = new Estado();
		mv.addObject("estado", estado);
		// mv.addObject("textBtnCadastrarEditar", "Cadastrar");
		return mv;
	}

	@PostMapping("/cadastro")
	public ModelAndView save(@Valid Estado estado, BindingResult result, RedirectAttributes attributes) {
		boolean existeEsseEstado = estadoService.findBySigla(estado.getSigla()).isPresent();
		if (result.hasErrors() || existeEsseEstado && estado.getId() == null) {
			ModelAndView mv = new ModelAndView("estado/cadastro");
			mv.addObject("estado", estado);
			mv.addObject("mensagemErro", estadoService.getMensagensErros(result, existeEsseEstado));

			return mv;
		}
		ModelAndView mv = new ModelAndView("redirect:/estado/cadastro");

		estadoService.save(estado);
		attributes.addFlashAttribute("mensagemSucesso", "ESTADO alterado/cadastrado com sucesso!");
		return mv;
	}

	@GetMapping("/consulta")
	public ModelAndView findAll() {
		ModelAndView mv = new ModelAndView("estado/consulta");
		mv.addObject("listEstado", estadoService.findAll());

		return mv;
	}

	@PostMapping("/consulta")
	public ModelAndView findBySigla(@RequestParam("siglaEstado") String siglaEstado, Model model) {
		ModelAndView mv = new ModelAndView("estado/consulta");
		mv.addObject("listEstado", estadoService.findBySigla(EstadoSigla.valueOf(siglaEstado)));

		return mv;
	}

	// Método que irá carregar na tela de cadastro, os valores cadastrados de um
	// Estado (para poder editar)
	@GetMapping("/editar/{id}")
	public ModelAndView edit(@PathVariable long id) {
		ModelAndView mv = new ModelAndView("estado/cadastro");
		Estado estado = estadoService.findOne(id).get();
		mv.addObject("estado", estado);
		// mv.addObject("textBtnCadastrarEditar", "Editar");

		return mv;
	}

	@PostMapping("/deletar/{id}")
	public ModelAndView delete(@PathVariable("id") long id, RedirectAttributes attributes) {
		estadoService.deleteById(id);
		attributes.addFlashAttribute("mensagemSucesso", "Estado deletado com sucesso!");
		return new ModelAndView("redirect:/estado/consulta");
	}
}
