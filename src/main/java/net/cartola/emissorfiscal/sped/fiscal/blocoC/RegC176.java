package net.cartola.emissorfiscal.sped.fiscal.blocoC;

import java.math.BigDecimal;
import java.time.LocalDate;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;
import net.cartola.emissorfiscal.sped.fiscal.enums.DocumentoDeArrecadacao;
import net.cartola.emissorfiscal.sped.fiscal.enums.MotivoRessarcimento;
import net.cartola.emissorfiscal.sped.fiscal.enums.ResponsavelRetencaoIcmsSt;

/**
 * 02/09/2020
 * @author robson.costa
 */
@Record(fields = { 
	@Field(name = "reg", maxLength = 4),
    @Field(name = "codModUltE"),
    @Field(name = "numDocUltE"),
    @Field(name = "serUltE"),
    @Field(name = "dtUltE"),
    @Field(name = "codPartUltE"),
    @Field(name = "quantUltE"),
    @Field(name = "vlUnitUltE"),
    @Field(name = "vlUnitBcSt"),
    @Field(name = "chaveNfeUltmEntr"),
    @Field(name = "numItemUltEntr"),
    @Field(name = "vlrUnitBcIcmsUltEntr"),
    @Field(name = "aliqIcmsUltEntr"),
    @Field(name = "vlUnitLimiteBcIcmsUltEntr"),
    @Field(name = "vlUnitIcmsUltEntr"),
    @Field(name = "aliqStUltEntr"),
    @Field(name = "vlUnitRes"),
    @Field(name = "codRespRet"),
    @Field(name = "codMotRes"),
    @Field(name = "chaveNfeRet"),
    @Field(name = "codPartNfeRet"),
    @Field(name = "serNfeRet"),
    @Field(name = "numNfeRet"),
    @Field(name = "itemNfeRet"),
    @Field(name = "codDa"),
    @Field(name = "numDa"),
    @Field(name = "vlUnitResFcpSt")
})
public class RegC176 {
	
	private final String reg = "C176";
    private String codModUltE;
    private Long numDocUltE;
    private String serUltEntr;
    private LocalDate dtUltEntr;
    private String codPartUltEntr;
    private Double quantUltEntr;
    private BigDecimal vlUnitUltEntr;
    private BigDecimal vlUnitBcSt;
    
    // ================== ESSA PARTE EH USADO PELA AG ================== 
	private Long chaveNfeUltmEntr;
	private int numItemUltEntr;
	private BigDecimal vlrUnitBcIcmsUltEntr;
	private BigDecimal aliqIcmsUltEntr;
	private BigDecimal vlUnitLimiteBcIcmsUltEntr;
	private BigDecimal vlUnitIcmsUltEntr;
	private BigDecimal aliqStUltEntr;
	private BigDecimal vlUnitRes;
	private ResponsavelRetencaoIcmsSt codRespRet;
	private MotivoRessarcimento codMotRes;
	private Long chaveNfeRet;
	private String codPartNfeRet;
	private String serNfeRet;
	private Long numNfeRet;
	private int itemNfeRet;
	private DocumentoDeArrecadacao codDa;
	private String numDa;
	private BigDecimal vlUnitResFcpSt;
	
	public String getReg() {
		return reg;
	}

	public String getCodModUltE() {
		return codModUltE;
	}

	public void setCodModUltE(String codModUltE) {
		this.codModUltE = codModUltE;
	}

	public Long getNumDocUltE() {
		return numDocUltE;
	}

	public void setNumDocUltE(Long numDocUltE) {
		this.numDocUltE = numDocUltE;
	}

	public String getSerUltEntr() {
		return serUltEntr;
	}

	public void setSerUltEntr(String serUltEntr) {
		this.serUltEntr = serUltEntr;
	}

	public LocalDate getDtUltEntr() {
		return dtUltEntr;
	}

	public void setDtUltEntr(LocalDate dtUltEntr) {
		this.dtUltEntr = dtUltEntr;
	}

	public String getCodPartUltEntr() {
		return codPartUltEntr;
	}

	public void setCodPartUltEntr(String codPartUltEntr) {
		this.codPartUltEntr = codPartUltEntr;
	}

	public Double getQuantUltEntr() {
		return quantUltEntr;
	}

	public void setQuantUltEntr(Double quantUltEntr) {
		this.quantUltEntr = quantUltEntr;
	}

	public BigDecimal getVlUnitUltEntr() {
		return vlUnitUltEntr;
	}

	public void setVlUnitUltEntr(BigDecimal vlUnitUltEntr) {
		this.vlUnitUltEntr = vlUnitUltEntr;
	}

