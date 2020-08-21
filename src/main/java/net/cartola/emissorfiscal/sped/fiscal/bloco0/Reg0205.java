package net.cartola.emissorfiscal.sped.fiscal.bloco0;

import java.time.LocalDate;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;

/**
 * 19/08/2020
 * @author robson.costa
 * 
 * 
 * Registro 0205 - Alteração do Item
 */
@Record(fields = { 
	@Field(name = "reg", maxLength = 4), 
	@Field(name = "descrAntItem"),
	@Field(name = "dtIni"),
	@Field(name = "dtFim"),
	@Field(name = "codAntItem")
})	
public class Reg0205 {

	private final String reg = "0205";
    private String descrAntItem;
    private LocalDate dtIni;
    private LocalDate dtFim;
    private String codAntItem;
    
	public String getReg() {
		return reg;
	}

	public String getDescrAntItem() {
		return descrAntItem;
	}

	public void setDescrAntItem(String descrAntItem) {
		this.descrAntItem = descrAntItem;
	}

	public LocalDate getDtIni() {
		return dtIni;
	}

	public void setDtIni(LocalDate dtIni) {
		this.dtIni = dtIni;
	}

	public LocalDate getDtFim() {
		return dtFim;
	}

	public void setDtFim(LocalDate dtFim) {
		this.dtFim = dtFim;
	}

	public String getCodAntItem() {
		return codAntItem;
	}

	public void setCodAntItem(String codAntItem) {
		this.codAntItem = codAntItem;
	}
	
}
