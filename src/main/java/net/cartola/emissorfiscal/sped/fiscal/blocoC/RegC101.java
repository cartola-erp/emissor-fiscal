package net.cartola.emissorfiscal.sped.fiscal.blocoC;

import java.math.BigDecimal;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;

/**
 * 02/09/2020
 * @author robson.costa
 * 
 * REGISTRO C101: INFORMAÇÃO COMPLEMENTAR DOS DOCUMENTOS FISCAIS QUANDO DAS
 * OPERAÇÕES INTERESTADUAIS DESTINADAS A CONSUMIDOR FINAL NÃO CONTRIBUINTE EC 87/15
 *
 */
@Record(fields = { 
	@Field(name = "reg", maxLength = 4),
	@Field(name = "vlFcpUfDest"),
	@Field(name = "vlIcmsUfDest"),
	@Field(name = "vlIcmsUfRem")
})
public class RegC101 {
	
	private final String reg = "C101";
	private BigDecimal vlFcpUfDest;
	private BigDecimal vlIcmsUfDest;
	private BigDecimal vlIcmsUfRem;
	
	public String getReg() {
		return reg;
	}

	public BigDecimal getVlFcpUfDest() {
		return vlFcpUfDest;
	}

	public void setVlFcpUfDest(BigDecimal vlFcpUfDest) {
		this.vlFcpUfDest = vlFcpUfDest;
	}

	public BigDecimal getVlIcmsUfDest() {
		return vlIcmsUfDest;
	}

	public void setVlIcmsUfDest(BigDecimal vlIcmsUfDest) {
		this.vlIcmsUfDest = vlIcmsUfDest;
	}

	public BigDecimal getVlIcmsUfRem() {
		return vlIcmsUfRem;
	}

	public void setVlIcmsUfRem(BigDecimal vlIcmsUfRem) {
		this.vlIcmsUfRem = vlIcmsUfRem;
	}
	
}
