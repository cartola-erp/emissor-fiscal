package net.cartola.emissorfiscal.sped.fiscal.blocoC;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;
import coffeepot.bean.wr.typeHandler.DefaultStringHandler;

/**
 * 02/09/2020
 * @author robson.costa
 */
@Record(fields = { 
	@Field(name = "reg", maxLength = 4),
	@Field(name = "chvCfe", params = {DefaultStringHandler.PARAM_FILTER_NUMBER_LETTERS_ONLY}),
    @Field(name = "numCcf")
})
public class RegC465 {
	
	private final String reg = "C465";
    private String chvCfe;
    private Long numCcf;
	
    public String getReg() {
		return reg;
	}

	public String getChvCfe() {
		return chvCfe;
	}

	public void setChvCfe(String chvCfe) {
		this.chvCfe = chvCfe;
	}

	public Long getNumCcf() {
		return numCcf;
	}

	public void setNumCcf(Long numCcf) {
		this.numCcf = numCcf;
	}
    
}
