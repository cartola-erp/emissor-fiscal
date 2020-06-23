package net.cartola.emissorfiscal.documento;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.estado.Estado;
import net.cartola.emissorfiscal.estado.EstadoService;
import net.cartola.emissorfiscal.ibpt.DeOlhoNoImpostoService;
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
	private EstadoService estadoService;
	
	@Autowired
	private TributacaoFederalService tributacaoFederalService;
	
	@Autowired
	private CalculoFiscalEstadual calcFiscalEstadual;
	
	@Autowired
	private CalculoFiscalFederal calcFiscalFederal;
	
	@Autowired
	private DeOlhoNoImpostoService olhoNoImpostoService;
	
	public List<DocumentoFiscal> findAll() {
		return documentoFiscalRepository.findAll();
	}
	
	public Optional<DocumentoFiscal> save(DocumentoFiscal documentoFiscal) {
		calcFiscalEstadual.calculaImposto(documentoFiscal);
		calcFiscalFederal.calculaImposto(documentoFiscal);
		olhoNoImpostoService.setDeOlhoNoImposto(Optional.of(documentoFiscal));
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
	 * @param validaTribuEsta	- <b> true </b> - Irá verificar/calcular os TRIBUTOS ESTADUAIS
	 * @param validaTribuFede	- <b> true </b> - Irá verificar/calcular os TRIBUTOS FEDERAIS
	 * @return
	 */
	public List<String> validaDadosESetaValoresNecessarios(DocumentoFiscal documentoFiscal, boolean validaTribuEsta, boolean validaTribuFede) {
		Map<String, Boolean> map = new HashMap<>();
		Optional<Operacao> opOperacao = operacaoService.findOperacaoByDescricao(documentoFiscal.getOperacao().getDescricao());
		Optional<Pessoa> opEmitente = pessoaService.verificaSePessoaExiste(documentoFiscal.getEmitente());
		Optional<Pessoa> opDestinatario = pessoaService.verificaSePessoaExiste(documentoFiscal.getDestinatario());
		Estado estadoOrigem = estadoService.findBySigla(opEmitente.get().getUf()).get();
		Estado estadoDestino = estadoService.findBySigla(opDestinatario.get().getUf()).get();

		Set<Ncm> ncms = setEVerificaNcmParaDocumentoFiscalItem(documentoFiscal, map);
		Set<Finalidade> finalidades = documentoFiscal.getItens().stream().map(DocumentoFiscalItem::getFinalidade).collect(Collectors.toSet());
		List<TributacaoEstadual> tributacoesEstaduais = new ArrayList<TributacaoEstadual>();
		Set<TributacaoFederal> tributacoesFederais = new HashSet<>();
		
		if (opOperacao.isPresent() && !ncms.isEmpty() && opEmitente.get().getRegimeTributario() != null && !map.containsValue(false) ) {
			tributacoesEstaduais = icmsService.findTribuEstaByOperUfOrigemUfDestinoRegTribuEFinalidadeENcms(opOperacao.get(),estadoOrigem, estadoDestino,
					opEmitente.get().getRegimeTributario(), finalidades, ncms);
			tributacoesFederais = tributacaoFederalService.findTributacaoFederalByVariosNcmsEOperacaoEFinalidadeERegimeTributario(opOperacao.get(),opEmitente.get().getRegimeTributario(), finalidades, ncms);
		}
		
		map.put("A operação: " +documentoFiscal.getOperacao().getDescricao()+ " NÃO existe", opOperacao.isPresent());
		map.put("O CNPJ: " +documentoFiscal.getEmitente().getCnpj()+ " do emitente NÃO existe" , opEmitente.isPresent());
		map.put("O CNPJ: " +documentoFiscal.getDestinatario().getCnpj()+ " do destinatário NÃO existe", opDestinatario.isPresent());
		
		if (validaTribuEsta) {
			map.put("Não existe a tributação estadual para essa OPERAÇÃO e os NCMS dos itens", !tributacoesEstaduais.isEmpty());
		}
		if (validaTribuFede) {
			boolean temTributosParaTodosNcms = isTamanhoSetNcmEqTribFede(ncms, tributacoesFederais);
//			map.put("Não existe a tributação federal para essa OPERAÇÃO e os NCMS dos itens", !tributacoesFederais.isEmpty());
			map.put("Não existe a tributação federal para essa OPERAÇÃO e os NCMS dos itens", temTributosParaTodosNcms);
		}
		
		if (!map.containsValue(false)) {
			setValoresNecessariosParaODocumentoFiscal(documentoFiscal, opOperacao, opEmitente, opDestinatario);
		}
		return ValidationHelper.processaErros(map);
	}
	
	/**
	 * Seta os NCMS para os Itens
	 * E adiciona Msg no Map, caso o NCM, não exista
	 * @param documentoFiscal
	 * @param map
	 * @return Set<Ncms>
	 */
	private Set<Ncm> setEVerificaNcmParaDocumentoFiscalItem(DocumentoFiscal documentoFiscal, Map<String, Boolean> map) {
		documentoFiscal.getItens().forEach(docItem -> {
			docItem.setDocumentoFiscal(documentoFiscal);
			Optional<Ncm> opNcm = ncmService.findNcmByNumeroAndExcecao(docItem.getNcm().getNumero(), docItem.getNcm().getExcecao());
			if(opNcm.isPresent()) {
				docItem.setNcm(opNcm.get());
			}
			map.put("O NCM: " +docItem.getNcm().getNumero()+ " NÃO existe", opNcm.isPresent());
		});
		Set<Ncm> ncms = documentoFiscal.getItens().stream().map(DocumentoFiscalItem::getNcm).collect(Collectors.toSet());
		return ncms;
	}
	
//	private void verificaTributos(Optional<Operacao> opOperacao, Set<Ncm> ncms, Estado estaOrigem, Estado estaDestino, emitente, finalidade ncm, verTributEsta, verTribuFede) {
		
//	}
	
//	private void verificaTributos(Optional<Operacao> opOperacao, Set<Ncm> ncms, Estado estaOrigem, Estado estaDestino, emitente, finalidade ncm, verTributEsta, verTribuFede) {
	
//	}
	
	private void setValoresNecessariosParaODocumentoFiscal(DocumentoFiscal documentoFiscal, Optional<Operacao> opOperacao, Optional<Pessoa> opEmitente, Optional<Pessoa> opDestinatario) {
		if (opOperacao.isPresent() && opEmitente.isPresent() && opDestinatario.isPresent()) {
			documentoFiscal.setOperacao(opOperacao.get());
			documentoFiscal.setEmitente(opEmitente.get());
			documentoFiscal.setDestinatario(opDestinatario.get());
		}
	}
	
	// O ideal é usar generics para Tributacao (p/ poder usar na tributacao estadual tbm)
	private boolean isTamanhoSetNcmEqTribFede(Set<Ncm> ncms, Set<TributacaoFederal> tributacoesFederais) {
		if(ncms.size() == tributacoesFederais.size()) {
			return true;
		}
		return false;
	}
	
	
}
