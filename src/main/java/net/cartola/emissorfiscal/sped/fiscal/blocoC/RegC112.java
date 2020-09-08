package net.cartola.emissorfiscal.sped.fiscal.blocoC;

import java.time.LocalDate;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;
import net.cartola.emissorfiscal.sped.fiscal.enums.DocumentoDeArrecadacao;

/**
 * 02/09/2020
 * @author robson.costa
 */
@Record(fields = { 
	@Field(name = "reg", maxLength = 4),
	@Field(name = "codDa"),
	@Field(name = "uf"),
	@Field(name = "numDa"),
	@Field(name = "codAut"),
	@Field(name = "vlDa"),
	@Field(name = "dtVcto"),
	@Field(name = "dtPgto")
})
public class RegC112 {
	
	private final String reg = "C112";
    private DocumentoDeArrecadacao codDa;
    private String uf;
    private String numDa;
    private String codAut;
    private Double vlDa;
    private LocalDate dtVcto;
    private LocalDate dtPgto;
	
    public String getReg() {
		return reg;
	}

	public DocumentoDeArrecadacao getCodDa() {
		return codDa;
	}

	public void setCodDa(DocumentoDeArrecadacao codDa) {
		this.codDa = codDa;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getNumDa() {
		return numDa;
	}

	public void setNumDa(String numDa) {
		this.numDa = numDa;
	}

	public String getCodAut() {
		return codAut;
	}

	public void setCodAut(String codAut) {
		this.codAut = codAut;
	}

	public Double getVlDa() {
		return vlDa;
	}

	public void setVlDa(Double vlDa) {
		this.vlDa = vlDa;
	}

	public LocalDate getDtVcto() {
		return dtVcto;
	}

	public void setDtVcto(LocalDate dtVcto) {
		this.dtVcto = dtVcto;
	}

	public LocalDate getDtPgto() {
		return dtPgto;
	}

	public void setDtPgto(LocalDate dtPgto) {
		this.dtPgto = dtPgto;
	}
	
}
