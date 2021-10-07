package net.cartola.emissorfiscal.sped.fiscal.blocoC;

import static net.cartola.emissorfiscal.util.SpedFiscalUtil.getCodSituacao;
import static net.cartola.emissorfiscal.util.SpedFiscalUtil.getNumeroCfe;
import static net.cartola.emissorfiscal.util.SpedFiscalUtil.getNumeroSerieSat;
import static net.cartola.emissorfiscal.util.SpedFiscalUtil.isInformaDesconto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;
import net.cartola.emissorfiscal.documento.DocumentoFiscal;
import net.cartola.emissorfiscal.documento.IndicadorDeOperacao;
import net.cartola.emissorfiscal.properties.SpedFiscalProperties;
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
	@Field(name = "numCfe"),
	@Field(name = "dtDoc"),
	@Field(name = "vlCfe"),
	@Field(name = "vlPis"),
	@Field(name = "vlCofins"),
	@Field(name = "cnpjCpf"),
	@Field(name = "nrSat"),
	@Field(name = "chvCfe"),
	@Field(name = "vlDesc"),
	@Field(name = "vlMerc"),
	@Field(name = "vlOutDa"),
	@Field(name = "vlIcms"),
	@Field(name = "vlPisSt"),
	@Field(name = "vlCofinsSt"),
    // ==================== FILHO ====================
	@Field(name = "regC810"),
	@Field(name = "regC850")
	
})
public class RegC800 {
	
	private final String reg = "C800";
	private ModeloDocumentoFiscal codMod;
	private SituacaoDoDocumento codSit;
	private String numCfe;
	private LocalDate dtDoc;
	private BigDecimal vlCfe;
	private BigDecimal vlPis;
	private BigDecimal vlCofins;
	private Long cnpjCpf;
	private String nrSat;
	private String chvCfe;
	private BigDecimal vlDesc;
	private BigDecimal vlMerc;
	private BigDecimal vlOutDa;
	private BigDecimal vlIcms;
	private BigDecimal vlPisSt;
	private BigDecimal vlCofinsSt;
    // ==================== FILHO ====================
	private List<RegC810> regC810;
	private List<RegC850> regC850;	
	
	public RegC800() {	}
	
	public RegC800(DocumentoFiscal satEmititdo, SpedFiscalProperties spedFiscPropertie) {
		IndicadorDeOperacao tipoOperacao = satEmititdo.getTipoOperacao();
		
		this.codMod = satEmititdo.getModelo();
		this.codSit = getCodSituacao(satEmititdo);
		this.numCfe = getNumeroCfe(satEmititdo);
		this.dtDoc = satEmititdo.getEmissao();
		this.vlCfe = satEmititdo.getValorTotalDocumento();
		this.vlPis = satEmititdo.getPisValor();
		this.vlCofins = satEmititdo.getCofinsValor();
		this.cnpjCpf = null;
		this.nrSat = getNumeroSerieSat(satEmititdo);
		this.chvCfe = satEmititdo.getNfeChaveAcesso();
		// Está Zerando o desconto, pois atualmente não é destacado o desconto na NFE
		boolean informaDesconto = isInformaDesconto(tipoOperacao, spedFiscPropertie);
		this.vlDesc =  informaDesconto ? satEmititdo.getValorDesconto() : BigDecimal.ZERO;
//		this.vlDesc = satEmititdo.getValorDesconto();
		this.vlMerc = satEmititdo.getValorTotalDocumento();
		this.vlOutDa = satEmititdo.getValorOutrasDespesasAcessorias();
		this.vlIcms = satEmititdo.getIcmsValor();
		this.vlPisSt = BigDecimal.ZERO;
		this.vlCofinsSt = BigDecimal.ZERO;
	}

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

	public String getNumCfe() {
		return numCfe;
	}

	public void setNumCfe(String numCfe) {
		this.numCfe = numCfe;
	}

	public LocalDate getDtDoc() {
		return dtDoc;
	}

	public void setDtDoc(LocalDate dtDoc) {
		this.dtDoc = dtDoc;
	}

	public BigDecimal getVlCfe() {
		return vlCfe;
	}

	public void setVlCfe(BigDecimal vlCfe) {
		this.vlCfe = vlCfe;
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

	public Long getCnpjCpf() {
		return cnpjCpf;
	}

	public void setCnpjCpf(Long cnpjCpf) {
		this.cnpjCpf = cnpjCpf;
	}

	public String getNrSat() {
		return nrSat;
	}

	public void setNrSat(String nrSat) {
		this.nrSat = nrSat;
	}

	public String getChvCfe() {
		return chvCfe;
	}

	public void setChvCfe(String chvCfe) {
		this.chvCfe = chvCfe;
	}

	public BigDecimal getVlDesc() {
		return vlDesc;
	}

	public void setVlDesc(BigDecimal vlDesc) {
		this.vlDesc = vlDesc;
	}

	public BigDecimal getVlMerc() {
		return vlMerc;
	}

	public void setVlMerc(BigDecimal vlMerc) {
		this.vlMerc = vlMerc;
	}

	public BigDecimal getVlOutDa() {
		return vlOutDa;
	}

	public void setVlOutDa(BigDecimal vlOutDa) {
		this.vlOutDa = vlOutDa;
	}

	public BigDecimal getVlIcms() {
		return vlIcms;
	}

	public void setVlIcms(BigDecimal vlIcms) {
		this.vlIcms = vlIcms;
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

	public List<RegC810> getRegC810() {
		return regC810;
	}

	public void setRegC810(List<RegC810> regC810) {
		this.regC810 = regC810;
	}

	public List<RegC850> getRegC850() {
		return regC850;
	}

	public void setRegC850(List<RegC850> regC850) {
		this.regC850 = regC850;
	}

}
