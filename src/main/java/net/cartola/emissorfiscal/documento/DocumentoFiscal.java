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
import net.cartola.emissorfiscal.devolucao.Devolucao;
import net.cartola.emissorfiscal.loja.Loja;
import net.cartola.emissorfiscal.operacao.Operacao;
import net.cartola.emissorfiscal.pessoa.Pessoa;
import net.cartola.emissorfiscal.sped.fiscal.enums.FreteConta;
import net.cartola.emissorfiscal.sped.fiscal.enums.IndicadorDePagamento;
import net.cartola.emissorfiscal.sped.fiscal.enums.ModeloDocumentoFiscal;
import net.cartola.emissorfiscal.util.LocalDateDeserializer;
import net.cartola.emissorfiscal.util.LocalDateTimeDeserializer;

@ToString
@Entity
@Table(name = "docu_fisc")
public class DocumentoFiscal extends Documento<DocumentoFiscalItem> implements Serializable {

	private static final long serialVersionUID = 250495916716488531L;

	private static final String ESTADO_EMITENTE_OBRIGATORIO = "Atenção!! A informação referente a estado emitente é obrigatória!";
	private static final String ESTADO_DESTINATARIO_OBRIGATORIO = "Atenção!! A informação referente a estado destinatário é obrigatória!";
	private static final String PESSOA_TIPO_DESTINATARIO_OBRIGATORIO = "Atenção!! A informação referente a ao tipo de pessoa (Física ou Jurídica) é obrigatória!";

	private Long id;
    private int documento;		// Campo referente a COMPRA ou NFE (do ERP, dependendo do tipo do DocumentoFiscal, que for)
	private Long numeroNota;
	private IndicadorDeOperacao tipoOperacao;
	private FinalidadeEmissao finalidadeEmissao = FinalidadeEmissao.NORMAL;
	private TipoServico tipoServico = TipoServico.NENHUM;
	private ModeloDocumentoFiscal modelo = ModeloDocumentoFiscal._55;
    private NFeStatus status;
	private IndicadorDePagamento indicadorPagamento;
	private String nfeChaveAcesso;

	private Long serie;
	private Set<DocumentoFiscalReferencia> referencias;
	private BigDecimal valorDesconto;
	private FreteConta indicadorFrete;
	private BigDecimal valorFrete;
	private BigDecimal valorSeguro;
	private BigDecimal valorOutrasDespesasAcessorias;
	
	private BigDecimal icmsBase = BigDecimal.ZERO;					// == vBC (Valor Base Calculo)
	private BigDecimal icmsValor = BigDecimal.ZERO;
	private BigDecimal icmsValorDesonerado = BigDecimal.ZERO;
    private BigDecimal icmsFcpValor = BigDecimal.ZERO;
	private BigDecimal icmsStBase = BigDecimal.ZERO;
	private BigDecimal icmsStValor = BigDecimal.ZERO;
//    private BigDecimal icmsFcpStValor = BigDecimal.ZERO;
//    private BigDecimal icmsFcpStRetidoValor = BigDecimal.ZERO;
    private BigDecimal icmsValorUfDestino = BigDecimal.ZERO;		// É a soma do DIFAL
    private BigDecimal valorTotalProduto = BigDecimal.ZERO;
	private BigDecimal pisBase = BigDecimal.ZERO;				
	private BigDecimal pisValor = BigDecimal.ZERO;
	private BigDecimal cofinsBase = BigDecimal.ZERO;
	private BigDecimal cofinsValor = BigDecimal.ZERO;
	private BigDecimal ipiBase = BigDecimal.ZERO;
	private BigDecimal ipiValor = BigDecimal.ZERO;
    private BigDecimal valorTotalDocumento = BigDecimal.ZERO;		// vNF
    
	private String infoAdicionalFisco;
	private String infoComplementar;
	@ToString.Exclude String xml;

	// Para "atender" a lei "De Olho No Imposto"
	private BigDecimal valorImpostoFederal = BigDecimal.ZERO;
	private BigDecimal valorImpostoEstadual = BigDecimal.ZERO;
	private BigDecimal valorImpostoMunicipal = BigDecimal.ZERO;
	
	private LocalDate emissao;
//	private LocalDateTime cadastro;
//	private String criadoPor;
//	private LocalDateTime alterado;
//	private String alteradoPor;
	
	public DocumentoFiscal() {}

	/**
	 * Será copiado: A operação, loja, emitente e destinatario da devolução;
	 * PS: Não será criado os itens
	 * 
	 * @param devolucao
	 */
	public DocumentoFiscal(Devolucao devolucao) {
		super.operacao = devolucao.getOperacao();
		super.loja = devolucao.getLoja();
		super.emitente = devolucao.getEmitente();
		super.destinatario = devolucao.getDestinatario();
//		super.itens = new ArrayList<>();
		
//		devolucao.getItens().forEach(devoItem -> {
//			super.itens.add(new DocumentoFiscalItem(devoItem));
//		});
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "docu")
	public int getDocumento() {
		return documento;
	}

