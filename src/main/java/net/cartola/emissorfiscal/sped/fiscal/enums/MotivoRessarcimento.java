package net.cartola.emissorfiscal.sped.fiscal.enums;

/**
 * 09/09/2020
 * @author robson.costa
 */
public enum MotivoRessarcimento implements EnumCodificado {
	
	VENDA_PARA_OUTRA_UF("1"),
	SAIDA_AMPARADA_POR_ISENCAO_OU_NAO_INCIDENCIA("2"),
	PERDA_OU_DETERIORACAO("3"),
	FURTO_OU_ROUBO("4"),
	EXPORTACAO("5"),
	VENDA_INTERNA_PARA_SIMPLES_NACIONAL("6"),
	OUTROS("9");

	private String codMotRessarcimento;
	 
	MotivoRessarcimento(String codMotRessarcimento) {
        this.codMotRessarcimento = codMotRessarcimento;
    }
	
	@Override
    public String getCodigo() {
        return codMotRessarcimento;
    }

}
