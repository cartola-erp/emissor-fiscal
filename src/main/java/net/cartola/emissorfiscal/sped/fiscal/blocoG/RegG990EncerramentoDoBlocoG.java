package net.cartola.emissorfiscal.sped.fiscal.blocoG;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;

/**
 * 14/09/2020
 * @author robson.costa
 */
@Record(fields = { 
	@Field(name = "reg", maxLength = 4),
	@Field(name = "qtdLinG")
})
public class RegG990EncerramentoDoBlocoG {
	
	private final String reg = "G990";
	private Long qtdLinG;

	public RegG990EncerramentoDoBlocoG(Long qtdLinG) {
		this.qtdLinG = qtdLinG;
	}

	public String getReg() {
		return reg;
	}

	public Long getQtdLinG() {
		return qtdLinG;
	}
}
