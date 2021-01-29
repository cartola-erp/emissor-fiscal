package net.cartola.emissorfiscal.produto;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
@Entity
@Table(name = "prod_alte_sped")
public class ProdutoAlteradoSped {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "prod_cod_erp")
	private int produtoCodigoErp;
	@Column(name = "cod_fabr")
	private String codigoFabricante;
	@Column(name = "cod_original")
	private String codigoOriginal;
	private String ean;
	// PS: Atualmente sempre informa o: CODIGO_X + CODIGO_SEQUENCIA, como codigo do PRODUTO, no SPED
	// Preciso disso abaixo no SPED
	@Column(name = "cod_x_ant")
	private Long codigoXAnt;
	private String codigoSequenciaAnt;
	@Column(name = "cod_novo")
	private String codigoNovo;
	@Column(name = "dscr_ant")
	private String descricaoAnt;
	@Column(name = "dscr_nova")
	private String descricaoNova;
	@Column(name = "data_inic_util_dscr_ant")
	private LocalDate dtInicialUtilizacaoDescAnterior;
	@Column(name = "data_fin_util_dscr_ant")
	private LocalDate dtFinalUtilizacaoDescAnterior;

}
