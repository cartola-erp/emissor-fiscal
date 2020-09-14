package net.cartola.emissorfiscal.sped.fiscal.blocoE;

import java.math.BigDecimal;
import java.util.List;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;
import net.cartola.emissorfiscal.sped.fiscal.enums.IndicadorDeMovimentoFcp;

/**
 * 11/09/2020
 * @author robson.costa
 */
@Record(fields = { 
		@Field(name = "reg", maxLength = 4),
		@Field(name = "indMovFcpDifal"),
		@Field(name = "vlSldCredAntDifal"),
		@Field(name = "vlTotDebitosDifal"),
		@Field(name = "vlOutDebDifal"),
		@Field(name = "vlTotCreditosDifal"),
		@Field(name = "vlOutCredDifal"),
		@Field(name = "vlSldDevAntDifal"),
		@Field(name = "vlDeducoesDifal"),
		@Field(name = "vlRecolDifal"),
		@Field(name = "vlSldCredTransportarDifal"),
		@Field(name = "debEspDifal"),
		@Field(name = "vlSldCredAntFcp"),
		@Field(name = "vlTotDebFcpSaida"),
		@Field(name = "vlOutDebFcp"),
		@Field(name = "vlTotCredFcpEntr"),
		@Field(name = "vlOutCredFcp"),
		@Field(name = "vlSldDevAntFcp"),
		@Field(name = "vlDeducoesFcp"),
		@Field(name = "vlRecolFcp"),
		@Field(name = "vlSldCredTransportarFcp"),
		@Field(name = "debEspFcp"),
		// ==================== FILHOS ====================
//		@Field(name = "regE311"),
		@Field(name = "regE316")
})
public class RegE310 {
	
	private final String reg = "E310";
	private IndicadorDeMovimentoFcp indMovFcpDifal;
	private BigDecimal vlSldCredAntDifal;
	private BigDecimal vlTotDebitosDifal;
	private BigDecimal vlOutDebDifal;
	private BigDecimal vlTotCreditosDifal;
	private BigDecimal vlOutCredDifal;
	private BigDecimal vlSldDevAntDifal;
	private BigDecimal vlDeducoesDifal;
	private BigDecimal vlRecolDifal;
	private BigDecimal vlSldCredTransportarDifal;
	private BigDecimal debEspDifal;
	private BigDecimal vlSldCredAntFcp;
	private BigDecimal vlTotDebFcpSaida;
	private BigDecimal vlOutDebFcp;
	private BigDecimal vlTotCredFcpEntr;
	private BigDecimal vlOutCredFcp;
	private BigDecimal vlSldDevAntFcp;
	private BigDecimal vlDeducoesFcp;
	private BigDecimal vlRecolFcp;
	private BigDecimal vlSldCredTransportarFcp;
	private BigDecimal debEspFcp;
	// ==================== FILHOS ====================
//	private List<RegE311> regE311;
	private List<RegE316> regE316;		// Esse é Usado
	
	public String getReg() {
		return reg;
	}

	public IndicadorDeMovimentoFcp getIndMovFcpDifal() {
		return indMovFcpDifal;
	}

	public void setIndMovFcpDifal(IndicadorDeMovimentoFcp indMovFcpDifal) {
		this.indMovFcpDifal = indMovFcpDifal;
	}

	public BigDecimal getVlSldCredAntDifal() {
		return vlSldCredAntDifal;
	}

	public void setVlSldCredAntDifal(BigDecimal vlSldCredAntDifal) {
		this.vlSldCredAntDifal = vlSldCredAntDifal;
	}

	public BigDecimal getVlTotDebitosDifal() {
		return vlTotDebitosDifal;
	}

	public void setVlTotDebitosDifal(BigDecimal vlTotDebitosDifal) {
		this.vlTotDebitosDifal = vlTotDebitosDifal;
	}

	public BigDecimal getVlOutDebDifal() {
		return vlOutDebDifal;
	}

	public void setVlOutDebDifal(BigDecimal vlOutDebDifal) {
		this.vlOutDebDifal = vlOutDebDifal;
	}

	public BigDecimal getVlTotCreditosDifal() {
		return vlTotCreditosDifal;
	}

	public void setVlTotCreditosDifal(BigDecimal vlTotCreditosDifal) {
		this.vlTotCreditosDifal = vlTotCreditosDifal;
	}

