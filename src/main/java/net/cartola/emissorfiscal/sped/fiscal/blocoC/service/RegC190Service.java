package net.cartola.emissorfiscal.sped.fiscal.blocoC.service;

import static net.cartola.emissorfiscal.util.NumberUtilRegC100.getBigDecimalDuasCasas;
import static net.cartola.emissorfiscal.util.NumberUtilRegC100.multiplicaAliqPorCem;
import static net.cartola.emissorfiscal.util.SpedFiscalUtil.getCstIcmsComOrigem;
import static net.cartola.emissorfiscal.util.SpedFiscalUtil.getMapaItensParaRegistroAnalitico;
import static net.cartola.emissorfiscal.util.SpedFiscalUtil.isEntradaConsumoOuAtivo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.documento.DocumentoFiscal;
import net.cartola.emissorfiscal.documento.DocumentoFiscalItem;
import net.cartola.emissorfiscal.documento.ProdutoOrigem;
import net.cartola.emissorfiscal.properties.SpedFiscalProperties;
import net.cartola.emissorfiscal.sped.fiscal.MovimentoMensalIcmsIpi;
import net.cartola.emissorfiscal.sped.fiscal.blocoC.RegC190;
import net.cartola.emissorfiscal.util.SpedFiscalUtil;

@Service
class RegC190Service {

	@Autowired
	private SpedFiscalProperties spedFiscPropertie;
	
	/**
	 * Irá fazer o Registro analitico do REC 190, para o DocumentoFiscal recebido.
	 * PS: Por enquanto monta o registro para qualquer DocumentoFiscal, recebido;
	 * 
	 * @param docFisc
	 * @return
	 */
	public List<RegC190> montarGrupoRegC190(DocumentoFiscal docFisc, MovimentoMensalIcmsIpi movimentosIcmsIpi) {
		List<RegC190> listRegC190 = new ArrayList<>();
		boolean isEntradaConsumo = isEntradaConsumoOuAtivo(docFisc);
		if (isEntradaConsumo) {
			zerarAliqIcmsItens(docFisc);
		}
		
		/**Um mapa dentro do outro, até retornar uma lista no ultimo. Sendo as seguintes Chaves:
		 * ProdutoOrigem, CstIcms, Cfop, Aliquota do Icms --> Retornam uma List<DocumentoFiscalItem>
		 */
		Map<ProdutoOrigem, Map<Integer, Map<Integer, Map<BigDecimal, List<DocumentoFiscalItem>>>>> mapPorOrigemCstCfopAliqIcms = getMapaItensParaRegistroAnalitico(docFisc);

		/**
		 * preenchemento o registro C190 para o documento fiscal recebido
		 */
		mapPorOrigemCstCfopAliqIcms.forEach((origem, newMapPorOrigemCstCfopAliqIcms) 
				-> newMapPorOrigemCstCfopAliqIcms.forEach((cstIcms, mapPorCstCfopAliqIcms) 
						-> mapPorCstCfopAliqIcms.forEach((cfop, mapPorCfopAliqIcms) 
								-> mapPorCfopAliqIcms.forEach((aliqIcms, listItens) -> {
											RegC190 regC190 = new RegC190();
//											regC190.setCstIcms(Integer.toString(origem.ordinal())  + Integer.toString(cstIcms));
											aliqIcms = isEntradaConsumo ? BigDecimal.ZERO : multiplicaAliqPorCem(aliqIcms);
											
											regC190.setCstIcms(getCstIcmsComOrigem(origem, cstIcms));
											regC190.setCfop(cfop);
											regC190.setAliqIcms(aliqIcms);
											regC190.setVlOpr(calcularTotalVlrOperacao(listItens, docFisc));
											regC190.setVlBcIcms(calcularTotalVlrBcIcms(listItens, isEntradaConsumo));
											regC190.setVlIcms(calcularTotalValorIcms(listItens, isEntradaConsumo));
											regC190.setVlBcIcmsSt(calcularTotalVlrBcIcmsSt(listItens));
											regC190.setVlIcmsSt(calcularTotalValorIcmsSt(listItens));
											regC190.setVlRedBc(calcularTotalValorRedBc(listItens, isEntradaConsumo));		//TODO
											regC190.setVlIpi(calcularTotalValorIpi(listItens));
											regC190.setCodObs(Integer.toString(cfop));			// TODO
											listRegC190.add(regC190);
										}))));
		
		movimentosIcmsIpi.addRegistroAnalitico(listRegC190, docFisc);
		return listRegC190;
	}

	
	private void zerarAliqIcmsItens(DocumentoFiscal docFisc) {
		docFisc.getItens().forEach(i -> i.setIcmsAliquota(BigDecimal.ZERO));
	}

