package net.cartola.emissorfiscal.sped.fiscal.bloco9;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;

/**
 * 15/09/2020
 * @author robson.costa
 */
@Record(fields = { 
		@Field(name = "reg", maxLength = 4),
	    @Field(name = "qtdLin", classType = Long.class)
})
public class Reg9999EncerramentoDoArquivoDigital {
	
	private final String reg = "9999";
	private Long qtdLin;

    public Reg9999EncerramentoDoArquivoDigital(Long qtdLin) {
        this.qtdLin = qtdLin;
    }

	public String getReg() {
		return reg;
	}

	public Long getQtdLin() {
		return qtdLin;
	}
    
}
