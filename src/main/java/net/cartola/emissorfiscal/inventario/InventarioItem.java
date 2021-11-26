package net.cartola.emissorfiscal.inventario;

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

import net.cartola.emissorfiscal.documento.Finalidade;

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
@Table(name = "inve_item")
public class InventarioItem implements Serializable {

	private static final long serialVersionUID = -4196616473887012506L;

	private Long id;
	private Inventario inventario;
	// private Produto produto;
//	private BigDecimal quantidade;
//	private BigDecimal valorUnitario;
	// ============
	private int codigoErp;
	private int lojaErp;
	
	private Long codigoX;
	private String codigoSequencia;
	
//	private int inventarioCodigoErp;
	private int produtoCodigoErp;					// PK ->  de Produtos_dbf (autogeral)
	private Finalidade finalidadeEmpresa;			// Será a finaldiade que está no produto, no momento que copiou o invetário do DB da AG para o do emissorfiscal
	private BigDecimal saldoAnterior;
	private BigDecimal entradasCompras;
	private BigDecimal entradasTransferencias;
	private BigDecimal entradasDevolucoes;
	private BigDecimal saidasVendas;
	private BigDecimal saidasVendasCadastradas;
	private BigDecimal saidasTransferencias;
	private BigDecimal saidasOutras;
	private BigDecimal saidasDevolucoes;
	private BigDecimal saldoPosterior;
	private BigDecimal estoqueAtual;
	private BigDecimal entrando;
	private BigDecimal disponivel;
	private BigDecimal saindo;
	private BigDecimal transferindo;
	private BigDecimal estoqueControle;
	private BigDecimal estoqueDeclarado;
	private BigDecimal custo;
	private BigDecimal precoPrazo;
	private String descricao;
	private String descricao2;
	private String descricao3;
	private String unidade;

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

	@Column(name="cod_erp", columnDefinition="int(11) COMMENT ' PK  de autogeral  inventarios itens ' ")
	public int getCodigoErp() {
		return codigoErp;
	}

	public void setCodigoErp(int codigoErp) {
		this.codigoErp = codigoErp;
	}

	public int getLojaErp() {
		return lojaErp;
	}

	public void setLojaErp(int lojaErp) {
		this.lojaErp = lojaErp;
	}

	@Column(name = "codigo_x")
	public Long getCodigoX() {
		return codigoX;
	}

	public void setCodigoX(Long codigoX) {
		this.codigoX = codigoX;
	}
		
	@Column(name = "codigo_seq")
	public String getCodigoSequencia() {
		return codigoSequencia;
	}

	public void setCodigoSequencia(String codigoSequencia) {
		this.codigoSequencia = codigoSequencia;
	}

//	@Column(name="inve_cod_erp", columnDefinition="decimal(19,2) COMMENT 'Código do inventário no ERP - DB autogeral' ")
//	public int getInventarioCodigoErp() {
//		return inventarioCodigoErp;
//	}
//
//	public void setInventarioCodigoErp(int inventarioCodigoErp) {
//		this.inventarioCodigoErp = inventarioCodigoErp;
//	}
	
	@Column(name = "prod_cod_erp")
	public int getProdutoCodigoErp() {
		return produtoCodigoErp;
	}

	public void setProdutoCodigoErp(int produtoCodigoErp) {
		this.produtoCodigoErp = produtoCodigoErp;
	}
	
	@Enumerated(EnumType.STRING)
	@Column(name = "finalidade_empresa", columnDefinition="enum('COMERCIALIZACAO', 'BRINDE', 'DOACAO', 'PATRIMONIO', 'CONSUMO') default null ")
	public Finalidade getFinalidadeEmpresa() {
		return finalidadeEmpresa;
	}

	public void setFinalidadeEmpresa(Finalidade finalidadeEmpresa) {
		this.finalidadeEmpresa = finalidadeEmpresa;
	}

	@Column(name = "sald_ante", columnDefinition=" decimal(19,2) COMMENT 'Saldo anterior' ")
	public BigDecimal getSaldoAnterior() {
		return saldoAnterior;
	}

	public void setSaldoAnterior(BigDecimal saldoAnterior) {
		this.saldoAnterior = saldoAnterior;
	}

	@Column(name = "entr_comp", columnDefinition = " decimal(19,2) COMMENT 'Entradas compras' ")
	public BigDecimal getEntradasCompras() {
		return entradasCompras;
	}

	public void setEntradasCompras(BigDecimal entradasCompras) {
		this.entradasCompras = entradasCompras;
	}

	@Column(name = "entr_tran" , columnDefinition=" decimal(19,2) COMMENT 'Entradas transferencias' " )
	public BigDecimal getEntradasTransferencias() {
		return entradasTransferencias;
	}

	public void setEntradasTransferencias(BigDecimal entradasTransferencias) {
		this.entradasTransferencias = entradasTransferencias;
	}

	@Column(name = "entr_devo", columnDefinition=" decimal(19,2) COMMENT 'Entradas devolucoes' ")
	public BigDecimal getEntradasDevolucoes() {
		return entradasDevolucoes;
	}

