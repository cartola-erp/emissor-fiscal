package net.cartola.emissorfiscal.sped.fiscal.bloco0;

import java.util.Date;

import com.ancientprogramming.fixedformat4j.annotation.Record;

import net.cartola.emissorfiscal.sped.fiscal.FinalidadeDoArquivo;
import net.cartola.emissorfiscal.sped.fiscal.PerfilEnquadramento;
import net.cartola.emissorfiscal.sped.fiscal.TipoDeAtividade;
import net.cartola.emissorfiscal.sped.fiscal.VersaoDoLayout;

/**
 * 19/08/2020
 * @author robson.costa
 * 
 * Registro 0000 - abertura do arquivo digital e indentificação da entidade
 */

@Record
public class Reg0000AberturaArquivoDigital {
	
	private String reg;
	private VersaoDoLayout codVer;
	private FinalidadeDoArquivo codFin = FinalidadeDoArquivo.REMESSA_ARQUIVO_ORIGINAL;
	private Date dtIni;
	private Date dtFin;
	private String nome;
	private Long cnpj;
	private Long cpf;
	private String uf;
	private String ie;
	private Integer codMun;
	private String im;
	private String suframa;
	private PerfilEnquadramento indPerfil = PerfilEnquadramento.A;
	private TipoDeAtividade indAtiv = TipoDeAtividade.OUTROS;
	
	public String getReg() {
		return reg;
	}
	
	public void setReg(String reg) {
		this.reg = reg;
	}

	public VersaoDoLayout getCodVer() {
		return codVer;
	}

	public void setCodVer(VersaoDoLayout codVer) {
		this.codVer = codVer;
	}

	public FinalidadeDoArquivo getCodFin() {
		return codFin;
	}

	public void setCodFin(FinalidadeDoArquivo codFin) {
		this.codFin = codFin;
	}

	public Date getDtIni() {
		return dtIni;
	}

	public void setDtIni(Date dtIni) {
		this.dtIni = dtIni;
	}

	public Date getDtFin() {
		return dtFin;
	}

	public void setDtFin(Date dtFin) {
		this.dtFin = dtFin;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getCnpj() {
		return cnpj;
	}

	public void setCnpj(Long cnpj) {
		this.cnpj = cnpj;
	}

	public Long getCpf() {
		return cpf;
	}

	public void setCpf(Long cpf) {
		this.cpf = cpf;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getIe() {
		return ie;
	}

	public void setIe(String ie) {
		this.ie = ie;
	}

	public Integer getCodMun() {
		return codMun;
	}

	public void setCodMun(Integer codMun) {
		this.codMun = codMun;
	}

	public String getIm() {
		return im;
	}

	public void setIm(String im) {
		this.im = im;
	}

	public String getSuframa() {
		return suframa;
	}

	public void setSuframa(String suframa) {
		this.suframa = suframa;
	}

	public PerfilEnquadramento getIndPerfil() {
		return indPerfil;
	}

	public void setIndPerfil(PerfilEnquadramento indPerfil) {
		this.indPerfil = indPerfil;
	}

	public TipoDeAtividade getIndAtiv() {
		return indAtiv;
	}

	public void setIndAtiv(TipoDeAtividade indAtiv) {
		this.indAtiv = indAtiv;
	}
	
}
