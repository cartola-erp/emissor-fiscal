package net.cartola.emissorfiscal.documento;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import net.cartola.emissorfiscal.operacao.Operacao;
import net.cartola.emissorfiscal.pessoa.Pessoa;

public class DocumentoFiscalDto implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Operacao operacao;
	private String tipo;
	private Long serie;
	private Long numero;
	private Pessoa emitente;
	private Pessoa destinatario;
	private List<DocumentoFiscalItemDto> itens;
	private BigDecimal icmsBase;
	private BigDecimal icmsValor;
	private BigDecimal pisBase;
	private BigDecimal pisValor;
	private BigDecimal cofinsBase;
	private BigDecimal cofinsValor;
	private BigDecimal ipiBase;
	private BigDecimal ipiValor;
	
	
	public Long getId() {
		return id;
	}
	public Operacao getOperacao() {
		return operacao;
	}
	public String getTipo() {
		return tipo;
	}
	public Long getSerie() {
		return serie;
	}
	public Long getNumero() {
		return numero;
	}
	public Pessoa getEmitente() {
		return emitente;
	}
	public Pessoa getDestinatario() {
		return destinatario;
	}
	public List<DocumentoFiscalItemDto> getItens() {
		return itens;
	}
	public BigDecimal getIcmsBase() {
		return icmsBase;
	}
	public BigDecimal getIcmsValor() {
		return icmsValor;
	}
	public BigDecimal getPisBase() {
		return pisBase;
	}
	public BigDecimal getPisValor() {
		return pisValor;
	}
	public BigDecimal getCofinsBase() {
		return cofinsBase;
	}
	public BigDecimal getCofinsValor() {
		return cofinsValor;
	}
	public BigDecimal getIpiBase() {
		return ipiBase;
	}
	public BigDecimal getIpiValor() {
		return ipiValor;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setOperacao(Operacao operacao) {
		this.operacao = operacao;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public void setSerie(Long serie) {
		this.serie = serie;
	}
	public void setNumero(Long numero) {
		this.numero = numero;
	}
	public void setEmitente(Pessoa emitente) {
		this.emitente = emitente;
	}
	public void setDestinatario(Pessoa destinatario) {
		this.destinatario = destinatario;
	}
	public void setItens(List<DocumentoFiscalItemDto> itens) {
		this.itens = itens;
	}
	public void setIcmsBase(BigDecimal icmsBase) {
		this.icmsBase = icmsBase;
	}
	public void setIcmsValor(BigDecimal icmsValor) {
		this.icmsValor = icmsValor;
	}
	public void setPisBase(BigDecimal pisBase) {
		this.pisBase = pisBase;
	}
	public void setPisValor(BigDecimal pisValor) {
		this.pisValor = pisValor;
	}
	public void setCofinsBase(BigDecimal cofinsBase) {
		this.cofinsBase = cofinsBase;
	}
	public void setCofinsValor(BigDecimal cofinsValor) {
		this.cofinsValor = cofinsValor;
	}
	public void setIpiBase(BigDecimal ipiBase) {
		this.ipiBase = ipiBase;
	}
	public void setIpiValor(BigDecimal ipiValor) {
		this.ipiValor = ipiValor;
	}

}
