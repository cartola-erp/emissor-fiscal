package net.cartola.emissorfiscal.sped.fiscal.bloco0;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;

/**
 * 19/08/2020
 * @author robson.costa
 * 
 * Registro 0990 - Encerramento do Bloco 0
 */

@Record(fields = {
	    @Field(name = "reg", maxLength = 4),
	    @Field(name = "qtdLin0")
//	    @Field(name = "qtdLin", classType = Long.class)
})
public class Reg0990EncerramentoDoBloco {
	
	private final String reg = "0900";
	private Long qtdLin0;
	
	public Reg0990EncerramentoDoBloco(Long qtdLinhasBlocoZero) {
		this.qtdLin0 = qtdLinhasBlocoZero;
	}

	public String getReg() {
		return reg;
	}

	public Long getQtdLin0() {
		return qtdLin0;
	}
	
}
