package net.cartola.emissorfiscal.sped.fiscal.bloco0;

import java.io.Serializable;
import java.util.List;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;

/**
 * 19/08/2020
 * @author robson.costa
 * 
 * Bloco 0 - Identificação e referências (registros de tabelas)
 * 
 * <b> IMPORTANTE: </b> Deixar somente Registros, cujo o nivel seja até o "NÍVEL == 2", acima disso (registros filhos), 
 * deveram ser preenchidos através de seu "REGISTRO PAI" 
 */
@Record(fields = {
	@Field(name = "reg0000"),
	@Field(name = "reg0001"),
	@Field(name = "reg0005"),
//	@Field(name = "reg0015"),
	@Field(name = "reg0100"),
	@Field(name = "reg0150"),
	@Field(name = "reg0190"),
	@Field(name = "reg0200"),
	@Field(name = "reg0300"),
	@Field(name = "reg0400"),
	@Field(name = "reg0450"),
	@Field(name = "reg0460"),
//	@Field(name = "reg0500"),
//    @Field(name = "reg0600"),
	@Field(name = "reg0990")
})    
public class Bloco0 implements Serializable {
	
	private static final long serialVersionUID = -1432636138632034013L;
	
	private Reg0000AberturaArquivoDigital reg0000;
	private Reg0001AberturaDoBloco reg0001;
//	private Reg0002 reg0002;
	private Reg0005 reg0005;
//	private List<Reg0015> reg0015;
	private Reg0100 reg0100;
	private List<Reg0150> reg0150;
	private List<Reg0190> reg0190;		
	private List<Reg0200> reg0200;
	private List<Reg0300> reg0300;
	private List<Reg0400> reg0400;
	private List<Reg0450> reg0450;
	private List<Reg0460> reg0460;
//	private List<Reg0500> reg0500;
//	private List<Reg0600> reg0600;
	private Reg0990EncerramentoDoBloco reg0990;
	
	public Reg0000AberturaArquivoDigital getReg0000() {
		return reg0000;
	}
	
	public void setReg0000(Reg0000AberturaArquivoDigital reg0000) {
		this.reg0000 = reg0000;
	}

	public Reg0001AberturaDoBloco getReg0001() {
		return reg0001;
	}

	public void setReg0001(Reg0001AberturaDoBloco reg0001) {
		this.reg0001 = reg0001;
	}

	public Reg0005 getReg0005() {
		return reg0005;
	}

	public void setReg0005(Reg0005 reg0005) {
		this.reg0005 = reg0005;
	}

//	public List<Reg0015> getReg0015() {
//		return reg0015;
//	}
//
//	public void setReg0015(List<Reg0015> reg0015) {
//		this.reg0015 = reg0015;
//	}

	public Reg0100 getReg0100() {
		return reg0100;
	}

	public void setReg0100(Reg0100 reg0100) {
		this.reg0100 = reg0100;
	}

	public List<Reg0150> getReg0150() {
		return reg0150;
	}

	public void setReg0150(List<Reg0150> reg0150) {
		this.reg0150 = reg0150;
	}

	public List<Reg0190> getReg0190() {
		return reg0190;
	}

	public void setReg0190(List<Reg0190> reg0190) {
		this.reg0190 = reg0190;
	}

	public List<Reg0200> getReg0200() {
		return reg0200;
	}

	public void setReg0200(List<Reg0200> reg0200) {
		this.reg0200 = reg0200;
	}

	public List<Reg0300> getReg0300() {
		return reg0300;
	}

	public void setReg0300(List<Reg0300> reg0300) {
		this.reg0300 = reg0300;
	}

	public List<Reg0400> getReg0400() {
		return reg0400;
	}

	public void setReg0400(List<Reg0400> reg0400) {
		this.reg0400 = reg0400;
	}

	public List<Reg0450> getReg0450() {
		return reg0450;
	}

	public void setReg0450(List<Reg0450> reg0450) {
		this.reg0450 = reg0450;
	}

	public List<Reg0460> getReg0460() {
		return reg0460;
	}

	public void setReg0460(List<Reg0460> reg0460) {
		this.reg0460 = reg0460;
	}

//	public List<Reg0500> getReg0500() {
//		return reg0500;
//	}
//
//	public void setReg0500(List<Reg0500> reg0500) {
//		this.reg0500 = reg0500;
//	}
//
//	public List<Reg0600> getReg0600() {
//		return reg0600;
//	}
//
//	public void setReg0600(List<Reg0600> reg0600) {
//		this.reg0600 = reg0600;
//	}

	public Reg0990EncerramentoDoBloco getReg0990() {
		return reg0990;
	}

	public void setReg0990(Reg0990EncerramentoDoBloco reg0990) {
		this.reg0990 = reg0990;
	}
}
