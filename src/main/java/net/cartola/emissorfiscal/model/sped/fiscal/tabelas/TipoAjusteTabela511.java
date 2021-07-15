package net.cartola.emissorfiscal.model.sped.fiscal.tabelas;

/**
 * Enum com os tipos de ajustes presentes na "tabela: 5.1.1- Tabela de Códigos de Ajustes da Apuração do ICMS - SP";
 * 
 * O tipo de ajuste é descoberto pelo terceiro caractere do registro da tabela 5.1.1;
 * 
 * Ex.: COD AJUSTE: SP000210 -> É um código de ajuste para "ICMS PROPRIO", pois o 3º caractere é 0;
 * SP229999 -> Código de ajuste para DIFAL (pois o 3º caractere é 2);
 * 
 * @date 12 de jul. de 2021
 * @author robson.costa
 */
public enum TipoAjusteTabela511 {
	
	ICMS_PROPRIA('0'),
	ICMS_ST('1'),
	DIFAL('2'),
	FCP('3');
	
	private Character terceiroCodigo;
	
	private TipoAjusteTabela511(Character terceiroCodigo) {
		this.terceiroCodigo = terceiroCodigo;
	}
	
	public Character getTerceiroCodigo() {
		return this.terceiroCodigo;
	}
	
}
