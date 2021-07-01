package net.cartola.emissorfiscal.tributacao.estadual;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.documento.DocumentoFiscal;
import net.cartola.emissorfiscal.documento.DocumentoFiscalItem;
import net.cartola.emissorfiscal.documento.DocumentoFiscalItemService;
import net.cartola.emissorfiscal.documento.DocumentoFiscalService;
import net.cartola.emissorfiscal.documento.Finalidade;
import net.cartola.emissorfiscal.estado.Estado;
import net.cartola.emissorfiscal.estado.EstadoService;
import net.cartola.emissorfiscal.ncm.Ncm;
import net.cartola.emissorfiscal.tributacao.CalculoFiscal;
import net.cartola.emissorfiscal.tributacao.CalculoImposto;
import net.cartola.emissorfiscal.tributacao.CalculoImpostoIcms00;
//import net.cartola.emissorfiscal.tributacao.CalculoImpostoFcpSt;
import net.cartola.emissorfiscal.tributacao.CalculoImpostoIcms10;
import net.cartola.emissorfiscal.tributacao.CalculoImpostoIcms30;
import net.cartola.emissorfiscal.tributacao.CalculoImpostoIcms70;
import net.cartola.emissorfiscal.tributacao.CalculoImpostoIcms90;
import net.cartola.emissorfiscal.tributacao.CalculoImpostoIcmsSt;
import net.cartola.emissorfiscal.tributacao.Imposto;


@Service
public class CalculoFiscalEstadual implements CalculoFiscal {

	private static final Logger LOG = Logger.getLogger(CalculoFiscalEstadual.class.getName());
	
	@Autowired
	private TributacaoEstadualService icmsService;

	@Autowired
	private EstadoService estadoService;
	
	@Autowired
	private DocumentoFiscalService docuFiscService;
	
	@Autowired
	private DocumentoFiscalItemService docFiscItemService;
	
	@Autowired
	private CalculoIcms calculoIcms;
	
	/**
	 * O calculo de imposto retornado aqui é do TOTAL DA NFE (aparentemente)
	 */
	@Override
	public void calculaImposto(DocumentoFiscal documentoFiscal) {
		LOG.log(Level.INFO, "Fazendo o calculo das TRIBUTACÕES ESTADUAIS, para o Documento {0} ", documentoFiscal.getDocumento());
		List<CalculoImposto> listCalculoImpostos = new ArrayList<>();
		Set<Ncm> ncms = documentoFiscal.getItens().stream().map(DocumentoFiscalItem::getNcm).collect(Collectors.toSet());
		Set<Finalidade> finalidades = documentoFiscal.getItens().stream().map(DocumentoFiscalItem::getFinalidade).collect(Collectors.toSet());
		Estado estadoOrigem = estadoService.findBySigla(documentoFiscal.getEmitente().getEndereco().getUf()).get();
		Estado estadoDestino = estadoService.findBySigla(documentoFiscal.getDestinatario().getEndereco().getUf()).get();
		
		List<TributacaoEstadual> listTributacoes = icmsService.findTribuEstaByOperUfOrigemUfDestinoRegTribuEFinalidadeENcms(documentoFiscal.getOperacao(), estadoOrigem, estadoDestino, documentoFiscal.getEmitente().getRegimeTributario(), finalidades, ncms);
				
		Map<Ncm, Map<Boolean, TributacaoEstadual>> mapaTributacoesPorNcmEOrigem = listTributacoes.stream().collect(groupingBy(TributacaoEstadual::getNcm,
				Collectors.toMap(TributacaoEstadual::isProdutoImportado, (TributacaoEstadual tribEsta) -> tribEsta )));
		
		documentoFiscal.getItens().forEach(docItem -> {
			boolean isProdutoImportado = docFiscItemService.verificaSeEhImportado(docItem);
			TributacaoEstadual tributacao = mapaTributacoesPorNcmEOrigem.get(docItem.getNcm()).get(isProdutoImportado);
			calculoIcms.calculaIcms(docItem, tributacao, documentoFiscal).ifPresent(calcIcms -> listCalculoImpostos.add(calcIcms));;
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
	 * Atualmente o que tem aqui é o calcular do total, que temos de crédito nas entradas. Referente ao "DocumentoFiscal" (compra)
	 * Pois dos itens já foram feitos, calculados previamente no ERP, porém não é calculado o TOTAL que temos de crédito referente ao DocumentoFiscal.
	 * 
	 * @param docFiscal
	 */
	public void calculaImpostoEntrada(DocumentoFiscal docFiscal) {
		BigDecimal icmsBase = docFiscal.getItens().stream().map(DocumentoFiscalItem::getIcmsBase).reduce(BigDecimal.ZERO, BigDecimal::add); 
		BigDecimal icmsValor = docFiscal.getItens().stream().map(DocumentoFiscalItem::getIcmsValor).reduce(BigDecimal.ZERO, BigDecimal::add);
		
		docFiscal.setIcmsBase(icmsBase);
		docFiscal.setIcmsValor(icmsValor);
	}

	private void setaIcmsBaseEValor(DocumentoFiscal documentoFiscal, List<CalculoImposto> listCalculoImpostos) {
		LOG.log(Level.INFO, "SETANDO o ICMS BASE e o VALOR para o DocFiscId : {0} ",  documentoFiscal.getId());
//		documentoFiscal.setIcmsBase(documentoFiscal.getItens().stream().map(DocumentoFiscalItem::getIcmsBase)
//				.reduce(BigDecimal.ZERO, BigDecimal::add));
		
		// Foi colocado dessa forma pois no momento está rodando apenas VENDA e TRANSFERENCIA (Que no momento são CST 00 e 60 apenas, nas duas operacoes)
		documentoFiscal.setIcmsBase(listCalculoImpostos.stream().filter(calcImp -> 
			!calcImp.getImposto().equals(Imposto.ICMS_60)).map(CalculoImposto::getBaseDeCalculo).reduce(BigDecimal.ZERO, BigDecimal::add));
		 
		documentoFiscal.setIcmsValor(totaliza(listCalculoImpostos.stream()
				.filter(calcImp -> !calcImp.getImposto().equals(Imposto.ICMS_60)).collect(toList())));
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
		valorTotalDocumento = valorTotalDocumento.add(docFiscal.getValorSeguro());
		valorTotalDocumento = valorTotalDocumento.add(docFiscal.getValorOutrasDespesasAcessorias());
		valorTotalDocumento = valorTotalDocumento.add(docFiscal.getIpiValor());
		/**
		 * Essa parte está comentado pois, HOJE em dia não "destacamos" o vDESC(valor desconto) no xml, porém mesmo assim esse campos estava vindo preenchido do
		 * ERP, e como não é informado no ERP estava dando diferença
		 */
//		valorTotalDocumento = valorTotalDocumento.subtract((docFiscal.getValorDesconto()));

		return valorTotalDocumento;
	}
	

}
