package net.cartola.emissorfiscal.tributacao.estadual;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static net.cartola.emissorfiscal.util.NumberUtilRegC100.getBigDecimalNullSafe;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.devolucao.Devolucao;
import net.cartola.emissorfiscal.devolucao.DevolucaoItem;
import net.cartola.emissorfiscal.devolucao.DevolucaoService;
import net.cartola.emissorfiscal.devolucao.DevolucaoTipo;
import net.cartola.emissorfiscal.documento.DocumentoFiscal;
import net.cartola.emissorfiscal.documento.DocumentoFiscalItem;
import net.cartola.emissorfiscal.documento.DocumentoFiscalItemService;
import net.cartola.emissorfiscal.documento.DocumentoFiscalService;
import net.cartola.emissorfiscal.ncm.Ncm;
import net.cartola.emissorfiscal.operacao.Operacao;
import net.cartola.emissorfiscal.tributacao.CalculoImposto;
import net.cartola.emissorfiscal.tributacao.CalculoImpostoIcms00;
//import net.cartola.emissorfiscal.tributacao.CalculoImpostoFcpSt;
import net.cartola.emissorfiscal.tributacao.CalculoImpostoIcms10;
import net.cartola.emissorfiscal.tributacao.CalculoImpostoIcms30;
import net.cartola.emissorfiscal.tributacao.CalculoImpostoIcms70;
import net.cartola.emissorfiscal.tributacao.CalculoImpostoIcms90;
import net.cartola.emissorfiscal.tributacao.CalculoImpostoIcmsSt;
import net.cartola.emissorfiscal.tributacao.Imposto;
import net.cartola.emissorfiscal.tributacao.federal.CalculoFiscalFederal;
import net.cartola.emissorfiscal.tributacao.federal.CalculoIpi;
import net.cartola.emissorfiscal.util.DocumentoFiscalUtil;


@Service
public class CalculoFiscalEstadual implements CalculoFiscalDevolucao {
	
	private static final Logger LOG = Logger.getLogger(CalculoFiscalEstadual.class.getName());
	
	@Autowired
	private TributacaoEstadualService icmsService;
	
	@Autowired
	private TributacaoEstadualDevolucaoService tribEstaDevolucaoService;
	
	@Autowired
	private DocumentoFiscalService docuFiscService;
	
	@Autowired
	private DocumentoFiscalItemService docFiscItemService;
	
	@Autowired
	private DevolucaoService devolucaoService;
	
	@Autowired
	private CalculoIcms calculoIcms;
	
	@Autowired
	private CalculoIcmsDevolucao calculoIcmsDevolucao;
	
	@Autowired
	private CalculoFiscalFederal calcFiscalFederal;
	
	@Autowired
	private CalculoIpi calculoIpi;
	
	private final Predicate<Devolucao> IS_DEVOLUCAO_DE_CLIENTE = devo -> devo.getOperacao().isDevolucao() && DevolucaoTipo.DE_CLIENTE.equals(devo.getDevolucaoTipo());

	/**
	 * O calculo de imposto retornado aqui é do TOTAL DA NFE (aparentemente)
	 */
	@Override
	public void calculaImposto(DocumentoFiscal documentoFiscal) {
		LOG.log(Level.INFO, "Fazendo o calculo das TRIBUTACÕES ESTADUAIS, para o Documento {0} ", documentoFiscal.getDocumento());
		List<CalculoImposto> listCalculoImpostos = new ArrayList<>();
		List<TributacaoEstadual> listTributacoes = icmsService.findTribuEstaByOperUfOrigemUfDestinoRegTribuEFinalidadeENcms(documentoFiscal);

		Map<Ncm, Map<Boolean, TributacaoEstadual>> mapaTributacoesPorNcmEOrigem = listTributacoes.stream().collect(groupingBy(TributacaoEstadual::getNcm,
				toMap(TributacaoEstadual::isProdutoImportado, (TributacaoEstadual tribEsta) -> tribEsta )));
		
		documentoFiscal.getItens().forEach(docItem -> {
			boolean isProdutoImportado = docFiscItemService.verificaSeEhImportado(docItem);
			TributacaoEstadual tributacao = mapaTributacoesPorNcmEOrigem.get(docItem.getNcm()).get(isProdutoImportado);
			calculoIcms.calculaIcms(docItem, tributacao, documentoFiscal).ifPresent(listCalculoImpostos::add);;
//			listCalculoImpostos.add(calculoIcms.calculaIcms(docItem, tributacao));
		});

		setaIcmsBaseEValor(documentoFiscal, listCalculoImpostos);
		setaIcmsFcpEDifal(documentoFiscal, listCalculoImpostos);
//		setaFcpValor(documentoFiscal, listCalculoImpostos);
//		setaFcpStValor(documentoFiscal, listCalculoImpostos);
		setaIcmsStBaseEValor(documentoFiscal, listCalculoImpostos);
		documentoFiscal.setValorTotalProduto(calcularValorTotalProdutos(documentoFiscal));
		/**
		 * No calculo abaixo, atualmente NÃO ESTOU subtraindo o desconto, pois não é informado no XML....
		 */
		documentoFiscal.setValorTotalDocumento(calcularValorTotalDocumento(documentoFiscal));
	}

