package net.cartola.emissorfiscal.sped.fiscal.blocoE;

import java.math.BigDecimal;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;

/**
 * 11/09/2020
 * @author robson.costa
 */
@Record(fields = { 
		@Field(name = "reg", maxLength = 4),
		@Field(name = "codInfAdic", maxLength = 8),
		@Field(name = "vlInfAdic"),
		@Field(name = "descrComplAj")
})
public class RegE115 {
	
	private final String reg = "E115";
	private String codInfAdic;
	private BigDecimal vlInfAdic;
	private String descrComplAj;
	
	public String getReg() {
		return reg;
	}

	public String getCodInfAdic() {
		return codInfAdic;
	}

	public void setCodInfAdic(String codInfAdic) {
		this.codInfAdic = codInfAdic;
	}

	public BigDecimal getVlInfAdic() {
		return vlInfAdic;
	}

	public void setVlInfAdic(BigDecimal vlInfAdic) {
		this.vlInfAdic = vlInfAdic;
	}

	public String getDescrComplAj() {
		return descrComplAj;
	}

	public void setDescrComplAj(String descrComplAj) {
		this.descrComplAj = descrComplAj;
	}

}
