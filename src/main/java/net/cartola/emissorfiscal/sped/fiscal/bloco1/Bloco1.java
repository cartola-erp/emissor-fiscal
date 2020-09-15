package net.cartola.emissorfiscal.sped.fiscal.bloco1;

import coffeepot.bean.wr.annotation.Record;
import coffeepot.bean.wr.annotation.Field;

/**
 * 19/08/2020
 * @author robson.costa
 * 
 * BLOCO 1 - OUTRAS INFORMAÇÕES

 * <b> IMPORTANTE: </b> Deixar somente Registros, cujo o nivel seja até o "NÍVEL == 2", acima disso (registros filhos), 
 * deveram ser preenchidos através de seu "REGISTRO PAI"  
 * 
 */
@Record(fields = {
	    @Field(name = "reg1001"),
	    @Field(name = "reg1010"),

//	   @Field(name = "reg1100"),
//	   @Field(name = "reg1200"),
//	   @Field(name = "reg1300"),
//	   @Field(name = "reg1350"),
//	   @Field(name = "reg1390"),
//	   @Field(name = "reg1400"),
//	   @Field(name = "reg1500"),
//	   @Field(name = "reg1600"),
//	   @Field(name = "reg1700"),
//	   @Field(name = "reg1800"),
//	   @Field(name = "reg1900"),
	   @Field(name = "reg1990")
})
public class Bloco1 {

	private Reg1001AberturaDoBloco1 reg1001;
	private Reg1010 reg1010;

	private Reg1990EncerramentoDoBloco1 reg1990;

	/** GRUPO de que NÃO estavam presentes em nenhum arquivo do SPED (gerado pelo SAGE), que a Gabi me passou
	 * 	Caso precise de algum, só modela todo o registro do grupo
	 */
//	private List<Reg1100> reg1100;
//	private List<Reg1200> reg1200;
//	private List<Reg1300> reg1300;
//	private List<Reg1350> reg1350;
//	private List<Reg1390> reg1390;
//	private List<Reg1400> reg1400;
//	private List<Reg1500> reg1500;
//	private List<Reg1600> reg1600;
//	private List<Reg1700> reg1700;
//	private Reg1800 reg1800;
//	private List<Reg1900> reg1900;
	
	public Reg1001AberturaDoBloco1 getReg1001() {
		return reg1001;
	}

	public void setReg1001(Reg1001AberturaDoBloco1 reg1001) {
		this.reg1001 = reg1001;
	}

	public Reg1010 getReg1010() {
		return reg1010;
	}

	public void setReg1010(Reg1010 reg1010) {
		this.reg1010 = reg1010;
	}

	public Reg1990EncerramentoDoBloco1 getReg1990() {
		return reg1990;
	}

	public void setReg1990(Reg1990EncerramentoDoBloco1 reg1990) {
		this.reg1990 = reg1990;
	}
	
}
