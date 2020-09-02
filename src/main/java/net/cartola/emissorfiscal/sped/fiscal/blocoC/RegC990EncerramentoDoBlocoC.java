package net.cartola.emissorfiscal.sped.fiscal.blocoC;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;

/**
 * 02/09/2020
 * @author robson.costa
 * 
 * REGISTRO C990: ENCERRAMENTO DO BLOCO C
 * 
 */
@Record(fields = { 
	@Field(name = "reg", maxLength = 4),
    @Field(name = "qtdLinC")
})
public class RegC990EncerramentoDoBlocoC {
	
	private final String reg = "C990";
	private Long qtdLinC;

	public RegC990EncerramentoDoBlocoC(Long qtdLinhasBlocoC) {
		this.qtdLinC = qtdLinhasBlocoC;
	}

	public String getReg() {
		return reg;
	}

	public Long getQtdLinC() {
		return qtdLinC;
	}
	
}