	/**
	 * Será calculado o imposto da Devolução para o DocumentoFiscal; PS: Os itens
	 * desse DocumentoFiscal, será criado e calculado nesse método
	 *
	 */
	@Override
	public DocumentoFiscal calculaImposto(DocumentoFiscal docFisc, Devolucao devolucao) {
		List<CalculoImposto> listCalculoImpostos = new ArrayList<>();
		Operacao operacao = devolucao.getOperacao();
		final Set<TributacaoEstadualDevolucao> setTribEsta = tribEstaDevolucaoService.findByOperacao(operacao);
		Map<Integer, Map<Integer, TributacaoEstadualDevolucao>> mapTribEstaDevoPorCfopVendaEIcmsCst = null;
		Map<Integer, TributacaoEstadualDevolucao> mapTribEstaDevoPorCfopVenda = null;
		if (IS_DEVOLUCAO_DE_CLIENTE.test(devolucao)) {
			// devolucao de venda futura precisa do map abaixo, portanto para as Devoluções de cliente será usado ele
			mapTribEstaDevoPorCfopVendaEIcmsCst = setTribEsta.stream()
					.collect(groupingBy(TributacaoEstadualDevolucao::getCfopVenda,
							toMap(TributacaoEstadualDevolucao::getIcmsCst, (tribEstaDevo) -> tribEstaDevo)));
		} else {
			 mapTribEstaDevoPorCfopVenda = setTribEsta.stream().collect(toMap(TributacaoEstadualDevolucao::getCfopVenda, (tribEstaDevo) -> tribEstaDevo));
		}

		final Map<Integer, Map<Long, Map<String, DevolucaoItem>>> mapDevolucaoPorItemCodigoXECodigoSeq = devolucaoService.getMapDevolucaoPorItemCodigoXECodigoSeq(devolucao);
		for (DocumentoFiscalItem di : docFisc.getItens()) {
			DevolucaoItem devolucaoItem = mapDevolucaoPorItemCodigoXECodigoSeq.get(di.getItem()).get(di.getCodigoX()).get(di.getCodigoSequencia());
			TributacaoEstadualDevolucao tribEstaDevo = getTribEstaDevo(mapTribEstaDevoPorCfopVenda, mapTribEstaDevoPorCfopVendaEIcmsCst, devolucaoItem, devolucao);
			if (operacao.isDevolucao()) {
				listCalculoImpostos.add(calculoIpi.calculaIpi(di, devolucaoItem));
			}
			calculoIcmsDevolucao.calculaIcmsDevolucao(di, tribEstaDevo, devolucaoItem, devolucao).ifPresent(listCalculoImpostos::add);
		}

		setaIcmsBaseEValor(docFisc, listCalculoImpostos);
		docFisc.setValorOutrasDespesasAcessorias(calcularTotalOutrasDespesasAcessorias(docFisc));
//		docFisc.setIpiBase(calcularTotalIpiBase(docFisc));
//		docFisc.setIpiValor(calcularTotalIpiDevolvido(docFisc));
		docFisc.setValorTotalProduto(calcularValorTotalProdutos(docFisc));
		calcFiscalFederal.setaIpiBaseValor(docFisc, listCalculoImpostos);
		
		/**
		 * ESTOU subtraindo o desconto aqui, pois não é informado no XML o DESCONTO quando vendemos etc... Atualmente apenas nas DEVOLUCOES
		 */
		BigDecimal valorTotalDocumento = calcularValorTotalDocumento(docFisc).subtract(docFisc.getValorDesconto());
		docFisc.setValorTotalDocumento(valorTotalDocumento);
		
		return docFisc;
	}

