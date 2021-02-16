package net.cartola.emissorfiscal.sped.fiscal.blocoC.service;

import java.math.BigDecimal;
import java.util.logging.Logger;

public class NumberUtilRegC100 {

	
	private static final Logger LOG = Logger.getLogger(NumberUtilRegC100.class.getName());

	
	public static String getAliqEntradas(BigDecimal aliqEmDecimal) {
		if (aliqEmDecimal != null && aliqEmDecimal != BigDecimal.ZERO) {
			BigDecimal aliq = aliqEmDecimal.multiply(new BigDecimal(100D));
			return String.valueOf(aliq);
		}
		return "";
	}
	

	public static String getVlrOrBaseCalcEntradas(BigDecimal vlrOrBaseCalc) {
		if (vlrOrBaseCalc != null && vlrOrBaseCalc != BigDecimal.ZERO) {
			return String.valueOf(vlrOrBaseCalc);
		}
		
		return "";
	}
	

}
