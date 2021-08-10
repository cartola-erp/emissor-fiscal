package net.cartola.emissorfiscal.sped.fiscal.enums;

/**
 * 02/09/2020
 * @author robson.costa
 * 
 *  Código da Situação do Documento (tabela 4.1.2)
 */
public enum SituacaoDoDocumento implements EnumCodificado {
	
	/**
	 * 00
	 */
	DOCUMENTO_REGULAR("00"),	
	
	/**
	 * 01
	 */
	ESCRITURAÇÃO_EXTEMPORÂNEA_DE_DOCUMENTO_REGULAR("01"),
	
	/**
	 * 02
	 */
	DOCUMENTO_CANCELADO("02"),
	
	/**
	 * 03
	 */
	ESCRITURAÇÃO_EXTEMPORÂNEA_DE_DOCUMENTO_CANCELADO("03"),
	
	/**
	 * 04
	 */
	NF_E_NFC_E_OU_CT_E_DENEGADO("04"),
	
	/**
	 * 05
	 */
	NF_E_NFC_E_OU_CT_E_NUMERACAO_INUTILIZADA("05"),
	
	/**
	 * 06
	 */
	DOCUMENTO_FISCAL_COMPLEMENTAR("06"),
	
	/**
	 * 07
	 */
	ESCRITURACAO_EXTEMPORANEA_DE_DOCUMENTO_COMPLEMENTAR("07"),
	
	/**
	 * 08
	 */
	DOCUMENTO_FISCAL_EMITIDO_COM_BASE_EM_REGIME_ESPECIAL_OU_NORMA_ESPECÍFICA("08");
	
	private final String codigo;
	    
	private SituacaoDoDocumento(String codigo) {
		this.codigo = codigo;
	}
	
	@Override
	public String getCodigo() {
		return codigo;
	}
	
}
