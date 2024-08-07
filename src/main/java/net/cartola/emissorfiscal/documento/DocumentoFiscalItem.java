package net.cartola.emissorfiscal.documento;

import static org.springframework.util.StringUtils.hasLength;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.ToString;
import net.cartola.emissorfiscal.devolucao.DevolucaoItem;
import net.cartola.emissorfiscal.produto.ProdutoUnidade;


@ToString(exclude = {"documentoFiscal"})
@Entity
@Table(name = "docu_fisc_item")
@JsonIgnoreProperties(value = { "documentoFiscal" })
public class DocumentoFiscalItem extends Item implements Serializable {

	private static final long serialVersionUID = -3885752189101767947L;

	private static final String FINALIDADE_OBRIGATORIA = "Atenção! A finalidade do item é obrigatória!!";
	private static final String PRODUTO_ORIGEM_OBRIGATORIA = "Atenção! A origem do produto é obrigatória!!";
	private static final String QUANTIDADE_OBRIGATORIA = "Atenção! A quantidade do item é obrigatória!!";
	private static final String QUANTIDADE_INVALIDA = "Atenção! A quantidade inserida é inválida!!";
	private static final String VALOR_OBRIGATORIO = "Atenção! O valor do item é obrigatório!!";
	private static final String VALOR_INVALIDO = "Atenção! O valor inserida é inválido!!";

	private Long id;
	private String ean;		// --> Codigo Barras
	private String descricaoEmpresa;
	private int codigoAnp;		// --> Deverá ser preenchido pelo OBJ -> TributacaoEstadual
	private Finalidade finalidade = Finalidade.CONSUMO;
	private Finalidade finalidadeEmpresa = Finalidade.COMERCIALIZACAO;	// Essa é a finalidade no cadastro do produto (no ERP), ou seja, sob o enfoque da AG
	private int cfop;
	private Integer icmsCest = 0;
	private BigDecimal icmsBase = BigDecimal.ZERO;
	private BigDecimal icmsReducaoBaseValor = BigDecimal.ZERO;
	private BigDecimal icmsAliquotaDestino = BigDecimal.ZERO;
	private BigDecimal icmsValor = BigDecimal.ZERO;
    private BigDecimal icmsFcpAliquota = BigDecimal.ZERO;
    private BigDecimal icmsFcpValor = BigDecimal.ZERO;
	private BigDecimal icmsStBase = BigDecimal.ZERO;
	private BigDecimal icmsStValor = BigDecimal.ZERO;
    private BigDecimal icmsStBaseRetido = BigDecimal.ZERO;
    private BigDecimal icmsStValorRetido = BigDecimal.ZERO;
//    private BigDecimal icmsBaseFcpStRetido = BigDecimal.ZERO;
//    private BigDecimal icmsAliqFcpStRetido = BigDecimal.ZERO;
//    private BigDecimal icmsValorFcpStRetido = BigDecimal.ZERO;
	private int icmsCst;
	private BigDecimal icmsBaseUfDestino = BigDecimal.ZERO; 		// Aparentemente é (valorUnitario * ) - desconto (ou seja, não entra o frete nessa base)
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
	private BigDecimal ipiValor = BigDecimal.ZERO;
	private int ipiCst;
	
	// Para "atender" a lei "De Olho No Imposto"
	private BigDecimal valorTotalImposto = BigDecimal.ZERO;
	
	private DocumentoFiscal documentoFiscal;

	public DocumentoFiscalItem() {	}

	public DocumentoFiscalItem(DevolucaoItem devoItem, DocumentoFiscal documentoFiscal) {
		super.item = devoItem.getItem();
		super.codigoX = devoItem.getCodigoX();
		super.codigoSequencia = devoItem.getCodigoSequencia();
		super.produtoCodigoErp = devoItem.getProdutoCodigoErp();
		super.classeFiscal = devoItem.getClasseFiscal();
		super.setClasseFiscal(devoItem.getNcm());
		super.quantidade = devoItem.getQuantidade();
		super.valorUnitario = devoItem.getValorUnitario();
		super.desconto = devoItem.getDesconto().multiply(devoItem.getQuantidade());			// Está calculando aqui os descontos das devoluções para devolver corretamente para o ERP
		this.descricaoEmpresa = devoItem.getDescricao();
		super.icmsIva = devoItem.getIcmsIva();
		super.ipiAliquota = devoItem.getIpiAliquota();
		super.origem = devoItem.getOrigem();
		super.unidade = devoItem.getUnidade();
		this.documentoFiscal = documentoFiscal;
	}

