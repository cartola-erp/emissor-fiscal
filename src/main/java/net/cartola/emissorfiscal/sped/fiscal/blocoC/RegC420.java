package net.cartola.emissorfiscal.sped.fiscal.blocoC;

import java.math.BigDecimal;
import java.util.List;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;

/**
 * 02/09/2020
 * @author robson.costa
 */
@Record(fields = { 
	@Field(name = "reg", maxLength = 4),
    @Field(name = "codTotPar"),
    @Field(name = "vlrAcumTot"),
    @Field(name = "nrTot"),
    @Field(name = "descrNrTot"),
    // ==================== FILHO ====================
    @Field(name = "regC425")
})
public class RegC420 {
	
	private final String reg = "C420";
    private String codTotPar;
    private BigDecimal vlrAcumTot;
    private Integer nrTot;
    private String descrNrTot;
    // ==================== FILHO ====================
    private List<RegC425> regC425;
	
    public String getReg() {
		return reg;
	}

	public String getCodTotPar() {
		return codTotPar;
	}

	public void setCodTotPar(String codTotPar) {
		this.codTotPar = codTotPar;
	}

	public BigDecimal getVlrAcumTot() {
		return vlrAcumTot;
	}

	public void setVlrAcumTot(BigDecimal vlrAcumTot) {
		this.vlrAcumTot = vlrAcumTot;
	}

	public Integer getNrTot() {
		return nrTot;
	}

	public void setNrTot(Integer nrTot) {
		this.nrTot = nrTot;
	}

	public String getDescrNrTot() {
		return descrNrTot;
	}

	public void setDescrNrTot(String descrNrTot) {
		this.descrNrTot = descrNrTot;
	}

	public List<RegC425> getRegC425() {
		return regC425;
	}

	public void setRegC425(List<RegC425> regC425) {
		this.regC425 = regC425;
	}
    
}
