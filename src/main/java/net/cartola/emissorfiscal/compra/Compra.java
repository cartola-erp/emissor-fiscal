package net.cartola.emissorfiscal.compra;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import net.cartola.emissorfiscal.sped.fiscal.enums.ModeloDocumentoFiscal;



/**
 * 25/09/2020
 * @author robson.costa
 */

/** OS ATRIBUTOS COMENTADOS, acredito que NÃO tem nenhuma utilidade para essa API, sejam para o SPED, ou até mesmo, quando implementar 
 * o calculo de para as operações de devolução **/
public class Compra implements Serializable {
	
	private static final long serialVersionUID = 25875508275822613L;
	
	private int compra;
	private int operacaoCodigo;
	private int loja;
//	private Integer acumuloCodigo;			// Aparentemente esses acumulos tem a ver com boletos, ou seja, acredito que não mude nada nesse projeto
//	private Integer acumuloLoja;
	private Integer transportadoraCodigo;
	private Integer transportadoraLoja;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	private Date cadastro;
	private String criadoPor;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	private Date alterado;
	private String alteradoPor;
	private Integer compraFaturaCodigo;
	private Integer compraFaturaLoja;
	private Integer numeroPedido;
	private long numeroNota;
	private ModeloDocumentoFiscal modelo = ModeloDocumentoFiscal._55;
	private Integer serie;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	private Date emissao;
//	private int cadastroCodigo;
//	private int cadastroLoja;
	private String fornecedorNome;
	private String cadastroCpfCnpj;
	private int modoPgtoCodigo;
	private String modoPgtoDescricao;
//	private boolean alteraCusto = false;
//	private int estoquistaCodigo;
//	private String estoquistaNome = "";
//	private boolean estoqueAlterado;
//	private String codigoVerificacao;
//	private String descricaoServico;
	private double valorFrete;
	private double valorSeguro;
	private double valorTotalDesconto;
	private double valorTotalNota;
	private double valorOutrasDespesas;
	private Double valorIr;
	private Double valorInss;
	private Double valorCsll;
	private Double valorCofins;
	private Double valorPis;
	private Double valorIss;
	private Double aliquotaIss;
	private boolean issReterPeloTomador;
	private Double valorRetencoes;
	private Double valorTotalLiquido;
	private CompraTipo compraTipo = CompraTipo.MERCADORIA_NFE;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	private Date chegada;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	private Date guardaInicio;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	private Date guardaTermino;
	private int volumes;
	private String nfeChaveAcesso;
	private String nfeXml;
	private String nfeXmlCancelamento;
	private String operacaoDescricao;
	private Integer devolucaoLoja;
	private Integer devolucaoCodigo;
	private int reajustesPrecos;
	private String obs;
//	private Long cartolaId;
//	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
//	private Date cartolaTs;
	private Double cteValor;
	private Double cteValorCreditoIcms;
	private Integer centroCustoCodigo;
	private Integer caixaCodigo;
	private Integer caixaLoja;
	private boolean devolucaoEmitidaPeloCliente;
	private boolean redistribuicao;

	private List<CompraItem> itens;
	private List<CompraReferencia> referencias;

//	private List<CompraItemRedistribuicao> itensRedistribuicao;

	public int getCompra() {
		return this.compra;
	}

	public void setCompra(int compra) {
		this.compra = compra;
	}

	public int getOperacaoCodigo() {
		return this.operacaoCodigo;
	}

	public void setOperacaoCodigo(int operacaoCodigo) {
		this.operacaoCodigo = operacaoCodigo;
	}

	public int getLoja() {
		return this.loja;
	}

	public void setLoja(int loja) {
		this.loja = loja;
	}

	public Integer getTransportadoraCodigo() {
		return this.transportadoraCodigo;
	}

	public void setTransportadoraCodigo(Integer transportadoraCodigo) {
		this.transportadoraCodigo = transportadoraCodigo;
	}

	public Integer getTransportadoraLoja() {
		return this.transportadoraLoja;
	}

	public void setTransportadoraLoja(Integer transportadoraLoja) {
		this.transportadoraLoja = transportadoraLoja;
	}

	public Date getCadastro() {
		return this.cadastro;
	}

	public void setCadastro(Date cadastro) {
		this.cadastro = cadastro;
	}

