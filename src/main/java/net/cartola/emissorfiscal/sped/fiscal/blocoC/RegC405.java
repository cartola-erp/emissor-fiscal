package net.cartola.emissorfiscal.sped.fiscal.blocoC;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;

/**
 * 02/09/2020
 * @author robson.costa
 */
@Record(fields = { 
	@Field(name = "reg", maxLength = 4),
    @Field(name = "dtDoc"),
    @Field(name = "cro"),
    @Field(name = "crz"),
    @Field(name = "numCooFin"),
    @Field(name = "gtFin"),
    @Field(name = "vlBrt"),
    // ==================== FILHO ====================
    @Field(name = "regC410"),
    @Field(name = "regC420"),
    @Field(name = "regC460"),
    @Field(name = "regC490")
})
public class RegC405 {
	
	private final String reg = "C405";
    private LocalDate dtDoc;
    private Integer cro;
    private Integer crz;
    private Integer numCooFin;
    private BigDecimal gtFin;
    private BigDecimal vlBrt;
    // ==================== FILHO ====================
    private RegC410 regC410;
    private List<RegC420> regC420;
    private List<RegC460> regC460;
    private List<RegC490> regC490;
	
    public String getReg() {
		return reg;
	}

	public LocalDate getDtDoc() {
		return dtDoc;
	}

	public void setDtDoc(LocalDate dtDoc) {
		this.dtDoc = dtDoc;
	}

	public Integer getCro() {
		return cro;
	}

	public void setCro(Integer cro) {
		this.cro = cro;
	}

	public Integer getCrz() {
		return crz;
	}

	public void setCrz(Integer crz) {
		this.crz = crz;
	}

	public Integer getNumCooFin() {
		return numCooFin;
	}

	public void setNumCooFin(Integer numCooFin) {
		this.numCooFin = numCooFin;
	}

	public BigDecimal getGtFin() {
		return gtFin;
	}

	public void setGtFin(BigDecimal gtFin) {
		this.gtFin = gtFin;
	}

	public BigDecimal getVlBrt() {
		return vlBrt;
	}

	public void setVlBrt(BigDecimal vlBrt) {
		this.vlBrt = vlBrt;
	}

	public RegC410 getRegC410() {
		return regC410;
	}

	public void setRegC410(RegC410 regC410) {
		this.regC410 = regC410;
	}

	public List<RegC420> getRegC420() {
		return regC420;
	}

	public void setRegC420(List<RegC420> regC420) {
		this.regC420 = regC420;
	}

	public List<RegC460> getRegC460() {
		return regC460;
	}

	public void setRegC460(List<RegC460> regC460) {
		this.regC460 = regC460;
	}

	public List<RegC490> getRegC490() {
		return regC490;
	}

	public void setRegC490(List<RegC490> regC490) {
		this.regC490 = regC490;
	}
    
}
