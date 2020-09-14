package net.cartola.emissorfiscal.sped.fiscal.blocoH;

import java.math.BigDecimal;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;

/**
 * 14/09/2020
 * @author robson.costa
 */
@Record(fields = { 
		@Field(name = "reg", maxLength = 4),
		@Field(name = "vlIcmsOp"),
		@Field(name = "vlBcIcmsSt"),
		@Field(name = "vlIcmsSt"),
		@Field(name = "vlFcp")
})
public class RegH030 {
	
	private final String reg = "H030";
	private BigDecimal vlIcmsOp;
	private BigDecimal vlBcIcmsSt;
	private BigDecimal vlIcmsSt;
	private BigDecimal vlFcp;
	
	public String getReg() {
		return reg;
	}

	public BigDecimal getVlIcmsOp() {
		return vlIcmsOp;
	}

	public void setVlIcmsOp(BigDecimal vlIcmsOp) {
		this.vlIcmsOp = vlIcmsOp;
	}

	public BigDecimal getVlBcIcmsSt() {
		return vlBcIcmsSt;
	}

	public void setVlBcIcmsSt(BigDecimal vlBcIcmsSt) {
		this.vlBcIcmsSt = vlBcIcmsSt;
	}

	public BigDecimal getVlIcmsSt() {
		return vlIcmsSt;
	}

	public void setVlIcmsSt(BigDecimal vlIcmsSt) {
		this.vlIcmsSt = vlIcmsSt;
	}

	public BigDecimal getVlFcp() {
		return vlFcp;
	}

	public void setVlFcp(BigDecimal vlFcp) {
		this.vlFcp = vlFcp;
	}
	
}
