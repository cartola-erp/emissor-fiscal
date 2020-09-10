package net.cartola.emissorfiscal.sped.fiscal.blocoC;

import java.math.BigDecimal;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;

/**
 * 02/09/2020
 * @author robson.costa
 * 
 * REGISTRO C815: INFORMAÇÕES COMPLEMENTARES DAS OPERAÇÕES DE SAÍDA DE MERCADORIAS
 * SUJEITAS À SUBSTITUIÇÃO TRIBUTÁRIA (CF-E-SAT) (CÓDIGO 59)
 * 
 */
@Record(fields = { 
	@Field(name = "reg", maxLength = 4),
	@Field(name = "codMotRestCompl"),
	@Field(name = "quantConv"),
	@Field(name = "unid"),
	@Field(name = "vlUnitConv"),
	@Field(name = "vlUnitIcmsNaOperacaoConv"),
	@Field(name = "vlUnitIcmsOpConv"),
	@Field(name = "vlUnitIcmsOpEstoqueConv"),
	@Field(name = "vlUnitIcmsStEstoqueConv"),
	@Field(name = "vlUnitFcpIcmsStEstoqueConv"),
	@Field(name = "vlUnitIcmsStConvRest"),
	@Field(name = "vlUnitFcpStConvRest"),
	@Field(name = "vlUnitIcmsStConvCompl"),
	@Field(name = "vlUnitFcpStConvCompl")
})
public class RegC815 {
	
	private final String reg = "C815";
	private String codMotRestCompl;
	private Double quantConv;
	private String unid;
	private BigDecimal vlUnitConv;
	private BigDecimal vlUnitIcmsNaOperacaoConv;
	private BigDecimal vlUnitIcmsOpConv;
	
	private BigDecimal vlUnitIcmsOpEstoqueConv;
	private BigDecimal vlUnitIcmsStEstoqueConv;
	private BigDecimal vlUnitFcpIcmsStEstoqueConv;
	private BigDecimal vlUnitIcmsStConvRest;
	private BigDecimal vlUnitFcpStConvRest;
	private BigDecimal vlUnitIcmsStConvCompl;
	private BigDecimal vlUnitFcpStConvCompl;
	
	public String getReg() {
		return reg;
	}

	public String getCodMotRestCompl() {
		return codMotRestCompl;
	}

	public void setCodMotRestCompl(String codMotRestCompl) {
		this.codMotRestCompl = codMotRestCompl;
	}

	public Double getQuantConv() {
		return quantConv;
	}

	public void setQuantConv(Double quantConv) {
		this.quantConv = quantConv;
	}

	public String getUnid() {
		return unid;
	}

	public void setUnid(String unid) {
		this.unid = unid;
	}

	public BigDecimal getVlUnitConv() {
		return vlUnitConv;
	}

	public void setVlUnitConv(BigDecimal vlUnitConv) {
		this.vlUnitConv = vlUnitConv;
	}

	public BigDecimal getVlUnitIcmsNaOperacaoConv() {
		return vlUnitIcmsNaOperacaoConv;
	}

	public void setVlUnitIcmsNaOperacaoConv(BigDecimal vlUnitIcmsNaOperacaoConv) {
		this.vlUnitIcmsNaOperacaoConv = vlUnitIcmsNaOperacaoConv;
	}

	public BigDecimal getVlUnitIcmsOpConv() {
		return vlUnitIcmsOpConv;
	}

	public void setVlUnitIcmsOpConv(BigDecimal vlUnitIcmsOpConv) {
		this.vlUnitIcmsOpConv = vlUnitIcmsOpConv;
	}

	public BigDecimal getVlUnitIcmsOpEstoqueConv() {
		return vlUnitIcmsOpEstoqueConv;
	}

	public void setVlUnitIcmsOpEstoqueConv(BigDecimal vlUnitIcmsOpEstoqueConv) {
		this.vlUnitIcmsOpEstoqueConv = vlUnitIcmsOpEstoqueConv;
	}

	public BigDecimal getVlUnitIcmsStEstoqueConv() {
		return vlUnitIcmsStEstoqueConv;
	}

	public void setVlUnitIcmsStEstoqueConv(BigDecimal vlUnitIcmsStEstoqueConv) {
		this.vlUnitIcmsStEstoqueConv = vlUnitIcmsStEstoqueConv;
	}

	public BigDecimal getVlUnitFcpIcmsStEstoqueConv() {
		return vlUnitFcpIcmsStEstoqueConv;
	}

	public void setVlUnitFcpIcmsStEstoqueConv(BigDecimal vlUnitFcpIcmsStEstoqueConv) {
		this.vlUnitFcpIcmsStEstoqueConv = vlUnitFcpIcmsStEstoqueConv;
	}

	public BigDecimal getVlUnitIcmsStConvRest() {
		return vlUnitIcmsStConvRest;
	}

	public void setVlUnitIcmsStConvRest(BigDecimal vlUnitIcmsStConvRest) {
		this.vlUnitIcmsStConvRest = vlUnitIcmsStConvRest;
	}

	public BigDecimal getVlUnitFcpStConvRest() {
		return vlUnitFcpStConvRest;
	}

	public void setVlUnitFcpStConvRest(BigDecimal vlUnitFcpStConvRest) {
		this.vlUnitFcpStConvRest = vlUnitFcpStConvRest;
	}

	public BigDecimal getVlUnitIcmsStConvCompl() {
		return vlUnitIcmsStConvCompl;
	}

	public void setVlUnitIcmsStConvCompl(BigDecimal vlUnitIcmsStConvCompl) {
		this.vlUnitIcmsStConvCompl = vlUnitIcmsStConvCompl;
	}

	public BigDecimal getVlUnitFcpStConvCompl() {
		return vlUnitFcpStConvCompl;
	}

	public void setVlUnitFcpStConvCompl(BigDecimal vlUnitFcpStConvCompl) {
		this.vlUnitFcpStConvCompl = vlUnitFcpStConvCompl;
	}
	
}
