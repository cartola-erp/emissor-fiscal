package net.cartola.emissorfiscal.compra;

import java.io.Serializable;
import java.util.Date;


/**
 * 25/09/2020
 * @author robson.costa
 */
/** É a mesma classe do ERP (ao menos do WS, pois não mudei nada) **/
public class CompraReferencia implements Serializable {
	
	private static final long serialVersionUID = -3187518314077465666L;
	
	private int loja;
	private int compra;
	private int item;
	private NfeReferenciaTipo tipo;
	private Long cnpjEmitente;
	private Integer ufEmitente;
	private Integer numeroDocumentoCoo;
	private String serieModelo;
	private String chave;
	private String modelo;
	private Integer lojaVinculada;
	private Integer compraVinculada;
	private Date cadastro;
	private Date alterado;

	public int getLoja() {
		return this.loja;
	}

	public void setLoja (int loja) {
		this.loja = loja;
	}

	public int getCompra() {
		return this.compra;
	}

	public void setCompra (int compra) {
		this.compra = compra;
	}

	public int getItem() {
		return this.item;
	}

	public void setItem (int item) {
		this.item = item;
	}

	public NfeReferenciaTipo getTipo() {
		return this.tipo;
	}

	public void setTipo (NfeReferenciaTipo tipo) {
		this.tipo = tipo;
	}

	public Long getCnpjEmitente() {
		return this.cnpjEmitente;
	}

	public void setCnpjEmitente (Long cnpjEmitente) {
		this.cnpjEmitente = cnpjEmitente;
	}

	public Integer getUfEmitente() {
		return this.ufEmitente;
	}

	public void setUfEmitente (Integer ufEmitente) {
		this.ufEmitente = ufEmitente;
	}

	public Integer getNumeroDocumentoCoo() {
		return this.numeroDocumentoCoo;
	}

	public void setNumeroDocumentoCoo (Integer numeroDocumentoCoo) {
		this.numeroDocumentoCoo = numeroDocumentoCoo;
	}

	public String getSerieModelo() {
		return this.serieModelo;
	}

	public void setSerieModelo (String serieModelo) {
		this.serieModelo = serieModelo;
	}

	public String getChave() {
		return this.chave;
	}

	public void setChave (String chave) {
		this.chave = chave;
	}

	public String getModelo() {
		return this.modelo;
	}

	public void setModelo (String modelo) {
		this.modelo = modelo;
	}

	public Integer getLojaVinculada() {
		return this.lojaVinculada;
	}

	public void setLojaVinculada (Integer lojaVinculada) {
		this.lojaVinculada = lojaVinculada;
	}

	public Integer getCompraVinculada() {
		return this.compraVinculada;
	}

	public void setCompraVinculada (Integer compraVinculada) {
		this.compraVinculada = compraVinculada;
	}

	public Date getCadastro() {
		return this.cadastro;
	}

	public void setCadastro (Date cadastro) {
		this.cadastro = cadastro;
	}

	public Date getAlterado() {
		return this.alterado;
	}

	public void setAlterado (Date alterado) {
		this.alterado = alterado;
	}
}
