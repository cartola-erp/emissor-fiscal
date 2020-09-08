package net.cartola.emissorfiscal.sped.fiscal.blocoC;

import java.time.LocalDate;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;
import coffeepot.bean.wr.typeHandler.DefaultStringHandler;
import net.cartola.emissorfiscal.sped.fiscal.enums.ModeloDocumentoFiscal;

/**
 * 02/09/2020
 * @author robson.costa
 */
@Record(fields = { 
	@Field(name = "reg", maxLength = 4),
	@Field( name = "codMod"),
	@Field( name = "ecfFab", params = {DefaultStringHandler.PARAM_FILTER_NUMBER_LETTERS_ONLY}),
	@Field( name = "ecfCx"),
	@Field( name = "numDoc"),
	@Field( name = "dtDoc")
})
public class RegC114 {

	private final String reg = "C114";
	private ModeloDocumentoFiscal codMod;
	private String ecfFab;
	private Integer ecfCx;
	private Long numDoc;
	private LocalDate dtDoc;
	
	public String getReg() {
		return reg;
	}

	public ModeloDocumentoFiscal getCodMod() {
		return codMod;
	}

	public void setCodMod(ModeloDocumentoFiscal codMod) {
		this.codMod = codMod;
	}

	public String getEcfFab() {
		return ecfFab;
	}

	public void setEcfFab(String ecfFab) {
		this.ecfFab = ecfFab;
	}

	public Integer getEcfCx() {
		return ecfCx;
	}

	public void setEcfCx(Integer ecfCx) {
		this.ecfCx = ecfCx;
	}

	public Long getNumDoc() {
		return numDoc;
	}

	public void setNumDoc(Long numDoc) {
		this.numDoc = numDoc;
	}

	public LocalDate getDtDoc() {
		return dtDoc;
	}

	public void setDtDoc(LocalDate dtDoc) {
		this.dtDoc = dtDoc;
	}
	
}
