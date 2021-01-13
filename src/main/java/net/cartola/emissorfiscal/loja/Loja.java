package net.cartola.emissorfiscal.loja;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.cartola.emissorfiscal.estado.EstadoSigla;
import net.cartola.emissorfiscal.pessoa.RegimeTributario;
import net.cartola.emissorfiscal.sped.fiscal.enums.PerfilEnquadramento;

/**
 * 25/09/2020
 * @author robson.costa
 */
// É a mesma classe do ERP (ao menos do WS, pois não mudei nada)
@ToString
@Getter
@Setter
@Entity
@Table(name = "loja")
public class Loja implements Serializable {
	
	private static final long serialVersionUID = -7121328260972557814L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private int codigoLoja;
	private String nome = "";
	private String razaoSocial = "";
	private String nomeFantasia = "";
	private int cnae; //no DAO é String, mas no banco é INT -> deixei int
//	private String cnpj = "";
	private Long cnpj;
	private String ie = "";
	private String im = "";
	private String endereco = "";
	
	@Column(name = "ende_nume")
	private int enderecoNumero;
	
	@Column(name = "ende_compl")
	private String enderecoComplemento = "";
	
	private String bairro = "";
	private Integer cidadeCodigo;
//	private String uf = "SP";
	@Enumerated(EnumType.STRING)
	private EstadoSigla uf = EstadoSigla.SP;
	private String cep = "";
    private int ibgeCodigo;		// vem do obj cidade

	private String telefone = "";
	private Date cadastro;
	private Date alterado;
//	private Integer cedenteCodigo;
//	private Integer tabelaPrecoCodigo;
//	private boolean estoqueGerenciado = false;
//	private String contatoSintegra = "";
//	private String site = "";
//	private int codigoContabilContmatic;
//	private RegimeTributario tributacao = RegimeTributario.NORMAL;
	@Column(name = "regime_tribu")
	private RegimeTributario regimeTributario= RegimeTributario.NORMAL;
//	private boolean tributaIpi = false;
	private PerfilEnquadramento spedFiscalPerfil; //No erpj é String -> ver se não implica em algo na integração
	private String eMail = "";
//	private Double latitude;
//	private Double longitude;
//	private Integer acessoGestorCodigo;
//	private String controlpayKey;
//	private String senhaControlpay;
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

}
