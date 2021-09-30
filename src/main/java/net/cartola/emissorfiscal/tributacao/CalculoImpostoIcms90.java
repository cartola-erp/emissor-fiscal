package net.cartola.emissorfiscal.tributacao;

import java.math.BigDecimal;

public class CalculoImpostoIcms90 extends CalculoImpostoFcp {

	private static final long serialVersionUID = 7229567900282493638L;
	
	private BigDecimal aliqReducaoBase;
	private BigDecimal iva;
	private CalculoImpostoIcmsSt calcIcmsSt;

//	 * A autogeral só utiliza o FCP normal
//	private CalculoImpostoFcpSt calcFcpSt = new CalculoImpostoFcpSt();
	
	public CalculoImpostoIcms90() {
		super.setImposto(Imposto.ICMS_90);
	}
	
	public BigDecimal getAliqReducaoBase() {
		return aliqReducaoBase;
	}
	
	public void setAliqReducaoBase(BigDecimal aliqReducaoBase) {
		this.aliqReducaoBase = aliqReducaoBase;
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
