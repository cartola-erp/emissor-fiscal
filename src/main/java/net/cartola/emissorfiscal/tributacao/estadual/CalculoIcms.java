package net.cartola.emissorfiscal.tributacao.estadual;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.documento.DocumentoFiscal;
import net.cartola.emissorfiscal.documento.DocumentoFiscalItem;
import net.cartola.emissorfiscal.pessoa.Pessoa;
import net.cartola.emissorfiscal.pessoa.PessoaTipo;
import net.cartola.emissorfiscal.tributacao.CalculoImposto;
import net.cartola.emissorfiscal.tributacao.CalculoImpostoDifal;
//import net.cartola.emissorfiscal.tributacao.CalculoImpostoFcpSt;
import net.cartola.emissorfiscal.tributacao.CalculoImpostoIcms00;
import net.cartola.emissorfiscal.tributacao.CalculoImpostoIcms10;
import net.cartola.emissorfiscal.tributacao.CalculoImpostoIcms20;
import net.cartola.emissorfiscal.tributacao.CalculoImpostoIcms30;
import net.cartola.emissorfiscal.tributacao.CalculoImpostoIcms51;
import net.cartola.emissorfiscal.tributacao.CalculoImpostoIcms60;
import net.cartola.emissorfiscal.tributacao.CalculoImpostoIcms70;
import net.cartola.emissorfiscal.tributacao.CalculoImpostoIcms90;
import net.cartola.emissorfiscal.tributacao.CalculoImpostoIcmsSt;
import net.cartola.emissorfiscal.tributacao.Imposto;

@Service
public class CalculoIcms {
	
	private static final Logger LOG = Logger.getLogger(CalculoIcms.class.getName());
	
	public Optional<CalculoImposto> calculaIcms(DocumentoFiscalItem docItem, TributacaoEstadual tributacao, DocumentoFiscal documentoFiscal) {
		Optional<CalculoImposto> opCalcImposto;
		switch (tributacao.getIcmsCst()) {
		case 00:
			Pessoa destinatario = documentoFiscal.getDestinatario();
			opCalcImposto = Optional.of(((CalculoImpostoIcms00) calculaIcms00(docItem, tributacao, destinatario)));
			break;
		case 10:
			opCalcImposto = Optional.of(((CalculoImpostoIcms10) calculaIcms10(docItem, tributacao)));
			break;
		case 20:
			opCalcImposto = Optional.of(((CalculoImpostoIcms20) calculaIcms20(docItem, tributacao)));
			break;
		case 30:
			opCalcImposto = Optional.of(((CalculoImpostoIcms30) calculaIcms30(docItem, tributacao)));
			break;
		case 51:
			opCalcImposto = Optional.of(((CalculoImpostoIcms51) calculaIcms51(docItem, tributacao)));
			break;
		case 60:
			opCalcImposto = Optional.of(((CalculoImpostoIcms60) calculaIcms60(docItem, tributacao)));
			break;
		case 70: 
			opCalcImposto = Optional.of(((CalculoImpostoIcms70) calculaIcms70(docItem, tributacao)));
			break;
		case 90:
			opCalcImposto = Optional.of(((CalculoImpostoIcms90) calculaIcms90(docItem, tributacao)));
			break;
		default:
			opCalcImposto = Optional.empty();
		}
		return opCalcImposto;
	}
	
