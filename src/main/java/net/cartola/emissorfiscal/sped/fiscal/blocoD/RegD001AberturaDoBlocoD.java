package net.cartola.emissorfiscal.sped.fiscal.blocoD;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;
import net.cartola.emissorfiscal.sped.fiscal.enums.IndicadorDeMovimento;

/**
 * 11/09/2020
 * @author robson.costa
 */
@Record(fields = { 
	@Field(name = "reg", maxLength = 4),
	@Field(name = "indMov")
})
public class RegD001AberturaDoBlocoD {

	private final String reg = "D001";
	private IndicadorDeMovimento indMov = IndicadorDeMovimento.BLOCO_COM_DADOS_INFORMADOS;
	
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
