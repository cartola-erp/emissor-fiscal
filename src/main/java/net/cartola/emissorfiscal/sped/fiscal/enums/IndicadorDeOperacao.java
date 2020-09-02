package net.cartola.emissorfiscal.sped.fiscal.enums;

/**
 * 21/08/2020
 * @author robson.costa
 */
public enum IndicadorDeOperacao {

	ENTRADA(0),
	SAIDA(1);

	private int indOper;
	 
	IndicadorDeOperacao(int indOper) {
        this.indOper = indOper;
    }
 
    public int getIndOper() {
        return indOper;
    }
}
