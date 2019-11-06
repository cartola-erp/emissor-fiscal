package net.cartola.emissorfiscal.tributacao;

import net.cartola.emissorfiscal.documento.DocumentoFiscal;

public interface CalculoFiscal {
	
	CalculoImposto calculaImposto(DocumentoFiscal documentoFiscal);
}