	/**
	 * Irá Obter a TributacaoEstadualDevolucao, do mapa, para o item <br>
	 * Conforme a operação de devolução 
	 * 
	 * @param mapTribEstaDevoPorCfopVenda				- Usando em todos os casos em que a devolução NÃO é DE CLIENTE
	 * @param mapTribEstaDevoPorCfopVendaEIcmsCst		- Usado para buscar a parametrização quando a devolução for de cliente
	 * @param devoItem
	 * @param operacao
	 * @return
	 */
	private TributacaoEstadualDevolucao getTribEstaDevo(
			Map<Integer, TributacaoEstadualDevolucao> mapTribEstaDevoPorCfopVenda,
			Map<Integer, Map<Integer, TributacaoEstadualDevolucao>> mapTribEstaDevoPorCfopVendaEIcmsCst,
			DevolucaoItem devoItem, Devolucao devolucao) {
		Operacao operacao = devolucao.getOperacao();
		
		if (IS_DEVOLUCAO_DE_CLIENTE.test(devolucao)) {
			return mapTribEstaDevoPorCfopVendaEIcmsCst.get(devoItem.getCfopFornecedor()).get(devoItem.getIcmsCstFornecedor());
		}
		if (operacao.isDevolucao()) {
			return mapTribEstaDevoPorCfopVenda.get(devoItem.getCfopFornecedor());
		} else if (operacao.isRemessaParaFornecedor()) {
			boolean isFornSn = DocumentoFiscalUtil.isItemFromSimplesNacionalFor(devoItem);
			// Se for "remessa para fornecedor", estará buscando pela CFOP "0", pois não tem uma parametrização especifica para cada cfop da nota de venda do fornecedor (é a msm para qualquer CFOP de venda deles que devolveremos)
			return isFornSn ? mapTribEstaDevoPorCfopVenda.get(devoItem.getCfopFornecedor()) : mapTribEstaDevoPorCfopVenda.get(0);
		}
		return null;
	}

	/**
	 * Atualmente o que tem aqui é o calcular do total, que temos de crédito nas entradas. Referente ao "DocumentoFiscal" (compra)
	 * Pois dos itens já foram feitos, calculados previamente no ERP, porém não é calculado o TOTAL que temos de crédito referente ao DocumentoFiscal.
	 * 
	 * @param docFiscal
	 */
	public void calculaImpostoEntrada(DocumentoFiscal docFiscal) {
		BigDecimal icmsBase = docFiscal.getItens().stream().map(DocumentoFiscalItem::getIcmsBase).reduce(BigDecimal.ZERO, BigDecimal::add); 
		BigDecimal icmsValor = docFiscal.getItens().stream().map(DocumentoFiscalItem::getIcmsValor).reduce(BigDecimal.ZERO, BigDecimal::add);
		
		BigDecimal icmsStBase = docFiscal.getItens().stream().map(DocumentoFiscalItem::getIcmsStBase).reduce(BigDecimal.ZERO, BigDecimal::add);
		BigDecimal icmsStValor = docFiscal.getItens().stream().map(DocumentoFiscalItem::getIcmsStValor).reduce(BigDecimal.ZERO, BigDecimal::add);
		
		docFiscal.setIcmsBase(icmsBase);
		docFiscal.setIcmsValor(icmsValor);
		docFiscal.setIcmsStBase(icmsStBase);
		docFiscal.setIcmsStValor(icmsStValor);
	}

	private void setaIcmsBaseEValor(DocumentoFiscal documentoFiscal, List<CalculoImposto> listCalculoImpostos) {
		LOG.log(Level.INFO, "SETANDO o ICMS BASE e o VALOR para o DocFiscId : {0} ",  documentoFiscal.getId());
//		documentoFiscal.setIcmsBase(documentoFiscal.getItens().stream().map(DocumentoFiscalItem::getIcmsBase)
//				.reduce(BigDecimal.ZERO, BigDecimal::add));
		Predicate<CalculoImposto> adicicionaNoTotal = imp -> !imp.getImposto().equals(Imposto.ICMS_60) && !imp.getImposto().equals(Imposto.IPI);
		
		// Foi colocado dessa forma pois no momento está rodando apenas VENDA e TRANSFERENCIA (Que no momento são CST 00 e 60 apenas, nas duas operacoes)
		documentoFiscal.setIcmsBase(listCalculoImpostos.stream().filter(adicicionaNoTotal::test).map(CalculoImposto::getBaseDeCalculo).reduce(BigDecimal.ZERO, BigDecimal::add));
		 
		documentoFiscal.setIcmsValor(totaliza(listCalculoImpostos.stream()
				.filter(adicicionaNoTotal::test).collect(toList())));
	}

