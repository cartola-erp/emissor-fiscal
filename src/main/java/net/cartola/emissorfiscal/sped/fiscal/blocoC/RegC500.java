package net.cartola.emissorfiscal.sped.fiscal.blocoC;

import static net.cartola.emissorfiscal.util.NumberUtilRegC100.getVlrOrBaseCalc;
import static net.cartola.emissorfiscal.util.SpedFiscalUtil.getCodSituacao;
import static net.cartola.emissorfiscal.util.SpedFiscalUtil.getIndicadorEmitente;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;
import net.cartola.emissorfiscal.documento.DocumentoFiscal;
import net.cartola.emissorfiscal.documento.IndicadorDeOperacao;
import net.cartola.emissorfiscal.loja.Loja;
import net.cartola.emissorfiscal.sped.fiscal.enums.CodConsumoEnergiaOuGas;
import net.cartola.emissorfiscal.sped.fiscal.enums.CodTipoDeLigacao;
import net.cartola.emissorfiscal.sped.fiscal.enums.IndicadorDoEmitente;
import net.cartola.emissorfiscal.sped.fiscal.enums.ModeloDocumentoFiscal;
import net.cartola.emissorfiscal.sped.fiscal.enums.SituacaoDoDocumento;
import net.cartola.emissorfiscal.util.SpedFiscalUtil;

/**
 * 02/09/2020
 * @author robson.costa
 * 
 * REGISTRO C500: NOTA FISCAL/CONTA DE ENERGIA ELÉTRICA (CÓDIGO 06), NOTA FISCAL DE ENERGIA
 * ELÉTRICA ELETRÔNICA – NF3e (CÓDIGO 66), NOTA FISCAL/CONTA DE FORNECIMENTO D'ÁGUA
 * CANALIZADA (CÓDIGO 29) E NOTA FISCAL CONSUMO FORNECIMENTO DE GÁS (CÓDIGO 28). 
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
	@Field(name = "codCons"),
	@Field(name = "numDOc"),
	@Field(name = "dtDoc"),
	@Field(name = "dtES"),
	@Field(name = "vlDoc"),
	@Field(name = "vlDesc"),
	@Field(name = "vlForn"),
	@Field(name = "vlSerNt"),
	@Field(name = "vlTerc"),
	@Field(name = "vlDa"),
	@Field(name = "vlBcIcms"),
	@Field(name = "vlIcms"),
	@Field(name = "vlBcIcmsSt"),
	@Field(name = "vlIcmsSt"),
	@Field(name = "codInf"),
	@Field(name = "vlPis"),
	@Field(name = "vlCofins"),
	@Field(name = "tpLigacao"),
	@Field(name = "codGrupoTensao"),
	@Field(name = "chvDOCe"),
	@Field(name = "finDOCe"),
	@Field(name = "chvDOCeRef"),
	@Field(name = "indDest"),
	@Field(name = "codMunDest"),
	@Field(name = "codCta"),
	// ==================== FILHO ====================
	@Field(name = "regC510"),
	@Field(name = "regC590"),
	@Field(name = "regC595")

})
public class RegC500 {
	
	private final String reg = "C500";
	private IndicadorDeOperacao indOper;
	private IndicadorDoEmitente indEmit;
	private String codPart;
	private ModeloDocumentoFiscal codMod;
	private SituacaoDoDocumento codSit;
	private Long ser;
	private Long sub;
	private CodConsumoEnergiaOuGas codCons;
	private Long numDoc;
	private LocalDate dtDoc;
	private LocalDate dtES;
	private BigDecimal vlDoc;
	private BigDecimal vlDesc;
	private BigDecimal vlForn;
	private BigDecimal vlSerNt;
	private BigDecimal vlTerc;
	private BigDecimal vlDa;
	private BigDecimal vlBcIcms;
	private BigDecimal vlIcms;
	private BigDecimal vlBcIcmsSt;
	private BigDecimal vlIcmsSt;
	private BigDecimal codInf;
	private BigDecimal vlPis;
	private BigDecimal vlCofins;
	private CodTipoDeLigacao tpLigacao;
	private String codGrupoTensao;
	private Long chvDOCe;
	private String finDOCe;
	private Long chvDOCeRef;
	private String indDest;
	private String codMunDest;
	private String codCta;

	// ==================== FILHO ====================
	private List<RegC510> regC510;
	private List<RegC590> regC590;
	private List<RegC595> regC595;

	public RegC500(DocumentoFiscal servico, Loja lojaSped) {
		IndicadorDeOperacao tipoOperacao = servico.getTipoOperacao();
    	
		this.indOper = tipoOperacao;
		this.indEmit = getIndicadorEmitente(servico, lojaSped);
		/*Nos casos que emitimos a NFE, o cod é do DESTINATARIO, contrario, seria o EMITENTE*/
		this.codPart = SpedFiscalUtil.getCodPart(servico);
		this.codMod = servico.getModelo();
		this.codSit = getCodSituacao(servico);
		this.ser = servico.getSerie();
