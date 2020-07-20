package net.cartola.emissorfiscal.tributacao.estadual;

import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.documento.DocumentoFiscalItem;
import net.cartola.emissorfiscal.tributacao.CalculoImposto;
import net.cartola.emissorfiscal.tributacao.CalculoImpostoFcp;
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
	
	/**
	 * Irá realizar calculo para a classe mãe: CalculoImposto, (Que são valores que tem em todos os GRUPOS de ICMS)
	 * @param di
	 * @param tributacao
	 * @param calcIcms
	 */
	private void calculaImpostoBase(DocumentoFiscalItem di, TributacaoEstadual tributacao, CalculoImposto calcIcms) {
		LOG.log(Level.INFO, "Calculando o ICMS BASE");
		BigDecimal valorTotal = di.getQuantidade().multiply(di.getValorUnitario());
		BigDecimal valorIcmsBase = tributacao.getIcmsBase().multiply(valorTotal);
		BigDecimal valorIcms = valorIcmsBase.multiply(tributacao.getIcmsAliquota());
		
		calcIcms.setValorUnitario(di.getValorUnitario());
		calcIcms.setQuantidade(di.getQuantidade());
		calcIcms.setBaseDeCalculo(valorIcmsBase);
		calcIcms.setAliquota(tributacao.getIcmsAliquota());
//		calcIcms.setOrdem(di.getId().intValue()); // -> mudar
		calcIcms.setValor(valorIcms);

		di.setIcmsCst(tributacao.getIcmsCst());
		di.setIcmsBase(valorIcmsBase);
		di.setIcmsValor(valorIcms);
	}
	
	/**
	 * Será feito o calculo do: FCP - Fundo de Combate a Pobreza,  para os grupos de ICMS que tiverem
	 * @param di
	 * @param tributacao
	 * @param calcIcmsFcp
	 */
	private void calculaIcmsFcp(DocumentoFiscalItem di, TributacaoEstadual tributacao, CalculoImpostoFcp calcIcmsFcp) {
		LOG.log(Level.INFO, "Calculando o FCP");
		BigDecimal valorIcmsFcpBase = di.getIcmsBase();
		BigDecimal valorFcp = valorIcmsFcpBase.multiply(tributacao.getFcpAliquota());
		
		calcIcmsFcp.setVlrBaseCalcFcp(valorIcmsFcpBase);
		calcIcmsFcp.setFcpAliquota(tributacao.getFcpAliquota());
		calcIcmsFcp.setValorFcp(valorFcp);

		di.setIcmsFcpAliquota(tributacao.getFcpAliquota());
		di.setIcmsFcpValor(valorFcp);
	}
	
	
	private CalculoImposto calculaIcms00(DocumentoFiscalItem di, TributacaoEstadual tributacao) {
		LOG.log(Level.INFO, "Calculando o ICMS 00 para o ITEM: {0} ", di);
		CalculoImposto icms00 = new CalculoImposto();

		icms00.setImposto(Imposto.ICMS);
		calculaImpostoBase(di, tributacao, icms00);
		
		di.setIcmsAliquota(tributacao.getIcmsAliquota());
		di.setIcmsCst(tributacao.getIcmsCst());				
		//		di.setIcmsCest(tributacao.getCest());
		return icms00;
	}
	
	private CalculoImpostoIcms10 calculaIcms10(DocumentoFiscalItem di, TributacaoEstadual tributacao) {
		LOG.log(Level.INFO, "Calculando o ICMS 10 para o ITEM: {0} ", di);
		CalculoImpostoIcms10 icms10 = new CalculoImpostoIcms10();
		
		icms10.setImposto(Imposto.ICMS_ST);
		calculaImpostoBase(di, tributacao, icms10);
		calculaIcmsFcp(di, tributacao, icms10);
		
		// ICMS_ST - CST 10
		BigDecimal valorIcmsStBase = di.getIcmsBase().multiply(tributacao.getIcmsIva()); 
		BigDecimal valorIcmsSt = valorIcmsStBase.multiply(tributacao.getIcmsStAliquota());
		
//		icms10.setModalidadeDaBaseCalculoSt("4");
		icms10.setBaseDeCalculoSt(valorIcmsStBase);
		icms10.setIva(tributacao.getIcmsIva());
		icms10.setAliquotaIcmsSt(tributacao.getIcmsStAliquota());
		icms10.setValorIcmsSt(valorIcmsSt);
		icms10.setAliqReducaoBaseSt(tributacao.getIcmsBase());
		
		
		// calcular o FCP ST aqui
			// 
		// setar no icms10 os valores referentes ao FCP ST
//		icms10.setVl
		
		return icms10;
	}

	private CalculoImpostoIcms20 calculaIcms20(DocumentoFiscalItem di, TributacaoEstadual tributacao) {
		LOG.log(Level.INFO, "Calculando o ICMS 20 para o ITEM: {0} ", di);

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

}
