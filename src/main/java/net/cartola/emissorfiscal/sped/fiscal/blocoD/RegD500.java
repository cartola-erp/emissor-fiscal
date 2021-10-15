package net.cartola.emissorfiscal.sped.fiscal.blocoD;

import static net.cartola.emissorfiscal.util.NumberUtilRegC100.getVlrOrBaseCalc;
import static net.cartola.emissorfiscal.util.SpedFiscalUtil.getCodSituacao;
import static net.cartola.emissorfiscal.util.SpedFiscalUtil.getIndicadorEmitente;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;
import net.cartola.emissorfiscal.documento.DocumentoFiscal;
import net.cartola.emissorfiscal.documento.IndicadorDeOperacao;
import net.cartola.emissorfiscal.loja.Loja;
import net.cartola.emissorfiscal.sped.fiscal.enums.IndicadorDoEmitente;
import net.cartola.emissorfiscal.sped.fiscal.enums.ModeloDocumentoFiscal;
import net.cartola.emissorfiscal.sped.fiscal.enums.SituacaoDoDocumento;
import net.cartola.emissorfiscal.sped.fiscal.enums.TipoDeAssinante;
import net.cartola.emissorfiscal.util.SpedFiscalUtil;

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
	@Field(name = "codPart"),
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
	@Field(name = "codInf"),
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
	private String codPart;
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
	private String codInf;
	private BigDecimal vlPis;
	private BigDecimal vlCofins;
	private String codCta;
	private TipoDeAssinante tpAssinante;
    // ==================== FILHOS ====================
//	private List<RegD510> regD510;
//	private List<RegD530> regD530;
	private List<RegD590> regD590;
	
	public RegD500(DocumentoFiscal servico, Loja lojaSped, Map<String, Loja> mapLojasPorCnpj) {
		IndicadorDeOperacao tipoOperacao = servico.getTipoOperacao();
    	
		this.indOper = tipoOperacao;
		this.indEmit = getIndicadorEmitente(servico, lojaSped);
		/*Nos casos que emitimos a NFE, o cod é do DESTINATARIO, contrario, seria o EMITENTE*/
		this.codPart = SpedFiscalUtil.getCodPart(servico, mapLojasPorCnpj);
		this.codMod = servico.getModelo();
		this.codSit = getCodSituacao(servico);
		this.ser = servico.getSerie() != null && servico.getSerie() != 0 ? servico.getSerie().toString() : "";		// TODO @see ATUAALMENET em DOC FISCAL, a série é um LONG
//		this.sub = null
		this.numDoc = servico.getNumeroNota();
		this.dtDoc = servico.getEmissao();
		this.dtAP = servico.getCadastro().toLocalDate();
		this.vlDoc = servico.getValorTotalDocumento();
		this.vlDesc = getVlrOrBaseCalc(servico.getValorDesconto(), tipoOperacao);
		this.vlServ = BigDecimal.ZERO;
		this.vlSerNt = null;		// Se esses campos não aceitarem null, então colocar ZERO
		this.vlTerc = null;
		this.vlDa = null;
		this.vlBcIcms = null;
		this.vlIcms = null;
		this.codInf = null;
		this.vlPis = getVlrOrBaseCalc(servico.getPisValor(), tipoOperacao);
		this.vlCofins = getVlrOrBaseCalc(servico.getCofinsValor(), tipoOperacao);
		this.codCta = null;
		this.tpAssinante = null;
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

	public String getCodInf() {
		return codInf;
	}

	public void setCodinf(String codInf) {
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
