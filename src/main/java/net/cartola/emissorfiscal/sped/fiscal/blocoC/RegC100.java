package net.cartola.emissorfiscal.sped.fiscal.blocoC;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;
import net.cartola.emissorfiscal.documento.IndicadorDeOperacao;
import net.cartola.emissorfiscal.sped.fiscal.enums.IndicadorDeFrete;
import net.cartola.emissorfiscal.sped.fiscal.enums.IndicadorDePagamento;
import net.cartola.emissorfiscal.sped.fiscal.enums.IndicadorDoEmitente;
import net.cartola.emissorfiscal.sped.fiscal.enums.ModeloDocumentoFiscal;
import net.cartola.emissorfiscal.sped.fiscal.enums.SituacaoDoDocumento;

/**
 * 02/09/2020
 * 
 * @author robson.costa
 * 
 * REGISTRO C100: NOTA FISCAL (CÓDIGO 01), NOTA FISCAL AVULSA (CÓDIGO 1B), NOTA FISCAL DE PRODUTOR (CÓDIGO 04), 
 * NF-e (CÓDIGO 55) e NFC-e (CÓDIGO 65)
 *
 */
@Record(fields = {
    @Field(name = "reg", maxLength = 4),
    @Field(name = "indOper"),
    @Field(name = "indEmit"),
    @Field(name = "codPart"),
    @Field(name = "codMod"),
    @Field(name = "codSit"),
    @Field(name = "ser"),
    @Field(name = "numDoc"),
    @Field(name = "chvNfe"),
    @Field(name = "dtDoc"),
    @Field(name = "dtES"),
    @Field(name = "vlDoc"),
    @Field(name = "indPgto"),
    @Field(name = "vlDesc"),
    @Field(name = "vlAbatNt"),
    @Field(name = "vlMerc"),
    @Field(name = "indFrt"),
    @Field(name = "vlFrt"),
    @Field(name = "vlSeg"),
    @Field(name = "vlOutDa"),
    @Field(name = "vlBcIcms"),
    @Field(name = "vlIcms"),
    @Field(name = "vlBcIcmsSt"),
    @Field(name = "vlIcmsSt"),
    @Field(name = "vlIpi"),
    @Field(name = "vlPis"),
    @Field(name = "vlCofins"),
    @Field(name = "vlPisSt"),
    @Field(name = "vlCofinsSt"),
    
	// ============ REGISTROS FILHOS ============
    @Field(name = "regC101"),
    @Field(name = "regC105"),
    @Field(name = "regC110"),
    @Field(name = "regC120"),
    @Field(name = "regC130"),
    @Field(name = "regC140"),
    @Field(name = "regC160"),
    @Field(name = "regC165"),
    @Field(name = "regC170"),
//    @Field(name = "regC185"),
    @Field(name = "regC190"),
    @Field(name = "regC195")
})
public class RegC100 {

	private final String reg = "C100";
	private IndicadorDeOperacao indOper;
	private IndicadorDoEmitente indEmit;
	private String codPart;
	private ModeloDocumentoFiscal codMod;
	private SituacaoDoDocumento codSit;
	private String ser;
	private Long numDoc;
	private Long chvNfe;
	private LocalDate dtDoc;
	private LocalDate dtES;
	private BigDecimal vlDoc;
	private IndicadorDePagamento indPgto;
	private BigDecimal vlDesc;
	private BigDecimal vlAbatNt;
	private BigDecimal vlMerc;
	private IndicadorDeFrete indFrt;
	
	private BigDecimal vlFrt;
	private BigDecimal vlSeg;
	private BigDecimal vlOutDa;
	private BigDecimal vlBcIcms;
	private BigDecimal vlIcms;
	private BigDecimal vlBcIcmsSt;
	private BigDecimal vlIcmsSt;
	private BigDecimal vlIpi;
	private BigDecimal vlPis;
	private BigDecimal vlCofins;
	private BigDecimal vlPisSt;
	private BigDecimal vlCofinsSt;

	// ============ REGISTROS FILHOS ============
	private RegC101 regC101;
	private RegC105 regC105;
	private List<RegC110> regC110;
	private List<RegC120> regC120;
	private RegC130 regC130;
	private RegC140 regC140;
	private RegC160 regC160;
	private List<RegC165> regC165;
	private List<RegC170> regC170;
//	private List<RegC185> regC185;			// this 
	private List<RegC190> regC190;
	private List<RegC195> regC195;
	
	public String getReg() {
		return reg;
	}

	public IndicadorDeOperacao getIndOper() {
		return indOper;
	}

	public void setIndOper(IndicadorDeOperacao indOper) {
		this.indOper = indOper;
	}

	public IndicadorDoEmitente getIndEmit() {
		return indEmit;
	}

	public void setIndEmit(IndicadorDoEmitente indEmit) {
		this.indEmit = indEmit;
	}

	public String getCodPart() {
		return codPart;
	}

