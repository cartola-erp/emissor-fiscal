package net.cartola.emissorfiscal.sped.fiscal.enums;

/**
 * 21/08/2020
 * @author robson.costa
 */
public enum IndicadorDoEmitente implements EnumCodificado {

	EMISSAO_PROPRIA("0"),
	TERCEIROS("1");

	private String indEmit;
	 
	IndicadorDoEmitente(String indEmit) {
        this.indEmit = indEmit;
    }
	
	@Override
    public String getCodigo() {
        return indEmit;
    }
}
