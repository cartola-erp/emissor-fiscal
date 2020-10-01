package net.cartola.emissorfiscal.compra;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import net.cartola.emissorfiscal.documento.ProdutoOrigem;
import net.cartola.emissorfiscal.produto.ProdutoItem;


/**
 * 25/09/2020
 * @author robson.costa
 */
/** É a mesma classe do  WS, pois não mudei nada) **/
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CompraItem extends ProdutoItem implements Serializable {
	
	private static final long serialVersionUID = 1329297828313077898L;
	
	private int compra;

	private String codigoOriginal;

	private String eanEntrada;
	private Integer ncmEntrada;
	private Integer excessaoIpi;
	private Integer classeFiscal;

	private String descricaoEmpresa;

	private String descricaoFornecedor;
	private String informacoesAdicionais;
	private String etiqueta;
	private Integer produtoUnidadeCodigo;

	private String locacao;

	private Double quantidadeComercial = 0.00;
	private Double quantidadeTributada = 0.00;
	private Double quantidadeAdicional;
	private Double quantidadeEstoque;
	private Double valorUnidadeComercial = 0.00;
	private Double valorUnidadeTributada = 0.00;
	private ProdutoOrigem origem = ProdutoOrigem.NACIONAL;
	private Double icmsCreditoPorcentagem;
	private Double icmsCreditoValor;
	private Integer cfopEmpresa;
	private Integer cfopFornecedor;
	private Integer icmsCstFornecedor;
	private Integer icmsCstEmpresa;
	private String icmsUf;
	private Double icmsBaseReducao = 0.00;
	private Double icmsIsento = 0.00;
	private Double icmsRetidoRemetente;
	private Double icmsBaseRetidoRemetente;
	private Double icmsBaseDestino;
	private Double icmsDestino;
	private String icmsModbc;
	private Double icmsModbcCst;
	private Double icmsCreditoSn;
	private Double icmsBaseFornecedor = 0.00;
	private Double icmsBaseEmpresa;
	private Double icmsAliquotaFornecedor = 0.00;
	private Double icmsAliquotaEmpresa;
	private Double icmsValorFornecedor = 0.00;
	private Double icmsValorEmpresa = 0.00;
	private Double icmsStIva = 0.00;
	private Double icmsStBase = 0.00;
	private Double icmsStAliquota = 0.00;
	private Double icmsStValor = 0.00;
	private Double icmsStBaseReducao;
	private Double icmsStBaseRetido;
	private Double icmsStRetido;
	private Integer ipiCodigoEnquadramento;
	private Integer ipiCstFornecedor;
	private Integer ipiCstEmpresa;
	private Double ipiBaseFornecedor;
	private Double ipiBaseEmpresa;
	private Double ipiAliquotaFornecedor;
	private Double ipiAliquotaEmpresa;
	private Double ipiValorFornecedor = 0.00;
	private Double ipiValorEmpresa;
	private Double ipiValorUnidade;
	private Long ipiCnpjProdutor;
	private Integer pisCstFornecedor;
	private Integer pisCstEmpresa;
	private Double pisBaseFornecedor;
	private Double pisBaseEmpresa;
	private Double pisAliquotaFornecedor;
	private Double pisAliquotaEmpresa;
	private Double pisValorFornecedor;
	private Double pisValorEmpresa;
	private Double pisStBase;
	private Double pisStAliquota;
	private Double pisStValor;
	private Integer cofinsCstFornecedor;
	private Integer cofinsCstEmpresa;
	private Double cofinsBaseFornecedor;
	private Double cofinsBaseEmpresa;
	private Double cofinsAliquotaFornecedor;
	private Double cofinsAliquotaEmpresa;
	private Double cofinsValorFornecedor;
	private Double cofinsValorEmpresa;
	private Double cofinsStBase;
	private Double cofinsStAliquota;
	private Double cofinsStValor;
	private Double desconto = 0.00;
	private Double custoSeguro = 0.00;
	private Double custoOutrasDespesas = 0.00;
	private Double custoFrete = 0.00;
	private Double custoLiquido;
	private Double valorTotalBruto;
	private Integer estoqueCodigo;
	private Integer compraPedidoLoja;
	private Integer compraPedidoCodigo;
	private Integer compraPedidoItem;
	private String codigoProdutoFornecedor;
	private String criadoPor;
	private String alteradoPor;

	
}