	/**
	 * Irá realizar calculo para a classe mãe: CalculoImposto, (Que são valores que tem em todos os GRUPOS de ICMS)
	 * E setar os valores necessários no DocumentoFiscalItem
	 * 
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
		di.setIcmsCest(tributacao.getCest());
		di.setCfop(tributacao.getCfop());
		di.setIcmsBase(valorIcmsBase);
		di.setIcmsReducaoBaseAliquota(tributacao.getIcmsBase());
		di.setIcmsValor(valorIcms);
		di.setIcmsAliquota(tributacao.getIcmsAliquota());
		di.setIcmsAliquotaDestino(tributacao.getIcmsAliquotaDestino());
	}
	
	/**
	 * Irá Calcular o ICMS ST, que PODEM ser usados nas CSTs do ICMS (10, 30, 70 e 90)
	 * E setar os valores referente ao ICMS ST, no <strong>DocumentoFiscalItem</strong>
	 * 
	 * @param di
	 * @param tributacao
	 * @return {@link CalculoImpostoIcmsSt}
	 */
	private CalculoImpostoIcmsSt calculaIcmsSt(DocumentoFiscalItem di, TributacaoEstadual tributacao) {
		LOG.log(Level.INFO, "Calculando o ICMS ST para o ITEM  ");
		CalculoImpostoIcmsSt icmsSt = new CalculoImpostoIcmsSt();
		
		BigDecimal valorIcmsStBase = di.getIcmsBase().multiply(tributacao.getIcmsIva()); 
		BigDecimal valorIcmsSt = valorIcmsStBase.multiply(tributacao.getIcmsStAliquota());
		
		icmsSt.setBaseDeCalculoSt(valorIcmsStBase);
		icmsSt.setAliquotaIcmsSt(tributacao.getIcmsStAliquota());
		icmsSt.setValorIcmsSt(valorIcmsSt);
		icmsSt.setAliqReducaoBaseSt(tributacao.getIcmsBase());
//		icmsSt.setModalidadeDaBaseCalculoSt("4");
		
		di.setIcmsStBase(icmsSt.getBaseDeCalculoSt());
		di.setIcmsIva(tributacao.getIcmsIva());
		di.setIcmsStAliquota(tributacao.getIcmsStAliquota());
		di.setIcmsStValor(icmsSt.getValorIcmsSt());
		di.setIcmsReducaoBaseStAliquota(tributacao.getIcmsBase());
		return icmsSt;
	}
	
	/**
	 * Calcula o DIFAL para OPERAÇÃO INTERESTADUAL, e pessoa NÃO contribuinte (PF)
	 * Calculo referente aos campos da TAG: ICMSUFDest ("DIFAL") do XML. Para a AG (é somente na CST 00)
	 * 
	 * @param <T> extends CalculoImpostoDifal
	 * @param di
	 * @param tributacao
	 * @param calcDifal
	 */
	private <T extends CalculoImpostoDifal> void calculaDifal(DocumentoFiscalItem di, TributacaoEstadual tributacao, Pessoa destinatario, T calcDifal) {
		// Tbm tenho que verificar se é PF, para poder calcular
		boolean ehEstadosDiferentes = !tributacao.getEstadoOrigem().equals(tributacao.getEstadoDestino());
		if (ehEstadosDiferentes && destinatario.getPessoaTipo().equals(PessoaTipo.FISICA)) {
			LOG.log(Level.INFO, "Calculando o DIFAL da TAG (ICMSUFDest) ");
			BigDecimal valorBaseUfDest = di.getIcmsBase();
			BigDecimal aliqInterDifal = tributacao.getIcmsAliquota().subtract(tributacao.getIcmsAliquotaDestino());
			BigDecimal valorIcmsUfDest = valorBaseUfDest.multiply(aliqInterDifal);
			
			calcDifal.setVlrBaseUfDest(valorBaseUfDest);
			calcDifal.setAliquotaIcmsUfDest(tributacao.getIcmsAliquotaDestino());
			calcDifal.setAliquotaIcmsInter(tributacao.getIcmsAliquota()); 			// -4% - Importada (Ou seja, se ORIGEM = 1), isso vale também p/ o campo pICMS
			calcDifal.setVlrIcmsUfDest(valorIcmsUfDest);
			
			di.setIcmsValorUfDestino(valorIcmsUfDest);
			
			calculaIcmsFcp(di, tributacao, calcDifal);
		}
	}
	
	
	/**
	 * Será feito o calculo do: FCP - Fundo de Combate a Pobreza,  para o ICMS da CST 00 - VENDA INTERESTADUAL.
	 * PS: Segundo a contabilidade, no caso da  AutoGeral, somente nessa CST é preciso calcular o FCP, p/ AG.
	 * Ou seja, se o destino, for para algum estado que tenha FCP, será calculado junto com o "DIFAL", já que ambos nesse caso,
	 * fazem parte da tag "ICMSUFDest", no .XML! Caso NÃO tenha FCP, será calculado somente o "DIFAL".
	 * E setar os valores necessários no DocumentoFiscalItem
	 * 
	 * @param di
	 * @param tributacao
	 * @param calcIcmsFcp
	 */
	private void calculaIcmsFcp(DocumentoFiscalItem di, TributacaoEstadual tributacao, CalculoImpostoDifal calcDifal) {
//		LOG.log(Level.INFO, "Calculando o FCP");
		if (!tributacao.getFcpAliquota().equals(BigDecimal.ZERO)) {
			LOG.log(Level.INFO, "Calculando o FCP da TAG (ICMSUFDest)");
			BigDecimal valorBaseFcpUfDest = calcDifal.getVlrBaseUfDest();
			BigDecimal valorFcpUfDest = valorBaseFcpUfDest.multiply(tributacao.getFcpAliquota());
			
			calcDifal.setVlrBaseFcpUfDest(valorBaseFcpUfDest);
			calcDifal.setAliquotaFcpUfDest(tributacao.getFcpAliquota());
			calcDifal.setVlrFcpUfDest(valorFcpUfDest);
			
			di.setIcmsFcpAliquota(tributacao.getFcpAliquota());
			di.setIcmsFcpValor(valorFcpUfDest);
		}
		
		// ============
//		BigDecimal valorIcmsFcpBase = di.getIcmsBase();
//		BigDecimal valorFcp = valorIcmsFcpBase.multiply(tributacao.getFcpAliquota());
//		
//		calcIcmsFcp.setVlrBaseCalcFcp(valorIcmsFcpBase);
//		calcIcmsFcp.setFcpAliquota(tributacao.getFcpAliquota());
//		calcIcmsFcp.setValorFcp(valorFcp);
//
//		di.setIcmsFcpAliquota(tributacao.getFcpAliquota());
//		di.setIcmsFcpValor(valorFcp);
	}
	
	
	/**
	 * Para autogeral, o DIFAL e FCP somente iram sair nessa CST (Segundo Gabriela da Contabilidade). 
	 * Por isso, não foi feito o calculo de FCP e DIFAL, nas outras CSTs
	 * 
	 * @param di
	 * @param tributacao
	 * @return
	 */
	private CalculoImpostoIcms00 calculaIcms00(DocumentoFiscalItem di, TributacaoEstadual tributacao, Pessoa destinatario) {
		LOG.log(Level.INFO, "Calculando o ICMS 00 para o ITEM: {0} ", di);
		CalculoImpostoIcms00 icms00 = new CalculoImpostoIcms00();

		icms00.setImposto(Imposto.ICMS_00);
		calculaImpostoBase(di, tributacao, icms00);
		
		di.setIcmsCst(tributacao.getIcmsCst());				

		calculaDifal(di, tributacao, destinatario, icms00.getCalcImpostoDifal());
		return icms00;
	}
	
