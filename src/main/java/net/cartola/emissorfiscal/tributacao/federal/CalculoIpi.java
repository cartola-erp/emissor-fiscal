package net.cartola.emissorfiscal.tributacao.federal;

import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.documento.DocumentoFiscalItem;
import net.cartola.emissorfiscal.tributacao.CalculoImposto;
import net.cartola.emissorfiscal.tributacao.Imposto;

@Service
public class CalculoIpi {
	
	private static final Logger LOG = Logger.getLogger(CalculoIpi.class.getName());
	
	public CalculoImposto calculaIpi(DocumentoFiscalItem di, TributacaoFederal tributacao) {
		LOG.log(Level.INFO, "Calculando o IPI para o ITEM: {0} ", di);
		CalculoImposto ipi = new CalculoImposto();
		BigDecimal valorTotal = di.getQuantidade().multiply(di.getValorUnitario());
		BigDecimal valorIpiBase = tributacao.getIpiBase().multiply(valorTotal);
		BigDecimal valorIpi = valorIpiBase.multiply(tributacao.getIpiAliquota());
		ipi.setImposto(Imposto.IPI);
		ipi.setAliquota(tributacao.getIpiAliquota());
		ipi.setBaseDeCalculo(valorIpiBase);
//		pis.setOrdem(di.getId().intValue()); // -> mudar
		ipi.setQuantidade(di.getQuantidade());
		ipi.setValorUnitario(di.getValorUnitario());
		ipi.setValor(valorIpi);
		di.setIpiAliquota(tributacao.getIpiAliquota());
		di.setIpiBase(valorIpiBase);
		di.setIpiValor(valorIpi);
		di.setIpiCst(tributacao.getIpiCst());
		return ipi;
	}
	
}
