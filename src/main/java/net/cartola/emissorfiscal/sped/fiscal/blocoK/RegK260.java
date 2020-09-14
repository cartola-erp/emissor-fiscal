package net.cartola.emissorfiscal.sped.fiscal.blocoK;

import java.util.List;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;
import net.cartola.emissorfiscal.sped.fiscal.blocoH.RegH010;

/**
 * 14/09/2020
 * @author robson.costa
 */
//@Record(fields = { 
//	@Field(name = "reg", maxLength = 4),
//})
public class RegK260 {
	
	private final String reg = "K260";
	
	/** 
	 * COLOCAR os ATRIBUTOS QUE CONSTAM na DOCUMENTAÇÃO
	 */
	
	 // ==================== FILHOS ====================
    private List<RegK265> regK265;

}
