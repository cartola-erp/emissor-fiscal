package net.cartola.emissorfiscal.sped.fiscal.blocoC;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;
import net.cartola.emissorfiscal.sped.fiscal.enums.ModeloDocumentoFiscal;
import net.cartola.emissorfiscal.sped.fiscal.enums.SituacaoDoDocumento;

/**
 * 02/09/2020
 * @author robson.costa
 */
@Record(fields = { 
	@Field(name = "reg", maxLength = 4),
	@Field(name = "codMod"),
	@Field(name = "codSit"),
	@Field(name = "numDoc"),
	@Field(name = "dtDoc"),
	@Field(name = "vlDoc"),
	@Field(name = "vlPis"),
	@Field(name = "vlCofins"),
	@Field(name = "cpfCnpj"),
	@Field(name = "nomAdq"),
	// ==================== FILHO ====================
	@Field(name = "regC465"),
	@Field(name = "regC470")
})
public class RegC460 {

	private final String reg = "C460";
	private ModeloDocumentoFiscal codMod;
	private SituacaoDoDocumento codSit;
	private Long numDoc;
	private LocalDate dtDoc;
	private BigDecimal vlDoc;
	private BigDecimal vlPis;
	private BigDecimal vlCofins;
	private Long cpfCnpj;
	private String nomAdq;
	// ==================== FILHO ====================
	private RegC465 regC465;
	private List<RegC470> regC470;
	
	public String getReg() {
		return reg;
	}

	public ModeloDocumentoFiscal getCodMod() {
		return codMod;
	}

	public void setCodMod(ModeloDocumentoFiscal codMod) {
		this.codMod = codMod;
	}

	public SituacaoDoDocumento getCodSit() {
		return codSit;
	}

	public void setCodSit(SituacaoDoDocumento codSit) {
		this.codSit = codSit;
	}

	public Long getNumDoc() {
		return numDoc;
	}

	public void setNumDoc(Long numDoc) {
		this.numDoc = numDoc;
	}

	public LocalDate getDtDoc() {
		return dtDoc;
	}

	public void setDtDoc(LocalDate dtDoc) {
		this.dtDoc = dtDoc;
	}

	public BigDecimal getVlDoc() {
		return vlDoc;
	}

	public void setVlDoc(BigDecimal vlDoc) {
		this.vlDoc = vlDoc;
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

	public Long getCpfCnpj() {
		return cpfCnpj;
	}

	public void setCpfCnpj(Long cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}

	public String getNomAdq() {
		return nomAdq;
	}

	public void setNomAdq(String nomAdq) {
		this.nomAdq = nomAdq;
	}

	public RegC465 getRegC465() {
		return regC465;
	}

	public void setRegC465(RegC465 regC465) {
		this.regC465 = regC465;
	}

	public List<RegC470> getRegC470() {
		return regC470;
	}

	public void setRegC470(List<RegC470> regC470) {
		this.regC470 = regC470;
	}
	
}