	public String getCriadoPor() {
		return this.criadoPor;
	}

	public void setCriadoPor(String criadoPor) {
		this.criadoPor = criadoPor;
	}

	public Date getAlterado() {
		return this.alterado;
	}

	public void setAlterado(Date alterado) {
		this.alterado = alterado;
	}

	public String getAlteradoPor() {
		return this.alteradoPor;
	}

	public void setAlteradoPor(String alteradoPor) {
		this.alteradoPor = alteradoPor;
	}

	public Integer getCompraFaturaCodigo() {
		return this.compraFaturaCodigo;
	}

	public void setCompraFaturaCodigo(Integer compraFaturaCodigo) {
		this.compraFaturaCodigo = compraFaturaCodigo;
	}

	public Integer getCompraFaturaLoja() {
		return this.compraFaturaLoja;
	}

	public void setCompraFaturaLoja(Integer compraFaturaLoja) {
		this.compraFaturaLoja = compraFaturaLoja;
	}

	public Integer getNumeroPedido() {
		return this.numeroPedido;
	}

	public void setNumeroPedido(Integer numeroPedido) {
		this.numeroPedido = numeroPedido;
	}

	public long getNumeroNota() {
		return this.numeroNota;
	}

	public void setNumeroNota(long numeroNota) {
		this.numeroNota = numeroNota;
	}

	public ModeloDocumentoFiscal getModelo() {
		return this.modelo;
	}

	public void setModelo(ModeloDocumentoFiscal modelo) {
		this.modelo = modelo;
	}

	public Integer getSerie() {
		return this.serie;
	}

	public void setSerie(Integer serie) {
		this.serie = serie;
	}

	public Date getEmissao() {
		return this.emissao;
	}

	public void setEmissao(Date emissao) {
		this.emissao = emissao;
	}

	public String getFornecedorNome() {
		return this.fornecedorNome;
	}

	public void setFornecedorNome(String fornecedorNome) {
		this.fornecedorNome = fornecedorNome;
	}

	public String getCadastroCpfCnpj() {
		return this.cadastroCpfCnpj;
	}

	public void setCadastroCpfCnpj(String cadastroCpfCnpj) {
		this.cadastroCpfCnpj = cadastroCpfCnpj;
	}

	public int getModoPgtoCodigo() {
		return this.modoPgtoCodigo;
	}

	public void setModoPgtoCodigo(int modoPgtoCodigo) {
		this.modoPgtoCodigo = modoPgtoCodigo;
	}

	public String getModoPgtoDescricao() {
		return this.modoPgtoDescricao;
	}

	public void setModoPgtoDescricao(String modoPgtoDescricao) {
		this.modoPgtoDescricao = modoPgtoDescricao;
	}

	public double getValorFrete() {
		return this.valorFrete;
	}

	public void setValorFrete(double valorFrete) {
		this.valorFrete = valorFrete;
	}

	public double getValorSeguro() {
		return this.valorSeguro;
	}

	public void setValorSeguro(double valorSeguro) {
		this.valorSeguro = valorSeguro;
	}

	public double getValorTotalDesconto() {
		return this.valorTotalDesconto;
	}

	public void setValorTotalDesconto(double valorTotalDesconto) {
		this.valorTotalDesconto = valorTotalDesconto;
	}

	public double getValorTotalNota() {
		return this.valorTotalNota;
	}

	public void setValorTotalNota(double valorTotalNota) {
		this.valorTotalNota = valorTotalNota;
	}

	public double getValorOutrasDespesas() {
		return this.valorOutrasDespesas;
	}

	public void setValorOutrasDespesas(double valorOutrasDespesas) {
		this.valorOutrasDespesas = valorOutrasDespesas;
	}

	public Double getValorIr() {
		return this.valorIr;
	}

	public void setValorIr(Double valorIr) {
		this.valorIr = valorIr;
	}

	public Double getValorInss() {
		return this.valorInss;
	}

	public void setValorInss(Double valorInss) {
		this.valorInss = valorInss;
	}

	public Double getValorCsll() {
		return this.valorCsll;
	}

	public void setValorCsll(Double valorCsll) {
		this.valorCsll = valorCsll;
	}

