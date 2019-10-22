package net.cartola.emissorfiscal.tributacao;

import java.util.Map;

import net.cartola.emissorfiscal.documento.DocumentoFiscal;

public interface CalculoFiscal {
	
	Map<String, String> calculaImposto(DocumentoFiscal documentoFiscal);
}
