package net.cartola.emissorfiscal.sped.fiscal.blocoD;

import java.util.List;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;

/**
 * 11/09/2020
 * @author robson.costa
 */
//@Record(fields = { 
//	@Field(name = "reg", maxLength = 4),
//
//})
public class RegD350 {

	private final String reg = "D350";
	
	/** 
	 * COLOCAR os ATRIBUTOS QUE CONSTAM na DOCUMENTAÇÃO
	 */
	
    // ==================== FILHOS ====================
	private List<RegD355> regD355;
}
