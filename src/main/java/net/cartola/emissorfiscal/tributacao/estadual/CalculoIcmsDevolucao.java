package net.cartola.emissorfiscal.tributacao.estadual;

import static net.cartola.emissorfiscal.util.NumberUtilRegC100.isBigDecimalMaiorQueZero;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.devolucao.DevolucaoItem;
import net.cartola.emissorfiscal.documento.DocumentoFiscalItem;
import net.cartola.emissorfiscal.operacao.Operacao;
import net.cartola.emissorfiscal.tributacao.CalculoImposto;
import net.cartola.emissorfiscal.tributacao.CalculoImpostoIcms00;
import net.cartola.emissorfiscal.tributacao.CalculoImpostoIcms10;
import net.cartola.emissorfiscal.tributacao.CalculoImpostoIcms20;
import net.cartola.emissorfiscal.tributacao.CalculoImpostoIcms60;
import net.cartola.emissorfiscal.tributacao.CalculoImpostoIcms90;
import net.cartola.emissorfiscal.tributacao.Imposto;
import net.cartola.emissorfiscal.tributacao.federal.CalculoIpi;

/**
 * @date 29 de set. de 2021
 * @author robson.costa
 */
@Service
public class CalculoIcmsDevolucao {

	private static final Logger LOG = Logger.getLogger(CalculoIcmsDevolucao.class.getName());
	
	@Autowired
	private CalculoIpi calculoIpi;
	
	/**
	 * Esse é o método que irá calcular o ICMS para as: DEVOLUÇÕES, REMESSSAS EM GARANTIAS e OUTRAS SAÍDAS
	 * 
	 * @param di
	 * @param tribEstaDevo
	 * @param devoItem
	 * @return 
	 */
	public Optional<CalculoImposto> calculaIcmsDevolucao(DocumentoFiscalItem di, TributacaoEstadualDevolucao tribEstaDevo, DevolucaoItem devoItem) {
		Optional<CalculoImposto> opCalcImposto;

		if(tribEstaDevo.isUsaMesmaCstFornecedor()) {
			opCalcImposto = calculaIcmsDevolucao(di, tribEstaDevo, devoItem, devoItem.getIcmsCstFornecedor());
		} else {
			opCalcImposto = calculaIcmsDevolucao(di, tribEstaDevo, devoItem, tribEstaDevo.getIcmsCst());
		}
		return opCalcImposto;
	}

	private Optional<CalculoImposto> calculaIcmsDevolucao(DocumentoFiscalItem di, TributacaoEstadualDevolucao tribEstaDevo, DevolucaoItem devoItem, int icmsCst) {
		Optional<CalculoImposto> opCalcImposto;
		switch (icmsCst) {
		case 00:
			opCalcImposto = Optional.of((CalculoImpostoIcms00) calculaIcms00(di, tribEstaDevo, devoItem, icmsCst));
			break;
		case 10:
			opCalcImposto = Optional.of((CalculoImpostoIcms10) calculaIcms10(di, tribEstaDevo, devoItem, icmsCst));
			break;
		case 20: 
			opCalcImposto = Optional.of((CalculoImpostoIcms20) calculaIcms20(di, tribEstaDevo, devoItem, icmsCst));
			break;
		case 40:
		case 41:
		case 50:
			opCalcImposto = Optional.of((CalculoImposto) calculaIcms40(di, tribEstaDevo, devoItem, icmsCst));
			break;
		case 60:
			opCalcImposto = Optional.of((CalculoImpostoIcms60) calculaIcms60(di, tribEstaDevo, devoItem, icmsCst));
			break;
		case 90:
			opCalcImposto = Optional.of((CalculoImpostoIcms90) calculaIcms90(di, tribEstaDevo, devoItem, icmsCst));
			break;
		default:
			opCalcImposto = Optional.empty();
			break;
		}
		return opCalcImposto;
	}


	/**
	 * 
	 * @param devoItem
	 * @return Base de calculo do ICMS próprio com a reducao da base de calculo 
	 */
	public BigDecimal calcularIcmsBase(DevolucaoItem devoItem) {
		BigDecimal valorIcmsBase = calcularIcmsBaseSemReducao(devoItem);
		if (isBigDecimalMaiorQueZero(devoItem.getIcmsReducaoBaseAliquota())) {
			final BigDecimal icmsBaseAConsiderar = BigDecimal.ONE.subtract(devoItem.getIcmsReducaoBaseAliquota());
			valorIcmsBase = valorIcmsBase.multiply(icmsBaseAConsiderar);
		}
		return valorIcmsBase;
	}
	
	/**
	 * 
	 * @param devoItem
	 * @return Base de calculo do ICMS próprio sem reducao da BC 
	 */
	public BigDecimal calcularIcmsBaseSemReducao(DevolucaoItem devoItem) {
		final BigDecimal valorIcmsBase = devoItem.getValorUnitario()
				.add(devoItem.getValorSeguro())
				.add(devoItem.getValorOutrasDespesasAcessorias())
				.add(devoItem.getValorFrete())
				.subtract(devoItem.getDesconto())
				.multiply(devoItem.getQuantidade());
		return valorIcmsBase;
	}
	
