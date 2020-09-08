package net.cartola.emissorfiscal.sped.fiscal.blocoC;

import java.math.BigDecimal;
import java.time.LocalDate;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;

/**
 * 02/09/2020
 * @author robson.costa
 * 
 * REGISTRO C173: OPERAÇÕES COM MEDICAMENTOS (CÓDIGO 01, 55)
 */
@Record(fields = { 
	@Field(name = "reg", maxLength = 4),
    @Field(name = "loteMed"),
    @Field(name = "qtdItem"),
    @Field(name = "dtFab"),
    @Field(name = "dtVal"),
    @Field(name = "indMed"),
    @Field(name = "tpProd"),
    @Field(name = "vlTabMax")
})
public class RegC173 {
	
	private final String reg = "C173";
    private String loteMed;
    private Double qtdItem;
    private LocalDate dtFab;
    private LocalDate dtVal;
    private String indMed;
    private String tpProd;
    private BigDecimal vlTabMax;
	
    public String getReg() {
		return reg;
	}

	public String getLoteMed() {
		return loteMed;
	}

	public void setLoteMed(String loteMed) {
		this.loteMed = loteMed;
	}

	public Double getQtdItem() {
		return qtdItem;
	}

	public void setQtdItem(Double qtdItem) {
		this.qtdItem = qtdItem;
	}

	public LocalDate getDtFab() {
		return dtFab;
	}

	public void setDtFab(LocalDate dtFab) {
		this.dtFab = dtFab;
	}

	public LocalDate getDtVal() {
		return dtVal;
	}

	public void setDtVal(LocalDate dtVal) {
		this.dtVal = dtVal;
	}

	public String getIndMed() {
		return indMed;
	}

	public void setIndMed(String indMed) {
		this.indMed = indMed;
	}

	public String getTpProd() {
		return tpProd;
	}

	public void setTpProd(String tpProd) {
		this.tpProd = tpProd;
	}

	public BigDecimal getVlTabMax() {
		return vlTabMax;
	}

	public void setVlTabMax(BigDecimal vlTabMax) {
		this.vlTabMax = vlTabMax;
	}
    
}
