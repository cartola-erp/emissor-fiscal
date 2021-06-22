package net.cartola.emissorfiscal.sped.fiscal.bloco9;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;
import net.cartola.emissorfiscal.sped.fiscal.enums.IndicadorDeMovimento;

/**
 * 15/09/2020
 * @author robson.costa
 */
@Record(fields = { 
		@Field(name = "reg", maxLength = 4),
		@Field(name = "indMov")
})
public class Reg9001AberturaDoBloco9 {
	
	private final String reg = "9001";
	private IndicadorDeMovimento indMov = IndicadorDeMovimento.BLOCO_COM_DADOS_INFORMADOS;
	
	public Reg9001AberturaDoBloco9(IndicadorDeMovimento indMov) {
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
