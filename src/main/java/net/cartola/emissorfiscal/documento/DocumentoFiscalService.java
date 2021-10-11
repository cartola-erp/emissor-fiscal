package net.cartola.emissorfiscal.documento;

import static java.util.stream.Collectors.toMap;
import static net.cartola.emissorfiscal.sped.fiscal.enums.FreteConta.DESTINATARIO;
import static net.cartola.emissorfiscal.sped.fiscal.enums.FreteConta.DESTINATARIO_PROPRIO;
import static net.cartola.emissorfiscal.sped.fiscal.enums.FreteConta.TERCEIROS;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.devolucao.Devolucao;
import net.cartola.emissorfiscal.devolucao.DevolucaoService;
import net.cartola.emissorfiscal.estado.Estado;
import net.cartola.emissorfiscal.ibpt.DeOlhoNoImpostoService;
import net.cartola.emissorfiscal.loja.Loja;
import net.cartola.emissorfiscal.ncm.Ncm;
import net.cartola.emissorfiscal.operacao.Operacao;
import net.cartola.emissorfiscal.pessoa.Pessoa;
import net.cartola.emissorfiscal.sped.fiscal.enums.FreteConta;
import net.cartola.emissorfiscal.sped.fiscal.enums.ModeloDocumentoFiscal;
import net.cartola.emissorfiscal.tributacao.estadual.CalculoFiscalEstadual;
import net.cartola.emissorfiscal.tributacao.estadual.CalculoGuiaEstadualService;
import net.cartola.emissorfiscal.tributacao.estadual.TributacaoEstadual;
import net.cartola.emissorfiscal.tributacao.estadual.TributacaoEstadualService;
import net.cartola.emissorfiscal.tributacao.federal.CalculoFiscalFederal;
import net.cartola.emissorfiscal.tributacao.federal.TributacaoFederal;
import net.cartola.emissorfiscal.tributacao.federal.TributacaoFederalService;
import net.cartola.emissorfiscal.util.ValidationHelper;

@Service
public class DocumentoFiscalService extends DocumentoService {

	private static final Logger LOG = Logger.getLogger(DocumentoFiscalService.class.getName());
	
	@Autowired
	private DocumentoFiscalRepository documentoFiscalRepository;
	
	@Autowired
	private DocumentoFiscalItemService docuFiscItemService;
	
	@Autowired
	private DevolucaoService devolucaoService;
	
//	@Autowired
//	private OperacaoService operacaoService;
//	
//	@Autowired
//	private LojaService lojaService;
//	
//	@Autowired
//	private PessoaService pessoaService;
//	
//	@Autowired 
//	private NcmService ncmService;
//	
//	@Autowired
//	private ProdutoUnidadeService prodUnidService;
	
	@Autowired
	private TributacaoEstadualService icmsService;
	
//	@Autowired
//	private EstadoService estadoService;
	
	@Autowired
	private TributacaoFederalService tributacaoFederalService;
	
	@Autowired
	private CalculoFiscalEstadual calcFiscalEstadual;
	
	@Autowired
	private CalculoFiscalFederal calcFiscalFederal;
	
	@Autowired
	private CalculoGuiaEstadualService calcGuiaEstaService;
	
	@Autowired
	private DeOlhoNoImpostoService olhoNoImpostoService;
	
	public List<DocumentoFiscal> findAll() {
		return documentoFiscalRepository.findAll();
	}

	public List<DocumentoFiscal> findByNfeChaveAcessoIn(Collection<String> setChaveAcessoReferencia) {
		return documentoFiscalRepository.findByNfeChaveAcessoIn(setChaveAcessoReferencia);
	}
	
	private List<DocumentoFiscal> findDocumentoFiscalByOperacao(Operacao operacao) {
		return documentoFiscalRepository.findByOperacao(operacao);
	}
	
