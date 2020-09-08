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
	@Field(name = "vlServNt"),
	@Field(name = "vlBcIssqn"),
	@Field(name = "vlIssqn"),
	@Field(name = "vlBcIrrf"),
	@Field(name = "vlIrrf"),
	@Field(name = "vlBcPrev"),
	@Field(name = "vlPrev")
})
public class RegC130 {
	
	private final String reg = "C130";
    private BigDecimal vlServNt;
    private BigDecimal vlBcIssqn;
    private BigDecimal vlIssqn;
    private BigDecimal vlBcIrrf;
    private BigDecimal vlIrrf;
    private BigDecimal vlBcPrev;
    private BigDecimal vlPrev;
	
    public String getReg() {
		return reg;
	}

	public BigDecimal getVlServNt() {
		return vlServNt;
	}

	public void setVlServNt(BigDecimal vlServNt) {
		this.vlServNt = vlServNt;
	}

	public BigDecimal getVlBcIssqn() {
		return vlBcIssqn;
	}

	public void setVlBcIssqn(BigDecimal vlBcIssqn) {
		this.vlBcIssqn = vlBcIssqn;
	}

	public BigDecimal getVlIssqn() {
		return vlIssqn;
	}

	public void setVlIssqn(BigDecimal vlIssqn) {
		this.vlIssqn = vlIssqn;
	}

	public BigDecimal getVlBcIrrf() {
		return vlBcIrrf;
	}

	public void setVlBcIrrf(BigDecimal vlBcIrrf) {
		this.vlBcIrrf = vlBcIrrf;
	}

	public BigDecimal getVlIrrf() {
		return vlIrrf;
	}

	public void setVlIrrf(BigDecimal vlIrrf) {
		this.vlIrrf = vlIrrf;
	}

	public BigDecimal getVlBcPrev() {
		return vlBcPrev;
	}

	public void setVlBcPrev(BigDecimal vlBcPrev) {
		this.vlBcPrev = vlBcPrev;
	}

	public BigDecimal getVlPrev() {
		return vlPrev;
	}

	public void setVlPrev(BigDecimal vlPrev) {
		this.vlPrev = vlPrev;
	}
    
}
