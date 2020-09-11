package net.cartola.emissorfiscal.sped.fiscal.blocoD;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;

/**
 * 11/09/2020
 * @author robson.costa
 */
@Record(fields = { 
	@Field(name = "reg", maxLength = 4),
    @Field(name = "qtdLinD")
})
public class RegD990EncerramentoDoBlocoD {
	
	private final String reg = "D990";
	private Long qtdLinD;

	public RegD990EncerramentoDoBlocoD(Long qtdLinD) {
		this.qtdLinD = qtdLinD;
	}

	public String getReg() {
		return reg;
	}

	public Long getQtdLinD() {
		return qtdLinD;
	}
	
}
