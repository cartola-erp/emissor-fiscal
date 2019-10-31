package net.cartola.emissorfiscal.simulacao;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.cartola.emissorfiscal.documento.DocumentoFiscal;
import net.cartola.emissorfiscal.documento.Finalidade;
import net.cartola.emissorfiscal.estado.EstadoService;
import net.cartola.emissorfiscal.ncm.Ncm;
import net.cartola.emissorfiscal.operacao.Operacao;
import net.cartola.emissorfiscal.operacao.OperacaoService;
import net.cartola.emissorfiscal.tributacao.estadual.CalculoFiscalEstadual;

@RequestMapping("/")
@RestController
public class SimulacaoController {
	
	@Autowired
	OperacaoService operacaoService;
	
	@Autowired
	EstadoService estadoService;

	
	@Autowired
	CalculoFiscalEstadual calcFiscalEstadual;
	
	
//	@GetMapping("/simulacao")
	@GetMapping
	public ModelAndView loadSimulacaoTeste() {
		ModelAndView mv = new ModelAndView("simulacao-teste/simulador-calculo");
//		Operacao operacao = new Operacao();
		DocumentoFiscal documentoFiscal = new DocumentoFiscal();
		mv.addObject("documentoFiscal ", documentoFiscal );
		
		Operacao operacao = new Operacao();
//		mv.addObject("operacao", operacao);
		mv.addObject("listOperacao", operacaoService.findAll());
		
		mv.addObject("listEstado", estadoService.findAll());
		mv.addObject("finalidades", Arrays.asList(Finalidade.values()));

		return mv;
	}
	
	
	@PostMapping("simulador/calculo")
//	@PostMapping
	public ModelAndView calculaTributacaoEstadual(@Valid DocumentoFiscal documentoFiscal, BindingResult result, RedirectAttributes attributes) {
//		boolean existeNumeroEExecao = ncmService.existeNumeroEExcecao(documentoFiscal);
//		if (result.hasErrors() || existeNumeroEExecao ){
//			ModelAndView mv = new ModelAndView("ncm/cadastro");
//			mv.addObject("documentoFiscal", documentoFiscal);
//			mv.addObject("mensagemErro", ncmService.getMensagensErros(result, existeNumeroEExecao));
//				
//			return mv;
//		}
		System.out.println("Documento Fiscal: " +documentoFiscal);
		Map<String, String> resultMapCalculoIcms = new HashMap<>();

		resultMapCalculoIcms = calcFiscalEstadual.calculaImposto(documentoFiscal);
		
		System.out.println("resultMapCalculoIcms: " +resultMapCalculoIcms);

		
		ModelAndView mv = new ModelAndView("redirect:/");

//		ncmService.save(documentoFiscal);
		attributes.addFlashAttribute("mensagemSucesso", "Calculo Realizado com SUCESSO");
		return mv;
	}
}
