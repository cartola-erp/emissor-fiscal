package net.cartola.emissorfiscal.tributacao;

import java.math.BigDecimal;

// APARENTEMENTE ESSE GRUPO DE ICMS não é USADO na AG, de qql forma A modelagem dos Campos estam aqui
public class CalculoImpostoIcms51 extends CalculoImposto {

	private static final long serialVersionUID = -6800234562794581141L;
	
	private BigDecimal aliqReducaoBase;
	private BigDecimal vlrIcmsOperacao;
	private BigDecimal aliqDiferimento;
	private BigDecimal vlrIcmsDiferido;
	
	public BigDecimal getAliqReducaoBase() {
		return aliqReducaoBase;
	}
	
	public void setAliqReducaoBase(BigDecimal aliqReducaoBase) {
		this.aliqReducaoBase = aliqReducaoBase;
	}
	
	public BigDecimal getVlrIcmsOperacao() {
		return vlrIcmsOperacao;
	}
	
	public void setVlrIcmsOperacao(BigDecimal vlrIcmsOperacao) {
		this.vlrIcmsOperacao = vlrIcmsOperacao;
	}
	
	public BigDecimal getAliqDiferimento() {
		return aliqDiferimento;
	}
	
	public void setAliqDiferimento(BigDecimal aliqDiferimento) {
		this.aliqDiferimento = aliqDiferimento;
	}
	
	public BigDecimal getVlrIcmsDiferido() {
		return vlrIcmsDiferido;
	}
	
	public void setVlrIcmsDiferido(BigDecimal vlrIcmsDiferido) {
		this.vlrIcmsDiferido = vlrIcmsDiferido;
	}
	
}
