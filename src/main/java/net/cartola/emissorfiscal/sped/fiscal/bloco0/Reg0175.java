package net.cartola.emissorfiscal.sped.fiscal.bloco0;

import java.time.LocalDate;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;

/**
 * 19/08/2020
 * @author robson.costa
 * 
 * Registro 0175 - Alteração da Tabela de Cadastro de Participante
 */

@Record(fields = { 
	@Field(name = "reg", maxLength = 4), 
	@Field(name = "dtAlt"),
	@Field(name = "nrCamp"),
	@Field(name = "contAnt")
})		
public class Reg0175 {
	
	private final String reg = "0175";
    private LocalDate dtAlt;
    private int nrCamp;
    private String contAnt;
	
    public String getReg() {
		return reg;
	}
	
	public LocalDate getDtAlt() {
		return dtAlt;
	}

	public void setDtAlt(LocalDate dtAlt) {
		this.dtAlt = dtAlt;
	}

	public int getNrCamp() {
		return nrCamp;
	}

	public void setNrCamp(int nrCamp) {
		this.nrCamp = nrCamp;
	}

	public String getContAnt() {
		return contAnt;
	}

	public void setContAnt(String contAnt) {
		this.contAnt = contAnt;
	}
	
}
