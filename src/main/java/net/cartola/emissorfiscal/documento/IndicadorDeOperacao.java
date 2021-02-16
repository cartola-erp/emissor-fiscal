package net.cartola.emissorfiscal.documento;

import net.cartola.emissorfiscal.sped.fiscal.enums.EnumCodificado;

/**
 * 21/08/2020
 * @author robson.costa
 */
public enum IndicadorDeOperacao implements EnumCodificado {

	ENTRADA("0"),
	SAIDA("1");

	private String indOper;
	 
	IndicadorDeOperacao(String indOper) {
        this.indOper = indOper;
    }
	
	@Override
    public String getCodigo() {
        return indOper;
    }
	
}
