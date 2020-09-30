package net.cartola.emissorfiscal.produto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 19 de mai de 2020
 *
 * @author gregory.feijon
 *
 */
/** É a mesma classe do  WS, pois não mudei nada) **/
public abstract class ProdutoItem {

	@JsonAlias("devolucaoLoja")
	protected int loja;

	@JsonAlias({ "itemCodigo", "devolucaoItem" })
	protected int item;

	protected transient int itemBanco;

	protected double quantidade;
	protected double quantidadeGarantia;
	
	protected Integer produtoCodigo;
	protected Long codigoX = 0L;
	protected String codigoSequencia = "";
	protected String codigoFabricante = "";

	@JsonAlias({ "grupo", "codigoGrupo" })
	protected Integer grupoCodigo;

	@JsonAlias("descricaoProduto")
	protected String descricao = "";

	protected String marca = "";

	@JsonAlias("unidadeOrigem")
	protected String unidade = "";

	protected String codigoBarras = "";

	protected Double valorFrete;
	protected double valorUnidade;

	@JsonAlias("valor")
	protected double valorTotal;

	@JsonAlias("custoUnitario")
	protected double custoUnidade;

	@JsonAlias("valorBaseRevenda")
	protected double valorBase;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	protected LocalDateTime cadastro;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	protected LocalDateTime alterado;
	
	public void setValorBaseString(String valorBase) {
		this.valorBase = Double.parseDouble(valorBase.replace(",", "."));
	}
	
}
