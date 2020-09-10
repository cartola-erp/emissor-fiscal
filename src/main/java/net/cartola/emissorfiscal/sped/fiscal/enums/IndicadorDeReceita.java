package net.cartola.emissorfiscal.sped.fiscal.enums;

/**
 * 10/09/2020
 * @author robson.costa
 */
public enum IndicadorDeReceita implements EnumCodificado {
	
	 RECEITA_PROPRIA("0"),
	 RECEITA_DE_TERCEIROS("1");

	private String indReceita;
	 
	IndicadorDeReceita(String indReceita) {
        this.indReceita = indReceita;
    }
	
	@Override
    public String getCodigo() {
        return indReceita;
    }
	
}
