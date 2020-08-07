package net.cartola.emissorfiscal.pessoa;

/**
 * 21 de fev de 2020
 * @author robson.costa
 */
public enum RegimeTributario {
	
	SIMPLES("Simples Nacional"),
	SIMPLES_EXCESSO("Lucro Presumido"),
	NORMAL("Lucro Real");
	
	private String descricao;
	 
	RegimeTributario(String descricao) {
        this.descricao = descricao;
    }
 
    public String getDescricao() {
        return descricao;
    }
}

