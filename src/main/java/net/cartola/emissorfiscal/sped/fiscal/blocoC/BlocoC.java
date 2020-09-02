package net.cartola.emissorfiscal.sped.fiscal.blocoC;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;

/**
 * 19/08/2020
 * @author robson.costa
 */

@Record(fields = {
        @Field(name = "regC001"),
        @Field(name = "regC100"),
        @Field(name = "regC990")
})
public class BlocoC {
	
	private RegC001AberturaDoBlocoC regC001;
	private RegC100 regC100;
	
	// ADICIONAR OS OUTROS REGISTROS (Pelo menos aqueles que estão no arquivo que a GABRIELA ME MANDOU) 
	
	private RegC990EncerramentoDoBlocoC regC990;

	
	
	public RegC001AberturaDoBlocoC getRegC001() {
		return regC001;
	}

	public void setRegC001(RegC001AberturaDoBlocoC regC001) {
		this.regC001 = regC001;
	}

	public RegC100 getRegC100() {
		return regC100;
	}

	public void setRegC100(RegC100 regC100) {
		this.regC100 = regC100;
	}

	public RegC990EncerramentoDoBlocoC getRegC990() {
		return regC990;
	}

	public void setRegC990(RegC990EncerramentoDoBlocoC regC990) {
		this.regC990 = regC990;
	}
	
}
