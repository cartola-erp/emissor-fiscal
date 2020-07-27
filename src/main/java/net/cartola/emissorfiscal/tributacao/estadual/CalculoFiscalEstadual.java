package net.cartola.emissorfiscal.tributacao.estadual;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.documento.DocumentoFiscal;
import net.cartola.emissorfiscal.documento.DocumentoFiscalItem;
import net.cartola.emissorfiscal.documento.Finalidade;
import net.cartola.emissorfiscal.estado.Estado;
import net.cartola.emissorfiscal.estado.EstadoService;
import net.cartola.emissorfiscal.ncm.Ncm;
import net.cartola.emissorfiscal.tributacao.CalculoFiscal;
import net.cartola.emissorfiscal.tributacao.CalculoImposto;
import net.cartola.emissorfiscal.tributacao.CalculoImpostoFcp;
//import net.cartola.emissorfiscal.tributacao.CalculoImpostoFcpSt;
import net.cartola.emissorfiscal.tributacao.CalculoImpostoIcms10;
import net.cartola.emissorfiscal.tributacao.CalculoImpostoIcms30;
import net.cartola.emissorfiscal.tributacao.CalculoImpostoIcms70;
import net.cartola.emissorfiscal.tributacao.CalculoImpostoIcms90;
import net.cartola.emissorfiscal.tributacao.Imposto;

@Service
public class CalculoFiscalEstadual implements CalculoFiscal {

	private static final Logger LOG = Logger.getLogger(CalculoFiscalEstadual.class.getName());
	
	@Autowired
	private TributacaoEstadualService icmsService;

	@Autowired
	private EstadoService estadoService;
	
	@Autowired
	private CalculoIcms calculoIcms;
	
	/**
	 * O calculo de imposto retornado aqui é do TOTAL DA NFE (aparentemente)
	 */
	@Override
	public void calculaImposto(DocumentoFiscal documentoFiscal) {
		LOG.log(Level.INFO, "Fazendo o calculo das TRIBUTACÕES ESTADUAIS, para {0} ", documentoFiscal);
		List<CalculoImposto> listCalculoImpostos = new ArrayList<>();
		Set<Ncm> ncms = documentoFiscal.getItens().stream().map(DocumentoFiscalItem::getNcm).collect(Collectors.toSet());
		Set<Finalidade> finalidades = documentoFiscal.getItens().stream().map(DocumentoFiscalItem::getFinalidade).collect(Collectors.toSet());
		Estado estadoOrigem = estadoService.findBySigla(documentoFiscal.getEmitente().getUf()).get();
		Estado estadoDestino = estadoService.findBySigla(documentoFiscal.getDestinatario().getUf()).get();
		
		List<TributacaoEstadual> listTributacoes = icmsService.findTribuEstaByOperUfOrigemUfDestinoRegTribuEFinalidadeENcms(documentoFiscal.getOperacao(), estadoOrigem, estadoDestino, documentoFiscal.getEmitente().getRegimeTributario(), finalidades, ncms);

		Map<Ncm, TributacaoEstadual> mapaTributacoes = ncms.stream()
				.collect(Collectors.toMap(ncm -> ncm, ncm -> listTributacoes.stream()
						.filter(icms -> icms.getNcm().getId().equals(ncm.getId())).findAny().get()));
		
		documentoFiscal.getItens().forEach(docItem -> {
			TributacaoEstadual tributacao = mapaTributacoes.get(docItem.getNcm());
			listCalculoImpostos.add(calculoIcms.calculaIcms(docItem, tributacao));
		});

		setaIcmsBaseEValor(documentoFiscal, listCalculoImpostos);
		setaFcpValor(documentoFiscal, listCalculoImpostos);
//		setaFcpStValor(documentoFiscal, listCalculoImpostos);
//		setaIcmsStBaseEValor(documentoFiscal, listCalculoImpostos);
	}


	private void setaIcmsBaseEValor(DocumentoFiscal documentoFiscal, List<CalculoImposto> listCalculoImpostos) {
		LOG.log(Level.INFO, "Totalizando o ICMS BASE e o VALOR para : {0} ", documentoFiscal);
		documentoFiscal.setIcmsBase(documentoFiscal.getItens().stream().map(DocumentoFiscalItem::getIcmsBase)
				.reduce(BigDecimal.ZERO, BigDecimal::add));
		
		documentoFiscal.setIcmsValor(totaliza(listCalculoImpostos.stream().collect(toList())));
	}

