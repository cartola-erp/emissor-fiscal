package net.cartola.emissorfiscal.tributacao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class CalculoImposto implements Serializable {

	private static final long serialVersionUID = 1969652305638170032L;

	protected Imposto imposto;
	protected BigDecimal valorUnitario;
	protected int ordem;
	protected BigDecimal quantidade;
	protected BigDecimal baseDeCalculo;
	protected BigDecimal aliquota;
	protected BigDecimal valor;
	
    // Usado no calculo de ICMS
	private String modalidadeDaBaseCalculo;

	private Map<MensagemTipo, List<String>> mensagens;

	public Imposto getImposto() {
		return imposto;
	}

	public void setImposto(Imposto imposto) {
		this.imposto = imposto;
	}

	public BigDecimal getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(BigDecimal valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	public int getOrdem() {
		return ordem;
	}

	public void setOrdem(int ordem) {
		this.ordem = ordem;
	}

	public BigDecimal getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getBaseDeCalculo() {
		return baseDeCalculo;
	}

	public void setBaseDeCalculo(BigDecimal baseDeCalculo) {
		this.baseDeCalculo = baseDeCalculo;
	}

	public BigDecimal getAliquota() {
		return aliquota;
	}

	public void setAliquota(BigDecimal aliquota) {
		this.aliquota = aliquota;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public String getModalidadeDaBaseCalculo() {
		return modalidadeDaBaseCalculo;
	}

	public void setModalidadeDaBaseCalculo(String modalidadeDaBaseCalculo) {
		this.modalidadeDaBaseCalculo = modalidadeDaBaseCalculo;
	}

	public Map<MensagemTipo, List<String>> getMensagens() {
		return mensagens;
	}

	public void setMensagens(Map<MensagemTipo, List<String>> mensagens) {
		this.mensagens = mensagens;
	}
}
