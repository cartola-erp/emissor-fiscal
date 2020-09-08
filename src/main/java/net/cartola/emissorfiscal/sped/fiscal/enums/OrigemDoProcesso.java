package net.cartola.emissorfiscal.sped.fiscal.enums;

/**
 * 08/09/2020
 * @author robson.costa
 */
public enum OrigemDoProcesso implements EnumCodificado {
  
	SEFAZ("0"),
    JUSTICA_FEDERAL("1"),
    JUSTICA_ESTADUAL("2"),
    SECEX_RFB("3"),
    OUTROS("9");

    private final String codigo;

    private OrigemDoProcesso(String codigo) {
        this.codigo = codigo;
    }

    @Override
    public String getCodigo() {
        return codigo;
    }
}
