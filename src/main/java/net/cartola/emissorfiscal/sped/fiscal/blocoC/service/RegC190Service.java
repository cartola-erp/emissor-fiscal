package net.cartola.emissorfiscal.sped.fiscal.blocoC.service;

import static java.util.stream.Collectors.groupingBy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import static net.cartola.emissorfiscal.util.NumberUtilRegC100.multiplicaAliqPorCem;
import static net.cartola.emissorfiscal.util.SpedFiscalUtil.getCstIcmsComOrigem;

import net.cartola.emissorfiscal.documento.DocumentoFiscal;
import net.cartola.emissorfiscal.documento.DocumentoFiscalItem;
import net.cartola.emissorfiscal.documento.ProdutoOrigem;
import net.cartola.emissorfiscal.sped.fiscal.blocoC.RegC190;

@Service
class RegC190Service {

	/**
	 * Irá fazer o Registro analitico do REC 190, para o DocumentoFiscal recebido.
	 * PS: Por enquanto monta o registro para qualquer DocumentoFiscal, recebido;
	 * 
	 * @param docFisc
	 * @return
	 */
	public List<RegC190> montarGrupoRegC190(DocumentoFiscal docFisc) {
		List<RegC190> listRegC190 = new ArrayList<>();
		
		/**Um mapa dentro do outro, até retornar uma lista no ultimo. Sendo as seguintes Chaves:
		 * ProdutoOrigem, CstIcms, Cfop, Aliquota do Icms --> Retornam uma List<DocumentoFiscalItem>
		 */
		Map<ProdutoOrigem, Map<Integer, Map<Integer, Map<BigDecimal, List<DocumentoFiscalItem>>>>> mapPorOrigemCstCfopAliqIcms = 
				docFisc.getItens().stream().collect(groupingBy(DocumentoFiscalItem::getOrigem, 
				  groupingBy(DocumentoFiscalItem::getIcmsCst, 
						  groupingBy(DocumentoFiscalItem::getCfop, 
								  groupingBy(DocumentoFiscalItem::getIcmsAliquota)))));
		/**
		 * preenchemento o registro C190 para o documento fiscal recebido
		 */
		mapPorOrigemCstCfopAliqIcms.forEach((origem, newMapPorOrigemCstCfopAliqIcms) 
				-> newMapPorOrigemCstCfopAliqIcms.forEach((cstIcms, mapPorCstCfopAliqIcms) 
						-> mapPorCstCfopAliqIcms.forEach((cfop, mapPorCfopAliqIcms) 
								-> mapPorCfopAliqIcms.forEach((aliqIcms, listItens) -> {
											RegC190 regC190 = new RegC190();
//											regC190.setCstIcms(Integer.toString(origem.ordinal())  + Integer.toString(cstIcms));
											regC190.setCstIcms(getCstIcmsComOrigem(origem, cstIcms));
											regC190.setCfop(cfop);
											regC190.setAliqIcms(multiplicaAliqPorCem(aliqIcms));
											regC190.setVlOpr(totalizaVlrOperacao(listItens));
											regC190.setVlBcIcms(totalizaVlrBcIcms(listItens));
											regC190.setVlIcms(totalizaValorIcms(listItens));
											regC190.setVlBcIcmsSt(totalizaVlrBcIcmsSt(listItens));
											regC190.setVlIcmsSt(totalizaValorIcmsSt(listItens));
											regC190.setVlRedBc(totalizaValorRedBc(listItens));		//TODO
											regC190.setVlIpi(totalizaValorIpi(listItens));
											regC190.setCodObs("");			// TODO
											listRegC190.add(regC190);
										}))));
		return listRegC190;
	}

	/**
	 * Como a base do icms já é (ao menos é para aontecer isso) salva corretamente no banco, só estou acrescentando o valor do FCP;
	 * não temos FCP ST, por isso não é somado
	 * 
	 * @param listItens
	 * @return
	 */
	private BigDecimal totalizaVlrOperacao(List<DocumentoFiscalItem> listItens) {
		return listItens.stream().map(item -> item.getIcmsBase().add(item.getIcmsFcpValor().add(item.getIcmsStValor()))).reduce(BigDecimal.ZERO, BigDecimal::add);
//		return listItens.stream().map(item -> getVlrOperacao(item)).reduce(BigDecimal.ZERO, BigDecimal::add);
	}
	
	private BigDecimal totalizaVlrBcIcms(List<DocumentoFiscalItem> listItens) {
		return listItens.stream().map(DocumentoFiscalItem::getIcmsBase).reduce(BigDecimal.ZERO, BigDecimal::add);
	}
	
	private BigDecimal totalizaValorIcms(List<DocumentoFiscalItem> listItens) {
		return listItens.stream().map(DocumentoFiscalItem::getIcmsValor).reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	private BigDecimal totalizaVlrBcIcmsSt(List<DocumentoFiscalItem> listItens) {
		return listItens.stream().map(DocumentoFiscalItem::getIcmsStBase).reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	private BigDecimal totalizaValorIcmsSt(List<DocumentoFiscalItem> listItens) {
		return listItens.stream().map(DocumentoFiscalItem::getIcmsStValor).reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	private BigDecimal totalizaValorRedBc(List<DocumentoFiscalItem> listItens) {
		// TODO
//		return listItens.stream().map(DocumentoFiscalItem::getValor);
		return null;
	}

	private BigDecimal totalizaValorIpi(List<DocumentoFiscalItem> listItens) {
		return listItens.stream().map(DocumentoFiscalItem::getIpiValor).reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	
}
