package net.cartola.emissorfiscal.sped.fiscal.blocoG;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;
import net.cartola.emissorfiscal.sped.fiscal.enums.IndicadorDeMovimento;

/**
 * 14/09/2020
 * @author robson.costa
 */
@Record(fields = { 
		@Field(name = "reg", maxLength = 4),
		@Field(name = "indMov")
})
public class RegG001AberturaDoBlocoG {
	
	private final String reg = "G001";
	private IndicadorDeMovimento indMov = IndicadorDeMovimento.BLOCO_SEM_DADOS_INFORMADOS;

	public RegG001AberturaDoBlocoG(IndicadorDeMovimento indMov) {
		this.indMov = indMov;
	}
	
	public String getReg() {
		return reg;
	}
	
	public IndicadorDeMovimento getIndMov() {
		return indMov;
	}
	
	public void setIndMov(IndicadorDeMovimento indMov) {
		this.indMov = indMov;
	}
	
}
