package net.cartola.emissorfiscal.documento;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class DocumentoFiscal {

	private Long id;
	private String tipo;
	private String emitenteUf;
	private String emitenteRegimeApuracao;
	private String destinatarioUf;
	private Pessoa destinatarioPessoa;
	private List<DocumentoFiscalItem> itens;
	private BigDecimal icmsBase;
	private BigDecimal icmsValor;

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

}
