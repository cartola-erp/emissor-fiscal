package net.cartola.emissorfiscal.documento;

import net.cartola.emissorfiscal.sped.fiscal.enums.EnumCodificado;


/**
 * Na NF-e existe o campo finNFe - Finalidade de Emissão da NF-e. Esse campo
 * pode ser preenchido com os seguintes valores:
 * 
 * 
 * 1 = NF-e normal. 
 * 2 = NF-e complementar. 
 * 3 = NF-e de ajuste. 
 * 4 = Devolução de mercadoria.
 * 
 * @author robson.costa
 * @data 8 de abr. de 2021
 */
public enum FinalidadeEmissao implements EnumCodificado {
	
	NORMAL("1"),
	COMPLEMENTAR("2"),
	AJUSTE("3"),
	DEVOLUCAO_MERCADORIA("4");
	
	private String codigo;
	
	FinalidadeEmissao(String codigo) {
		this.codigo = codigo;
	}

	@Override
	public String getCodigo() {
		return this.codigo;
	}

}
