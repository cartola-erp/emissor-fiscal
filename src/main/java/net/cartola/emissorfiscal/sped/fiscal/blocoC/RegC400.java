package net.cartola.emissorfiscal.sped.fiscal.blocoC;

import java.util.List;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;
import coffeepot.bean.wr.typeHandler.DefaultStringHandler;
import net.cartola.emissorfiscal.sped.fiscal.enums.ModeloDocumentoFiscal;

/**
 * 02/09/2020
 * @author robson.costa
 */
@Record(fields = { 
	@Field(name = "reg", maxLength = 4),
    @Field(name = "codMod"),
    @Field(name = "ecfMod"),
    @Field(name = "ecfFab", params = {DefaultStringHandler.PARAM_FILTER_NUMBER_LETTERS_ONLY}),
    @Field(name = "ecfCx"),
    // ==================== FILHO ====================
    @Field(name = "regC405")
})
public class RegC400 {
	
	private final String reg = "C400";

    private ModeloDocumentoFiscal codMod;
    private String ecfMod;
    private String ecfFab;
    private Integer ecfCx;
    // ==================== FILHO ====================
    private List<RegC405> regC405;
	
    public String getReg() {
		return reg;
	}

	public ModeloDocumentoFiscal getCodMod() {
		return codMod;
	}

	public void setCodMod(ModeloDocumentoFiscal codMod) {
		this.codMod = codMod;
	}

	public String getEcfMod() {
		return ecfMod;
	}

	public void setEcfMod(String ecfMod) {
		this.ecfMod = ecfMod;
	}

	public String getEcfFab() {
		return ecfFab;
	}

	public void setEcfFab(String ecfFab) {
		this.ecfFab = ecfFab;
	}

	public Integer getEcfCx() {
		return ecfCx;
	}

	public void setEcfCx(Integer ecfCx) {
		this.ecfCx = ecfCx;
	}

	public List<RegC405> getRegC405() {
		return regC405;
	}

	public void setRegC405(List<RegC405> regC405) {
		this.regC405 = regC405;
	}

}
