package net.cartola.emissorfiscal.sped.fiscal;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
/**
 * Super Classe do REG C 197 e D197 
 * Pode ser do C597 também (Mas aparentemente nunca é escriturado esse registro no nosso caso)
 * 
 * @author robson.costa
 */
public abstract class OutrasObrigacoesEAjustes {

	protected String codAj;
    protected String descrComplAj;
    protected String codItem;
    protected BigDecimal vlBcIcms;
    protected BigDecimal aliqIcms;
    protected BigDecimal vlIcms;
    protected BigDecimal vlOutros;
    
}
