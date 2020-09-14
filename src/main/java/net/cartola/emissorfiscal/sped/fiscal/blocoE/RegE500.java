package net.cartola.emissorfiscal.sped.fiscal.blocoE;

import java.time.LocalDate;
import java.util.List;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;
import net.cartola.emissorfiscal.sped.fiscal.enums.ApuracaoIpi;

/**
 * 11/09/2020
 * @author robson.costa
 */
//@Record(fields = { 
//	@Field(name = "reg", maxLength = 4),
//
//})
public class RegE500 {
	
	private final String reg = "E500";
	private ApuracaoIpi indApur;
	private LocalDate dtIni;
	private LocalDate dtFin;
	
	// ==================== FILHOS ====================
	private List<RegE510> regE510;
	private RegE520 regE520;
	
	
}
