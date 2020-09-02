package net.cartola.emissorfiscal.sped.fiscal.enums;

/**
 * 21/08/2020
 * @author robson.costa
 */
public enum IndicadorDoEmitente {

	EMISSAO_PROPRIA(0),
	TERCEIROS(1);

	private int indEmit;
	 
	IndicadorDoEmitente(int indEmit) {
        this.indEmit = indEmit;
    }
 
    public int getIndEmit() {
        return indEmit;
    }
}
