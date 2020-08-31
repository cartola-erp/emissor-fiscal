package net.cartola.emissorfiscal.sped.fiscal.bloco0;

import java.time.LocalDate;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;

/**
 * 19/08/2020
 * @author robson.costa
 * 
 * Registro 0600 - Centro de custos
 */

@Record(fields = {
	    @Field(name = "reg", maxLength = 4),
	    @Field(name = "dtAlt"),
	    @Field(name = "codCcus"),
	    @Field(name = "ccus")
})
public class Reg0600 {

	private final String reg = "0600";
    private LocalDate dtAlt;
    private String codCcus;
    private String ccus;
	
    public String getReg() {
		return reg;
	}

	public LocalDate getDtAlt() {
		return dtAlt;
	}

	public void setDtAlt(LocalDate dtAlt) {
		this.dtAlt = dtAlt;
	}

	public String getCodCcus() {
		return codCcus;
	}

	public void setCodCcus(String codCcus) {
		this.codCcus = codCcus;
	}

	public String getCcus() {
		return ccus;
	}

	public void setCcus(String ccus) {
		this.ccus = ccus;
	}
    
}
