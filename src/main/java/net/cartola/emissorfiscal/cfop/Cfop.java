package net.cartola.emissorfiscal.cfop;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *	13 de fev de 2020
 *	@author robson.costa
 */

@Entity
@Table
public class Cfop implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private BigDecimal numero;
	private String descricao;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cfop_id")
	public Long getId() {
		return id;
	}
	
	public BigDecimal getNumero() {
		return numero;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setNumero(BigDecimal numero) {
		this.numero = numero;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@Override
	public String toString() {
		return "Cfop [id=" + id + ", numero=" + numero + ", descricao=" + descricao + "]";
	}
	
}


