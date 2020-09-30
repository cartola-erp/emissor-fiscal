package net.cartola.emissorfiscal.produto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

/**
 * 30/09/2020
 * @author robson.costa
 * 
 * Classe que deverá guardar quais produtos/itens tiveram alteração em sua DESCRIÇÃO e/ou CODIGO, 
 * pois tem que informa essa alteração no SPED: REGISTRO 0205
 * 
 */
@Getter
@Setter
public class ProdutoAlteradoSped {
	
	private Long id;
	private int codigoProduto;
	private String codigoFabricante;
	private String codigoOriginal;
	private String codigoBarras;
	private Integer etiquetaCodigo;	
	// PS: Atualmente sempre informa o: CODIGO_X + CODIGO_SEQUENCIA, como codigo do PRODUTO, no SPED
	// Preciso disso abaixo no SPED
	private long codigoXAnt;
	private String codigoSequenciaAnt;
	private String codigoNovo;
	private String descricaoAnt;
	private String descricaoNova;
	private LocalDate dtInicialUtilizacaoDescAnterior;
	private LocalDate dtFinalUtilizacaoDescAnterior;

}