//		this.sub = null
		this.codCons = null; 		
		this.numDoc = servico.getNumeroNota();
		this.dtDoc = servico.getEmissao();
		this.dtES = servico.getCadastro().toLocalDate();
		this.vlDoc = servico.getValorTotalDocumento();
		this.vlDesc = getVlrOrBaseCalc(servico.getValorDesconto(), tipoOperacao);
		this.vlForn = servico.getValorTotalDocumento();
		this.vlSerNt = null;		// Se esses campos não aceitarem null, então colocar ZERO
		this.vlTerc = null;
		this.vlDa = null;
		this.vlBcIcms = null;
		this.vlIcms = null;
		this.vlBcIcmsSt = null;
		this.vlIcmsSt = null;
		this.codInf = null;
		this.vlPis = getVlrOrBaseCalc(servico.getPisValor(), tipoOperacao);
		this.vlCofins = getVlrOrBaseCalc(servico.getCofinsValor(), tipoOperacao);
		this.tpLigacao = null;					// Se esses campos não aceitarem null, então colocar ZERO
		this.codGrupoTensao = null;
		this.chvDOCe = null;
		this.finDOCe = null;
		this.chvDOCeRef = null;
		this.indDest = null;
		this.codMunDest = null;
		this.codCta = null;
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

	public Long getSer() {
		return ser;
	}

	public void setSer(Long ser) {
		this.ser = ser;
	}

	public Long getSub() {
		return sub;
	}

	public void setSub(Long sub) {
		this.sub = sub;
	}

	public CodConsumoEnergiaOuGas getCodCons() {
		return codCons;
	}

	public void setCodCons(CodConsumoEnergiaOuGas codCons) {
		this.codCons = codCons;
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

	public BigDecimal getVlDesc() {
		return vlDesc;
	}

	public void setVlDesc(BigDecimal vlDesc) {
		this.vlDesc = vlDesc;
	}

	public BigDecimal getVlForn() {
		return vlForn;
	}

	public void setVlForn(BigDecimal vlForn) {
		this.vlForn = vlForn;
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

	public BigDecimal getCodInf() {
		return codInf;
	}

	public void setCodInf(BigDecimal codInf) {
		this.codInf = codInf;
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

	public CodTipoDeLigacao getTpLigacao() {
		return tpLigacao;
	}

	public void setTpLigacao(CodTipoDeLigacao tpLigacao) {
		this.tpLigacao = tpLigacao;
	}

	public String getCodGrupoTensao() {
		return codGrupoTensao;
	}

	public void setCodGrupoTensao(String codGrupoTensao) {
		this.codGrupoTensao = codGrupoTensao;
	}

	public Long getChvDOCe() {
		return chvDOCe;
	}

	public void setChvDOCe(Long chvDOCe) {
		this.chvDOCe = chvDOCe;
	}

	public String getFinDOCe() {
		return finDOCe;
	}

	public void setFinDOCe(String finDOCe) {
		this.finDOCe = finDOCe;
	}

	public Long getChvDOCeRef() {
		return chvDOCeRef;
	}

	public void setChvDOCeRef(Long chvDOCeRef) {
		this.chvDOCeRef = chvDOCeRef;
	}

	public String getIndDest() {
		return indDest;
	}

	public void setIndDest(String indDest) {
		this.indDest = indDest;
	}

	public String getCodMunDest() {
		return codMunDest;
	}

	public void setCodMunDest(String codMunDest) {
		this.codMunDest = codMunDest;
	}

	public String getCodCta() {
		return codCta;
	}

	public void setCodCta(String codCta) {
		this.codCta = codCta;
	}

	public List<RegC510> getRegC510() {
		return regC510;
	}

	public void setRegC510(List<RegC510> regC510) {
		this.regC510 = regC510;
	}

	public List<RegC590> getRegC590() {
		return regC590;
	}

	public void setRegC590(List<RegC590> regC590) {
		this.regC590 = regC590;
	}

	public List<RegC595> getRegC595() {
		return regC595;
	}

	public void setRegC595(List<RegC595> regC595) {
		this.regC595 = regC595;
	}
	
}	
