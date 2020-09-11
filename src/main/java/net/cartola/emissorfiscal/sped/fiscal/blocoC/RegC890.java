package net.cartola.emissorfiscal.sped.fiscal.blocoC;

import java.math.BigDecimal;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;

/**
 * 02/09/2020
 * @author robson.costa
 */
//@Record(fields = { 
//	@Field(name = "reg", maxLength = 4),
//	@Field(name = "cstIcms"),
//	@Field(name = "cfop"),
//	@Field(name = "aliqIcms"),
//	@Field(name = "vlOpr"),
//	@Field(name = "vlBcIcms"),
//	@Field(name = "vlIcms"),
//	@Field(name = "codObs")
//})
public class RegC890 {
	
	private final String reg = "C890";
	private int cstIcms;
    private int cfop;
    private BigDecimal aliqIcms;
    private BigDecimal vlOpr;
    private BigDecimal vlBcIcms;
    private BigDecimal vlIcms;
    private String codObs;
    
    
}
