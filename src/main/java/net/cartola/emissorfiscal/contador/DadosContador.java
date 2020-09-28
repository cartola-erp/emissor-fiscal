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
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getCpf() {
		return cpf;
	}

	public void setCpf(Long cpf) {
		this.cpf = cpf;
	}

	public Long getCrc() {
		return crc;
	}

	public void setCrc(Long crc) {
		this.crc = crc;
	}

	public Long getCnpj() {
		return cnpj;
	}

	public void setCnpj(Long cnpj) {
		this.cnpj = cnpj;
	}

	public Long getCep() {
		return cep;
	}

	public void setCep(Long cep) {
		this.cep = cep;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public int getNumImovel() {
		return numImovel;
	}

	public void setNumImovel(int numImovel) {
		this.numImovel = numImovel;
	}

	public String getDadosComplementares() {
		return dadosComplementares;
	}

	public void setDadosComplementares(String dadosComplementares) {
		this.dadosComplementares = dadosComplementares;
	}

	public String getBairroDoImovel() {
		return bairroDoImovel;
	}

	public void setBairroDoImovel(String bairroDoImovel) {
		this.bairroDoImovel = bairroDoImovel;
	}

	public String getNumTelefone() {
		return numTelefone;
	}

	public void setNumTelefone(String numTelefone) {
		this.numTelefone = numTelefone;
	}

	public String getNumFax() {
		return numFax;
	}

	public void setNumFax(String numFax) {
		this.numFax = numFax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getCodMunicipio() {
		return codMunicipio;
	}

	public void setCodMunicipio(Long codMunicipio) {
		this.codMunicipio = codMunicipio;
	}
	
	
}