	private BigDecimal calcularBaseIcmsSt(DevolucaoItem devoItem) {
		BigDecimal icmsIvaAliquota = BigDecimal.ONE.add(devoItem.getIcmsIva());
		if (devoItem.getIcmsIva().compareTo(BigDecimal.ZERO) <= 0) {
//			final BigDecimal icmsIvaAliquota = BigDecimal.ONE.add(devoItem.getIcmsIva());
//			BigDecimal icmsIvaAliquota = BigDecimal.ONE.add(devoItem.g
			icmsIvaAliquota = BigDecimal.ZERO;
		}
//		final BigDecimal baseIcmsSt = calcularIpiDevolvido(devoItem).add(calcularIcmsBase(devoItem));
		final BigDecimal baseIcmsSt = calculoIpi.calcularIpiDevolvido(devoItem).add(calcularIcmsBase(devoItem)).multiply(icmsIvaAliquota);
		return baseIcmsSt;
	}
	
	
	private BigDecimal calcularOutrasDespesasAcessorias(DevolucaoItem devoItem, Operacao operacao) {
		final BigDecimal valorIcms = calcularIcmsBase(devoItem).multiply(devoItem.getIcmsAliquota());
	
		BigDecimal valorTotalFreteAndOutrasDespesDaOrigem = devoItem.getValorFrete()
							.add(devoItem.getValorOutrasDespesasAcessorias())
//							.add(devoItem.getIpi)
							.multiply(devoItem.getQuantidade());
		
		if(operacao.isRemessaParaFornecedor()) {
			BigDecimal valorIpi = calculoIpi.calcularIpiDevolvido(devoItem);
			valorTotalFreteAndOutrasDespesDaOrigem = valorTotalFreteAndOutrasDespesDaOrigem.add(valorIpi);
		}
		
		final BigDecimal icmsStBase = calcularBaseIcmsSt(devoItem);
		BigDecimal valorIcmsSt = BigDecimal.ZERO;
		if (isBigDecimalMaiorQueZero(icmsStBase)) {
			valorIcmsSt = icmsStBase.multiply(devoItem.getIcmsStAliquota()).subtract(valorIcms);
		}
		
		final BigDecimal valorOutrasDespesasAcessorias = valorTotalFreteAndOutrasDespesDaOrigem.add(valorIcmsSt);
		return valorOutrasDespesasAcessorias;
	}
	
	
	private void calculaImpostoBase(DocumentoFiscalItem di, TributacaoEstadualDevolucao tribEstaDevo, CalculoImposto calcIcms, DevolucaoItem devoItem, int icmsCst) {
		LOG.log(Level.INFO, "Calculando o ICMS BASE para a Devolução ");
		
		final BigDecimal valorIcmsBase = calcularIcmsBase(devoItem);
		final BigDecimal valorIcms = valorIcmsBase.multiply(devoItem.getIcmsAliquota());
	
		calcIcms.setValorUnitario(devoItem.getValorUnitario());
		calcIcms.setQuantidade(devoItem.getQuantidade());
		calcIcms.setBaseDeCalculo(valorIcmsBase);
		calcIcms.setAliquota(devoItem.getIcmsAliquota());
//		setOrdem
		calcIcms.setValor(valorIcms);

//		di.setIcmsCst(tribEstaDevo.getIcmsCst());
		di.setIcmsCst(icmsCst);
//		di.setIcmsCest(tributacao.getCest());
		di.setCfop(tribEstaDevo.getCfopNotaDevolucao());
//		di.setCodigoAnp(tributacao.getCodigoAnp());
		di.setValorOutrasDespesasAcessorias(calcularOutrasDespesasAcessorias(devoItem, tribEstaDevo.getOperacao()));
		di.setIcmsBase(valorIcmsBase);
//		di.setIcmsReducaoBaseAliquota(devoItem.getIcmsReducaoBaseAliquota());
		di.setIcmsValor(valorIcms);
		di.setIcmsAliquota(devoItem.getIcmsAliquota());
		di.setIcmsIva(devoItem.getIcmsIva());
		di.setIcmsStAliquota(devoItem.getIcmsStAliquota());
//		di.setIcmsAliquotaDestino(tributacao.getIcmsAliquotaDestino());	
		
		/**
		 * O IPI devolvido não é calculado AQUI
		 */
//		di.setIpiValor(calcularIpiDevolvido(devoItem));

	}
	
	
	private CalculoImpostoIcms00 calculaIcms00(DocumentoFiscalItem di, TributacaoEstadualDevolucao tribEstaDevo, DevolucaoItem devoItem, int icmsCst) {
		LOG.log(Level.INFO, "Calculando o ICMS 10 (DEVOLUCAO) para o ITEM: {0} ", devoItem);
		CalculoImpostoIcms00 icms00 = new CalculoImpostoIcms00();

		this.calculaImpostoBase(di, tribEstaDevo, icms00, devoItem, icmsCst);
		
		return icms00;
	}
	
