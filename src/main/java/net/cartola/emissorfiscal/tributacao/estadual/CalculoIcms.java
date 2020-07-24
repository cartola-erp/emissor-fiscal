package net.cartola.emissorfiscal.tributacao.estadual;

import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.documento.DocumentoFiscalItem;
import net.cartola.emissorfiscal.tributacao.CalculoImposto;
import net.cartola.emissorfiscal.tributacao.CalculoImpostoDifal;
import net.cartola.emissorfiscal.tributacao.CalculoImpostoFcp;
import net.cartola.emissorfiscal.tributacao.CalculoImpostoFcpSt;
import net.cartola.emissorfiscal.tributacao.CalculoImpostoIcms00;
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
			return ((CalculoImpostoIcms00) calculaIcms00(docItem, tributacao));
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
	 * E setar os valores necessários no DocumentoFiscalItem
	 * @param di (DocumentoFiscalItem)
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
		//		di.setIcmsCest(tributacao.getCest());
		di.setIcmsBase(valorIcmsBase);
		di.setIcmsValor(valorIcms);
		di.setIcmsAliquota(tributacao.getIcmsAliquota());
	}
	
	/**
	 * Será feito o calculo do: FCP - Fundo de Combate a Pobreza,  para os grupos de ICMS que tiverem
	 * E setar os valores necessários no DocumentoFiscalItem
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
	
	/**
	 * Calcula o FCP ST, para os Grupos de ICMS 10, 30, 70 e 90.
	 * 
	 * @param <T>
	 * @param di
	 * @param tributacao
	 * @param calcFcpSt
	 */
	private <T extends CalculoImpostoFcpSt> void calculaIcmsFcpSt(DocumentoFiscalItem di, TributacaoEstadual tributacao, T calcFcpSt) {
		LOG.log(Level.INFO, "Calculando o FCP ST");
		BigDecimal valorIcmsFcpStBase = di.getIcmsStBase();
		BigDecimal valorFcpSt = valorIcmsFcpStBase.multiply(tributacao.getFcpAliquota());
		
		calcFcpSt.setVlrBaseFcpSt(valorIcmsFcpStBase);
		calcFcpSt.setAliquotaFcpSt(tributacao.getFcpAliquota());
		calcFcpSt.setValorFcpSt(valorFcpSt);
		
		di.setIcmsFcpStAliquota(tributacao.getFcpAliquota());
		di.setIcmsFcpStValor(valorFcpSt);
	}
	
	/**
	 * Calcula o DIFAL para OPERAÇÃO INTERESTADUAL, e pessoa NÃO contribuinte (PF)
	 * Calculo referente aos cammpos da TAG: ICMSUFDest ("DIFAL") do XML
	 * @param <T> extends CalculoImpostoDifal
	 * @param di
	 * @param tributacao
	 * @param calcDifal
	 */
	private <T extends CalculoImpostoDifal> void calculaDifal(DocumentoFiscalItem di, TributacaoEstadual tributacao, T calcDifal) {
		// Tbm tenho que verificar se é PF, para poder calcular
		boolean ehEstadosDiferentes = !tributacao.getEstadoOrigem().equals(tributacao.getEstadoDestino());
		if (ehEstadosDiferentes) {
			LOG.log(Level.INFO, "Calculando o DIFAL da TAG (ICMSUFDest) ");
			BigDecimal valorBaseUfDest = di.getIcmsBase();
			BigDecimal aliqInterDifal = tributacao.getIcmsAliquotaDestino().subtract(tributacao.getIcmsAliquota());
			BigDecimal valorIcmsUfDest = valorBaseUfDest.multiply(aliqInterDifal);
			
			calcDifal.setVlrBaseUfDest(valorBaseUfDest);
			calcDifal.setAliquotaIcmsUfDest(tributacao.getIcmsAliquotaDestino());
			calcDifal.setAliquotaIcmsInter(tributacao.getIcmsAliquota()); 			// -4% - Importada (Ou seja, se ORIGEM = 1), isso vale também p/ o campo pICMS
			calcDifal.setVlrIcmsUfDest(valorIcmsUfDest);
			
			di.setIcmsValorUfDestino(valorIcmsUfDest);
		}
		
		// CASO, o calculo do FCP, seja também SOMENTE para A CST 00, basta eu fazer essa parada aqui (E os outros metodo de FCP eu não usaria)
		// Se for ORIGEM != DESTINO, faça, o calculo;
		if (ehEstadosDiferentes && !tributacao.getFcpAliquota().equals(BigDecimal.ZERO)) {
			LOG.log(Level.INFO, "Calculando o FCP da TAG (ICMSUFDest)");
			BigDecimal valorBaseFcpUfDest = calcDifal.getVlrBaseUfDest();
			BigDecimal valorFcpUfDest = valorBaseFcpUfDest.multiply(tributacao.getFcpAliquota());
			
			calcDifal.setVlrBaseFcpUfDest(valorBaseFcpUfDest);
			calcDifal.setAliquotaFcpUfDest(tributacao.getFcpAliquota());
			calcDifal.setVlrFcpUfDest(valorFcpUfDest);
		}
	}
	
	private CalculoImpostoIcms00 calculaIcms00(DocumentoFiscalItem di, TributacaoEstadual tributacao) {
		LOG.log(Level.INFO, "Calculando o ICMS 00 para o ITEM: {0} ", di);
		CalculoImpostoIcms00 icms00 = new CalculoImpostoIcms00();

		icms00.setImposto(Imposto.ICMS_00);
		calculaImpostoBase(di, tributacao, icms00);
		
		di.setIcmsCst(tributacao.getIcmsCst());				
		//		di.setIcmsCest(tributacao.getCest());
		calculaDifal(di, tributacao, icms00.getCalcImpostoDifal());
		return icms00;
	}
	
	private CalculoImpostoIcms10 calculaIcms10(DocumentoFiscalItem di, TributacaoEstadual tributacao) {
		LOG.log(Level.INFO, "Calculando o ICMS 10 para o ITEM: {0} ", di);
		CalculoImpostoIcms10 icms10 = new CalculoImpostoIcms10();
		
		icms10.setImposto(Imposto.ICMS_10);
		calculaImpostoBase(di, tributacao, icms10);
		calculaIcmsFcp(di, tributacao, icms10);
		
		// ICMS_ST - CST 10
		BigDecimal valorIcmsStBase = di.getIcmsBase().multiply(tributacao.getIcmsIva()); 
		BigDecimal valorIcmsSt = valorIcmsStBase.multiply(tributacao.getIcmsStAliquota());
		
		// ACREDITO QUE PARA CIMA ESTEJA OK
//		icms10.setModalidadeDaBaseCalculoSt("4");
		icms10.setBaseDeCalculoSt(valorIcmsStBase);
		icms10.setIva(tributacao.getIcmsIva());
		icms10.setAliquotaIcmsSt(tributacao.getIcmsStAliquota());
		icms10.setValorIcmsSt(valorIcmsSt);
		icms10.setAliqReducaoBaseSt(tributacao.getIcmsBase());
		
		di.setIcmsStBase(valorIcmsStBase);
		di.setIcmsIva(tributacao.getIcmsIva());
		di.setIcmsStAliquota(tributacao.getIcmsStAliquota());
		di.setIcmsStValor(valorIcmsSt);
		di.setIcmsReducaoBaseStAliquota(tributacao.getIcmsBase());
		
		calculaIcmsFcpSt(di, tributacao, icms10.getCalcFcpSt());

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
