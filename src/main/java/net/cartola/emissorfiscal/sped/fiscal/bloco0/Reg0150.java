package net.cartola.emissorfiscal.sped.fiscal.bloco0;

import java.util.List;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;

/**
 * 19/08/2020
 * @author robson.costa
 * 
 * Registro 0150 - Tabela de Cadastro do Participante
 */
@Record(fields = { 
	@Field(name = "reg", maxLength = 4), 
	@Field(name = "codPart"),
	@Field(name = "nome", maxLength = 100),
	@Field(name = "codPais"),
	@Field(name = "cnpj"),
	@Field(name = "cpf"),
	@Field(name = "ie"),
	@Field(name = "codMun"),
	@Field(name = "suframa", length = 9),
	@Field(name = "end", maxLength = 60),
	@Field(name = "num", maxLength = 10),
	@Field(name = "compl", maxLength = 60),
	@Field(name = "bairro", maxLength = 60),
	@Field(name = "reg0175")
})		
public class Reg0150 {
	
	private final String reg = "0150";
    private String codPart;
    private String nome;
    private int codPais;
    private String cnpj;
    private String cpf;
    private String ie;
    private int codMun;
    private String suframa;
    private String end;
    private String num;
    private String compl;
    private String bairro;
    private List<Reg0175> reg0175;
	
    public String getReg() {
		return reg;
	}
	
	public String getCodPart() {
		return codPart;
	}

	public void setCodPart(String codPart) {
		this.codPart = codPart;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getCodPais() {
		return codPais;
	}

	public void setCodPais(int codPais) {
		this.codPais = codPais;
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

	public String getIe() {
		return ie;
	}

	public void setIe(String ie) {
		this.ie = ie;
	}

	public int getCodMun() {
		return codMun;
	}

	public void setCodMun(int codMun) {
		this.codMun = codMun;
	}

	public String getSuframa() {
		return suframa;
	}

	public void setSuframa(String suframa) {
		this.suframa = suframa;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
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

	public List<Reg0175> getReg0175() {
		return reg0175;
	}

	public void setReg0175(List<Reg0175> reg0175) {
		this.reg0175 = reg0175;
	}
}
