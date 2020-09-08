package net.cartola.emissorfiscal.sped.fiscal.blocoC;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;
import coffeepot.bean.wr.typeHandler.DefaultStringHandler;

/**
 * 02/09/2020
 * @author robson.costa
 * 
 * REGISTRO C175: OPERAÇÕES COM VEÍCULOS NOVOS (CÓDIGO 01, 55)
 */
@Record(fields = { 
	@Field(name = "reg", maxLength = 4),
    @Field(name = "indVeicOper"),
    @Field(name = "cnpj", params = {DefaultStringHandler.PARAM_FILTER_NUMBER_ONLY}),
    @Field(name = "uf"),
    @Field(name = "chassiVeic")
})
public class RegC175 {
	
	private final String reg = "C175";
    private String indVeicOper;
    private String cnpj;
    private String uf;
    private String chassiVeic;
	
    public String getReg() {
		return reg;
	}

	public String getIndVeicOper() {
		return indVeicOper;
	}

	public void setIndVeicOper(String indVeicOper) {
		this.indVeicOper = indVeicOper;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getChassiVeic() {
		return chassiVeic;
	}

	public void setChassiVeic(String chassiVeic) {
		this.chassiVeic = chassiVeic;
	}

}
