package net.cartola.emissorfiscal.sped.fiscal.enums;

/**
 * 19/08/2020
 * @author robson.costa
 */
public enum VersaoDoLayout {

	/**
	 *  Leiaute válido de 01/01/2020 a 31/12/2020 - publicado pelo Ato Cotepe nº 65/2019.
	 */
	V_014("014");
	
	
	private String descricao;
	 
	VersaoDoLayout(String descricao) {
        this.descricao = descricao;
    }
 
    public String getDescricao() {
        return descricao;
    }
    
    
}
