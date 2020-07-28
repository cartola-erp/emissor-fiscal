package net.cartola.emissorfiscal.tributacao;

import java.io.Serializable;
import java.math.BigDecimal;

public class CalculoImpostoIcmsSt implements Serializable{

	private static final long serialVersionUID = 6039396079528807477L;

	private BigDecimal baseDeCalculoSt;
	private BigDecimal aliquotaIcmsSt;
	private BigDecimal valorIcmsSt;
	private BigDecimal aliqReducaoBaseSt;
	private String modalidadeDaBaseCalculoSt;
	
	public BigDecimal getBaseDeCalculoSt() {
		return baseDeCalculoSt;
	}
	
	public void setBaseDeCalculoSt(BigDecimal baseDeCalculoSt) {
		this.baseDeCalculoSt = baseDeCalculoSt;
	}

	public BigDecimal getAliquotaIcmsSt() {
		return aliquotaIcmsSt;
	}

	public void setAliquotaIcmsSt(BigDecimal aliquotaIcmsSt) {
		this.aliquotaIcmsSt = aliquotaIcmsSt;
	}

	public BigDecimal getValorIcmsSt() {
		return valorIcmsSt;
	}

	public void setValorIcmsSt(BigDecimal valorIcmsSt) {
		this.valorIcmsSt = valorIcmsSt;
	}

	public BigDecimal getAliqReducaoBaseSt() {
		return aliqReducaoBaseSt;
	}

	public void setAliqReducaoBaseSt(BigDecimal aliqReducaoBaseSt) {
		this.aliqReducaoBaseSt = aliqReducaoBaseSt;
	}

	public String getModalidadeDaBaseCalculoSt() {
		return modalidadeDaBaseCalculoSt;
	}

	public void setModalidadeDaBaseCalculoSt(String modalidadeDaBaseCalculoSt) {
		this.modalidadeDaBaseCalculoSt = modalidadeDaBaseCalculoSt;
	}
	
}
