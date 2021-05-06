package net.cartola.emissorfiscal.sped.fiscal.blocoC.service;

/**
 * Abaixo é os Enum para TODAS as "Excecções", do preenchimento do REGISTRO C100,
 * em que não é preciso ser preenchido os Registros filhos: C170 e C190.
 * 
 * 
 * Porém cada exceção pode ter sua particularidade de preenchimento.
 * 
 * OBS: "NORMAL" É quando o DocumentoFiscal não se encaixar em nenhuma excecao
 * 
 * @date 26 de fev. de 2021
 * @author robson.costa
 */
enum TipoPreenchimentoRegC100 {

	
	EX_1_COD_SITUACAO, 
	EX_2_NFE_EMISSAO_PROPRIA, 
	EX_3_NFE_COMPLEMENTAR,
	EX_4_NFE_REGIME_ESPECIAL_NORMA_ESPECIFICA,
	EX_5_,
	EX_6_VENDA_COM_RESSARCIMENTO,
	EX_7_NFE_EMITIDA_TERCEIROS,
	EX_8_NFE_UF_CONSUMO_PREENCHIDO,
	EX_9_NFCE,
	EX_10_INFO_COMPLEMENTAR_ITEM,
	
	NORMAL;
	
	
}
