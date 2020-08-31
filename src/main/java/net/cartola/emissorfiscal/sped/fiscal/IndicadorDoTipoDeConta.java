package net.cartola.emissorfiscal.sped.fiscal;

/**
 * 31/08/2020
 * @author robson.costa
 */
public enum IndicadorDoTipoDeConta {
	
	SINTETICA_GRUPO_DE_CONTA("S"),
	ANALITICA_CONTA("A");

	private String indicadorTipoDeConta;
	 
	IndicadorDoTipoDeConta(String indicadorTipoDeConta) {
        this.indicadorTipoDeConta = indicadorTipoDeConta;
    }
 
    public String getCodNaturezaDaConta() {
        return indicadorTipoDeConta;
    }
    
}
