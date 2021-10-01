package net.cartola.emissorfiscal.devolucao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.ToString;
import net.cartola.emissorfiscal.documento.Item;
import net.cartola.emissorfiscal.documento.ProdutoOrigem;
import net.cartola.emissorfiscal.loja.Loja;
import net.cartola.emissorfiscal.produto.ProdutoUnidade;
import net.cartola.emissorfiscal.util.LocalDateTimeDeserializer;

/**
 * @date 15 de set. de 2021
 * @author robson.costa
 */
@ToString
@Entity
@Table(name = "devo_item")
@JsonIgnoreProperties(value = {"devolucao"})
public class DevolucaoItem extends Item implements Serializable {

	private static final long serialVersionUID = 2124683402501336359L;

	private Long id;
	/** Serão somente usada em ENTRADAS/COMPRAS, as info abaixo? Não sei, mas acredito que sim !!!**/
	// referente a "origemCodigo", do ERP
	// no ERP o "origemCodigo", é basicamente o campo "numero_nota" que tem em DocumentoFiscal
	// Com esse objeto que é o "DocumentoFiscal", origem terei que verificar se o mesmo já está salvo no DB do emissorfiscal
	// caso NÃO irei SALVAR
    private Long documentoOrigemCodigo;  // OR origem nota
    // private DocumentoFiscal documentoFiscalOrigem;
	// referente a "origemLoja"
	private Loja lojaOrigem;
	private String descricao;
	// ==================================================================================== informacoes que usa para "calcular" os tributos
	private int cfopFornecedor;
//	private int cfopEntradaEmpresa;
//	private Integer icmsCest = 0;
	
	// ====================================================================================
    private VendaTipo origemTipo = VendaTipo.NFE;
	private LocalDateTime cadastro;
	private LocalDateTime alterado;

	private Devolucao devolucao;
	
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
		return id;
	}
	
    public void setId(Long id) {
		this.id = id;
	}

    @Override
    @Column(name = "origem_item")
	public int getItem() {
		return item;
	}

	public void setItem(int item) {
		this.item = item;
	}
    
//	public int getOrigemItem() {
//		return origemItem;
//	}
//
//	public void setOrigemItem(int origemItem) {
//		this.origemItem = origemItem;
//	}

	@Override
	@Column(name = "codigo_x")
	public Long getCodigoX() {
		return codigoX;
	}

	public void setCodigoX(Long codigoX) {
		this.codigoX = codigoX;
	}

	@Override
	@Column(name = "codigo_seq")
	public String getCodigoSequencia() {
		return codigoSequencia;
	}

	public void setCodigoSequencia(String codigoSequencia) {
		this.codigoSequencia = codigoSequencia;
	}

	@Column(name = "prod_cod_erp")
	public int getProdutoCodigoErp() {
		return produtoCodigoErp;
	}

	public void setProdutoCodigoErp(int produtoCodigoErp) {
		this.produtoCodigoErp = produtoCodigoErp;
	}

//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "docu_fisc_orig_id", unique = false, foreignKey = @ForeignKey(name = "fnk_devo_item_docu_fisc"))
//	public DocumentoFiscal getDocumentoFiscalOrigem() {
//		return documentoFiscalOrigem;
//	}
//
//	public void setDocumentoFiscalOrigem(DocumentoFiscal documentoFiscalOrigem) {
//		this.documentoFiscalOrigem = documentoFiscalOrigem;
//	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "loja_orig_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fnk_devo_item_loja_id"))
	public Loja getLojaOrigem() {
		return lojaOrigem;
	}

	public void setLojaOrigem(Loja lojaOrigem) {
		this.lojaOrigem = lojaOrigem;
	}

	public String getDescricao() {
		return descricao;
	}

	@Column(name = "desc")
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "prod_unid_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fnk_devo_item_prod_unid_id"))
	public ProdutoUnidade getUnidade() {
		return unidade;
	}

	@Override
	public void setUnidade(ProdutoUnidade unidade) {
		this.unidade = unidade;
	}
	
	public BigDecimal getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}
	
	@Column(name = "vlr_unit")
	public BigDecimal getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(BigDecimal valorUnitario) {
		this.valorUnitario = valorUnitario;
	}
	
	@Column(name = "desc_unit")
	public BigDecimal getDesconto() {
		return desconto;
	}

	public void setDesconto(BigDecimal desconto) {
		this.desconto = desconto;
	}
	
	@Column(name = "vlr_fret_unit")
	public BigDecimal getValorFrete() {
		return valorFrete;
	}

	public void setValorFrete(BigDecimal valorFrete) {
		this.valorFrete = valorFrete;
	}

	@Column(name = "vlr_segu_unit")
	public BigDecimal getValorSeguro() {
		return valorSeguro;
	}

	public void setValorSeguro(BigDecimal valorSeguro) {
		this.valorSeguro = valorSeguro;
	}
	
	@Enumerated(EnumType.STRING)
	@Column(name="origem", columnDefinition="enum('NACIONAL', 'ESTRANGEIRA_IMPORTACAO_DIRETA', 'ESTRANGEIRA_ADQUIRIDO_MERCADO_INTERNO', 'NACIONAL_CONTEUDO_IMPORTADO_MAIOR_40', 'NACIONAL_CONFORMIDADE_PROCESSOS', 'NACIONAL_CONTEUDO_IMPORTADO_MENOR_40', 'ESTRANGEIRA_IMPORTACAO_DIRETA_CAMEX', 'ESTRANGEIRA_ADQUIRIDO_MERCADO_INTERNO_CAMEX', 'NACIONAL_CONTEUDO_IMPORTADO_MAIOR_70') default 'NACIONAL' ")
	public ProdutoOrigem getOrigem() {
		return origem;
	}

	public void setOrigem(ProdutoOrigem origem) {
		this.origem = origem;
	}

	@Column(name = "vlr_outr_desp_acess_unit")
	public BigDecimal getValorOutrasDespesasAcessorias() {
		return super.valorOutrasDespesasAcessorias;
	}

	public void setValorOutrasDespesasAcessorias(BigDecimal valorOutrasDespesasAcessorias) {
		super.valorOutrasDespesasAcessorias = valorOutrasDespesasAcessorias;
	}
	
	@Override
	public String getClasseFiscal() {
		return classeFiscal;
	}

	@Override
	public void setClasseFiscal(String classeFiscal) {
		this.classeFiscal = classeFiscal;
	}

	@Override
	public int getExcecao() {
		return excecao;
	}

	@Override
	public void setExcecao(int excecao) {
		this.excecao = excecao;
	}
	
	@Column(name = "cfop_forn", scale = 4, nullable = false)
	public int getCfopFornecedor() {
		return cfopFornecedor;
	}

	public void setCfopFornecedor(int cfopFornecedor) {
		this.cfopFornecedor = cfopFornecedor;
	}

