package net.cartola.emissorfiscal.tributacao.federal;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import net.cartola.emissorfiscal.documento.Finalidade;
import net.cartola.emissorfiscal.ncm.Ncm;
import net.cartola.emissorfiscal.operacao.Operacao;
import net.cartola.emissorfiscal.pessoa.RegimeTributario;

/**
 * 08/08/2017 17:37:43
 * @author murilo
 */
@Entity
@Table(name = "trib_fede", uniqueConstraints = @UniqueConstraint(name = "unk_trib_fede_oper_ncms", columnNames = {
		"oper_id", "ncm_id" }))
public class TributacaoFederal implements Serializable {

	private static final long serialVersionUID = 2348194323467554L;

	private static final String FINALIDADE_OBRIGATORIA = "Atenção! A finalidade é obrigatória!!";

	private Long id;
	private Operacao operacao;
	private Ncm ncm = new Ncm();
	private Finalidade finalidade = Finalidade.CONSUMO;
	private RegimeTributario regimeTributario;
	private int pisCst;
	private BigDecimal pisBase;
	private BigDecimal pisAliquota;
	private int cofinsCst;
	private BigDecimal cofinsBase;
	private BigDecimal cofinsAliquota;
	private int ipiCst;
	private BigDecimal ipiBase;
	private BigDecimal ipiAliquota;
	private String mensagem;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "trib_fede_id", nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "oper_id", nullable = false, foreignKey = @ForeignKey(name = "fnk_trib_fede_oper"))
	public Operacao getOperacao() {
		return operacao;
	}

	public void setOperacao(Operacao operacao) {
		this.operacao = operacao;
	}

	@ManyToOne
	@JoinColumn(name = "ncm_id", foreignKey = @ForeignKey(name = "fnk_trib_fede_ncms"))
	public Ncm getNcm() {
		return ncm;
	}

	public void setNcm(Ncm ncm) {
		this.ncm = ncm;
	}

	@NotNull(message = FINALIDADE_OBRIGATORIA)
	@Enumerated(EnumType.STRING)
	@Column(name="finalidade", columnDefinition="enum('CONSUMO', 'REVENDA') default 'CONSUMO' ")
	public Finalidade getFinalidade() {
		return finalidade;
	}

	public void setFinalidade(Finalidade finalidade) {
		this.finalidade = finalidade;
	}

	@Enumerated(EnumType.STRING)
	@Column(name="regime_tributario", columnDefinition="enum('SIMPLES', 'PRESUMIDO', 'REAL') ")
	public RegimeTributario getRegimeTributario() {
		return regimeTributario;
	}

	public void setRegimeTributario(RegimeTributario regimeTributario) {
		this.regimeTributario = regimeTributario;
	}

	@Column(name = "pis_cst", scale = 4, nullable = false)
	public int getPisCst() {
		return pisCst;
	}

	public void setPisCst(int pisCst) {
		this.pisCst = pisCst;
	}

	@Column(name = "pis_base", precision = 7, scale = 6, nullable = false, columnDefinition = "Numeric(7,6) default '0.00'")
	public BigDecimal getPisBase() {
		return pisBase;
	}

	public void setPisBase(BigDecimal pisBase) {
		this.pisBase = pisBase;
	}

	@Column(name = "pis_aliq", precision = 7, scale = 6, nullable = false, columnDefinition = "Numeric(7,6) default '0.00'")
	public BigDecimal getPisAliquota() {
		return pisAliquota;
	}

	public void setPisAliquota(BigDecimal pisAliquota) {
		this.pisAliquota = pisAliquota;
	}

	@Column(name = "cofins_cst", scale = 4, nullable = false)
	public int getCofinsCst() {
		return cofinsCst;
	}

	public void setCofinsCst(int cofinsCst) {
		this.cofinsCst = cofinsCst;
	}

	@Column(name = "cofins_base", precision = 7, scale = 6, nullable = false, columnDefinition = "Numeric(7,6) default '0.00'")
	public BigDecimal getCofinsBase() {
		return cofinsBase;
	}

	public void setCofinsBase(BigDecimal cofinsBase) {
		this.cofinsBase = cofinsBase;
	}

	@Column(name = "cofins_aliq", precision = 7, scale = 6, nullable = false, columnDefinition = "Numeric(7,6) default '0.00'")
	public BigDecimal getCofinsAliquota() {
		return cofinsAliquota;
	}

	public void setCofinsAliquota(BigDecimal cofinsAliquota) {
		this.cofinsAliquota = cofinsAliquota;
	}

	@Column(name = "ipi_cst", scale = 4, nullable = false)
	public int getIpiCst() {
		return ipiCst;
	}

	public void setIpiCst(int ipiCst) {
		this.ipiCst = ipiCst;
	}

	@Column(name = "ipi_base", precision = 7, scale = 6, nullable = false, columnDefinition = "Numeric(7,6) default '0.00'")
	public BigDecimal getIpiBase() {
		return ipiBase;
	}

	public void setIpiBase(BigDecimal ipiBase) {
		this.ipiBase = ipiBase;
	}

	@Column(name = "ipi_aliq", precision = 7, scale = 6, nullable = false, columnDefinition = "Numeric(7,6) default '0.00'")
	public BigDecimal getIpiAliquota() {
		return ipiAliquota;
	}

	public void setIpiAliquota(BigDecimal ipiAliquota) {
		this.ipiAliquota = ipiAliquota;
	}

	@Column(name = "mens")
	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof TributacaoFederal)) {
			return false;
		}
		TributacaoFederal other = (TributacaoFederal) object;
		return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
	}

	@Override
	public String toString() {
		return "TributacaoFederal [id=" + id + ", operacao=" + operacao + ", ncm=" + ncm + ", pisCst=" + pisCst
				+ ", pisBase=" + pisBase + ", pisAliquota=" + pisAliquota + ", cofinsCst=" + cofinsCst + ", cofinsBase="
				+ cofinsBase + ", cofinsAliquota=" + cofinsAliquota + ", ipiCst=" + ipiCst + ", ipiBase=" + ipiBase
				+ ", ipiAliquota=" + ipiAliquota + ", mensagem=" + mensagem + "]";
	}

}
