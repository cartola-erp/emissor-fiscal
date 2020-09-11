package net.cartola.emissorfiscal.sped.fiscal.blocoD;

import java.math.BigDecimal;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;

/**
 * 11/09/2020
 * @author robson.costa
 * 
 * REGISTRO D590: REGISTRO ANALÍTICO DO DOCUMENTO (CÓDIGO 21 E 22)
 */
@Record(fields = { 
	@Field(name = "reg", maxLength = 4),
	@Field(name = "cstIcms"),
	@Field(name = "cfop"),
	@Field(name = "aliqIcms"),
	@Field(name = "vlOpr"),
	@Field(name = "vlBcIcms"),
	@Field(name = "vlIcms"),
	@Field(name = "vlBcIcmsUf"),
	@Field(name = "vlIcmsUf"),
	@Field(name = "vlRedBc"),
	@Field(name = "codObs")
})
public class RegD590 {
	
	private final String reg = "D590";
	private int cstIcms;
	private int cfop;
	private BigDecimal aliqIcms;
	private BigDecimal vlOpr;
	private BigDecimal vlBcIcms;
	private BigDecimal vlIcms;
	private BigDecimal vlBcIcmsUf;
	private BigDecimal vlIcmsUf;
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

	public BigDecimal getVlBcIcmsUf() {
		return vlBcIcmsUf;
	}

	public void setVlBcIcmsUf(BigDecimal vlBcIcmsUf) {
		this.vlBcIcmsUf = vlBcIcmsUf;
	}

	public BigDecimal getVlIcmsUf() {
		return vlIcmsUf;
	}

	public void setVlIcmsUf(BigDecimal vlIcmsUf) {
		this.vlIcmsUf = vlIcmsUf;
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