	public void setDocumento(int documento) {
		this.documento = documento;
	}

	public void setNumeroNota(Long numeroNota) {
		this.numeroNota = numeroNota;
	}
	
	public Long getNumeroNota() {
		return numeroNota;
	}
	
	@Override
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "oper_id", referencedColumnName = "oper_id", nullable = false, foreignKey = @ForeignKey(name = "fnk_docu_fisc_oper_id"))
	public Operacao getOperacao() {
		return operacao;
	}

	@Override
	public void setOperacao(Operacao operacao) {
		this.operacao = operacao;
	}

	@Enumerated(EnumType.STRING)
	@Column(name="tipo_oper", columnDefinition="enum('ENTRADA', 'SAIDA') ")
	public IndicadorDeOperacao getTipoOperacao() {
		return tipoOperacao;
	}

	public void setTipoOperacao(IndicadorDeOperacao tipoOperacao) {
		this.tipoOperacao = tipoOperacao;
	}
	
	@Enumerated(EnumType.STRING)
	@Column(name="fina_emiss", columnDefinition="enum('NORMAL', 'COMPLEMENTAR', 'AJUSTE', 'DEVOLUCAO_MERCADORIA') default 'NORMAL' ")
	public FinalidadeEmissao getFinalidadeEmissao() {
		return finalidadeEmissao;
	}

	public void setFinalidadeEmissao(FinalidadeEmissao finalidadeEmissao) {
		this.finalidadeEmissao = finalidadeEmissao;
	}
	
	@Enumerated(EnumType.STRING)
	@Column(name="tipo_serv", columnDefinition="enum('NENHUM', 'OUTROS', 'CTE', 'ENERGIA', 'AGUA', 'INTERNET', 'TELEFONE_FIXO_MOVEL') default 'NENHUM' ")
	public TipoServico getTipoServico() {
		return tipoServico;
	}

	public void setTipoServico(TipoServico tipoServico) {
		this.tipoServico = tipoServico;
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
			
	@Enumerated(EnumType.STRING)
	@Column(name = "indi_paga", columnDefinition ="enum('A_VISTA', 'A_PRAZO', 'OUTROS') ")
	public IndicadorDePagamento getIndicadorPagamento() {
		return indicadorPagamento;
	}

	public void setIndicadorPagamento(IndicadorDePagamento indicadorPagamento) {
		this.indicadorPagamento = indicadorPagamento;
	}
	
	@Column(length = 44)
	public String getNfeChaveAcesso() {
		return nfeChaveAcesso;
	}

	public void setNfeChaveAcesso(String nfeChaveAcesso) {
		this.nfeChaveAcesso = nfeChaveAcesso;
	}
	
	public void setSerie(Long serie) {
		this.serie = serie;
	}
	
	public Long getSerie() {
		return serie;
	}

	@Override
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "loja_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fnk_docu_fisc_loja_id"))
	public Loja getLoja() {
		return loja;
	}

	@Override
	public void setLoja(Loja loja) {
		this.loja = loja;
	}
	
	@Override
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "emit_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fnk_docu_fisc_emitente_id"))
	public Pessoa getEmitente() {
		return emitente;
	}

	@Override
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "dest_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fnk_docu_fisc_destinatario_id"))
	public Pessoa getDestinatario() {
		return destinatario;
	}

	@Override
	public void setEmitente(Pessoa emitente) {
		this.emitente = emitente;
	}

	@Override
	public void setDestinatario(Pessoa destinatario) {
		this.destinatario = destinatario;
	}
	
	@Override
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

	@Column(name = "vlr_desconto")
	public BigDecimal getValorDesconto() {
		return valorDesconto;
	}

	public void setValorDesconto(BigDecimal valorDesconto) {
		this.valorDesconto = valorDesconto;
	}
	
