package net.cartola.emissorfiscal.tributacao.federal;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.documento.DocumentoFiscalItem;
import net.cartola.emissorfiscal.tributacao.CalculoImposto;
import net.cartola.emissorfiscal.tributacao.Imposto;

@Service
public class CalculoPisCofins {

	public CalculoImposto calculaPis(DocumentoFiscalItem di, TributacaoFederal tributacao) {
		CalculoImposto pis = new CalculoImposto();
		BigDecimal valorTotal = di.getQuantidade().multiply(di.getValorUnitario());
		BigDecimal valorPisBase = tributacao.getPisBase().multiply(valorTotal);
		BigDecimal valorPis = valorPisBase.multiply(tributacao.getPisAliquota());
		pis.setImposto(Imposto.PIS);
		pis.setAliquota(tributacao.getPisAliquota());
		pis.setBaseDeCalculo(valorPisBase);
		pis.setOrdem(di.getId().intValue());
		pis.setQuantidade(di.getQuantidade());
		pis.setValorUnitario(di.getValorUnitario());
		pis.setValor(valorPis);
		di.setPisAliquota(tributacao.getPisAliquota());
		di.setPisBase(valorPisBase);
		di.setPisValor(valorPis);
		return pis;
	}

	public CalculoImposto calculaCofins(DocumentoFiscalItem di, TributacaoFederal tributacao) {
		CalculoImposto cofins = new CalculoImposto();
		BigDecimal valorTotal = di.getQuantidade().multiply(di.getValorUnitario());
		BigDecimal valorCofinsBase = tributacao.getCofinsBase().multiply(valorTotal);
		BigDecimal valorCofins = valorCofinsBase.multiply(tributacao.getCofinsAliquota());
		cofins.setImposto(Imposto.COFINS);
		cofins.setAliquota(tributacao.getCofinsAliquota());
		cofins.setBaseDeCalculo(valorCofinsBase);
		cofins.setOrdem(di.getId().intValue());
		cofins.setQuantidade(di.getQuantidade());
		cofins.setValorUnitario(di.getValorUnitario());
		cofins.setValor(valorCofins);
		di.setCofinsAliquota(tributacao.getCofinsAliquota());
		di.setCofinsBase(valorCofinsBase);
		di.setCofinsValor(valorCofins);
		return cofins;
	}
}
