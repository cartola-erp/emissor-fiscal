package net.cartola.emissorfiscal.sped.fiscal.blocoC;

import java.math.BigDecimal;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;

/**
 * 02/09/2020
 * @author robson.costa
 */
@Record(fields = { 
	@Field(name = "reg", maxLength = 4),
    @Field(name = "bcStOrigDest"),
    @Field(name = "icmsStRep"),
    @Field(name = "icmsStCompl"),
    @Field(name = "bcRet"),
    @Field(name = "icmsRet")
})
public class RegC179 {

	private final String reg = "C179";
    private BigDecimal bcStOrigDest;
    private BigDecimal icmsStRep;
    private BigDecimal icmsStCompl;
    private BigDecimal bcRet;
    private BigDecimal icmsRet;
	
    public String getReg() {
		return reg;
	}

	public BigDecimal getBcStOrigDest() {
		return bcStOrigDest;
	}

	public void setBcStOrigDest(BigDecimal bcStOrigDest) {
		this.bcStOrigDest = bcStOrigDest;
	}

	public BigDecimal getIcmsStRep() {
		return icmsStRep;
	}

	public void setIcmsStRep(BigDecimal icmsStRep) {
		this.icmsStRep = icmsStRep;
	}

	public BigDecimal getIcmsStCompl() {
		return icmsStCompl;
	}

	public void setIcmsStCompl(BigDecimal icmsStCompl) {
		this.icmsStCompl = icmsStCompl;
	}

	public BigDecimal getBcRet() {
		return bcRet;
	}

	public void setBcRet(BigDecimal bcRet) {
		this.bcRet = bcRet;
	}

	public BigDecimal getIcmsRet() {
		return icmsRet;
	}

	public void setIcmsRet(BigDecimal icmsRet) {
		this.icmsRet = icmsRet;
	}
    
}
