package net.cartola.emissorfiscal.tributacao;

import net.cartola.emissorfiscal.documento.DocumentoFiscal;

public interface CalculoFiscal {

	void calculaImposto(DocumentoFiscal documentoFiscal);
}
