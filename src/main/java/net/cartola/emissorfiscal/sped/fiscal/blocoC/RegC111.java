package net.cartola.emissorfiscal.sped.fiscal.blocoC;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;
import net.cartola.emissorfiscal.sped.fiscal.enums.OrigemDoProcesso;

/**
 * 02/09/2020
 * @author robson.costa
 */
@Record(fields = { 
	@Field(name = "reg", maxLength = 4),
    @Field(name = "numProc"),
    @Field(name = "indProc")
})
public class RegC111 {

	private final String reg = "C111";
	private String numProc;
	private OrigemDoProcesso indProc;
	
	public String getReg() {
		return reg;
	}

	public String getNumProc() {
		return numProc;
	}

	public void setNumProc(String numProc) {
		this.numProc = numProc;
	}

	public OrigemDoProcesso getIndProc() {
		return indProc;
	}

	public void setIndProc(OrigemDoProcesso indProc) {
		this.indProc = indProc;
	}
	
}