	private List<DocumentoFiscal> findDocumentoFiscalByVariasOperacoes(Collection<Operacao> operacoes) {
		return documentoFiscalRepository.findByOperacaoIn(operacoes);
	}
	
	private Optional<DocumentoFiscal> findOne(Long id) {
		return documentoFiscalRepository.findById(id);
	}
	
	public Optional<DocumentoFiscal> findDocumentoFiscal(DocumentoFiscal docFiscal) {
//		if (docFiscal.getOperacao().isDevolucao()) {
//			/****
//			 * TODO aqui tenho que buscar a DEVOLUÇÕA, mas como irei FAZER isso ?????
//			 * - Chave de acesso  da tabela devo_origem ?
//			 * - 
//			 */
//			
//			
//			
//			
//			return null;
//		}
		
		return this.findDocumentoFiscalByCnpjTipoOperacaoSerieENumero(docFiscal.getEmitente().getCnpj(), docFiscal.getTipoOperacao(), docFiscal.getSerie(), docFiscal.getNumeroNota());
//		return null;
	}
	
	public Optional<DocumentoFiscal> findDocumentoFiscalByCnpjTipoOperacaoSerieENumero(String cnpjEmitente, IndicadorDeOperacao tipoOperacao, Long serie, Long numero) {
		return documentoFiscalRepository.findDocumentoFiscalByEmitenteCnpjAndTipoOperacaoAndSerieAndNumeroNota(cnpjEmitente,  tipoOperacao,  serie,  numero);
	}

	public void deleteById(Long id) {
		documentoFiscalRepository.deleteById(id);
	}
	
	/**
	 * Busca todos os DocumentoFicais de um Período que uma Pessoa emitiu (Loja)
	 * @param dataHoraInicio
	 * @param dataHoraFim
	 * @param loja
	 * @return
	 */
	public List<DocumentoFiscal> findByPeriodoCadastroAndLojaAndTipoServico(LocalDateTime dataHoraInicio, LocalDateTime dataHoraFim, Loja loja, Collection<TipoServico> listTipoServico) {
		return documentoFiscalRepository.findByCadastroBetweenAndLojaAndTipoServicoIn(dataHoraInicio, dataHoraFim, loja, listTipoServico);
	}
	
	/**
	 * Buscando todos os DocumentoFiscais de um PERIODO para um determinado MODELO para uma Pessoa de ENTRADA ou SAÍDA
	 *
	 * @param dataHoraInicio
	 * @param dataHoraFim
	 * @param loja
	 * @param modelo
	 * @param tipoOperacao
	 * @return
	 */
	public List<DocumentoFiscal> findByPeriodoCadastroAndLojaAndModeloAndTipoOperacao(LocalDateTime dataHoraInicio, LocalDateTime dataHoraFim, Loja loja, ModeloDocumentoFiscal modelo, IndicadorDeOperacao tipoOperacao) {
		return documentoFiscalRepository.findByCadastroBetweenAndLojaAndModeloAndTipoOperacao(dataHoraInicio, dataHoraFim, loja, modelo, tipoOperacao);
	}
	
	/**
	 * Será retornado, todos os DocumentoFiscais, de SC, ES e ou MG (ou qualquer outro estado X operacao, que estiver na tabela trib_esta_guia); <\br>
	 * que deram entrada no periodo informado;
	 * 
	 * @param dataHoraInicio
	 * @param dataHoraFim
	 * @return
	 */
	public Set<DocumentoFiscal> findDocsQueRecolhemosIcmsNaEntradaDeSantaCatarinaPorPeriodo(LocalDateTime dataHoraInicio, LocalDateTime dataHoraFim) {
		return documentoFiscalRepository.findAllDocsInterestadualQuePagamosIcmsNaEntradaPorPeriodo(dataHoraInicio, dataHoraFim);
	}
	
