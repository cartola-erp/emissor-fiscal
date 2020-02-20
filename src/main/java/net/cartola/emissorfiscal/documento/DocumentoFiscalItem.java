package net.cartola.emissorfiscal.documento;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import net.cartola.emissorfiscal.ncm.Ncm;

@Entity
@Table(name = "docu_fisc_item")
@JsonIgnoreProperties(value = { "documentoFiscal" })
public class DocumentoFiscalItem implements Serializable {

	private static final long serialVersionUID = -3885752189101767947L;

	private static final String FINALIDADE_OBRIGATORIA = "Atenção! A finalidade do item é obrigatória!!";
	private static final String PRODUTO_ORIGEM_OBRIGATORIA = "Atenção! A origem do produto é obrigatória!!";
	private static final String QUANTIDADE_OBRIGATORIA = "Atenção! A quantidade do item é obrigatória!!";
	private static final String QUANTIDADE_INVALIDA = "Atenção! A quantidade inserida é inválida!!";
	private static final String VALOR_OBRIGATORIO = "Atenção! O valor do item é obrigatório!!";
	private static final String VALOR_INVALIDO = "Atenção! O valor inserida é inválido!!";

	private Long id;
	private Finalidade finalidade = Finalidade.CONSUMO;
	private ProdutoOrigem origem = ProdutoOrigem.NACIONAL;
	private BigDecimal quantidade;
	private BigDecimal valorUnitario;
	private Ncm ncm;
	private int cfop;
	private Integer icmsCest;
	private BigDecimal icmsBase;
	private BigDecimal icmsAliquota;
	private BigDecimal icmsValor;
	private int icmsCst;
	private BigDecimal pisBase;
	private BigDecimal pisAliquota;
	private BigDecimal pisValor;
	private int pisCst;
	private BigDecimal cofinsBase;
	private BigDecimal cofinsAliquota;
	private BigDecimal cofinsValor;
	private int cofinsCst;
	private BigDecimal ipiBase;
	private BigDecimal ipiAliquota;
	private BigDecimal ipiValor;
	private int ipiCst;
	
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
	
	@NotNull(message = PRODUTO_ORIGEM_OBRIGATORIA)
	@Enumerated(EnumType.STRING)
	public ProdutoOrigem getOrigem() {
		return origem;
	}

	public void setOrigem(ProdutoOrigem origem) {
		this.origem = origem;
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
	@JoinColumn(name = "ncm_id", referencedColumnName = "ncm_id", nullable = false, foreignKey = @ForeignKey(name = "fnk_ncms"))
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

    @Column(name = "icms_cest", scale = 7)
	public Integer getIcmsCest() {
		return icmsCest;
	}

	public void setIcmsCest(Integer icmsCest) {
		this.icmsCest = icmsCest;
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
	
	public int getIcmsCst() {
		return icmsCst;
	}

	public void setIcmsCst(int icmsCst) {
		this.icmsCst = icmsCst;
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

	public int getPisCst() {
		return pisCst;
	}

	public void setPisCst(int pisCst) {
		this.pisCst = pisCst;
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
	
	public int getCofinsCst() {
		return cofinsCst;
	}

	public void setCofinsCst(int cofinsCst) {
		this.cofinsCst = cofinsCst;
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
	
	public int getIpiCst() {
		return ipiCst;
	}

	public void setIpiCst(int ipiCst) {
		this.ipiCst = ipiCst;
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