	public BigDecimal getVlOutCredDifal() {
		return vlOutCredDifal;
	}

	public void setVlOutCredDifal(BigDecimal vlOutCredDifal) {
		this.vlOutCredDifal = vlOutCredDifal;
	}

	public BigDecimal getVlSldDevAntDifal() {
		return vlSldDevAntDifal;
	}

	public void setVlSldDevAntDifal(BigDecimal vlSldDevAntDifal) {
		this.vlSldDevAntDifal = vlSldDevAntDifal;
	}

	public BigDecimal getVlDeducoesDifal() {
		return vlDeducoesDifal;
	}

	public void setVlDeducoesDifal(BigDecimal vlDeducoesDifal) {
		this.vlDeducoesDifal = vlDeducoesDifal;
	}

	public BigDecimal getVlRecolDifal() {
		return vlRecolDifal;
	}

	public void setVlRecolDifal(BigDecimal vlRecolDifal) {
		this.vlRecolDifal = vlRecolDifal;
	}

	public BigDecimal getVlSldCredTransportarDifal() {
		return vlSldCredTransportarDifal;
	}

	public void setVlSldCredTransportarDifal(BigDecimal vlSldCredTransportarDifal) {
		this.vlSldCredTransportarDifal = vlSldCredTransportarDifal;
	}

	public BigDecimal getDebEspDifal() {
		return debEspDifal;
	}

	public void setDebEspDifal(BigDecimal debEspDifal) {
		this.debEspDifal = debEspDifal;
	}

	public BigDecimal getVlSldCredAntFcp() {
		return vlSldCredAntFcp;
	}

	public void setVlSldCredAntFcp(BigDecimal vlSldCredAntFcp) {
		this.vlSldCredAntFcp = vlSldCredAntFcp;
	}

	public BigDecimal getVlTotDebFcpSaida() {
		return vlTotDebFcpSaida;
	}

	public void setVlTotDebFcpSaida(BigDecimal vlTotDebFcpSaida) {
		this.vlTotDebFcpSaida = vlTotDebFcpSaida;
	}

	public BigDecimal getVlOutDebFcp() {
		return vlOutDebFcp;
	}

	public void setVlOutDebFcp(BigDecimal vlOutDebFcp) {
		this.vlOutDebFcp = vlOutDebFcp;
	}

	public BigDecimal getVlTotCredFcpEntr() {
		return vlTotCredFcpEntr;
	}

	public void setVlTotCredFcpEntr(BigDecimal vlTotCredFcpEntr) {
		this.vlTotCredFcpEntr = vlTotCredFcpEntr;
	}

	public BigDecimal getVlOutCredFcp() {
		return vlOutCredFcp;
	}

	public void setVlOutCredFcp(BigDecimal vlOutCredFcp) {
		this.vlOutCredFcp = vlOutCredFcp;
	}

	public BigDecimal getVlSldDevAntFcp() {
		return vlSldDevAntFcp;
	}

	public void setVlSldDevAntFcp(BigDecimal vlSldDevAntFcp) {
		this.vlSldDevAntFcp = vlSldDevAntFcp;
	}

	public BigDecimal getVlDeducoesFcp() {
		return vlDeducoesFcp;
	}

	public void setVlDeducoesFcp(BigDecimal vlDeducoesFcp) {
		this.vlDeducoesFcp = vlDeducoesFcp;
	}

	public BigDecimal getVlRecolFcp() {
		return vlRecolFcp;
	}

	public void setVlRecolFcp(BigDecimal vlRecolFcp) {
		this.vlRecolFcp = vlRecolFcp;
	}

	public BigDecimal getVlSldCredTransportarFcp() {
		return vlSldCredTransportarFcp;
	}

	public void setVlSldCredTransportarFcp(BigDecimal vlSldCredTransportarFcp) {
		this.vlSldCredTransportarFcp = vlSldCredTransportarFcp;
	}

	public BigDecimal getDebEspFcp() {
		return debEspFcp;
	}

	public void setDebEspFcp(BigDecimal debEspFcp) {
		this.debEspFcp = debEspFcp;
	}

	public List<RegE316> getRegE316() {
		return regE316;
	}

	public void setRegE316(List<RegE316> regE316) {
		this.regE316 = regE316;
	}
	
}
