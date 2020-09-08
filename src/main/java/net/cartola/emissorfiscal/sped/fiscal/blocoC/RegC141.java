package net.cartola.emissorfiscal.sped.fiscal.blocoC;

import java.time.LocalDate;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;

/**
 * 02/09/2020
 * @author robson.costa
 */
@Record(fields = { 
	@Field(name = "reg", maxLength = 4),
	@Field(name = "numParc"),
	@Field(name = "dtVcto"),
	@Field(name = "vlParc")
})
public class RegC141 {
	
	private final String reg = "C141";
    private Integer numParc;
    private LocalDate dtVcto;
    private Double vlParc;
	
    public String getReg() {
		return reg;
	}

	public Integer getNumParc() {
		return numParc;
	}

	public void setNumParc(Integer numParc) {
		this.numParc = numParc;
	}

	public LocalDate getDtVcto() {
		return dtVcto;
	}

	public void setDtVcto(LocalDate dtVcto) {
		this.dtVcto = dtVcto;
	}

	public Double getVlParc() {
		return vlParc;
	}

	public void setVlParc(Double vlParc) {
		this.vlParc = vlParc;
	}
    
}
