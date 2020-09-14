package net.cartola.emissorfiscal.sped.fiscal.blocoH;

import java.math.BigDecimal;
import java.util.List;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;
import net.cartola.emissorfiscal.sped.fiscal.enums.PropriedadeEPosseItem;

/**
 * 14/09/2020
 * @author robson.costa
 */
@Record(fields = { 
		@Field(name = "reg", maxLength = 4),
		@Field(name = "codItem"),
	    @Field(name = "unid"),
	    @Field(name = "qtd"),
	    @Field(name = "vlUnit"),
	    @Field(name = "vlItem"),
	    @Field(name = "indProp"),
	    @Field(name = "codPart"),
	    @Field(name = "txtCompl"),
	    @Field(name = "codCta"),
	    @Field(name = "vlItemIr"),
		 // ==================== FILHOS ====================
	    @Field(name = "regH020"),
	    @Field(name = "regH030")
})
public class RegH010 {
	
	private final String reg = "H010";
	private String codItem;
	private String unid;
	private BigDecimal qtd;
	private BigDecimal vlUnit;
	private BigDecimal vlItem;
	private PropriedadeEPosseItem indProp;
	private String codPart;
	private String txtCompl;
	private String codCta;
	private BigDecimal vlItemIr;
	// ==================== FILHOS ====================
	private List<RegH020> regH020;
	private RegH030 regH030;
	
	public String getReg() {
		return reg;
	}

	public String getCodItem() {
		return codItem;
	}

	public void setCodItem(String codItem) {
		this.codItem = codItem;
	}

	public String getUnid() {
		return unid;
	}

	public void setUnid(String unid) {
		this.unid = unid;
	}

	public BigDecimal getQtd() {
		return qtd;
	}

	public void setQtd(BigDecimal qtd) {
		this.qtd = qtd;
	}

	public BigDecimal getVlUnit() {
		return vlUnit;
	}

	public void setVlUnit(BigDecimal vlUnit) {
		this.vlUnit = vlUnit;
	}

	public BigDecimal getVlItem() {
		return vlItem;
	}

	public void setVlItem(BigDecimal vlItem) {
		this.vlItem = vlItem;
	}

	public PropriedadeEPosseItem getIndProp() {
		return indProp;
	}

	public void setIndProp(PropriedadeEPosseItem indProp) {
		this.indProp = indProp;
	}

	public String getCodPart() {
		return codPart;
	}

	public void setCodPart(String codPart) {
		this.codPart = codPart;
	}

	public String getTxtCompl() {
		return txtCompl;
	}

	public void setTxtCompl(String txtCompl) {
		this.txtCompl = txtCompl;
	}

	public String getCodCta() {
		return codCta;
	}

	public void setCodCta(String codCta) {
		this.codCta = codCta;
	}

	public BigDecimal getVlItemIr() {
		return vlItemIr;
	}

	public void setVlItemIr(BigDecimal vlItemIr) {
		this.vlItemIr = vlItemIr;
	}

	public List<RegH020> getRegH020() {
		return regH020;
	}

	public void setRegH020(List<RegH020> regH020) {
		this.regH020 = regH020;
	}

	public RegH030 getRegH030() {
		return regH030;
	}

	public void setRegH030(RegH030 regH030) {
		this.regH030 = regH030;
	}
	
}