	public void setEntradasDevolucoes(BigDecimal entradasDevolucoes) {
		this.entradasDevolucoes = entradasDevolucoes;
	}

	@Column(name = "said_vend", columnDefinition=" decimal(19,2) COMMENT 'Saidas vendas' " )
	public BigDecimal getSaidasVendas() {
		return saidasVendas;
	}

	public void setSaidasVendas(BigDecimal saidasVendas) {
		this.saidasVendas = saidasVendas;
	}

	@Column(name = "said_vend_cada", columnDefinition=" decimal(19,2) COMMENT 'Saidas vendas cadastradas' " )
	public BigDecimal getSaidasVendasCadastradas() {
		return saidasVendasCadastradas;
	}

	public void setSaidasVendasCadastradas(BigDecimal saidasVendasCadastradas) {
		this.saidasVendasCadastradas = saidasVendasCadastradas;
	}

	@Column(name = "said_tran", columnDefinition=" decimal(19,2) COMMENT 'Saidas transferencias' ")
	public BigDecimal getSaidasTransferencias() {
		return saidasTransferencias;
	}

	public void setSaidasTransferencias(BigDecimal saidasTransferencias) {
		this.saidasTransferencias = saidasTransferencias;
	}

	@Column(name = "said_outr", columnDefinition=" decimal(19,2) COMMENT 'Saidas outras' ")
	public BigDecimal getSaidasOutras() {
		return saidasOutras;
	}

	public void setSaidasOutras(BigDecimal saidasOutras) {
		this.saidasOutras = saidasOutras;
	}

	@Column(name = "said_devo", columnDefinition=" decimal(19,2) COMMENT 'Saidas devolucoes' ")
	public BigDecimal getSaidasDevolucoes() {
		return saidasDevolucoes;
	}

	public void setSaidasDevolucoes(BigDecimal saidasDevolucoes) {
		this.saidasDevolucoes = saidasDevolucoes;
	}

	@Column(name = "sald_post", columnDefinition=" decimal(19,2) COMMENT 'Saldo posterior' ")
	public BigDecimal getSaldoPosterior() {
		return saldoPosterior;
	}

	public void setSaldoPosterior(BigDecimal saldoPosterior) {
		this.saldoPosterior = saldoPosterior;
	}

	@Column(name = "esto_atua", columnDefinition=" decimal(19,2) COMMENT 'Estoque Atual ' ")
	public BigDecimal getEstoqueAtual() {
		return estoqueAtual;
	}

	public void setEstoqueAtual(BigDecimal estoqueAtual) {
		this.estoqueAtual = estoqueAtual;
	}

	@Column(name = "entrando", columnDefinition=" decimal(19,2) COMMENT 'Entrando' ")
	public BigDecimal getEntrando() {
		return entrando;
	}

	public void setEntrando(BigDecimal entrando) {
		this.entrando = entrando;
	}

	@Column(name = "disponivel")
	public BigDecimal getDisponivel() {
		return disponivel;
	}

	public void setDisponivel(BigDecimal disponivel) {
		this.disponivel = disponivel;
	}

	@Column(name = "saindo")
	public BigDecimal getSaindo() {
		return saindo;
	}

	public void setSaindo(BigDecimal saindo) {
		this.saindo = saindo;
	}

	@Column(name = "transferindo")
	public BigDecimal getTransferindo() {
		return transferindo;
	}

	public void setTransferindo(BigDecimal transferindo) {
		this.transferindo = transferindo;
	}

	@Column(name = "esto_contr", columnDefinition=" decimal(19,2) COMMENT 'Estoque Controle' " )
	public BigDecimal getEstoqueControle() {
		return estoqueControle;
	}

	public void setEstoqueControle(BigDecimal estoqueControle) {
		this.estoqueControle = estoqueControle;
	}

	@Column(name = "esto_decl", columnDefinition=" decimal(19,2) COMMENT 'Estoque Declarado' " )
	public BigDecimal getEstoqueDeclarado() {
		return estoqueDeclarado;
	}

	public void setEstoqueDeclarado(BigDecimal estoqueDeclarado) {
		this.estoqueDeclarado = estoqueDeclarado;
	}

	@Column(name = "custo")
	public BigDecimal getCusto() {
		return custo;
	}

	public void setCusto(BigDecimal custo) {
		this.custo = custo;
	}

	@Column(name = "prec_praz")
	public BigDecimal getPrecoPrazo() {
		return precoPrazo;
	}

	public void setPrecoPrazo(BigDecimal precoPrazo) {
		this.precoPrazo = precoPrazo;
	}

	@Column(name = "dscr")
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@Column(name = "dscr2")
	public String getDescricao2() {
		return descricao2;
	}

	public void setDescricao2(String descricao2) {
		this.descricao2 = descricao2;
	}

	@Column(name = "dscr3")
	public String getDescricao3() {
		return descricao3;
	}

	public void setDescricao3(String descricao3) {
		this.descricao3 = descricao3;
	}

	@Column(name = "unidade")
	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

	
}
