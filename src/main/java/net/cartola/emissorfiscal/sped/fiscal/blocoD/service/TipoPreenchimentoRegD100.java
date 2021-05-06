package net.cartola.emissorfiscal.sped.fiscal.blocoD.service;

/**
 * 
 * 
 * Abaixo é os Enum para TODAS as "Excecções", do preenchimento do REGISTRO D100,
 * 
 * PS: Algumas são semelhantes ou iguais ao do Preenchimento do REG C100
 * 
 * Porém cada exceção pode ter sua particularidade de preenchimento.
 * 
 * OBS: "NORMAL" É quando o DocumentoFiscal não se encaixar em nenhuma excecao
 * 
 * @date 26 de fev. de 2021
 * @author robson.costa
 */
enum TipoPreenchimentoRegD100 {

	
	EX_1_COD_SITUACAO, 
	EX_2_CTE_COMPLEMENTAR, 
	EX_3_CTE_REGIME_ESPECIAL_NORMA_ESPECIFICA,
	EX_4_CTE_EMISSAO_PROPRIA, 
	EX_5_CTE_EMITIDA_TERCEIROS,

	NORMAL;
	
	
}
