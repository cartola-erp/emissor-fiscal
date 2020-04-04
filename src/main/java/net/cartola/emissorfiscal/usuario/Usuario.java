package net.cartola.emissorfiscal.usuario;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;


/**
 *	26 de dez de 2019
 *	@author robson.costa
 */
@Entity
@Table(name = "usua")
public class Usuario implements Serializable {
	
	private static final long serialVersionUID = -2676374644535061459L;

	private Long id;
	private String login;
	private String nome;
	private String email;
	private String senha;
	private List<UsuarioPerfil> perfis = new ArrayList<>();
//	private List<Perfil> perfis = null;
  
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	@Column(unique = true, nullable=false)
	@Length(min=3, max=255)
	public String getLogin() {
		return login;
	}

	public String getNome() {
		return nome;
	}
	
	@Email(message="Email inválido!")
	@Column(unique = true) 
	public String getEmail() {
		return email;
	}
	
	@NotNull
	@NotBlank
	@Length(min=3, max=255)
	public String getSenha() {
		return senha;
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

	public void setSenha(String senha) {
		this.senha = senha;
	}

//	@ManyToMany
//	@Enumerated(EnumType.STRING)
	@OneToMany(mappedBy = "usuario", fetch=FetchType.EAGER)
	public List<UsuarioPerfil> getPerfis() {
		return perfis;
	}

	public void setPerfis(List<UsuarioPerfil> perfis) {
		this.perfis = perfis;
	}
	
}


