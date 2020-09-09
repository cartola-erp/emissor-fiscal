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
	@Field(name = "clEnq"),
	@Field(name = "vlUnid"),
	@Field(name = "quantPad")
})
public class RegC178 {

	private final String reg = "C178";
    private String clEnq;
    private BigDecimal vlUnid;
    private Double quantPad;
	
    public String getReg() {
		return reg;
	}

	public String getClEnq() {
		return clEnq;
	}

	public void setClEnq(String clEnq) {
		this.clEnq = clEnq;
	}

	public BigDecimal getVlUnid() {
		return vlUnid;
	}

	public void setVlUnid(BigDecimal vlUnid) {
		this.vlUnid = vlUnid;
	}

	public Double getQuantPad() {
		return quantPad;
	}

	public void setQuantPad(Double quantPad) {
		this.quantPad = quantPad;
	}
    
}
