package net.cartola.emissorfiscal.sped.fiscal.enums;

/**
 * 08/09/2020
 * @author robson.costa
 */
public enum DocumentoDeArrecadacao implements EnumCodificado {
    
	ESTADUAL("0"),
    GNRE("1");

    private final String codigo;

    private DocumentoDeArrecadacao(String codigo) {
        this.codigo = codigo;
    }

    @Override
    public String getCodigo() {
        return codigo;
    }
    
}
