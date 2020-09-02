package net.cartola.emissorfiscal.sped.fiscal.enums;

/**
 * 21/08/2020
 * @author robson.costa
 */
public enum IdentificacaoTipoDeMercadoria {
	
	BEM(1),
	COMPONENTE(2);

	private int identMerc;
	 
	IdentificacaoTipoDeMercadoria(int indMov) {
        this.identMerc = indMov;
    }
 
    public int getIndMov() {
        return identMerc;
    }
}
