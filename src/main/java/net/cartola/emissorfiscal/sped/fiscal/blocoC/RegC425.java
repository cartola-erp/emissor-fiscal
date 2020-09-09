package net.cartola.emissorfiscal.sped.fiscal.blocoC;

import java.math.BigDecimal;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;

/**
 * 02/09/2020
 * @author robson.costa
 */
@Record(fields = { 
	@Field(name = "reg", maxLength = 4),
    @Field(name = "codItem"),
    @Field(name = "qtd"),
    @Field(name = "unid"),
    @Field(name = "vlItem"),
    @Field(name = "vlPis"),
    @Field(name = "vlCofins")
})
public class RegC425 {
	
	/**
	 * PARA O PERFIL ==> " A " NÃO é preciso (AO menos a MATRIZ é ESSE PERFIL) 
	 * 
	 */
	
	private final String reg = "C425";
    private String codItem;
    private Double qtd;
    private String unid;
    private BigDecimal vlItem;
    private BigDecimal vlPis;
    private BigDecimal vlCofins;
	
    public String getReg() {
		return reg;
	}

	public String getCodItem() {
		return codItem;
	}

	public void setCodItem(String codItem) {
		this.codItem = codItem;
	}

	public Double getQtd() {
		return qtd;
	}

	public void setQtd(Double qtd) {
		this.qtd = qtd;
	}

	public String getUnid() {
		return unid;
	}

	public void setUnid(String unid) {
		this.unid = unid;
	}

	public BigDecimal getVlItem() {
		return vlItem;
	}

	public void setVlItem(BigDecimal vlItem) {
		this.vlItem = vlItem;
	}

	public BigDecimal getVlPis() {
		return vlPis;
	}

	public void setVlPis(BigDecimal vlPis) {
		this.vlPis = vlPis;
	}

	public BigDecimal getVlCofins() {
		return vlCofins;
	}

	public void setVlCofins(BigDecimal vlCofins) {
		this.vlCofins = vlCofins;
	}
	
}
