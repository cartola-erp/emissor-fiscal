package net.cartola.emissorfiscal.sped.fiscal.blocoE;

import java.math.BigDecimal;
import java.util.List;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;

/**
 * 11/09/2020
 * @author robson.costa
 */
@Record(fields = { 
		@Field(name = "reg", maxLength = 4),
		@Field(name = "codAjApur"),
		@Field(name = "descrComplAj"),
		@Field(name = "vlAjApur"),
	    // ==================== FILHOS ====================
		@Field(name = "regE112"),
		@Field(name = "regE113")
})
public class RegE111 {
	
	private final String reg = "E111";
	private String codAjApur;
	private String descrComplAj;
	private BigDecimal vlAjApur;
    // ==================== FILHOS ====================
	private List<RegE112> regE112;
	private List<RegE113> regE113;
	
	public String getReg() {
		return reg;
	}

	public String getCodAjApur() {
		return codAjApur;
	}

	public void setCodAjApur(String codAjApur) {
		this.codAjApur = codAjApur;
	}

	public String getDescrComplAj() {
		return descrComplAj;
	}

	public void setDescrComplAj(String descrComplAj) {
		this.descrComplAj = descrComplAj;
	}

	public BigDecimal getVlAjApur() {
		return vlAjApur;
	}

	public void setVlAjApur(BigDecimal vlAjApur) {
		this.vlAjApur = vlAjApur;
	}

	public List<RegE112> getRegE112() {
		return regE112;
	}

	public void setRegE112(List<RegE112> regE112) {
		this.regE112 = regE112;
	}

	public List<RegE113> getRegE113() {
		return regE113;
	}

	public void setRegE113(List<RegE113> regE113) {
		this.regE113 = regE113;
	}
	
}
