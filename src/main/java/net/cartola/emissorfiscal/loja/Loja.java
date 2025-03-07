package net.cartola.emissorfiscal.loja;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

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
@ToString
@Getter
@Setter
@Entity
@Table(name = "loja", uniqueConstraints = @UniqueConstraint(name = "unk_loja_cnpj", columnNames = {
		"cnpj"})) 
public class Loja implements Serializable {
	
	private static final long serialVersionUID = -7121328260972557814L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private int codigoLoja;
	private String nome = "";
	private String razaoSocial = "";
	private String nomeFantasia = "";
	@Column(length = 20)
	private String cnae; //no DAO é String, mas no banco é INT -> deixei int
	@Column(length = 14)
	private String cnpj;
	private String ie = "";
	private String im = "";
	private String endereco = "";
	
	@Column(name = "ende_nume")
	private int enderecoNumero;
	
	@Column(name = "ende_compl")
	private String enderecoComplemento = "";
	
	private String bairro = "";
	private Integer cidadeCodigo;
	@Column(length = 2)
	@Enumerated(EnumType.STRING)
	private EstadoSigla uf = EstadoSigla.SP;
	@Column(length = 9)
	private String cep = "";
    private int ibgeCodigo;		// vem do obj cidade

	private String telefone = "";
//	private Integer cedenteCodigo;
//	private String site = "";
//	private RegimeTributario tributacao = RegimeTributario.NORMAL;
	@Enumerated(EnumType.STRING)
	@Column(name = "regime_tribu", columnDefinition="enum('SIMPLES', 'PRESUMIDO', 'REAL') ")
	private RegimeTributario regimeTributario= RegimeTributario.NORMAL;
//	private boolean tributaIpi = false;
	@Enumerated(EnumType.STRING)
	@Column(name = "sped_fisc_perf", columnDefinition="enum('A', 'B', 'C') ")
	private PerfilEnquadramento spedFiscalPerfil; //No erpj é String -> ver se não implica em algo na integração
	private String eMail = "";
	private String criadoPor;
	private LocalDateTime cadastro;
	private String alteradoPor;
	private LocalDateTime alterado;
	// Numero fornecido pela SEFAZ, https://www.nfce.fazenda.sp.gov.br/NFCeSiteContribuinte/Secure/GerenciamentoCodAutQRCode.aspx
	// O número sempre está formatado com 6 digitos
	@Column(name="nfce_codi_segu_id")
	private Integer nfceCodigoSegurancaId;
	// Numero fornecido pela SEFAZ, https://www.nfce.fazenda.sp.gov.br/NFCeSiteContribuinte/Secure/GerenciamentoCodAutQRCode.aspx
	@Column(name="nfce_codi_segu_nume")
	private String nfceCodigoSegurancaNumero;

	public String getLojaAndRazaoSocial() {
		return this.codigoLoja +" - "+ razaoSocial;
	}
	
	public String getEnderecoAndNumeroAndBairro() {
		return this.razaoSocial +", "+ this.enderecoNumero + " - "+ this.bairro;
	}
	
}
