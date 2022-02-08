package net.cartola.emissorfiscal.sped.fiscal.enums;

/**
 * 14/09/2020
 * @author robson.costa
 */
public enum PropriedadeEPosseItem implements EnumCodificado {

	/**
	 * 0
	 */
    ITEM_PROP_INFORMANTE_EM_SEU_PODER("0"),
    
    /**
     * 1
     */
    ITEM_PROP_INFORMANTE_EM_POSSE_DE_TERCEIROS("1"),
    
    /**
     * 2
     */
    ITEM_PROP_TERCEIROS_EM_POSSE_INFORMANTE("2");
	
    private final String indProp;

    private PropriedadeEPosseItem(String indProp) {
        this.indProp = indProp;
    }

    @Override
    public String getCodigo() {
        return indProp;
    }
}
