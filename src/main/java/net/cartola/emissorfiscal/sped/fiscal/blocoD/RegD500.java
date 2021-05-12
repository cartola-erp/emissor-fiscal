package net.cartola.emissorfiscal.sped.fiscal.blocoD;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;
import net.cartola.emissorfiscal.documento.DocumentoFiscal;
import net.cartola.emissorfiscal.documento.IndicadorDeOperacao;
import net.cartola.emissorfiscal.loja.Loja;
import net.cartola.emissorfiscal.sped.fiscal.enums.IndicadorDoEmitente;
import net.cartola.emissorfiscal.sped.fiscal.enums.ModeloDocumentoFiscal;
import net.cartola.emissorfiscal.sped.fiscal.enums.SituacaoDoDocumento;
import net.cartola.emissorfiscal.sped.fiscal.enums.TipoDeAssinante;

/**
 * 11/09/2020
 * @author robson.costa
 * 
 * REGISTRO D500: NOTA FISCAL DE SERVIÇO DE COMUNICAÇÃO (CÓDIGO 21) E NOTA FISCAL DE
 * SERVIÇO DE TELECOMUNICAÇÃO (CÓDIGO 22)
 */
@Record(fields = { 
	@Field(name = "reg", maxLength = 4),
	@Field(name = "indOper"),
	@Field(name = "indEmit"),
	@Field(name = "codPary"),
	@Field(name = "codMod"),
	@Field(name = "codSit"),
	@Field(name = "ser"),
	@Field(name = "sub"),
	@Field(name = "numDoc"),
	@Field(name = "dtDoc"),
	@Field(name = "dtAP"),
	@Field(name = "vlDoc"),
	@Field(name = "vlDesc"),
	@Field(name = "vlServ"),
	@Field(name = "vlSerNt"),
	@Field(name = "vlTerc"),
	@Field(name = "vlDa"),
	@Field(name = "vlBcIcms"),
	@Field(name = "vlIcms"),
	@Field(name = "codUInf"),
	@Field(name = "vlPis"),
	@Field(name = "vlCofins"),
	@Field(name = "codCta"),
	@Field(name = "tpAssinante"),
    // ==================== FILHOS ====================
//	@Field(name = "regD510"),
//	@Field(name = "regD530"),
	@Field(name = "regD590")
})
public class RegD500 {
	
	private final String reg = "D500";
	private IndicadorDeOperacao indOper;
	private IndicadorDoEmitente indEmit;
	private String codPary;
	private ModeloDocumentoFiscal codMod;
	private SituacaoDoDocumento codSit;
	private String ser;
	private String sub;
	private Long numDoc;
	private LocalDate dtDoc;
	private LocalDate dtAP;
	private BigDecimal vlDoc;
	private BigDecimal vlDesc;
	private BigDecimal vlServ;
	private BigDecimal vlSerNt;
	private BigDecimal vlTerc;
	private BigDecimal vlDa;
	private BigDecimal vlBcIcms;
	private BigDecimal vlIcms;
	private String codUInf;
	private BigDecimal vlPis;
	private BigDecimal vlCofins;
	private String codCta;
	private TipoDeAssinante tpAssinante;
    // ==================== FILHOS ====================
//	private List<RegD510> regD510;
//	private List<RegD530> regD530;
	private List<RegD590> regD590;
	
	public RegD500(DocumentoFiscal servico, Loja lojaSped) {
		// TODO Auto-generated constructor stub
	}

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

	public String getCodPary() {
		return codPary;
	}

	public void setCodPary(String codPary) {
		this.codPary = codPary;
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

	public BigDecimal getVlServ() {
		return vlServ;
	}

	public void setVlServ(BigDecimal vlServ) {
		this.vlServ = vlServ;
	}

	public BigDecimal getVlSerNt() {
		return vlSerNt;
	}

	public void setVlSerNt(BigDecimal vlSerNt) {
		this.vlSerNt = vlSerNt;
	}

	public BigDecimal getVlTerc() {
		return vlTerc;
	}

	public void setVlTerc(BigDecimal vlTerc) {
		this.vlTerc = vlTerc;
	}

	public BigDecimal getVlDa() {
		return vlDa;
	}

	public void setVlDa(BigDecimal vlDa) {
		this.vlDa = vlDa;
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

	public String getCodUInf() {
		return codUInf;
	}

	public void setCodUInf(String codUInf) {
		this.codUInf = codUInf;
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

	public TipoDeAssinante getTpAssinante() {
		return tpAssinante;
	}

	public void setTpAssinante(TipoDeAssinante tpAssinante) {
		this.tpAssinante = tpAssinante;
	}

	public List<RegD590> getRegD590() {
		return regD590;
	}

	public void setRegD590(List<RegD590> regD590) {
		this.regD590 = regD590;
	}
	
}
