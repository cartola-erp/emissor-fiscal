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
    @Field(name = "codItem"),
    @Field(name = "qtd"),
    @Field(name = "qtdCanc"),
    @Field(name = "unid"),
    @Field(name = "vlItem"),
    @Field(name = "cstIcms"),
    @Field(name = "cfop"),
    @Field(name = "aliqIcms"),
    @Field(name = "vlPis"),
    @Field(name = "vlCofins")
})
public class RegC470 {
	
	private final String reg = "C470";
    private String codItem;
    private Double qtd;
    private Double qtdCanc;
    private String unid;
    private BigDecimal vlItem;
    private int cstIcms;
    private int cfop;
    private BigDecimal aliqIcms;
    private BigDecimal vlPis;
    private BigDecimal vlCofins;
	
    public String getReg() {
		return reg;
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

	public Double getQtdCanc() {
		return qtdCanc;
	}

	public void setQtdCanc(Double qtdCanc) {
		this.qtdCanc = qtdCanc;
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

	public BigDecimal getAliqIcms() {
		return aliqIcms;
	}

	public void setAliqIcms(BigDecimal aliqIcms) {
		this.aliqIcms = aliqIcms;
	}

	public BigDecimal getVlPis() {
		return vlPis;
	}

	public void setVlPis(BigDecimal vlPis) {
		this.vlPis = vlPis;
	}

	public BigDecimal getVlCofins() {
		return vlCofins;
	}

	public void setVlCofins(BigDecimal vlCofins) {
		this.vlCofins = vlCofins;
	}
    
}
