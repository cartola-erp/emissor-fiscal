package net.cartola.emissorfiscal.sped.fiscal.blocoC;

import java.math.BigDecimal;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;

/**
 * 02/09/2020
 * @author robson.costa
 */
@Record(fields = { 
	@Field(name = "reg", maxLength = 4),
	@Field(name = "vlFcpOp"),
	@Field(name = "vlFcpSt"),
	@Field(name = "vlFcpRet")
})
public class RegC191 {
	
	private final String reg = "C191";
	private BigDecimal vlFcpOp;
	private BigDecimal vlFcpSt;
	private BigDecimal vlFcpRet;
	
	public String getReg() {
		return reg;
	}

	public BigDecimal getVlFcpOp() {
		return vlFcpOp;
	}

	public void setVlFcpOp(BigDecimal vlFcpOp) {
		this.vlFcpOp = vlFcpOp;
	}

	public BigDecimal getVlFcpSt() {
		return vlFcpSt;
	}

	public void setVlFcpSt(BigDecimal vlFcpSt) {
		this.vlFcpSt = vlFcpSt;
	}

	public BigDecimal getVlFcpRet() {
		return vlFcpRet;
	}

	public void setVlFcpRet(BigDecimal vlFcpRet) {
		this.vlFcpRet = vlFcpRet;
	}
	
}
