package net.cartola.emissorfiscal.sped.fiscal.blocoC;

import java.util.List;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;

/**
 * 19/08/2020
 * @author robson.costa
 * 
 * BLOCO C - DOCUMENTOS FISCAIS I – MERCADORIAS (ICMS/IPI)
 * 
 *  * <b> IMPORTANTE: </b> Deixar somente Registros, cujo o nivel seja até o "NÍVEL == 2", acima disso (registros filhos), 
 * deveram ser preenchidos através de seu "REGISTRO PAI" 
 */

@Record(fields = {
        @Field(name = "regC001"),
        @Field(name = "regC100"),
        @Field(name = "regC350"),
        @Field(name = "regC400"),
        @Field(name = "regC500"),
        @Field(name = "regC800"),
        @Field(name = "regC990")
})
public class BlocoC {
	
	private RegC001AberturaDoBlocoC regC001;
	private List<RegC100> regC100;
    private List<RegC350> regC350;
    private List<RegC400> regC400;
    private List<RegC500> regC500;
    private List<RegC800> regC800;
	private RegC990EncerramentoDoBlocoC regC990;

	/** REGISTROS QUE APARENTEMENTE NÃO SÃO USADOS NA AG, CASO USE ALGUM LEMBRE-SE DE COLOCAR NA ORDEM NUMÉRICA CORRETA **/
		//  private List<RegC300> regC300;			/** PARA O PERFIL A NÃO é preciso (AO menos a MATRIZ é ESSE PERFIL) **/
		//  private List<RegC495> regC495;			/** APARENTEMENTE é apenas para o ESTADO da BAHIA 					**/
		//  private List<RegC600> regC600;			/** PARA O PERFIL A NÃO é preciso (AO menos a MATRIZ é ESSE PERFIL) **/
		//  private List<RegC700> regC700;			/** PERFIL A, é opcional na saida, e na entrada NÃO precisa 		**/
		//  private List<RegC860> regC860;			/** PARA O PERFIL A NÃO é preciso (AO menos a MATRIZ é ESSE PERFIL) **/
	
	public RegC001AberturaDoBlocoC getRegC001() {
		return regC001;
	}

	public void setRegC001(RegC001AberturaDoBlocoC regC001) {
		this.regC001 = regC001;
	}

	public List<RegC100> getRegC100() {
		return regC100;
	}

	public void setRegC100(List<RegC100> regC100) {
		this.regC100 = regC100;
	}

	public List<RegC350> getRegC350() {
		return regC350;
	}

	public void setRegC350(List<RegC350> regC350) {
		this.regC350 = regC350;
	}

	public List<RegC400> getRegC400() {
		return regC400;
	}

	public void setRegC400(List<RegC400> regC400) {
		this.regC400 = regC400;
	}

	public List<RegC500> getRegC500() {
		return regC500;
	}

	public void setRegC500(List<RegC500> regC500) {
		this.regC500 = regC500;
	}

	public List<RegC800> getRegC800() {
		return regC800;
	}

	public void setRegC800(List<RegC800> regC800) {
		this.regC800 = regC800;
	}

	public RegC990EncerramentoDoBlocoC getRegC990() {
		return regC990;
	}

	public void setRegC990(RegC990EncerramentoDoBlocoC regC990) {
		this.regC990 = regC990;
	}
	
}
