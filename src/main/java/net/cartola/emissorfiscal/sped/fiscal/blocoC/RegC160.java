package net.cartola.emissorfiscal.sped.fiscal.blocoC;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;
import coffeepot.bean.wr.typeHandler.DefaultStringHandler;

/**
 * 02/09/2020
 * @author robson.costa
 */
@Record(fields = { 
	@Field(name = "reg", maxLength = 4),
    @Field(name = "codPart"),
    @Field(name = "veicId", params = {DefaultStringHandler.PARAM_FILTER_NUMBER_LETTERS_ONLY}),
    @Field(name = "qtdVol"),
    @Field(name = "pesoBrt"),
    @Field(name = "pesoLiq"),
    @Field(name = "ufId")
})
public class RegC160 {

	private final String reg = "C160";
    private String codPart;
    private String veicId;
    private Integer qtdVol;
    private Double pesoBrt;
    private Double pesoLiq;
    private String ufId;
	
    public String getReg() {
		return reg;
	}

	public String getCodPart() {
		return codPart;
	}

	public void setCodPart(String codPart) {
		this.codPart = codPart;
	}

	public String getVeicId() {
		return veicId;
	}

	public void setVeicId(String veicId) {
		this.veicId = veicId;
	}

	public Integer getQtdVol() {
		return qtdVol;
	}

	public void setQtdVol(Integer qtdVol) {
		this.qtdVol = qtdVol;
	}

	public Double getPesoBrt() {
		return pesoBrt;
	}

	public void setPesoBrt(Double pesoBrt) {
		this.pesoBrt = pesoBrt;
	}

	public Double getPesoLiq() {
		return pesoLiq;
	}

	public void setPesoLiq(Double pesoLiq) {
		this.pesoLiq = pesoLiq;
	}

	public String getUfId() {
		return ufId;
	}

	public void setUfId(String ufId) {
		this.ufId = ufId;
	}
    
}
