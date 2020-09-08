package net.cartola.emissorfiscal.sped.fiscal.blocoC;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;

/**
 * 02/09/2020
 * @author robson.costa
 * 
 * REGISTRO C174: OPERAÇÕES COM ARMAS DE FOGO (CÓDIGO 01)
 */
@Record(fields = { 
	@Field(name = "reg", maxLength = 4),
    @Field(name = "indArm"),
    @Field(name = "numArm"),
    @Field(name = "descrCompl")
})
public class RegC174 {
	
	private final String reg = "C174";
    private String indArm;
    private String numArm;
    private String descrCompl;
	
    public String getReg() {
		return reg;
	}

	public String getIndArm() {
		return indArm;
	}

	public void setIndArm(String indArm) {
		this.indArm = indArm;
	}

	public String getNumArm() {
		return numArm;
	}

	public void setNumArm(String numArm) {
		this.numArm = numArm;
	}

	public String getDescrCompl() {
		return descrCompl;
	}

	public void setDescrCompl(String descrCompl) {
		this.descrCompl = descrCompl;
	}
    
}
