package net.cartola.emissorfiscal.sped.fiscal;

import lombok.Getter;
import lombok.Setter;

/** 
 * Super Classe, dos REGISTROS -> C195 e D195
 * 
 * @date 6 de ago. de 2021
 * @author robson.costa
 * 
 */
@Getter
@Setter
public abstract class ObservacoesLancamentoFiscal {

	protected String codObs;
    protected String txtCompl;
		
}
