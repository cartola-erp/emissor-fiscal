package net.cartola.emissorfiscal.inventario;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import net.cartola.emissorfiscal.pessoa.Pessoa;

@Entity
@Table(name = "inve")
public class Inventario implements Serializable {

	private static final long serialVersionUID = 2752210794475293298L;
	private Long id;
//	private LocalDate data;
	private Date data;
	private Pessoa pessoa;
	private List<InventarioItem> itens;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "inve_id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "data", columnDefinition = "DATE")
	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	@ManyToOne
	@JoinColumn(name = "pess_id")
	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	@OneToMany(mappedBy = "inventario")
	public List<InventarioItem> getItens() {
		return itens;
	}

	public void setItens(List<InventarioItem> itens) {
		this.itens = itens;
	}

}
