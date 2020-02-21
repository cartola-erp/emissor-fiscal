package net.cartola.emissorfiscal.simulacao;

import java.util.Arrays;

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
import net.cartola.emissorfiscal.estado.Estado;
import net.cartola.emissorfiscal.estado.EstadoService;
import net.cartola.emissorfiscal.ncm.Ncm;
import net.cartola.emissorfiscal.ncm.NcmService;
import net.cartola.emissorfiscal.operacao.Operacao;
import net.cartola.emissorfiscal.operacao.OperacaoService;
import net.cartola.emissorfiscal.pessoa.Pessoa;
import net.cartola.emissorfiscal.pessoa.RegimeTributario;
import net.cartola.emissorfiscal.tributacao.estadual.CalculoFiscalEstadual;
import net.cartola.emissorfiscal.tributacao.federal.CalculoFiscalFederal;

@RequestMapping("/")
@RestController
public class SimulacaoController {
	
	@Autowired
	private OperacaoService operacaoService;
	
	@Autowired
	private EstadoService estadoService;

	@Autowired
	private NcmService ncmService;
	
	@Autowired
	private CalculoFiscalFederal calcFiscalFederal;
	
	@Autowired
	private CalculoFiscalEstadual calcFiscalEstadual;
	
	
	
//	@GetMapping("simulador")
	@GetMapping({"", "home"})
	public ModelAndView loadSimulacaoTeste() {
		ModelAndView mv = new ModelAndView("simulacao-teste/simulador-calculo");
//		Operacao operacao = new Operacao();
//		DocumentoFiscal documentoFiscal = new DocumentoFiscal();
//		mv.addObject("documentoFiscal ", documentoFiscal );

		Operacao operacao = new Operacao();
//		mv.addObject("operacao", operacao);
		mv.addObject("listOperacao", operacaoService.findAll());
		
		mv.addObject("listEstado", estadoService.findAll());
		mv.addObject("listNcm", ncmService.findAll());
		
		mv.addObject("finalidades", Arrays.asList(Finalidade.values()));
		mv.addObject("regimesTributarios", Arrays.asList(RegimeTributario.values()));
		return mv;
	}
	
	
	@PostMapping("simulador/calculo")
//	@PostMapping
	public ModelAndView calculaTributacaoEstadual(@Valid DocumentoFiscal documentoFiscal, Long ufOrigemId, Long ufDestinoId, Long operacaoId, Long ncmId, String regimeTributario, BindingResult result, RedirectAttributes attributes) {

		ModelAndView mv = new ModelAndView("redirect:/");

		try {
			Estado estadoOrigem = estadoService.findOne(ufOrigemId).get();
			Estado estadoDestino = estadoService.findOne(ufDestinoId).get();
			Operacao operacao = operacaoService.findOne(operacaoId).get();
			Ncm ncm = ncmService.findOne(ncmId).get();
			
			Pessoa emitente = new Pessoa();
			emitente.setCnpj(123456789012L);
			emitente.setRegimeTributario(RegimeTributario.REAL);
			
			Pessoa destinatario = new Pessoa();
			destinatario.setCnpj(98765432102L);
			for(RegimeTributario regimeTributa : RegimeTributario.values()) {
				if(regimeTributa.getDescricao().equalsIgnoreCase(regimeTributario)) {
					destinatario.setRegimeTributario(regimeTributa);
				}
			}
			destinatario.setUf(estadoDestino.getSigla());;
//			destinatario.setRegimeTributario(RegimeTributario.REAL);
			
			
			documentoFiscal.setEmitente(emitente);
			documentoFiscal.setDestinatario(destinatario);

			documentoFiscal.setOperacao(operacao);
			documentoFiscal.getItens().get(0).setNcm(ncm);
			
//			docFiscalItemService.save(docFiscalItemService.getItens());
		
			calcFiscalFederal.calculaImposto(documentoFiscal);
			calcFiscalEstadual.calculaImposto(documentoFiscal);
			
			mv.addObject("simulacao", documentoFiscal);
			
			System.out.println(documentoFiscal);
			
		} catch (Exception ex) {
//			mv.addObject("mensagemErro", "Algo inesperado aconteceu ao tentar salvar/editar, essa tributação federal ");
			attributes.addFlashAttribute("mensagemErro", "Algo inesperado aconteceu ao tentar calcular " + ex.getMessage());
			return mv;
		}
		
			
		System.out.println("Documento Fiscal: " +documentoFiscal);

//		System.out.println("simulacao: " +simulacao);
		
//		calcFiscalEstadual.calculaImposto(documentoFiscal);
//		
//		System.out.println("ICMS Base: " + documentoFiscal.getIcmsBase());
		
//		System.out.println("ICMS Valor: " + documentoFiscal.getIcmsValor());
//		Map<String, String> resultMapCalculoIcms = new HashMap<>();
//		resultMapCalculoIcms = calcFiscalEstadual.calculaImposto(documentoFiscal);
		
//		System.out.println("resultMapCalculoIcms: " +resultMapCalculoIcms);

		
//		ModelAndView mv = new ModelAndView("redirect:/");

//		ncmService.save(documentoFiscal);
		attributes.addFlashAttribute("mensagemSucesso", "Calculo Realizado com SUCESSO");
		return mv;
	}
}
