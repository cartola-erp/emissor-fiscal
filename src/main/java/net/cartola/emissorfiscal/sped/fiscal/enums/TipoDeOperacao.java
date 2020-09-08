package net.cartola.emissorfiscal.sped.fiscal.enums;

/**
 * 08/09/2020
 * 
 * @author robson.costa
 */
public enum TipoDeOperacao implements EnumCodificado {

	COMBUSTIVEIS_E_LUBRIFICANTES("0"),
	LEASING_DE_VEICULOS_OU_FATURAMENTO_DIRETO("1");

	private final String codigo;

	private TipoDeOperacao(String codigo) {
		this.codigo = codigo;
	}

	@Override
	public String getCodigo() {
		return codigo;
	}
}
