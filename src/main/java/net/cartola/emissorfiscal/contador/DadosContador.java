package net.cartola.emissorfiscal.contador;

/**
 * 25/09/2020
 * @author robson.costa
 */
public class DadosContador {

	/**
	 * Na tabela cadastros, do DB -> Autogeral, tem alguns funcionarios cadastrados inclusive contadores, então na hora de preencher
	 * posso pegar os dados dessa tabela conforme o contador selecionar, e caso NÃO, tenha cadastrado algum que eu precise, habilito 
	 * para preencher/editar (isso lá no ERP, no momento que selecionar as lojas que serão buscadas as movimentações mensais) 
	 * 
	 */
	
	private String nome;
	private Long cpf;
	private Long crc;
	private Long cnpj;
	private Long cep;
	private String endereco;
	private int numImovel;
	private String dadosComplementares;
	private String bairroDoImovel;
	private String numTelefone;
	private String numFax;
	private String email;
	private Long codMunicipio;
	
}
