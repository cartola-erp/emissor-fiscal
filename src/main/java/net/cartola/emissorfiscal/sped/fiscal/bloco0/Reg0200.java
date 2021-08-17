package net.cartola.emissorfiscal.sped.fiscal.bloco0;

import java.util.List;


import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;
import coffeepot.bean.wr.typeHandler.DefaultStringHandler;
import coffeepot.bean.wr.types.Align;
import lombok.ToString;
import net.cartola.emissorfiscal.sped.fiscal.enums.TipoDoItem;

/**
 * 19/08/2020
 * @author robson.costa
 * 
 * Registro 0200 - Tabela de Identificação do Item (Produtos e Serviços)
 */
@Record(fields = { 
	@Field(name = "reg", maxLength = 4), 
    @Field(name = "codItem", padding = '0', minLength = 8, maxLength = 8),
    @Field(name = "descrItem"),
    @Field(name = "codBarra"),
    @Field(name = "codAntItem"),
    @Field(name = "unidInv", align = Align.RIGHT, padding = '0', minLength = 6, maxLength = 6),
    @Field(name = "tipoItem"),
    @Field(name = "codNcm", params = {DefaultStringHandler.PARAM_FILTER_NUMBER_ONLY}),
    @Field(name = "exIpi"),
    @Field(name = "codGen", params = {DefaultStringHandler.PARAM_FILTER_NUMBER_ONLY}),
    @Field(name = "codLst"),
    @Field(name = "aliqIcms"),
    @Field(name = "cest"),
    @Field(name = "reg0205"),
    @Field(name = "reg0206"),
    @Field(name = "reg0210"),
    @Field(name = "reg0220")
})	
@ToString
public class Reg0200 {
	
	private final String reg = "0200";
    private String codItem;
    private String descrItem;
    private String codBarra;
    private String codAntItem;
    private String unidInv;
    private TipoDoItem tipoItem = TipoDoItem.MERCADORIA_PARA_REVENDA;
    private String codNcm;
    private String exIpi;
    private String codGen;
    private String codLst;
    private Double aliqIcms;
    private String cest;
    private List<Reg0205> reg0205;
    private Reg0206 reg0206;
    private List<Reg0210> reg0210; // ACHO que nem se usa esse REGISTRO para o nosso caso
    private List<Reg0220> reg0220;
    
	public String getReg() {
		return reg;
	}

	public String getCodItem() {
		return codItem;
	}

	public void setCodItem(String codItem) {
		this.codItem = codItem;
	}

	public String getDescrItem() {
		return descrItem;
	}

	public void setDescrItem(String descrItem) {
		this.descrItem = descrItem;
	}

	public String getCodBarra() {
		return codBarra;
	}

	public void setCodBarra(String codBarra) {
		this.codBarra = codBarra;
	}

	public String getCodAntItem() {
		return codAntItem;
	}

	public void setCodAntItem(String codAntItem) {
		this.codAntItem = codAntItem;
	}

	public String getUnidInv() {
		return unidInv;
	}

	public void setUnidInv(String unidInv) {
		this.unidInv = unidInv;
	}

	public TipoDoItem getTipoItem() {
		return tipoItem;
	}

	public void setTipoItem(TipoDoItem tipoItem) {
		this.tipoItem = tipoItem;
	}

	public String getCodNcm() {
		return codNcm;
	}

	public void setCodNcm(String codNcm) {
		this.codNcm = codNcm;
	}

	public String getExIpi() {
		return exIpi;
	}

	public void setExIpi(String exIpi) {
		this.exIpi = exIpi;
	}

	public String getCodGen() {
		return codGen;
	}

	public void setCodGen(String codGen) {
		this.codGen = codGen;
	}

	public String getCodLst() {
		return codLst;
	}

	public void setCodLst(String codLst) {
		this.codLst = codLst;
	}

	public Double getAliqIcms() {
		return aliqIcms;
	}

	public void setAliqIcms(Double aliqIcms) {
		this.aliqIcms = aliqIcms;
	}

	public String getCest() {
		return cest;
	}

	public void setCest(String cest) {
		this.cest = cest;
	}

	public List<Reg0205> getReg0205() {
		return reg0205;
	}

	public void setReg0205(List<Reg0205> reg0205) {
		this.reg0205 = reg0205;
	}

	public Reg0206 getReg0206() {
		return reg0206;
	}

	public void setReg0206(Reg0206 reg0206) {
		this.reg0206 = reg0206;
	}

	public List<Reg0210> getReg0210() {
		return reg0210;
	}

	public void setReg0210(List<Reg0210> reg0210) {
		this.reg0210 = reg0210;
	}

	public List<Reg0220> getReg0220() {
		return reg0220;
	}

	public void setReg0220(List<Reg0220> reg0220) {
		this.reg0220 = reg0220;
	}
}
