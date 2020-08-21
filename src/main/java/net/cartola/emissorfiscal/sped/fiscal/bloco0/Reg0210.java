package net.cartola.emissorfiscal.sped.fiscal.bloco0;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;

/**
 * 19/08/2020
 * @author robson.costa
 * 
 * Registro 0210 - Consumo Específico Padronizado
 * ACHO que nem se usa esse REGISTRO para o nosso caso
 */
@Record(fields = { 
	@Field(name = "reg", maxLength = 4), 
	@Field(name = "codItemComp"), 
	@Field(name = "qtdComp"), 
	@Field(name = "perda")
})			
public class Reg0210 {
	
	private final String reg = "0210";
	private String codItemComp;
	private int qtdComp;
	private int perda;
	
	public String getReg() {
		return reg;
	}

	public String getCodItemComp() {
		return codItemComp;
	}

	public void setCodItemComp(String codItemComp) {
		this.codItemComp = codItemComp;
	}

	public int getQtdComp() {
		return qtdComp;
	}

	public void setQtdComp(int qtdComp) {
		this.qtdComp = qtdComp;
	}

	public int getPerda() {
		return perda;
	}

	public void setPerda(int perda) {
		this.perda = perda;
	}
}
