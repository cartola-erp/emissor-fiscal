package net.cartola.emissorfiscal.sped.fiscal.blocoE;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;
import net.cartola.emissorfiscal.sped.fiscal.enums.OrigemDoProcesso;

/**
 * 11/09/2020
 * @author robson.costa
 */
@Record(fields = { 
		@Field(name = "reg", maxLength = 4),
		@Field(name = "numDA"),
		@Field(name = "numProc", maxLength = 15),
		@Field(name = "indProc"),
		@Field(name = "proc"),
		@Field(name = "txtCompl")
})
public class RegE112 {
	
	private final String reg = "E112";
	private String numDA;
	private String numProc;
	private OrigemDoProcesso indProc;
	private String proc;
	private String txtCompl;
	
	public String getReg() {
		return reg;
	}

	public String getNumDA() {
		return numDA;
	}

	public void setNumDA(String numDA) {
		this.numDA = numDA;
	}

	public String getNumProc() {
		return numProc;
	}

	public void setNumProc(String numProc) {
		this.numProc = numProc;
	}

	public OrigemDoProcesso getIndProc() {
		return indProc;
	}

	public void setIndProc(OrigemDoProcesso indProc) {
		this.indProc = indProc;
	}

	public String getProc() {
		return proc;
	}

	public void setProc(String proc) {
		this.proc = proc;
	}

	public String getTxtCompl() {
		return txtCompl;
	}

	public void setTxtCompl(String txtCompl) {
		this.txtCompl = txtCompl;
	}
	
}
