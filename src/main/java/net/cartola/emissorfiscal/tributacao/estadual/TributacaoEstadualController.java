package net.cartola.emissorfiscal.tributacao.estadual;

import java.math.BigDecimal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.cartola.emissorfiscal.estado.Estado;
import net.cartola.emissorfiscal.estado.EstadoService;
import net.cartola.emissorfiscal.ncm.Ncm;
import net.cartola.emissorfiscal.ncm.NcmService;
import net.cartola.emissorfiscal.operacao.Operacao;
import net.cartola.emissorfiscal.operacao.OperacaoService;

@RequestMapping("/tributacaoEstadual")
@RestController
public class TributacaoEstadualController {
	
	@Autowired
	private TributacaoEstadualService icmsService;
	
	@Autowired
	private EstadoService estadoService;
	
	@Autowired
	private OperacaoService operacaoService;
	
	@Autowired
	private NcmService ncmService;
	
	@GetMapping("/cadastro")
	public ModelAndView loadTributacaoEstadual() {
		ModelAndView mv = new ModelAndView("tributacao-estadual/cadastro");
		TributacaoEstadual icms = new TributacaoEstadual();

		mv.addObject("icms ", icms);
//		mv.addObject("listEstado", estadoService.findAll());
		mv.addObject("listEstado", estadoService.findAll());
//		mv.addObject("estadoOrigem", new Estado());
		mv.addObject("listOperacao", operacaoService.findAll());
		mv.addObject("listNcms", ncmService.findAll());
		
		// mv.addObject("textBtnCadastrarEditar", "Cadastrar");
		return mv;
	}
	
	@PostMapping("/cadastro")
	public ModelAndView save(@Valid TributacaoEstadual icms, Long ufOrigemId, Long ufDestinoId, Long operacaoId, Long ncmId, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors() ){
			ModelAndView mv = new ModelAndView("tributacao-estadual/cadastro");
			mv.addObject("icms ", icms);
//			mv.addObject("mensagemErro", icmsService.getMensagensErros(result, existeNumeroEExecao));
			return mv;
		}
		ModelAndView mv = new ModelAndView("redirect:/tributacaoEstadual/cadastro");
		
		Estado estadoOrigem = estadoService.findOne(ufOrigemId).get();
		Estado estadoDestino = estadoService.findOne(ufDestinoId).get();
		Operacao operacao = operacaoService.findOne(operacaoId).get();
		Ncm ncm = ncmService.findOne(ncmId).get();
		
		icms.setEstadoOrigem(estadoOrigem);
		icms.setEstadoDestino(estadoDestino);
		icms.setOperacao(operacao);
		icms.setNcm(ncm);
		// Ver com o Murilo, se esses valores serão salvos já divididos por cem, ou será salvo o número "inteiro" e nos calculos que usam tais valores divide por 100
		icms.setIcmsAliquota(icms.getIcmsAliquota().divide(new BigDecimal(100D)));
		icms.setIcmsAliquotaDestino(icms.getIcmsAliquotaDestino().divide(new BigDecimal(100D)));
		icms.setIcmsBase(icms.getIcmsBase().divide(new BigDecimal(100D)));
		icms.setIcmsIva(icms.getIcmsIva().divide(new BigDecimal(100D)));
		
		icmsService.save(icms);
		
		attributes.addFlashAttribute("mensagemSucesso", "ICMS alterado/cadastrado com sucesso!");
		return mv;
	}
		
	@GetMapping("/consulta")
	public ModelAndView findAll() {
		ModelAndView mv = new ModelAndView("tributacaoEstadual/consulta");
		mv.addObject("listNcm", icmsService.findAll());
		
		return mv;
	}

//	@PostMapping("/consulta")
//	public ModelAndView findByNumero(@RequestParam("numeroNcm") String numeroNcm, Model model) {
//		ModelAndView mv = new ModelAndView("tributacaoEstadual/consulta");
//		mv.addObject("listNcm", icmsService.findByNumero(Integer.parseInt(numeroNcm)));
//
//		return mv;
//	}
//
//	// Método que irá carregar na tela de cadastro, os valores cadastrados de um NCM (para poder editar)
//	@GetMapping("/editar/{id}")
//	public ModelAndView edit(@PathVariable long id) {
//		ModelAndView mv = new ModelAndView("tributacaoEstadual/cadastro");
//		Tributação Estadual tributacaoEstadual = icmsService.findOne(id).get();
//		mv.addObject("tributacaoEstadual", tributacaoEstadual);
//		// mv.addObject("textBtnCadastrarEditar", "Editar");
//
//		return mv;
//	}
//
//	@PostMapping("/deletar/{id}")
//	public ModelAndView delete(@PathVariable("id") long id, RedirectAttributes attributes) {
//		icmsService.deleteById(id);
//		attributes.addFlashAttribute("mensagemSucesso", "Tributação Estadual deletado com sucesso!");
//		return new ModelAndView("redirect:/tributacaoEstadual/consulta");
//	}
}
