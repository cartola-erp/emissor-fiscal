package net.cartola.emissorfiscal.sped.fiscal.blocoC;

import java.math.BigDecimal;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;

/**
 * 02/09/2020
 * @author robson.costa
 */
@Record(fields = { 
	@Field(name = "reg", maxLength = 4),
	@Field(name = "numItem"),
	@Field(name = "codItem"),
	@Field(name = "qtd"),
	@Field(name = "unid"),
	@Field(name = "vlItem"),
	@Field(name = "cstIcms"),
	@Field(name = "cfop"),
    // ==================== FILHO ====================
	@Field(name = "regC815")
})
public class RegC810 {
	
	private final String reg = "C810";
	private int numItem;
	private String codItem;
	private Double qtd;
	private String unid;
	private BigDecimal vlItem;
	private int cstIcms;
	private int cfop;
    // ==================== FILHO ====================
	private RegC815 regC815;
	
	public String getReg() {
		return reg;
	}

	public int getNumItem() {
		return numItem;
	}

	public void setNumItem(int numItem) {
		this.numItem = numItem;
	}

	public String getCodItem() {
		return codItem;
	}

	public void setCodItem(String codItem) {
		this.codItem = codItem;
	}

	public Double getQtd() {
		return qtd;
	}

	public void setQtd(Double qtd) {
		this.qtd = qtd;
	}

	public String getUnid() {
		return unid;
	}

	public void setUnid(String unid) {
		this.unid = unid;
	}

	public BigDecimal getVlItem() {
		return vlItem;
	}

	public void setVlItem(BigDecimal vlItem) {
		this.vlItem = vlItem;
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

	public RegC815 getRegC815() {
		return regC815;
	}

	public void setRegC815(RegC815 regC815) {
		this.regC815 = regC815;
	}

}
