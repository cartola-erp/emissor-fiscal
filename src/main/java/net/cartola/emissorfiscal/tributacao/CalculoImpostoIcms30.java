package net.cartola.emissorfiscal.tributacao;

import java.math.BigDecimal;

public class CalculoImpostoIcms30 extends CalculoImposto {

	private static final long serialVersionUID = -6800234562794581141L;
	
	private String modalidadeDaBaseCalculoSt;
	private BigDecimal iva;
	private BigDecimal aliqReducaoBaseSt;

	// =============== PARA ESSAS TRÊS PROPRIEDADES, Eu posso UTILIZAR o da classe CalculoImposto =============== 
//	private BigDecimal baseDeCalculoSt;			// baseDeCalculo
//	private BigDecimal aliquotaIcmsSt;			// aliquota
//	private BigDecimal valorIcmsSt;				// valor
	
	private BigDecimal vlrBaseFcpSt;
	private BigDecimal aliquotaFcpSt;
	private BigDecimal valorFcpSt;

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

	public BigDecimal getVlrBaseFcpSt() {
		return vlrBaseFcpSt;
	}

	public void setVlrBaseFcpSt(BigDecimal vlrBaseFcpSt) {
		this.vlrBaseFcpSt = vlrBaseFcpSt;
	}

	public BigDecimal getAliquotaFcpSt() {
		return aliquotaFcpSt;
	}

	public void setAliquotaFcpSt(BigDecimal aliquotaFcpSt) {
		this.aliquotaFcpSt = aliquotaFcpSt;
	}

	public BigDecimal getValorFcpSt() {
		return valorFcpSt;
	}

	public void setValorFcpSt(BigDecimal valorFcpSt) {
		this.valorFcpSt = valorFcpSt;
	}
	
}
