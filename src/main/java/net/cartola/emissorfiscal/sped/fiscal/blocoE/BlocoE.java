package net.cartola.emissorfiscal.sped.fiscal.blocoE;

import java.util.List;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;

/**
 * 19/08/2020
 * @author robson.costa
 */
@Record(fields = {
	    @Field(name = "regE001"),
	    @Field(name = "regE100"),
	    @Field(name = "regE300"),
	    @Field(name = "regE990")
})
public class BlocoE {
	
    private RegE001AberturaDoBlocoE regE001;
    private List<RegE100> regE100;
//	private List<RegE200> regE200;				
    private List<RegE300> regE300;
//  private List<RegE500> regE500;
    private RegE990EncerramentoDoBlocoE regE990;
	
    public RegE001AberturaDoBlocoE getRegE001() {
		return regE001;
	}
	
	public void setRegE001(RegE001AberturaDoBlocoE regE001) {
		this.regE001 = regE001;
	}

	public List<RegE100> getRegE100() {
		return regE100;
	}

	public void setRegE100(List<RegE100> regE100) {
		this.regE100 = regE100;
	}

	public List<RegE300> getRegE300() {
		return regE300;
	}

	public void setRegE300(List<RegE300> regE300) {
		this.regE300 = regE300;
	}

	public RegE990EncerramentoDoBlocoE getRegE990() {
		return regE990;
	}

	public void setRegE990(RegE990EncerramentoDoBlocoE regE990) {
		this.regE990 = regE990;
	}

}
