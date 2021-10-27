package net.cartola.emissorfiscal.tributacao.estadual;

import net.cartola.emissorfiscal.devolucao.Devolucao;
import net.cartola.emissorfiscal.documento.DocumentoFiscal;
import net.cartola.emissorfiscal.tributacao.CalculoFiscal;

/**
 * @date 27 de out. de 2021
 * @author robson.costa
 */
public interface CalculoFiscalDevolucao extends CalculoFiscal {

	DocumentoFiscal calculaImposto(DocumentoFiscal documentoFiscal, Devolucao devolucao);
	
}