	/**
	 * Como a base do icms já é (ao menos é para aontecer isso) salva corretamente no banco, só estou acrescentando o valor do FCP;
	 * não temos FCP ST, por isso não é somado
	 * 
	 * @param listItens
	 * @param docFisc 
	 * @return
	 */
	private BigDecimal calcularTotalVlrOperacao(List<DocumentoFiscalItem> listItens, DocumentoFiscal docFisc) {
		boolean isInformaDesconto = SpedFiscalUtil.isInformaDesconto(docFisc.getTipoOperacao(), this.spedFiscPropertie);

		BigDecimal totalOprSemOsDesconto = listItens.stream().map(item -> item.getQuantidade().multiply(item.getValorUnitario()).
				 							add(item.getValorFrete()).add(item.getIpiValor()).
				 							add(item.getValorSeguro()).add(item.getValorOutrasDespesasAcessorias()).
				 							add(item.getIcmsFcpValor().add(item.getIcmsStValor()))).
		 reduce(BigDecimal.ZERO, BigDecimal::add);
		
		 if (isInformaDesconto) {
			 BigDecimal totalDesconto = listItens.stream().map(DocumentoFiscalItem::getDesconto).reduce(BigDecimal.ZERO, BigDecimal::add);
			 return getBigDecimalDuasCasas(totalOprSemOsDesconto.subtract(totalDesconto));
		 }
//		 return listItens.stream().map(item -> getVlrOperacao(item)).reduce(BigDecimal.ZERO, BigDecimal::add);
		 return getBigDecimalDuasCasas(totalOprSemOsDesconto);
	}
	
	private BigDecimal calcularTotalVlrBcIcms(List<DocumentoFiscalItem> listItens, boolean isEntradaConsumo) {
		BigDecimal vlBcIcms = listItens.stream().map(DocumentoFiscalItem::getIcmsBase).reduce(BigDecimal.ZERO, BigDecimal::add);
		if (isEntradaConsumo) {
			/**
			 * ATUALIZAÇÃO: 22/10/2021
			 * PELO que eu VI nos arquivos de sped de exemplo é para retornar 0, na BC ICMS, quando for entrada de CONSUMO
			 */
			return BigDecimal.ZERO;
//			return vlBcIcms;
		} else {
//			return listItens.stream()
//					.map(DocumentoFiscalItem::getIcmsReducaoBaseValor)
//					.reduce(BigDecimal.ZERO, BigDecimal::add)
//					.add(vlBcIcms);
			return vlBcIcms;
		}
	}
	
	private BigDecimal calcularTotalValorIcms(List<DocumentoFiscalItem> listItens, boolean isEntradaConsumo) {
		if (isEntradaConsumo) {
			return BigDecimal.ZERO;
		}
		return listItens.stream().map(DocumentoFiscalItem::getIcmsValor).reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	private BigDecimal calcularTotalVlrBcIcmsSt(List<DocumentoFiscalItem> listItens) {
		// Pelo que vi nos arquivos de exemplo do SPED, só informamos o VL ICMS ST no
		// REG C197 (cod Aj Apur == ) SP90090278, pois somos o "CONTRIBUINTE SUBSTITUÍDO"
		//		return listItens.stream().map(DocumentoFiscalItem::getIcmsStBase).reduce(BigDecimal.ZERO, BigDecimal::add);
		return BigDecimal.ZERO;
	}

	private BigDecimal calcularTotalValorIcmsSt(List<DocumentoFiscalItem> listItens) {
		// Pelo que vi nos arquivos de exemplo do SPED, só informamos o VL ICMS ST no
		// REG C197 (cod Aj Apur == ) SP90090278, pois somos o "CONTRIBUINTE SUBSTITUÍDO"
		//		return listItens.stream().map(DocumentoFiscalItem::getIcmsStValor).reduce(BigDecimal.ZERO, BigDecimal::add);
		return BigDecimal.ZERO;
	}

	private BigDecimal calcularTotalValorRedBc(List<DocumentoFiscalItem> listItens, boolean isEntradaConsumo) {
		/**
		 * TODO tenho que colocar uma regra aqui referente a ser para comercializacao ou não, pois se for consumo
		 * sempre será zero, e adiciona o icms isento na base do icms proprio
		 */
		if (isEntradaConsumo) {
			return BigDecimal.ZERO;
		}
		return listItens.stream().map(DocumentoFiscalItem::getIcmsReducaoBaseValor).reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	private BigDecimal calcularTotalValorIpi(List<DocumentoFiscalItem> listItens) {
		return listItens.stream().map(DocumentoFiscalItem::getIpiValor).reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	
}
