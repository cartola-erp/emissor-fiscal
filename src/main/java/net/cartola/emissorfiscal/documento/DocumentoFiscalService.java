package net.cartola.emissorfiscal.documento;

import static java.util.stream.Collectors.toMap;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
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

	private static final Logger LOG = Logger.getLogger(DocumentoFiscalService.class.getName());
	
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
	
	/**
	 * O "lojaEmitDest", é usado na query, para trazer todos os DocFiscais, em que uma Determinada Loja esteja envolvida.
	 * Ou seja, em que ela seja a Emitente (geralmente operações de SAIDAS), ou a destinataria (operações de ENTRADAS)
	 * 
	 * @param dataHoraInicio
	 * @param dataFim
	 * @param lojaEmitDest
	 * @return List<DocumentoFiscal> De uma loja num determinado Período
	 */
	public List<DocumentoFiscal> findByPeriodoCadastroAndLoja(LocalDateTime dataHoraInicio, LocalDateTime dataHoraFim, Pessoa lojaEmitDest) {
		return documentoFiscalRepository.findByCadastroBetweenAndEmitenteOrDestinatario(dataHoraInicio, dataHoraFim, lojaEmitDest, lojaEmitDest);
	}
	
	public Optional<DocumentoFiscal> save(DocumentoFiscal documentoFiscal) {
		calcFiscalEstadual.calculaImposto(documentoFiscal);
		calcFiscalFederal.calculaImposto(documentoFiscal);
//		olhoNoImpostoService.setDeOlhoNoImposto(Optional.of(documentoFiscal));
		return Optional.ofNullable(documentoFiscalRepository.saveAndFlush(documentoFiscal));
	}
	
	public Optional<DocumentoFiscal> updateStatusAndChaveAcesso(DocumentoFiscal documentoFiscal) {
		
		/**
		 * VER O QUE EU PRECISO FAZER PARA ATUALIZAR O DOCUMENTO FISCAL
		 * */
		
		
		return Optional.ofNullable(documentoFiscalRepository.saveAndFlush(documentoFiscal));
	}
	
	public Optional<DocumentoFiscal> saveCompra(DocumentoFiscal documentoFiscal){
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
	
	public Optional<DocumentoFiscal> findDocumentoFiscalByCnpjTipoDocumentoSerieENumero(String cnpjEmitente, String tipoDocumento, Long serie, Long numero) {
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
		LOG.log(Level.INFO, "Validando e Setando os Valores necessarios para o DocumentoFiscal {0} ", documentoFiscal);
		Map<String, Boolean> mapErros = new HashMap<>(); // FALSE == NÃO TEM ERRO | TRUE == TEM ERRO
		Optional<Operacao> opOperacao = operacaoService.findOperacaoByDescricao(documentoFiscal.getOperacao().getDescricao());
		Optional<Pessoa> opEmitente = pessoaService.verificaSePessoaExiste(documentoFiscal.getEmitente());
		Optional<Pessoa> opDestinatario = pessoaService.verificaSePessoaExiste(documentoFiscal.getDestinatario());
		Estado estadoOrigem = estadoService.findBySigla(opEmitente.get().getEndereco().getUf()).get();
		Estado estadoDestino = estadoService.findBySigla(opDestinatario.get().getEndereco().getUf()).get();

		Set<Ncm> ncms = setEVerificaNcmParaDocumentoFiscalItem(documentoFiscal, mapErros);
		Set<Finalidade> finalidades = documentoFiscal.getItens().stream().map(DocumentoFiscalItem::getFinalidade).collect(Collectors.toSet());
		List<TributacaoEstadual> tributacoesEstaduais = new ArrayList<TributacaoEstadual>();
		Set<TributacaoFederal> tributacoesFederais = new HashSet<>();
		
		if (opOperacao.isPresent() && !ncms.isEmpty() && opEmitente.get().getRegimeTributario() != null && !mapErros.containsValue(true) ) {
			tributacoesEstaduais = icmsService.findTribuEstaByOperUfOrigemUfDestinoRegTribuEFinalidadeENcms(opOperacao.get(),estadoOrigem, estadoDestino,
					opEmitente.get().getRegimeTributario(), finalidades, ncms);
			tributacoesFederais = tributacaoFederalService.findTributacaoFederalByVariosNcmsEOperacaoEFinalidadeERegimeTributario(opOperacao.get(),opEmitente.get().getRegimeTributario(), finalidades, ncms);
		}
		
		mapErros.put("A operação: " +documentoFiscal.getOperacao().getDescricao()+ " NÃO existe", !opOperacao.isPresent());
		mapErros.put("O CNPJ: " +documentoFiscal.getEmitente().getCnpj()+ " do emitente NÃO existe" , !opEmitente.isPresent());
		mapErros.put("O CNPJ: " +documentoFiscal.getDestinatario().getCnpj()+ " do destinatário NÃO existe", !opDestinatario.isPresent());
		
		if (validaTribuEsta) {
			validaTributosEstaduais(ncms, tributacoesEstaduais, mapErros);
		}
		
		if (validaTribuFede) {
			validaTributosFederais(ncms, tributacoesFederais, mapErros);
		}
		
		if (!mapErros.containsValue(true)) {
			setValoresNecessariosParaODocumentoFiscal(documentoFiscal, opOperacao, opEmitente, opDestinatario);
		}
		return ValidationHelper.processaErros(mapErros);
	}
	
	
	/**
	 * Método para setar os valores necessários, referente a um DocumentoFiscal, de entrada vindo do ERP.
	 * E quem sabe no futuro, para validar, também caso calcule a entrada por aqui rs.
	 * @param documentoFiscal
	 * @return
	 */
	public List<String> setaValoresNecessariosCompra(DocumentoFiscal documentoFiscal) {
		LOG.log(Level.INFO, "Setando os Valores necessarios para (compra) o DocumentoFiscal {0} " ,documentoFiscal);
		Map<String, Boolean> mapErros = new HashMap<>(); // FALSE == TEM ERRO | TRUE == Não tem
		Optional<Operacao> opOperacao = operacaoService.findOperacaoByDescricao(documentoFiscal.getOperacao().getDescricao());
		Optional<Pessoa> opEmitente = pessoaService.verificaSePessoaExiste(documentoFiscal.getEmitente());
		Optional<Pessoa> opDestinatario = pessoaService.verificaSePessoaExiste(documentoFiscal.getDestinatario());
		
		Set<Ncm> ncms = setEVerificaNcmParaDocumentoFiscalItem(documentoFiscal, mapErros);
		
		if (!mapErros.containsValue(true)) {
			setValoresNecessariosParaODocumentoFiscal(documentoFiscal, opOperacao, opEmitente, opDestinatario);
		}
		
		return ValidationHelper.processaErros(mapErros);
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
			map.put("O NCM: " +docItem.getNcm().getNumero()+ " NÃO existe", !opNcm.isPresent());
		});
		
		Set<Ncm> ncms = documentoFiscal.getItens().stream().map(DocumentoFiscalItem::getNcm).collect(Collectors.toSet());
		return ncms;
	}
	
	private void setValoresNecessariosParaODocumentoFiscal(DocumentoFiscal documentoFiscal, Optional<Operacao> opOperacao, Optional<Pessoa> opEmitente, Optional<Pessoa> opDestinatario) {
		if (opOperacao.isPresent() && opEmitente.isPresent() && opDestinatario.isPresent()) {
			documentoFiscal.setOperacao(opOperacao.get());
			documentoFiscal.setEmitente(opEmitente.get());
			documentoFiscal.setDestinatario(opDestinatario.get());
		}
		documentoFiscal.getReferencias().forEach(referencia -> {
			referencia.setDocumentoFiscal(documentoFiscal);
		});
	}
	
	// ================================================ VALIDA TRIBUTOS FEDERAIS ================================================
	private void validaTributosFederais(Set<Ncm> ncms, Set<TributacaoFederal> tributacoesFederais, Map<String, Boolean> mapErros) {
		Map<Ncm, Boolean> mapTribuFedeAchadasPorNcm = getMapaTribuFedeAchadasPorNcm(ncms, tributacoesFederais);
		mapTribuFedeAchadasPorNcm.forEach((kNcm, achouTributacao) -> {
			if(!achouTributacao) {
				mapErros.put("Não existe TRIBUTAÇÃO FEDERAL para essa OPERAÇÃO e o NCM: " +kNcm.getNumero() + " | EX: " +kNcm.getExcecao(), true);
			}
		});
	}
	
	/**
	 * Retorna
	 * @param ncmsDocumento
	 * @param tributacoesFederais
	 * @return Map<Ncm, Boolean> - QUe é um mapa com no qual o value irá informar se achou ou não (true or false) a tributação federal para aquele para os Ncms que tem no DocumentoFiscal
	 */
	private Map<Ncm, Boolean> getMapaTribuFedeAchadasPorNcm(Set<Ncm> ncmsDocumento, Set<TributacaoFederal> tributacoesFederais) {
		Map<Ncm, Boolean> mapaTributacoesAchadasPorNcm = ncmsDocumento.stream()
				.collect(toMap(ncm -> ncm, 
						ncm -> tributacoesFederais.stream()
						.filter(tribuFede -> tribuFede.getNcm().getId().equals(ncm.getId()))
						.findAny().isPresent()));
		return mapaTributacoesAchadasPorNcm;
	}
	
	// ================================================  VALIDA TRIBUTOS ESTADUAIS ================================================
	private void validaTributosEstaduais(Set<Ncm> ncms, List<TributacaoEstadual> tributacoesEstaduais, Map<String, Boolean> mapErros) {
		Map<Ncm, Boolean> mapaTribuEstaPorNcm = getMapaTribuEstaAchadaPorNcm(ncms, tributacoesEstaduais);
			mapaTribuEstaPorNcm.forEach((kNcm, achouTributacao) -> {
				if(!achouTributacao) {
					mapErros.put("Não existe TRIBUTAÇÃO ESTADUAL para essa OPERAÇÃO e o NCM: " +kNcm.getNumero() + " | EX: " +kNcm.getExcecao(), true);
				}
			});
	}
	
	/**
	 * Irá Retornar um Map<Ncm, Boolean>, onde caso o value, boolean seja true -> Achou a tributação estadual para aquele NCM
	 * false -> NÃO achou
	 * @param ncmsDocumento
	 * @param tributacoesEstaduais
	 * @return
	 */
	private Map<Ncm, Boolean> getMapaTribuEstaAchadaPorNcm(Set<Ncm> ncmsDocumento, List<TributacaoEstadual> tributacoesEstaduais) {
		Map<Ncm, Boolean> mapaTributacoesPorNcm = ncmsDocumento.stream()
			.collect(toMap(ncm -> ncm, 
					ncm -> tributacoesEstaduais.stream()
						.filter(tributaEstadual -> tributaEstadual.getNcm().getId().equals(ncm.getId()))
						.findAny().isPresent()));
		return mapaTributacoesPorNcm;
	}
	// ==================================================================================================================================

}
