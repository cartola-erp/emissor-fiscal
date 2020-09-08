package net.cartola.emissorfiscal.sped.fiscal.blocoC;

import java.util.List;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;

/**
 * 02/09/2020
 * @author robson.costa
 * 
 * REGISTRO C110: INFORMAÇÃO COMPLEMENTAR DA NOTA FISCAL (CÓDIGO 01; 1B, 04 e 55)
 */
@Record(fields = { 
	@Field(name = "reg", maxLength = 4),
	@Field(name = "codInf"),
	@Field(name = "txtCompl"),
	@Field(name = "regC111"),
	@Field(name = "regC112"),
	@Field(name = "regC113"),
	@Field(name = "regC114"),
	@Field(name = "regC115"),
	@Field(name = "regC116")
})
public class RegC110 {

	private final String reg = "C110";
	private String codInf;
	private String txtCompl;
	private List<RegC111> regC111;
	private List<RegC112> regC112;
	private List<RegC113> regC113;
	private List<RegC114> regC114;
	private List<RegC115> regC115;
	private List<RegC116> regC116;
	
	public String getReg() {
		return reg;
	}

	public String getCodInf() {
		return codInf;
	}

	public void setCodInf(String codInf) {
		this.codInf = codInf;
	}

	public String getTxtCompl() {
		return txtCompl;
	}

	public void setTxtCompl(String txtCompl) {
		this.txtCompl = txtCompl;
	}

	public List<RegC111> getRegC111() {
		return regC111;
	}

	public void setRegC111(List<RegC111> regC111) {
		this.regC111 = regC111;
	}

	public List<RegC112> getRegC112() {
		return regC112;
	}

	public void setRegC112(List<RegC112> regC112) {
		this.regC112 = regC112;
	}

	public List<RegC113> getRegC113() {
		return regC113;
	}

	public void setRegC113(List<RegC113> regC113) {
		this.regC113 = regC113;
	}

	public List<RegC114> getRegC114() {
		return regC114;
	}

	public void setRegC114(List<RegC114> regC114) {
		this.regC114 = regC114;
	}

	public List<RegC115> getRegC115() {
		return regC115;
	}

	public void setRegC115(List<RegC115> regC115) {
		this.regC115 = regC115;
	}

	public List<RegC116> getRegC116() {
		return regC116;
	}

	public void setRegC116(List<RegC116> regC116) {
		this.regC116 = regC116;
	}
}
