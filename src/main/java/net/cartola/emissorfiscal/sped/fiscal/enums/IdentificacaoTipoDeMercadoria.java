package net.cartola.emissorfiscal.sped.fiscal.enums;


/**
 * 21/08/2020
 * @author robson.costa
 */
public enum IdentificacaoTipoDeMercadoria implements EnumCodificado {
	
	BEM("1"),
	COMPONENTE("2");

	private String identMerc;
	 
	IdentificacaoTipoDeMercadoria(String indMov) {
        this.identMerc = indMov;
    }
	
	@Override
    public String getCodigo() {
        return identMerc;
    }
}
