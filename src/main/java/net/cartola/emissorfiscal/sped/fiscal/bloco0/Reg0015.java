package net.cartola.emissorfiscal.sped.fiscal.bloco0;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;

/**
 * 19/08/2020
 * @author robson.costa
 * 
 * Registro 0015 - Dados do Contribuinte Substituto ou Responsável pelo ICMS Destino
 */
@Record(fields = {
		@Field(name = "reg", maxLength = 4),	    
	    @Field(name = "ufSt", maxLength = 2),
	    @Field(name = "ieSt")
})
public class Reg0015 {
	
	private final String reg = "0015";
    private String ufSt;
    private String ieSt;
	
    public String getReg() {
		return reg;
	}
	
    public String getUfSt() {
		return ufSt;
	}
	
    public void setUfSt(String ufSt) {
		this.ufSt = ufSt;
	}
	
    public String getIeSt() {
		return ieSt;
	}
	
    public void setIeSt(String ieSt) {
		this.ieSt = ieSt;
	}
    
}
