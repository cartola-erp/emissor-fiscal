package net.cartola.emissorfiscal.properties;

import lombok.Getter;
import lombok.Setter;

/**
 * @data 14 de mai. de 2021
 * @author robson.costa
 */
@Getter
@Setter
public class SpedFiscalProperties {

	
//	private static Long VENDA_INTERESTADUAL_NAO_CONTRIBUINTE = 3;
//	@ConfigurationProperties(value = "venda-interestadual-nao-contribuinte")
	private Long codVendaInterestadualNaoContribuinte;
	
	private boolean informarDescontoEntrada;
	private boolean informarDescontoSaida;
	
	
}
