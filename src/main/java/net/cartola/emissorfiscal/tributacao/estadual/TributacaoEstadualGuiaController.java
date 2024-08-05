package net.cartola.emissorfiscal.tributacao.estadual;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

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

import net.cartola.emissorfiscal.documento.ProdutoOrigem;
import net.cartola.emissorfiscal.estado.EstadoService;
import net.cartola.emissorfiscal.ncm.Ncm;
import net.cartola.emissorfiscal.ncm.NcmService;
import net.cartola.emissorfiscal.operacao.OperacaoService;

@Controller
@RequestMapping("/tributacao-estadual/guia")
public class TributacaoEstadualGuiaController {
	
	private static final Logger LOG = Logger.getLogger(TributacaoEstadualGuiaController.class.getName());
	
	@Autowired
	private TributacaoEstadualGuiaService tribEstaGuiaService;
	
	@Autowired
	private EstadoService estadoService;
	
	@Autowired
	private OperacaoService operacaoService;
	
	@Autowired
	private NcmService ncmService;
	
	@GetMapping("/cadastro")
	public ModelAndView loadTributacaoEstadualGuia() {
		ModelAndView mv = new ModelAndView("tributacao-estadual-guia/cadastro");
		TributacaoEstadualGuia tribEstaGuia = new TributacaoEstadualGuia();
		addObjetosNaView(mv, tribEstaGuia);
		
		// mv.addObject("textBtnCadastrarEditar", "Cadastrar");
		return mv;
	}
	
	@PostMapping("/cadastro")
	public ModelAndView save(@Valid TributacaoEstadualGuia tribEstaGuia, Long ufOrigemId, Long ufDestinoId, Long operacaoId, Long ncmId, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors() || ufOrigemId== null || ufDestinoId== null || operacaoId== null || ncmId== null || ncmId.equals(0L)){
			ModelAndView mv = new ModelAndView("tributacao-estadual-guia/cadastro");
//			mv.addObject("mensagemErro", icmsService.getMensagensErros(result, existeNumeroEExecao));
			mv.addObject("mensagemErro", "Por favor, preencha todos os campos necessários!");

			addObjetosNaView(mv, tribEstaGuia);
			return mv;
		}
		ModelAndView mv = new ModelAndView("redirect:/tributacao-estadual/guia/cadastro");
		
		try {
			tribEstaGuiaService.save(tribEstaGuia, ufOrigemId, ufDestinoId, operacaoId, ncmId);
		} catch (Exception ex) {
//			mv.addObject("mensagemErro", "Algo inesperado aconteceu ao tentar salvar/editar, essa tributação federal ");
			attributes.addFlashAttribute("mensagemErro", "Algo inesperado aconteceu ao tentar salvar/editar, essa tributação estadual de guia ");
			return mv;
		}
		
		attributes.addFlashAttribute("mensagemSucesso", "ICMS alterado/cadastrado com sucesso!");
		return mv;
	}

	@GetMapping("/consulta")
	public ModelAndView findAll(@RequestParam(defaultValue = "0") int page) {
		ModelAndView mv = new ModelAndView("tributacao-estadual-guia/consulta");

		try {
			// Cria o PageRequest para a página atual e tamanho de página
			PageRequest pr = PageRequest.of(page, 20);

			// Busca a página de resultados
			Page<TributacaoEstadualGuia> pageTribuEsta = tribEstaGuiaService.findAll(pr);

			// Multiplica os valores por cem se houver resultados
			if (!pageTribuEsta.isEmpty()) {
				pageTribuEsta.forEach(tribEstaGuia -> {
					tribEstaGuiaService.multiplicaTribEstaGuiaPorCem(tribEstaGuia);
				});
			}

			// Calcula o intervalo de páginas a ser exibido
			int totalPages = pageTribuEsta.getTotalPages();
			int startPage = Math.max(0, Math.min(page - 1, totalPages - 3));
			int endPage = Math.min(startPage + 2, totalPages - 1);

			// Adiciona os dados para a view
			mv.addObject("listTribEstaGuia", pageTribuEsta);
			mv.addObject("paginaAtual", page); // Página atual
			mv.addObject("totalPages", totalPages); // Total de páginas
			mv.addObject("startPage", startPage); // Início do intervalo de páginas
			mv.addObject("endPage", endPage); // Fim do intervalo de páginas
		} catch (Exception ex) {
			mv.addObject("mensagemErro", "Erro ao tentar buscar a tributação");
		}

		return mv;
	}


	@PostMapping("/consulta")
	public ModelAndView findByNumero(@RequestParam("ncm") String numeroNcm, Model model, @RequestParam(defaultValue="0") int page) {
		ModelAndView mv = new ModelAndView("tributacao-estadual-guia/consulta");
		try {
			PageRequest pr = PageRequest.of(page, 30);
			List<Ncm> listNcm = ncmService.findByNumero(Integer.parseInt(numeroNcm));
			Page<TributacaoEstadualGuia> pageTribuEsta = tribEstaGuiaService.findTributacaoEstadualGuiaByNcms(listNcm, PageRequest.of(page, 30));
//			
			if (!pageTribuEsta.isEmpty()) {
				pageTribuEsta.forEach(tribEstaGuia -> {
					tribEstaGuiaService.multiplicaTribEstaGuiaPorCem(tribEstaGuia);
				});
			}
			mv.addObject("listTribEstaGuia", pageTribuEsta);
		} catch (Exception ex) {
			mv.addObject("mensagemErro", "Erro ao tentar buscar a tributação informada");
		} 
		return mv;
	}

	
	// Método que irá carregar na tela de cadastro, os valores cadastrados de uma tributação estadual(para poder editar)
	@GetMapping("/editar/{id}")
	public ModelAndView edit(@PathVariable long id, Model model) {
		ModelAndView mv = new ModelAndView("tributacao-estadual-guia/cadastro");
		TributacaoEstadualGuia tribEstaGuia = tribEstaGuiaService.findOne(id).get();
	
		model.addAttribute("tipoGuiaSelecionado", tribEstaGuia.getTipoGuia());
		model.addAttribute("ufOrigemIdSelecionado", tribEstaGuia.getEstadoOrigem().getId());
		model.addAttribute("ufDestinoIdSelecionado", tribEstaGuia.getEstadoDestino().getId());
		model.addAttribute("operacaoIdSelecionado", tribEstaGuia.getOperacao().getId());
		model.addAttribute("ncmdIdSelecionado", tribEstaGuia.getNcm().getId());
		
		tribEstaGuiaService.multiplicaTribEstaGuiaPorCem(tribEstaGuia);
		addObjetosNaView(mv, tribEstaGuia);

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
//		return new ModelAndView("redirect:/TributacaoEstadualGuia/consulta");
//	}
	
	// Para adicionar os objetos necessários na view
	private void addObjetosNaView(ModelAndView mv, TributacaoEstadualGuia tribEstaGuia) {
//		TributacaoEstadualGuia tribEstaGuia = new TributacaoEstadualGuia();
		mv.addObject("tribEstaGuia", tribEstaGuia);

		mv.addObject("listTipoGuia", Arrays.asList(TipoGuia.values()));
		mv.addObject("listEstado", estadoService.findAll());
		mv.addObject("listProdutoOrigem", Arrays.asList(ProdutoOrigem.values()));
		mv.addObject("listOperacao", operacaoService.findAll());
	}
}
