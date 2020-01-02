package net.cartola.emissorfiscal.usuario;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *	26 de dez de 2019
 *	@author robson.costa
 */
@Entity
@Table(name = "usua_perf")
public class UsuarioPerfil implements Serializable {

	private static final long serialVersionUID = -3489621368595642366L;
	
	private Long id;
	private Usuario usuario;
	private Perfil perfil;
	 
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@ManyToOne
	@JoinColumn(name = "usua_id", unique = false, foreignKey = @ForeignKey(name = "fnk_usua"))
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Enumerated(EnumType.STRING)
	public Perfil getPerfil() {
		return perfil;
	}
	
	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}
	
}


