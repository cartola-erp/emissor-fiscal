package net.cartola.emissorfiscal.sped.fiscal.enums;

/**
 * 08/09/2020
 * @author robson.costa
 */
public enum ApuracaoIpi implements EnumCodificado{
	
    MENSAL('0'),
    DECENDIAL('1');

    private final char codigo;

    private ApuracaoIpi(char codigo) {
        this.codigo = codigo;
    }

    @Override
    public String getCodigo() {
        return String.valueOf(codigo);
    }
    
}