	private void setaIcmsStBaseEValor(DocumentoFiscal documentoFiscal, List<CalculoImposto> listCalculoImpostos) {
		LOG.log(Level.INFO, "SETANDO o ICMS ST BASE e o VALOR  : {0} ", documentoFiscal);
		List<CalculoImpostoIcmsSt> listCalculoIcmsSt = new ArrayList<>();
		
		listCalculoImpostos.forEach(calcImp -> {
			Optional<CalculoImpostoIcmsSt> opCalcIcmsSt = getCalculoImpostoIcmsSt(calcImp);
			opCalcIcmsSt.ifPresent(calcIcmsSt -> listCalculoIcmsSt.add(calcIcmsSt));
		});
		
		documentoFiscal.setIcmsStBase(documentoFiscal.getItens().stream().map(DocumentoFiscalItem::getIcmsStBase)
				.reduce(BigDecimal.ZERO, BigDecimal::add));
		documentoFiscal.setIcmsStValor(totalizaIcmsSt(listCalculoIcmsSt.stream().collect(toList())));
	}
	
	private void setaIcmsFcpEDifal(DocumentoFiscal documentoFiscal, List<CalculoImposto> listCalculoImpostos) {
		LOG.log(Level.INFO, "SETANDO o ICMS FCP e DIFAL para o DocFiscId: {0} ",  documentoFiscal.getId());
		
		List<CalculoImposto> listCalculoIcms = listCalculoImpostos.stream().filter(calcImp -> calcImp.getImposto().equals(Imposto.ICMS_00)).collect(toList());
		List<CalculoImpostoIcms00> listCalculoIcms00 = new ArrayList<>();
		listCalculoIcms.forEach(calcIcms -> listCalculoIcms00.add((CalculoImpostoIcms00) calcIcms));
		
		documentoFiscal.setIcmsFcpValor(totalizaFcp(listCalculoIcms00));
		documentoFiscal.setIcmsValorUfDestino(totalizaDifal(listCalculoIcms00));
	}

	
//	private void setaFcpValor(DocumentoFiscal documentoFiscal, List<CalculoImposto> listCalculoImpostos) {
//		LOG.log(Level.INFO, "Setando o VALOR do FCP : DocFiscal, NUMERO = {0} ", documentoFiscal.getNumero());
////		List<Imposto> cstFcp = Arrays.asList(Imposto.ICMS_10, Imposto.ICMS_20, Imposto.ICMS_70, Imposto.ICMS_90);
//		List<Imposto> cstFcp = Arrays.asList(Imposto.ICMS_00);
//		List<CalculoImpostoFcp> listCalculoImpostoFcp = new ArrayList<>();
//
////		listCalculoImpostos.stream().
////		filter(calcImposto -> calcImposto.getClass().isInstance(CalculoImpostoFcp.class))
////		.findAny().ifPresent(calcFcp -> listCalculoImpostoFcp.add((CalculoImpostoFcp) calcFcp));
//
//		listCalculoImpostos.stream().
//		filter(calcImp -> cstFcp.contains(calcImp.getImposto()))
//		.findAny().ifPresent(calcFcp -> listCalculoImpostoFcp.add((CalculoImpostoFcp) calcFcp));
//		
//		documentoFiscal.setIcmsFcpValor(totalizaFcp(listCalculoImpostoFcp.stream().collect(toList())));
//	}
//	

	
	/**
	 * Irá verificar se o CalculoImposto, recebido, é de alguma CST do ICMS, em que, PODEM, ter o calculo, referente ao ICMS_ST
	 * @param calcImposto
	 * @return Optional de {@link CalculoImpostoIcmsSt}
	 */
	private Optional<CalculoImpostoIcmsSt> getCalculoImpostoIcmsSt(CalculoImposto calcImposto) {
//		List<Imposto> cstFcpSt = Arrays.asList(Imposto.ICMS_10, Imposto.ICMS_30, Imposto.ICMS_70, Imposto.ICMS_90);
		Optional<CalculoImpostoIcmsSt> opCalcIcmsSt;
		switch (calcImposto.getImposto()) {
		case ICMS_10:
			opCalcIcmsSt = Optional.of(((CalculoImpostoIcms10) calcImposto).getCalcIcmsSt());
			break;
		case ICMS_30:
			opCalcIcmsSt = Optional.of(((CalculoImpostoIcms30) calcImposto).getCalcIcmsSt());
			break;
		case ICMS_70:
			opCalcIcmsSt = Optional.of(((CalculoImpostoIcms70) calcImposto).getCalcIcmsSt());
			break;
		case ICMS_90:
			opCalcIcmsSt = Optional.of(((CalculoImpostoIcms90) calcImposto).getCalcIcmsSt());
			break;
		default:
			opCalcIcmsSt = Optional.empty();
		}
		return opCalcIcmsSt;
	}

	
	/**
	 * Calcula a soma do ICMS para os itens
	 * 
	 * @param documentoFiscal
	 */
	private BigDecimal totaliza(List<CalculoImposto> listCalculoImpostos) {
		return listCalculoImpostos.stream().map(CalculoImposto::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);
	}
	
