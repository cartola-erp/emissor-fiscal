package net.cartola.emissorfiscal.ncm;

import java.io.Serializable;

/**
 * Ncm
 */
/**
 * 08/08/2017 16:52:40
 * 
 * @author murilo
 */
public class NcmDto implements Serializable {

	private static final long serialVersionUID = 7049871263623L;

	private Long id;
	private int numero;
	private int excecao;
//	private String descricao;
	
	public Long getId() {
		return id;
	}
	public int getNumero() {
		return numero;
	}
	public int getExcecao() {
		return excecao;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public void setExcecao(int excecao) {
		this.excecao = excecao;
	}

}
