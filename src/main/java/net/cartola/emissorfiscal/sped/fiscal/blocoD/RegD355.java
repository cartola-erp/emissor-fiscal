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
public class RegD355 {
	
	private final String reg = "D355";

	
	/** 
	 * COLOCAR os ATRIBUTOS QUE CONSTAM na DOCUMENTAÇÃO
	 */
	
    // ==================== FILHOS ====================
	private RegD360 regD360;
	private List<RegD365> regD365;
	private List<RegD390> regD390;
	
}
