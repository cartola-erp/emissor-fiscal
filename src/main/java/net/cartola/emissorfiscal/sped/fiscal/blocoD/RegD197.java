package net.cartola.emissorfiscal.sped.fiscal.blocoD;

import java.math.BigDecimal;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;

/**
 * 11/09/2020
 * @author robson.costa
 */
@Record(fields = { 
	@Field(name = "reg", maxLength = 4),
	@Field(name = "codAj"),
	@Field(name = "descrComplAj"),
	@Field(name = "codItem"),
	@Field(name = "vlBcIcms"),
	@Field(name = "aliqIcms"),
	@Field(name = "vlOutros"),
})
public class RegD197 {
	
	private final String reg = "D197";
	private String codAj;
	private String descrComplAj;
	private String codItem;
	private BigDecimal vlBcIcms;
	private BigDecimal aliqIcms;
	private BigDecimal vlOutros;
	
	public String getReg() {
		return reg;
	}

	public String getCodAj() {
		return codAj;
	}

	public void setCodAj(String codAj) {
		this.codAj = codAj;
	}

	public String getDescrComplAj() {
		return descrComplAj;
	}

	public void setDescrComplAj(String descrComplAj) {
		this.descrComplAj = descrComplAj;
	}

	public String getCodItem() {
		return codItem;
	}

	public void setCodItem(String codItem) {
		this.codItem = codItem;
	}

	public BigDecimal getVlBcIcms() {
		return vlBcIcms;
	}

	public void setVlBcIcms(BigDecimal vlBcIcms) {
		this.vlBcIcms = vlBcIcms;
	}

	public BigDecimal getAliqIcms() {
		return aliqIcms;
	}

	public void setAliqIcms(BigDecimal aliqIcms) {
		this.aliqIcms = aliqIcms;
	}

	public BigDecimal getVlOutros() {
		return vlOutros;
	}

	public void setVlOutros(BigDecimal vlOutros) {
		this.vlOutros = vlOutros;
	}
	
}
