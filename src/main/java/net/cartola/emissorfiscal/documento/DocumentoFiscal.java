package net.cartola.emissorfiscal.documento;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import net.cartola.emissorfiscal.estado.EstadoSigla;
import net.cartola.emissorfiscal.operacao.Operacao;

@Entity
@Table(name = "docu_fisc")
public class DocumentoFiscal implements Serializable {

	private static final long serialVersionUID = 250495916716488531L;

	private static final String ESTADO_EMITENTE_OBRIGATORIO = "Atenção!! A informação referente a estado emitente é obrigatória!";
	private static final String ESTADO_DESTINATARIO_OBRIGATORIO = "Atenção!! A informação referente a estado destinatário é obrigatória!";
	private static final String PESSOA_TIPO_DESTINATARIO_OBRIGATORIO = "Atenção!! A informação referente a ao tipo de pessoa (Física ou Jurídica) é obrigatória!";

	private Long id;
	private Operacao operacao;
	private String tipo;
	private EstadoSigla emitenteUf = EstadoSigla.SP;
	private String emitenteRegimeApuracao;
	private EstadoSigla destinatarioUf = EstadoSigla.SP;
	private Pessoa destinatarioPessoa = Pessoa.FISICA;
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

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "operacao_id", referencedColumnName = "oper_id")
	public Operacao getOperacao() {
		return operacao;
	}

	public void setOperacao(Operacao operacao) {
		this.operacao = operacao;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@NotNull(message = ESTADO_EMITENTE_OBRIGATORIO)
	@Enumerated(EnumType.STRING)
	public EstadoSigla getEmitenteUf() {
		return emitenteUf;
	}

	public void setEmitenteUf(EstadoSigla emitenteUf) {
		this.emitenteUf = emitenteUf;
	}

	public String getEmitenteRegimeApuracao() {
		return emitenteRegimeApuracao;
	}

	public void setEmitenteRegimeApuracao(String emitenteRegimeApuracao) {
		this.emitenteRegimeApuracao = emitenteRegimeApuracao;
	}

	@NotNull(message = ESTADO_DESTINATARIO_OBRIGATORIO)
	@Enumerated(EnumType.STRING)
	public EstadoSigla getDestinatarioUf() {
		return destinatarioUf;
	}

	public void setDestinatarioUf(EstadoSigla destinatarioUf) {
		this.destinatarioUf = destinatarioUf;
	}

	@NotNull(message = PESSOA_TIPO_DESTINATARIO_OBRIGATORIO)
	@Enumerated(EnumType.STRING)
	public Pessoa getDestinatarioPessoa() {
		return destinatarioPessoa;
	}

	public void setDestinatarioPessoa(Pessoa destinatarioPessoa) {
		this.destinatarioPessoa = destinatarioPessoa;
	}

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "doc_fiscal_id", unique = false, foreignKey = @ForeignKey(name = "fnk_documento_fiscal_item"))
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
