package net.cartola.emissorfiscal.sped.fiscal.enums;

/**
 * 08/09/2020
 * @author robson.costa
 */
public enum TipoTituloCredito implements EnumCodificado {
	 
    DUPLICATA("00"),
    CHEQUE("01"),
    PROMISSORIA("02"),
    RECIBO("03"),
    OUTROS("99");
    
	private final String codigo;

    private TipoTituloCredito(String codigo) {
        this.codigo = codigo;
    }

    @Override
    public String getCodigo() {
        return codigo;
    }
}