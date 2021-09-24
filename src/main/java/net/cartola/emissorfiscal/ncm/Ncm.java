package net.cartola.emissorfiscal.ncm;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Range;

import net.cartola.emissorfiscal.util.NumberUtil;

/**
 * Ncm
 */
/**
 * 08/08/2017 16:52:40
 * 
 * @author murilo
 */
@Entity
@Table(name = "ncms", uniqueConstraints = {
		@UniqueConstraint(name = "unk_ncms_nume_exce", columnNames = { "nume", "exce" }) })
public class Ncm implements Serializable {

	private static final long serialVersionUID = 7049871263623L;

	private Long id = 0L;
	private int numero;
	private int excecao;
	private boolean isAtivo;				// FALSE = NCM não é mais válido
	private transient String descricao;

	public Ncm() {	}
	
	public Ncm(Long id, String ncm, int excecao) {
		this(ncm, excecao);
		this.id = id;
	}
	
	private Ncm(String ncm, int excecao) {
		this.numero = NumberUtil.getIntegerNullSafe(ncm);
		this.excecao = excecao;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ncm_id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "nume", nullable = false)
	@Positive(message = "O NÚMERO tem que ser positivo e maior que ZERO")
	@Range(min = 1000000, max = 99999999, message = "O NÚMERO do NCM, está incorreto!")
	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	@Column(name = "exce", nullable = false)
	public int getExcecao() {
		return excecao;
	}

	public void setExcecao(int excecao) {
		this.excecao = excecao;
	}
	
	@Column(name = "ativo", nullable = false, columnDefinition = " TINYINT DEFAULT 1") 
	public boolean isAtivo() {
		return isAtivo;
	}

	public void setAtivo(Boolean isAtivo) {
		this.isAtivo = isAtivo;
	}

	@NotNull(message = "A DESCRIÇÃO, não pode ser NULA")
	@NotBlank(message = "É obrigatória uma DESCRIÇÃO")
	@Column(length = 1020)
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Ncm)) {
			return false;
		}
		Ncm other = (Ncm) object;
		return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
	}

	@Override
	public String toString() {
//		if(id.equals(0L)) {
//			return "";
//		}
		return   numero + (excecao>0?" Ex: "+ excecao:"")+" "+descricao;
	}

}
