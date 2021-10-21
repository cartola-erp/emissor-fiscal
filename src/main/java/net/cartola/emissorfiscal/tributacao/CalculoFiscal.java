package net.cartola.emissorfiscal.tributacao;

import net.cartola.emissorfiscal.devolucao.Devolucao;
import net.cartola.emissorfiscal.documento.DocumentoFiscal;

public interface CalculoFiscal {

	void calculaImposto(DocumentoFiscal documentoFiscal);
	
	DocumentoFiscal calculaImposto(DocumentoFiscal documentoFiscal, Devolucao devolucao);

}
