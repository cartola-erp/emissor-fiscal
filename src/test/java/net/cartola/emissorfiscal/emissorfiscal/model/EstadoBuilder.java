package net.cartola.emissorfiscal.emissorfiscal.model;

import net.cartola.emissorfiscal.estado.Estado;

/**
 * 5 de nov de 2019
 * 
 * @author gregory.feijon
 */
public class EstadoBuilder {

	private Estado estado = new Estado();

	public void novo() {
		this.estado = new Estado();
	}

	public Estado build() {
		return estado;
	}

	public EstadoBuilder withId(Long id) {
		estado.setId(id);
		return this;
	}

	public EstadoBuilder withSigla(String sigla) {
		estado.setSigla(sigla);
		return this;
	}

	public EstadoBuilder withNome(String nome) {
		estado.setNome(nome);
		return this;
	}
}
