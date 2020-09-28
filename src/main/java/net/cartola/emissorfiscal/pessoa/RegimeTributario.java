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
    
    /**
     * retorna o código do regime tributario para a NFe
     * 1 – Simples Nacional;
     * 2 – Simples Nacional – excesso de sublimite de receita bruta;
     * 3 – Regime Normal. (v2.0).
     * @return
     */
	
    public int getCrt() {
        return this.ordinal()+1;
    }
}