//	@Column(name = "cfop_entr_emp")
//	public int getCfopEntradaEmpresa() {
//		return cfopEntradaEmpresa;
//	}
//
//	public void setCfopEntradaEmpresa(int cfopEntradaEmpresa) {
//		this.cfopEntradaEmpresa = cfopEntradaEmpresa;
//	}

    @Column(name = "icms_redu_base_aliq", precision = 7, scale = 6, nullable = false, columnDefinition = "Numeric(7,6) default '0.00'")
	public BigDecimal getIcmsReducaoBaseAliquota() {
		return icmsReducaoBaseAliquota;
	}

	public void setIcmsReducaoBaseAliquota(BigDecimal icmsReducaoBaseAliquota) {
		this.icmsReducaoBaseAliquota = icmsReducaoBaseAliquota;
	}

    @Column(name = "icms_redu_base_st_aliq", precision = 7, scale = 6, nullable = false, columnDefinition = "Numeric(7,6) default '0.00'")
	public BigDecimal getIcmsReducaoBaseStAliquota() {
		return icmsReducaoBaseStAliquota;
	}

	public void setIcmsReducaoBaseStAliquota(BigDecimal icmsReducaoBaseStAliquota) {
		this.icmsReducaoBaseStAliquota = icmsReducaoBaseStAliquota;
	}

	@Column(name = "icms_aliquota", precision = 7, scale = 6, nullable = false, columnDefinition = "Numeric(7,6) default '0.00'")
	public BigDecimal getIcmsAliquota() {
		return icmsAliquota;
	}

	public void setIcmsAliquota(BigDecimal icmsAliquota) {
		this.icmsAliquota = icmsAliquota;
	}

    @Column(name = "icms_st_aliq", precision = 7, scale = 6, nullable = false, columnDefinition = "Numeric(7,6) default '0.00'")
	public BigDecimal getIcmsStAliquota() {
		return icmsStAliquota;
	}

	public void setIcmsStAliquota(BigDecimal icmsStAliquota) {
		this.icmsStAliquota = icmsStAliquota;
	}

	@Column(name = "icms_iva", precision = 7, scale = 6, nullable = false, columnDefinition = "Numeric(7,6) default '0.00'")
	public BigDecimal getIcmsIva() {
		return super.icmsIva;
	}

	public void setIcmsIva(BigDecimal icmsIva) {
		super.icmsIva = icmsIva;
	}

	@Column(name = "ipi_aliq", precision = 7, scale = 6, nullable = false, columnDefinition = "Numeric(7,6) default '0.00'")
	public BigDecimal getIpiAliquota() {
		return ipiAliquota;
	}

	public void setIpiAliquota(BigDecimal ipiAliquota) {
		this.ipiAliquota = ipiAliquota;
	}
	
	@Enumerated(EnumType.STRING)
	@Column(name="origem_tipo", columnDefinition="enum('NOTA','NFE','PEDIDO','CUPOM','COMPRA','SAT') ")
	public VendaTipo getOrigemTipo() {
		return origemTipo;
	}
	
	public void setOrigemTipo(VendaTipo origemTipo) {
		this.origemTipo = origemTipo;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "devo_id", unique = false, foreignKey = @ForeignKey(name = "fnk_devolucao"))
	public Devolucao getDevolucao() {
		return devolucao;
	}

	public void setDevolucao(Devolucao devolucao) {
		this.devolucao = devolucao;
	}


	
}
