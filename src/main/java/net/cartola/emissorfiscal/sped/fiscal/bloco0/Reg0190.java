package net.cartola.emissorfiscal.sped.fiscal.bloco0;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;
import coffeepot.bean.wr.types.Align;
import lombok.ToString;

/**
 * 19/08/2020
 * @author robson.costa
 * 
 * Registro 0190 - Identificação das unidades de medida
 */

@Record(fields = { 
	@Field(name = "reg", maxLength = 4), 
	@Field(name = "unid", align = Align.RIGHT, padding = '0', minLength = 6, maxLength = 6),
	@Field(name = "descr") 
})	
@ToString
public class Reg0190 {

	private String reg = "0190";
	private String unid;
	private String descr;
	
	public String getReg() {
		return reg;
	}
	
	public void setReg(String reg) {
		this.reg = reg;
	}

	public String getUnid() {
		return unid;
	}

	public void setUnid(String unid) {
		this.unid = unid;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}
	
}
