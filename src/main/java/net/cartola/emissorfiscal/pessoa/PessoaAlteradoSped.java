package net.cartola.emissorfiscal.pessoa;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

/**
 * 30/09/2020
 * @author robson.costa
 * 
 * Classe que deverá guardar quais cadastros tiveram alteração em um dos campos que o SPED solicita que infome..., 
 * pois tem que informa essa alteração no SPED: REGISTRO 0175
 * 
 * Semelhante ao que irei fazer com o @ProdutoAlteradoSped
 * Porém a de cadastro a lógica deverá ser feita no ERP, pois segundo o Greg a que tem no WS, ainda não é usada;
 */
@Getter
@Setter
public class PessoaAlteradoSped {

	private Long id;

	private LocalDate dtAlteracaoCadastro;
	
	private String nomeAnt;
	private String nomeNovo;
	private int codPaisAnt;
	private int codPaisNovo;
	private String CnpjAnt;
	private String CnpjNovo;
	private String cpfAnt;
	private String cpfNovo;
	private int codMunAnt;		// no erpj ibgeCodigo em tbl cidades
	private int codMunNovo;
	private String suframaAnt;
	private String suframaNovo;
	private String enderecoAnt;
	private String enderecoNovo;
	private int numeroAnt;
	private int numeroNovo;
	private String complementoAnt;
	private String complementoNovo;
	private String bairroAnt;
	private String bairroNovo;
	
	private LocalDateTime cadastro;
	private String criadoPor;
	
	
}