	private void setaFcpValor(DocumentoFiscal documentoFiscal, List<CalculoImposto> listCalculoImpostos) {
		LOG.log(Level.INFO, "Setando o VALOR do FCP : DocFiscal, NUMERO = {0} ", documentoFiscal.getNumero());
		List<Imposto> cstFcp = Arrays.asList(Imposto.ICMS_10, Imposto.ICMS_20, Imposto.ICMS_70, Imposto.ICMS_90);
		List<CalculoImpostoFcp> listCalculoImpostoFcp = new ArrayList<>();

//		listCalculoImpostos.stream().
//		filter(calcImposto -> calcImposto.getClass().isInstance(CalculoImpostoFcp.class))
//		.findAny().ifPresent(calcFcp -> listCalculoImpostoFcp.add((CalculoImpostoFcp) calcFcp));

		listCalculoImpostos.stream().
		filter(calcImp -> cstFcp.contains(calcImp.getImposto()))
		.findAny().ifPresent(calcFcp -> listCalculoImpostoFcp.add((CalculoImpostoFcp) calcFcp));
		
		documentoFiscal.setIcmsFcpValor(totalizaFcp(listCalculoImpostoFcp.stream().collect(toList())));
	}
	

//	private void setaFcpStValor(DocumentoFiscal documentoFiscal, List<CalculoImposto> listCalculoImpostos) {
//		LOG.log(Level.INFO, "Setando o VALOR do FCP ST : DocFiscal, NUMERO = {0} ", documentoFiscal.getNumero());
////		List<Imposto> cstFcpSt = Arrays.asList(Imposto.ICMS_10, Imposto.ICMS_30, Imposto.ICMS_70, Imposto.ICMS_90);
//		List<CalculoImpostoFcpSt> listCalculoImpostoFcpSt = new ArrayList<>();
//
//		listCalculoImpostos.forEach(calcImp -> {
//			Optional<CalculoImpostoFcpSt> opCalcFcpSt = getFcpSt(calcImp);
//			opCalcFcpSt.ifPresent(calcFcpSt -> listCalculoImpostoFcpSt.add(calcFcpSt));
//		});
//		documentoFiscal.setIcmsFcpStValor(totalizaFcpSt(listCalculoImpostoFcpSt.stream().collect(toList())));
//	}

	/**
	 * Irá verificar se o CalculoImposto, recebido, é de alguma CST do ICMS, em que, PODEM, ter o calculo, referente ao FCP_ST
	 * @param calcImposto
	 * @return Optional de {@link CalculoImpostoFcpSt}
	 */
//	private Optional<CalculoImpostoFcpSt> getFcpSt(CalculoImposto calcImposto) {
////		List<Imposto> cstFcpSt = Arrays.asList(Imposto.ICMS_10, Imposto.ICMS_30, Imposto.ICMS_70, Imposto.ICMS_90);
//		Optional<CalculoImpostoFcpSt> opCalcFcpSt;
//		switch (calcImposto.getImposto()) {
//		case ICMS_10:
//			opCalcFcpSt = Optional.of(((CalculoImpostoIcms10) calcImposto).getCalcFcpSt());
//			break;
//		case ICMS_30:
//			opCalcFcpSt = Optional.of(((CalculoImpostoIcms30) calcImposto).getCalcFcpSt());
//			break;
//		case ICMS_70:
//			opCalcFcpSt = Optional.of(((CalculoImpostoIcms70) calcImposto).getCalcFcpSt());
//			break;
//		case ICMS_90:
//			opCalcFcpSt = Optional.of(((CalculoImpostoIcms90) calcImposto).getCalcFcpSt());
//			break;
//		default:
//			opCalcFcpSt = Optional.empty();
//		}
//		return opCalcFcpSt;
//	}
	
	private void setaIcmsStBaseEValor(DocumentoFiscal documentoFiscal, List<CalculoImposto> listCalculoImpostos) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Calcula a soma do ICMS para os itens
	 * 
	 * @param documentoFiscal
	 */
	private BigDecimal totaliza(List<CalculoImposto> listCalculoImpostos) {
		return listCalculoImpostos.stream().map(CalculoImposto::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);
	}
	
	private <T extends CalculoImpostoFcp> BigDecimal totalizaFcp(List<T> listCalculoImpostoFcp) {
		LOG.log(Level.INFO, "Totalizando o VALOR do FCP para {0} ", listCalculoImpostoFcp);
		return listCalculoImpostoFcp.stream().map(CalculoImpostoFcp::getValorFcp).reduce(BigDecimal.ZERO, BigDecimal::add);
	}
	
//	private BigDecimal totalizaFcpSt(List<CalculoImpostoFcpSt> listCalculoImpostoFcpSt) {
//		LOG.log(Level.INFO, "Totalizando o VALOR do FCP ST {0} ", listCalculoImpostoFcpSt);
//		return listCalculoImpostoFcpSt.stream().map(CalculoImpostoFcpSt::getValorFcpSt).reduce(BigDecimal.ZERO, BigDecimal::add);
//	}
	
	/**
	 * Calcula a soma do ICMS ST para os itens
	 * 
	 * @param documentoFiscal
	 */
	private BigDecimal totalizaIcmsSt(List<CalculoImposto> listCalculoImpostos) {
		// TODO the implementation
		
//		return listCalculoImpostos.stream().map(CalculoImposto::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);
		
		return null;
	}

	private BigDecimal totalizaDifal(DocumentoFiscal docFiscal) {
		//TODO the implemetentantion
		return null;
	}
	
	/**
	 * Calcula o valor total para os Produtos
	 */
	private BigDecimal totalizaProdutos(DocumentoFiscal docFiscal) {
		// TODO the implementation
		return null;
	}
}
