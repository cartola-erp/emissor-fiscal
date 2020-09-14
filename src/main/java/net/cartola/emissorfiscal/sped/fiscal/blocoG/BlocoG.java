package net.cartola.emissorfiscal.sped.fiscal.blocoG;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;

/**
 * 19/08/2020
 * @author robson.costa
 * 
 * BLOCO G - CONTROLE DO CRÉDITO DE ICMS DO ATIVO PERMANENTE - CIAP 
 * 
 */
@Record(fields = {
	    @Field(name = "regG001"),
	    @Field(name = "regG990")
})
public class BlocoG {
	
	private RegG001AberturaDoBlocoG regG001;
//	    private List<RegG110> regG110List;
	private RegG990EncerramentoDoBlocoG regG990;

	public RegG001AberturaDoBlocoG getRegG001() {
		return regG001;
	}
	
	public void setRegG001(RegG001AberturaDoBlocoG regG001) {
		this.regG001 = regG001;
	}
	
	public RegG990EncerramentoDoBlocoG getRegG990() {
		return regG990;
	}
	
	public void setRegG990(RegG990EncerramentoDoBlocoG regG990) {
		this.regG990 = regG990;
	}
	
}
