package net.cartola.emissorfiscal.model.sped.fiscal.tabelas;

/**
 * O quarto caractere deve ser preenchido, conforme item 5.1.1. da Nota Técnica, instituída pelo Ato COTEPE/ICMS nº 
 * 44/2018 e alterações, com um dos códigos abaixo:
 * 
 * 0 – Outros débitos; 
 * 1 – Estorno de créditos; 
 * 2 – Outros créditos; 
 * 3 – Estorno de débitos; 
 * 4 – Deduções do imposto apurado; 
 * 5 – Débitos Especiais.
 * 
 * @date 12 de jul. de 2021
 * @author robson.costa
 */
enum TipoDeducaoTabela511 {
	
	
	OUTROS_DEBITOS('0'),
	ESTORNO_DE_CREDITOS('1'),
	OUTROS_CREDITOS('2'),
	ESTORNO_DE_DEBITOS('3'),
	DEDUCOES_DO_IMPOSTO_APURADO('4'),
	DEBITOS_ESPECIAIS('5');


	private Character quartoCodigo;

	private TipoDeducaoTabela511(Character quartoCodigo) {
		this.quartoCodigo = quartoCodigo;
	}

	public Character getQuartoCodigo() {
		return this.quartoCodigo;
	}

}
