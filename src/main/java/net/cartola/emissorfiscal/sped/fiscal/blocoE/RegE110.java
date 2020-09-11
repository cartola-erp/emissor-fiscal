package net.cartola.emissorfiscal.sped.fiscal.blocoE;

import java.math.BigDecimal;
import java.util.List;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;

/**
 * 11/09/2020
 * @author robson.costa
 */
@Record(fields = { 
		@Field(name = "reg", maxLength = 4),
		@Field(name = "vlTotDebitos"),
		@Field(name = "vlAjDebitos"),
		@Field(name = "vlTotAjDebitos"),
		@Field(name = "vlEstornosCred"),
		@Field(name = "vlTotCreditos"),
		@Field(name = "vlAjCreditos"),
		@Field(name = "vlTotAjCreditos"),
		@Field(name = "vlEstornosDeb"),
		@Field(name = "vlSldCredorAnt"),
		@Field(name = "vlSldApurado"),
		@Field(name = "vlTotDed"),
		@Field(name = "vlIcmsRecolher"),
		@Field(name = "vlSldCredorTransportar"),
		@Field(name = "debEsp"),
	    // ==================== FILHOS ====================
		@Field(name = "regE111"),
//		@Field(name = "regE115"),
		@Field(name = "regE116")
})
public class RegE110 {
	
	private final String reg = "E110";
	private BigDecimal vlTotDebitos;
	private BigDecimal vlAjDebitos;
	private BigDecimal vlTotAjDebitos;
	private BigDecimal vlEstornosCred;
	private BigDecimal vlTotCreditos;
	private BigDecimal vlAjCreditos;
	private BigDecimal vlTotAjCreditos;
	private BigDecimal vlEstornosDeb;
	private BigDecimal vlSldCredorAnt;
	private BigDecimal vlSldApurado;
	private BigDecimal vlTotDed;
	private BigDecimal vlIcmsRecolher;
	private BigDecimal vlSldCredorTransportar;
	private BigDecimal debEsp;
    // ==================== FILHOS ====================
	private List<RegE111> regE111;
//	private List<RegE115> regE115;
	private List<RegE116> regE116;
	
	public String getReg() {
		return reg;
	}

	public BigDecimal getVlTotDebitos() {
		return vlTotDebitos;
	}

	public void setVlTotDebitos(BigDecimal vlTotDebitos) {
		this.vlTotDebitos = vlTotDebitos;
	}

	public BigDecimal getVlAjDebitos() {
		return vlAjDebitos;
	}

	public void setVlAjDebitos(BigDecimal vlAjDebitos) {
		this.vlAjDebitos = vlAjDebitos;
	}

	public BigDecimal getVlTotAjDebitos() {
		return vlTotAjDebitos;
	}

	public void setVlTotAjDebitos(BigDecimal vlTotAjDebitos) {
		this.vlTotAjDebitos = vlTotAjDebitos;
	}

	public BigDecimal getVlEstornosCred() {
		return vlEstornosCred;
	}

	public void setVlEstornosCred(BigDecimal vlEstornosCred) {
		this.vlEstornosCred = vlEstornosCred;
	}

	public BigDecimal getVlTotCreditos() {
		return vlTotCreditos;
	}

	public void setVlTotCreditos(BigDecimal vlTotCreditos) {
		this.vlTotCreditos = vlTotCreditos;
	}

	public BigDecimal getVlAjCreditos() {
		return vlAjCreditos;
	}

	public void setVlAjCreditos(BigDecimal vlAjCreditos) {
		this.vlAjCreditos = vlAjCreditos;
	}

	public BigDecimal getVlTotAjCreditos() {
		return vlTotAjCreditos;
	}

	public void setVlTotAjCreditos(BigDecimal vlTotAjCreditos) {
		this.vlTotAjCreditos = vlTotAjCreditos;
	}

	public BigDecimal getVlEstornosDeb() {
		return vlEstornosDeb;
	}

	public void setVlEstornosDeb(BigDecimal vlEstornosDeb) {
		this.vlEstornosDeb = vlEstornosDeb;
	}

	public BigDecimal getVlSldCredorAnt() {
		return vlSldCredorAnt;
	}

	public void setVlSldCredorAnt(BigDecimal vlSldCredorAnt) {
		this.vlSldCredorAnt = vlSldCredorAnt;
	}

	public BigDecimal getVlSldApurado() {
		return vlSldApurado;
	}

	public void setVlSldApurado(BigDecimal vlSldApurado) {
		this.vlSldApurado = vlSldApurado;
	}

	public BigDecimal getVlTotDed() {
		return vlTotDed;
	}

	public void setVlTotDed(BigDecimal vlTotDed) {
		this.vlTotDed = vlTotDed;
	}

	public BigDecimal getVlIcmsRecolher() {
		return vlIcmsRecolher;
	}

	public void setVlIcmsRecolher(BigDecimal vlIcmsRecolher) {
		this.vlIcmsRecolher = vlIcmsRecolher;
	}

	public BigDecimal getVlSldCredorTransportar() {
		return vlSldCredorTransportar;
	}

	public void setVlSldCredorTransportar(BigDecimal vlSldCredorTransportar) {
		this.vlSldCredorTransportar = vlSldCredorTransportar;
	}

	public BigDecimal getDebEsp() {
		return debEsp;
	}

	public void setDebEsp(BigDecimal debEsp) {
		this.debEsp = debEsp;
	}

	public List<RegE111> getRegE111() {
		return regE111;
	}

	public void setRegE111(List<RegE111> regE111) {
		this.regE111 = regE111;
	}

	public List<RegE116> getRegE116() {
		return regE116;
	}

	public void setRegE116(List<RegE116> regE116) {
		this.regE116 = regE116;
	}
	
}
