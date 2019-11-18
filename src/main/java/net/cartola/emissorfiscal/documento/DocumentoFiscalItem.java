package net.cartola.emissorfiscal.documento;

import java.io.Serializable;
import java.math.BigDecimal;

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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import net.cartola.emissorfiscal.ncm.Ncm;

@Entity
@Table(name = "docu_fisc_item")
public class DocumentoFiscalItem implements Serializable {

	private static final long serialVersionUID = -3885752189101767947L;

	private static final String FINALIDADE_OBRIGATORIA = "Atenção! A finalidade do item é obrigatória!!";
	private static final String QUANTIDADE_OBRIGATORIA = "Atenção! A quantidade do item é obrigatória!!";
	private static final String QUANTIDADE_INVALIDA = "Atenção! A quantidade inserida é inválida!!";
	private static final String VALOR_OBRIGATORIO = "Atenção! O valor do item é obrigatório!!";
	private static final String VALOR_INVALIDO = "Atenção! O valor inserida é inválido!!";

	private Long id;
	private Finalidade finalidade = Finalidade.CONSUMO;
	private BigDecimal quantidade;
	private BigDecimal valorUnitario;
	private Ncm ncm;
	private int cfop;
	private BigDecimal icmsBase;
	private BigDecimal icmsAliquota;
	private BigDecimal icmsValor;
	private BigDecimal pisBase;
	private BigDecimal pisAliquota;
	private BigDecimal pisValor;
	private BigDecimal cofinsBase;
	private BigDecimal cofinsAliquota;
	private BigDecimal cofinsValor;
	private BigDecimal ipiBase;
	private BigDecimal ipiAliquota;
	private BigDecimal ipiValor;

	private DocumentoFiscal documentoFiscal;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@NotNull(message = FINALIDADE_OBRIGATORIA)
	@Enumerated(EnumType.STRING)
	public Finalidade getFinalidade() {
		return finalidade;
	}

	public void setFinalidade(Finalidade finalidade) {
		this.finalidade = finalidade;
	}

	@NotNull(message = QUANTIDADE_OBRIGATORIA)
	@Positive(message = QUANTIDADE_INVALIDA)
	public BigDecimal getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}

	@NotNull(message = VALOR_OBRIGATORIO)
	@Positive(message = VALOR_INVALIDO)
	public BigDecimal getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(BigDecimal valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ncm_id", referencedColumnName = "ncm_id", nullable = false, foreignKey = @ForeignKey(foreignKeyDefinition = "fnk_ncms"))
	public Ncm getNcm() {
		return ncm;
	}

	public void setNcm(Ncm ncm) {
		this.ncm = ncm;
	}

	public int getCfop() {
		return cfop;
	}

	public void setCfop(int cfop) {
		this.cfop = cfop;
	}

	public BigDecimal getIcmsBase() {
		return icmsBase;
	}

	public void setIcmsBase(BigDecimal icmsBase) {
		this.icmsBase = icmsBase;
	}

	public BigDecimal getIcmsAliquota() {
		return icmsAliquota;
	}

	public void setIcmsAliquota(BigDecimal icmsAliquota) {
		this.icmsAliquota = icmsAliquota;
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

	public BigDecimal getPisAliquota() {
		return pisAliquota;
	}

	public void setPisAliquota(BigDecimal pisAliquota) {
		this.pisAliquota = pisAliquota;
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

	public BigDecimal getCofinsAliquota() {
		return cofinsAliquota;
	}

	public void setCofinsAliquota(BigDecimal cofinsAliquota) {
		this.cofinsAliquota = cofinsAliquota;
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

	public BigDecimal getIpiAliquota() {
		return ipiAliquota;
	}

	public void setIpiAliquota(BigDecimal ipiAliquota) {
		this.ipiAliquota = ipiAliquota;
	}

	public BigDecimal getIpiValor() {
		return ipiValor;
	}

	public void setIpiValor(BigDecimal ipiValor) {
		this.ipiValor = ipiValor;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "docu_fisc_id", unique = false, foreignKey = @ForeignKey(name = "fnk_documento_fiscal"))
	public DocumentoFiscal getDocumentoFiscal() {
		return documentoFiscal;
	}

	public void setDocumentoFiscal(DocumentoFiscal documentoFiscal) {
		this.documentoFiscal = documentoFiscal;
	}
}