	/**
	 * Será copiado: <br>
	 * - produtoCodigoErp <br>
	 * - ean			 <br>
	 * - icmsCest		<br>
	 * @param newItem
	 */
	public void copyValuesToUpdate(DocumentoFiscalItem newItem) {
		super.produtoCodigoErp = (newItem.getProdutoCodigoErp() != 0) ? newItem.getProdutoCodigoErp() : super.produtoCodigoErp;
		this.ean = hasLength(newItem.getEan()) ? newItem.getEan() : this.ean;
		this.icmsCest =  (newItem.getIcmsCest() != null && newItem.getIcmsCest() != 0) ? newItem.getIcmsCest() : this.icmsCest;
//		super.origem;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Override
	public int getItem() {
		return super.item;
	}

	public void setItem(int item) {
		super.item = item;
	}

	@Override
	@Column(name = "codigo_x")
	public Long getCodigoX() {
		return super.codigoX;
	}

	public void setCodigoX(Long codigoX) {
		super.codigoX = codigoX;
	}

	@Override
	@Column(name = "codigo_seq")
	public String getCodigoSequencia() {
		return super.codigoSequencia;
	}

	public void setCodigoSequencia(String codigoSequencia) {
		super.codigoSequencia = codigoSequencia;
	}
	
	@Column(name = "prod_cod_erp")
	public int getProdutoCodigoErp() {
		return produtoCodigoErp;
	}

	public void setProdutoCodigoErp(int produtoCodigoErp) {
		super.produtoCodigoErp = produtoCodigoErp;
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

	@Column(name = "cod_anp", nullable = false)
	public int getCodigoAnp() {
		return codigoAnp;
	}

	public void setCodigoAnp(int codigoAnp) {
		this.codigoAnp = codigoAnp;
	}
	
	@Override
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "prod_unid_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fnk_docu_fisc_item_prod_unid_id"))
//	@JoinColumn(name = "prod_unid_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fnk_docu_fisc_item_prod_unid_id"))
	public ProdutoUnidade getUnidade() {
		return super.unidade;
	}
	
	@Override
	public void setUnidade(ProdutoUnidade unidade) {
		super.unidade = unidade;
	}
	
	@NotNull(message = FINALIDADE_OBRIGATORIA)
	@Enumerated(EnumType.STRING)
	@Column(name="finalidade", columnDefinition="enum('COMERCIALIZACAO', 'BRINDE', 'DOACAO', 'PATRIMONIO', 'CONSUMO') default 'CONSUMO' ")
	public Finalidade getFinalidade() {
		return finalidade;
	}

	public void setFinalidade(Finalidade finalidade) {
		this.finalidade = finalidade;
	}
	
	@Enumerated(EnumType.STRING)
	@Column(name = "finalidade_empresa", columnDefinition="enum('COMERCIALIZACAO', 'BRINDE', 'DOACAO', 'PATRIMONIO', 'CONSUMO') default 'COMERCIALIZACAO' ")
	public Finalidade getFinalidadeEmpresa() {
		return finalidadeEmpresa;
	}

