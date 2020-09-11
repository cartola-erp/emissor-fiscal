package net.cartola.emissorfiscal.sped.fiscal.blocoE;

import java.time.LocalDate;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;

/**
 * 11/09/2020
 * @author robson.costa
 */
@Record(fields = { 
		@Field(name = "reg", maxLength = 4),
		@Field(name = "dtIni"),
		@Field(name = "dtFin"),
	    // ==================== FILHOS ====================
		@Field(name = "regE110")
})
public class RegE100 {
	
	private final String reg = "E100";

    private LocalDate dtIni;
    private LocalDate dtFin;
    // ==================== FILHOS ====================
    private RegE110 regE110;
	
    public String getReg() {
		return reg;
	}

	public LocalDate getDtIni() {
		return dtIni;
	}

	public void setDtIni(LocalDate dtIni) {
		this.dtIni = dtIni;
	}

	public LocalDate getDtFin() {
		return dtFin;
	}

	public void setDtFin(LocalDate dtFin) {
		this.dtFin = dtFin;
	}

	public RegE110 getRegE110() {
		return regE110;
	}

	public void setRegE110(RegE110 regE110) {
		this.regE110 = regE110;
	}
    
}
