package net.cartola.emissorfiscal.sped.fiscal.bloco0;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;
import net.cartola.emissorfiscal.sped.fiscal.enums.IndicadorDeMovimento;

/**
 * 19/08/2020
 * @author robson.costa
 * 
 * Registro 0001 - Abertura do Bloco 0
 */
@Record(fields = {
		@Field(name = "reg", maxLength = 4),	    
		@Field(name = "indMov")
})
public class Reg0001AberturaDoBloco {
	
	private final String reg = "0001";
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
