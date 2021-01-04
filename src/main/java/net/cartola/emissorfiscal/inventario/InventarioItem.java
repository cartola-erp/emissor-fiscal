package net.cartola.emissorfiscal.inventario;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "inve_item")
public class InventarioItem implements Serializable {

	private static final long serialVersionUID = -4196616473887012506L;

	private Long id;
	private Inventario inventario;
	// private Produto produto;
	private BigDecimal quantidade;
	private BigDecimal valorUnitario;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "inve_item_id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name="inve_id", foreignKey = @ForeignKey(name = "fk_inve_item_inve"))
	public Inventario getInventario() {
		return inventario;
	}

	public void setInventario(Inventario inventario) {
		this.inventario = inventario;
	}

	@Column(name = "quan", columnDefinition = "DECIMAL(10,2)", scale=10, precision=2)
	public BigDecimal getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}

	@Column(name = "valo_unit", columnDefinition = "DECIMAL(10,2)", scale=10, precision=2)
	public BigDecimal getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(BigDecimal valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

}
