package net.cartola.emissorfiscal.tributacao.estadual;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.documento.DocumentoFiscalItem;
import net.cartola.emissorfiscal.tributacao.CalculoImposto;
import net.cartola.emissorfiscal.tributacao.Imposto;

@Service
public class CalculoIcms {

	public CalculoImposto calculaIcms(DocumentoFiscalItem docItem, TributacaoEstadual tributacao) {
		CalculoImposto icms = new CalculoImposto();
//		calcImposto.setValor(di.getQuantidade().multiply(di.getValorUnitario())); // valor total
//		calcImposto.setBaseDeCalculo(tributacao.getIcmsBase().multiply(calcImposto.getValor())); //valorIcmsBase
		
		BigDecimal valorTotal = docItem.getQuantidade().multiply(docItem.getValorUnitario());
		BigDecimal valorIcmsBase = tributacao.getIcmsBase().multiply(valorTotal);
		BigDecimal valorIcms = valorIcmsBase.multiply(tributacao.getIcmsAliquota());
		
		icms.setImposto(Imposto.ICMS);
		icms.setAliquota(tributacao.getIcmsAliquota());
		icms.setBaseDeCalculo(valorIcmsBase);
//		calcImposto.setOrdem(ordem);
		icms.setQuantidade(docItem.getQuantidade());
		icms.setValorUnitario(docItem.getValorUnitario());  // --> vlr uni de icms para 1 item

		icms.setValor(valorIcms); // valor total
		
		docItem.setIcmsCest(tributacao.getCest());
		docItem.setIcmsAliquota(tributacao.getIcmsAliquota());
		docItem.setIcmsBase(valorIcmsBase);
		docItem.setIcmsValor(valorIcms);
		return icms;
	}
	
	
//	public CalculoImposto calculaIcmsST(DocumentoFiscalItem di, TributacaoEstadual tributacao) {
//		CalculoImposto icmsSt = new CalculoImposto();
//		CalculoImposto icms = calculaIcms(di, tributacao);
//		
//		BigDecimal valorTotal = di.getQuantidade().multiply(di.getValorUnitario());
//		// base IcmsSt = (vlrProduto + IPI + Frete !? +  
////		BigDecimal valorIcmsStBase = di.
//		
//		return null;
//	}

}
