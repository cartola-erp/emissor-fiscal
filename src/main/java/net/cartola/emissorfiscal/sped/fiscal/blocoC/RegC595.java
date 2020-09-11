package net.cartola.emissorfiscal.sped.fiscal.blocoC;

import java.util.List;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;

/**
 * 02/09/2020
 * @author robson.costa
 */
@Record(fields = { 
	@Field(name = "reg", maxLength = 4),
	@Field(name = "codObs"),
	@Field(name = "txtCompl"),
	// ==================== FILHO ====================
	@Field(name = "regC597")
})
public class RegC595 {
	
	private final String reg = "C595";
	private String codObs;
	private String txtCompl;
	// ==================== FILHO ====================
	private List<RegC597> regC597;
	
	public String getReg() {
		return reg;
	}

	public String getCodObs() {
		return codObs;
	}

	public void setCodObs(String codObs) {
		this.codObs = codObs;
	}

	public String getTxtCompl() {
		return txtCompl;
	}

	public void setTxtCompl(String txtCompl) {
		this.txtCompl = txtCompl;
	}

	public List<RegC597> getRegC597() {
		return regC597;
	}

	public void setRegC597(List<RegC597> regC597) {
		this.regC597 = regC597;
	}
	
}
