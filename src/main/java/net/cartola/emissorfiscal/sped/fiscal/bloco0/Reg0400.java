package net.cartola.emissorfiscal.sped.fiscal.bloco0;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;

/**
 * 19/08/2020
 * @author robson.costa
 * 
 * Registro 0400 - Tabela de Natureza da Operação/ Prestação
 */
@Record(fields = {
	@Field(name = "reg"),
	@Field(name = "codNat"),
	@Field(name = "descrNat", maxLength = 50)
})
public class Reg0400 {
	
	private final String reg = "0400";
	private String codNat;
	private String descrNat;
	
	public String getReg() {
		return reg;
	}

	public String getCodNat() {
		return codNat;
	}

	public void setCodNat(String codNat) {
		this.codNat = codNat;
	}

	public String getDescrNat() {
		return descrNat;
	}

	public void setDescrNat(String descrNat) {
		this.descrNat = descrNat;
	}
}
