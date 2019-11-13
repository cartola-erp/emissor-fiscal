package net.cartola.emissorfiscal.tributacao.estadual;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.documento.DocumentoFiscalItem;
import net.cartola.emissorfiscal.tributacao.CalculoImposto;
import net.cartola.emissorfiscal.tributacao.Imposto;

@Service
public class CalculoIcms {

	public CalculoImposto calculaIcms(DocumentoFiscalItem di, TributacaoEstadual tributacao) {
		CalculoImposto icms = new CalculoImposto();
//		calcImposto.setValor(di.getQuantidade().multiply(di.getValorUnitario())); // valor total
//		calcImposto.setBaseDeCalculo(tributacao.getIcmsBase().multiply(calcImposto.getValor())); //valorIcmsBase
		
		BigDecimal valorTotal = di.getQuantidade().multiply(di.getValorUnitario());
		BigDecimal valorIcmsBase = tributacao.getIcmsBase().multiply(valorTotal);
		BigDecimal valorIcms = valorIcmsBase.multiply(tributacao.getIcmsAliquota());
		
		icms.setImposto(Imposto.ICMS);
		icms.setAliquota(tributacao.getIcmsAliquota());
		icms.setBaseDeCalculo(valorIcmsBase);
//		calcImposto.setOrdem(ordem);
		icms.setQuantidade(di.getQuantidade());
		icms.setValorUnitario(di.getValorUnitario());  // --> vlr uni de icms para 1 item

		icms.setValor(valorIcms); // valor total
		
		di.setIcmsAliquota(tributacao.getIcmsAliquota());
		di.setIcmsBase(valorIcmsBase);
		di.setIcmsValor(valorIcms);
		return icms;
	}
	
	

}
