package net.cartola.emissorfiscal.sped.fiscal.blocoE;

import java.math.BigDecimal;
import java.time.LocalDate;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;
import net.cartola.emissorfiscal.sped.fiscal.enums.ModeloDocumentoFiscal;

/**
 * 11/09/2020
 * @author robson.costa
 */
@Record(fields = { 
		@Field(name = "reg", maxLength = 4),
		@Field(name = "codPart", maxLength = 60),
		@Field(name = "codMod"),
		@Field(name = "ser"),
		@Field(name = "sub"),
		@Field(name = "numDoc"),
		@Field(name = "dtDoc"),
		@Field(name = "codItem"),
		@Field(name = "vlAjItem"),
		@Field(name = "chvDOCe")
})
public class RegE113 {
	
	private final String reg = "E113";
	private String codPart;
	private ModeloDocumentoFiscal codMod;
	private String ser;
	private Long sub;
	private Long numDoc;
	private LocalDate dtDoc;
	private String codItem;
	private BigDecimal vlAjItem;
	private Long chvDOCe;
	
	public String getReg() {
		return reg;
	}

	public String getCodPart() {
		return codPart;
	}

	public void setCodPart(String codPart) {
		this.codPart = codPart;
	}

	public ModeloDocumentoFiscal getCodMod() {
		return codMod;
	}

	public void setCodMod(ModeloDocumentoFiscal codMod) {
		this.codMod = codMod;
	}

	public String getSer() {
		return ser;
	}

	public void setSer(String ser) {
		this.ser = ser;
	}

	public Long getSub() {
		return sub;
	}

	public void setSub(Long sub) {
		this.sub = sub;
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

	public String getCodItem() {
		return codItem;
	}

	public void setCodItem(String codItem) {
		this.codItem = codItem;
	}

	public BigDecimal getVlAjItem() {
		return vlAjItem;
	}

	public void setVlAjItem(BigDecimal vlAjItem) {
		this.vlAjItem = vlAjItem;
	}

	public Long getChvDOCe() {
		return chvDOCe;
	}

	public void setChvDOCe(Long chvDOCe) {
		this.chvDOCe = chvDOCe;
	}
	
}
