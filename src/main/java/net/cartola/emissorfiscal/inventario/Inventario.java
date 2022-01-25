package net.cartola.emissorfiscal.inventario;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import net.cartola.emissorfiscal.loja.Loja;
import net.cartola.emissorfiscal.util.LocalDateDeserializer;
import net.cartola.emissorfiscal.util.LocalDateTimeDeserializer;

/**
 * COPIEI, os mesmos atributos que estão NO ERP !!!
 * Minha intenção é montar uma PROCEDURE que "dispare", pelo menos duas vezes no mês subsequente;
 * 1 - Irá verificar se no banco do emissorfiscal já tem o último inventário (que está no db da autogeral) salvo;
 * 2 - Caso não tenha, basicamente irei fazer um insert do banco de dados da AUTO GERAL, para o BANCO DE DADOS do emissorfiscal;
 * 
 * 
 * @author robson.costa
 */
@Entity
@Table(name = "inve")
public class Inventario implements Serializable {

	private static final long serialVersionUID = 2752210794475293298L;
	
	private Long id;
	private int inventarioCodigoErp;						// --> Código do inventário no ERPJ
	private int inventarioLojaErp;
//	private int loja;
	private Loja loja;							// --> PS: Tenho que verificar qual é o ID no DB do EMISSOR-FISCAL, pois poderá não ser o msm que esteja no DB da AUTOGERAL;
	private LocalDate inicio;
	private LocalDate fim;
	private BigDecimal valor;
	private BigDecimal valorTransferindo;
	private BigDecimal valorConsumo;
	private BigDecimal valorSaldo;
	private BigDecimal valorDeclarado;
	private LocalDateTime cadastro;
	private LocalDateTime alterado;
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

//	@Column(name = "inve_cod_erp", )
	@Column(name="inve_cod_erp", columnDefinition=" INT(11) COMMENT 'Código do inventário no ERP - DB autogeral' ")
	public int getInventarioCodigoErp() {
		return inventarioCodigoErp;
	}

	public void setInventarioCodigoErp(int inventarioCodigoErp) {
		this.inventarioCodigoErp = inventarioCodigoErp;
	}

	@Column(name="inve_loja_erp", columnDefinition=" INT(11) COMMENT 'Código da loja do inventário no ERP - DB autogeral' ")
	public int getInventarioLojaErp() {
		return inventarioLojaErp;
	}

	public void setInventarioLojaErp(int inventarioLojaErp) {
		this.inventarioLojaErp = inventarioLojaErp;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "loja_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fnk_inve_loja_id"))
	public Loja getLoja() {
		return loja;
	}

	public void setLoja(Loja loja) {
		this.loja = loja;
	}

	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	public LocalDate getInicio() {
		return inicio;
	}

	public void setInicio(LocalDate inicio) {
		this.inicio = inicio;
	}

	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	public LocalDate getFim() {
		return fim;
	}

	public void setFim(LocalDate fim) {
		this.fim = fim;
	}

	@Column(name = "valor", columnDefinition = " decimal(19,2) COMMENT 'Valor' ")
	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	@Column(name = "vlr_trans", columnDefinition = " decimal(19,2) COMMENT 'Valor transferindo' ")
	public BigDecimal getValorTransferindo() {
		return valorTransferindo;
	}

	public void setValorTransferindo(BigDecimal valorTransferindo) {
		this.valorTransferindo = valorTransferindo;
	}

	@Column(name = "vlr_cons" , columnDefinition = " decimal(19,2) COMMENT 'Valor consumo' ")
	public BigDecimal getValorConsumo() {
		return valorConsumo;
	}

	public void setValorConsumo(BigDecimal valorConsumo) {
		this.valorConsumo = valorConsumo;
	}
	
	@Column(name = "vlr_sald", columnDefinition = " decimal(19,2) COMMENT 'Valor saldo' " )
	public BigDecimal getValorSaldo() {
		return valorSaldo;
	}

	public void setValorSaldo(BigDecimal valorSaldo) {
		this.valorSaldo = valorSaldo;
	}

	@Column(name = "vlr_decl", columnDefinition = " decimal(19,2) COMMENT 'Valor declarado' ")
	public BigDecimal getValorDeclarado() {
		return valorDeclarado;
	}

	public void setValorDeclarado(BigDecimal valorDeclarado) {
		this.valorDeclarado = valorDeclarado;
	}

	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	public LocalDateTime getCadastro() {
		return cadastro;
	}

	public void setCadastro(LocalDateTime cadastro) {
		this.cadastro = cadastro;
	}

	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	public LocalDateTime getAlterado() {
		return alterado;
	}

	public void setAlterado(LocalDateTime alterado) {
		this.alterado = alterado;
	}

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "inventario")
	public List<InventarioItem> getItens() {
		return itens;
	}
	
	public void setItens(List<InventarioItem> itens) {
		this.itens = itens;
	}

}
