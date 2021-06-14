package net.cartola.emissorfiscal.sped.fiscal.blocoC.service;

import static net.cartola.emissorfiscal.util.SpedFiscalUtil.getCstIcmsComOrigem;
import static net.cartola.emissorfiscal.util.SpedFiscalUtil.getMapaItensParaRegistroAnalitico;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.documento.DocumentoFiscal;
import net.cartola.emissorfiscal.documento.DocumentoFiscalItem;
import net.cartola.emissorfiscal.documento.ProdutoOrigem;
import net.cartola.emissorfiscal.sped.fiscal.MovimentoMensalIcmsIpi;
import net.cartola.emissorfiscal.sped.fiscal.blocoC.RegC850;

/**
 * @autor robson.costa
 * @data 29 de abr. de 2021
 */
@Service
public class RegC850Service {

	public List<RegC850> montarGrupoRegC850(DocumentoFiscal satEmititdo, MovimentoMensalIcmsIpi movimentoMensalIcmsIpi) {
		List<RegC850> listRegC850 = new ArrayList<>();
		
		Map<ProdutoOrigem, Map<Integer, Map<Integer, Map<BigDecimal, List<DocumentoFiscalItem>>>>> mapPorOrigemCstCfopAliqIcms = getMapaItensParaRegistroAnalitico(satEmititdo);
		
		mapPorOrigemCstCfopAliqIcms.forEach((origem, newMapPorOrigemCstCfopAliqIcms)
				-> newMapPorOrigemCstCfopAliqIcms.forEach((cstIcms, mapPorCstCfopAliqIcms)
						-> mapPorCstCfopAliqIcms.forEach((cfop, mapPorCfopAliqIcms)
								-> mapPorCfopAliqIcms.forEach((aliqIcms, lisItens) -> {
										RegC850 regC850 = new RegC850();
										regC850.setCstIcms(getCstIcmsComOrigem(origem, cstIcms));
										regC850.setCfop(cfop);
										regC850.setAliqIcms(aliqIcms);
										regC850.setVlOpr(calcularTotalVlrOperacao(lisItens));
										regC850.setVlBcIcms(calcularTotalVlrBcIcms(lisItens));
										regC850.setVlIcms(calcularTotalValorIcms(lisItens));
										regC850.setCodObs("");
										listRegC850.add(regC850);
								}))));
		movimentoMensalIcmsIpi.addRegistroAnalitico(listRegC850, satEmititdo);
		return listRegC850;
	}

	private BigDecimal calcularTotalVlrOperacao(List<DocumentoFiscalItem> lisItens) {
		return lisItens.stream().map(item -> item.getValorUnitario().multiply(item.getQuantidade())).reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	private BigDecimal calcularTotalVlrBcIcms(List<DocumentoFiscalItem> lisItens) {
		return lisItens.stream().map(DocumentoFiscalItem::getIcmsBase).reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	private BigDecimal calcularTotalValorIcms(List<DocumentoFiscalItem> lisItens) {
		return lisItens.stream().map(DocumentoFiscalItem::getIcmsValor).reduce(BigDecimal.ZERO, BigDecimal::add);
	}
	
	
}
