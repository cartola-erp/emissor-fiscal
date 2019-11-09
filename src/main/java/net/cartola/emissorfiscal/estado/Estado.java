package net.cartola.emissorfiscal.estado;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 08/08/2017 16:50:47
 * 
 * @author murilo
 */
@Entity
@Table(name = "esta", uniqueConstraints = { @UniqueConstraint(columnNames = "nome", name = "unk_esta_nome"),
		@UniqueConstraint(columnNames = "sigla", name = "unk_esta_sigla") })
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Estado.findAll", query = "SELECT e FROM Estado e"),
		@NamedQuery(name = "Estado.findById", query = "SELECT e FROM Estado e WHERE e.id=:id") })
public class Estado implements Comparable<Estado>, Serializable {

	private static final long serialVersionUID = 309485301L;

	private Long id;
	private EstadoSigla sigla = EstadoSigla.SP;
	private String nome;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "esta_id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

//	@Column(name = "sigla", length = 2)
//	@NotNull(message = "sigla não pode ser vazia")
//	@Size(min = 1, max = 2, message = "tamanho da sigla deve conter entre 1 e 2")
//	public String getSigla() {
//		return sigla;
//	}
//
//	public void setSigla(String sigla) {
//		this.sigla = sigla;
//	}

	@Column(name = "sigla", length = 2)
	@NotNull(message = "sigla não pode ser vazia")
	@Size(min = 2, max = 2, message = "tamanho da sigla deve conter entre 2 e 2")
	@Enumerated(EnumType.STRING)
	public EstadoSigla getSigla() {
		return sigla;
	}

	public void setSigla(EstadoSigla sigla) {
		this.sigla = sigla;
	}

	@NotNull(message = "descrição não pode ser vazia")
	@Size(min = 1, max = 19, message = "tamanho da descrição deve conter entre 1 e 19")
	@Column(name = "nome", length = 19)
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Estado)) {
			return false;
		}
		Estado other = (Estado) object;
		return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
	}

	@Override
	public String toString() {
		return "Estado[id=" + id + ", sigla=" + sigla + ", nome=" + nome + ']';
	}

	@Override
	public int compareTo(Estado o) {
		if (this.sigla == null) {
			return -1;
		} else {
			return this.sigla.compareTo(o.sigla);
		}
	}
}