	/**
	 * TODO fazer salvar um OBJ de DEVOLUCAO
	 * Será salvo, uma Devolucao, e criado um DocumentoFiscal, com  os valores calculados da devolução (se for para fornecedor por exemplo); 
	 * Caso seja uma devolucao emitida pelo cliente, terá que basicamente ser dado entrada
	 * 
	 * @param devolucao
	 * @return DocumentoFiscal, que foi salvo, (E calculado se o mesmo é uma Devoluçao:  "para fornecedor"
 	 */
	public Optional<DocumentoFiscal> save(Devolucao devolucao) {
		Optional<DocumentoFiscal> opDocFiscSaved = Optional.empty();
		Optional<Devolucao> opDevolucao = devolucaoService.save(devolucao);
		
		if (opDevolucao.isPresent()) {
			DocumentoFiscal documentoFiscal = new DocumentoFiscal(opDevolucao.get());
			DocumentoFiscal docFiscCalculado = calcFiscalEstadual.calculaImposto(documentoFiscal, devolucao);
			calcFiscalFederal.calculaImposto(docFiscCalculado);
			opDocFiscSaved = Optional.ofNullable(documentoFiscalRepository.saveAndFlush(documentoFiscal));
		}
		return opDocFiscSaved;
	}
	
	public Optional<DocumentoFiscal> save(DocumentoFiscal documentoFiscal) {
		calcFiscalEstadual.calculaImposto(documentoFiscal);
		calcFiscalFederal.calculaImposto(documentoFiscal);
//		olhoNoImpostoService.setDeOlhoNoImposto(Optional.of(documentoFiscal));
		return Optional.ofNullable(documentoFiscalRepository.saveAndFlush(documentoFiscal));
	}
	
	/**
	 * Método  que responsável por salvar uma Compra (DocumentoFiscal de ENTRADA)
	 * @param documentoFiscal
	 * @param isNewCompra
	 * @return
	 */
	public Optional<CompraDto> saveCompra(DocumentoFiscal documentoFiscal, boolean isNewCompra) {
		CompraDto compraDto = new CompraDto();
		if (isNewCompra) {
			compraDto = calcGuiaEstaService.calculaGuiaGareIcmsStEntrada(documentoFiscal);
			if (compraDto.isFoiCalculadoIcmsSt()) {
				calcGuiaEstaService.enviarEmail(compraDto);
			}
		}
		calcFiscalEstadual.calculaImpostoEntrada(documentoFiscal);
		calcFiscalFederal.calculaImpostoEntrada(documentoFiscal);
		DocumentoFiscal docFiscal = documentoFiscalRepository.saveAndFlush(documentoFiscal);
		compraDto.setDocFiscal(docFiscal);
		return Optional.ofNullable(compraDto);
	}

	
	/**
	 * Se o frete é pago pelo DESTINATARIO ou TERCEIROS, o Mesmo deverá ser incluído na base de Calculo do ICMS
	 * 
	 * @param docFiscal
	 * @return
	 */
	public boolean isAdicionaFreteNoTotal(DocumentoFiscal docFiscal) {
		FreteConta freteConta = docFiscal.getIndicadorFrete();
	        return ((freteConta != null) && (freteConta.equals(DESTINATARIO) || freteConta.equals(DESTINATARIO_PROPRIO) || freteConta.equals(TERCEIROS)));
//	        		&& 	                (this.freteCalculoPeso || this.freteCalculoPreco || this.freteCalculoUnidade || this.freteCalculoVenda));
	}
	
	
	/**
	 * Método que irá preparar o newDocFiscal, para salvar no Banco de Dados.
	 * PS: Ainda tem que chamar, o método -> {@linkplain} validaDadosESetaValoresNecessarios(...); para o Documento, que será salvo no Banco
	 * 
	 * @param opOldDocFiscal - DocumentoFiscal, que está atualmente no Banco de Dados;
	 * @param newDocFiscal - DocumentoFiscal com as novas informações para serem atualizadas
	 * 
	 * @return Lista de erros que impedem de atualizar o DocumentoFiscal no emissor-fiscal
	 */
	public void prepareDocumentoFiscalToUpdate(Optional<DocumentoFiscal> opOldDocFiscal, DocumentoFiscal newDocFiscal) {
		Map<Integer, DocumentoFiscalItem> mapNewItemPorNumItem = newDocFiscal.getItens().stream().collect(toMap(DocumentoFiscalItem::getItem, newDocFiscItem -> newDocFiscItem));
		DocumentoFiscal oldDocFiscal = opOldDocFiscal.get();
		
		newDocFiscal.setId(oldDocFiscal.getId());
		boolean isItensEquals = newDocFiscal.getItens().containsAll(opOldDocFiscal.get().getItens());
		if (isItensEquals) {
			oldDocFiscal.getItens().stream().forEach(oldItem -> mapNewItemPorNumItem.get(oldItem.getItem()).setId(oldItem.getId()));
		} else {
			docuFiscItemService.deleteByListItens(oldDocFiscal.getItens());
		}
		List<DocumentoFiscalItem> newListItens = new ArrayList<>();
		newListItens.addAll(mapNewItemPorNumItem.values());
		newDocFiscal.setItens(newListItens);
	}

