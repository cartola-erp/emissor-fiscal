package net.cartola.emissorfiscal.sped.fiscal.bloco0;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;

/**
 * 19/08/2020
 * @author robson.costa
 * 
 * 
 * Registro 0206 - Código de produto conforme Tabela ANP
 */
@Record(fields = { 
	@Field(name = "reg", maxLength = 4), 
    @Field(name = "codComb")
})	
public class Reg0206 {
	
	private final String reg = "0206";
    private String codComb;
	
    public String getReg() {
		return reg;
	}

	public String getCodComb() {
		return codComb;
	}

	public void setCodComb(String codComb) {
		this.codComb = codComb;
	}
}
