package net.cartola.emissorfiscal.sped.fiscal.bloco0;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;

/**
 * 19/08/2020
 * @author robson.costa
 * 
 * Registro 0220 - Fatores de Conversão de Unidades
 */
@Record(fields = { 
	@Field(name = "reg"),
	@Field(name = "unidConv"),
	@Field(name = "fatConv")
})
public class Reg0220 {

	private final String reg = "0220";
	private String unidConv;
	private Double fatConv;
	
	public String getReg() {
		return reg;
	}

	public String getUnidConv() {
		return unidConv;
	}

	public void setUnidConv(String unidConv) {
		this.unidConv = unidConv;
	}

	public Double getFatConv() {
		return fatConv;
	}

	public void setFatConv(Double fatConv) {
		this.fatConv = fatConv;
	}
}
