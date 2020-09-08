package net.cartola.emissorfiscal.sped.fiscal.enums;

/**
 * 08/09/2020
 * @author robson.costa
 */
public enum TipoTransporte implements EnumCodificado{
    
	RODOVIARIO('0'),
    FERROVIARIO('1'),
    RODO_FERRROVIARIO('2'),
    AQUAVIARIO('3'),
    DUTOVIARIO('4'),
    AEREO('5'),
    OUTROS('9');

    private final char codigo;

    private TipoTransporte(char codigo) {
        this.codigo = codigo;
    }

    @Override
    public String getCodigo() {
        return String.valueOf(codigo);
    }
    
}