	/**
	 * Valida se as informações necessárias para um documento Fiscal existem, caso sim, as mesmas são setadas no 
	 * documentoFiscal
	 * @param docuFisc
	 * @param validaTribuEsta	- <b> true </b> - Irá verificar/calcular os TRIBUTOS ESTADUAIS
	 * @param validaTribuFede	- <b> true </b> - Irá verificar/calcular os TRIBUTOS FEDERAIS
	 * @return
	 */
	public List<String> validaDadosESetaValoresNecessarios(DocumentoFiscal docuFisc, boolean validaTribuEsta, boolean validaTribuFede) {
		LOG.log(Level.INFO, "Validando e Setando os Valores necessarios para o Documento = {0} ", docuFisc.getDocumento());
		Map<String, Boolean> mapErros = new HashMap<>(); // FALSE == NÃO TEM ERRO | TRUE == TEM ERRO
		Optional<Estado> opUfDestino = estadoService.findBySigla(docuFisc.getDestinatario().getEndereco().getUf());

		super.setValoresNecessariosParaOsItens(docuFisc, mapErros);
		super.setValoresNecessariosParaODocumento(docuFisc, mapErros);
		
		List<TributacaoEstadual> tributacoesEstaduais = new ArrayList<>();
		Set<TributacaoFederal> tributacoesFederais = new HashSet<>();

		if (!mapErros.containsValue(true)) {
			tributacoesEstaduais = icmsService.findTribuEstaByOperUfOrigemUfDestinoRegTribuEFinalidadeENcms(docuFisc);
			tributacoesFederais = tributacaoFederalService.findTributacaoFederalByVariosNcmsEOperacaoEFinalidadeERegimeTributario(docuFisc);
		}
		
		if (validaTribuEsta) {
			validaTributosEstaduais(docuFisc, tributacoesEstaduais, opUfDestino, mapErros);
		}
		
		if (validaTribuFede) {
			validaTributosFederais(docuFisc.getNcms(), tributacoesFederais, mapErros);
		}
		
		return ValidationHelper.processaErros(mapErros);
	}
	

