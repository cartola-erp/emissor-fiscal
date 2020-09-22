package net.cartola.emissorfiscal.sped.fiscal.enums;

/**
 * 19/08/2020
 * @author robson.costa
 */
public enum VersaoDoLayout implements EnumCodificado {

	/**
	 *  Leiaute válido de 01/01/2020 a 31/12/2020 - publicado pelo Ato Cotepe nº 65/2019.
	 */
	V_014("014");
	
	
	private String descricao;
	 
	VersaoDoLayout(String descricao) {
        this.descricao = descricao;
    }
	
	@Override
    public String getCodigo() {
        return descricao;
    }
    
    public static VersaoDoLayout getUltimaVersao() {
    	return VersaoDoLayout.values()[VersaoDoLayout.values().length - 1];
    }
    
}
