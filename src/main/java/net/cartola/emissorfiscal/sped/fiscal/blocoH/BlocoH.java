package net.cartola.emissorfiscal.sped.fiscal.blocoH;

import java.util.List;

import coffeepot.bean.wr.annotation.Record;
import coffeepot.bean.wr.annotation.Field;


/**
 * 19/08/2020
 * @author robson.costa
 * 
 * BLOCO H - INVENTÁRIO FÍSICO
 * 
 * <b> IMPORTANTE: </b> Deixar somente Registros, cujo o nivel seja até o "NÍVEL == 2", acima disso (registros filhos), 
 * deveram ser preenchidos através de seu "REGISTRO PAI"  
 */
@Record(fields = {
	    @Field(name = "regH001"),
//	    @Field(name = "regH005"),
	    @Field(name = "regH990")
})
public class BlocoH {
	
    private RegH001AberturaDoBlocoH regH001;
//    private List<RegH005> regH005;
    private RegH990EncerramentoDoBlocoH regH990;

    public RegH001AberturaDoBlocoH getRegH001() {
		return regH001;
	}
	
	public void setRegH001(RegH001AberturaDoBlocoH regH001) {
		this.regH001 = regH001;
	}

//	public List<RegH005> getRegH005() {
//		return regH005;
//	}
//
//	public void setRegH005(List<RegH005> regH005) {
//		this.regH005 = regH005;
//	}

	public RegH990EncerramentoDoBlocoH getRegH990() {
		return regH990;
	}

	public void setRegH990(RegH990EncerramentoDoBlocoH regH990) {
		this.regH990 = regH990;
	}

}
