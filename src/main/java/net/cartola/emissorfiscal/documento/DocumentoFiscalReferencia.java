package net.cartola.emissorfiscal.documento;

import java.util.Date;

/**
 * 25/09/2020
 * @author robson.costa
 */
public class DocumentoFiscalReferencia {
	
	private Long id;
//	private int loja;
	private int nfe;
	private String chave;
	private String modeloEcf;
	private Integer ecf;
	private Integer coo;
//	private Date cadastro;
//	private Date alterado;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

//	public int getLoja() {
//		return this.loja;
//	}
//
//	public void setLoja (int loja) {
//		this.loja = loja;
//	}

	public int getNfe() {
		return this.nfe;
	}

	public void setNfe (int nfe) {
		this.nfe = nfe;
	}

	public String getChave() {
		return this.chave;
	}

	public void setChave (String chave) {
		this.chave = chave;
	}

	public String getModeloEcf() {
		return this.modeloEcf;
	}

	public void setModeloEcf (String modeloEcf) {
		this.modeloEcf = modeloEcf;
	}

	public Integer getEcf() {
		return this.ecf;
	}

	public void setEcf (Integer ecf) {
		this.ecf = ecf;
	}

	public Integer getCoo() {
		return this.coo;
	}

	public void setCoo (Integer coo) {
		this.coo = coo;
	}

//	public Date getCadastro() {
//		return this.cadastro;
//	}
//
//	public void setCadastro (Date cadastro) {
//		this.cadastro = cadastro;
//	}
//
//	public Date getAlterado() {
//		return this.alterado;
//	}
//
//	public void setAlterado (Date alterado) {
//		this.alterado = alterado;
//	}
}
