package net.cartola.emissorfiscal.sped.fiscal.enums;

/**
 * 19/08/2020
 * @author robson.costa
 */
public enum PerfilEnquadramento implements EnumCodificado {
	
	A("A"),
	B("B"),
	C("C");
	
	private String descricao;
	 
	PerfilEnquadramento(String descricao) {
        this.descricao = descricao;
    }
	
	@Override
    public String getCodigo() {
        return descricao;
    }
    
}
