package net.cartola.emissorfiscal.sped.fiscal.blocoE;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;

/**
 * 11/09/2020
 * @author robson.costa
 */
@Record(fields = { 
		@Field(name = "reg", maxLength = 4),
	    @Field(name = "qtdLinE")
	})
public class RegE990EncerramentoDoBlocoE {
	
	private final String reg = "E990";
	private Long qtdLinE;

	public RegE990EncerramentoDoBlocoE(Long qtdLinE) {
		this.qtdLinE = qtdLinE;
	}

	public String getReg() {
		return reg;
	}

	public Long getQtdLinE() {
		return qtdLinE;
	}
}
