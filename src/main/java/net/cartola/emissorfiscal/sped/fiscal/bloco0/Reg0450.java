package net.cartola.emissorfiscal.sped.fiscal.bloco0;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;

/**
 * 19/08/2020
 * @author robson.costa
 * 
 * Registro 0450 - Tabela de Informação Complementar do documento fiscal
 */
@Record(fields = {
	    @Field(name = "reg", maxLength = 4),
	    @Field(name = "codInf"),
	    @Field(name = "txt")
})
public class Reg0450 {
	
	private final String reg = "0450";
	private String codInf;
	private String txt;
	
	public String getCodInf() {
		return codInf;
	}
	
	public void setCodInf(String codInf) {
		this.codInf = codInf;
	}
	
	public String getTxt() {
		return txt;
	}
	
	public void setTxt(String txt) {
		this.txt = txt;
	}

	public String getReg() {
		return reg;
	}
	
}
