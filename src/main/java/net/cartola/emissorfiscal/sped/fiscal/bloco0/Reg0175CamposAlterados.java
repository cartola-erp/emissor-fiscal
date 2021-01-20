package net.cartola.emissorfiscal.sped.fiscal.bloco0;

import net.cartola.emissorfiscal.sped.fiscal.enums.EnumCodificado;

/**
 * @date 20 de jan. de 2021
 * @author robson.costa
 */
public enum Reg0175CamposAlterados implements EnumCodificado {
	
	NOME("03"), 
	COD_PAIS("04"), 
	CNPJ("05"), 
	CPF("06"), 
	COD_MUN("08"), 
	SUFRAMA("09"), 
	END("10"), 
	NUM("11"), 
	COMPL("12"),
	BAIRRO("13");
	
	private String numCampo;
	
	Reg0175CamposAlterados(String numCampo) {
		this.numCampo = numCampo;
	}

	@Override
	public String getCodigo() {
		return numCampo;
	}

}
