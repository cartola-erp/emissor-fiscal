package net.cartola.emissorfiscal.sped.fiscal.blocoC;

import java.math.BigDecimal;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;

/**
 * 02/09/2020
 * @author robson.costa
 * 
 * REGISTRO C390: REGISTRO ANALÍTICO DAS NOTAS FISCAIS DE VENDA A CONSUMIDOR (CÓDIGO 02)
 */
@Record(fields = { 
	@Field(name = "reg", maxLength = 4),
	@Field(name = "cstIcms"),
	@Field(name = "cfop"),
	@Field(name = "aliqIcms"),
	@Field(name = "vlOpr"),
	@Field(name = "vlBcIcms"),
	@Field(name = "vlIcms"),
	@Field(name = "vlRedBc"),
	@Field(name = "codObs")
})
public class RegC390 {

	private final String reg = "C390";
    private int cstIcms;
    private int cfop;
    private BigDecimal aliqIcms;
    private BigDecimal vlOpr;
    private BigDecimal vlBcIcms;
    private BigDecimal vlIcms;
    private BigDecimal vlRedBc;
    private String codObs;
	
    public String getReg() {
		return reg;
	}

	public int getCstIcms() {
		return cstIcms;
	}

	public void setCstIcms(int cstIcms) {
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

	public BigDecimal getVlRedBc() {
		return vlRedBc;
	}

	public void setVlRedBc(BigDecimal vlRedBc) {
		this.vlRedBc = vlRedBc;
	}

	public String getCodObs() {
		return codObs;
	}

	public void setCodObs(String codObs) {
		this.codObs = codObs;
	}
    
}
