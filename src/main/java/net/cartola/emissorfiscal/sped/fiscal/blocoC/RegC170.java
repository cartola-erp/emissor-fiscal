package net.cartola.emissorfiscal.sped.fiscal.blocoC;

import static net.cartola.emissorfiscal.util.NumberUtilRegC100.getAliqAsBigDecimal;
import static net.cartola.emissorfiscal.util.NumberUtilRegC100.getVlrOrBaseCalc;
import static net.cartola.emissorfiscal.util.SpedFiscalUtil.getCstIcmsComOrigem;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;
import coffeepot.bean.wr.types.Align;
import net.cartola.emissorfiscal.documento.DocumentoFiscal;
import net.cartola.emissorfiscal.documento.DocumentoFiscalItem;
import net.cartola.emissorfiscal.documento.IndicadorDeOperacao;
import net.cartola.emissorfiscal.sped.fiscal.enums.ApuracaoIpi;
import net.cartola.emissorfiscal.util.SpedFiscalUtil;

/**
 * 02/09/2020
 * @author robson.costa
 */
@Record(fields = { 
	@Field(name = "reg", maxLength = 4),
    @Field(name = "numItem"),
    @Field(name = "codItem", align = Align.RIGHT, padding = '0', minLength = 8, maxLength = 8),
    @Field(name = "descrCompl"),
    @Field(name = "qtd"),
    @Field(name = "unid"),
    @Field(name = "vlItem"),
    @Field(name = "vlDesc"),
    @Field(name = "indMov", params = {"true=0;false=1"}),
    @Field(name = "cstIcms", length = 3, align = Align.RIGHT, padding = '0'),
    @Field(name = "cfop"),
    @Field(name = "codNat"),
    @Field(name = "vlBcIcms"),
    @Field(name = "aliqIcms"),
    @Field(name = "vlIcms"),
    @Field(name = "vlBcIcmsSt"),
    @Field(name = "aliqSt"),
    @Field(name = "vlIcmsSt"),
    @Field(name = "indApur"),
    @Field(name = "cstIpi", minLength = 2, align = Align.RIGHT, padding = '0'),
    @Field(name = "codEnq"),
    @Field(name = "vlBcIpi"),
    @Field(name = "aliqIpi"),
    @Field(name = "vlIpi"),
    @Field(name = "cstPis", minLength = 2, align = Align.RIGHT, padding = '0'),
    @Field(name = "vlBcPis"),
    @Field(name = "aliqPis"),
    @Field(name = "quantBcPis"),
    @Field(name = "aliqPisReal"),
    @Field(name = "vlPis"),
    @Field(name = "cstCofins", minLength = 2, align = Align.RIGHT, padding = '0'),
    @Field(name = "vlBcCofins"),
    @Field(name = "aliqCofins"),
    @Field(name = "quantBcCofins"),
    @Field(name = "aliqCofinsReal"),
    @Field(name = "vlCofins"),
    @Field(name = "codCta"),
//    @Field(name = "vlAbatNt", minVersion = 13),
    @Field(name = "vlAbatNt"),
    // ==================== FILHO ====================
    @Field(name = "regC171"),
    @Field(name = "regC172"),
    @Field(name = "regC173"),
    @Field(name = "regC174"),
    @Field(name = "regC175"),
    @Field(name = "regC176"),
	
    @Field(name = "regC177"),
    @Field(name = "regC178"),
    @Field(name = "regC179"),
    @Field(name = "regC180")
})
public class RegC170 {

	private final String reg = "C170";
	private int numItem;
	private String codItem;
	private String descrCompl;
	private Double qtd;
	private String unid;
	private BigDecimal vlItem;
	private BigDecimal vlDesc;
	private Boolean indMov;
	private String cstIcms;
	private int cfop;
	private Long codNat;
	private BigDecimal vlBcIcms;
	private BigDecimal aliqIcms;
	private BigDecimal vlIcms;
	private BigDecimal vlBcIcmsSt;
	private BigDecimal aliqSt;
	private BigDecimal vlIcmsSt;
	
	private ApuracaoIpi indApur;
	private Integer cstIpi;
	private String codEnq;
	private BigDecimal vlBcIpi;
	private BigDecimal aliqIpi;
	private BigDecimal vlIpi;
	
	private int cstPis;
	private BigDecimal vlBcPis;
	private BigDecimal aliqPis;
	private BigDecimal quantBcPis;
	private BigDecimal aliqPisReal;
	private BigDecimal vlPis;
	
	private int cstCofins;
	private BigDecimal vlBcCofins;
	private BigDecimal aliqCofins;
	private BigDecimal quantBcCofins;
	private BigDecimal aliqCofinsReal;
	private BigDecimal vlCofins;
	private Double codCta;
	private BigDecimal vlAbatNt;
	
	// ==================== FILHO ====================
	private List<RegC171> regC171;
	private List<RegC172> regC172;
	private List<RegC173> regC173;
	private List<RegC174> regC174;
	private List<RegC175> regC175;
	private List<RegC176> regC176;
	private RegC177 regC177;
	private RegC178 regC178;
	private RegC179 regC179;
	private RegC180 regC180;
	
