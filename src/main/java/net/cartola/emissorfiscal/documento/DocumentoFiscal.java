package net.cartola.emissorfiscal.documento;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.ToString;
import net.cartola.emissorfiscal.operacao.Operacao;
import net.cartola.emissorfiscal.pessoa.Pessoa;
import net.cartola.emissorfiscal.sped.fiscal.enums.ModeloDocumentoFiscal;
import net.cartola.emissorfiscal.util.LocalDateDeserializer;
import net.cartola.emissorfiscal.util.LocalDateTimeDeserializer;

@ToString
@Entity
@Table(name = "docu_fisc")
public class DocumentoFiscal implements Serializable {

	private static final long serialVersionUID = 250495916716488531L;

	private static final String ESTADO_EMITENTE_OBRIGATORIO = "Atenção!! A informação referente a estado emitente é obrigatória!";
	private static final String ESTADO_DESTINATARIO_OBRIGATORIO = "Atenção!! A informação referente a estado destinatário é obrigatória!";
	private static final String PESSOA_TIPO_DESTINATARIO_OBRIGATORIO = "Atenção!! A informação referente a ao tipo de pessoa (Física ou Jurídica) é obrigatória!";

	private Long id;
	private Operacao operacao;
	private DocumentoFiscalTipoOperacao tipoOperacao;
	private Long serie;
	private Long numero;
	private Pessoa emitente;
	private Pessoa destinatario;
	private List<DocumentoFiscalItem> itens;
	private Set<DocumentoFiscalReferencia> referencias;
	private BigDecimal icmsBase = BigDecimal.ZERO;
	private BigDecimal icmsValor = BigDecimal.ZERO;
	private BigDecimal icmsValorDesonerado = BigDecimal.ZERO;
    private BigDecimal icmsFcpValor = BigDecimal.ZERO;
	private BigDecimal icmsStBase = BigDecimal.ZERO;
	private BigDecimal icmsStValor = BigDecimal.ZERO;
//    private BigDecimal icmsFcpStValor = BigDecimal.ZERO;
//    private BigDecimal icmsFcpStRetidoValor = BigDecimal.ZERO;
    private BigDecimal icmsValorUfDestino = BigDecimal.ZERO;		// É a soma do DIFAL
    private BigDecimal vlrTotalProduto = BigDecimal.ZERO;
	private BigDecimal pisBase = BigDecimal.ZERO;				// Acredito que só precise da "BASE do ICMS" (aparentemente é o msm)
	private BigDecimal pisValor = BigDecimal.ZERO;
	private BigDecimal cofinsBase = BigDecimal.ZERO;			// Acredito que só precise da "BASE do ICMS" (aparentemente é o msm)
	private BigDecimal cofinsValor = BigDecimal.ZERO;
	private BigDecimal ipiBase = BigDecimal.ZERO;				// Acredito que só precise da "BASE do ICMS" (aparentemente é o msm)
	private BigDecimal ipiValor = BigDecimal.ZERO;
    
	private ModeloDocumentoFiscal modelo = ModeloDocumentoFiscal._55;
    private NFeStatus status;
	private String nfeChaveAcesso;
	private LocalDate emissao;
	private LocalDateTime cadastro;
	private String criadoPor;
	private LocalDateTime alterado;
	private String alteradoPor;
	
