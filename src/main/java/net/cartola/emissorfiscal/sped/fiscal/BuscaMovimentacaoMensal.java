package net.cartola.emissorfiscal.sped.fiscal;

import java.time.LocalDate;

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
	
	T buscarMovimentacoesDoPeriodo(Loja loja, Long contadorId, Long inventarioId, LocalDate dataInicio, LocalDate dataFim);
	
}
