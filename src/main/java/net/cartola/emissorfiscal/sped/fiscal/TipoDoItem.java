package net.cartola.emissorfiscal.sped.fiscal;

/**
 * 21/08/2020
 * @author robson.costa
 */
public enum TipoDoItem {

	MERCADORIA_PARA_REVENDA("00"),
	MATÉRIA_PRIMA("01"),
	EMBALAGEM("02"),
	PRODUTO_EM_PROCESSO("03"),
	PRODUTO_ACABADO("04"),
	SUBPRODUTO("05"),
	PRODUTO_INTERMEDIÁRIO("06"),
	MATERIAL_DE_USO_E_CONSUMO("07"),
	ATIVO_IMOBILIZADO("08"),
	SERVIÇOS("09"),
	OUTROS_INSUMOS("10"),
	OUTRAS("99");
	
	private String tipoDoItem;
	 
	TipoDoItem(String tipoDoItem) {
        this.tipoDoItem = tipoDoItem;
    }
 
    public String getTipoDoItem() {
        return tipoDoItem;
    }
}
