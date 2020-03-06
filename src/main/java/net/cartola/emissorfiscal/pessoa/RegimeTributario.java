package net.cartola.emissorfiscal.pessoa;

/**
 * 21 de fev de 2020
 * @author robson.costa
 */
public enum RegimeTributario {
	
	SIMPLES("Simples"),
	SIMPLES_EXCESSO("Presumido"),
	NORMAL("Real");
	
//    , , ;

	
	private String descricao;
	 
	RegimeTributario(String descricao) {
        this.descricao = descricao;
    }
 
    public String getDescricao() {
        return descricao;
    }
}

