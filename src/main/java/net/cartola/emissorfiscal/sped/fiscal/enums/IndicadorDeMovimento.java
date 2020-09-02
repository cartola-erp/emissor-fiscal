package net.cartola.emissorfiscal.sped.fiscal.enums;

/**
 * 21/08/2020
 * @author robson.costa
 */
public enum IndicadorDeMovimento {

	BLOCO_COM_DADOS_INFORMADOS(0),
	BLOCO_SEM_DADOS_INFORMADOS(1);

	private int indMov;
	 
	IndicadorDeMovimento(int indMov) {
        this.indMov = indMov;
    }
 
    public int getIndMov() {
        return indMov;
    }
}
