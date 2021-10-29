package net.cartola.emissorfiscal.sped.fiscal.blocoE;

import java.time.LocalDate;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;
import lombok.Getter;
import lombok.Setter;

/**
 * 11/09/2020
 * @author robson.costa
 */
@Record(fields = { 
		@Field(name = "reg", maxLength = 4),
		@Field(name = "uf"),
		@Field(name = "dtIni"),
		@Field(name = "dtFin"),
	    // ==================== FILHOS ====================
		@Field(name = "regE210")

})
@Setter
@Getter
public class RegE200 {
	
	private final String reg = "E200";
	private String uf;
    private LocalDate dtIni;
    private LocalDate dtFin;	
	// ==================== FILHOS ====================
	private RegE210 regE210;
	
}
