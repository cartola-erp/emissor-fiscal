package net.cartola.emissorfiscal.tributacao;

import java.math.BigDecimal;

public class CalculoImpostoIcms70 extends CalculoImpostoFcp {

	private static final long serialVersionUID = 3421434571361563907L;
	
	private BigDecimal aliqReducaoBase;

	private String modalidadeDaBaseCalculoSt;
	private BigDecimal iva;
	private BigDecimal aliqReducaoBaseSt;
	
	private BigDecimal vlrBaseCalculoSt;
	private BigDecimal aliquotaIcmsSt;
	private BigDecimal vlrIcmsSt;

	private BigDecimal vlrBaseCalcFcpSt;
	private BigDecimal fcpStAliquota;
	private BigDecimal valorFcpSt;
	
	public BigDecimal getAliqReducaoBase() {
		return aliqReducaoBase;
	}
	
	public void setAliqReducaoBase(BigDecimal aliqReducaoBase) {
		this.aliqReducaoBase = aliqReducaoBase;
	}

	public String getModalidadeDaBaseCalculoSt() {
		return modalidadeDaBaseCalculoSt;
	}

	public void setModalidadeDaBaseCalculoSt(String modalidadeDaBaseCalculoSt) {
		this.modalidadeDaBaseCalculoSt = modalidadeDaBaseCalculoSt;
	}

	public BigDecimal getIva() {
		return iva;
	}

	public void setIva(BigDecimal iva) {
		this.iva = iva;
	}

	public BigDecimal getAliqReducaoBaseSt() {
		return aliqReducaoBaseSt;
	}

	public void setAliqReducaoBaseSt(BigDecimal aliqReducaoBaseSt) {
		this.aliqReducaoBaseSt = aliqReducaoBaseSt;
	}

	public BigDecimal getVlrBaseCalculoSt() {
		return vlrBaseCalculoSt;
	}

	public void setVlrBaseCalculoSt(BigDecimal vlrBaseCalculoSt) {
		this.vlrBaseCalculoSt = vlrBaseCalculoSt;
	}

	public BigDecimal getAliquotaIcmsSt() {
		return aliquotaIcmsSt;
	}

	public void setAliquotaIcmsSt(BigDecimal aliquotaIcmsSt) {
		this.aliquotaIcmsSt = aliquotaIcmsSt;
	}

	public BigDecimal getVlrIcmsSt() {
		return vlrIcmsSt;
	}

	public void setVlrIcmsSt(BigDecimal vlrIcmsSt) {
		this.vlrIcmsSt = vlrIcmsSt;
	}

	public BigDecimal getVlrBaseCalcFcpSt() {
		return vlrBaseCalcFcpSt;
	}

	public void setVlrBaseCalcFcpSt(BigDecimal vlrBaseCalcFcpSt) {
		this.vlrBaseCalcFcpSt = vlrBaseCalcFcpSt;
	}

	public BigDecimal getFcpStAliquota() {
		return fcpStAliquota;
	}

	public void setFcpStAliquota(BigDecimal fcpStAliquota) {
		this.fcpStAliquota = fcpStAliquota;
	}

	public BigDecimal getValorFcpSt() {
		return valorFcpSt;
	}

	public void setValorFcpSt(BigDecimal valorFcpSt) {
		this.valorFcpSt = valorFcpSt;
	}
	
	
}
