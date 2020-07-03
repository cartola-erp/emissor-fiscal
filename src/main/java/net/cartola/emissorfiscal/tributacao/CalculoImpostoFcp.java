package net.cartola.emissorfiscal.tributacao;

import java.math.BigDecimal;

public class CalculoImpostoFcp extends CalculoImposto {
	
	private static final long serialVersionUID = 7774800497416778238L;

	protected BigDecimal vlrBaseCalcFcp;
	protected BigDecimal fcpAliquota = BigDecimal.ZERO;
	protected BigDecimal valorFcp;
	
	public BigDecimal getVlrBaseCalcFcp() {
		return vlrBaseCalcFcp;
	}
	
	public void setVlrBaseCalcFcp(BigDecimal vlrBaseCalcFcp) {
		this.vlrBaseCalcFcp = vlrBaseCalcFcp;
	}
	
	public BigDecimal getFcpAliquota() {
		return fcpAliquota;
	}

	public void setFcpAliquota(BigDecimal fcpAliquota) {
		this.fcpAliquota = fcpAliquota;
	}
	
	public BigDecimal getValorFcp() {
		return valorFcp;
	}
	
	public void setValorFcp(BigDecimal valorFcp) {
		this.valorFcp = valorFcp;
	}
	
}
