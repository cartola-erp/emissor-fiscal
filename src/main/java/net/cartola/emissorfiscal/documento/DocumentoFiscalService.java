package net.cartola.emissorfiscal.documento;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.ncm.Ncm;
import net.cartola.emissorfiscal.ncm.NcmService;
import net.cartola.emissorfiscal.operacao.Operacao;
import net.cartola.emissorfiscal.operacao.OperacaoService;
import net.cartola.emissorfiscal.pessoa.Pessoa;
import net.cartola.emissorfiscal.pessoa.PessoaService;
import net.cartola.emissorfiscal.tributacao.estadual.CalculoFiscalEstadual;
import net.cartola.emissorfiscal.tributacao.estadual.TributacaoEstadual;
import net.cartola.emissorfiscal.tributacao.estadual.TributacaoEstadualService;
import net.cartola.emissorfiscal.tributacao.federal.CalculoFiscalFederal;
import net.cartola.emissorfiscal.tributacao.federal.TributacaoFederal;
import net.cartola.emissorfiscal.tributacao.federal.TributacaoFederalService;
import net.cartola.emissorfiscal.util.ValidationHelper;

@Service
public class DocumentoFiscalService {

	@Autowired
	private DocumentoFiscalRepository documentoFiscalRepository;
	
	@Autowired
	private OperacaoService operacaoService;
	
	@Autowired
	private PessoaService pessoaService;
	
	@Autowired 
	private NcmService ncmService;
	
	@Autowired
	private TributacaoEstadualService icmsService;
	
	@Autowired
	private TributacaoFederalService tributacaoFederalService;
	
	@Autowired
	private CalculoFiscalEstadual calcFiscalEstadual;
	
	@Autowired
	private CalculoFiscalFederal calcFiscalFederal;
	
	
	public List<DocumentoFiscal> findAll() {
		return documentoFiscalRepository.findAll();
	}
	
	public Optional<DocumentoFiscal> save(DocumentoFiscal documentoFiscal) {
		calcFiscalEstadual.calculaImposto(documentoFiscal);
		calcFiscalFederal.calculaImposto(documentoFiscal);
		return Optional.ofNullable(documentoFiscalRepository.saveAndFlush(documentoFiscal));
	}
	
	public List<DocumentoFiscal> findDocumentoFiscalByOperacao(Operacao operacao) {
		return documentoFiscalRepository.findByOperacao(operacao);
	}
	
	public List<DocumentoFiscal> findDocumentoFiscalByVariasOperacoes(Collection<Operacao> operacoes) {
		return documentoFiscalRepository.findByOperacaoIn(operacoes);
	}
	
	public Optional<DocumentoFiscal> findOne(Long id) {
		return documentoFiscalRepository.findById(id);
	}

	public void deleteById(Long id) {
		documentoFiscalRepository.deleteById(id);
	}
	
	public Optional<DocumentoFiscal> findDocumentoFiscalByCnpjTipoDocumentoSerieENumero(Long cnpjEmitente, String tipoDocumento, Long serie, Long numero) {
		return documentoFiscalRepository.findDocumentoFiscalByEmitenteCnpjAndTipoAndSerieAndNumero(cnpjEmitente,  tipoDocumento,  serie,  numero);
	}
	