	public void setCodPart(String codPart) {
		this.codPart = codPart;
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

	public String getSer() {
		return ser;
	}

	public void setSer(String ser) {
		this.ser = ser;
	}

	public Long getNumDoc() {
		return numDoc;
	}

	public void setNumDoc(Long numDoc) {
		this.numDoc = numDoc;
	}

	public Long getChvNfe() {
		return chvNfe;
	}

	public void setChvNfe(Long chvNfe) {
		this.chvNfe = chvNfe;
	}

	public LocalDate getDtDoc() {
		return dtDoc;
	}

	public void setDtDoc(LocalDate dtDoc) {
		this.dtDoc = dtDoc;
	}

	public LocalDate getDtES() {
		return dtES;
	}

	public void setDtES(LocalDate dtES) {
		this.dtES = dtES;
	}

	public BigDecimal getVlDoc() {
		return vlDoc;
	}

	public void setVlDoc(BigDecimal vlDoc) {
		this.vlDoc = vlDoc;
	}

	public IndicadorDePagamento getIndPgto() {
		return indPgto;
	}

	public void setIndPgto(IndicadorDePagamento indPgto) {
		this.indPgto = indPgto;
	}

	public BigDecimal getVlDesc() {
		return vlDesc;
	}

	public void setVlDesc(BigDecimal vlDesc) {
		this.vlDesc = vlDesc;
	}

	public BigDecimal getVlAbatNt() {
		return vlAbatNt;
	}

	public void setVlAbatNt(BigDecimal vlAbatNt) {
		this.vlAbatNt = vlAbatNt;
	}

	public BigDecimal getVlMerc() {
		return vlMerc;
	}

	public void setVlMerc(BigDecimal vlMerc) {
		this.vlMerc = vlMerc;
	}

	public IndicadorDeFrete getIndFrt() {
		return indFrt;
	}

	public void setIndFrt(IndicadorDeFrete indFrt) {
		this.indFrt = indFrt;
	}

	public BigDecimal getVlFrt() {
		return vlFrt;
	}

	public void setVlFrt(BigDecimal vlFrt) {
		this.vlFrt = vlFrt;
	}

	public BigDecimal getVlSeg() {
		return vlSeg;
	}

	public void setVlSeg(BigDecimal vlSeg) {
		this.vlSeg = vlSeg;
	}

	public BigDecimal getVlOutDa() {
		return vlOutDa;
	}

	public void setVlOutDa(BigDecimal vlOutDa) {
		this.vlOutDa = vlOutDa;
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

	public BigDecimal getVlIpi() {
		return vlIpi;
	}

	public void setVlIpi(BigDecimal vlIpi) {
		this.vlIpi = vlIpi;
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

	public BigDecimal getVlPisSt() {
		return vlPisSt;
	}

	public void setVlPisSt(BigDecimal vlPisSt) {
		this.vlPisSt = vlPisSt;
	}

	public BigDecimal getVlCofinsSt() {
		return vlCofinsSt;
	}

	public void setVlCofinsSt(BigDecimal vlCofinsSt) {
		this.vlCofinsSt = vlCofinsSt;
	}

	public RegC101 getRegC101() {
		return regC101;
	}

	public void setRegC101(RegC101 regC101) {
		this.regC101 = regC101;
	}

	public RegC105 getRegC105() {
		return regC105;
	}

	public void setRegC105(RegC105 regC105) {
		this.regC105 = regC105;
	}

	public List<RegC110> getRegC110() {
		return regC110;
	}

	public void setRegC110(List<RegC110> regC110) {
		this.regC110 = regC110;
	}

	public List<RegC120> getRegC120() {
		return regC120;
	}

	public void setRegC120(List<RegC120> regC120) {
		this.regC120 = regC120;
	}

	public RegC130 getRegC130() {
		return regC130;
	}

	public void setRegC130(RegC130 regC130) {
		this.regC130 = regC130;
	}

	public RegC140 getRegC140() {
		return regC140;
	}

	public void setRegC140(RegC140 regC140) {
		this.regC140 = regC140;
	}

	public RegC160 getRegC160() {
		return regC160;
	}

	public void setRegC160(RegC160 regC160) {
		this.regC160 = regC160;
	}

	public List<RegC165> getRegC165() {
		return regC165;
	}

	public void setRegC165(List<RegC165> regC165) {
		this.regC165 = regC165;
	}

	public List<RegC170> getRegC170() {
		return regC170;
	}

	public void setRegC170(List<RegC170> regC170) {
		this.regC170 = regC170;
	}

//	public List<RegC185> getRegC185() {
//		return regC185;
//	}
//
//	public void setRegC185(List<RegC185> regC185) {
//		this.regC185 = regC185;
//	}

	public List<RegC190> getRegC190() {
		return regC190;
	}

	public void setRegC190(List<RegC190> regC190) {
		this.regC190 = regC190;
	}

	public List<RegC195> getRegC195() {
		return regC195;
	}

	public void setRegC195(List<RegC195> regC195) {
		this.regC195 = regC195;
	}

}
