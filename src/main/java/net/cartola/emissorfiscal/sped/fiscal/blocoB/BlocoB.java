package net.cartola.emissorfiscal.sped.fiscal.blocoB;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;

/**
 * 19/08/2020
 * @author robson.costa
 */
@Record(fields = {
    @Field(name = "regB001"),
    @Field(name = "regB990")
})
public class BlocoB {
	
	private RegB001AberturaDoBloco regB001;
	private RegB990EncerramentoDoBlocoB regB990;
	
	public RegB001AberturaDoBloco getRegB001() {
		return regB001;
	}
	
	public RegB990EncerramentoDoBlocoB getRegB990() {
		return regB990;
	}

	public void setRegB001(RegB001AberturaDoBloco regB001) {
		this.regB001 = regB001;
	}

	public void setRegB990(RegB990EncerramentoDoBlocoB regB990) {
		this.regB990 = regB990;
	}
	
	
}
