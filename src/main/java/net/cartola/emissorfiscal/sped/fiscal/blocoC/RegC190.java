package net.cartola.emissorfiscal.sped.fiscal.blocoC;

import java.math.BigDecimal;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;
import coffeepot.bean.wr.types.Align;

/**
 * 02/09/2020
 * @author robson.costa
 * 
 * REGISTRO C190: REGISTRO ANALÍTICO DO DOCUMENTO (CÓDIGO 01, 1B, 04, 55 e 65)
 */
@Record(fields = { 
	@Field(name = "reg", maxLength = 4),
    @Field(name = "cstIcms", length = 3, align = Align.RIGHT, padding = '0'),
    @Field(name = "cfop"),
    @Field(name = "aliqIcms"),
    @Field(name = "vlOpr"),
    @Field(name = "vlBcIcms"),
    @Field(name = "vlIcms"),
    @Field(name = "vlBcIcmsSt"),
    @Field(name = "vlIcmsSt"),
    @Field(name = "vlRedBc"),
    @Field(name = "vlIpi"),
    @Field(name = "codObs")
})
public class RegC190 {
	
	private final String reg = "C190";
    private String cstIcms;
    private int cfop;
    private BigDecimal aliqIcms;
    private BigDecimal vlOpr;
    private BigDecimal vlBcIcms;
    private BigDecimal vlIcms;
    private BigDecimal vlBcIcmsSt;
    private BigDecimal vlIcmsSt;
    private BigDecimal vlRedBc;
    private BigDecimal vlIpi;
    private String codObs;
	
    public String getReg() {
		return reg;
	}

	public String getCstIcms() {
		return cstIcms;
	}

	public void setCstIcms(String cstIcms) {
		this.cstIcms = cstIcms;
	}

	public int getCfop() {
		return cfop;
	}

	public void setCfop(int cfop) {
		this.cfop = cfop;
	}

	public BigDecimal getAliqIcms() {
		return aliqIcms;
	}

	public void setAliqIcms(BigDecimal aliqIcms) {
		this.aliqIcms = aliqIcms;
	}

	public BigDecimal getVlOpr() {
		return vlOpr;
	}

	public void setVlOpr(BigDecimal vlOpr) {
		this.vlOpr = vlOpr;
	}

	public BigDecimal getVlBcIcms() {
		return vlBcIcms;
	}

	public void setVlBcIcms(BigDecimal vlBcIcms) {
		this.vlBcIcms = vlBcIcms;
	}

	public BigDecimal getVlIcms() {
		return vlIcms;
	}

	public void setVlIcms(BigDecimal vlIcms) {
		this.vlIcms = vlIcms;
	}

	public BigDecimal getVlBcIcmsSt() {
		return vlBcIcmsSt;
	}

	public void setVlBcIcmsSt(BigDecimal vlBcIcmsSt) {
		this.vlBcIcmsSt = vlBcIcmsSt;
	}

	public BigDecimal getVlIcmsSt() {
		return vlIcmsSt;
	}

	public void setVlIcmsSt(BigDecimal vlIcmsSt) {
		this.vlIcmsSt = vlIcmsSt;
	}

	public BigDecimal getVlRedBc() {
		return vlRedBc;
	}

	public void setVlRedBc(BigDecimal vlRedBc) {
		this.vlRedBc = vlRedBc;
	}

	public BigDecimal getVlIpi() {
		return vlIpi;
	}

	public void setVlIpi(BigDecimal vlIpi) {
		this.vlIpi = vlIpi;
	}

	public String getCodObs() {
		return codObs;
	}

	public void setCodObs(String codObs) {
		this.codObs = codObs;
	}
    
}
