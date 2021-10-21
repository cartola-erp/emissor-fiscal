package net.cartola.emissorfiscal.tributacao.estadual;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.devolucao.DevolucaoItem;
import net.cartola.emissorfiscal.documento.DocumentoFiscalItem;
import net.cartola.emissorfiscal.tributacao.CalculoImposto;
import net.cartola.emissorfiscal.tributacao.CalculoImpostoIcms00;
import net.cartola.emissorfiscal.tributacao.CalculoImpostoIcms10;
import net.cartola.emissorfiscal.tributacao.CalculoImpostoIcms60;

/**
 * @date 29 de set. de 2021
 * @author robson.costa
 */
@Service
public class CalculoIcmsDevolucao {

	private static final Logger LOG = Logger.getLogger(CalculoIcmsDevolucao.class.getName());
	
	
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
		switch (tribEstaDevo.getIcmsCst()) {
		case 00:
			opCalcImposto = Optional.of((CalculoImpostoIcms00) calculaIcms00(di, tribEstaDevo, devoItem));
			break;
		case 10:
			opCalcImposto = Optional.of((CalculoImpostoIcms10) calculaIcms10(di, tribEstaDevo, devoItem));
			break;
		case 60:
			opCalcImposto = Optional.of(((CalculoImpostoIcms60) calculaIcms60(di, tribEstaDevo, devoItem)));
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
	 * @return Base de calculo do ICMS próprio
	 */
	public BigDecimal calcularIcmsBase (DevolucaoItem devoItem) {
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
		final BigDecimal baseIcmsSt = calcularIpiDevolvido(devoItem).add(calcularIcmsBase(devoItem)).multiply(icmsIvaAliquota);
		return baseIcmsSt;
	}
	
	
	private BigDecimal calcularIpiDevolvido(DevolucaoItem devoItem) {
		BigDecimal valorIpiDevolvido = calcularIcmsBase(devoItem).multiply(devoItem.getIpiAliquota());
		return valorIpiDevolvido;
	}
	
	private BigDecimal calcularOutrasDespesasAcessorias(DevolucaoItem devoItem) {
		final BigDecimal valorIcms = calcularIcmsBase(devoItem).multiply(devoItem.getIcmsAliquota());
	
		final BigDecimal valorTotalFreteAndOutrasDespesDaOrigem = devoItem.getValorFrete()
							.add(devoItem.getValorOutrasDespesasAcessorias())
//							.add(devoItem.getIpi)
							.multiply(devoItem.getQuantidade());
		
		final BigDecimal icmsStBase = calcularBaseIcmsSt(devoItem);
		final BigDecimal valorIcmsSt = icmsStBase.multiply(devoItem.getIcmsStAliquota()).subtract(valorIcms);
		
		final BigDecimal valorOutrasDespesasAcessorias = valorTotalFreteAndOutrasDespesDaOrigem.add(valorIcmsSt);
		return valorOutrasDespesasAcessorias;
	}
	
	
	private void calculaImpostoBase(DocumentoFiscalItem di, TributacaoEstadualDevolucao tribEstaDevo, CalculoImposto calcIcms, DevolucaoItem devoItem) {
		LOG.log(Level.INFO, "Calculando o ICMS BASE para a Devolução ");
		
		final BigDecimal valorIcmsBase = calcularIcmsBase(devoItem);
		final BigDecimal valorIcms = valorIcmsBase.multiply(devoItem.getIcmsAliquota());
	
		calcIcms.setValorUnitario(devoItem.getValorUnitario());
		calcIcms.setQuantidade(devoItem.getQuantidade());
		calcIcms.setBaseDeCalculo(valorIcmsBase);
		calcIcms.setAliquota(devoItem.getIcmsAliquota());
//		setOrdem
		calcIcms.setValor(valorIcms);

		di.setIcmsCst(tribEstaDevo.getIcmsCst());
//		di.setIcmsCest(tributacao.getCest());
		di.setCfop(tribEstaDevo.getCfopNotaDevolucao());
//		di.setCodigoAnp(tributacao.getCodigoAnp());
		di.setValorOutrasDespesasAcessorias(calcularOutrasDespesasAcessorias(devoItem));
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
	
	
	private CalculoImpostoIcms00 calculaIcms00(DocumentoFiscalItem di, TributacaoEstadualDevolucao tribEstaDevo, DevolucaoItem devoItem) {
		LOG.log(Level.INFO, "Calculando o ICMS 10 (DEVOLUCAO) para o ITEM: {0} ", devoItem);
		CalculoImpostoIcms00 icms00 = new CalculoImpostoIcms00();

		this.calculaImpostoBase(di, tribEstaDevo, icms00, devoItem);
		
		return icms00;
	}
	
	private CalculoImpostoIcms10 calculaIcms10(DocumentoFiscalItem di, TributacaoEstadualDevolucao tribEstaDevo, DevolucaoItem devoItem) {
		CalculoImpostoIcms10 icms10 = new CalculoImpostoIcms10();

		this.calculaImpostoBase(di, tribEstaDevo, icms10, devoItem);
		
		/**
		 * Calcular ICMS ST ????
		 */
		
		return icms10;
	}

	private CalculoImpostoIcms60 calculaIcms60(DocumentoFiscalItem di, TributacaoEstadualDevolucao tribEstaDevo, DevolucaoItem devoItem) {
		LOG.log(Level.INFO, "Calculando o ICMS 60 (DEVOLUCAO) para o ITEM: {0} ", devoItem);
		CalculoImpostoIcms60 icms60 = new CalculoImpostoIcms60();
		
		di.setIcmsCst(tribEstaDevo.getIcmsCst());
//		di.setIcmsCest(tributacao.getCest());
		di.setCfop(tribEstaDevo.getCfopNotaDevolucao());
//		di.setCodigoAnp(tributacao.getCodigoAnp());				// verificar se irei receber isso da origem

		di.setValorOutrasDespesasAcessorias(calcularOutrasDespesasAcessorias(devoItem));
//		di.setIpiValor(calcularIpiDevolvido(devoItem));
//		di.setIcmsStBaseRetido(valorBaseIcmsStRet);
//		di.setIcmsStValorRetido(vlrIcmsStRetido);
//		di.setIcmsStAliquota(devoItem.getIcmsStAliquota());
		di.setIcmsStAliquota(new BigDecimal(0.18));

		return icms60;
	}
	
	
}
