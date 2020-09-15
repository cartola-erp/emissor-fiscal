package net.cartola.emissorfiscal.sped.fiscal.bloco9;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;

/**
 * 15/09/2020
 * @author robson.costa
 */
@Record(fields = { 
		@Field(name = "reg", maxLength = 4),
	    @Field(name = "regBlc"),
	    @Field(name = "qtdRegBlc")
})
public class Reg9900 {
	
	private final String reg = "9900";
    private String regBlc;
    private Long qtdRegBlc;
	
    public String getReg() {
		return reg;
	}

	public String getRegBlc() {
		return regBlc;
	}

	public void setRegBlc(String regBlc) {
		this.regBlc = regBlc;
	}

	public Long getQtdRegBlc() {
		return qtdRegBlc;
	}

	public void setQtdRegBlc(Long qtdRegBlc) {
		this.qtdRegBlc = qtdRegBlc;
	}
	
}
