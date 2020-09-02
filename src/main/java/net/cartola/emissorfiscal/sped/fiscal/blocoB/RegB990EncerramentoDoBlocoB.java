package net.cartola.emissorfiscal.sped.fiscal.blocoB;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;

/**
 * 31/08/2020
 * @author robson.costa
 * 
 * Registro B990: ENCERRAMENTO DO BLOCO B
 */
@Record(fields = { 
		@Field(name = "reg", maxLength = 4),
	    @Field(name = "qtdLinB")
})
public class RegB990EncerramentoDoBlocoB {
	
	private final String reg = "B990";
	private Long qtdLin0;

	public RegB990EncerramentoDoBlocoB() {
		this.qtdLin0 = 2L;
	}

	public String getReg() {
		return reg;
	}

	public Long getQtdLin0() {
		return qtdLin0;
	}

	
}
