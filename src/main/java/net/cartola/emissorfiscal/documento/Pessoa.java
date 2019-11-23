package net.cartola.emissorfiscal.documento;

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
	private String regimeApuracao;
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

	public String getRegimeApuracao() {
		return regimeApuracao;
	}

	public void setRegimeApuracao(String regimeApuracao) {
		this.regimeApuracao = regimeApuracao;
	}
	
	@Enumerated(EnumType.STRING)
	public PessoaTipo getPessoaTipo() {
		return pessoaTipo;
	}

	public void setPessoaTipo(PessoaTipo pessoaTipo) {
		this.pessoaTipo = pessoaTipo;
	}
	
}


