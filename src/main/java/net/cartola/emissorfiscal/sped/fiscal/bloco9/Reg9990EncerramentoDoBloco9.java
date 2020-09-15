package net.cartola.emissorfiscal.sped.fiscal.bloco9;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;

/**
 * 15/09/2020
 * @author robson.costa
 */
@Record(fields = { 
		@Field(name = "reg", maxLength = 4),
		@Field(name = "qtdLin9")
})
public class Reg9990EncerramentoDoBloco9 {
		
	private final String reg = "9990";
	private Long qtdLin9;

	public Reg9990EncerramentoDoBloco9(Long qtdLin9) {
		this.qtdLin9 = qtdLin9;
	}

	public String getReg() {
		return reg;
	}

	public Long getQtdLin9() {
		return qtdLin9;
	}
}
