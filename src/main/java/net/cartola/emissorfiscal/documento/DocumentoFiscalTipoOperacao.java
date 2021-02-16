package net.cartola.emissorfiscal.documento;

public enum DocumentoFiscalTipoOperacao {

	ENTRADA("Entrada"),
	SAIDA("Saida");

	private String descricao;
	
	private DocumentoFiscalTipoOperacao(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
}
