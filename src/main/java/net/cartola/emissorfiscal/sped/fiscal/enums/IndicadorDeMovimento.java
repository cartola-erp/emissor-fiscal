package net.cartola.emissorfiscal.sped.fiscal.enums;

/**
 * 21/08/2020
 * @author robson.costa
 */
public enum IndicadorDeMovimento implements EnumCodificado {

	BLOCO_COM_DADOS_INFORMADOS("0"),
	BLOCO_SEM_DADOS_INFORMADOS("1");

	private String indMov;
	 
	IndicadorDeMovimento(String indMov) {
        this.indMov = indMov;
    }
	
	@Override
    public String getCodigo() {
        return indMov;
    }
}
