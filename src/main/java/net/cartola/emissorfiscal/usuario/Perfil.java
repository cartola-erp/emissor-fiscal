package net.cartola.emissorfiscal.usuario;

/**
 *	26 de dez de 2019
 *	@author robson.costa
 */
public enum Perfil {
	
	/**
	 *  Acesso a Tudo,
	 *  Ex. Administrar usuário (As roles abaixo não terão isso)
	 */
	ADMIN,
	
	/**
	 * Acesso Somente para consulta nas páginas WEB
	 * 
	 * CFOP, NCM, ESTADO, OPERACAO, TRIBUTACAO ESTADUAL - ICMS e TRIBUTACAO FEDERAL (PIS/COFINS)
	 * 
	 * Exceto: Do SPED, isso somente o ESCRITURADOR consegue
	 */
	WEB_ACESS,
	
	
	// Falta definir melhor as roles abaixo, mas do que tenho em mente no momento
	
	/**
	 * Basicamente é uma Junção de (WEB_ACESS e API_ACESS), porém consegue fazer alterações em tributações, operações etc. Nas telas
	 * WEB, e consegue visualizar/baixar o arquivo do SPED (porém não consegue gerar)
	 */
	CONTADOR,
	
	
	/**
	 * Difrerentemente da role anterior, esse não consegue alterar coisas referentes a contabilidade, porém 
	 * consegue visualizar/baixar o arquivo do SPED
	 * 
	 * 
	 * @See --> OBS <--
	 * ACHO que essa não tem nenhuma necessidade, a não ser que alguém além de precisar fazer consultas WEB,
	 * precise também de visualizar/baixar o arquivo do SPED
	 */
//	FATURAMENTO,
	
	/**
	 * Consegue gerar o arquivo do SPED FISCAL ICMS IPI
	 */
	ESCRITURADOR,
	
	
	/**
	 * Acesso somente para as URL ".../api/...."
	 * 
	 * Usuário que consomem essa API, através do ERP (ou seja, nem sabem que esse sistema existe)
	 */
	API_ACESS;
	
}