	/**
	 * Método para setar os valores necessários, referente a um DocumentoFiscal, de entrada vindo do ERP.
	 * E quem sabe no futuro, para validar, também caso calcule a entrada por aqui rs.
	 * @param docuFisc
	 * @return
	 */
	public List<String> setaValoresNecessariosCompra(DocumentoFiscal docuFisc) {
		LOG.log(Level.INFO, "Setando os Valores necessarios para (compra) o DocumentoFiscal {0} " ,docuFisc);
		Map<String, Boolean> mapErros = new HashMap<>(); // FALSE == TEM ERRO | TRUE == Não tem
		Optional<Operacao> opOperacao = operacaoService.findOperacaoByDescricao(docuFisc.getOperacao().getDescricao());
		Optional<Pessoa> opEmitente = pessoaService.verificaSePessoaExiste(docuFisc.getEmitente());
		Optional<Pessoa> opDestinatario = pessoaService.verificaSePessoaExiste(docuFisc.getDestinatario());
		
		super.setValoresNecessariosParaOsItens(docuFisc, mapErros);
		defineModeloDocumentoParaServico(docuFisc);
		
//		mapErros.put("O CNPJ: " +docuFisc.getEmitente().getCnpj()+ " do emitente NÃO existe" , !opEmitente.isPresent());
//		mapErros.put("O CNPJ: " +docuFisc.getDestinatario().getCnpj()+ " do destinatário NÃO existe", !opDestinatario.isPresent());
		
		if (!mapErros.containsValue(true)) {
			super.setValoresNecessariosParaODocumentoFiscalEntrada(docuFisc, opOperacao, opEmitente, opDestinatario, mapErros);
		}
		
		return ValidationHelper.processaErros(mapErros);
	}
	
	/**
	 * Irá definir o {@link ModeloDocumentoFiscal}, para alguns tipos de servicos "essenciais". E que hoje em dia para darmos entrada não é através de um xml.
	 * Ou seja, é os famosos "papéis" de: CONTA de ENERGIA, ÁGUA, TELEFONE  etc....
	 * @param documentoFiscal
	 */
	private void defineModeloDocumentoParaServico(DocumentoFiscal docuFisc) {
		ModeloDocumentoFiscal modelo = null;
		switch (docuFisc.getTipoServico()) {
		case NENHUM:
			modelo = docuFisc.getModelo();
			break;
		case OUTROS:
			modelo = ModeloDocumentoFiscal.NFSE;
			break;
		/**	
		 * PS: Comentei esse trecho, pois atualente defino o "tipo de servico", com base no CENTRO CUSTO (lá no ERP);
		 * E aconteceram de informar o de "07.02 - FRETES", para um modelo de DocumentoFiscal, que era NFSE;
		 * 
		 * PS1: Pode acontecer o msm com os outros tipos de serviço nesse método, mas a probabilidade é minima
		 */
//		case CTE: 
////			modelo = ModeloDocumentoFiscal._57;
//			break;
		case ENERGIA: 
			modelo = ModeloDocumentoFiscal._6;
			break;
		case AGUA: 
			modelo = ModeloDocumentoFiscal._29;
			break;
		case INTERNET: 
			modelo = ModeloDocumentoFiscal._21;
			break;
		case TELEFONE_FIXO_MOVEL: 
			modelo = ModeloDocumentoFiscal._22;
			break;
//		default:
//			break;
		}
		docuFisc.setModelo(modelo);
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
	private void validaTributosEstaduais(DocumentoFiscal docuFisc, List<TributacaoEstadual> tributacoesEstaduais, Optional<Estado> opUfDestino, Map<String, Boolean> mapErros) {
		Set<Ncm> ncms = docuFisc.getNcms();
		String operacaoDescricao = docuFisc.getOperacao().getDescricao();
		
		Map<Ncm, Boolean> mapaTribuEstaPorNcm = getMapaTribuEstaAchadaPorNcm(ncms, tributacoesEstaduais);
		
		mapaTribuEstaPorNcm.forEach((kNcm, achouTributacao) -> {
			if(!achouTributacao) {
				StringBuilder msg = new StringBuilder();
				String ufDestinoName[] = {""};
				opUfDestino.ifPresent(ufDestino -> ufDestinoName[0] = ufDestino.getSigla().toString());
				msg.append("Não existe ICMS cadastrado para: | ").append(operacaoDescricao).append(" | e o NCM: ").append(kNcm.getNumero()).append(" | EX: ")
					.append(kNcm.getExcecao()).append(" | com UF Destino: ").append(ufDestinoName[0]);
				mapErros.put(msg.toString(), true);
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
