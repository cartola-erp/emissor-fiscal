package net.cartola.emissorfiscal.tributacao;

import java.math.BigDecimal;

public class CalculoImpostoIcms10 extends CalculoImpostoFcp {

	private static final long serialVersionUID = -6800234562794581141L;
	
	private BigDecimal iva;
	private CalculoImpostoIcmsSt calcIcmsSt;
	
	
//	protected BigDecimal reducaoBaseAliquota;			//	setIcmsReducaoBaseAliquota
//	protected BigDecimal vlrBaseFcp;
//	protected BigDecimal fcpAliquota = BigDecimal.ZERO;
//	protected BigDecimal valorFcp;
	
	
//	 * A autogeral só utiliza o FCP normal
//	private CalculoImpostoFcpSt calcFcpSt = new CalculoImpostoFcpSt();

	public CalculoImpostoIcms10() {
		super.setImposto(Imposto.ICMS_10);
	}

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
