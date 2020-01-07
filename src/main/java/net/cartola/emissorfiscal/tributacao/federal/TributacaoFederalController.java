package net.cartola.emissorfiscal.tributacao.federal;

import java.math.BigDecimal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.cartola.emissorfiscal.ncm.Ncm;
import net.cartola.emissorfiscal.ncm.NcmService;
import net.cartola.emissorfiscal.operacao.Operacao;
import net.cartola.emissorfiscal.operacao.OperacaoService;

@RequestMapping("/tributacao-federal")
@RestController
public class TributacaoFederalController {
	
	@Autowired
	private TributacaoFederalService tributacaoFederalService;
	
	@Autowired
	private OperacaoService operacaoService;
	
	@Autowired
	private NcmService ncmService;
	
	@GetMapping("/cadastro")
	public ModelAndView loadTributacaoEstadual() {
		ModelAndView mv = new ModelAndView("tributacao-federal/cadastro");
		TributacaoFederal tributacaoFederal = new TributacaoFederal();
		mv.addObject("tributacaoFederal", tributacaoFederal);
		mv.addObject("listOperacao", operacaoService.findAll());
		mv.addObject("listNcms", ncmService.findAll());
		
		// mv.addObject("textBtnCadastrarEditar", "Cadastrar");
		return mv;
	}
	
	@PostMapping("/cadastro")
	public ModelAndView save(@Valid TributacaoFederal tributacaoFederal, Long operacaoId, Long ncmId, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			ModelAndView mv = new ModelAndView("tributacao-federal/cadastro");
			mv.addObject("tributacaoFederal", tributacaoFederal);
//			mv.addObject("mensagemErro", tributacaoFederalService.getMensagensErros(result, existeNumeroEExecao));
			return mv;
		}
		ModelAndView mv = new ModelAndView("redirect:/tributacao-federal/cadastro");
		
		Operacao operacao = operacaoService.findOne(operacaoId).get();
		Ncm ncm = ncmService.findOne(ncmId).get();
		
		tributacaoFederal.setOperacao(operacao);
		tributacaoFederal.setNcm(ncm);
		
		// Ver com o Murilo, se esses valores serão salvos já divididos por cem, ou será salvo o número "inteiro" e nos calculos que usam tais valores divide por 100
		// Se for dividir aqui, vai dar errado quando tentar editar, pois os valores q não mudarem SERAM DIVIDIOS NOVAMENTE
		tributacaoFederal.setPisAliquota(tributacaoFederal.getPisAliquota().divide(new BigDecimal(100D)));
		tributacaoFederal.setPisBase(tributacaoFederal.getPisBase().divide(new BigDecimal(100D)));
		tributacaoFederal.setCofinsAliquota(tributacaoFederal.getCofinsAliquota().divide(new BigDecimal(100D)));
		tributacaoFederal.setCofinsBase(tributacaoFederal.getCofinsBase().divide(new BigDecimal(100D)));
		tributacaoFederal.setIpiAliquota(tributacaoFederal.getIpiAliquota().divide(new BigDecimal(100D)));
		tributacaoFederal.setIpiBase(tributacaoFederal.getIpiBase().divide(new BigDecimal(100D)));
		
		try {
			tributacaoFederalService.save(tributacaoFederal);
		} catch (Exception ex) {
			mv.setViewName("tributacao-federal/cadastro");
//			mv.addObject("tributacaoFederal", tributacaoFederal);
			mv.addObject("listOperacao", operacaoService.findAll());
			mv.addObject("listNcms", ncmService.findAll());
			mv.addObject("mensagemErro", "Já existe um cadastro para esse tributo federal com essa OPERAÇÃO e NCM");
		}
		
		attributes.addFlashAttribute("mensagemSucesso", "Tributação Federal alterado/cadastrado com sucesso!");
		return mv;
	}
		
	@GetMapping("/consulta")
	public ModelAndView findAll() {
		ModelAndView mv = new ModelAndView("tributacao-federal/consulta");
		mv.addObject("listTributacaoFederal", tributacaoFederalService.findAll());
		
		return mv;
	}

//	@PostMapping("/consulta")
//	public ModelAndView findByNumero(@RequestParam("numeroNcm") String numeroNcm, Model model) {
//		ModelAndView mv = new ModelAndView("tributacaoEstadual/consulta");
//		mv.addObject("listNcm", tributacaoFederalService.findByNumero(Integer.parseInt(numeroNcm)));
//
//		return mv;
//	}
//
	// Método que irá carregar na tela de cadastro, os valores cadastrados de uma tributação federal(para poder editar)
	@GetMapping("/editar/{id}")
	public ModelAndView edit(@PathVariable long id, Model model) {
		ModelAndView mv = new ModelAndView("tributacao-federal/cadastro");
		TributacaoFederal tributacaoFederal = tributacaoFederalService.findOne(id).get();
	
		model.addAttribute("operacaoIdSelecionado", tributacaoFederal.getOperacao().getId());
		model.addAttribute("ncmdIdSelecionado", tributacaoFederal.getNcm().getId());
		
		mv.addObject("tributacaoFederal", tributacaoFederal);
		mv.addObject("listOperacao", operacaoService.findAll());
		mv.addObject("listNcms", ncmService.findAll());

		// mv.addObject("textBtnCadastrarEditar", "Editar");

		return mv;
	}

//	@PostMapping("/deletar/{id}")
//	public ModelAndView delete(@PathVariable("id") long id, RedirectAttributes attributes) {
//		tributacaoFederalService.deleteById(id);
//		attributes.addFlashAttribute("mensagemSucesso", "Tributação Estadual deletado com sucesso!");
//		return new ModelAndView("redirect:/tributacaoEstadual/consulta");
//	}
}
