package net.cartola.emissorfiscal.sped.fiscal.enums;

/**
 * 08/09/2020
 * 
 * @author robson.costa
 */
public enum DocumentoDeImportacao implements EnumCodificado  {
	
	DECLARACAO_IMPORTACAO('0'), 
	DECLARACAO_SIMPLIFICADA_IMPORTACAO('1');

	private final char codigo;

	private DocumentoDeImportacao(char codigo) {
		this.codigo = codigo;
	}

	@Override
	public String getCodigo() {
		return String.valueOf(codigo);
	}
}
