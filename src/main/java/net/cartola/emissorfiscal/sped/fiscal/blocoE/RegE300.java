package net.cartola.emissorfiscal.sped.fiscal.blocoE;

import java.time.LocalDate;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;

/**
 * 11/09/2020
 * @author robson.costa
 * 
 * REGISTRO E300: PERÍODO DE APURAÇÃO DO FUNDO DE COMBATE À POBREZA E DO ICMS
 * DIFERENCIAL DE ALÍQUOTAS - UF ORIGEM/DESTINO EC 87/15
 * 
 */
@Record(fields = { 
		@Field(name = "reg", maxLength = 4),
		@Field(name = "uf"),
		@Field(name = "dtIni"),
		@Field(name = "dtFin"),
		// ==================== FILHOS ====================
		@Field(name = "regE310")
})
public class RegE300 {
	
	private final String reg = "E300";
	private String uf;
	private LocalDate dtIni;
	private LocalDate dtFin;
	// ==================== FILHOS ====================
	private RegE310 regE310;
	
	public String getReg() {
		return reg;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public LocalDate getDtIni() {
		return dtIni;
	}

	public void setDtIni(LocalDate dtIni) {
		this.dtIni = dtIni;
	}

	public LocalDate getDtFin() {
		return dtFin;
	}

	public void setDtFin(LocalDate dtFin) {
		this.dtFin = dtFin;
	}

	public RegE310 getRegE310() {
		return regE310;
	}

	public void setRegE310(RegE310 regE310) {
		this.regE310 = regE310;
	}
	
}
