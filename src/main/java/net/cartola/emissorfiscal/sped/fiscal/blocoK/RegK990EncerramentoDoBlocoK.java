package net.cartola.emissorfiscal.sped.fiscal.blocoK;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;

/**
 * 14/09/2020
 * @author robson.costa
 */
@Record(fields = { 
	@Field(name = "reg", maxLength = 4),
	@Field(name = "qtdLinK")
})
public class RegK990EncerramentoDoBlocoK {
	
	private final String reg = "K990";
	private Long qtdLinK;

	public RegK990EncerramentoDoBlocoK(Long qtdLinK) {
		this.qtdLinK = qtdLinK;
	}

	public String getReg() {
		return reg;
	}

	public Long getQtdLinK() {
		return qtdLinK;
	}
}
