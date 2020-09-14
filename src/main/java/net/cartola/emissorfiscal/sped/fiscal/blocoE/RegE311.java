package net.cartola.emissorfiscal.sped.fiscal.blocoE;

import java.util.List;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;

/**
 * 11/09/2020
 * @author robson.costa
 */
//@Record(fields = { 
//		@Field(name = "reg", maxLength = 4),
//
//})
public class RegE311 {
	
	private final String reg = "E311";

	
	// ==================== FILHOS ====================
	private List<RegE312> regE312;
	private List<RegE313> regE313;

	
}
