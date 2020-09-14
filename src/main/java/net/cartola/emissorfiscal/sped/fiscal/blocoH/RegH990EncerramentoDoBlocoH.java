package net.cartola.emissorfiscal.sped.fiscal.blocoH;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;

/**
 * 14/09/2020
 * @author robson.costa
 */
@Record(fields = { 
		@Field(name = "reg", maxLength = 4),
		@Field(name = "qtdLinH")
})
public class RegH990EncerramentoDoBlocoH {
	
	private final String reg = "H990";
	private Long qtdLinH;

	public RegH990EncerramentoDoBlocoH(Long qtdLinH) {
		this.qtdLinH = qtdLinH;
	}

	public String getReg() {
		return reg;
	}

	public Long getQtdLinH() {
		return qtdLinH;
	}
}
