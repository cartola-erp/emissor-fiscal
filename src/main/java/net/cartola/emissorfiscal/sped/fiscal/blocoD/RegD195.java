package net.cartola.emissorfiscal.sped.fiscal.blocoD;

import java.util.List;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;
import net.cartola.emissorfiscal.sped.fiscal.ObservacoesLancamentoFiscal;

/**
 * 11/09/2020
 * @author robson.costa
 */
@Record(fields = { 
	@Field(name = "reg", maxLength = 4),
	@Field(name = "codObs"),
	@Field(name = "txtCompl"),
    // ==================== FILHO ====================
	@Field(name = "regD197")
})
public class RegD195 extends ObservacoesLancamentoFiscal {
	
	private final String reg = "D195";
//	private String codObs;
//	private String txtCompl;
    // ==================== FILHO ====================
	private List<RegD197> regD197;
	
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

	public List<RegD197> getRegD197() {
		return regD197;
	}

	public void setRegD197(List<RegD197> regD197) {
		this.regD197 = regD197;
	}
	
}