	private CalculoImpostoIcms10 calculaIcms10(DocumentoFiscalItem di, TributacaoEstadual tributacao) {
		LOG.log(Level.INFO, "Calculando o ICMS 10 para o ITEM: {0} ", di);
		CalculoImpostoIcms10 icms10 = new CalculoImpostoIcms10();
		
		icms10.setImposto(Imposto.ICMS_10);
		calculaImpostoBase(di, tributacao, icms10);
//		calculaIcmsFcp(di, tributacao, icms10);
		
		CalculoImpostoIcmsSt icmsSt = calculaIcmsSt(di, tributacao);
		icms10.setCalcIcmsSt(icmsSt);
		icms10.setIva(tributacao.getIcmsIva());

//		calculaIcmsFcpSt(di, tributacao, icms10.getCalcFcpSt());
		return icms10;
	}

	
	private CalculoImpostoIcms20 calculaIcms20(DocumentoFiscalItem di, TributacaoEstadual tributacao) {
		LOG.log(Level.INFO, "Calculando o ICMS 20 para o ITEM: {0} ", di);
		CalculoImpostoIcms20 icms20 = new CalculoImpostoIcms20();
		icms20.setImposto(Imposto.ICMS_20);

		calculaImpostoBase(di, tributacao, icms20);
		icms20.setAliqReducaoBase(tributacao.getIcmsBase());
		return icms20;
	}

	private CalculoImpostoIcms30 calculaIcms30(DocumentoFiscalItem di, TributacaoEstadual tributacao) {
		LOG.log(Level.INFO, "Calculando o ICMS 30 para o ITEM: {0} ", di);
		CalculoImpostoIcms30 icms30 = new CalculoImpostoIcms30();

		icms30.setImposto(Imposto.ICMS_30);
		calculaImpostoBase(di, tributacao, icms30);
		CalculoImpostoIcmsSt icmsSt = calculaIcmsSt(di, tributacao);
		icms30.setCalcIcmsSt(icmsSt);
		icms30.setIva(tributacao.getIcmsIva());

		return icms30;
	}

	private CalculoImpostoIcms51 calculaIcms51(DocumentoFiscalItem di, TributacaoEstadual tributacao) {
		// TODO Auto-generated method stub
		return new CalculoImpostoIcms51();
	}

