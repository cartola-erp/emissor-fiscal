package net.cartola.emissorfiscal.sped.fiscal.blocoC;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;
import net.cartola.emissorfiscal.sped.fiscal.enums.DocumentoDeImportacao;

/**
 * 02/09/2020
 * @author robson.costa
 */
@Record(fields = { 
	@Field(name = "reg", maxLength = 4),
	@Field( name = "codDocImp"),
	@Field( name = "numDocImp"),
	@Field( name = "pisImp"),
	@Field( name = "cofinsImp"),
	@Field( name = "numAcdraw")
})
public class RegC120 {
	
	private final String reg = "C120";
    private DocumentoDeImportacao codDocImp;
    private String numDocImp;
    private Double pisImp;
    private Double cofinsImp;
    private String numAcdraw;
	
    public String getReg() {
		return reg;
	}

	public DocumentoDeImportacao getCodDocImp() {
		return codDocImp;
	}

	public void setCodDocImp(DocumentoDeImportacao codDocImp) {
		this.codDocImp = codDocImp;
	}

	public String getNumDocImp() {
		return numDocImp;
	}

	public void setNumDocImp(String numDocImp) {
		this.numDocImp = numDocImp;
	}

	public Double getPisImp() {
		return pisImp;
	}

	public void setPisImp(Double pisImp) {
		this.pisImp = pisImp;
	}

	public Double getCofinsImp() {
		return cofinsImp;
	}

	public void setCofinsImp(Double cofinsImp) {
		this.cofinsImp = cofinsImp;
	}

	public String getNumAcdraw() {
		return numAcdraw;
	}

	public void setNumAcdraw(String numAcdraw) {
		this.numAcdraw = numAcdraw;
	}
    
}