	private CalculoImpostoIcms10 calculaIcms10(DocumentoFiscalItem di, TributacaoEstadualDevolucao tribEstaDevo, DevolucaoItem devoItem, int icmsCst) {
		CalculoImpostoIcms10 icms10 = new CalculoImpostoIcms10();
		this.calculaImpostoBase(di, tribEstaDevo, icms10, devoItem, icmsCst);
		
		return icms10;
	}

	private CalculoImpostoIcms20 calculaIcms20(DocumentoFiscalItem di, TributacaoEstadualDevolucao tribEstaDevo, DevolucaoItem devoItem, int icmsCst) {
		CalculoImpostoIcms20 icms20 = new CalculoImpostoIcms20();
		final BigDecimal icmsReducaoBaseValor =  calcularIcmsBaseSemReducao(devoItem).multiply(devoItem.getIcmsReducaoBaseAliquota());

		this.calculaImpostoBase(di, tribEstaDevo, icms20, devoItem, icmsCst);
		di.setIcmsReducaoBaseAliquota(devoItem.getIcmsReducaoBaseAliquota());
		di.setIcmsReducaoBaseStAliquota(devoItem.getIcmsReducaoBaseStAliquota());
		di.setIcmsReducaoBaseValor(icmsReducaoBaseValor);
		return icms20;
	}
	
	private CalculoImposto calculaIcms40(DocumentoFiscalItem di, TributacaoEstadualDevolucao tribEstaDevo, DevolucaoItem devoItem, int icmsCst) {
		LOG.log(Level.INFO, "Calculando o ICMS 40 (DEVOLUCAO) para o ITEM: {0} ", devoItem);
		CalculoImposto calcImposto = new CalculoImposto(Imposto.ICMS_40);
		calcImposto.setValorUnitario(BigDecimal.ZERO);
		calcImposto.setQuantidade(BigDecimal.ZERO);
		calcImposto.setBaseDeCalculo(BigDecimal.ZERO);
		calcImposto.setAliquota(BigDecimal.ZERO);
//		calcIcms.setOrdem(di.getId().intValue()); // -> mudar
		calcImposto.setValor(BigDecimal.ZERO);
		
		di.setIcmsCst(icmsCst);
		di.setCfop(tribEstaDevo.getCfopNotaDevolucao());
//		di.setCodigoAnp(tributacao.getCodigoAnp());				// verificar se irei receber isso da origem

		di.setValorOutrasDespesasAcessorias(calcularOutrasDespesasAcessorias(devoItem, tribEstaDevo.getOperacao()));
		di.setIcmsStAliquota(new BigDecimal(0.18));
		
		return calcImposto;
	}
	
	private CalculoImpostoIcms60 calculaIcms60(DocumentoFiscalItem di, TributacaoEstadualDevolucao tribEstaDevo, DevolucaoItem devoItem, int icmsCst) {
		LOG.log(Level.INFO, "Calculando o ICMS 60 (DEVOLUCAO) para o ITEM: {0} ", devoItem);
		CalculoImpostoIcms60 icms60 = new CalculoImpostoIcms60();
		
		di.setIcmsCst(icmsCst);
//		di.setIcmsCst(tribEstaDevo.getIcmsCst());
//		di.setIcmsCest(tributacao.getCest());
		di.setCfop(tribEstaDevo.getCfopNotaDevolucao());
//		di.setCodigoAnp(tributacao.getCodigoAnp());				// verificar se irei receber isso da origem

		di.setValorOutrasDespesasAcessorias(calcularOutrasDespesasAcessorias(devoItem, tribEstaDevo.getOperacao()));
//		di.setIpiValor(calcularIpiDevolvido(devoItem));
//		di.setIcmsStBaseRetido(valorBaseIcmsStRet);
//		di.setIcmsStValorRetido(vlrIcmsStRetido);
//		di.setIcmsStAliquota(devoItem.getIcmsStAliquota());
		di.setIcmsStAliquota(new BigDecimal(0.18));

		return icms60;
	}
	
	private CalculoImpostoIcms90 calculaIcms90(DocumentoFiscalItem di, TributacaoEstadualDevolucao tribEstaDevo, DevolucaoItem devoItem, int icmsCst) {
		LOG.log(Level.INFO, "Calculando o ICMS 90 (DEVOLUCAO) para o ITEM: {0} ", devoItem);
		CalculoImpostoIcms90 icms90 = new CalculoImpostoIcms90();

		icms90.setValorUnitario(di.getValorUnitario());
		icms90.setQuantidade(di.getQuantidade());
		icms90.setBaseDeCalculo(BigDecimal.ZERO);
		icms90.setAliquota(BigDecimal.ZERO);
//		calcIcms.setOrdem(di.getId().intValue()); // -> mudar
		icms90.setValor(BigDecimal.ZERO);
		
		di.setIcmsCst(icmsCst);
		di.setCfop(tribEstaDevo.getCfopNotaDevolucao());
		
		return icms90;
	}
	
}
