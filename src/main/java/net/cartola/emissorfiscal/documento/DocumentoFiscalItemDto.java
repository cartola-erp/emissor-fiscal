package net.cartola.emissorfiscal.documento;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import net.cartola.emissorfiscal.cfop.Cfop;
import net.cartola.emissorfiscal.ncm.NcmDto;

@JsonIgnoreProperties(value = { "documentoFiscal" })
public class DocumentoFiscalItemDto implements Serializable {

	private static final long serialVersionUID = -3885752189101767947L;

	private Long id;
	private Finalidade finalidade = Finalidade.CONSUMO;
	private ProdutoOrigem origem = ProdutoOrigem.NACIONAL;
	private BigDecimal quantidade;
	private BigDecimal valorUnitario;
	private NcmDto ncmDto;
	private Cfop cfop;
	private Integer icmsCest;
	private BigDecimal icmsBase;
	private BigDecimal icmsAliquota;
	private BigDecimal icmsValor;
	private int icmsCst;
	private BigDecimal pisBase;
	private BigDecimal pisAliquota;
	private BigDecimal pisValor;
	private int pisCst;
	private BigDecimal cofinsBase;
	private BigDecimal cofinsAliquota;
	private BigDecimal cofinsValor;
	private int cofinsCst;
	private BigDecimal ipiBase;
	private BigDecimal ipiAliquota;
	private BigDecimal ipiValor;
	private int ipiCst;

	private DocumentoFiscalDto documentoFiscal;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Finalidade getFinalidade() {
		return finalidade;
	}

	public void setFinalidade(Finalidade finalidade) {
		this.finalidade = finalidade;
	}

	public ProdutoOrigem getOrigem() {
		return origem;
	}

	public void setOrigem(ProdutoOrigem origem) {
		this.origem = origem;
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

	public NcmDto getNcm() {
		return ncmDto;
	}

	public void setNcm(NcmDto ncm) {
		this.ncmDto = ncm;
	}

	public Cfop getCfop() {
		return cfop;
	}

	public void setCfop(Cfop cfop) {
		this.cfop = cfop;
	}

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

	public int getIcmsCst() {
		return icmsCst;
	}

	public void setIcmsCst(int icmsCst) {
		this.icmsCst = icmsCst;
	}

	public BigDecimal getPisBase() {
		return pisBase;
	}

	public void setPisBase(BigDecimal pisBase) {
		this.pisBase = pisBase;
	}

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

	public DocumentoFiscalDto getDocumentoFiscal() {
		return documentoFiscal;
	}

	public void setDocumentoFiscal(DocumentoFiscalDto documentoFiscal) {
		this.documentoFiscal = documentoFiscal;
	}

	
}