	/**
	 * Caso TODOS os valores sejam > 0, é pq encontrou a ultima compra no ERP;
	 * @param di
	 * @return
	 */
	private boolean achouUltimaCompra(DocumentoFiscalItem di) {
		return (!di.getIcmsStBaseUltimaCompra().equals(BigDecimal.ZERO) && !di.getIcmsStValorUltimaCompra().equals(BigDecimal.ZERO) 
				&& !di.getItemQtdCompradaUltimaCompra().equals(BigDecimal.ZERO) && !di.getIcmsStAliqUltimaCompra().equals(BigDecimal.ZERO));
	}
	
	private CalculoImpostoIcms60 calculaIcms60(DocumentoFiscalItem di, TributacaoEstadual tributacao) {
		LOG.log(Level.INFO, "Calculando o ICMS 60 para o ITEM ");
		CalculoImpostoIcms60 icms60 = new CalculoImpostoIcms60();
		BigDecimal valorBaseIcmsStRet = BigDecimal.ZERO;
		BigDecimal vlrIcmsStRetido = BigDecimal.ZERO;
		
		icms60.setImposto(Imposto.ICMS_60);
		
		/**
		 * Considerando a ALIQ. 18% (de SP), na CST 60, quando for calcular o ICMS RET.
		 * Mas o ideal é já salvar essa ALIQ na TRIBUTACAO. E pegar de lá.
		 */
		tributacao.setIcmsStAliquota(new BigDecimal(0.18D));
//		calculaImpostoBase(di, tributacao, icms60);
		
		if (achouUltimaCompra(di)) {
			valorBaseIcmsStRet = di.getIcmsStBaseUltimaCompra().divide(di.getItemQtdCompradaUltimaCompra(), BigDecimal.ROUND_UP)
					.multiply(di.getQuantidade());
			vlrIcmsStRetido = di.getIcmsStValorUltimaCompra().divide(di.getItemQtdCompradaUltimaCompra(), BigDecimal.ROUND_UP)
					.multiply(di.getQuantidade());
		} else {
			valorBaseIcmsStRet = di.getQuantidade().multiply(di.getValorUnitario());
			vlrIcmsStRetido = valorBaseIcmsStRet.multiply(tributacao.getIcmsStAliquota());
		}
		
		icms60.setVlrBaseCalcIcmsStRetido(valorBaseIcmsStRet);
		icms60.setVlrIcmsStRetido(vlrIcmsStRetido);
		// o correto da aliquotaPst é = icmsStAliquota + fcpAliquota
		icms60.setAliquotaPst(tributacao.getIcmsStAliquota());
		icms60.setVlrIcmsSubstituto(vlrIcmsStRetido);
		
		di.setIcmsCst(tributacao.getIcmsCst());
		di.setIcmsCest(tributacao.getCest());
		di.setCfop(tributacao.getCfop());
		
		di.setIcmsStBaseRetido(valorBaseIcmsStRet);
		di.setIcmsStValorRetido(vlrIcmsStRetido);
//		di.setIcmsAliquota(tributacao.getIcmsAliquota());
		di.setIcmsStAliquota(tributacao.getIcmsStAliquota());
		return icms60;
	}
	
	private CalculoImpostoIcms70 calculaIcms70(DocumentoFiscalItem di, TributacaoEstadual tributacao) {
		LOG.log(Level.INFO, "Calculando o ICMS 70 para o ITEM ");
		CalculoImpostoIcms70 icms70 = new CalculoImpostoIcms70();
		
		icms70.setImposto(Imposto.ICMS_70);
		calculaImpostoBase(di, tributacao, icms70);
		CalculoImpostoIcmsSt icmsSt = calculaIcmsSt(di, tributacao);
		icms70.setCalcIcmsSt(icmsSt);
		icms70.setIva(tributacao.getIcmsIva());
		icms70.setAliqReducaoBase(tributacao.getIcmsBase());
		return icms70;
	}

	private CalculoImpostoIcms90 calculaIcms90(DocumentoFiscalItem di, TributacaoEstadual tributacao) {
		LOG.log(Level.INFO, "Calculando o ICMS 90 para o ITEM ");
		CalculoImpostoIcms90 icms90 = new CalculoImpostoIcms90();

		icms90.setImposto(Imposto.ICMS_90);
		calculaImpostoBase(di, tributacao, icms90);
		CalculoImpostoIcmsSt icmsSt = calculaIcmsSt(di, tributacao);
		icms90.setCalcIcmsSt(icmsSt);
		icms90.setIva(tributacao.getIcmsIva());
		icms90.setAliqReducaoBase(tributacao.getIcmsBase());

		return icms90;
	}

}