	/**
	 * Valida se as informações necessárias para um documento Fiscal existem, caso sim, as mesmas são setadas no 
	 * documentoFiscal
	 * @param documentoFiscal
	 * @return
	 */
	public List<String> validaDadosESetaValoresNecessarios(DocumentoFiscal documentoFiscal) {
		Map<String, Boolean> map = new HashMap<>();
		Optional<Operacao> opOperacao = operacaoService.findOperacaoByDescricao(documentoFiscal.getOperacao().getDescricao());
		List<Pessoa> opEmitente = pessoaService.findByCnpj(documentoFiscal.getEmitente().getCnpj());
		List<Pessoa> opDestinatario = pessoaService.findByCnpj(documentoFiscal.getDestinatario().getCnpj());
		
		documentoFiscal.getItens().forEach(docItem -> {
			Optional<Ncm> opNcm = ncmService.findNcmByNumeroAndExcecao(docItem.getNcm().getNumero(), docItem.getNcm().getExcecao());
			map.put("O NCM: " +docItem.getNcm().getNumero()+ " NÃO existe", opNcm.isPresent());
		});
		
		Set<Ncm> ncms = documentoFiscal.getItens().stream().map(DocumentoFiscalItem::getNcm).collect(Collectors.toSet());
		List<TributacaoEstadual> tributacoesEstaduais = new ArrayList<TributacaoEstadual>();
		List<TributacaoFederal> tributacoesFederais = new ArrayList<TributacaoFederal>();
		
		if (opOperacao.isPresent() && !ncms.isEmpty() && !map.containsValue(false) ) {
			tributacoesEstaduais = icmsService.findTributacaoEstadualByOperacaoENcms(opOperacao.get(), ncms);
			tributacoesFederais = tributacaoFederalService.findTributacaoFederalByVariosNcmsEOperacao(documentoFiscal.getOperacao(), ncms);
		}
		
		if (!tributacoesFederais.isEmpty()) {
			validaTributacaoFederalDoDocumentoFiscal(documentoFiscal, ncms, tributacoesFederais, map);
		}
		
		if (!tributacoesEstaduais.isEmpty()) {
			validaTributacaoEstadualDoDocumentoFiscal(documentoFiscal, ncms, tributacoesEstaduais, map);
		}
	
		
		map.put("A operação: " +documentoFiscal.getOperacao().getDescricao()+ " NÃO existe", opOperacao.isPresent());
		map.put("O CNPJ: " +documentoFiscal.getEmitente().getCnpj()+ " do emitente NÃO existe" , !opEmitente.isEmpty());
		map.put("O CNPJ: " +documentoFiscal.getDestinatario().getCnpj()+ " do destinatário NÃO existe", !opDestinatario.isEmpty());
		map.put("Não existe a tributação estadual para essa OPERAÇÃO e os NCMS dos itens", !tributacoesEstaduais.isEmpty());
		map.put("Não existe a tributação federal para essa OPERAÇÃO e os NCMS dos itens", !tributacoesFederais.isEmpty());
		
		if (!map.containsValue(false)) {
			setValoresNecessariosParaODocumentoFiscal(documentoFiscal, opOperacao, opEmitente, opDestinatario);
		}
		return ValidationHelper.processaErros(map);
	}
	
	private void setValoresNecessariosParaODocumentoFiscal(DocumentoFiscal documentoFiscal, Optional<Operacao> opOperacao, List<Pessoa> opEmitente, List<Pessoa> opDestinatario) {
		documentoFiscal.getItens().forEach(docItem -> {
			docItem.setDocumentoFiscal(documentoFiscal);
			Optional<Ncm> opNcm = ncmService.findNcmByNumeroAndExcecao(docItem.getNcm().getNumero(), docItem.getNcm().getExcecao());
			if(opNcm.isPresent()) {
				docItem.setNcm(opNcm.get());
			}
		});
		
		if (opOperacao.isPresent() && !opEmitente.isEmpty() && !opDestinatario.isEmpty()) {
			documentoFiscal.setOperacao(opOperacao.get());
			documentoFiscal.setEmitente(opEmitente.get(0));
			documentoFiscal.setDestinatario(opDestinatario.get(0));
		}
	}
	
