package net.cartola.emissorfiscal.sped.fiscal.blocoE;

import java.math.BigDecimal;
import java.time.LocalDate;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;
import net.cartola.emissorfiscal.sped.fiscal.enums.ObrigacaoIcmsARecolher;
import net.cartola.emissorfiscal.sped.fiscal.enums.OrigemDoProcesso;

/**
 * 11/09/2020
 * @author robson.costa
 */
@Record(fields = { 
		@Field(name = "reg", maxLength = 4),
		@Field(name = "codOr"),	
		@Field(name = "vlOr"),
		@Field(name = "dtVcto"),
		@Field(name = "codRec"),
		@Field(name = "numProc"),
		@Field(name = "indProc"),
		@Field(name = "proc"),
		@Field(name = "txtCompl"),
		@Field(name = "mesRef")
})
public class RegE316 {
	
	private final String reg = "E316";
	private ObrigacaoIcmsARecolher codOr;
	private BigDecimal vlOr;
	private LocalDate dtVcto;
	private String codRec;
	private Long numProc;
	private OrigemDoProcesso indProc;
	private String proc;
	private String txtCompl;
	private String mesRef;	//Informe o mês de referência no formato “mmaaaa | 6 caracteres”
	
	public String getReg() {
		return reg;
	}

	public ObrigacaoIcmsARecolher getCodOr() {
		return codOr;
	}

	public void setCodOr(ObrigacaoIcmsARecolher codOr) {
		this.codOr = codOr;
	}

	public BigDecimal getVlOr() {
		return vlOr;
	}

	public void setVlOr(BigDecimal vlOr) {
		this.vlOr = vlOr;
	}

	public LocalDate getDtVcto() {
		return dtVcto;
	}

	public void setDtVcto(LocalDate dtVcto) {
		this.dtVcto = dtVcto;
	}

	public String getCodRec() {
		return codRec;
	}

	public void setCodRec(String codRec) {
		this.codRec = codRec;
	}

	public Long getNumProc() {
		return numProc;
	}

	public void setNumProc(Long numProc) {
		this.numProc = numProc;
	}

	public OrigemDoProcesso getIndProc() {
		return indProc;
	}

	public void setIndProc(OrigemDoProcesso indProc) {
		this.indProc = indProc;
	}

	public String getProc() {
		return proc;
	}

	public void setProc(String proc) {
		this.proc = proc;
	}

	public String getTxtCompl() {
		return txtCompl;
	}

	public void setTxtCompl(String txtCompl) {
		this.txtCompl = txtCompl;
	}

	public String getMesRef() {
		return mesRef;
	}

	public void setMesRef(String mesRef) {
		this.mesRef = mesRef;
	}
}
