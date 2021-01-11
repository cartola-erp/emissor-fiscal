package net.cartola.emissorfiscal.pessoa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.cartola.emissorfiscal.estado.EstadoSigla;

/**
 * 
 * Tabela populada pela -> cadastros | Do ERP
 * 
 * @date 6 de jan. de 2021
 * @author robson.costa
 */
@Entity
@Table(name = "pess_end")
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class PessoaEndereco implements Serializable {
	
	private static final long serialVersionUID = 4332938216302489958L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pess_end_id")
	private Long id;
	
	@Column(name = "cad_cod_erp")
	private int cadastroCodigoErp;
	@Column(name = "cad_loja_erp")
	private int cadastroLojaErp;
	
//	@Column(name = "end_cod_erp")
//	private int enderecoCodigoErp;
//	@Column(name = "end_loja_erp")
//	private int enderecoLojaErp;

//	@JsonAlias("tipoEndereco")
	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "enum('PRINCIPAL', 'FATURAMENTO', 'FINANCEIRO', 'ENTREGA', 'TRABALHO')")
	private PessoaEnderecoTipo tipo;
	
	private String cep;
//	private int cidadeCodigo;

	@Column(name = "logra_end")
	private String logradouroEndereco;

	@Column(name = "end_num")
	private Integer enderecoNumero;

	@Column(name = "compl_end")
	private String complementoEndereco;
	
	private String bairro;
	private String cidade;

	@Enumerated(EnumType.STRING)
	private EstadoSigla uf = EstadoSigla.SP;

//	private Double latitude;
//	private Double longitude;
//	private String googlePlaceId;
	
	@Column(name="ibgeCod")
	private int ibgeCodigo;
	
}
