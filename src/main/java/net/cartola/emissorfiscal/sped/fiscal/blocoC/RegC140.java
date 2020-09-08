package net.cartola.emissorfiscal.sped.fiscal.blocoC;

import java.math.BigDecimal;
import java.util.List;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;
import net.cartola.emissorfiscal.sped.fiscal.enums.IndicadorDoEmitente;
import net.cartola.emissorfiscal.sped.fiscal.enums.TipoTituloCredito;

/**
 * 02/09/2020
 * @author robson.costa
 */
@Record(fields = { 
	@Field(name = "reg", maxLength = 4),
	@Field(name = "indEmit"),
	@Field(name = "indTit"),
	@Field(name = "descTit"),
	@Field(name = "numTit"),
	@Field(name = "qtdParc"),
	@Field(name = "vlTit"),
	// ============= FILHO ================
	@Field(name = "regC141")
})
public class RegC140 {

	private final String reg = "C140";
	private IndicadorDoEmitente indEmit;
	private TipoTituloCredito indTit;
	private String descTit;
	private String numTit;
	private Integer qtdParc;
	private BigDecimal vlTit;
	
	// ============= FILHO ================
	private List<RegC141> regC141;

	public String getReg() {
		return reg;
	}

	public IndicadorDoEmitente getIndEmit() {
		return indEmit;
	}

	public void setIndEmit(IndicadorDoEmitente indEmit) {
		this.indEmit = indEmit;
	}

	public TipoTituloCredito getIndTit() {
		return indTit;
	}

	public void setIndTit(TipoTituloCredito indTit) {
		this.indTit = indTit;
	}

	public String getDescTit() {
		return descTit;
	}

	public void setDescTit(String descTit) {
		this.descTit = descTit;
	}

	public String getNumTit() {
		return numTit;
	}

	public void setNumTit(String numTit) {
		this.numTit = numTit;
	}

	public Integer getQtdParc() {
		return qtdParc;
	}

	public void setQtdParc(Integer qtdParc) {
		this.qtdParc = qtdParc;
	}

	public BigDecimal getVlTit() {
		return vlTit;
	}

	public void setVlTit(BigDecimal vlTit) {
		this.vlTit = vlTit;
	}

	public List<RegC141> getRegC141() {
		return regC141;
	}

	public void setRegC141(List<RegC141> regC141) {
		this.regC141 = regC141;
	}

}
