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
public class RegE210 {
	
	private final String reg = "E210";
	
	/** 
	 * COLOCAR os ATRIBUTOS QUE CONSTAM na DOCUMENTAÇÃO
	 */
	
	
	// ==================== FILHOS ====================
	private List<RegE220> regE220;
	private List<RegE240> regE240;
	
}
