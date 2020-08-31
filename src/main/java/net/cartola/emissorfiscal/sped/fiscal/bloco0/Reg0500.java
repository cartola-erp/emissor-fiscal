package net.cartola.emissorfiscal.sped.fiscal.bloco0;

import java.time.LocalDate;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;
import net.cartola.emissorfiscal.sped.fiscal.GrupoDeConta;
import net.cartola.emissorfiscal.sped.fiscal.IndicadorDoTipoDeConta;

/**
 * 19/08/2020
 * @author robson.costa
 * 
 * Registro 0500 - Plano de contas contábeis
 */
@Record(fields = {
	    @Field(name = "reg", maxLength = 4),
	    @Field(name = "dtAlt"),
	    @Field(name = "codNatCc"),
	    @Field(name = "indCta"),
	    @Field(name = "nivel"),
	    @Field(name = "codCta"),
	    @Field(name = "nomeCta")
})
public class Reg0500 {
		
	private final String reg = "0500";
    private LocalDate dtAlt;
    private GrupoDeConta codNatCc;
    private IndicadorDoTipoDeConta indCta;
    private Integer nivel;
    private String codCta;
    private String nomeCta;
	
    public String getReg() {
		return reg;
	}

	public LocalDate getDtAlt() {
		return dtAlt;
	}

	public void setDtAlt(LocalDate dtAlt) {
		this.dtAlt = dtAlt;
	}

	public GrupoDeConta getCodNatCc() {
		return codNatCc;
	}

	public void setCodNatCc(GrupoDeConta codNatCc) {
		this.codNatCc = codNatCc;
	}

	public IndicadorDoTipoDeConta getIndCta() {
		return indCta;
	}

	public void setIndCta(IndicadorDoTipoDeConta indCta) {
		this.indCta = indCta;
	}

	public Integer getNivel() {
		return nivel;
	}

	public void setNivel(Integer nivel) {
		this.nivel = nivel;
	}

	public String getCodCta() {
		return codCta;
	}

	public void setCodCta(String codCta) {
		this.codCta = codCta;
	}

	public String getNomeCta() {
		return nomeCta;
	}

	public void setNomeCta(String nomeCta) {
		this.nomeCta = nomeCta;
	}

}
