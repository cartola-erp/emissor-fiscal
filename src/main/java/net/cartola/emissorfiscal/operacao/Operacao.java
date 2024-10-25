package net.cartola.emissorfiscal.operacao;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

/**
 * 08/08/2017 17:44:37
 *
 * @author murilo
 */
@Entity
@Table(name = "oper", uniqueConstraints = { @UniqueConstraint(name = "unk_oper_dscr", columnNames = {"dscr"}) })
public class Operacao implements Serializable, Comparable<Operacao> {

	private static final long serialVersionUID = 12701892348311L;
	private Long id;
	private String descricao;
	private boolean isDevolucao;
	private boolean isRemessaParaFornecedor;			// Poderá ser: "Remessa em Garantia" ou "Remessa para conserto"
	private boolean isInterestadual;

	// --- LOG

	private String criadoPor;
	private Date dataCriacao;
	private String alteradoPor;
	private Date dataAlteracao;
	private String excluidoPor;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "oper_id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "dscr", nullable = false)
	@NotNull(message="A DESCRIÇÃO não pode ser NULA")
	@NotBlank(message="A DESCRIÇÃO tem que ser preenchida")
	@Length(message= "A DESCRIÇÃO deve ter entre 3 á 150 caracteres", min=3, max=150)
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@Column(name = "devolucao", columnDefinition = "tinyint(1) NOT NULL DEFAULT '0' ")
	public boolean isDevolucao() {
		return isDevolucao;
	}

	public void setDevolucao(boolean isDevolucao) {
		this.isDevolucao = isDevolucao;
	}

	@Column(name = "remessa_fornecedor", columnDefinition = "tinyint(1) NOT NULL DEFAULT '0' ")
	public boolean isRemessaParaFornecedor() {
		return isRemessaParaFornecedor;
	}

	public void setRemessaParaFornecedor(boolean isRemessaParaFornecedor) {
		this.isRemessaParaFornecedor = isRemessaParaFornecedor;
	}
	
	@Column(name = "interestadual", columnDefinition = "tinyint(1) NOT NULL DEFAULT '0' ")
	public boolean isInterestatual() {
		return isInterestadual;
	}
				
	public void setInterestatual(boolean isInterestadual) {
		this.isInterestadual = isInterestadual;
	}

	/**
	 * Retorna true, se <br> 
	 * 
	 * isDevolucao <br>
	 * isRemessaParaFornecedor
	 * 
	 * @return
	 */
	public boolean ehAlgumaDevolucao() {
		return this.isDevolucao || this.isRemessaParaFornecedor;
	}

	// --- LOG ---- //
	// Metodos do Log -----------
	@Column(name="criado_por")
	public String getCriadoPor(){
		return criadoPor;
	};

	public void setCriadoPor(String criadoPor){
		this.criadoPor = criadoPor;
	};
	@Column(name="data_criacao")
	public Date getDataCriacao(){
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao){
		this.dataCriacao = dataCriacao;
	}

	@Column(name="alterado_por")
	public String getAlteradoPor(){
		return alteradoPor;
	}

	public void setAlteradoPor(String alteradoPor){
		this.alteradoPor = alteradoPor;
	}

	@Column(name="data_alteracao")
	public Date getDataAlteracao(){
		return dataAlteracao;
	}

	public void setDataAlteracao(Date dataAlteracao){
		this.dataAlteracao = dataAlteracao;
	}

	@Column(name="excluido_por")
	public String getExcluidoPor(){
		return excluidoPor;
	}

	public void setExcluidoPor(String excluidoPor){
		this.excluidoPor = excluidoPor;
	}
	
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Operacao)) {
			return false;
		}
		Operacao other = (Operacao) object;
		return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
	}

	@Override
	public String toString() {
		return "Operacao[id=" + id + ", descricao=" + descricao + "]";
	}

	@Override
	public int compareTo(Operacao obj) {
		Long i = (this.id - obj.id);
		return i.intValue();
	}

}
