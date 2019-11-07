package net.cartola.emissorfiscal.tributacao;

import java.util.List;

import net.cartola.emissorfiscal.documento.DocumentoFiscal;

public interface CalculoFiscal {
	
	List<CalculoImposto> calculaImposto(DocumentoFiscal documentoFiscal);
}
