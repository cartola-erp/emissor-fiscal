package net.cartola.emissorfiscal.documento;

import java.math.BigDecimal;

import net.cartola.emissorfiscal.ncm.Ncm;
import net.cartola.emissorfiscal.produto.ProdutoUnidade;

/**
 * Essa classe contém os atributos em comum de DocumentoFiscalItem e DevolucaoItem;
 * 
 * @date 21 de set. de 2021
 * @author robson.costa
 */
public abstract class Item {
	
	protected int item;
	protected Long codigoX;
	protected String codigoSequencia;
	protected int produtoCodigoErp;
	protected String classeFiscal;
	protected int excecao;
	protected BigDecimal quantidade = BigDecimal.ZERO;
	protected BigDecimal valorUnitario = BigDecimal.ZERO;
	protected BigDecimal desconto = BigDecimal.ZERO;
    protected BigDecimal valorFrete = BigDecimal.ZERO;
    protected BigDecimal valorSeguro = BigDecimal.ZERO;
//    protected BigDecimal icmsBase = BigDecimal.ZERO;
    protected BigDecimal icmsReducaoBaseAliquota = BigDecimal.ZERO;
    protected BigDecimal icmsReducaoBaseStAliquota = BigDecimal.ZERO;
	protected BigDecimal icmsAliquota = BigDecimal.ZERO;
	protected BigDecimal icmsStAliquota = BigDecimal.ZERO;
	protected BigDecimal ipiAliquota = BigDecimal.ZERO;
	protected ProdutoUnidade unidade;

	private Long ncmId;					// APENAS nessa classe para criar o objeto do tipo NCM e não ficar indo no DB
	private Ncm ncm;

	
	
	protected abstract int getItem();
	protected abstract Long getCodigoX();
	protected abstract String getCodigoSequencia();
	
	
	protected abstract String getClasseFiscal();
	
	protected abstract void setClasseFiscal(String ncm);
	
	public final void setClasseFiscal(Ncm ncm) {
		this.ncmId = ncm.getId();
		this.classeFiscal = String.valueOf(ncm.getNumero());
		this.excecao = ncm.getExcecao();
		this.ncm = ncm;
	}
	
	protected abstract int getExcecao();
	
	protected abstract void setExcecao(int excecao);
	
	
	protected final Long getNcmId() {
		return this.ncmId;
	}
	
	public final Ncm getNcm() {
		if (ncm != null) {
			Ncm ncm = new Ncm(ncmId, classeFiscal, excecao);
			this.ncm = ncm;
		}
		return ncm;
	}
	
	

			
	protected abstract ProdutoUnidade getUnidade();
	
	protected abstract void setUnidade(ProdutoUnidade unidade);
	
}

