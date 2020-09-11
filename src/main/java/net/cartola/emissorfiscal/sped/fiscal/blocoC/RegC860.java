package net.cartola.emissorfiscal.sped.fiscal.blocoC;

import java.time.LocalDate;
import java.util.List;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;
import net.cartola.emissorfiscal.sped.fiscal.enums.ModeloDocumentoFiscal;

/**
 * 02/09/2020
 * @author robson.costa
 */
//@Record(fields = { 
//	@Field(name = "reg", maxLength = 4),
//	@Field(name = "codMod"),
//	@Field(name = "nrSat"),
//	@Field(name = "dtDoc"),
//	@Field(name = "docIni"),
//	@Field(name = "docFim"),
//    // ==================== FILHO ====================
//	@Field(name = "regC870"),
//	@Field(name = "regC890")
//})
public class RegC860 {
	
	private final String reg = "C860";
    private ModeloDocumentoFiscal codMod;
    private String nrSat;
    private LocalDate dtDoc;
    private Long docIni;
    private Long docFim;
    // ==================== FILHO ====================
    private List<RegC870> regC870;
    private List<RegC890> regC890;
    
}
