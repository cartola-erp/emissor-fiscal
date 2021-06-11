package net.cartola.emissorfiscal.sped.fiscal;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * Essa classe é (pode ser) a SuperClasse dos registros: C190, C320, C390, C490, C590, C690, C790, C850, C890, D190, D300, D390, D410, D590, D690, D696;
 *
 * Atualmente ela só é SuperClasse dos dos Registros que escrituramos, que os Registros:
 * 
 * C190, C590, C850, D190, D590
 *
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