	// Para "atender" a lei "De Olho No Imposto"
	private BigDecimal valorImpostoFederal = BigDecimal.ZERO;
	private BigDecimal valorImpostoEstadual = BigDecimal.ZERO;
	private BigDecimal valorImpostoMunicipal = BigDecimal.ZERO;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "operacao_id", referencedColumnName = "oper_id", nullable = false, foreignKey = @ForeignKey(name = "fnk_docu_fisc_operacao_id"))
	public Operacao getOperacao() {
		return operacao;
	}

	public void setOperacao(Operacao operacao) {
		this.operacao = operacao;
	}

	@Enumerated(EnumType.STRING)
	@Column(name="tipo_oper", columnDefinition="enum('ENTRADA', 'SAIDA') ")
	public DocumentoFiscalTipoOperacao getTipoOperacao() {
		return tipoOperacao;
	}

	public void setTipoOperacao(DocumentoFiscalTipoOperacao tipoOperacao) {
		this.tipoOperacao = tipoOperacao;
	}
	
	public void setSerie(Long serie) {
		this.serie = serie;
	}
	
	public Long getSerie() {
		return serie;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}
	
	public Long getNumero() {
		return numero;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "emitente_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fnk_docu_fisc_emitente_id"))
	public Pessoa getEmitente() {
		return emitente;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "destinatario_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fnk_docu_fisc_destinatario_id"))
	public Pessoa getDestinatario() {
		return destinatario;
	}

	public void setEmitente(Pessoa emitente) {
		this.emitente = emitente;
	}

	public void setDestinatario(Pessoa destinatario) {
		this.destinatario = destinatario;
	}

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "documentoFiscal")
	public List<DocumentoFiscalItem> getItens() {
		return itens;
	}

	public void setItens(List<DocumentoFiscalItem> itens) {
		this.itens = itens;
	}

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "documentoFiscal")
	public Set<DocumentoFiscalReferencia> getReferencias() {
		return referencias;
	}

	public void setReferencias(Set<DocumentoFiscalReferencia> referencias) {
		this.referencias = referencias;
	}

	public BigDecimal getIcmsBase() {
		return icmsBase;
	}

	public void setIcmsBase(BigDecimal icmsBase) {
		this.icmsBase = icmsBase;
	}

	public BigDecimal getIcmsValor() {
		return icmsValor;
	}

	public void setIcmsValor(BigDecimal icmsValor) {
		this.icmsValor = icmsValor;
	}

    @Column(name = "icms_vlr_deson", precision = 19, scale = 2, nullable = false, columnDefinition = "Numeric(19,2)")
	public BigDecimal getIcmsValorDesonerado() {
		return icmsValorDesonerado;
	}

	public void setIcmsValorDesonerado(BigDecimal icmsValorDesonerado) {
		this.icmsValorDesonerado = icmsValorDesonerado;
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
	
    @Column(name = "icms_fcp_vlr", precision = 19, scale = 2, nullable = false, columnDefinition = "Numeric(19,2)")
	public BigDecimal getIcmsFcpValor() {
		return icmsFcpValor;
	}

	public void setIcmsFcpValor(BigDecimal icmsFcpValor) {
		this.icmsFcpValor = icmsFcpValor;
	}

//	@Column(name = "icms_fcp_st_vlr", precision = 7, scale = 6, nullable = false, columnDefinition = "Numeric(7,6) default '0.00'")
//	public BigDecimal getIcmsFcpStValor() {
//		return icmsFcpStValor;
//	}
//
//	public void setIcmsFcpStValor(BigDecimal icmsFcpStValor) {
//		this.icmsFcpStValor = icmsFcpStValor;
//	}

//	@Column(name = "icms_fcp_st_ret_vlr", precision = 7, scale = 6, nullable = false, columnDefinition = "Numeric(7,6) default '0.00'")
//	public BigDecimal getIcmsFcpStRetidoValor() {
//		return icmsFcpStRetidoValor;
//	}
//
//	public void setIcmsFcpStRetidoValor(BigDecimal icmsFcpStRetidoValor) {
//		this.icmsFcpStRetidoValor = icmsFcpStRetidoValor;
//	}

	@Column(name = "icms_vlr_uf_dest", precision = 19, scale = 2, nullable = false, columnDefinition = "Numeric(19,2)")
    public BigDecimal getIcmsValorUfDestino() {
		return icmsValorUfDestino;
	}

	public void setIcmsValorUfDestino(BigDecimal icmsValorUfDestino) {
		this.icmsValorUfDestino = icmsValorUfDestino;
	}

//	@Column(name = "vlr_tot_prod", precision = 7, scale = 6, nullable = false, columnDefinition = "Numeric(7,6) default '0.00'")
	@Column(name = "vlr_tot_prod", precision = 19, scale = 2, nullable = false, columnDefinition = "Numeric(19,2)")
	public BigDecimal getVlrTotalProduto() {
		return vlrTotalProduto;
	}

	public void setVlrTotalProduto(BigDecimal vlrTotalProduto) {
		this.vlrTotalProduto = vlrTotalProduto;
	}

	public BigDecimal getPisBase() {
		return pisBase;
	}

	public void setPisBase(BigDecimal pisBase) {
		this.pisBase = pisBase;
	}

	public BigDecimal getPisValor() {
		return pisValor;
	}

	public void setPisValor(BigDecimal pisValor) {
		this.pisValor = pisValor;
	}

	public BigDecimal getCofinsBase() {
		return cofinsBase;
	}

	public void setCofinsBase(BigDecimal cofinsBase) {
		this.cofinsBase = cofinsBase;
	}

	public BigDecimal getCofinsValor() {
		return cofinsValor;
	}

	public void setCofinsValor(BigDecimal cofinsValor) {
		this.cofinsValor = cofinsValor;
	}

	public BigDecimal getIpiBase() {
		return ipiBase;
	}

	public void setIpiBase(BigDecimal ipiBase) {
		this.ipiBase = ipiBase;
	}

	public BigDecimal getIpiValor() {
		return ipiValor;
	}

	public void setIpiValor(BigDecimal ipiValor) {
		this.ipiValor = ipiValor;
	}
	
	@Enumerated(EnumType.STRING)
	public ModeloDocumentoFiscal getModelo() {
		return modelo;
	}

	public void setModelo(ModeloDocumentoFiscal modelo) {
		this.modelo = modelo;
	}

	@Enumerated(EnumType.STRING)
	@Column(columnDefinition="enum('DIGITACAO', 'VALIDADA', 'ASSINADA', 'PROCESSAMENTO', 'AUTORIZADA', 'CANCELADA', 'INUTILIZADA', 'DENEGADA') ")
	public NFeStatus getStatus() {
		return status;
	}

	public void setStatus(NFeStatus status) {
		this.status = status;
	}

	@Column(length = 44)
	public String getNfeChaveAcesso() {
		return nfeChaveAcesso;
	}

	public void setNfeChaveAcesso(String nfeChaveAcesso) {
		this.nfeChaveAcesso = nfeChaveAcesso;
	}

	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	public LocalDate getEmissao() {
		return emissao;
	}

	public void setEmissao(LocalDate emissao) {
		this.emissao = emissao;
	}
	
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	public LocalDateTime getCadastro() {
		return cadastro;
	}

	public void setCadastro(LocalDateTime cadastro) {
		this.cadastro = cadastro;
	}
	
	public String getCriadoPor() {
		return criadoPor;
	}

	public void setCriadoPor(String criadoPor) {
		this.criadoPor = criadoPor;
	}

	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	public LocalDateTime getAlterado() {
		return alterado;
	}

	public void setAlterado(LocalDateTime alterado) {
		this.alterado = alterado;
	}

	public String getAlteradoPor() {
		return alteradoPor;
	}

	public void setAlteradoPor(String alteradoPor) {
		this.alteradoPor = alteradoPor;
	}

	@Column(name = "vlr_imposto_fede")
	public BigDecimal getValorImpostoFederal() {
		return valorImpostoFederal;
	}

	/**
	 * Para o "IBPT" - LEI: ("De olho no imposto")
	 * @param valorImpostoFederal
	 */
	public void setValorImpostoFederal(BigDecimal valorImpostoFederal) {
		this.valorImpostoFederal = valorImpostoFederal;
	}
	
	@Column(name = "vlr_imposto_esta")
	public BigDecimal getValorImpostoEstadual() {
		return valorImpostoEstadual;
	}

	/**
	 * Para o "IBPT" - LEI: ("De olho no imposto")
	 * @param valorImpostoEstadual
	 */
	public void setValorImpostoEstadual(BigDecimal valorImpostoEstadual) {
		this.valorImpostoEstadual = valorImpostoEstadual;
	}

	@Column(name = "vlr_imposto_muni")
	public BigDecimal getValorImpostoMunicipal() {
		return valorImpostoMunicipal;
	}

	/**
	 * Para o "IBPT" - LEI: ("De olho no imposto")
	 * @param valorImpostoMunicipal
	 */
	public void setValorImpostoMunicipal(BigDecimal valorImpostoMunicipal) {
		this.valorImpostoMunicipal = valorImpostoMunicipal;
	}

}
