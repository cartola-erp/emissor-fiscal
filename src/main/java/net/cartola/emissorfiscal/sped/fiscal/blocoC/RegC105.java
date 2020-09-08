package net.cartola.emissorfiscal.sped.fiscal.blocoC;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;
import net.cartola.emissorfiscal.sped.fiscal.enums.TipoDeOperacao;

/**
 * 02/09/2020
 * @author robson.costa
 * 
 * REGISTRO C105 - OPERAÇÕES COM ICMS ST RECOLHIDO PARA UF DIVERSA DO DESTINATÁRIO DO
 * DOCUMENTO FISCAL (CÓDIGO 55)
 * 
 */
@Record(fields = { 
	@Field(name = "reg", maxLength = 4),
    @Field(name = "oper"),
    @Field(name = "uf")
})
public class RegC105 {
	
	private final String reg = "C105";
	private TipoDeOperacao oper;
	private String uf;
	
	public String getReg() {
		return reg;
	}

	public TipoDeOperacao getOper() {
		return oper;
	}

	public void setOper(TipoDeOperacao oper) {
		this.oper = oper;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}
	
}