	// Validação TRIBUTAÇÃO FEDERAL  (Por regime Tributario e Finalidade)
	public void validaTributacaoFederalDoDocumentoFiscal(DocumentoFiscal documentoFiscal, Set<Ncm> ncms, List<TributacaoFederal> tributacoesFederais, Map<String, Boolean> mapMsgValidacao ) {
		Map<Ncm, TributacaoFederal> mapaTributacoes = filtraTributacaoFederalPorRegimeTributario(documentoFiscal, ncms, tributacoesFederais, mapMsgValidacao);
		documentoFiscal.getItens().stream().forEach(item -> {
			String finalidade = mapaTributacoes.get(item.getNcm()).getFinalidade().toString();
			if(!item.getFinalidade().toString().equalsIgnoreCase(finalidade)) {
				mapMsgValidacao.put("Não Existe Tributacao Federal para a finalidade do item de NCM: "+item.getNcm(), false);
			}
		});
	}
	
	private Map<Ncm, TributacaoFederal> filtraTributacaoFederalPorRegimeTributario(DocumentoFiscal documentoFiscal, Set<Ncm> ncms, List<TributacaoFederal> tributacoesFederais, Map<String, Boolean> mapMsgValidacao ) {
		Map<Ncm, TributacaoFederal> mapaTributacoesPorNcm = ncms.stream()
				.collect(Collectors.toMap(ncm -> ncm,
						ncm -> tributacoesFederais.stream()
								.filter(tributacaoFederal -> tributacaoFederal.getNcm().getId().equals(ncm.getId()))
								.findAny().get()));
		mapaTributacoesPorNcm.forEach((k, v) -> {
			String regimeTributario = documentoFiscal.getEmitente().getRegimeTributario().getDescricao();
			if (!v.getRegimeTributario().getDescricao().equalsIgnoreCase(regimeTributario)) {
				mapMsgValidacao.put("Não Existe Tributacao Federal para o REGIME (do emitente): " +regimeTributario, false);
			}
		});
		return mapaTributacoesPorNcm;
	}

	// Validação TRIBUTAÇÃO ESTADUAL (Por regime Tributario e Finalidade)
	public void validaTributacaoEstadualDoDocumentoFiscal(DocumentoFiscal documentoFiscal, Set<Ncm> ncms, List<TributacaoEstadual> tributacoesEstaduais, Map<String, Boolean> mapMsgValidacao ) {
		Map<Ncm, TributacaoEstadual> mapaTributacoesPorRegimeTributario = filtraTributacaoEstadualPorRegimeTributario(documentoFiscal, ncms, tributacoesEstaduais, mapMsgValidacao);
		documentoFiscal.getItens().stream().forEach(item -> {
			String finalidade = mapaTributacoesPorRegimeTributario.get(item.getNcm()).getFinalidade().toString();
			if (!item.getFinalidade().toString().equalsIgnoreCase(finalidade)) {
				mapMsgValidacao.put("Não Existe Tributação Estadual para a finalidade do item de NCM: " +item.getNcm(), false);
			}
		});
	}

	private Map<Ncm, TributacaoEstadual> filtraTributacaoEstadualPorRegimeTributario(DocumentoFiscal documentoFiscal, Set<Ncm> ncms, List<TributacaoEstadual> tributacoesEstaduais, Map<String, Boolean> mapMsgValidacao) {
		Map<Ncm, TributacaoEstadual> mapaTributacoesPorNcm = ncms.stream()
				.collect(Collectors.toMap(ncm -> ncm, ncm -> tributacoesEstaduais.stream()
						.filter(tributacaoEstadual -> tributacaoEstadual.getNcm().getId().equals(ncm.getId()))
						.findAny().get()));
		mapaTributacoesPorNcm.forEach((k, v) -> {
			String regimeTributario = documentoFiscal.getEmitente().getRegimeTributario().getDescricao();
			if (!v.getRegimeTributario().getDescricao().equalsIgnoreCase(regimeTributario)) {
				mapMsgValidacao.put("Não Existe TRIBUTAÇÃO ESTADUAL para o Regime (do emitente): " +regimeTributario, false);
			}
		});
		return mapaTributacoesPorNcm;
	}
	
}
