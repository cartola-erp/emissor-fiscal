package net.cartola.emissorfiscal.documento;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class DocumentoFiscal implements Serializable {

	private static final long serialVersionUID = 250495916716488531L;

	private Long id;
	private String tipo;
	private String emitenteUf;
	private String emitenteRegimeApuracao;
	private String destinatarioUf;
	private Pessoa destinatarioPessoa;
	private List<DocumentoFiscalItem> itens;
	private BigDecimal icmsBase;
	private BigDecimal icmsValor;
	private BigDecimal pisBase;
	private BigDecimal pisValor;
	private BigDecimal cofinsBase;
	private BigDecimal cofinsValor;
	private BigDecimal ipiBase;
	private BigDecimal ipiValor;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getEmitenteUf() {
		return emitenteUf;
	}

	public void setEmitenteUf(String emitenteUf) {
		this.emitenteUf = emitenteUf;
	}

	public String getEmitenteRegimeApuracao() {
		return emitenteRegimeApuracao;
	}

	public void setEmitenteRegimeApuracao(String emitenteRegimeApuracao) {
		this.emitenteRegimeApuracao = emitenteRegimeApuracao;
	}

	public String getDestinatarioUf() {
		return destinatarioUf;
	}

	public void setDestinatarioUf(String destinatarioUf) {
		this.destinatarioUf = destinatarioUf;
	}

	public Pessoa getDestinatarioPessoa() {
		return destinatarioPessoa;
	}

	public void setDestinatarioPessoa(Pessoa destinatarioPessoa) {
		this.destinatarioPessoa = destinatarioPessoa;
	}

	@ManyToMany
	public List<DocumentoFiscalItem> getItens() {
		return itens;
	}

	public void setItens(List<DocumentoFiscalItem> itens) {
		this.itens = itens;
	}

	public BigDecimal getIcmsBase() {
		return icmsBase;
	}

	public void setIcmsBase(BigDecimal icmsBase) {
		this.icmsBase = icmsBase;
	}

	public BigDecimal getIcmsValor() {
		return icmsValor;
	}

	public void setIcmsValor(BigDecimal icmsValor) {
		this.icmsValor = icmsValor;
	}

	public BigDecimal getPisBase() {
		return pisBase;
	}

	public void setPisBase(BigDecimal pisBase) {
		this.pisBase = pisBase;
	}

	public BigDecimal getPisValor() {
		return pisValor;
	}

	public void setPisValor(BigDecimal pisValor) {
		this.pisValor = pisValor;
	}

	public BigDecimal getCofinsBase() {
		return cofinsBase;
	}

	public void setCofinsBase(BigDecimal cofinsBase) {
		this.cofinsBase = cofinsBase;
	}

	public BigDecimal getCofinsValor() {
		return cofinsValor;
	}

	public void setCofinsValor(BigDecimal cofinsValor) {
		this.cofinsValor = cofinsValor;
	}

	public BigDecimal getIpiBase() {
		return ipiBase;
	}

	public void setIpiBase(BigDecimal ipiBase) {
		this.ipiBase = ipiBase;
	}

	public BigDecimal getIpiValor() {
		return ipiValor;
	}

	public void setIpiValor(BigDecimal ipiValor) {
		this.ipiValor = ipiValor;
	}
}
