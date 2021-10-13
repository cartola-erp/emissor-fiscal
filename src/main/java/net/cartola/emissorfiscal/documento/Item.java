package net.cartola.emissorfiscal.documento;

import java.math.BigDecimal;
import java.util.Objects;

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
    protected ProdutoOrigem origem = ProdutoOrigem.NACIONAL;
	protected BigDecimal valorOutrasDespesasAcessorias = BigDecimal.ZERO;
    protected BigDecimal icmsReducaoBaseAliquota = BigDecimal.ZERO;
    protected BigDecimal icmsReducaoBaseStAliquota = BigDecimal.ZERO;
	protected BigDecimal icmsAliquota = BigDecimal.ZERO;
	protected BigDecimal icmsStAliquota = BigDecimal.ZERO;
	protected BigDecimal icmsIva = BigDecimal.ZERO;

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
	
//	protected abstract ProdutoOrigem getOrigem();

//	protected abstract void setOrigem(ProdutoOrigem origem);
	
	protected final Long getNcmId() {
		return this.ncmId;
	}
	
	public final Ncm getNcm() {
		if (this.ncm == null || this.ncm.getNumero() == 0) {
			Ncm ncm = new Ncm(this.ncmId, this.classeFiscal, this.excecao);
			this.ncm = ncm;
		}
		return this.ncm;
	}
	
	

			
	protected abstract ProdutoUnidade getUnidade();
	
	protected abstract void setUnidade(ProdutoUnidade unidade);
	
	@Override
	public int hashCode() {
		return Objects.hash(classeFiscal, codigoSequencia, codigoX, excecao, item, quantidade);
	}
		
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		return Objects.equals(classeFiscal, other.classeFiscal)
				&& Objects.equals(codigoSequencia, other.codigoSequencia) 
				&& Objects.equals(codigoX, other.codigoX) 
				&& excecao == other.excecao 
				&& item == other.item 
				&& (quantidade.compareTo(other.quantidade) == 0);
	}
	
	
}

