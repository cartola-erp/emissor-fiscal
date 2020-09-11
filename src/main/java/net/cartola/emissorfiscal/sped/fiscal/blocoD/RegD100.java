package net.cartola.emissorfiscal.sped.fiscal.blocoD;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;
import coffeepot.bean.wr.typeHandler.DefaultStringHandler;
import net.cartola.emissorfiscal.sped.fiscal.enums.IndicadorDeFrete;
import net.cartola.emissorfiscal.sped.fiscal.enums.IndicadorDeOperacao;
import net.cartola.emissorfiscal.sped.fiscal.enums.IndicadorDoEmitente;
import net.cartola.emissorfiscal.sped.fiscal.enums.ModeloDocumentoFiscal;
import net.cartola.emissorfiscal.sped.fiscal.enums.SituacaoDoDocumento;

/**
 * 11/09/2020
 * @author robson.costa
 * 
 * REGISTRO D100: NOTA FISCAL DE SERVIÇO DE TRANSPORTE (CÓDIGO 07) E CONHECIMENTOS DE
 * TRANSPORTE RODOVIÁRIO DE CARGAS (CÓDIGO 08), CONHECIMENTOS DE TRANSPORTE DE CARGAS
 * AVULSO (CÓDIGO 8B), AQUAVIÁRIO DE CARGAS (CÓDIGO 09), AÉREO (CÓDIGO 10), FERROVIÁRIO DE
 * CARGAS (CÓDIGO 11), MULTIMODAL DE CARGAS (CÓDIGO 26), NOTA FISCAL DE TRANSPORTE
 * FERROVIÁRIO DE CARGA (CÓDIGO 27), CONHECIMENTO DE TRANSPORTE ELETRÔNICO – CT-e (CÓDIGO
 * 57), CONHECIMENTO DE TRANSPORTE ELETRÔNICO PARA OUTROS SERVIÇOS - CT-e OS (CÓDIGO 67) e
 * BILHETE DE PASSAGEM ELETRÔNICO (CÓDIGO 63)
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
    @Field(name = "sub"),
    @Field(name = "numDoc"),
    @Field(name = "chvCte", params = {DefaultStringHandler.PARAM_FILTER_NUMBER_ONLY}),
    @Field(name = "dtDoc"),
    @Field(name = "dtAP"),
    @Field(name = "tpCTe"),
    @Field(name = "chvCteRef", params = {DefaultStringHandler.PARAM_FILTER_NUMBER_ONLY}),
    @Field(name = "vlDoc"),
    @Field(name = "vlDesc"),
    @Field(name = "indFrt"),
    @Field(name = "vlServ"),
    @Field(name = "vlBcIcms"),
    @Field(name = "vlIcms"),
    @Field(name = "vlNt"),
    @Field(name = "codInf"),
    @Field(name = "codCta"),
    @Field(name = "codMunOrig", minVersion = 12),
    @Field(name = "codMunDest", minVersion = 12),
								
    // ==================== FILHOS ====================
//    @Field(name = "regD101"),
//    @Field(name = "regD110"),
//    @Field(name = "regD130"),
//    @Field(name = "regD140"),
//    @Field(name = "regD150"),
//    @Field(name = "regD160"),
//    @Field(name = "regD170"),
//    @Field(name = "regD180"),
    @Field(name = "regD190"),
    @Field(name = "regD195")

})
public class RegD100 {

	private final String reg = "D100";
    private IndicadorDeOperacao indOper; 
    private IndicadorDoEmitente indEmit;
    private String codPart;
    private ModeloDocumentoFiscal codMod;
    private SituacaoDoDocumento codSit;
    private String ser;
    private String sub;
    private Long numDoc;
    private String chvCte;
    private LocalDate dtDoc;
    private LocalDate dtAP;
    private String tpCTe;
    private String chvCteRef;
    private BigDecimal vlDoc;
    private BigDecimal vlDesc;
    private IndicadorDeFrete indFrt;
    private BigDecimal vlServ;
    private BigDecimal vlBcIcms;
    private BigDecimal vlIcms;
    private BigDecimal vlNt;
    private String codInf;
    private String codCta;
    private String codMunOrig;
    private String codMunDest;
    // ==================== FILHOS ====================
//  private RegD101 regD101;
//  private List<RegD110> regD110;
//  private List<RegD130> regD130;
//  private RegD140 regD140;
//  private RegD150 regD150;
//  private List<RegD160> regD160;
//  private RegD170 regD170;
//  private List<RegD180> regD180;
    private List<RegD190> regD190;
    private List<RegD195> regD195;
	
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

	public String getSub() {
		return sub;
	}

	public void setSub(String sub) {
		this.sub = sub;
	}

	public Long getNumDoc() {
		return numDoc;
	}

	public void setNumDoc(Long numDoc) {
		this.numDoc = numDoc;
	}

	public String getChvCte() {
		return chvCte;
	}

	public void setChvCte(String chvCte) {
		this.chvCte = chvCte;
	}

	public LocalDate getDtDoc() {
		return dtDoc;
	}

	public void setDtDoc(LocalDate dtDoc) {
		this.dtDoc = dtDoc;
	}

	public LocalDate getDtAP() {
		return dtAP;
	}

	public void setDtAP(LocalDate dtAP) {
		this.dtAP = dtAP;
	}

	public String getTpCTe() {
		return tpCTe;
	}

	public void setTpCTe(String tpCTe) {
		this.tpCTe = tpCTe;
	}

	public String getChvCteRef() {
		return chvCteRef;
	}

	public void setChvCteRef(String chvCteRef) {
		this.chvCteRef = chvCteRef;
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

	public IndicadorDeFrete getIndFrt() {
		return indFrt;
	}

	public void setIndFrt(IndicadorDeFrete indFrt) {
		this.indFrt = indFrt;
	}

	public BigDecimal getVlServ() {
		return vlServ;
	}

	public void setVlServ(BigDecimal vlServ) {
		this.vlServ = vlServ;
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

	public BigDecimal getVlNt() {
		return vlNt;
	}

	public void setVlNt(BigDecimal vlNt) {
		this.vlNt = vlNt;
	}

	public String getCodInf() {
		return codInf;
	}

	public void setCodInf(String codInf) {
		this.codInf = codInf;
	}

	public String getCodCta() {
		return codCta;
	}

	public void setCodCta(String codCta) {
		this.codCta = codCta;
	}

	public String getCodMunOrig() {
		return codMunOrig;
	}

	public void setCodMunOrig(String codMunOrig) {
		this.codMunOrig = codMunOrig;
	}

	public String getCodMunDest() {
		return codMunDest;
	}

	public void setCodMunDest(String codMunDest) {
		this.codMunDest = codMunDest;
	}

	public List<RegD190> getRegD190() {
		return regD190;
	}

	public void setRegD190(List<RegD190> regD190) {
		this.regD190 = regD190;
	}

	public List<RegD195> getRegD195() {
		return regD195;
	}

	public void setRegD195(List<RegD195> regD195) {
		this.regD195 = regD195;
	}
    
}