	public Double getValorCofins() {
		return this.valorCofins;
	}

	public void setValorCofins(Double valorCofins) {
		this.valorCofins = valorCofins;
	}

	public Double getValorPis() {
		return this.valorPis;
	}

	public void setValorPis(Double valorPis) {
		this.valorPis = valorPis;
	}

	public Double getValorIss() {
		return this.valorIss;
	}

	public void setValorIss(Double valorIss) {
		this.valorIss = valorIss;
	}

	public Double getAliquotaIss() {
		return this.aliquotaIss;
	}

	public void setAliquotaIss(Double aliquotaIss) {
		this.aliquotaIss = aliquotaIss;
	}

	public boolean getIssReterPeloTomador() {
		return this.issReterPeloTomador;
	}

	public void setIssReterPeloTomador(boolean issReterPeloTomador) {
		this.issReterPeloTomador = issReterPeloTomador;
	}

	public Double getValorRetencoes() {
		return this.valorRetencoes;
	}

	public void setValorRetencoes(Double valorRetencoes) {
		this.valorRetencoes = valorRetencoes;
	}

	public Double getValorTotalLiquido() {
		return this.valorTotalLiquido;
	}

	public void setValorTotalLiquido(Double valorTotalLiquido) {
		this.valorTotalLiquido = valorTotalLiquido;
	}

	public CompraTipo getCompraTipo() {
		return this.compraTipo;
	}

	public void setCompraTipo(CompraTipo compraTipo) {
		this.compraTipo = compraTipo;
	}

	public Date getChegada() {
		return this.chegada;
	}

	public void setChegada(Date chegada) {
		this.chegada = chegada;
	}

	public Date getGuardaInicio() {
		return this.guardaInicio;
	}

	public void setGuardaInicio(Date guardaInicio) {
		this.guardaInicio = guardaInicio;
	}

	public Date getGuardaTermino() {
		return this.guardaTermino;
	}

	public void setGuardaTermino(Date guardaTermino) {
		this.guardaTermino = guardaTermino;
	}

	public int getVolumes() {
		return this.volumes;
	}

	public void setVolumes(int volumes) {
		this.volumes = volumes;
	}

	public String getNfeChaveAcesso() {
		return this.nfeChaveAcesso;
	}

	public void setNfeChaveAcesso(String nfeChaveAcesso) {
		this.nfeChaveAcesso = nfeChaveAcesso;
	}

	public String getNfeXml() {
		return this.nfeXml;
	}

	public void setNfeXml(String nfeXml) {
		this.nfeXml = nfeXml;
	}

	public String getNfeXmlCancelamento() {
		return this.nfeXmlCancelamento;
	}

	public void setNfeXmlCancelamento(String nfeXmlCancelamento) {
		this.nfeXmlCancelamento = nfeXmlCancelamento;
	}

	public String getOperacaoDescricao() {
		return this.operacaoDescricao;
	}

	public void setOperacaoDescricao(String operacaoDescricao) {
		this.operacaoDescricao = operacaoDescricao;
	}

	public Integer getDevolucaoLoja() {
		return this.devolucaoLoja;
	}

	public void setDevolucaoLoja(Integer devolucaoLoja) {
		this.devolucaoLoja = devolucaoLoja;
	}

	public Integer getDevolucaoCodigo() {
		return this.devolucaoCodigo;
	}

	public void setDevolucaoCodigo(Integer devolucaoCodigo) {
		this.devolucaoCodigo = devolucaoCodigo;
	}

	public int getReajustesPrecos() {
		return this.reajustesPrecos;
	}

	public void setReajustesPrecos(int reajustesPrecos) {
		this.reajustesPrecos = reajustesPrecos;
	}

