package net.cartola.emissorfiscal.sped.fiscal.bloco1;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;

/**
 * 14/09/2020
 * @author robson.costa
 */
@Record(fields = { 
	@Field(name = "reg", maxLength = 4),
	@Field(name = "qtdLin1")
})
public class Reg1990EncerramentoDoBloco1 {
	
	private final String reg = "1990";
	private Long qtdLin1;

	public Reg1990EncerramentoDoBloco1(Long qtdLin1) {
		this.qtdLin1 = qtdLin1;
	}

	public String getReg() {
		return reg;
	}

	public Long getQtdLin1() {
		return qtdLin1;
	}
}