	/**
	 * Calcula a soma do ICMS ST para os itens
	 * 
	 * @param documentoFiscal
	 */
	private BigDecimal totalizaIcmsSt(List<CalculoImpostoIcmsSt> lisCalculoIcmsSt) {
		return lisCalculoIcmsSt.stream().map(CalculoImpostoIcmsSt::getValorIcmsSt).reduce(BigDecimal.ZERO, BigDecimal::add);
	}
	
	
	private BigDecimal totalizaDifal(List<CalculoImpostoIcms00> listCalculoImpostoIcms00) {
		BigDecimal totalDifal = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN);
		try {
			totalDifal = listCalculoImpostoIcms00.stream().map(icms00 -> icms00.getCalcImpostoDifal().getVlrIcmsUfDest())
					.reduce(BigDecimal.ZERO, BigDecimal::add);
		} catch (NullPointerException ex) {
			totalDifal = BigDecimal.ZERO;
		}
		return totalDifal;
	}
	
	private BigDecimal totalizaFcp(List<CalculoImpostoIcms00> listCalculoImpostoIcms00) {
		BigDecimal totalFcp;
		try {
			totalFcp =  listCalculoImpostoIcms00.stream().map(icms00 -> icms00.getCalcImpostoDifal().getVlrFcpUfDest()).reduce(BigDecimal.ZERO, BigDecimal::add);
		} catch (NullPointerException ex) {
			totalFcp = BigDecimal.ZERO;
		}
		return totalFcp;
	}
	
	/**
	 * Soma o valor de todos os produtos (vPROD) para o DocumentoFiscal
	 */
	private BigDecimal calcularValorTotalProdutos(DocumentoFiscal docFiscal) {
		return docFiscal.getItens().stream().map(item -> item.getValorUnitario().multiply(item.getQuantidade())).reduce(BigDecimal.ZERO, BigDecimal::add);
	}
	
	private BigDecimal calcularTotalOutrasDespesasAcessorias(DocumentoFiscal docFiscal) {
		return docFiscal.getItens().stream().map(item -> getBigDecimalNullSafe(item.getValorOutrasDespesasAcessorias())).reduce(BigDecimal.ZERO, BigDecimal::add);
	}
	
	/**
	 * NA realidade tenho que considerar o DESCONTO 
	 * Referente ao campo vNF do XML: vNF = (vProd + vST + vFrete + vSeg + vOutro + VII + vServ) - vDesc 
	 * 
	 * @param documentoFiscal
	 * @return
	 */
	private BigDecimal calcularValorTotalDocumento(DocumentoFiscal docFiscal) {
		BigDecimal valorTotalDocumento = docFiscal.getValorTotalProduto();
		
		if (docuFiscService.isAdicionaFreteNoTotal(docFiscal)) {
			valorTotalDocumento = valorTotalDocumento.add(docFiscal.getValorFrete());
		}
		valorTotalDocumento = valorTotalDocumento.add(getBigDecimalNullSafe(docFiscal.getValorSeguro()))
				.add(getBigDecimalNullSafe(docFiscal.getValorOutrasDespesasAcessorias()))
				.add(getBigDecimalNullSafe(docFiscal.getIpiValor()));
		
		/**
		 * Essa parte está comentado pois, HOJE em dia não "destacamos" o vDESC(valor desconto) no xml, porém mesmo assim esse campos estava vindo preenchido do
		 * ERP, e como não é informado no ERP estava dando diferença
		 */
//		valorTotalDocumento = valorTotalDocumento.subtract((docFiscal.getValorDesconto()));

		return valorTotalDocumento;
	}

}