	public String getObs() {
		return this.obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	public Double getCteValor() {
		return this.cteValor;
	}

	public void setCteValor(Double cteValor) {
		this.cteValor = cteValor;
	}

	public Double getCteValorCreditoIcms() {
		return this.cteValorCreditoIcms;
	}

	public void setCteValorCreditoIcms(Double cteValorCreditoIcms) {
		this.cteValorCreditoIcms = cteValorCreditoIcms;
	}

	public Integer getCentroCustoCodigo() {
		return this.centroCustoCodigo;
	}

	public void setCentroCustoCodigo(Integer centroCustoCodigo) {
		this.centroCustoCodigo = centroCustoCodigo;
	}

	public Integer getCaixaCodigo() {
		return this.caixaCodigo;
	}

	public void setCaixaCodigo(Integer caixaCodigo) {
		this.caixaCodigo = caixaCodigo;
	}

	public Integer getCaixaLoja() {
		return this.caixaLoja;
	}

	public void setCaixaLoja(Integer caixaLoja) {
		this.caixaLoja = caixaLoja;
	}

	public boolean isDevolucaoEmitidaPeloCliente() {
		return devolucaoEmitidaPeloCliente;
	}

	public void setDevolucaoEmitidaPeloCliente(boolean devolucaoEmitidaPeloCliente) {
		this.devolucaoEmitidaPeloCliente = devolucaoEmitidaPeloCliente;
	}

	public List<CompraItem> getItens() {
		return itens;
	}

	public void setItens(List<CompraItem> itens) {
		this.itens = itens;
	}

	public List<CompraReferencia> getReferencias() {
		return referencias;
	}

	public void setReferencias(List<CompraReferencia> referencias) {
		this.referencias = referencias;
	}
	
	public boolean isRedistribuicao() {
		return redistribuicao;
	}

	public void setRedistribuicao(boolean isRedistricao) {
		this.redistribuicao = isRedistricao;
	}

	@Override
	public String toString() {
		return "Compra [compra=" + compra + ", operacaoCodigo=" + operacaoCodigo + ", loja=" + loja + ", transportadoraCodigo=" 
				+ transportadoraCodigo + ", transportadoraLoja=" + transportadoraLoja + ", cadastro=" + cadastro + ", criadoPor=" + criadoPor
				+ ", alterado=" + alterado + ", alteradoPor=" + alteradoPor + ", compraFaturaCodigo="
				+ compraFaturaCodigo + ", compraFaturaLoja=" + compraFaturaLoja + ", numeroPedido=" + numeroPedido
				+ ", numeroNota=" + numeroNota + ", modelo=" + modelo + ", serie=" + serie + ", emissao=" + emissao
				+ ", fornecedorNome=" + fornecedorNome + ", cadastroCpfCnpj=" + cadastroCpfCnpj + ", modoPgtoCodigo="
				+ modoPgtoCodigo + ", modoPgtoDescricao=" + modoPgtoDescricao + ", valorFrete=" + valorFrete
				+ ", valorSeguro=" + valorSeguro + ", valorTotalDesconto=" + valorTotalDesconto + ", valorTotalNota="
				+ valorTotalNota + ", valorOutrasDespesas=" + valorOutrasDespesas + ", valorIr=" + valorIr
				+ ", valorInss=" + valorInss + ", valorCsll=" + valorCsll + ", valorCofins=" + valorCofins
				+ ", valorPis=" + valorPis + ", valorIss=" + valorIss + ", aliquotaIss=" + aliquotaIss
				+ ", issReterPeloTomador=" + issReterPeloTomador + ", valorRetencoes=" + valorRetencoes
				+ ", valorTotalLiquido=" + valorTotalLiquido + ", compraTipo=" + compraTipo + ", chegada=" + chegada
				+ ", guardaInicio=" + guardaInicio + ", guardaTermino=" + guardaTermino + ", volumes=" + volumes
				+ ", nfeChaveAcesso=" + nfeChaveAcesso + ", nfeXml=" + nfeXml + ", nfeXmlCancelamento="
				+ nfeXmlCancelamento + ", operacaoDescricao=" + operacaoDescricao + ", devolucaoLoja=" + devolucaoLoja
				+ ", devolucaoCodigo=" + devolucaoCodigo + ", reajustesPrecos=" + reajustesPrecos + ", obs=" + obs
				+ ", cteValor=" + cteValor+ ", cteValorCreditoIcms=" + cteValorCreditoIcms + ", centroCustoCodigo=" + centroCustoCodigo
				+ ", caixaCodigo=" + caixaCodigo + ", caixaLoja=" + caixaLoja + ", devolucaoEmitidaPeloCliente="
				+ devolucaoEmitidaPeloCliente + ", redistribuicao=" + redistribuicao + ", itens=" + itens + "]";
	}


}
