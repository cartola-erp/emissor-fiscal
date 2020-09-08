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
    @Field(name = "codMod"),
    @Field(name = "nrSat", params = {DefaultStringHandler.PARAM_FILTER_NUMBER_ONLY}),
    @Field(name = "chvCfe", params = {DefaultStringHandler.PARAM_FILTER_NUMBER_ONLY}),
    @Field(name = "numCfe"),
    @Field(name = "dtDoc")
})
public class RegC116 {
	
	private final String reg = "C116";
    private ModeloDocumentoFiscal codMod;
    private String nrSat;
    private String chvCfe;
    private Integer numCfe;
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



	public String getNrSat() {
		return nrSat;
	}

	public void setNrSat(String nrSat) {
		this.nrSat = nrSat;
	}

	public String getChvCfe() {
		return chvCfe;
	}

	public void setChvCfe(String chvCfe) {
		this.chvCfe = chvCfe;
	}

	public Integer getNumCfe() {
		return numCfe;
	}

	public void setNumCfe(Integer numCfe) {
		this.numCfe = numCfe;
	}

	public LocalDate getDtDoc() {
		return dtDoc;
	}

	public void setDtDoc(LocalDate dtDoc) {
		this.dtDoc = dtDoc;
	}
    
}
