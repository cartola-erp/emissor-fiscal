package net.cartola.emissorfiscal.sped.fiscal.blocoE;

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
public class RegE200 {
	
	private final String reg = "E200";
	
	/** 
	 * COLOCAR os ATRIBUTOS QUE CONSTAM na DOCUMENTAÇÃO
	 */
	
	
	// ==================== FILHOS ====================
	private RegE210 regE210;
	
}
