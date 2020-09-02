package net.cartola.emissorfiscal.sped.fiscal.bloco0;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;
import net.cartola.emissorfiscal.sped.fiscal.enums.IdentificacaoTipoDeMercadoria;

/**
 * 19/08/2020
 * @author robson.costa
 * 
 * Registro 0300 - Cadastro de bens ou componentes do Ativo Imobilizado
 */
@Record(fields = { 
	@Field(name = "reg"),
    @Field(name = "codIndBem"),
    @Field(name = "identMerc"),
    @Field(name = "descrItem"),
    @Field(name = "codPrnc"),
    @Field(name = "codCta"),
    @Field(name = "nrParc"),
    @Field(name = "reg0305")
})
public class Reg0300 {
	
	private final String reg = "0300";
	private String codIndBem;
	private IdentificacaoTipoDeMercadoria identMerc;
	private String descrItem;
	private String codPrnc;
	private String codCta;
	private Integer nrParc;
	private Reg0305 reg0305;
	
	public String getReg() {
		return reg;
	}

	public String getCodIndBem() {
		return codIndBem;
	}

	public void setCodIndBem(String codIndBem) {
		this.codIndBem = codIndBem;
	}

	public IdentificacaoTipoDeMercadoria getIdentMerc() {
		return identMerc;
	}

	public void setIdentMerc(IdentificacaoTipoDeMercadoria identMerc) {
		this.identMerc = identMerc;
	}

	public String getDescrItem() {
		return descrItem;
	}

	public void setDescrItem(String descrItem) {
		this.descrItem = descrItem;
	}

	public String getCodPrnc() {
		return codPrnc;
	}

	public void setCodPrnc(String codPrnc) {
		this.codPrnc = codPrnc;
	}

	public String getCodCta() {
		return codCta;
	}

	public void setCodCta(String codCta) {
		this.codCta = codCta;
	}

	public Integer getNrParc() {
		return nrParc;
	}

	public void setNrParc(Integer nrParc) {
		this.nrParc = nrParc;
	}

	public Reg0305 getReg0305() {
		return reg0305;
	}

	public void setReg0305(Reg0305 reg0305) {
		this.reg0305 = reg0305;
	}
}
