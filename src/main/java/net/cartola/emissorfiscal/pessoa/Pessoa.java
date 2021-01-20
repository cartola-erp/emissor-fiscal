package net.cartola.emissorfiscal.pessoa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *  Tabela populada pela -> cadastros | Do ERP
 *	22 de nov de 2019
 *	@author robson.costa
 */

@Entity
@Table(name = "pessoa")
//@Table(name = "pess")
public class Pessoa {
	
	private Long id;
	private String cnpj;
    private String cpf;
	private RegimeTributario regimeTributario;
	private PessoaTipo pessoaTipo = PessoaTipo.FISICA;
	private Long ie;
	private Long codSuframa;
	private String nome;
	private int codigoErp;
	private int lojaErp;
	private int enderecoCodigoErp;
	private PessoaEndereco endereco; 
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	
	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	@Enumerated(EnumType.STRING)
	@Column(name="regime_tributario", columnDefinition="enum('SIMPLES', 'PRESUMIDO', 'REAL') ")
	public RegimeTributario getRegimeTributario() {
		return regimeTributario;
	}

	public void setRegimeTributario(RegimeTributario regimeTributario) {
		this.regimeTributario = regimeTributario;
	}

	@Enumerated(EnumType.STRING)
	@Column(name="pessoa_tipo", columnDefinition="enum('FISICA', 'JURIDICA') default 'FISICA' ")
	public PessoaTipo getPessoaTipo() {
		return pessoaTipo;
	}

	public void setPessoaTipo(PessoaTipo pessoaTipo) {
		this.pessoaTipo = pessoaTipo;
	}

	public Long getIe() {
		return ie;
	}

	public void setIe(Long ie) {
		this.ie = ie;
	}

	public Long getCodSuframa() {
		return codSuframa;
	}

	public void setCodSuframa(Long codSuframa) {
		this.codSuframa = codSuframa;
	}
	
	@Column(length = 60)
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Column(name = "cod_erp")
	public int getCodigoErp() {
		return codigoErp;
	}

	public void setCodigoErp(int codigoErp) {
		this.codigoErp = codigoErp;
	}

	@Column(name = "loja_erp")
	public int getLojaErp() {
		return lojaErp;
	}

	public void setLojaErp(int lojaErp) {
		this.lojaErp = lojaErp;
	}

	@Column(name = "end_cod_erp")
	public int getEnderecoCodigoErp() {
		return enderecoCodigoErp;
	}

	public void setEnderecoCodigoErp(int enderecoCodigoErp) {
		this.enderecoCodigoErp = enderecoCodigoErp;
	}

	@ManyToOne(fetch = FetchType.EAGER) 
	@JoinColumn(name = "endereco_id", referencedColumnName = "pess_end_id", nullable = false, foreignKey = @ForeignKey(name = "fnk_pessoa_end_id"))
	public PessoaEndereco getEndereco() {
		return endereco;
	}

	public void setEndereco(PessoaEndereco endereco) {
		this.endereco = endereco;
	}

	@Override
	public String toString() {
		return "Pessoa [id=" + id + ", cnpj=" + cnpj + ", regimeTributario=" + regimeTributario
				+ ", pessoaTipo=" + pessoaTipo + "]";
	}
	
}


