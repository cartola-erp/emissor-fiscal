package net.cartola.emissorfiscal.sped.fiscal.blocoC;

import java.time.LocalTime;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;
import coffeepot.bean.wr.typeHandler.DefaultStringHandler;
import coffeepot.bean.wr.types.Align;

/**
 * 02/09/2020
 * @author robson.costa
 */
@Record(fields = { 
	@Field(name = "reg", maxLength = 4),
    @Field(name = "codPart"),
    @Field(name = "veicId", params = {DefaultStringHandler.PARAM_FILTER_NUMBER_LETTERS_ONLY}),
    @Field(name = "codAut"),
    @Field(name = "nrPasse"),
    @Field(name = "hora"),
    @Field(name = "temper"),
    @Field(name = "qtdVol"),
    @Field(name = "pesoBrt"),
    @Field(name = "pesoLiq"),
    @Field(name = "nomMot", maxLength = 60),
    @Field(name = "cpf", length = 11, padding = '0', align = Align.RIGHT, paddingIfNullOrEmpty = true, params = {DefaultStringHandler.PARAM_FILTER_NUMBER_ONLY}),
    @Field(name = "ufId")
})
public class RegC165 {

	private final String reg = "C165";
	private String codPart;
	private String veicId;
	private String codAut;
	private String nrPasse;
	private LocalTime hora;
	private Double temper;
	private int qtdVol;
	private Double pesoBrt;
	private Double pesoLiq;
	private String nomMot;
	private String cpf;
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

	public String getCodAut() {
		return codAut;
	}

	public void setCodAut(String codAut) {
		this.codAut = codAut;
	}

	public String getNrPasse() {
		return nrPasse;
	}

	public void setNrPasse(String nrPasse) {
		this.nrPasse = nrPasse;
	}

	public LocalTime getHora() {
		return hora;
	}

	public void setHora(LocalTime hora) {
		this.hora = hora;
	}

	public Double getTemper() {
		return temper;
	}

	public void setTemper(Double temper) {
		this.temper = temper;
	}

	public int getQtdVol() {
		return qtdVol;
	}

	public void setQtdVol(int qtdVol) {
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

	public String getNomMot() {
		return nomMot;
	}

	public void setNomMot(String nomMot) {
		this.nomMot = nomMot;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getUfId() {
		return ufId;
	}

	public void setUfId(String ufId) {
		this.ufId = ufId;
	}
	
}
