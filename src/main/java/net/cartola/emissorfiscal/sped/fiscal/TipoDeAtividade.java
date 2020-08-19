package net.cartola.emissorfiscal.sped.fiscal;

/**
 * 19/08/2020
 * @author robson.costa
 */
public enum TipoDeAtividade {
	
	INDUSTRIAL_OU_EQUIPARADO_A_INDUSTRIAL(0),
	OUTROS(1);

	private int indAtiv;
	 
	TipoDeAtividade(int indAtiv) {
        this.indAtiv = indAtiv;
    }
 
    public int getIndAtiv() {
        return indAtiv;
    }

}
