package net.cartola.emissorfiscal.documento;

import java.io.Serializable;
import java.math.BigDecimal;

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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.ToString;
import net.cartola.emissorfiscal.ncm.Ncm;


@ToString(exclude = {"documentoFiscal"})
@Entity
@Table(name = "docu_fisc_item")
@JsonIgnoreProperties(value = { "documentoFiscal" })
public class DocumentoFiscalItem implements Serializable {

	private static final long serialVersionUID = -3885752189101767947L;

	private static final String FINALIDADE_OBRIGATORIA = "Atenção! A finalidade do item é obrigatória!!";
	private static final String PRODUTO_ORIGEM_OBRIGATORIA = "Atenção! A origem do produto é obrigatória!!";
	private static final String QUANTIDADE_OBRIGATORIA = "Atenção! A quantidade do item é obrigatória!!";
	private static final String QUANTIDADE_INVALIDA = "Atenção! A quantidade inserida é inválida!!";
	private static final String VALOR_OBRIGATORIO = "Atenção! O valor do item é obrigatório!!";
	private static final String VALOR_INVALIDO = "Atenção! O valor inserida é inválido!!";

	private Long id;
	private int item;
	/** Serão somente usada em ENTRADAS/COMPRAS, as info abaixo? Não sei, mas acredito que sim !!!**/
	private Long codigoX;
	private String codigoSequencia;
	private int produtoCodigoErp;
	private String ean;		// --> Codigo Barras
	private String descricaoEmpresa;
	private String unidade;
	private int codigoAnp;		// --> Deverá ser preenchido pelo OBJ -> TributacaoEstadual
    private BigDecimal desconto;

	private Finalidade finalidade = Finalidade.CONSUMO;
	private ProdutoOrigem origem = ProdutoOrigem.NACIONAL;
	private BigDecimal quantidade = BigDecimal.ZERO;
	private BigDecimal valorUnitario = BigDecimal.ZERO;
	private Ncm ncm;
	private int cfop;
	private Integer icmsCest = 0;
	private BigDecimal icmsBase = BigDecimal.ZERO;
	private BigDecimal icmsReducaoBaseAliquota = BigDecimal.ZERO;
	private BigDecimal icmsReducaoBaseStAliquota = BigDecimal.ZERO;
	private BigDecimal icmsAliquota = BigDecimal.ZERO;
	private BigDecimal icmsAliquotaDestino = BigDecimal.ZERO;
	private BigDecimal icmsValor = BigDecimal.ZERO;
    private BigDecimal icmsFcpAliquota = BigDecimal.ZERO;
    private BigDecimal icmsFcpValor = BigDecimal.ZERO;
//    private BigDecimal icmsFcpStAliquota = BigDecimal.ZERO;
//    private BigDecimal icmsFcpStValor = BigDecimal.ZERO;
	private BigDecimal icmsStBase = BigDecimal.ZERO;
	private BigDecimal icmsStAliquota = BigDecimal.ZERO;
	private BigDecimal icmsStValor = BigDecimal.ZERO;
    private BigDecimal icmsIva = BigDecimal.ZERO;
    private BigDecimal icmsStBaseRetido = BigDecimal.ZERO;
    private BigDecimal icmsStValorRetido = BigDecimal.ZERO;
//    private BigDecimal icmsBaseFcpStRetido = BigDecimal.ZERO;
//    private BigDecimal icmsAliqFcpStRetido = BigDecimal.ZERO;
//    private BigDecimal icmsValorFcpStRetido = BigDecimal.ZERO;
	private int icmsCst;
    private BigDecimal icmsValorUfDestino = BigDecimal.ZERO;		// É o VALOR do DIFAL
    // Base do ICMS ST (ULTIMA COMPRA), que foi usado quando a AG comprou o produto, p/ usar no ICMS 60
	private BigDecimal icmsStBaseUltimaCompra = BigDecimal.ZERO; 
	private BigDecimal icmsStValorUltimaCompra = BigDecimal.ZERO;
	private BigDecimal itemQtdCompradaUltimaCompra = BigDecimal.ZERO;
    private BigDecimal icmsStAliqUltimaCompra = BigDecimal.ZERO;
	private BigDecimal pisBase = BigDecimal.ZERO;
	private BigDecimal pisAliquota = BigDecimal.ZERO;
	private BigDecimal pisValor = BigDecimal.ZERO;
	private int pisCst;
	private BigDecimal cofinsBase = BigDecimal.ZERO;
	private BigDecimal cofinsAliquota = BigDecimal.ZERO;
	private BigDecimal cofinsValor = BigDecimal.ZERO;
	private int cofinsCst;
	private BigDecimal ipiBase = BigDecimal.ZERO;
	private BigDecimal ipiAliquota = BigDecimal.ZERO;
	private BigDecimal ipiValor = BigDecimal.ZERO;
	private int ipiCst;
	
