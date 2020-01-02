package net.cartola.emissorfiscal.security.dto;

/**
 *	26 de dez de 2019
 *	@author robson.costa
 */
public class UsuarioDTO {
	
	private Long id;
	private String login;
	private String nome;
	private String email;
	
	public String getLogin() {
		return login;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	
}


