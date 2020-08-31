package net.cartola.emissorfiscal.sped.fiscal.bloco0;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;

/**
 * 19/08/2020
 * @author robson.costa
 * 
 * Registro 0460 - Tabela de Observações do Lançamento Fiscal
 */
@Record(fields = {
	    @Field(name = "reg", maxLength = 4),
	    @Field(name = "codObs"),
	    @Field(name = "txt")
})
public class Reg0460 {
	
	private final String reg = "0460";
    private String codObs;
    private String txt;
	
    public String getReg() {
		return reg;
	}

	public String getCodObs() {
		return codObs;
	}

	public void setCodObs(String codObs) {
		this.codObs = codObs;
	}

	public String getTxt() {
		return txt;
	}

	public void setTxt(String txt) {
		this.txt = txt;
	}
    
}
