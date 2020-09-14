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
public class RegE220 {
	
	private final String reg = "E220";
	
	/** 
	 * COLOCAR os ATRIBUTOS QUE CONSTAM na DOCUMENTAÇÃO
	 */
	
	
	// ==================== FILHOS ====================
	private List<RegE230> regE230;
	private List<RegE240> regE240;

}
