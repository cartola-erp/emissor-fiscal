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
    @Field(name = "vlBcIssqn"),
    @Field(name = "aliqIssqn"),
    @Field(name = "vlIssqn")
})
public class RegC172 {
	
	private final String reg = "C172";
    private BigDecimal vlBcIssqn;
    private BigDecimal aliqIssqn;
    private BigDecimal vlIssqn;
	
    public String getReg() {
		return reg;
	}

	public BigDecimal getVlBcIssqn() {
		return vlBcIssqn;
	}

	public void setVlBcIssqn(BigDecimal vlBcIssqn) {
		this.vlBcIssqn = vlBcIssqn;
	}

	public BigDecimal getAliqIssqn() {
		return aliqIssqn;
	}

	public void setAliqIssqn(BigDecimal aliqIssqn) {
		this.aliqIssqn = aliqIssqn;
	}

	public BigDecimal getVlIssqn() {
		return vlIssqn;
	}

	public void setVlIssqn(BigDecimal vlIssqn) {
		this.vlIssqn = vlIssqn;
	}
    
}
