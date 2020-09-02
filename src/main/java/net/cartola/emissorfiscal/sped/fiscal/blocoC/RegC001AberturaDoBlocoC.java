package net.cartola.emissorfiscal.sped.fiscal.blocoC;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;
import net.cartola.emissorfiscal.sped.fiscal.enums.IndicadorDeMovimento;

/**
 * 02/09/2020
 * @author robson.costa
 * 
 * REGISTRO C001: ABERTURA DO BLOCO C
 */
@Record(fields = { 
	@Field(name = "reg", maxLength = 4),
	@Field(name = "indMov", maxLength = 4)
})
public class RegC001AberturaDoBlocoC {
	
	private final String reg = "C001";
	private IndicadorDeMovimento indMov;
	
	public RegC001AberturaDoBlocoC(IndicadorDeMovimento indMov) {
		this.indMov = indMov;
	}

	public String getReg() {
		return reg;
	}

	public IndicadorDeMovimento getIndMov() {
		return indMov;
	}
	
}
