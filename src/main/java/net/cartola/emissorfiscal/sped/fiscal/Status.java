package net.cartola.emissorfiscal.sped.fiscal;

/**
 * 22/09/2020
 * @author robson.costa
 */
public enum Status {
	
	/**
	 * Pode até ter gerado o arquivo em alguma parte do código, MAS caso não tenha gravado ainda e não tenha nenhum erro, o STATUS deve permanecer esse 
	 */
	GERANDO("Gerando o Arquivo do SPED"), 			
	
	/**
	 * Esse status é quando conseguiu gerar o arquivo e salvar no diretório temporário
	 */
	SALVOU_DIR_TEMP("Conseguiu gerar o arquivo, PORÉM o mesmo não foi salvo em disco definitivamente"),
	

//	/**
//	 * Usado para quando consegue ler com sucesso, o arquivo que foi gerado na pasta temporária
//	 * 
//	 */
//	LEU_DIR_TEMP("Conseguiu ler o arquivo que foi gerado no diretório temporário" ),
	
	/**
	 * Quando houver qualquer erro  ao tentar criar, gerar, ou até mesmo gravar o arquivo
	 */
	ERRO("Erro ao tentar gerar o arquivo"),
	
	/**
	 *  Apenas quando consegue gravar em disco (No futuro no caso somente quando salva no "storage"/bucket)
	 */
	SUCESSO("Arquivo Gerado com sucesso"),			
	
	/**
	 * Irei assinar o arquivo? e se sim, será aqui no servidor ou no client?
	 */
	ASSINADO("Arquivo assinado com sucesso");
	

	private String descricao;
	
	private Status(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
	
}
