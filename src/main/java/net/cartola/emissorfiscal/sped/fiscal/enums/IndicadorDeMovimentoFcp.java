package net.cartola.emissorfiscal.sped.fiscal.enums;

/**
 * 21/08/2020
 * @author robson.costa
 */
public enum IndicadorDeMovimentoFcp implements EnumCodificado {

	SEM_OPERACOES("0"),
	COM_OPERACOES("1");

	private String indMovFcpDifal;
	 
	IndicadorDeMovimentoFcp(String indMovFcpDifal) {
        this.indMovFcpDifal = indMovFcpDifal;
    }
	
	@Override
    public String getCodigo() {
        return indMovFcpDifal;
    }
}
