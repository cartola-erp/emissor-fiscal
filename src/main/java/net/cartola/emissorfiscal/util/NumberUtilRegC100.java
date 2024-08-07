package net.cartola.emissorfiscal.util;

import java.math.BigDecimal;
import java.util.logging.Logger;

import net.cartola.emissorfiscal.documento.IndicadorDeOperacao;

/**
 * Classe utilitária (para o SPED ICMS IPI) para obter corretamente a forma que devem ser escriturados os campos abaixo (no REGISTRO C100 e seu filhos):
 * VALOR IMPOSTO, BASE CALCULO e ALIQUOTAS;
 *  
 * @author robson.costa
 */
public final class NumberUtilRegC100 {

	private static final Logger LOG = Logger.getLogger(NumberUtilRegC100.class.getName());

	
	/**
	 ** Multiplica a aliquota por 100, caso seja preciso;
	 *  Se a aliquota passada for ZERO. O retorno é um String VAZIA (" ") 
	 * 
	 * @param aliqEmDecimal
	 * @return <b> " " </b> Retorna String vazia se a aliquota passada é == 0  e for operacao de Entrada. Senão retorna ""
	 */
	public static String getAliqAsString(BigDecimal aliqEmDecimal, IndicadorDeOperacao tipoOperacao) {
		BigDecimal aliquotaConvertida = multiplicaAliqPorCem(aliqEmDecimal);
		if (aliquotaConvertida.compareTo(BigDecimal.ZERO) != 0) {
			return String.valueOf(aliquotaConvertida).replace('.', ',');
		}
//		return (tipoOperacao == IndicadorDeOperacao.ENTRADA) ? " " : "0";
		return "0";
	}
	
	
	/**
	 * Multiplica a aliquota por 100, caso seja preciso;
	 *  Se a aliquota passada for ZERO. O retorno é NULL
	 * 
	 * @param aliqEmDecimal
	 * @return <b> Null </b> Se a aliquota passada == 0 e for operacao de Entrada. Senão retorna ZERO
	 * 
	 */
	public static BigDecimal getAliqAsBigDecimal(BigDecimal aliqEmDecimal, IndicadorDeOperacao tipoOperacao) {
		BigDecimal aliquotaConvertida = multiplicaAliqPorCem(aliqEmDecimal);
		if (aliquotaConvertida.compareTo(BigDecimal.ZERO) != 0) {
			return aliquotaConvertida;
		}
//		return (tipoOperacao == IndicadorDeOperacao.ENTRADA) ? null : BigDecimal.ZERO;
		return BigDecimal.ZERO;
	}
	
	public static BigDecimal multiplicaAliqPorCem(BigDecimal aliqEmDecimal) {
		boolean isAliqEmDecimal = aliqEmDecimal.compareTo(BigDecimal.ONE) == 0;
		if (aliqEmDecimal != null && aliqEmDecimal != BigDecimal.ZERO && !isAliqEmDecimal) {
			BigDecimal aliq = aliqEmDecimal.multiply(new BigDecimal(100D));
			return aliq;
		}
		return aliqEmDecimal;
	}
	
	
	//======================== VALOR IMPOSTO OR BASE CALC ==========================
	
	/**
	 * Se VALOR passado, for == ZERO (0), e a operação for de entrada retorna NULL
	 * Senão, retorna o próprio valor
	 * 
	 * @param vlrImpostoOrBaseCalc
	 * @return
	 */
	public static BigDecimal getVlrOrBaseCalc(BigDecimal vlrImpostoOrBaseCalc, IndicadorDeOperacao tipoOperacao) {
		if (vlrImpostoOrBaseCalc != null && vlrImpostoOrBaseCalc.compareTo(BigDecimal.ZERO) != 0) {
//			return String.valueOf(vlrImpostoOrBaseCalc).replace('.', ',');
			return vlrImpostoOrBaseCalc;
		}
//		return (tipoOperacao == IndicadorDeOperacao.ENTRADA) ? null : BigDecimal.ZERO;
		return BigDecimal.ZERO;
	}
	
	/**
	 * 
	 * @param value
	 * 
	 * @return (0) -> ZERO, se o valor recebido for nulo
	 */
	public static BigDecimal getBigDecimalNullSafe(BigDecimal value) {
		if (value == null) {
			return BigDecimal.ZERO;
		}
		return value;
	}

	/**
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isValorMaiorIgualAZero(BigDecimal value) {
		if (value != null && (value.compareTo(BigDecimal.ZERO)) >= 0) {
			return true;
		}
		return false;
	}
	
	public static boolean isBigDecimalMaiorQueZero(BigDecimal value) {
		if (value != null && (value.compareTo(BigDecimal.ZERO)) > 0) {
			return true;
		}
		return false;
	}
	
	public static boolean isBigDecimalZero(BigDecimal value) {
		if (value != null && (value.compareTo(BigDecimal.ZERO)) == 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * Irá arrendondar o BigDecimal Value; <\br>
	 * Usando o: "<b> setScale(2, BigDecimal.ROUND_UP); <b>
	 * 
	 * @param value
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static BigDecimal getBigDecimalDuasCasas(BigDecimal value) {
		if (value != null) {
			return value.setScale(2, BigDecimal.ROUND_UP);
		} else {
			return value;
		}
		
	}
	
}
