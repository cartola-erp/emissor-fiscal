package net.cartola.emissorfiscal.sped.fiscal;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * 
 * @autor robson.costa
 * @data 12 de abr. de 2021
 */
@Getter
@Setter
public class CodificacaoReg0450InfoComplementarFisco {

	private Long id;
	private String codInfo;
	private String descricao;
	
	
	// Maybe is necessary, i add the field, to link where i can use this cod in the reg SPED;
	// I know my english is bad...
	
	// Talvez seja necessário. Adicionar uma List<String>, para poder addicionar, todos os registros possiveis, onde posso usar
	// os Códigos que está no Reg0450
	
}
