package net.cartola.emissorfiscal.sped.fiscal.blocoC;

import java.util.List;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;

/**
 * 02/09/2020
 * @author robson.costa,
 * 
 * REGISTRO C195: OBSERVAÇÕES DO LANÇAMENTO FISCAL (CÓDIGO 01, 1B, 04, 55 e 65)
 */
@Record(fields = { 
	@Field(name = "reg", maxLength = 4),
	@Field(name = "codObs"),
	@Field(name = "txtCompl"),
    // ====================== FILHO ====================== 
	@Field(name = "regC197")
})
public class RegC195 {
	
	private final String reg = "C195";
    private String codObs;
    private String txtCompl;
    // ====================== FILHO ====================== 
    private List<RegC197> regC197;
	
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

	public List<RegC197> getRegC197() {
		return regC197;
	}

	public void setRegC197(List<RegC197> regC197) {
		this.regC197 = regC197;
	}
    
}
