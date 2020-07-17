package net.cartola.emissorfiscal.tributacao.estadual;

import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.documento.DocumentoFiscalItem;
import net.cartola.emissorfiscal.tributacao.CalculoImposto;
import net.cartola.emissorfiscal.tributacao.CalculoImpostoIcms10;
import net.cartola.emissorfiscal.tributacao.CalculoImpostoIcms20;
import net.cartola.emissorfiscal.tributacao.CalculoImpostoIcms30;
import net.cartola.emissorfiscal.tributacao.CalculoImpostoIcms51;
import net.cartola.emissorfiscal.tributacao.CalculoImpostoIcms60;
import net.cartola.emissorfiscal.tributacao.CalculoImpostoIcms70;
import net.cartola.emissorfiscal.tributacao.CalculoImpostoIcms90;
import net.cartola.emissorfiscal.tributacao.Imposto;

@Service
public class CalculoIcms {
	
	private static final Logger LOG = Logger.getLogger(CalculoIcms.class.getName());
	
	public CalculoImposto calculaIcms(DocumentoFiscalItem docItem, TributacaoEstadual tributacao) {

		switch (tributacao.getIcmsCst()) {
		case 00:
			return calculaIcms00(docItem, tributacao);
		case 10:
			return ((CalculoImpostoIcms10) calculaIcms10(docItem, tributacao));
		case 20:
			return ((CalculoImpostoIcms20) calculaIcms20(docItem, tributacao));
		case 30:
			return ((CalculoImpostoIcms30) calculaIcms30(docItem, tributacao));
		case 51:
			return ((CalculoImpostoIcms51) calculaIcms51(docItem, tributacao));
		case 60:
			return ((CalculoImpostoIcms60) calculaIcms60(docItem, tributacao));
		case 70: 
			return ((CalculoImpostoIcms70) calculaIcms70(docItem, tributacao));
		case 90:
			return ((CalculoImpostoIcms90) calculaIcms90(docItem, tributacao));
		default:
			return null;
//			break;
		}

//		return icms;
	}
	
	
	private CalculoImposto calculaIcms00(DocumentoFiscalItem di, TributacaoEstadual tributacao) {
		LOG.log(Level.INFO, "Calculando o ICMS 00 para o ITEM: {0} ", di);
		CalculoImposto icms00 = new CalculoImposto();
		
		BigDecimal valorTotal = di.getQuantidade().multiply(di.getValorUnitario());
		BigDecimal valorIcmsBase = tributacao.getIcmsBase().multiply(valorTotal);
		BigDecimal valorIcms = valorIcmsBase.multiply(tributacao.getIcmsAliquota());
		
		icms00.setImposto(Imposto.ICMS);
		icms00.setAliquota(tributacao.getIcmsAliquota());
		icms00.setBaseDeCalculo(valorIcms);
//		pis.setOrdem(di.getId().intValue()); // -> mudar
		icms00.setQuantidade(di.getQuantidade());
		icms00.setValorUnitario(di.getValorUnitario());
		icms00.setValor(valorIcms);
		di.setIcmsAliquota(tributacao.getIcmsAliquota());
		di.setIcmsBase(valorIcmsBase);
		di.setIcmsValor(valorIcms);
		di.setIcmsCst(tributacao.getIcmsCst());
//		di.setIcmsCest(tributacao.getCest());
		return icms00;
	}
	
	private CalculoImpostoIcms10 calculaIcms10(DocumentoFiscalItem di, TributacaoEstadual tributacao) {
		LOG.log(Level.INFO, "Calculando o ICMS 10 para o ITEM: {0} ", di);
		CalculoImpostoIcms10 icms10 = new CalculoImpostoIcms10();
		
		return icms10;
	}

	private CalculoImpostoIcms20 calculaIcms20(DocumentoFiscalItem di, TributacaoEstadual tributacao) {
		// TODO Auto-generated method stub
		return null;
	}

	private CalculoImpostoIcms30 calculaIcms30(DocumentoFiscalItem di, TributacaoEstadual tributacao) {
		// TODO Auto-generated method stub
		return null;
	}

	private CalculoImpostoIcms51 calculaIcms51(DocumentoFiscalItem di, TributacaoEstadual tributacao) {
		// TODO Auto-generated method stub
		return null;
	}

	private CalculoImpostoIcms60 calculaIcms60(DocumentoFiscalItem di, TributacaoEstadual tributacao) {
		// TODO Auto-generated method stub
		return null;
	}

	private CalculoImpostoIcms70 calculaIcms70(DocumentoFiscalItem di, TributacaoEstadual tributacao) {
		// TODO Auto-generated method stub
		return null;
	}

	private CalculoImpostoIcms90 calculaIcms90(DocumentoFiscalItem di, TributacaoEstadual tributacao) {
		// TODO Auto-generated method stub
		return null;
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
	
	
	
//	public CalculoImposto calculaIcms(DocumentoFiscalItem docItem, TributacaoEstadual tributacao) {
//	CalculoImposto icms = new CalculoImposto();
////	calcImposto.setValor(di.getQuantidade().multiply(di.getValorUnitario())); // valor total
////	calcImposto.setBaseDeCalculo(tributacao.getIcmsBase().multiply(calcImposto.getValor())); //valorIcmsBase
//	
//	BigDecimal valorTotal = docItem.getQuantidade().multiply(docItem.getValorUnitario());
//	BigDecimal valorIcmsBase = tributacao.getIcmsBase().multiply(valorTotal);
//	BigDecimal valorIcms = valorIcmsBase.multiply(tributacao.getIcmsAliquota());
//	
//	icms.setImposto(Imposto.ICMS);
//	icms.setAliquota(tributacao.getIcmsAliquota());
//	icms.setBaseDeCalculo(valorIcmsBase);
////	calcImposto.setOrdem(ordem);
//	icms.setQuantidade(docItem.getQuantidade());
//	icms.setValorUnitario(docItem.getValorUnitario());  // --> vlr uni de icms para 1 item
//
//	icms.setValor(valorIcms); // valor total
//	
//	docItem.setIcmsCest(tributacao.getCest());
//	docItem.setIcmsAliquota(tributacao.getIcmsAliquota());
//	docItem.setIcmsBase(valorIcmsBase);
//	docItem.setIcmsValor(valorIcms);
//	docItem.setIcmsCst(tributacao.getIcmsCst());
//	return icms;
//}

}
