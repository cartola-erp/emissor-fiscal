package net.cartola.emissorfiscal.sped.fiscal.blocoC;

import java.math.BigDecimal;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;
import net.cartola.emissorfiscal.sped.fiscal.enums.DocumentoDeArrecadacao;
import net.cartola.emissorfiscal.sped.fiscal.enums.ResponsavelRetencaoIcmsSt;

/**
 * 02/09/2020
 * @author robson.costa
 */
@Record(fields = { 
	@Field(name = "reg", maxLength = 4),
	@Field(name = "codRespRet"),
	@Field(name = "quantConv"),
	@Field(name = "unid"),
	@Field(name = "vlUnitConv"),
	@Field(name = "vlUnitIcmsOpConv"),
	@Field(name = "vlUnitBcIcmsStConv"),
	@Field(name = "vlUnitIcmsStConv"),
	@Field(name = "vlUnitFcpStConv"),
	@Field(name = "codDa"),
	@Field(name = "numDa")
})
public class RegC180 {
	
	private final String reg = "C180";
	private ResponsavelRetencaoIcmsSt codRespRet;
	private Double quantConv;
	private String unid;
	private BigDecimal vlUnitConv;
	private BigDecimal vlUnitIcmsOpConv;
	private BigDecimal vlUnitBcIcmsStConv;
	private BigDecimal vlUnitIcmsStConv;
	private BigDecimal vlUnitFcpStConv;
	private DocumentoDeArrecadacao codDa;
	private String numDa;
	
	public String getReg() {
		return reg;
	}

	public ResponsavelRetencaoIcmsSt getCodRespRet() {
		return codRespRet;
	}

	public void setCodRespRet(ResponsavelRetencaoIcmsSt codRespRet) {
		this.codRespRet = codRespRet;
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

	public BigDecimal getVlUnitIcmsOpConv() {
		return vlUnitIcmsOpConv;
	}

	public void setVlUnitIcmsOpConv(BigDecimal vlUnitIcmsOpConv) {
		this.vlUnitIcmsOpConv = vlUnitIcmsOpConv;
	}

	public BigDecimal getVlUnitBcIcmsStConv() {
		return vlUnitBcIcmsStConv;
	}

	public void setVlUnitBcIcmsStConv(BigDecimal vlUnitBcIcmsStConv) {
		this.vlUnitBcIcmsStConv = vlUnitBcIcmsStConv;
	}

	public BigDecimal getVlUnitIcmsStConv() {
		return vlUnitIcmsStConv;
	}

	public void setVlUnitIcmsStConv(BigDecimal vlUnitIcmsStConv) {
		this.vlUnitIcmsStConv = vlUnitIcmsStConv;
	}

	public BigDecimal getVlUnitFcpStConv() {
		return vlUnitFcpStConv;
	}

	public void setVlUnitFcpStConv(BigDecimal vlUnitFcpStConv) {
		this.vlUnitFcpStConv = vlUnitFcpStConv;
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

}
