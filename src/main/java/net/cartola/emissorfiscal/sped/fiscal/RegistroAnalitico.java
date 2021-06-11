package net.cartola.emissorfiscal.sped.fiscal;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

/**
 * @data 11 de jun. de 2021
 * @author robson.costa
 */
@Getter
@Setter
public abstract class RegistroAnalitico {
		
	protected String cstIcms;
	protected int cfop;
	protected BigDecimal aliqIcms;
	protected BigDecimal vlOpr;
	protected BigDecimal vlBcIcms;
	protected BigDecimal vlIcms;

	
}
