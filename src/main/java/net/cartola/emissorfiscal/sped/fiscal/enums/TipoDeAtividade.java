package net.cartola.emissorfiscal.sped.fiscal.enums;

/**
 * 19/08/2020
 * @author robson.costa
 */
public enum TipoDeAtividade implements EnumCodificado {
	
	INDUSTRIAL_OU_EQUIPARADO_A_INDUSTRIAL("0"),
	OUTROS("1");

	private String indAtiv;
	 
	TipoDeAtividade(String indAtiv) {
        this.indAtiv = indAtiv;
    }
	
	@Override
    public String getCodigo() {
        return indAtiv;
    }

}
