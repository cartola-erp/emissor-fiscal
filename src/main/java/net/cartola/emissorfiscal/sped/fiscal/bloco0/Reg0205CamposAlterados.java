package net.cartola.emissorfiscal.sped.fiscal.bloco0;

/**
 * @date 26 de jan. de 2021
 * @author robson.costa
 */
public enum Reg0205CamposAlterados {

	
	DESCRICAO_ITEM("02"),
	CODIGO("05");
	
	private String numCampo;
	
	Reg0205CamposAlterados(String numCampo) {
		this.numCampo = numCampo;
	}

//	@Override
	public String getCodigo() {
		return numCampo;
	}
	
}
