package net.cartola.emissorfiscal.sped.fiscal.enums;

/**
 * 14/09/2020
 * @author robson.costa
 */
public enum MotivoDoInventario implements EnumCodificado {

    FINAL_PERIODO("01"),
    MUDANCA_TRIBUTACAO_ICMS("02"),
    BAIXA_CADASTRAL_PARALIZACAO_E_OUTRAS("03"),
    ALTERACAO_REGIME_PAG("04"),
    DETERMINACAO_FISCO("05"),
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
