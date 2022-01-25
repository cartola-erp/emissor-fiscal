package net.cartola.emissorfiscal.sped.fiscal;

import net.cartola.emissorfiscal.loja.Loja;

/**
 * @date 21 de jan. de 2021
 * @author robson.costa
 */
public interface BuscaMovimentacaoMensal<T> {

	/**
	 * PODEREI REAPROVEITAR essa INTERFACE quando eu estiver fazendo a EFD DO PIS/COFINS
	 * ???
	 */
	
	T buscarMovimentacoesDoPeriodo(MovimentoMensalParametrosBusca paramBuscaSped, Loja loja);

}
