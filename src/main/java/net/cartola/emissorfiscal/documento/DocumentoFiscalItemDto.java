package net.cartola.emissorfiscal.documento;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import net.cartola.emissorfiscal.ncm.NcmDto;

@JsonIgnoreProperties(value = { "documentoFiscal" })
public class DocumentoFiscalItemDto implements Serializable {

	private static final long serialVersionUID = -3885752189101767947L;

	private Long id;
	private Finalidade finalidade = Finalidade.CONSUMO;
	private BigDecimal quantidade;
	private BigDecimal valorUnitario;
	private NcmDto ncm;
	private int cfop;
	private Integer icmsCest;
	private BigDecimal icmsBase;
	private BigDecimal icmsAliquota;
	private BigDecimal icmsValor;
	private BigDecimal pisBase;
	private BigDecimal pisAliquota;
	private BigDecimal pisValor;
	private BigDecimal cofinsBase;
	private BigDecimal cofinsAliquota;
	private BigDecimal cofinsValor;
	private BigDecimal ipiBase;
	private BigDecimal ipiAliquota;
	private BigDecimal ipiValor;

	private DocumentoFiscalDto documentoFiscal;

	public Long getId() {
		return id;
	}

	public Finalidade getFinalidade() {
		return finalidade;
	}

	public BigDecimal getQuantidade() {
		return quantidade;
	}

	public BigDecimal getValorUnitario() {
		return valorUnitario;
	}

	public NcmDto getNcm() {
		return ncm;
	}

	public int getCfop() {
		return cfop;
	}

	public Integer getIcmsCest() {
		return icmsCest;
	}

	public BigDecimal getIcmsBase() {
		return icmsBase;
	}

	public BigDecimal getIcmsAliquota() {
		return icmsAliquota;
	}

	public BigDecimal getIcmsValor() {
		return icmsValor;
	}

	public BigDecimal getPisBase() {
		return pisBase;
	}

	public BigDecimal getPisAliquota() {
		return pisAliquota;
	}

	public BigDecimal getPisValor() {
		return pisValor;
	}

	public BigDecimal getCofinsBase() {
		return cofinsBase;
	}

	public BigDecimal getCofinsAliquota() {
		return cofinsAliquota;
	}

	public BigDecimal getCofinsValor() {
		return cofinsValor;
	}

	public BigDecimal getIpiBase() {
		return ipiBase;
	}

	public BigDecimal getIpiAliquota() {
		return ipiAliquota;
	}

	public BigDecimal getIpiValor() {
		return ipiValor;
	}

	public DocumentoFiscalDto getDocumentoFiscal() {
		return documentoFiscal;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setFinalidade(Finalidade finalidade) {
		this.finalidade = finalidade;
	}

	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}

	public void setValorUnitario(BigDecimal valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	public void setNcm(NcmDto ncm) {
		this.ncm = ncm;
	}

	public void setCfop(int cfop) {
		this.cfop = cfop;
	}

	public void setIcmsCest(Integer icmsCest) {
		this.icmsCest = icmsCest;
	}

	public void setIcmsBase(BigDecimal icmsBase) {
		this.icmsBase = icmsBase;
	}

	public void setIcmsAliquota(BigDecimal icmsAliquota) {
		this.icmsAliquota = icmsAliquota;
	}

	public void setIcmsValor(BigDecimal icmsValor) {
		this.icmsValor = icmsValor;
	}

	public void setPisBase(BigDecimal pisBase) {
		this.pisBase = pisBase;
	}

	public void setPisAliquota(BigDecimal pisAliquota) {
		this.pisAliquota = pisAliquota;
	}

	public void setPisValor(BigDecimal pisValor) {
		this.pisValor = pisValor;
	}

	public void setCofinsBase(BigDecimal cofinsBase) {
		this.cofinsBase = cofinsBase;
	}

	public void setCofinsAliquota(BigDecimal cofinsAliquota) {
		this.cofinsAliquota = cofinsAliquota;
	}

	public void setCofinsValor(BigDecimal cofinsValor) {
		this.cofinsValor = cofinsValor;
	}

	public void setIpiBase(BigDecimal ipiBase) {
		this.ipiBase = ipiBase;
	}

	public void setIpiAliquota(BigDecimal ipiAliquota) {
		this.ipiAliquota = ipiAliquota;
	}

	public void setIpiValor(BigDecimal ipiValor) {
		this.ipiValor = ipiValor;
	}

	public void setDocumentoFiscal(DocumentoFiscalDto documentoFiscal) {
		this.documentoFiscal = documentoFiscal;
	}
	
}
