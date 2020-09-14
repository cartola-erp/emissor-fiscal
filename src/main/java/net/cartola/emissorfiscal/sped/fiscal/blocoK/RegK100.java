package net.cartola.emissorfiscal.sped.fiscal.blocoK;

import java.time.LocalDate;
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
public class RegK100 {
	
	private final String reg = "K100";

	/** 
	 * COLOCAR os ATRIBUTOS QUE CONSTAM na DOCUMENTAÇÃO
	 */
	
	 // ==================== FILHOS ====================
    private List<RegK200> regK200;
    private List<RegK210> regK210;
    private List<RegK220> regK220;
    private List<RegK230> regK230;
    private List<RegK250> regK250;
    private List<RegK260> regK260;
    private List<RegK270> regK270;
    private List<RegK280> regK280;
    private List<RegK290> regK290;
    private List<RegK300> regK300;
}
