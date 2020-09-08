package net.cartola.emissorfiscal.sped.fiscal.blocoC;

import java.time.LocalDate;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;
import net.cartola.emissorfiscal.sped.fiscal.enums.IndicadorDeOperacao;
import net.cartola.emissorfiscal.sped.fiscal.enums.IndicadorDoEmitente;
import net.cartola.emissorfiscal.sped.fiscal.enums.ModeloDocumentoFiscal;

/**
 * 02/09/2020
 * @author robson.costa
 */
@Record(fields = { 
	@Field(name = "reg", maxLength = 4),
	@Field(name = "indOper"),
	@Field(name = "indEmit"),
	@Field(name = "codPart"),
	@Field(name = "codMod"),
	@Field(name = "ser"),
	@Field(name = "sub"),
	@Field(name = "numDoc"),
	@Field(name = "dtDoc"),
	@Field(name = "chvDocE", minVersion = 11)
})
public class RegC113 {
	
	private final String reg = "C113";
    private IndicadorDeOperacao indOper;
    private IndicadorDoEmitente indEmit;
    private String codPart;
    private ModeloDocumentoFiscal codMod;
    private String ser;
    private Integer sub;
    private Long numDoc;
    private LocalDate dtDoc;
    private Long chvDocE;
	
    public String getReg() {
		return reg;
	}

	public IndicadorDeOperacao getIndOper() {
		return indOper;
	}

	public void setIndOper(IndicadorDeOperacao indOper) {
		this.indOper = indOper;
	}

	public IndicadorDoEmitente getIndEmit() {
		return indEmit;
	}

	public void setIndEmit(IndicadorDoEmitente indEmit) {
		this.indEmit = indEmit;
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

	public Integer getSub() {
		return sub;
	}

	public void setSub(Integer sub) {
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

	public Long getChvDocE() {
		return chvDocE;
	}

	public void setChvDocE(Long chvDocE) {
		this.chvDocE = chvDocE;
	}
    
}
