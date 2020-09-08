package net.cartola.emissorfiscal.sped.fiscal.blocoC;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;
import coffeepot.bean.wr.typeHandler.DefaultStringHandler;
import coffeepot.bean.wr.types.Align;
import net.cartola.emissorfiscal.sped.fiscal.enums.TipoTransporte;

/**
 * 02/09/2020
 * @author robson.costa
 */
@Record(fields = { 
	@Field(name = "reg", maxLength = 4),
    @Field(name = "indCarga"),
    @Field(name = "cnpjCol", params = {DefaultStringHandler.PARAM_FILTER_NUMBER_ONLY}),
    @Field(name = "ieCol", params = {DefaultStringHandler.PARAM_FILTER_NUMBER_LETTERS_ONLY}),
    @Field(name = "cpfCol", params = {DefaultStringHandler.PARAM_FILTER_NUMBER_ONLY}),
    @Field(name = "codMunCol", length = 7, align = Align.RIGHT, padding = '0', paddingIfNullOrEmpty = true),
    @Field(name = "cnpjEntg", params = {DefaultStringHandler.PARAM_FILTER_NUMBER_ONLY}),
    @Field(name = "ieEntg", params = {DefaultStringHandler.PARAM_FILTER_NUMBER_LETTERS_ONLY}),
    @Field(name = "cpfEntg", params = {DefaultStringHandler.PARAM_FILTER_NUMBER_ONLY}),
    @Field(name = "codMunEntg", length = 7, align = Align.RIGHT, padding = '0', paddingIfNullOrEmpty = true)
})
public class RegC115 {
	
	private final String reg = "C115";
    private TipoTransporte indCarga;
    private String cnpjCol;
    private String ieCol;
    private String cpfCol;
    private Integer codMunCol;
    private String cnpjEntg;
    private String ieEntg;
    private String cpfEntg;
    private Integer codMunEntg;
	
    public String getReg() {
		return reg;
	}

	public TipoTransporte getIndCarga() {
		return indCarga;
	}

	public void setIndCarga(TipoTransporte indCarga) {
		this.indCarga = indCarga;
	}

	public String getCnpjCol() {
		return cnpjCol;
	}

	public void setCnpjCol(String cnpjCol) {
		this.cnpjCol = cnpjCol;
	}

	public String getIeCol() {
		return ieCol;
	}

	public void setIeCol(String ieCol) {
		this.ieCol = ieCol;
	}

	public String getCpfCol() {
		return cpfCol;
	}

	public void setCpfCol(String cpfCol) {
		this.cpfCol = cpfCol;
	}

	public Integer getCodMunCol() {
		return codMunCol;
	}

	public void setCodMunCol(Integer codMunCol) {
		this.codMunCol = codMunCol;
	}

	public String getCnpjEntg() {
		return cnpjEntg;
	}

	public void setCnpjEntg(String cnpjEntg) {
		this.cnpjEntg = cnpjEntg;
	}

	public String getIeEntg() {
		return ieEntg;
	}

	public void setIeEntg(String ieEntg) {
		this.ieEntg = ieEntg;
	}

	public String getCpfEntg() {
		return cpfEntg;
	}

	public void setCpfEntg(String cpfEntg) {
		this.cpfEntg = cpfEntg;
	}

	public Integer getCodMunEntg() {
		return codMunEntg;
	}

	public void setCodMunEntg(Integer codMunEntg) {
		this.codMunEntg = codMunEntg;
	}
    
}
