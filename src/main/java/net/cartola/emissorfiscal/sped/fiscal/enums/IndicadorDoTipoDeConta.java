package net.cartola.emissorfiscal.sped.fiscal.enums;

/**
 * 31/08/2020
 * @author robson.costa
 */
public enum IndicadorDoTipoDeConta implements EnumCodificado {
	
	SINTETICA_GRUPO_DE_CONTA("S"),
	ANALITICA_CONTA("A");

	private String indicadorTipoDeConta;
	 
	IndicadorDoTipoDeConta(String indicadorTipoDeConta) {
        this.indicadorTipoDeConta = indicadorTipoDeConta;
    }
	
	@Override
    public String getCodigo() {
        return indicadorTipoDeConta;
    }
    
}
