package net.cartola.emissorfiscal.sped.fiscal.bloco0;

import java.time.LocalDate;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;
import lombok.ToString;
import net.cartola.emissorfiscal.sped.fiscal.enums.FinalidadeDoArquivo;
import net.cartola.emissorfiscal.sped.fiscal.enums.PerfilEnquadramento;
import net.cartola.emissorfiscal.sped.fiscal.enums.TipoDeAtividade;
import net.cartola.emissorfiscal.sped.fiscal.enums.VersaoDoLayout;

/**
 * 19/08/2020
 * @author robson.costa
 * 
 * Registro 0000 - abertura do arquivo digital e indentificação da entidade
 */

@Record(fields = {
		@Field(name = "reg", maxLength = 4),	    
		@Field(name = "codVer"),
	    @Field(name = "codFin"),
	    @Field(name = "dtIni"),
	    @Field(name = "dtFin"),
	    @Field(name = "nome", maxLength = 100),
	    @Field(name = "cnpj"),
	    @Field(name = "cpf"),
	    @Field(name = "uf"),
	    @Field(name = "ie"),
	    @Field(name = "codMun", length = 7),
	    @Field(name = "im"),
	    @Field(name = "suframa", length = 9),
	    @Field(name = "indPerfil"),
	    @Field(name = "indAtiv")
})
@ToString
public class Reg0000AberturaArquivoDigital {
	
	private final String reg = "0000";
	private VersaoDoLayout codVer;
	private FinalidadeDoArquivo codFin = FinalidadeDoArquivo.REMESSA_ARQUIVO_ORIGINAL;
	private LocalDate dtIni;
	private LocalDate dtFin;
	private String nome;
	private String cnpj;
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

	public LocalDate getDtIni() {
		return dtIni;
	}

	public void setDtIni(LocalDate dtIni) {
		this.dtIni = dtIni;
	}

	public LocalDate getDtFin() {
		return dtFin;
	}

	public void setDtFin(LocalDate dtFin) {
		this.dtFin = dtFin;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
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
