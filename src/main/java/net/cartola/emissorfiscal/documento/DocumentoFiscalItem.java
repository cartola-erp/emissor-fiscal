package net.cartola.emissorfiscal.documento;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import net.cartola.emissorfiscal.ncm.Ncm;

@Entity
@Table(name="docu_fisc")
public class DocumentoFiscalItem {

//	@Id 
//	@Generated(Generatio)
	private Long id;
	private BigDecimal quantidade;
	private BigDecimal valorUnitario;
	private Ncm ncm;
	private int cfop;
	private BigDecimal icmsBase;
	private BigDecimal icmsAliquota;
	private BigDecimal icmsValor;
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public BigDecimal getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}
	public BigDecimal getValorUnitario() {
		return valorUnitario;
	}
	public void setValorUnitario(BigDecimal valorUnitario) {
		this.valorUnitario = valorUnitario;
	}
	
	@ManyToOne
	@JoinColumn(name="ncm_id", referencedColumnName="ncm_id" )
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
	
	
	
}
