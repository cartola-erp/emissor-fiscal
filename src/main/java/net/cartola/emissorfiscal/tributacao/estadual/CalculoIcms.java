package net.cartola.emissorfiscal.tributacao.estadual;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.documento.DocumentoFiscalItem;

@Service
public class CalculoIcms {

	public Map<String, String> calcula(DocumentoFiscalItem di, TributacaoEstadual tributacao) {
		BigDecimal valorTotal = di.getQuantidade().multiply(di.getValorUnitario());
		BigDecimal valorIcmsBase = tributacao.getIcmsBase().multiply(valorTotal);
		BigDecimal valorIcms = valorIcmsBase.multiply(tributacao.getIcmsAliquota());
		di.setIcmsBase(valorIcmsBase);
		di.setIcmsValor(valorIcms);
		return null;
	}
	
	

}
