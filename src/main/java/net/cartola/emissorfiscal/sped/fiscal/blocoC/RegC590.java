package net.cartola.emissorfiscal.sped.fiscal.blocoC;

import java.math.BigDecimal;
import java.util.List;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;

/**
 * 02/09/2020
 * @author robson.costa
 * 
 * REGISTRO C590: REGISTRO ANALÍTICO DO DOCUMENTO – NOTA FISCAL/CONTA DE ENERGIA
 * ELÉTRICA (CÓDIGO 06), NOTA FISCAL DE ENERGIA ELÉTRICA ELETRÔNICA – NF3e (CÓDIGO 66), NOTA
 * FISCAL/CONTA DE FORNECIMENTO D'ÁGUA CANALIZADA (CÓDIGO 29) E NOTA FISCAL CONSUMO
 * FORNECIMENTO DE GÁS (CÓDIGO 28).
 */
@Record(fields = { 
	@Field(name = "reg", maxLength = 4),
	@Field(name = "cstIcms"),
	@Field(name = "cfop"),
	@Field(name = "aliqIcms"),
	@Field(name = "vlOpr"),
	@Field(name = "vlBcIcms"),
	@Field(name = "vlIcms"),
	@Field(name = "vlBcIcmsSt"),
	@Field(name = "vlIcmsSt"),
	@Field(name = "vlRedBc"),
	@Field(name = "codObs"),
	// ==================== FILHO ====================
	@Field(name = "regC591"),
	@Field(name = "regC597")
})
public class RegC590 {
	
	private final String reg = "C590";
	private int cstIcms;
	private int cfop;
	private BigDecimal aliqIcms;
	private BigDecimal vlOpr;
	private BigDecimal vlBcIcms;
	private BigDecimal vlIcms;
	private BigDecimal vlBcIcmsSt;
	private BigDecimal vlIcmsSt;
	private BigDecimal vlRedBc;
	private String codObs;
	// ==================== FILHO ====================
	private RegC591 regC591;
	private List<RegC597> regC597;
	
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

	public String getCodObs() {
		return codObs;
	}

	public void setCodObs(String codObs) {
		this.codObs = codObs;
	}
	
}
