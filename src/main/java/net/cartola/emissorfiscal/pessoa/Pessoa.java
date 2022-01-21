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

import lombok.EqualsAndHashCode;

/**
 *  Tabela populada pela -> cadastros | Do ERP
 *	22 de nov de 2019
 *	@author robson.costa
 */

@EqualsAndHashCode
@Entity
@Table(name = "pess")
public class Pessoa {
	
	@EqualsAndHashCode.Exclude private Long id;
	private String cnpj;
    private String cpf;
	private RegimeTributario regimeTributario;
	private PessoaTipo pessoaTipo = PessoaTipo.FISICA;
	private String ie;
	private String codSuframa;
	private String nome;
	private int codigoErp;
	private int lojaErp;
	private int enderecoCodigoErp;
	private PessoaEndereco endereco; 
	private boolean zeraOutrasDespesas;
	
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
	@Column(name="regime_tributario", columnDefinition="enum('SIMPLES', 'SIMPLES_EXCESSO', 'NORMAL') ")
	public RegimeTributario getRegimeTributario() {
		return regimeTributario;
	}

	public void setRegimeTributario(RegimeTributario regimeTributario) {
		this.regimeTributario = regimeTributario;
	}

	@Enumerated(EnumType.STRING)
	@Column(name="pess_tipo", columnDefinition="enum('FISICA', 'JURIDICA') default 'FISICA' ")
	public PessoaTipo getPessoaTipo() {
		return pessoaTipo;
	}

	public void setPessoaTipo(PessoaTipo pessoaTipo) {
		this.pessoaTipo = pessoaTipo;
	}

	@Column(length = 14)
	public String getIe() {
		return ie;
	}

	public void setIe(String ie) {
		this.ie = ie;
	}

	@Column(length = 9)
	public String getCodSuframa() {
		return codSuframa;
	}

	public void setCodSuframa(String codSuframa) {
		this.codSuframa = codSuframa;
	}
	
	@Column(length = 255)
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
	@JoinColumn(name = "end_id", referencedColumnName = "pess_end_id", nullable = false, foreignKey = @ForeignKey(name = "fnk_pessoa_end_id"))
	public PessoaEndereco getEndereco() {
		return endereco;
	}
	@Column(name = "end_cod_erp")
	public void setEndereco(PessoaEndereco endereco) {
		this.endereco = endereco;
	}
	
	@Column(name = "zera_outras_despesas", columnDefinition = " TINYINT(1) DEFAULT FALSE")
	public boolean isZeraOutrasDespesas() {
		return zeraOutrasDespesas;
	}
	public void setZeraOutrasDespesas(boolean zeraOutrasDespesas) {
		this.zeraOutrasDespesas = zeraOutrasDespesas;
	}

	@Override
	public String toString() {
		return "Pessoa [id=" + id + ", cnpj=" + cnpj + ", cpf=" + cpf + ", regimeTributario=" + regimeTributario
				+ ", pessoaTipo=" + pessoaTipo + ", ie=" + ie + ", codSuframa=" + codSuframa + ", nome=" + nome
				+ ", codigoErp=" + codigoErp + ", lojaErp=" + lojaErp + ", enderecoCodigoErp=" + enderecoCodigoErp
				+ ", endereco=" + endereco + ", zeraOutrasDespesas=" + zeraOutrasDespesas + "]";
	}
}


