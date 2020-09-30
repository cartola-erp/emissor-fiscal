package net.cartola.emissorfiscal.loja;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import net.cartola.emissorfiscal.pessoa.RegimeTributario;
import net.cartola.emissorfiscal.sped.fiscal.enums.PerfilEnquadramento;

/**
 * 25/09/2020
 * @author robson.costa
 */
// É a mesma classe do ERP (ao menos do WS, pois não mudei nada)
@Getter
@Setter
public class Loja implements Serializable {
	
	private static final long serialVersionUID = -7121328260972557814L;
	
	private int codigo;
	private String nome = "";
	private String razaoSocial = "";
	private String nomeFantasia = "";
	private int cnae; //no DAO é String, mas no banco é INT -> deixei int
	private String cnpj = "";
	private String ie = "";
	private String im = "";
	private String endereco = "";
	private int enderecoNumero;
	private String enderecoComplemento = "";
	private String bairro = "";
	private Integer cidadeCodigo;
	private String cep = "";
	private String telefone = "";
	private Date cadastro;
	private Date alterado;
	private Integer cedenteCodigo;
	private Integer tabelaPrecoCodigo;
	private boolean estoqueGerenciado = false;
	private String contatoSintegra = "";
	private String site = "";
	private int codigoContabilContmatic;
	private RegimeTributario tributacao = RegimeTributario.SIMPLES;
	private boolean tributaIpi = false;
	private PerfilEnquadramento spedFiscalPerfil; //No erpj é String -> ver se não implica em algo na integração
	private String eMail = "";
	private Double latitude;
	private Double longitude;
	private Integer acessoGestorCodigo;
	private String controlpayKey;
	private String senhaControlpay;
	private String criadoPor;
	private String alteradoPor;
//	private List<Estoque> estoques;


//	public List<Estoque> getEstoques() {
//		return estoques;
//	}
//
//	public void setEstoques(List<Estoque> estoques) {
//		this.estoques = estoques;
//	}

	@Override
	public String toString() {
		return "Loja [codigo=" + codigo + ", nome=" + nome + ", razaoSocial=" + razaoSocial + ", nomeFantasia="
				+ nomeFantasia + ", cnae=" + cnae + ", cnpj=" + cnpj + ", ie=" + ie + ", im=" + im + ", endereco="
				+ endereco + ", enderecoNumero=" + enderecoNumero + ", enderecoComplemento=" + enderecoComplemento
				+ ", bairro=" + bairro + ", cidadeCodigo=" + cidadeCodigo + ", cep=" + cep + ", telefone=" + telefone
				+ ", cadastro=" + cadastro + ", alterado=" + alterado + ", cedenteCodigo=" + cedenteCodigo
				+ ", tabelaPrecoCodigo=" + tabelaPrecoCodigo + ", estoqueGerenciado=" + estoqueGerenciado
				+ ", contatoSintegra=" + contatoSintegra + ", site=" + site + ", codigoContabilContmatic="
				+ codigoContabilContmatic + ", tributacao=" + tributacao + ", tributaIpi=" + tributaIpi
				+ ", spedFiscalPerfil=" + spedFiscalPerfil + ", eMail=" + eMail + ", latitude=" + latitude
				+ ", longitude=" + longitude + ", acessoGestorCodigo=" + acessoGestorCodigo + ", criadoPor=" + criadoPor
				+ ", alteradoPor=" + alteradoPor + "]";
	}
}
