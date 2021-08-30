package net.cartola.emissorfiscal.documento;

public enum Finalidade {
	
	COMERCIALIZACAO("Revenda"),
	BRINDE("Brinde"), 
	DOACAO("Doação"), 
	PATRIMONIO("Patrimônio"), // Patrimonio/Ativo é quando o VL UNITARIO do item é maior que R$ 1400,00
	CONSUMO("Consumo");  
	
	private String descricao;
	
	Finalidade(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
}
