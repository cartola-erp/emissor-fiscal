package net.cartola.emissorfiscal.sped.fiscal.enums;

/**
 * 02/09/2020
 * @author robson.costa
 */
public enum IndicadorDePagamento {
	
	A_VISTA(0),
	A_PRAZO(1),
	OUTROS(2);

	private int indPgto;
	 
	IndicadorDePagamento(int indPgto) {
        this.indPgto = indPgto;
    }
 
    public int getIndPgto() {
        return indPgto;
    }
}
