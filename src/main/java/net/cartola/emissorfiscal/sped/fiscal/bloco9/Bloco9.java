package net.cartola.emissorfiscal.sped.fiscal.bloco9;

import java.util.List;

import coffeepot.bean.wr.annotation.Record;
import coffeepot.bean.wr.annotation.Field;


/**
 * 19/08/2020
 * @author robson.costa
 * 
 *  Bloco 9 - Encerramento do arquivo digital
 */
@Record(fields = {
	    @Field(name = "reg9001"),
	    @Field(name = "reg9900"),
	    @Field(name = "reg9990"),
	    @Field(name = "reg9999")
})
public class Bloco9 {
	
    private Reg9001AberturaDoBloco9 reg9001;
    private List<Reg9900> reg9900;
    private Reg9990EncerramentoDoBloco9 reg9990;
    private Reg9999EncerramentoDoArquivoDigital reg9999;
	
    public Reg9001AberturaDoBloco9 getReg9001() {
		return reg9001;
	}
	
    public void setReg9001(Reg9001AberturaDoBloco9 reg9001) {
		this.reg9001 = reg9001;
	}

	public List<Reg9900> getReg9900() {
		return reg9900;
	}

	public void setReg9900(List<Reg9900> reg9900) {
		this.reg9900 = reg9900;
	}

	public Reg9990EncerramentoDoBloco9 getReg9990() {
		return reg9990;
	}

	public void setReg9990(Reg9990EncerramentoDoBloco9 reg9990) {
		this.reg9990 = reg9990;
	}

	public Reg9999EncerramentoDoArquivoDigital getReg9999() {
		return reg9999;
	}

	public void setReg9999(Reg9999EncerramentoDoArquivoDigital reg9999) {
		this.reg9999 = reg9999;
	}
    
}
