package net.cartola.emissorfiscal.sped.fiscal.enums;

/**
 * 14/09/2020
 * @author robson.costa
 */
public enum MotivoDoInventario implements EnumCodificado {

	/**
	 * 01
	 */
    FINAL_PERIODO("01"),

    /**
     * 02
     */
    MUDANCA_TRIBUTACAO_ICMS("02"),
    
    /**
     * 03
     */
    BAIXA_CADASTRAL_PARALIZACAO_E_OUTRAS("03"),
    
    /**
     * 04
     */
    ALTERACAO_REGIME_PAG("04"),
    
    /**
     * 05
     */
    DETERMINACAO_FISCO("05"),
    
    /**
     * 06
     */
    CONTROLE_MERCADORIAS_COM_ST("06");
	
    private final String movInv;

    private MotivoDoInventario(String movInv) {
        this.movInv = movInv;
    }

    @Override
    public String getCodigo() {
        return movInv;
    }
    
}