	// Para "atender" a lei "De Olho No Imposto"
	private BigDecimal valorTotalImposto = BigDecimal.ZERO;
	
	private DocumentoFiscal documentoFiscal;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public int getItem() {
		return item;
	}

	public void setItem(int item) {
		this.item = item;
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
	
	@Column(name = "prod_cod_erp")
	public int getProdutoCodigoErp() {
		return produtoCodigoErp;
	}

	public void setProdutoCodigoErp(int produtoCodigoErp) {
		this.produtoCodigoErp = produtoCodigoErp;
	}

	@Column(name = "ean")
	public String getEan() {
		return ean;
	}

	public void setEan(String ean) {
		this.ean = ean;
	}

	@Column(name = "desc_empr")
	public String getDescricaoEmpresa() {
		return descricaoEmpresa;
	}

	public void setDescricaoEmpresa(String descricaoEmpresa) {
		this.descricaoEmpresa = descricaoEmpresa;
	}

	public String getUnidade() {
		return unidade;
	}

	@Column(name = "cod_anp", nullable = false)
	public int getCodigoAnp() {
		return codigoAnp;
	}

	public void setCodigoAnp(int codigoAnp) {
		this.codigoAnp = codigoAnp;
	}
	
	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}
	
	public BigDecimal getDesconto() {
		return desconto;
	}

	public void setDesconto(BigDecimal desconto) {
		this.desconto = desconto;
	}
	
	@NotNull(message = FINALIDADE_OBRIGATORIA)
	@Enumerated(EnumType.STRING)
	@Column(name="finalidade", columnDefinition="enum('CONSUMO', 'REVENDA') default 'CONSUMO' ")
	public Finalidade getFinalidade() {
		return finalidade;
	}

	public void setFinalidade(Finalidade finalidade) {
		this.finalidade = finalidade;
	}
	
	@NotNull(message = PRODUTO_ORIGEM_OBRIGATORIA)
	@Enumerated(EnumType.STRING)
	@Column(name="origem", columnDefinition="enum('NACIONAL', 'ESTRANGEIRA_IMPORTACAO_DIRETA', 'ESTRANGEIRA_ADQUIRIDO_MERCADO_INTERNO', 'NACIONAL_CONTEUDO_IMPORTADO_MAIOR_40', 'NACIONAL_CONFORMIDADE_PROCESSOS', 'NACIONAL_CONTEUDO_IMPORTADO_MENOR_40', 'ESTRANGEIRA_IMPORTACAO_DIRETA_CAMEX', 'ESTRANGEIRA_ADQUIRIDO_MERCADO_INTERNO_CAMEX', 'NACIONAL_CONTEUDO_IMPORTADO_MAIOR_70') default 'NACIONAL' ")
	public ProdutoOrigem getOrigem() {
		return origem;
	}

	public void setOrigem(ProdutoOrigem origem) {
		this.origem = origem;
	}

