package net.cartola.emissorfiscal.sped.fiscal.bloco1;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;

/**
 * 14/09/2020
 * @author robson.costa
 * 
 */
@Record(fields = { 
		@Field(name = "reg", maxLength = 4),
	    @Field(name = "indExp", params = "S;N"),
	    @Field(name = "indCcrf", params = "S;N"),
	    @Field(name = "indComb", params = "S;N"),
	    @Field(name = "indUsina", params = "S;N"),
	    @Field(name = "indVa", params = "S;N"),
	    @Field(name = "indEe", params = "S;N"),
	    @Field(name = "indCart", params = "S;N"),
	    @Field(name = "indForm", params = "S;N"),
	    @Field(name = "indAer", params = "S;N"),
	    @Field(name = "indGiaf1", params = "S;N", minVersion = 13),
	    @Field(name = "indGiaf3", params = "S;N", minVersion = 13),
	    @Field(name = "indGiaf4", params = "S;N", minVersion = 13),
	    @Field(name = "indRestRessarcComplIcms", params = "S;N", minVersion = 14)
})
public class Reg1010 {
	
	private final String reg = "1010";
	private Boolean indExp;
    private Boolean indCcrf;
    private Boolean indComb;
    private Boolean indUsina;
    private Boolean indVa;
    private Boolean indEe;
    private Boolean indCart;
    private Boolean indForm;
    private Boolean indAer;
    private Boolean indGiaf1;
    private Boolean indGiaf3;
    private Boolean indGiaf4;
    private Boolean indRestRessarcComplIcms;
	
    public String getReg() {
		return reg;
	}

	public Boolean getIndExp() {
		return indExp;
	}

	public void setIndExp(Boolean indExp) {
		this.indExp = indExp;
	}

	public Boolean getIndCcrf() {
		return indCcrf;
	}

	public void setIndCcrf(Boolean indCcrf) {
		this.indCcrf = indCcrf;
	}

	public Boolean getIndComb() {
		return indComb;
	}

	public void setIndComb(Boolean indComb) {
		this.indComb = indComb;
	}

	public Boolean getIndUsina() {
		return indUsina;
	}

	public void setIndUsina(Boolean indUsina) {
		this.indUsina = indUsina;
	}

	public Boolean getIndVa() {
		return indVa;
	}

	public void setIndVa(Boolean indVa) {
		this.indVa = indVa;
	}

	public Boolean getIndEe() {
		return indEe;
	}

	public void setIndEe(Boolean indEe) {
		this.indEe = indEe;
	}

	public Boolean getIndCart() {
		return indCart;
	}

	public void setIndCart(Boolean indCart) {
		this.indCart = indCart;
	}

	public Boolean getIndForm() {
		return indForm;
	}

	public void setIndForm(Boolean indForm) {
		this.indForm = indForm;
	}

	public Boolean getIndAer() {
		return indAer;
	}

	public void setIndAer(Boolean indAer) {
		this.indAer = indAer;
	}

	public Boolean getIndGiaf1() {
		return indGiaf1;
	}

	public void setIndGiaf1(Boolean indGiaf1) {
		this.indGiaf1 = indGiaf1;
	}

	public Boolean getIndGiaf3() {
		return indGiaf3;
	}

	public void setIndGiaf3(Boolean indGiaf3) {
		this.indGiaf3 = indGiaf3;
	}

	public Boolean getIndGiaf4() {
		return indGiaf4;
	}

	public void setIndGiaf4(Boolean indGiaf4) {
		this.indGiaf4 = indGiaf4;
	}

	public Boolean getIndRestRessarcComplIcms() {
		return indRestRessarcComplIcms;
	}

	public void setIndRestRessarcComplIcms(Boolean indRestRessarcComplIcms) {
		this.indRestRessarcComplIcms = indRestRessarcComplIcms;
	}

}
