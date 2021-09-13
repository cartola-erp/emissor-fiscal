package net.cartola.emissorfiscal.tributacao.estadual;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * Classe para retornar os valores de calculo, da GNRE (Guia nacional de recolhimento estadual), para ecommerce acrescentar no valor total do produto,
 * quando for Venda Interestadual para NÃO contribuinte;
 * 
 * Esse são os valores que será retornado para o ecommerce (ou seja lá quem fez a requisição)
 * 
 * @date 10 de set. de 2021
 * @author robson.costa
 */
@ToString
@Setter
@Getter
public class GnreAliquota {
	

	private boolean produtoImportado;		// ProdutoOrigem = 1, 2, 3, 6, 7 é importado  (Aliq Interestadual == 4%) 

	private BigDecimal icmsBase;
    private BigDecimal icmsAliquota;
    private BigDecimal icmsIva;
    private BigDecimal icmsAliquotaDestino;
    private BigDecimal fcpAliquota;	
	
	public GnreAliquota(TributacaoEstadual tribEsta) {
		this.produtoImportado = tribEsta.isProdutoImportado();
		this.icmsBase = tribEsta.getIcmsBase();
		this.icmsAliquota = tribEsta.getIcmsAliquota();
		this.icmsIva = tribEsta.getIcmsIva();
		this.icmsAliquotaDestino = tribEsta.getIcmsAliquotaDestino();
		this.fcpAliquota = tribEsta.getFcpAliquota();
	}
}
