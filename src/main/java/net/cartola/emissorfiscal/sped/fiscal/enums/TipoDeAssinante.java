package net.cartola.emissorfiscal.sped.fiscal.enums;

/**
 * 11/09/2020
 * @author robson.costa
 */
public enum TipoDeAssinante implements EnumCodificado {
	
	COMERCIAL_INDUSTRIAL('1'),
	PODER_PUBLICO('2'),
	RESIDENCIAL_PESSOA_FISICA('3'),
	PUBLICO('4'),
	SEMI_PUBLICO('5'),
	OUTROS('6');

	private char tpAssinante;
	
	private TipoDeAssinante(char tpAssinante) {
		this.tpAssinante = tpAssinante;
	}
	
	@Override
	public String getCodigo() {
		return String.valueOf(tpAssinante);
	}

}
