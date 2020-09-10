package net.cartola.emissorfiscal.sped.fiscal.blocoC;

import java.math.BigDecimal;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;
import net.cartola.emissorfiscal.sped.fiscal.enums.IndicadorDeReceita;

/**
 * 02/09/2020
 * @author robson.costa
 * 
 * REGISTRO C510: ITENS DO DOCUMENTO NOTA FISCAL/CONTA ENERGIA ELÉTRICA (CÓDIGO 06), NOTA
 * FISCAL/CONTA DE FORNECIMENTO DE ÁGUA CANALIZADA (CÓDIGO 29) E NOTA FISCAL/CONTA DE
 * FORNECIMENTO DE GÁS (CÓDIGO 28)
 *
 */
@Record(fields = { 
	@Field(name = "reg", maxLength = 4),
	@Field(name = "numItem"),
	@Field(name = "codItem"),
	@Field(name = "codClass"),
	@Field(name = "qtd"),
	@Field(name = "unid"),
	@Field(name = "vlitem"),
	@Field(name = "vlDesc"),
	@Field(name = "cstIcms"),
	@Field(name = "cfop"),
	@Field(name = "vlBcIcms"),
	@Field(name = "aliqIcms"),
	@Field(name = "vlIcms"),
	@Field(name = "vlBcIcmsSt"),
	@Field(name = "aliqSt"),
	@Field(name = "vlIcmsSt"),
	@Field(name = "indRec"),
	@Field(name = "codPart"),
	@Field(name = "vlPis"),
	@Field(name = "vlCofins"),
	@Field(name = "codCta")
})
public class RegC510 {
	
	private final String reg = "C510";
	private int numItem;
	private String codItem;
	private String codClass;
	private Double qtd;
	private String unid;
	private BigDecimal vlitem;
	private BigDecimal vlDesc;
	private int cstIcms;
	private int cfop;
	private BigDecimal vlBcIcms;
	private BigDecimal aliqIcms;
	private BigDecimal vlIcms;
	private BigDecimal vlBcIcmsSt;
	private BigDecimal aliqSt;
	private BigDecimal vlIcmsSt;
	private IndicadorDeReceita indRec;
	private String codPart;
	private BigDecimal vlPis;
	private BigDecimal vlCofins;
	private String codCta;
	
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

	public String getCodClass() {
		return codClass;
	}

	public void setCodClass(String codClass) {
		this.codClass = codClass;
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

	public BigDecimal getVlitem() {
		return vlitem;
	}

	public void setVlitem(BigDecimal vlitem) {
		this.vlitem = vlitem;
	}

	public BigDecimal getVlDesc() {
		return vlDesc;
	}

	public void setVlDesc(BigDecimal vlDesc) {
		this.vlDesc = vlDesc;
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

	public BigDecimal getVlBcIcms() {
		return vlBcIcms;
	}

	public void setVlBcIcms(BigDecimal vlBcIcms) {
		this.vlBcIcms = vlBcIcms;
	}

	public BigDecimal getAliqIcms() {
		return aliqIcms;
	}

	public void setAliqIcms(BigDecimal aliqIcms) {
		this.aliqIcms = aliqIcms;
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

	public BigDecimal getAliqSt() {
		return aliqSt;
	}

	public void setAliqSt(BigDecimal aliqSt) {
		this.aliqSt = aliqSt;
	}

	public BigDecimal getVlIcmsSt() {
		return vlIcmsSt;
	}

	public void setVlIcmsSt(BigDecimal vlIcmsSt) {
		this.vlIcmsSt = vlIcmsSt;
	}

	public IndicadorDeReceita getIndRec() {
		return indRec;
	}

	public void setIndRec(IndicadorDeReceita indRec) {
		this.indRec = indRec;
	}

	public String getCodPart() {
		return codPart;
	}

	public void setCodPart(String codPart) {
		this.codPart = codPart;
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

	public String getCodCta() {
		return codCta;
	}

	public void setCodCta(String codCta) {
		this.codCta = codCta;
	}

}