	@NotNull(message = QUANTIDADE_OBRIGATORIA)
	@Positive(message = QUANTIDADE_INVALIDA)
	public BigDecimal getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}

	@NotNull(message = VALOR_OBRIGATORIO)
	@Positive(message = VALOR_INVALIDO)
	public BigDecimal getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(BigDecimal valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ncm_id", referencedColumnName = "ncm_id", nullable = false, foreignKey = @ForeignKey(name = "fnk_ncms"))
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

    @Column(name = "icms_cest", scale = 7)
	public Integer getIcmsCest() {
		return icmsCest;
	}

	public void setIcmsCest(Integer icmsCest) {
		this.icmsCest = icmsCest;
	}

	public BigDecimal getIcmsBase() {
		return icmsBase;
	}

	public void setIcmsBase(BigDecimal icmsBase) {
		this.icmsBase = icmsBase;
	}
	
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

//    @Column(name = "icms_aliq_dest", precision = 7, scale = 6, nullable = false, columnDefinition = "Numeric(7,6) default '0.00'")
    @Column(name = "icms_aliq_dest", precision = 7, scale = 6, nullable = false, columnDefinition = "Numeric(7,6) default '0.00'")
	public BigDecimal getIcmsAliquotaDestino() {
		return icmsAliquotaDestino;
	}

	public void setIcmsAliquotaDestino(BigDecimal icmsAliquotaDestino) {
		this.icmsAliquotaDestino = icmsAliquotaDestino;
	}

	public BigDecimal getIcmsValor() {
		return icmsValor;
	}

	public void setIcmsValor(BigDecimal icmsValor) {
		this.icmsValor = icmsValor;
	}
	
    @Column(name = "icms_fcp_aliq", precision = 7, scale = 6, nullable = false, columnDefinition = "Numeric(7,6) default '0.00'")
	public BigDecimal getIcmsFcpAliquota() {
		return icmsFcpAliquota;
	}

	public void setIcmsFcpAliquota(BigDecimal icmsFcpAliquota) {
		this.icmsFcpAliquota = icmsFcpAliquota;
	}

    @Column(name = "icms_fcp_vlr")
	public BigDecimal getIcmsFcpValor() {
		return icmsFcpValor;
	}

	public void setIcmsFcpValor(BigDecimal icmsFcpValor) {
		this.icmsFcpValor = icmsFcpValor;
	}
	
//    @Column(name = "icms_fcp_st_aliq", precision = 7, scale = 6, nullable = false, columnDefinition = "Numeric(7,6) default '0.00'")
//	public BigDecimal getIcmsFcpStAliquota() {
//		return icmsFcpStAliquota;
//	}
//
//	public void setIcmsFcpStAliquota(BigDecimal icmsFcpStAliquota) {
//		this.icmsFcpStAliquota = icmsFcpStAliquota;
//	}
//
//	@Column(name = "icms_fcp_st_vlr", precision = 7, scale = 6, nullable = false, columnDefinition = "Numeric(7,6) default '0.00'")
//	public BigDecimal getIcmsFcpStValor() {
//		return icmsFcpStValor;
//	}
//
//	public void setIcmsFcpStValor(BigDecimal icmsStFcpValor) {
//		this.icmsFcpStValor = icmsStFcpValor;
//	}
	
    @Column(name = "icms_st_aliq", precision = 7, scale = 6, nullable = false, columnDefinition = "Numeric(7,6) default '0.00'")
	public BigDecimal getIcmsStAliquota() {
		return icmsStAliquota;
	}

	public void setIcmsStAliquota(BigDecimal icmsStAliquota) {
		this.icmsStAliquota = icmsStAliquota;
	}

	public BigDecimal getIcmsStBase() {
		return icmsStBase;
	}

	public void setIcmsStBase(BigDecimal icmsStBase) {
		this.icmsStBase = icmsStBase;
	}

	public BigDecimal getIcmsStValor() {
		return icmsStValor;
	}

	public void setIcmsStValor(BigDecimal icmsStValor) {
		this.icmsStValor = icmsStValor;
	}

	@Column(name = "icms_iva", precision = 7, scale = 6, nullable = false, columnDefinition = "Numeric(7,6) default '0.00'")
    public BigDecimal getIcmsIva() {
        return icmsIva;
    }

    public void setIcmsIva(BigDecimal icmsIva) {
        this.icmsIva = icmsIva;
    }
	
    @Column(name = "icms_st_base_ret")
	public BigDecimal getIcmsStBaseRetido() {
		return icmsStBaseRetido;
	}

	public void setIcmsStBaseRetido(BigDecimal icmsStBaseRetido) {
		this.icmsStBaseRetido = icmsStBaseRetido;
	}

    @Column(name = "icms_st_vlr_ret")
	public BigDecimal getIcmsStValorRetido() {
		return icmsStValorRetido;
	}

	public void setIcmsStValorRetido(BigDecimal icmsStValorRetido) {
		this.icmsStValorRetido = icmsStValorRetido;
	}

//    @Column(name = "icms_st_base_fcp_ret", precision = 7, scale = 6, nullable = false, columnDefinition = "Numeric(7,6) default '0.00'")
//	public BigDecimal getIcmsBaseFcpStRetido() {
//		return icmsBaseFcpStRetido;
//	}
//
//	public void setIcmsBaseFcpStRetido(BigDecimal icmsBaseFcpStRetido) {
//		this.icmsBaseFcpStRetido = icmsBaseFcpStRetido;
//	}
//
//    @Column(name = "icms_st_aliq_fcp_st_ret", precision = 7, scale = 6, nullable = false, columnDefinition = "Numeric(7,6) default '0.00'")
//	public BigDecimal getIcmsAliqFcpStRetido() {
//		return icmsAliqFcpStRetido;
//	}
//
//	public void setIcmsAliqFcpStRetido(BigDecimal icmsAliqFcpStRetido) {
//		this.icmsAliqFcpStRetido = icmsAliqFcpStRetido;
//	}

//    @Column(name = "icms_vlr_fcp_st_ret", precision = 7, scale = 6, nullable = false, columnDefinition = "Numeric(7,6) default '0.00'")
//	public BigDecimal getIcmsValorFcpStRetido() {
//		return icmsValorFcpStRetido;
//	}
//
//	public void setIcmsValorFcpStRetido(BigDecimal icmsValorFcpStRetido) {
//		this.icmsValorFcpStRetido = icmsValorFcpStRetido;
//	}

	public int getIcmsCst() {
		return icmsCst;
	}

	public void setIcmsCst(int icmsCst) {
		this.icmsCst = icmsCst;
	}

	@Column(name = "icms_vlr_uf_dest")
    public BigDecimal getIcmsValorUfDestino() {
		return icmsValorUfDestino;
	}

	public void setIcmsValorUfDestino(BigDecimal icmsValorUfDestino) {
		this.icmsValorUfDestino = icmsValorUfDestino;
	}
	
    @Column(name = "icms_st_base_ultim_comp")
	public BigDecimal getIcmsStBaseUltimaCompra() {
		return icmsStBaseUltimaCompra;
	}

	public void setIcmsStBaseUltimaCompra(BigDecimal icmsStBaseUltimaCompra) {
		this.icmsStBaseUltimaCompra = icmsStBaseUltimaCompra;
	}

	@Column(name = "icms_st_vlr_ultim_comp")
	public BigDecimal getIcmsStValorUltimaCompra() {
		return icmsStValorUltimaCompra;
	}

	public void setIcmsStValorUltimaCompra(BigDecimal icmsStValorUltimaCompra) {
		this.icmsStValorUltimaCompra = icmsStValorUltimaCompra;
	}
	
    @Column(name = "item_qtd_ultim_comp")
	public BigDecimal getItemQtdCompradaUltimaCompra() {
		return itemQtdCompradaUltimaCompra;
	}

	public void setItemQtdCompradaUltimaCompra(BigDecimal itemQtdCompradaUltimaCompra) {
		this.itemQtdCompradaUltimaCompra = itemQtdCompradaUltimaCompra;
	}
  
	@Column(name = "icms_st_aliq_ultim_comp", precision = 7, scale = 6, nullable = false, columnDefinition = "Numeric(7,6) default '0.00'")
	public BigDecimal getIcmsStAliqUltimaCompra() {
		return icmsStAliqUltimaCompra;
	}

	public void setIcmsStAliqUltimaCompra(BigDecimal icmsStAliqUltimaCompra) {
		this.icmsStAliqUltimaCompra = icmsStAliqUltimaCompra;
	}

	public BigDecimal getPisBase() {
		return pisBase;
	}

	public void setPisBase(BigDecimal pisBase) {
		this.pisBase = pisBase;
	}

	@Column(name = "pis_aliq", precision = 7, scale = 6, nullable = false, columnDefinition = "Numeric(7,6) default '0.00'")
	public BigDecimal getPisAliquota() {
		return pisAliquota;
	}

	public void setPisAliquota(BigDecimal pisAliquota) {
		this.pisAliquota = pisAliquota;
	}

	public BigDecimal getPisValor() {
		return pisValor;
	}

	public void setPisValor(BigDecimal pisValor) {
		this.pisValor = pisValor;
	}

	public int getPisCst() {
		return pisCst;
	}

	public void setPisCst(int pisCst) {
		this.pisCst = pisCst;
	}

	public BigDecimal getCofinsBase() {
		return cofinsBase;
	}

	public void setCofinsBase(BigDecimal cofinsBase) {
		this.cofinsBase = cofinsBase;
	}

	@Column(name = "cofins_aliq", precision = 7, scale = 6, nullable = false, columnDefinition = "Numeric(7,6) default '0.00'")
	public BigDecimal getCofinsAliquota() {
		return cofinsAliquota;
	}

	public void setCofinsAliquota(BigDecimal cofinsAliquota) {
		this.cofinsAliquota = cofinsAliquota;
	}

	public BigDecimal getCofinsValor() {
		return cofinsValor;
	}

	public void setCofinsValor(BigDecimal cofinsValor) {
		this.cofinsValor = cofinsValor;
	}
	
	public int getCofinsCst() {
		return cofinsCst;
	}

	public void setCofinsCst(int cofinsCst) {
		this.cofinsCst = cofinsCst;
	}

	public BigDecimal getIpiBase() {
		return ipiBase;
	}

	public void setIpiBase(BigDecimal ipiBase) {
		this.ipiBase = ipiBase;
	}

	@Column(name = "ipi_aliq", precision = 7, scale = 6, nullable = false, columnDefinition = "Numeric(7,6) default '0.00'")
	public BigDecimal getIpiAliquota() {
		return ipiAliquota;
	}

	public void setIpiAliquota(BigDecimal ipiAliquota) {
		this.ipiAliquota = ipiAliquota;
	}

	public BigDecimal getIpiValor() {
		return ipiValor;
	}

	public void setIpiValor(BigDecimal ipiValor) {
		this.ipiValor = ipiValor;
	}
	
	public int getIpiCst() {
		return ipiCst;
	}

	public void setIpiCst(int ipiCst) {
		this.ipiCst = ipiCst;
	}

	@Column(name = "vlr_tot_imposto")
	public BigDecimal getValorTotalImposto() {
		return valorTotalImposto;
	}

	public void setValorTotalImposto(BigDecimal valorTotalImposto) {
		this.valorTotalImposto = valorTotalImposto;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "docu_fisc_id", unique = false, foreignKey = @ForeignKey(name = "fnk_documento_fiscal"))
	public DocumentoFiscal getDocumentoFiscal() {
		return documentoFiscal;
	}

	public void setDocumentoFiscal(DocumentoFiscal documentoFiscal) {
		this.documentoFiscal = documentoFiscal;
	}

}
