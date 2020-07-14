package net.cartola.emissorfiscal.tributacao;

import java.math.BigDecimal;

public class CalculoImpostoIcms60 extends CalculoImposto {

	private static final long serialVersionUID = 3421434571361563907L;
	
	private BigDecimal vlrBaseCalcIcmsStRetido;
	private BigDecimal vlrIcmsStRetido;
	private BigDecimal aliquotaPst;		// = ALIQUOTA ICMS_ST + FCP
	// QUE EU me lembro é a msm coisa do campo "valor" da super class
//	private BigDecimal vlrIcmsSubstituto 
	private BigDecimal vlrBaseCalcFcpStRetido;		
	private BigDecimal aliqFcpStRetido;
	private BigDecimal vlrFcpStRetido;
	private BigDecimal aliqReducaoBaseEfet;
	// Aparentemente esses valores abaixo são os mesmos que: baseDeCalculo, aliquota e Valor da superClass
	private BigDecimal vlrBaseEfetiva;
	private BigDecimal aliqIcmsEfetiva;
	private BigDecimal vlrIcmsEfetivo;
	
	public BigDecimal getAliqFcpStRetido() {
		return aliqFcpStRetido;
	}
	
	public void setAliqFcpStRetido(BigDecimal aliqFcpStRetido) {
		this.aliqFcpStRetido = aliqFcpStRetido;
	}

	public BigDecimal getVlrBaseCalcIcmsStRetido() {
		return vlrBaseCalcIcmsStRetido;
	}

	public void setVlrBaseCalcIcmsStRetido(BigDecimal vlrBaseCalcIcmsStRetido) {
		this.vlrBaseCalcIcmsStRetido = vlrBaseCalcIcmsStRetido;
	}

	public BigDecimal getVlrIcmsStRetido() {
		return vlrIcmsStRetido;
	}

	public void setVlrIcmsStRetido(BigDecimal vlrIcmsStRetido) {
		this.vlrIcmsStRetido = vlrIcmsStRetido;
	}

	public BigDecimal getAliquotaPst() {
		return aliquotaPst;
	}

	public void setAliquotaPst(BigDecimal aliquotaPst) {
		this.aliquotaPst = aliquotaPst;
	}

	public BigDecimal getVlrBaseCalcFcpStRetido() {
		return vlrBaseCalcFcpStRetido;
	}

	public void setVlrBaseCalcFcpStRetido(BigDecimal vlrBaseCalcFcpStRetido) {
		this.vlrBaseCalcFcpStRetido = vlrBaseCalcFcpStRetido;
	}

	public BigDecimal getVlrFcpStRetido() {
		return vlrFcpStRetido;
	}

	public void setVlrFcpStRetido(BigDecimal vlrFcpStRetido) {
		this.vlrFcpStRetido = vlrFcpStRetido;
	}

	public BigDecimal getAliqReducaoBaseEfet() {
		return aliqReducaoBaseEfet;
	}

	public void setAliqReducaoBaseEfet(BigDecimal aliqReducaoBaseEfet) {
		this.aliqReducaoBaseEfet = aliqReducaoBaseEfet;
	}

	public BigDecimal getVlrBaseEfetiva() {
		return vlrBaseEfetiva;
	}

	public void setVlrBaseEfetiva(BigDecimal vlrBaseEfetiva) {
		this.vlrBaseEfetiva = vlrBaseEfetiva;
	}

	public BigDecimal getAliqIcmsEfetiva() {
		return aliqIcmsEfetiva;
	}

	public void setAliqIcmsEfetiva(BigDecimal aliqIcmsEfetiva) {
		this.aliqIcmsEfetiva = aliqIcmsEfetiva;
	}

	public BigDecimal getVlrIcmsEfetivo() {
		return vlrIcmsEfetivo;
	}

	public void setVlrIcmsEfetivo(BigDecimal vlrIcmsEfetivo) {
		this.vlrIcmsEfetivo = vlrIcmsEfetivo;
	}
	
	
}
