package net.cartola.emissorfiscal.pessoa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import net.cartola.emissorfiscal.estado.EstadoSigla;

/**
 *	22 de nov de 2019
 *	@author robson.costa
 */

@Entity
@Table(name = "pessoa")
public class Pessoa {
	
	private Long id;
	private Long cnpj;
	private EstadoSigla uf = EstadoSigla.SP;
	private RegimeTributario regimeTributario;
	private PessoaTipo pessoaTipo = PessoaTipo.FISICA;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCnpj() {
		return cnpj;
	}

	public void setCnpj(Long cnpj) {
		this.cnpj = cnpj;
	}

	//	@NotNull(message = ESTADO_EMITENTE_OBRIGATORIO)
	@Enumerated(EnumType.STRING)
	public EstadoSigla getUf() {
		return uf;
	}

	public void setUf(EstadoSigla uf) {
		this.uf = uf;
	}

	@Enumerated(EnumType.STRING)
	@Column(name="regime_tributario", columnDefinition="enum('SIMPLES', 'PRESUMIDO', 'REAL') ")
	public RegimeTributario getRegimeTributario() {
		return regimeTributario;
	}

	public void setRegimeTributario(RegimeTributario regimeTributario) {
		this.regimeTributario = regimeTributario;
	}

	@Enumerated(EnumType.STRING)
	@Column(name="pessoa_tipo", columnDefinition="enum('FISICA', 'JURIDICA') default 'FISICA' ")
	public PessoaTipo getPessoaTipo() {
		return pessoaTipo;
	}

	public void setPessoaTipo(PessoaTipo pessoaTipo) {
		this.pessoaTipo = pessoaTipo;
	}
	
}


