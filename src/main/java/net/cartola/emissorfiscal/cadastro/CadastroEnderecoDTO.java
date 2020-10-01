package net.cartola.emissorfiscal.cadastro;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;


/**
 * 25/09/2020
 * @author robson.costa
 */
/** OS ATRIBUTOS COMENTADOS, acredito que NÃO tem nenhuma utilidade para essa API **/
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CadastroEnderecoDTO implements Serializable {
	
	private static final long serialVersionUID = 9008669884881355862L;

	private int cadastroCodigo;
	private int cadastroLoja;
	private int enderecoCodigo;
	private int enderecoLoja;

	@JsonAlias("tipoEndereco")
	private CadastroEnderecoTipo tipo;

	private String cep;
	private int cidadeCodigo;
	private LogradouroTipo tipoLogradouro = LogradouroTipo.R;

	private String logradouroEndereco;

	@JsonAlias("numeroEndereco")
	private Integer enderecoNumero;

	private String complementoEndereco;
	private TipoBairro tipoBairro = TipoBairro.BR;
	private String bairro;
//	private String caixaPostal;
	private String obsEndereco;
	private Double latitude;
	private Double longitude;
	private String googlePlaceId;
//	private Integer lojaFaturamento;

//	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
//	private LocalDateTime cadastro;
//
//	private String criadoPor;
//
//	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
//	private LocalDateTime alterado;
//
//	private String alteradoPor;
//	private Long cartolaId;
//
//	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
//	private LocalDateTime cartolaTs;
	
}
