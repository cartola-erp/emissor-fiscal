package net.cartola.emissorfiscal.tributacao.federal;

import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.devolucao.DevolucaoItem;
import net.cartola.emissorfiscal.documento.DocumentoFiscalItem;
import net.cartola.emissorfiscal.tributacao.CalculoImposto;
import net.cartola.emissorfiscal.tributacao.Imposto;
import net.cartola.emissorfiscal.tributacao.estadual.CalculoIcmsDevolucao;

@Service
public class CalculoIpi {
	
	private static final Logger LOG = Logger.getLogger(CalculoIpi.class.getName());
	
	@Autowired
	private CalculoIcmsDevolucao calculoIcmsDevolucao;
	
	public CalculoImposto calculaIpi(DocumentoFiscalItem di, TributacaoFederal tributacao) {
		LOG.log(Level.INFO, "Calculando o IPI para o ITEM: {0} - X/s: {1} {2}", new Object[]{di.getItem(), di.getCodigoX(), di.getCodigoSequencia()} );
		CalculoImposto ipi = new CalculoImposto(Imposto.IPI);
		BigDecimal valorTotal = di.getQuantidade().multiply(di.getValorUnitario());
		BigDecimal valorIpiBase = tributacao.getIpiBase().multiply(valorTotal);
		BigDecimal valorIpi = valorIpiBase.multiply(tributacao.getIpiAliquota());
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

	public CalculoImposto calculaIpi(DocumentoFiscalItem di, DevolucaoItem devoItem) {
		LOG.log(Level.INFO, "Calculando o IPI da devolução para o ITEM {0} - X/s: {1} {2}", new Object[]{di.getItem(), di.getCodigoX(), di.getCodigoSequencia()});
		CalculoImposto ipi = new CalculoImposto(Imposto.IPI);
		
		BigDecimal valorIpiDevolvido = calcularIpiDevolvido(devoItem);

		ipi.setAliquota(devoItem.getIpiAliquota());
//		ipi.
		ipi.setQuantidade(di.getQuantidade());
		ipi.setValorUnitario(di.getValorUnitario());
		ipi.setValor(valorIpiDevolvido);
		
		di.setIpiAliquota(devoItem.getIpiAliquota());
//		di.setIpiBase(valorIpiDevolvido);
		di.setIpiValor(valorIpiDevolvido);
	
		return ipi;
	}
	

	private BigDecimal calcularIpiDevolvido(DevolucaoItem devoItem) {
		BigDecimal valorIpiDevolvido = calculoIcmsDevolucao.calcularIcmsBase(devoItem).multiply(devoItem.getIpiAliquota());
		return valorIpiDevolvido;
	}
	
}
