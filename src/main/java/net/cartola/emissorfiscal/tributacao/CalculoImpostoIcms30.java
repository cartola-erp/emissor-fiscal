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

	
//	 * A autogeral só utiliza o FCP normal
//	private CalculoImpostoFcpSt calcFcpSt = new CalculoImpostoFcpSt();

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

}
