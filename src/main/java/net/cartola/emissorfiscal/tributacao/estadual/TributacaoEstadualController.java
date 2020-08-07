package net.cartola.emissorfiscal.tributacao.estadual;

import java.util.Arrays;
import java.util.List;

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

import net.cartola.emissorfiscal.documento.Finalidade;
import net.cartola.emissorfiscal.estado.Estado;
import net.cartola.emissorfiscal.estado.EstadoService;
import net.cartola.emissorfiscal.ncm.Ncm;
import net.cartola.emissorfiscal.ncm.NcmService;
import net.cartola.emissorfiscal.operacao.Operacao;
import net.cartola.emissorfiscal.operacao.OperacaoService;
import net.cartola.emissorfiscal.pessoa.RegimeTributario;

@Controller
@RequestMapping("/tributacao-estadual")
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
		addObjetosNaView(mv, icms);
		
		// mv.addObject("textBtnCadastrarEditar", "Cadastrar");
		return mv;
	}
	
	@PostMapping("/cadastro")
	public ModelAndView save(@Valid TributacaoEstadual icms, Long ufOrigemId, Long ufDestinoId, Long operacaoId, Long ncmId, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors() || ufOrigemId== null || ufDestinoId== null || operacaoId== null || ncmId== null || ncmId.equals(0L)){
			ModelAndView mv = new ModelAndView("tributacao-estadual/cadastro");
//			mv.addObject("mensagemErro", icmsService.getMensagensErros(result, existeNumeroEExecao));
			mv.addObject("mensagemErro", "Por favor, preencha todos os campos necessários!");

			addObjetosNaView(mv, icms);
			return mv;
		}
		ModelAndView mv = new ModelAndView("redirect:/tributacao-estadual/cadastro");
		
		try {
			Estado estadoOrigem = estadoService.findOne(ufOrigemId).get();
			Estado estadoDestino = estadoService.findOne(ufDestinoId).get();
			Operacao operacao = operacaoService.findOne(operacaoId).get();
			Ncm ncm = ncmService.findOne(ncmId).get();

			icms.setEstadoOrigem(estadoOrigem);
			icms.setEstadoDestino(estadoDestino);
			icms.setOperacao(operacao);
			icms.setNcm(ncm);
			icmsService.save(icms);
		} catch (Exception ex) {
//			mv.addObject("mensagemErro", "Algo inesperado aconteceu ao tentar salvar/editar, essa tributação federal ");
			attributes.addFlashAttribute("mensagemErro", "Algo inesperado aconteceu ao tentar salvar/editar, essa tributação estadual ");
			return mv;
		}
		
		attributes.addFlashAttribute("mensagemSucesso", "ICMS alterado/cadastrado com sucesso!");
		return mv;
	}
		
	@GetMapping("/consulta")
	public ModelAndView findAll(Model model, @RequestParam(defaultValue="0") int page) {
		ModelAndView mv = new ModelAndView("tributacao-estadual/consulta");
		Page<TributacaoEstadual> pageTribuEsta = icmsService.findAll(PageRequest.of(page, 30));
		
		if (!pageTribuEsta.isEmpty()) {
			pageTribuEsta.forEach(tributacaoEstadual -> {
				icmsService.multiplicaTributacaoEstadualPorCem(tributacaoEstadual);
			});
		}
		mv.addObject("listTributacaoEstadual", pageTribuEsta);
		model.addAttribute("paginaAtual",page);

		return mv;
	}

	@PostMapping("/consulta")
	public ModelAndView findByNumero(@RequestParam("ncm") String numeroNcm, Model model, @RequestParam(defaultValue="0") int page) {
		ModelAndView mv = new ModelAndView("tributacao-estadual/consulta");
		try {
			PageRequest pr = PageRequest.of(page, 30);
			List<Ncm> listNcm = ncmService.findByNumero(Integer.parseInt(numeroNcm));
			Page<TributacaoEstadual> pageTribuEsta = icmsService.findTributacaoEstadualByNcms(listNcm, PageRequest.of(page, 30));
			
			if (!pageTribuEsta.isEmpty()) {
				pageTribuEsta.forEach(tributacaoEstadual -> {
					icmsService.multiplicaTributacaoEstadualPorCem(tributacaoEstadual);
				});
			}
			mv.addObject("listTributacaoEstadual", pageTribuEsta);
		} catch (Exception ex) {
			mv.addObject("mensagemErro", "Erro ao tentar buscar a tributação informada");
		} 
		return mv;
	}

	
	// Método que irá carregar na tela de cadastro, os valores cadastrados de uma tributação estadual(para poder editar)
	@GetMapping("/editar/{id}")
	public ModelAndView edit(@PathVariable long id, Model model) {
		ModelAndView mv = new ModelAndView("tributacao-estadual/cadastro");
		TributacaoEstadual icms = icmsService.findOne(id).get();
	
		model.addAttribute("ufOrigemIdSelecionado", icms.getEstadoOrigem().getId());
		model.addAttribute("ufDestinoIdSelecionado", icms.getEstadoDestino().getId());
		model.addAttribute("operacaoIdSelecionado", icms.getOperacao().getId());
		model.addAttribute("ncmdIdSelecionado", icms.getNcm().getId());
		model.addAttribute("finalidadeSelecionado", icms.getFinalidade());
		model.addAttribute("regimeTributarioSelecionado", icms.getRegimeTributario());
		
		icmsService.multiplicaTributacaoEstadualPorCem(icms);
		addObjetosNaView(mv, icms);

		// mv.addObject("textBtnCadastrarEditar", "Editar");

		return mv;
	}

//	@PostMapping("/deletar/{id}")
//	public ModelAndView delete(@PathVariable("id") long id, RedirectAttributes attributes, Model model) {
//		try {
//			icmsService.deleteById(id);
//		} catch (Exception ex) {
//			model.addAttribute("mensagemErro", "Erro ao tentar deletar a tributação estadual de ID: " +id);
//		}
//		attributes.addFlashAttribute("mensagemSucesso", "Tributação Estadual deletado com sucesso!");
//		return new ModelAndView("redirect:/tributacaoEstadual/consulta");
//	}
	
	// Para adicionar os objetos necessários na view
	private void addObjetosNaView(ModelAndView mv, TributacaoEstadual icms) {
//		TributacaoEstadual icms = new TributacaoEstadual();
		mv.addObject("icms", icms);
		mv.addObject("listEstado", estadoService.findAll());
		mv.addObject("listOperacao", operacaoService.findAll());
//		mv.addObject("listNcms", ncmService.findAll());
		mv.addObject("listFinalidade", Arrays.asList(Finalidade.values()));
		mv.addObject("listRegimeTributario", Arrays.asList(RegimeTributario.values()));
	}
}
