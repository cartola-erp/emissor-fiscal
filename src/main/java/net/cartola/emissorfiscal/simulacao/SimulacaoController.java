package net.cartola.emissorfiscal.simulacao;

import java.util.Arrays;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
@Controller
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
	
	@Autowired
	private SimulacaoService simulacaoService;
	
//	@GetMapping("simulador")
	@GetMapping({"", "home", "simulador/calculo"})
	public ModelAndView loadSimulacaoTeste() {
		ModelAndView mv = new ModelAndView("simulacao-teste/simulador-calculo");

		addObjetosNaView(mv, new DocumentoFiscal());
//		mv.addObject("resultadoCalculo", new StringBuffer("Esperando o calculo").toString());

		return mv;
	}
	
	
	@PostMapping("simulador/calculo")
//	@PostMapping
	public ModelAndView calculaTributacaoEstadual(@Valid DocumentoFiscal documentoFiscal, Long ufOrigemId, Long ufDestinoId, Long operacaoId, Long ncmId, String regimeTributario, BindingResult result, RedirectAttributes attributes) {
		ModelAndView mv = new ModelAndView("simulacao-teste/simulador-calculo");
		 StringBuffer erros = new StringBuffer("Não existe nenhuma tributação cadastrada, para os dados informados");
		try {
			Estado estadoOrigem = estadoService.findOne(ufOrigemId).get();
			Estado estadoDestino = estadoService.findOne(ufDestinoId).get();
			Operacao operacao = operacaoService.findOne(operacaoId).get();
			Ncm ncm = ncmService.findOne(ncmId).get();
			
			Pessoa emitente = new Pessoa();
			emitente.setCnpj(123456789012L);
			emitente.setUf(estadoOrigem.getSigla());
//			emitente.setRegimeTributario(RegimeTributario.REAL);
			for(RegimeTributario regimeTributa : RegimeTributario.values()) {
				if(regimeTributa.getDescricao().equalsIgnoreCase(regimeTributario)) {
					emitente.setRegimeTributario(regimeTributa);
				}
			}
			
			Pessoa destinatario = new Pessoa();
			destinatario.setCnpj(98765432102L);
		
			destinatario.setUf(estadoDestino.getSigla());;
			
			documentoFiscal.setEmitente(emitente);
			documentoFiscal.setDestinatario(destinatario);

			documentoFiscal.setOperacao(operacao);
			documentoFiscal.getItens().get(0).setNcm(ncm);
		
			calcFiscalFederal.calculaImposto(documentoFiscal);
			calcFiscalEstadual.calculaImposto(documentoFiscal);
			
			erros = simulacaoService.getStrbuffMsgResultadoCalculo(documentoFiscal);
			System.out.println("ERROS AO CALCULAR: " +erros);
			addObjetosNaView(mv, documentoFiscal);
		} catch (Exception ex) {
//			mv.addObject("mensagemErro", "Algo inesperado aconteceu ao tentar salvar/editar, essa tributação federal ");
			attributes.addFlashAttribute("mensagemErro", "Algo inesperado aconteceu ao tentar calcular " + ex.getMessage());
			addObjetosNaView(mv, documentoFiscal);
			mv.addObject("resultadoCalculo", erros.toString());
			return mv;
		}
		final StringBuffer sbResultCalculo = simulacaoService.getStrbuffMsgResultadoCalculo(documentoFiscal);
		int qtdLinhas = simulacaoService.getListMsgResultadoCalculo(documentoFiscal).size();

		mv.addObject("resultadoCalculo", sbResultCalculo.toString());
		mv.addObject("qtdLinhas", qtdLinhas + 2);

		System.out.println("RESULTADO DO CALCULO: " + sbResultCalculo.toString());

		return mv;
	}
	
	public void addObjetosNaView(ModelAndView mv, DocumentoFiscal documentoFiscal) {
		mv.addObject("documentoFiscal", documentoFiscal);

		mv.addObject("listOperacao", operacaoService.findAll());
		mv.addObject("listEstado", estadoService.findAll());
		mv.addObject("listNcm", ncmService.findAll());
		mv.addObject("finalidades", Arrays.asList(Finalidade.values()));
		mv.addObject("regimesTributarios", Arrays.asList(RegimeTributario.values()));

		mv.addObject("resultadoCalculo", new StringBuffer("Esperando o calculo").toString());
		mv.addObject("qtdLinhas", new Integer(2));

	}
}