	public void setFinalidadeEmpresa(Finalidade finalidadeEmpresa) {
		this.finalidadeEmpresa = finalidadeEmpresa;
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

//	@NotNull(message = QUANTIDADE_OBRIGATORIA)
//	@Positive(message = QUANTIDADE_INVALIDA)
	public BigDecimal getQuantidade() {
		return super.quantidade;
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
	
	public BigDecimal getDesconto() {
		return desconto;
	}

	public void setDesconto(BigDecimal desconto) {
		this.desconto = desconto;
	}
	
	@Column(name = "vlr_fret")
	public BigDecimal getValorFrete() {
		return valorFrete;
	}

	public void setValorFrete(BigDecimal valorFrete) {
		this.valorFrete = valorFrete;
	}

	@Column(name = "vlr_segu")
	public BigDecimal getValorSeguro() {
		return valorSeguro;
	}

	public void setValorSeguro(BigDecimal valorSeguro) {
		this.valorSeguro = valorSeguro;
	}

	@Column(name = "vlr_outr_desp_acess")
	public BigDecimal getValorOutrasDespesasAcessorias() {
		return super.valorOutrasDespesasAcessorias;
	}

	public void setValorOutrasDespesasAcessorias(BigDecimal valorOutrasDespesasAcessorias) {
		super.valorOutrasDespesasAcessorias = valorOutrasDespesasAcessorias;
	}
	
	@Override
	public String getClasseFiscal() {
		return super.classeFiscal;
	}

	@Override
	public void setClasseFiscal(String classeFiscal) {
		super.classeFiscal = classeFiscal;
	}
	
	@Override
	public int getExcecao() {
		return super.excecao;
	}

	@Override
	public void setExcecao(int excecao) {
		super.excecao = excecao;
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
	
    @Column(name = "icms_redu_base_vlr")
	public BigDecimal getIcmsReducaoBaseValor() {
		return icmsReducaoBaseValor;
	}

	public void setIcmsReducaoBaseValor(BigDecimal icmsReducaoBaseValor) {
		this.icmsReducaoBaseValor = icmsReducaoBaseValor;
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
		super.icmsStAliquota = icmsStAliquota;
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
        super.icmsIva = icmsIva;
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

	@Column(name = "icms_base_uf_dest")
	public BigDecimal getIcmsBaseUfDestino() {
		return icmsBaseUfDestino;
	}

	public void setIcmsBaseUfDestino(BigDecimal icmsBaseUfDestino) {
		this.icmsBaseUfDestino = icmsBaseUfDestino;
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
		super.ipiBase = ipiBase;
	}

	@Column(name = "ipi_aliq", precision = 7, scale = 6, nullable = false, columnDefinition = "Numeric(7,6) default '0.00'")
	public BigDecimal getIpiAliquota() {
		return ipiAliquota;
	}

	public void setIpiAliquota(BigDecimal ipiAliquota) {
		super.ipiAliquota = ipiAliquota;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(documentoFiscal);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
//		if (super.equals(obj) == false) {
//			return false;
//		}
//		DocumentoFiscalItem other = (DocumentoFiscalItem) obj;
//		return Objects.equals(documentoFiscal, other.documentoFiscal);
	}

	
	
//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + ((codigoSequencia == null) ? 0 : codigoSequencia.hashCode());
//		result = prime * result + ((codigoX == null) ? 0 : codigoX.hashCode());
//		result = prime * result + ((desconto == null) ? 0 : desconto.hashCode());
//		result = prime * result + ((descricaoEmpresa == null) ? 0 : descricaoEmpresa.hashCode());
//		result = prime * result + ((finalidade == null) ? 0 : finalidade.hashCode());
//		result = prime * result + item;
//		result = prime * result + ((origem == null) ? 0 : origem.hashCode());
//		result = prime * result + ((quantidade == null) ? 0 : quantidade.hashCode());
//		result = prime * result + ((unidade == null) ? 0 : unidade.hashCode());
//		result = prime * result + ((valorFrete == null) ? 0 : valorFrete.hashCode());
//		result = prime * result + ((valorUnitario == null) ? 0 : valorUnitario.hashCode());
//		return result;
//	}

	/**
	 * Considera se o <b>mesmo item</b> caso os valores abaixo <b> SEJAM IGUAIS:</b> 
	 * 	
	 * 	<p> - Codigo X											</p>	
	 * 	<p> - Codigo Sequencia                                  </p>
	 * 	<p> - DescricaoEmpresa                                  </p>
	 * 	<p> - Finalidade                                        </p>
	 * 	<p> - Desconto                                          </p>
	 *  <p> - item (num do item no DocumentoFiscal/nfe)         </p>
	 *  <p> - Origem                                            </p>
	 *  <p> - Quantidade                                        </p>
	 *  <p> - Valor do Frete                                    </p>
	 *  <p> - Valor Unitário                                    </p>
	 *                                                          </p>
	 */
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		DocumentoFiscalItem other = (DocumentoFiscalItem) obj;
//		if (codigoSequencia == null) {
//			if (other.codigoSequencia != null)
//				return false;
//		} else if (!codigoSequencia.equals(other.codigoSequencia))
//			return false;
//		if (codigoX == null) {
//			if (other.codigoX != null)
//				return false;
//		} else if (!codigoX.equals(other.codigoX))
//			return false;
//		if (desconto == null) {
//			if (other.desconto != null)
//				return false;
//		} else if (desconto.compareTo(other.desconto) != 0)
//			return false;
//		if (descricaoEmpresa == null) {
//			if (other.descricaoEmpresa != null)
//				return false;
//		} else if (!descricaoEmpresa.equals(other.descricaoEmpresa))
//			return false;
//		if (finalidade != other.finalidade)
//			return false;
//		if (item != other.item)
//			return false;
//		if (origem != other.origem)
//			return false;
//		if (quantidade == null) {
//			if (other.quantidade != null)
//				return false;
//		} else if (quantidade.compareTo(other.quantidade) != 0)
//			return false;
//		if (unidade == null) {
//			if (other.unidade != null)
//				return false;
//		} else if (!unidade.equals(other.unidade))
//			return false;
//		if (valorFrete == null) {
//			if (other.valorFrete != null)
//				return false;
//		} else if (valorFrete.compareTo(other.valorFrete) != 0)
//			return false;
//		if (valorUnitario == null) {
//			if (other.valorUnitario != null)
//				return false;
//		} else if (valorUnitario.compareTo(other.valorUnitario) != 0)
//			return false;
//		return true;
//	}
	
	

}
