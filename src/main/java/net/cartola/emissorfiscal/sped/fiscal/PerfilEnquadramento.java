package net.cartola.emissorfiscal.sped.fiscal;

/**
 * 19/08/2020
 * @author robson.costa
 */
public enum PerfilEnquadramento {
	
	A("A"),
	B("B"),
	C("C");
	
	private String descricao;
	 
	PerfilEnquadramento(String descricao) {
        this.descricao = descricao;
    }
 
    public String getDescricao() {
        return descricao;
    }
    
}
