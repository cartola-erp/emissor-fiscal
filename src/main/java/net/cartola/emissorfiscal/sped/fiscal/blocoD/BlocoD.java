package net.cartola.emissorfiscal.sped.fiscal.blocoD;

import java.util.List;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;

/**
 * 19/08/2020
 * @author robson.costa
 * 
 * BLOCO D - DOCUMENTOS FISCAIS II - SERVIÇOS (ICMS)
 * 
 *  <b> IMPORTANTE: </b> Deixar somente Registros, cujo o nivel seja até o "NÍVEL == 2", acima disso (registros filhos), 
 * deveram ser preenchidos através de seu "REGISTRO PAI"  
 */
@Record(fields = {
    @Field(name = "regD001"),
    @Field(name = "regD100"),
    @Field(name = "regD500"),
    @Field(name = "regD990")
})
public class BlocoD {
	
	private RegD001AberturaDoBlocoD regD001;
	private List<RegD100> regD100;
	private List<RegD500> regD500;
	private RegD990EncerramentoDoBlocoD regD990;
	
	/** REGISTROS QUE APARENTEMENTE NÃO SÃO USADOS NA AG, CASO USE ALGUM LEMBRE-SE DE COLOCAR NA ORDEM NUMÉRICA CORRETA 
	 * 	E ALÉM DISSO tem que olhar na documentação do governo para adicionar os ATRIBUTOS nas respectivas classes 	 * **/
	
		//  private List<RegD300> regD300;
		//  private List<RegD350> regD350;
		//  private List<RegD400> regD400;
			
		//  private List<RegD600> regD600;
		//  private List<RegD695> regD695;
	
	public RegD001AberturaDoBlocoD getRegD001() {
		return regD001;
	}
	
	public void setRegD001(RegD001AberturaDoBlocoD regD001) {
		this.regD001 = regD001;
	}

	public List<RegD100> getRegD100() {
		return regD100;
	}

	public void setRegD100(List<RegD100> regD100) {
		this.regD100 = regD100;
	}

	public List<RegD500> getRegD500() {
		return regD500;
	}

	public void setRegD500(List<RegD500> regD500) {
		this.regD500 = regD500;
	}

	public RegD990EncerramentoDoBlocoD getRegD990() {
		return regD990;
	}

	public void setRegD990(RegD990EncerramentoDoBlocoD regD990) {
		this.regD990 = regD990;
	}
	
}
