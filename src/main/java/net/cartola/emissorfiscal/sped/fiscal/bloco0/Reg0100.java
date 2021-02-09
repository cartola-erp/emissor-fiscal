package net.cartola.emissorfiscal.sped.fiscal.bloco0;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;
import lombok.ToString;

/**
 * 19/08/2020
 * @author robson.costa
 * 
 * Registro 0100 - Dados do Contabilista
 */

@Record(fields = { 
		@Field(name = "reg", maxLength = 4), 
		@Field(name = "nome", maxLength = 100),
		@Field(name = "cpf"),
		@Field(name = "crc", maxLength = 15),
		@Field(name = "cnpj"),
		@Field(name = "cep", length = 8),
		@Field(name = "end", maxLength = 60),
		@Field(name = "num", maxLength = 10),
		@Field(name = "compl", maxLength = 60),
		@Field(name = "bairro", maxLength = 60),
		@Field(name = "fone", maxLength = 11),
		@Field(name = "fax", maxLength = 11),
		@Field(name = "email"),
		@Field(name = "codMun", length = 7)
})
@ToString
public class Reg0100 {
	
	private final String reg = "0100";
	private String nome;
	private String cpf;
	private String crc;
	private String cnpj;
	private Long cep;
	private String end;
	private int num;
	private String compl;
	private String bairro;
	private String fone;
	private String fax;
	private String email;
	private Long codMun;
	
	public String getReg() {
		return reg;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getCrc() {
		return crc;
	}

	public void setCrc(String crc) {
		this.crc = crc;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public Long getCep() {
		return cep;
	}

	public void setCep(Long cep) {
		this.cep = cep;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getCompl() {
		return compl;
	}

	public void setCompl(String compl) {
		this.compl = compl;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getFone() {
		return fone;
	}

	public void setFone(String fone) {
		this.fone = fone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getCodMun() {
		return codMun;
	}

	public void setCodMun(Long codMun) {
		this.codMun = codMun;
	}
	
}