	public BigDecimal getVlUnitBcSt() {
		return vlUnitBcSt;
	}

	public void setVlUnitBcSt(BigDecimal vlUnitBcSt) {
		this.vlUnitBcSt = vlUnitBcSt;
	}

	public Long getChaveNfeUltmEntr() {
		return chaveNfeUltmEntr;
	}

	public void setChaveNfeUltmEntr(Long chaveNfeUltmEntr) {
		this.chaveNfeUltmEntr = chaveNfeUltmEntr;
	}

	public int getNumItemUltEntr() {
		return numItemUltEntr;
	}

	public void setNumItemUltEntr(int numItemUltEntr) {
		this.numItemUltEntr = numItemUltEntr;
	}

	public BigDecimal getVlrUnitBcIcmsUltEntr() {
		return vlrUnitBcIcmsUltEntr;
	}

	public void setVlrUnitBcIcmsUltEntr(BigDecimal vlrUnitBcIcmsUltEntr) {
		this.vlrUnitBcIcmsUltEntr = vlrUnitBcIcmsUltEntr;
	}

	public BigDecimal getAliqIcmsUltEntr() {
		return aliqIcmsUltEntr;
	}

	public void setAliqIcmsUltEntr(BigDecimal aliqIcmsUltEntr) {
		this.aliqIcmsUltEntr = aliqIcmsUltEntr;
	}

	public BigDecimal getVlUnitLimiteBcIcmsUltEntr() {
		return vlUnitLimiteBcIcmsUltEntr;
	}

	public void setVlUnitLimiteBcIcmsUltEntr(BigDecimal vlUnitLimiteBcIcmsUltEntr) {
		this.vlUnitLimiteBcIcmsUltEntr = vlUnitLimiteBcIcmsUltEntr;
	}

	public BigDecimal getVlUnitIcmsUltEntr() {
		return vlUnitIcmsUltEntr;
	}

	public void setVlUnitIcmsUltEntr(BigDecimal vlUnitIcmsUltEntr) {
		this.vlUnitIcmsUltEntr = vlUnitIcmsUltEntr;
	}

	public BigDecimal getAliqStUltEntr() {
		return aliqStUltEntr;
	}

	public void setAliqStUltEntr(BigDecimal aliqStUltEntr) {
		this.aliqStUltEntr = aliqStUltEntr;
	}

	public BigDecimal getVlUnitRes() {
		return vlUnitRes;
	}

	public void setVlUnitRes(BigDecimal vlUnitRes) {
		this.vlUnitRes = vlUnitRes;
	}

	public ResponsavelRetencaoIcmsSt getCodRespRet() {
		return codRespRet;
	}

	public void setCodRespRet(ResponsavelRetencaoIcmsSt codRespRet) {
		this.codRespRet = codRespRet;
	}

	public MotivoRessarcimento getCodMotRes() {
		return codMotRes;
	}

	public void setCodMotRes(MotivoRessarcimento codMotRes) {
		this.codMotRes = codMotRes;
	}

	public Long getChaveNfeRet() {
		return chaveNfeRet;
	}

	public void setChaveNfeRet(Long chaveNfeRet) {
		this.chaveNfeRet = chaveNfeRet;
	}

	public String getCodPartNfeRet() {
		return codPartNfeRet;
	}

	public void setCodPartNfeRet(String codPartNfeRet) {
		this.codPartNfeRet = codPartNfeRet;
	}

	public String getSerNfeRet() {
		return serNfeRet;
	}

	public void setSerNfeRet(String serNfeRet) {
		this.serNfeRet = serNfeRet;
	}

	public Long getNumNfeRet() {
		return numNfeRet;
	}

	public void setNumNfeRet(Long numNfeRet) {
		this.numNfeRet = numNfeRet;
	}

	public int getItemNfeRet() {
		return itemNfeRet;
	}

	public void setItemNfeRet(int itemNfeRet) {
		this.itemNfeRet = itemNfeRet;
	}

	public DocumentoDeArrecadacao getCodDa() {
		return codDa;
	}

	public void setCodDa(DocumentoDeArrecadacao codDa) {
		this.codDa = codDa;
	}

	public String getNumDa() {
		return numDa;
	}

	public void setNumDa(String numDa) {
		this.numDa = numDa;
	}

	public BigDecimal getVlUnitResFcpSt() {
		return vlUnitResFcpSt;
	}

	public void setVlUnitResFcpSt(BigDecimal vlUnitResFcpSt) {
		this.vlUnitResFcpSt = vlUnitResFcpSt;
	}
	
}
