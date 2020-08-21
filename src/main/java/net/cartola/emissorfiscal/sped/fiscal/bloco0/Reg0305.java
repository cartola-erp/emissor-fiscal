package net.cartola.emissorfiscal.sped.fiscal.bloco0;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;

/**
 * 19/08/2020
 * @author robson.costa
 * 
 * Registro 0305 - Informação sobre a Utilização do Bem
 */
@Record(fields = {
	@Field(name = "reg"),
	@Field(name = "codCcus"),
	@Field(name = "func"),
	@Field(name = "vidaUtil")
})
public class Reg0305 {
	
	private final String reg = "0305";
    private String codCcus;
    private String func;
    private Integer vidaUtil;
	
    public String getReg() {
		return reg;
	}

	public String getCodCcus() {
		return codCcus;
	}

	public void setCodCcus(String codCcus) {
		this.codCcus = codCcus;
	}

	public String getFunc() {
		return func;
	}

	public void setFunc(String func) {
		this.func = func;
	}

	public Integer getVidaUtil() {
		return vidaUtil;
	}

	public void setVidaUtil(Integer vidaUtil) {
		this.vidaUtil = vidaUtil;
	}
    
}
