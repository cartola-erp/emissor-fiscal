package net.cartola.emissorfiscal.tributacao;

import java.io.Serializable;
import java.math.BigDecimal;

public class CalculoImpostoDifal implements Serializable {
	
	private static final long serialVersionUID = 1601816091282731697L;

	private BigDecimal vlrBaseUfDest;				// QTD_ITEM * VLR_ITEM !?
	private BigDecimal vlrBaseFcpUfDest;
	private BigDecimal aliquotaFcpUfDest;
	private BigDecimal aliquotaIcmsUfDest;
	private BigDecimal aliquotaIcmsInter;		//Alíquota interestadual das UF envolvidas (SP p/ qualquer outra UF)
//	private BigDecimal aliquotaInterPartilha;		// Agora é sempre : 100%
	private BigDecimal vlrFcpUfDest;
	private BigDecimal vlrIcmsUfDest;
//	private BigDecimal vlrIcmsUfRemetente;			// Agora é sempre : 0 (pois não tem mais a partilha provisória)

	public BigDecimal getAliquotaFcpUfDest() {
		return aliquotaFcpUfDest;
	}
	
	public void setAliquotaFcpUfDest(BigDecimal aliquotaFcpUfDest) {
		this.aliquotaFcpUfDest = aliquotaFcpUfDest;
	}

	public BigDecimal getVlrBaseUfDest() {
		return vlrBaseUfDest;
	}

	public void setVlrBaseUfDest(BigDecimal vlrBaseUfDest) {
		this.vlrBaseUfDest = vlrBaseUfDest;
	}

	public BigDecimal getVlrBaseFcpUfDest() {
		return vlrBaseFcpUfDest;
	}

	public void setVlrBaseFcpUfDest(BigDecimal vlrBaseFcpUfDest) {
		this.vlrBaseFcpUfDest = vlrBaseFcpUfDest;
	}

	public BigDecimal getAliquotaIcmsUfDest() {
		return aliquotaIcmsUfDest;
	}

	public void setAliquotaIcmsUfDest(BigDecimal aliquotaIcmsUfDest) {
		this.aliquotaIcmsUfDest = aliquotaIcmsUfDest;
	}
	
	public BigDecimal getAliquotaIcmsInter() {
		return aliquotaIcmsInter;
	}

	public void setAliquotaIcmsInter(BigDecimal aliquotacIcmsInter) {
		this.aliquotaIcmsInter = aliquotacIcmsInter;
	}

	public BigDecimal getVlrFcpUfDest() {
		return vlrFcpUfDest;
	}

	public void setVlrFcpUfDest(BigDecimal vlrFcpUfDest) {
		this.vlrFcpUfDest = vlrFcpUfDest;
	}

	public BigDecimal getVlrIcmsUfDest() {
		return vlrIcmsUfDest;
	}

	public void setVlrIcmsUfDest(BigDecimal vlrIcmsUfDest) {
		this.vlrIcmsUfDest = vlrIcmsUfDest;
	}
	
}