	public RegC170(DocumentoFiscal docFisc, DocumentoFiscalItem item) {
		// TODO -> O ideal é ter uma parte de pré configurações do SPED, e essa parte também esteja lá
		List<Long> codOperacoesSemMovimentoEstoque = Arrays.asList(15L, 27L, 74L, 82L);
		Boolean indMov = !codOperacoesSemMovimentoEstoque.contains(docFisc.getOperacao().getId());
		IndicadorDeOperacao tipoOperacao = docFisc.getTipoOperacao();

		this.numItem = item.getItem();
		this.codItem = SpedFiscalUtil.getCodItem(item);
		this.descrCompl = item.getDescricaoEmpresa();
		this.qtd = item.getQuantidade().doubleValue();
		this.unid = item.getUnidade().getSigla();
		this.vlItem = item.getValorUnitario();
		this.vlDesc = item.getDesconto();
		this.indMov =  indMov; 											// MOVIMENTOU ou NÃO o ITEM
		this.cstIcms = getCstIcmsComOrigem(item.getOrigem(), item.getIcmsCst());
		this.cfop = item.getCfop();
		this.codNat = docFisc.getOperacao().getId();
		this.vlBcIcms = getVlrOrBaseCalc(item.getIcmsBase(), tipoOperacao);			// ICMS
		this.aliqIcms = getAliqAsBigDecimal(item.getIcmsAliquota(), tipoOperacao);
		this.vlIcms = getVlrOrBaseCalc(item.getIcmsValor(), tipoOperacao);
		this.vlBcIcmsSt = getVlrOrBaseCalc(item.getIcmsStBase(), tipoOperacao);
		this.aliqSt = getAliqAsBigDecimal(item.getIcmsStAliquota(), tipoOperacao);
		this.vlIcmsSt = getVlrOrBaseCalc(item.getIcmsStValor(), tipoOperacao);
		this.indApur = null;
		// Null pois segundo o sistema kolossus (validador de efd), como não somos contribuintes de IPI, devemos deixar "VAZIO"
		this.cstIpi = null;	// IPI
//		this.cstIpi = tipoOperacao.equals(IndicadorDeOperacao.ENTRADA) ? 49 : 99;	// IPI
		this.codEnq = null;
		this.vlBcIpi = getVlrOrBaseCalc(item.getIpiBase(), tipoOperacao);
		this.aliqIpi = getAliqAsBigDecimal(item.getIpiAliquota(), tipoOperacao);
		this.vlIpi = getVlrOrBaseCalc(item.getIpiValor(), tipoOperacao);
		this.cstPis = item.getPisCst();												// PIS
		this.vlBcPis = getVlrOrBaseCalc(item.getPisBase(), tipoOperacao);
		this.aliqPis = getAliqAsBigDecimal(item.getPisAliquota(), tipoOperacao);
		this.quantBcPis = BigDecimal.ZERO;
		this.aliqPisReal = BigDecimal.ZERO;
		this.vlPis = getVlrOrBaseCalc(item.getPisValor(), tipoOperacao);
		this.cstCofins = item.getCofinsCst();										// COFINS
		this.vlBcCofins = getVlrOrBaseCalc(item.getCofinsBase(), tipoOperacao);
		this.aliqCofins = getAliqAsBigDecimal(item.getCofinsAliquota(), tipoOperacao);
		this.quantBcCofins = BigDecimal.ZERO;
		this.aliqCofinsReal = BigDecimal.ZERO;
		this.vlCofins = getVlrOrBaseCalc(item.getCofinsValor(), tipoOperacao);
		this.codCta = 0d;
		this.vlAbatNt = BigDecimal.ZERO;
	}

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

	public String getDescrCompl() {
		return descrCompl;
	}

