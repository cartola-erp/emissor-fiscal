package net.cartola.emissorfiscal.pessoa;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
@ToString
@Entity
@Table(name = "pess_alte_sped")
public class PessoaAlteradoSped {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@CreationTimestamp
	@Column(name = "dt_alte_cad")
	private LocalDate dtAlteracaoCadastro;
	
	private String nomeAnt;
	private String nomeNovo;
	private int codPaisAnt;
	private int codPaisNovo;
	private String cnpjAnt;
	private String cnpjNovo;
	private String cpfAnt;
	private String cpfNovo;
	private int codMunAnt;		// no erpj ibgeCodigo em tbl cidades
	private int codMunNovo;
	private String suframaAnt;
	private String suframaNovo;
	@Column(name = "end_ant")
	private String enderecoAnt;
	@Column(name = "end_novo")
	private String enderecoNovo;
	@Column(name = "num_ant")
	private int numeroAnt;
	@Column(name = "num_novo")
	private int numeroNovo;
	@Column(name = "compl_ant")
	private String complementoAnt;
	@Column(name = "compl_novo")
	private String complementoNovo;
	private String bairroAnt;
	private String bairroNovo;
	
	private LocalDateTime cadastro;
	private String criadoPor;
	
	
}
