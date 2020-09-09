package net.cartola.emissorfiscal.sped.fiscal.blocoC;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;
import coffeepot.bean.wr.typeHandler.DefaultStringHandler;

/**
 * 02/09/2020
 * @author robson.costa
 */
@Record(fields = { 
	@Field(name = "reg", maxLength = 4),
    @Field(name = "ser"),
    @Field(name = "subSer"),
    @Field(name = "numDoc"),
    @Field(name = "dtDoc"),
    @Field(name = "cnpjCpf", params = {DefaultStringHandler.PARAM_FILTER_NUMBER_LETTERS_ONLY}),
    @Field(name = "vlMerc"),
    @Field(name = "vlDoc"),
    @Field(name = "vlDesc"),
    @Field(name = "vlPis"),
    @Field(name = "vlCofins"),
    @Field(name = "codCta"),
    // ===================== FILHO =======================
    @Field(name = "regC370"),
    @Field(name = "regC390")
})
public class RegC350 {
	
	private final String reg = "C350";
    private String ser;
    private String subSer;
    private String numDoc;
    private LocalDate dtDoc;
    private String cnpjCpf;
    private BigDecimal vlMerc;
    private BigDecimal vlDoc;
    private BigDecimal vlDesc;
    private BigDecimal vlPis;
    private BigDecimal vlCofins;
    private String codCta;
	// ===================== FILHO =======================
    private List<RegC370> regC370;
    private List<RegC390> regC390;
	
    public String getReg() {
		return reg;
	}

	public String getSer() {
		return ser;
	}

	public void setSer(String ser) {
		this.ser = ser;
	}

	public String getSubSer() {
		return subSer;
	}

	public void setSubSer(String subSer) {
		this.subSer = subSer;
	}

	public String getNumDoc() {
		return numDoc;
	}

	public void setNumDoc(String numDoc) {
		this.numDoc = numDoc;
	}

	public LocalDate getDtDoc() {
		return dtDoc;
	}

	public void setDtDoc(LocalDate dtDoc) {
		this.dtDoc = dtDoc;
	}

	public String getCnpjCpf() {
		return cnpjCpf;
	}

	public void setCnpjCpf(String cnpjCpf) {
		this.cnpjCpf = cnpjCpf;
	}

	public BigDecimal getVlMerc() {
		return vlMerc;
	}

	public void setVlMerc(BigDecimal vlMerc) {
		this.vlMerc = vlMerc;
	}

	public BigDecimal getVlDoc() {
		return vlDoc;
	}

	public void setVlDoc(BigDecimal vlDoc) {
		this.vlDoc = vlDoc;
	}

	public BigDecimal getVlDesc() {
		return vlDesc;
	}

	public void setVlDesc(BigDecimal vlDesc) {
		this.vlDesc = vlDesc;
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

	public List<RegC370> getRegC370() {
		return regC370;
	}

	public void setRegC370(List<RegC370> regC370) {
		this.regC370 = regC370;
	}

	public List<RegC390> getRegC390() {
		return regC390;
	}

	public void setRegC390(List<RegC390> regC390) {
		this.regC390 = regC390;
	}
    
}
