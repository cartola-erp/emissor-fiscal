package net.cartola.emissorfiscal.sped.fiscal;

/**
 * 22/09/2020
 * @author robson.costa
 */
public enum Status {
	
	GERANDO("Gerando o Arquivo do SPED"), 
	ERRO("Erro ao tentar gerar o arquivo"),
	SUCESSO("Arquivo Gerado com sucesso"),
	ASSINADO("Arquivo assinado com sucesso");	// Irei assinar o arquivo? e se sim, será aqui no servidor ou no client?
	
	private String descricao;
	
	private Status(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
	
}
