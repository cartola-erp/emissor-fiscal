package net.cartola.emissorfiscal.contador;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 25/09/2020
 * @author robson.costa
 */
@Entity
@Table(name = "contador")
public class Contador implements Serializable {

	private static final long serialVersionUID = -829857136477567741L;

	/**
	 * Na tabela cadastros, do DB -> Autogeral, tem alguns funcionarios cadastrados inclusive contadores, então na hora de preencher
	 * posso pegar os dados dessa tabela conforme o contador selecionar, e caso NÃO, tenha cadastrado algum que eu precise, habilito 
	 * para preencher/editar (isso lá no ERP, no momento que selecionar as lojas que serão buscadas as movimentações mensais) 
	 * 
	 */

	private Long id;
	private String nome;
	private String cpf;
	private String crc;
	private Long cnpj;
	private Long cep;
	private String endereco;
	private int numImovel;
	private String complementoEndereco;
	private String bairroDoImovel;
	private String telefone;
	private String numFax;
	private String email;
	private Long codMunicipio;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getCrc() {
		return crc;
	}

	public void setCrc(String crc) {
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

	@Column(name = "compl_end")
	public String getComplementoEndereco() {
		return complementoEndereco;
	}

	public void setComplementoEndereco(String complementoEndereco) {
		this.complementoEndereco = complementoEndereco;
	}

	@Column(name = "bairro_imov")
	public String getBairroDoImovel() {
		return bairroDoImovel;
	}

	public void setBairroDoImovel(String bairroDoImovel) {
		this.bairroDoImovel = bairroDoImovel;
	}

	@Column(name = "tele")
	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
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
