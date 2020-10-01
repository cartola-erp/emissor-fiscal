package net.cartola.emissorfiscal.cadastro;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import net.cartola.emissorfiscal.pessoa.PessoaTipo;
import net.cartola.emissorfiscal.util.StringUtil;


/**
 * 25/09/2020
 * @author robson.costa
 */
/** OS ATRIBUTOS COMENTADOS, acredito que NÃO tem nenhuma utilidade para essa API **/
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CadastroDTO implements Serializable {

	private static final long serialVersionUID = 7888779488545204252L;
	
	private int codigo;
	private int loja;

	private PessoaTipo tipo = PessoaTipo.FISICA;

	@JsonAlias({ "cpf", "cnpj" })
	private String cpfCnpj; // no erpj são separados

	private String cpfContador;
	private String crc;

	@JsonAlias({ "rg", "ie" })
	private String rgIe; // no erpj são separados

	private String im;
	private String cnae;

	private String nome = "";

	@JsonAlias("apelido")
	private String nomeFantasia;

//	private boolean inativo = false;
//	private boolean bloqueado = false;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonAlias("dataNascimento")
	private LocalDate nascimentoFundacao;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate obitoEm;

	private boolean cliente = false;
	private boolean fornecedor = false;
	private boolean postoCombustivel = false;
	private boolean funcionario = false;
	private boolean unidadeEmpresa = false;
	private boolean transportadora = false;
	private boolean contador = false;

//	@JsonAlias("cadastroGrupoCodigo")
//	private Integer cadastroGrupo;

	private CadastroDocumentoFiscal documentoFiscal = null;
	private String cpfCnpjOutro;

	@JsonAlias("modoPgto")
	private ModoPagamentoTipo modoPgtoTipo = ModoPagamentoTipo.VISTA;

	private CobrancaTipo cobrancaTipo = CobrancaTipo.INDEFINIDO;

	private String obs;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	private LocalDateTime cadastro;

	private String criadoPor;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	private LocalDateTime alterado;

	private String alteradoPor;

	private FaturaTipo fatura = FaturaTipo.CF;

//	private int tributacaoCodigo;
	private boolean unidadeMedidaNotaKg;

	@JsonAlias("cedenteCodigo")
	private Integer codigoCedente;

//	private Double comissaoPorcentagem; // não tem no CadastroModel em erpj -> criado em VendaItemModel

//	private Integer enderecoCodigo;
	private Integer enderecoLoja;
//	private Integer emailCodigo;
	private Integer emailLoja;
//	private Integer telefoneCodigo;
	private Integer telefoneLoja;

	private List<CadastroEnderecoDTO> listEnderecos;

	public void setCpfCnpj(String cpfCnpj) {
		if (cpfCnpj != null && !cpfCnpj.isEmpty()) {
			if (StringUtil.isCpf(cpfCnpj)) {
				this.cpfCnpj = validaCpf(cpfCnpj);
			} else {
				if (StringUtil.isCnpj(cpfCnpj)) {
					this.cpfCnpj = validaCnpj(cpfCnpj);
				} else {
					this.cpfCnpj = cpfCnpj;
				}
			}
		}
		this.cpfCnpj = cpfCnpj;
	}

	public String validaCpf(String cpf) {
		return cpf;
	}

	public String validaCnpj(String cnpj) {
		return cnpj;
	}
}
