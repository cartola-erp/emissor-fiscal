package net.cartola.emissorfiscal.sped.fiscal.blocoB;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;
import net.cartola.emissorfiscal.sped.fiscal.enums.IndicadorDeMovimento;

/**
 * 31/08/2020
 * @author robson.costa
 * 
 * Registro B001 - Abertura do Bloco B
 */
@Record(fields = { 
		@Field(name = "reg", maxLength = 4), 
		@Field(name = "indDad")
})
public class RegB001AberturaDoBloco {
	
	private final String reg = "B001";
	private IndicadorDeMovimento indDad;
	
	public String getReg() {
		return reg;
	}

	public IndicadorDeMovimento getIndDad() {
		return indDad;
	}

	public void setIndDad(IndicadorDeMovimento indDad) {
		this.indDad = indDad;
	}
	
	
}