//	@Column(name = "indi_fret", columnDefinition ="enum('0', '1', '2', '3', '4', '5') DEFAULT '5' ")
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "indi_fret")
	public FreteConta getIndicadorFrete() {
		return indicadorFrete;
	}

	public void setIndicadorFrete(FreteConta indicadorFrete) {
		this.indicadorFrete = indicadorFrete;
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
		return valorOutrasDespesasAcessorias;
	}

	public void setValorOutrasDespesasAcessorias(BigDecimal valorOutrasDespesasAcessorias) {
		this.valorOutrasDespesasAcessorias = valorOutrasDespesasAcessorias;
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
	public BigDecimal getValorTotalProduto() {
		return valorTotalProduto;
	}

	public void setValorTotalProduto(BigDecimal valorTotalProduto) {
		this.valorTotalProduto = valorTotalProduto;
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
	
	@Column(name = "vlr_tot_doc", precision = 19, scale = 2, nullable = false, columnDefinition = "Numeric(19,2)")
	public BigDecimal getValorTotalDocumento() {
		return valorTotalDocumento;
	}

	public void setValorTotalDocumento(BigDecimal valorTotalDocumento) {
		this.valorTotalDocumento = valorTotalDocumento;
	}
	
	@Column(columnDefinition = "mediumtext")
	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}
	
	@Column(name = "info_fisco", length = 2000)
	public String getInfoAdicionalFisco() {
		return infoAdicionalFisco;
	}

	public void setInfoAdicionalFisco(String infoAdicionalFisco) {
		this.infoAdicionalFisco = infoAdicionalFisco;
	}
	
	@Column(name = "info_compl", length = 5000)
	public String getInfoComplementar() {
		return infoComplementar;
	}

	public void setInfoComplementar(String infoComplementar) {
		this.infoComplementar = infoComplementar;
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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cofinsBase == null) ? 0 : cofinsBase.hashCode());
		result = prime * result + ((cofinsValor == null) ? 0 : cofinsValor.hashCode());
		result = prime * result + ((destinatario == null) ? 0 : destinatario.hashCode());
		result = prime * result + documento;
		result = prime * result + ((emissao == null) ? 0 : emissao.hashCode());
		result = prime * result + ((emitente == null) ? 0 : emitente.hashCode());
		result = prime * result + ((icmsBase == null) ? 0 : icmsBase.hashCode());
		result = prime * result + ((icmsFcpValor == null) ? 0 : icmsFcpValor.hashCode());
		result = prime * result + ((icmsStBase == null) ? 0 : icmsStBase.hashCode());
		result = prime * result + ((icmsStValor == null) ? 0 : icmsStValor.hashCode());
		result = prime * result + ((icmsValor == null) ? 0 : icmsValor.hashCode());
		result = prime * result + ((icmsValorDesonerado == null) ? 0 : icmsValorDesonerado.hashCode());
		result = prime * result + ((icmsValorUfDestino == null) ? 0 : icmsValorUfDestino.hashCode());
		result = prime * result + ((indicadorFrete == null) ? 0 : indicadorFrete.hashCode());
		result = prime * result + ((indicadorPagamento == null) ? 0 : indicadorPagamento.hashCode());
		result = prime * result + ((ipiBase == null) ? 0 : ipiBase.hashCode());
		result = prime * result + ((ipiValor == null) ? 0 : ipiValor.hashCode());
		result = prime * result + ((itens == null) ? 0 : itens.hashCode());
		result = prime * result + ((modelo == null) ? 0 : modelo.hashCode());
		result = prime * result + ((numeroNota == null) ? 0 : numeroNota.hashCode());
		result = prime * result + ((operacao == null) ? 0 : operacao.hashCode());
		result = prime * result + ((pisBase == null) ? 0 : pisBase.hashCode());
		result = prime * result + ((pisValor == null) ? 0 : pisValor.hashCode());
		result = prime * result + ((serie == null) ? 0 : serie.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((tipoOperacao == null) ? 0 : tipoOperacao.hashCode());
		result = prime * result + ((valorDesconto == null) ? 0 : valorDesconto.hashCode());
		result = prime * result + ((valorFrete == null) ? 0 : valorFrete.hashCode());
		result = prime * result + ((valorImpostoEstadual == null) ? 0 : valorImpostoEstadual.hashCode());
		result = prime * result + ((valorImpostoFederal == null) ? 0 : valorImpostoFederal.hashCode());
		result = prime * result + ((valorImpostoMunicipal == null) ? 0 : valorImpostoMunicipal.hashCode());
		result = prime * result
				+ ((valorOutrasDespesasAcessorias == null) ? 0 : valorOutrasDespesasAcessorias.hashCode());
		result = prime * result + ((valorSeguro == null) ? 0 : valorSeguro.hashCode());
		result = prime * result + ((valorTotalProduto == null) ? 0 : valorTotalProduto.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DocumentoFiscal other = (DocumentoFiscal) obj;
		if (cofinsBase == null) {
			if (other.cofinsBase != null)
				return false;
		} else if (cofinsBase.compareTo(other.cofinsBase) != 0)
			return false;
		if (cofinsValor == null) {
			if (other.cofinsValor != null)
				return false;
		} else if (cofinsValor.compareTo(other.cofinsValor) != 0)
			return false;
		if (destinatario == null) {
			if (other.destinatario != null)
				return false;
		} else if (!destinatario.equals(other.destinatario))
			return false;
		if (documento != other.documento)
			return false;
		if (emissao == null) {
			if (other.emissao != null)
				return false;
		} else if (!emissao.equals(other.emissao))
			return false;
		if (emitente == null) {
			if (other.emitente != null)
				return false;
		} else if (!emitente.equals(other.emitente))
			return false;
		if (icmsBase == null) {
			if (other.icmsBase != null)
				return false;
		} else if (icmsBase.compareTo(other.icmsBase) != 0)
			return false;
		if (icmsFcpValor == null) {
			if (other.icmsFcpValor != null)
				return false;
		} else if (icmsFcpValor.compareTo(other.icmsFcpValor) != 0)
			return false;
		if (icmsStBase == null) {
			if (other.icmsStBase != null)
				return false;
		} else if (icmsStBase.compareTo(other.icmsStBase) != 0)
			return false;
		if (icmsStValor == null) {
			if (other.icmsStValor != null)
				return false;
		} else if (icmsStValor.compareTo(other.icmsStValor) != 0)
			return false;
		if (icmsValor == null) {
			if (other.icmsValor != null)
				return false;
		} else if (icmsValor.compareTo(other.icmsValor) != 0)
			return false;
		if (icmsValorDesonerado == null) {
			if (other.icmsValorDesonerado != null)
				return false;
		} else if (icmsValorDesonerado.compareTo(other.icmsValorDesonerado) != 0)
			return false;
		if (icmsValorUfDestino == null) {
			if (other.icmsValorUfDestino != null)
				return false;
		} else if (icmsValorUfDestino.compareTo(other.icmsValorUfDestino) != 0)
			return false;
		if (indicadorFrete != other.indicadorFrete)
			return false;
		if (indicadorPagamento != other.indicadorPagamento)
			return false;
		if (ipiBase == null) {
			if (other.ipiBase != null)
				return false;
		} else if (ipiBase.compareTo(other.ipiBase) != 0)
			return false;
		if (ipiValor == null) {
			if (other.ipiValor != null)
				return false;
		} else if (ipiValor.compareTo(other.ipiValor) != 0)
			return false;
		if (itens == null) {
			if (other.itens != null)
				return false;
		} else if (!itens.equals(other.itens))
			return false;
		if (modelo != other.modelo)
			return false;
		if (numeroNota == null) {
			if (other.numeroNota != null)
				return false;
		} else if (numeroNota.compareTo(other.numeroNota) != 0)
			return false;
		if (operacao == null) {
			if (other.operacao != null)
				return false;
		} else if (!operacao.equals(other.operacao))
			return false;
		if (pisBase == null) {
			if (other.pisBase != null)
				return false;
		} else if (pisBase.compareTo(other.pisBase) != 0)
			return false;
		if (pisValor == null) {
			if (other.pisValor != null)
				return false;
		} else if (pisValor.compareTo(other.pisValor) != 0)
			return false;
		if (serie == null) {
			if (other.serie != null)
				return false;
		} else if (serie.compareTo(other.serie) != 0)
			return false;
		if (tipoOperacao != other.tipoOperacao)
			return false;
		if (valorDesconto == null) {
			if (other.valorDesconto != null)
				return false;
		} else if (valorDesconto.compareTo(other.valorDesconto) != 0)
			return false;
		if (valorFrete == null) {
			if (other.valorFrete != null)
				return false;
		} else if (valorFrete.compareTo(other.valorFrete) != 0)
			return false;
		if (valorImpostoEstadual == null) {
			if (other.valorImpostoEstadual != null)
				return false;
		} else if (valorImpostoEstadual.compareTo(other.valorImpostoEstadual) != 0)
			return false;
		if (valorImpostoFederal == null) {
			if (other.valorImpostoFederal != null)
				return false;
		} else if (valorImpostoFederal.compareTo(other.valorImpostoFederal) != 0)
			return false;
		if (valorImpostoMunicipal == null) {
			if (other.valorImpostoMunicipal != null)
				return false;
		} else if (valorImpostoMunicipal.compareTo(other.valorImpostoMunicipal) != 0)
			return false;
		if (valorOutrasDespesasAcessorias == null) {
			if (other.valorOutrasDespesasAcessorias != null)
				return false;
		} else if (valorOutrasDespesasAcessorias.compareTo(other.valorOutrasDespesasAcessorias) != 0)
			return false;
		if (valorSeguro == null) {
			if (other.valorSeguro != null)
				return false;
		} else if (valorSeguro.compareTo(other.valorSeguro) != 0)
			return false;
		if (valorTotalProduto == null) {
			if (other.valorTotalProduto != null)
				return false;
		} else if (valorTotalProduto.compareTo(other.valorTotalProduto) != 0)
			return false;
		return true;
	}

}
