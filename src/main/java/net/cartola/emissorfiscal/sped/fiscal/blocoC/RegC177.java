package net.cartola.emissorfiscal.sped.fiscal.blocoC;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;

/**
 * 02/09/2020
 * @author robson.costa
 */
@Record(fields = { 
	@Field(name = "reg", maxLength = 4),
    @Field(name = "codInfItem")
})
public class RegC177 {
	
	private final String reg = "C177";
    private String codInfItem;

    public String getReg() {
		return reg;
	}

	public String getCodInfItem() {
		return codInfItem;
	}

	/**
	 * Código da informação adicional de acordo com tabela a ser publicada pelas
	 * SEFAZ, conforme tabela definida no item 5.6 
	 * @param codInfItem
	 */
	public void setCodInfItem(String codInfItem) {
		this.codInfItem = codInfItem;
	}
    
}
