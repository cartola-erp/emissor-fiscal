package net.cartola.emissorfiscal.documento;

public enum Finalidade {
	
	COMERCIALIZACAO("Revenda"),
	BRINDE("Brinde"), 
	DOACAO("Doação"), 
	PATRIMONIO("Patrimônio"), 
	CONSUMO("Consumo");  
	
	private String descricao;
	
	Finalidade(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
}
