package net.cartola.emissorfiscal.tributacao;

import java.math.BigDecimal;

public class CalculoImpostoIcms30 extends CalculoImposto {

	private static final long serialVersionUID = -6800234562794581141L;
	
	private BigDecimal iva;

	private CalculoImpostoIcmsSt calcIcmsSt;

//	 * A autogeral só utiliza o FCP normal
//	private CalculoImpostoFcpSt calcFcpSt = new CalculoImpostoFcpSt();


	public BigDecimal getIva() {
		return iva;
	}

	public void setIva(BigDecimal iva) {
		this.iva = iva;
	}

	public CalculoImpostoIcmsSt getCalcIcmsSt() {
		return calcIcmsSt;
	}
	
	public void setCalcIcmsSt(CalculoImpostoIcmsSt calcIcmsSt) {
		this.calcIcmsSt = calcIcmsSt;
	}

}
