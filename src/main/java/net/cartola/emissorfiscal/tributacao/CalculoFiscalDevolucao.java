package net.cartola.emissorfiscal.tributacao;

import net.cartola.emissorfiscal.devolucao.Devolucao;
import net.cartola.emissorfiscal.documento.DocumentoFiscal;

/**
 * @date 29 de set. de 2021
 * @author robson.costa
 */
public interface CalculoFiscalDevolucao extends CalculoFiscal {

	DocumentoFiscal calculaImposto(DocumentoFiscal documentoFiscal, Devolucao devolucao);

}