	public void setDescrCompl(String descrCompl) {
		this.descrCompl = descrCompl;
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

	public BigDecimal getVlItem() {
		return vlItem;
	}

	public void setVlItem(BigDecimal vlItem) {
		this.vlItem = vlItem;
	}

	public BigDecimal getVlDesc() {
		return vlDesc;
	}

	public void setVlDesc(BigDecimal vlDesc) {
		this.vlDesc = vlDesc;
	}

	public Boolean getIndMov() {
		return indMov;
	}

	public void setIndMov(Boolean indMov) {
		this.indMov = indMov;
	}

	public String getCstIcms() {
		return cstIcms;
	}

	public void setCstIcms(String cstIcms) {
		this.cstIcms = cstIcms;
	}

	public int getCfop() {
		return cfop;
	}

	public void setCfop(int cfop) {
		this.cfop = cfop;
	}

	public Long getCodNat() {
		return codNat;
	}

	public void setCodNat(Long codNat) {
		this.codNat = codNat;
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

	public ApuracaoIpi getIndApur() {
		return indApur;
	}

	public void setIndApur(ApuracaoIpi indApur) {
		this.indApur = indApur;
	}

	public Integer getCstIpi() {
		return cstIpi;
	}

	public void setCstIpi(Integer cstIpi) {
		this.cstIpi = cstIpi;
	}

	public String getCodEnq() {
		return codEnq;
	}

	public void setCodEnq(String codEnq) {
		this.codEnq = codEnq;
	}

	public BigDecimal getVlBcIpi() {
		return vlBcIpi;
	}

	public void setVlBcIpi(BigDecimal vlBcIpi) {
		this.vlBcIpi = vlBcIpi;
	}

	public BigDecimal getAliqIpi() {
		return aliqIpi;
	}

	public void setAliqIpi(BigDecimal aliqIpi) {
		this.aliqIpi = aliqIpi;
	}

	public BigDecimal getVlIpi() {
		return vlIpi;
	}

	public void setVlIpi(BigDecimal vlIpi) {
		this.vlIpi = vlIpi;
	}

	public int getCstPis() {
		return cstPis;
	}

	public void setCstPis(int cstPis) {
		this.cstPis = cstPis;
	}

	public BigDecimal getVlBcPis() {
		return vlBcPis;
	}

	public void setVlBcPis(BigDecimal vlBcPis) {
		this.vlBcPis = vlBcPis;
	}

	public BigDecimal getAliqPis() {
		return aliqPis;
	}

	public void setAliqPis(BigDecimal aliqPis) {
		this.aliqPis = aliqPis;
	}

	public BigDecimal getQuantBcPis() {
		return quantBcPis;
	}

	public void setQuantBcPis(BigDecimal quantBcPis) {
		this.quantBcPis = quantBcPis;
	}

	public BigDecimal getAliqPisReal() {
		return aliqPisReal;
	}

	public void setAliqPisReal(BigDecimal aliqPisReal) {
		this.aliqPisReal = aliqPisReal;
	}

	public BigDecimal getVlPis() {
		return vlPis;
	}

	public void setVlPis(BigDecimal vlPis) {
		this.vlPis = vlPis;
	}

	public int getCstCofins() {
		return cstCofins;
	}

	public void setCstCofins(int cstCofins) {
		this.cstCofins = cstCofins;
	}

	public BigDecimal getVlBcCofins() {
		return vlBcCofins;
	}

	public void setVlBcCofins(BigDecimal vlBcCofins) {
		this.vlBcCofins = vlBcCofins;
	}

	public BigDecimal getAliqCofins() {
		return aliqCofins;
	}

	public void setAliqCofins(BigDecimal aliqCofins) {
		this.aliqCofins = aliqCofins;
	}

	public BigDecimal getQuantBcCofins() {
		return quantBcCofins;
	}

	public void setQuantBcCofins(BigDecimal quantBcCofins) {
		this.quantBcCofins = quantBcCofins;
	}

	public BigDecimal getAliqCofinsReal() {
		return aliqCofinsReal;
	}

	public void setAliqCofinsReal(BigDecimal aliqCofinsReal) {
		this.aliqCofinsReal = aliqCofinsReal;
	}

	public BigDecimal getVlCofins() {
		return vlCofins;
	}

	public void setVlCofins(BigDecimal vlCofins) {
		this.vlCofins = vlCofins;
	}

	public Double getCodCta() {
		return codCta;
	}

	public void setCodCta(Double codCta) {
		this.codCta = codCta;
	}

	public BigDecimal getVlAbatNt() {
		return vlAbatNt;
	}

	public void setVlAbatNt(BigDecimal vlAbatNt) {
		this.vlAbatNt = vlAbatNt;
	}

	public List<RegC171> getRegC171() {
		return regC171;
	}

	public void setRegC171(List<RegC171> regC171) {
		this.regC171 = regC171;
	}

	public List<RegC172> getRegC172() {
		return regC172;
	}

	public void setRegC172(List<RegC172> regC172) {
		this.regC172 = regC172;
	}

	public List<RegC173> getRegC173() {
		return regC173;
	}

	public void setRegC173(List<RegC173> regC173) {
		this.regC173 = regC173;
	}

	public List<RegC174> getRegC174() {
		return regC174;
	}

	public void setRegC174(List<RegC174> regC174) {
		this.regC174 = regC174;
	}

	public List<RegC175> getRegC175() {
		return regC175;
	}

	public void setRegC175(List<RegC175> regC175) {
		this.regC175 = regC175;
	}

	public List<RegC176> getRegC176() {
		return regC176;
	}

	public void setRegC176(List<RegC176> regC176) {
		this.regC176 = regC176;
	}

	public RegC177 getRegC177() {
		return regC177;
	}

	public void setRegC177(RegC177 regC177) {
		this.regC177 = regC177;
	}

	public RegC178 getRegC178() {
		return regC178;
	}

	public void setRegC178(RegC178 regC178) {
		this.regC178 = regC178;
	}

	public RegC179 getRegC179() {
		return regC179;
	}

	public void setRegC179(RegC179 regC179) {
		this.regC179 = regC179;
	}

	public RegC180 getRegC180() {
		return regC180;
	}

	public void setRegC180(RegC180 regC180) {
		this.regC180 = regC180;
	}
	
}
