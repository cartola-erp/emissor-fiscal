package net.cartola.emissorfiscal.sped.fiscal.enums;

/**
 * 30/12/2021
 * @author robson.costa
 * 
 * Enum com os valores possíveis que podem sair na TAG procEmi do XML
 */
//public enum ProcessoEmissaoNfe implements EnumCodificado {
public enum ProcessoEmissaoNfe {

	/**
	 * tag XML nfe
	 * <b> procEmi = </b> 0
	 */
	NFE_COM_APLICATIVO_DO_CONTRIBUINTE("0"),
	
	/**
	 * tag XML nfe
	 * <b> procEmi = </b> 1
	 */
	NFE_AVULSA_PELO_FISCO("1"),
	
	/**
	 * tag XML nfe
	 * <b> procEmi = </b> 2
	 */
	NFE_AVULSA_PELO_CONTRIBUINTE_COM_CERTIFICADO_PELO_SITE_DO_FISCO("2"),

	
	/**
	 * tag XML nfe
	 * <b> procEmi = </b> 3
	 */
	NFE_PELO_CONTRIBUINTE_COM_APLICATIVO_FORNECIDO_PELO_FISCO("3");
	
	
	private final String codigo;
	
	private ProcessoEmissaoNfe(String codigo) {
		this.codigo = codigo;
	}
	
//	@Override
	public String getCodigo() {
		return codigo;
	}
	
}
