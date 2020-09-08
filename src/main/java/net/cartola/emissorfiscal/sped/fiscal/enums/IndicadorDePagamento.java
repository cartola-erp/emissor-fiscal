package net.cartola.emissorfiscal.sped.fiscal.enums;

/**
 * 02/09/2020
 * @author robson.costa
 */
public enum IndicadorDePagamento implements EnumCodificado {
	
	A_VISTA("0"),
	A_PRAZO("1"),
	OUTROS("2");

	private String indPgto;
	 
	IndicadorDePagamento(String indPgto) {
        this.indPgto = indPgto;
    }
	
	@Override
    public String getCodigo() {
        return indPgto;
    }
}
