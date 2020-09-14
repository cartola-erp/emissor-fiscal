package net.cartola.emissorfiscal.sped.fiscal.blocoK;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;

/**
 * 19/08/2020
 * @author robson.costa
 * 
 * BLOCO K - CONTROLE DA PRODUÇÃO E DO ESTOQUE 
 * 
 * <b> IMPORTANTE: </b> Deixar somente Registros, cujo o nivel seja até o "NÍVEL == 2", acima disso (registros filhos), 
 * deveram ser preenchidos através de seu "REGISTRO PAI"  
 */
@Record(fields = {
	    @Field(name = "regK001"),
	    @Field(name = "regK990")
})
public class BlocoK {
	
    private RegK001AberturaDoBlocoK regK001;
//    private List<RegK100> regK100;
    private RegK990EncerramentoDoBlocoK regK990;
	
    public RegK001AberturaDoBlocoK getRegK001() {
		return regK001;
	}
	
	public void setRegK001(RegK001AberturaDoBlocoK regK001) {
		this.regK001 = regK001;
	}
	
	public RegK990EncerramentoDoBlocoK getRegK990() {
		return regK990;
	}

	public void setRegK990(RegK990EncerramentoDoBlocoK regK990) {
		this.regK990 = regK990;
	}
    	
}
