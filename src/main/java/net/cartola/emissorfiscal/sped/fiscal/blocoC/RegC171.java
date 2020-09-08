package net.cartola.emissorfiscal.sped.fiscal.blocoC;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;

/**
 * 02/09/2020
 * @author robson.costa
 */
@Record(fields = { 
	@Field(name = "reg", maxLength = 4),
    @Field(name = "numTanque"),
    @Field(name = "qtde")
})
public class RegC171 {

	private final String reg = "C171";
    private String numTanque;
    private Double qtde;
	
    public String getReg() {
		return reg;
	}

	public String getNumTanque() {
		return numTanque;
	}

	public void setNumTanque(String numTanque) {
		this.numTanque = numTanque;
	}

	public Double getQtde() {
		return qtde;
	}

	public void setQtde(Double qtde) {
		this.qtde = qtde;
	}
    
}
