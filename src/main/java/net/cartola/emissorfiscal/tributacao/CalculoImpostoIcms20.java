package net.cartola.emissorfiscal.tributacao;

import java.math.BigDecimal;

public class CalculoImpostoIcms20  extends CalculoImpostoFcp {

	private static final long serialVersionUID = -2627077232687774288L;

	private BigDecimal aliqReducaoBase;

	public BigDecimal getAliqReducaoBase() {
		return aliqReducaoBase;
	}

	public void setAliqReducaoBase(BigDecimal aliqReducaoBase) {
		this.aliqReducaoBase = aliqReducaoBase;
	}
	
}
