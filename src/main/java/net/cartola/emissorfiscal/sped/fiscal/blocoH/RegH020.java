package net.cartola.emissorfiscal.sped.fiscal.blocoH;

import java.math.BigDecimal;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;

/**
 * Quando  o "MOTIVO", for FINAL do PERIODO, esse registro não deverá ser apresentado;
 * 
 * 14/09/2020
 * @author robson.costa
 */
@Record(fields = { 
		@Field(name = "reg", maxLength = 4),
		@Field(name = "cstIcms"),
		@Field(name = "bcIcms"),
		@Field(name = "vlIcms"),
})
public class RegH020 {
	
	private final String reg = "H020";
	private int cstIcms;
	private BigDecimal bcIcms;
	private BigDecimal vlIcms;
	
	public String getReg() {
		return reg;
	}

	public int getCstIcms() {
		return cstIcms;
	}

	public void setCstIcms(int cstIcms) {
		this.cstIcms = cstIcms;
	}

	public BigDecimal getBcIcms() {
		return bcIcms;
	}

	public void setBcIcms(BigDecimal bcIcms) {
		this.bcIcms = bcIcms;
	}

	public BigDecimal getVlIcms() {
		return vlIcms;
	}

	public void setVlIcms(BigDecimal vlIcms) {
		this.vlIcms = vlIcms;
	}
	
}
