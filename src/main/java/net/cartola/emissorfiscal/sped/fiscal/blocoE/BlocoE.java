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
	    
	    @Field(name = "regE990"),
})
public class BlocoE {
	
    private RegE001AberturaDoBlocoE regE001;
    private List<RegE100> regE100;
//	private List<RegE200> regE200;				// PAREI nesse grupo aqui AQUi
//  private List<RegE500> regE500;
    private RegE990EncerramentoDoBlocoE regE990;
    
    
}
