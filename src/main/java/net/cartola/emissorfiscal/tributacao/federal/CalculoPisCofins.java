package net.cartola.emissorfiscal.tributacao.federal;

import static net.cartola.emissorfiscal.tributacao.Imposto.COFINS;
import static net.cartola.emissorfiscal.tributacao.Imposto.PIS;

import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.documento.DocumentoFiscal;
import net.cartola.emissorfiscal.documento.DocumentoFiscalItem;
import net.cartola.emissorfiscal.documento.IndicadorDeOperacao;
import net.cartola.emissorfiscal.tributacao.CalculoImposto;

/**
 * 
 * Nova regra para a BC do PIS/COFINS  (Começou a rodar nesse sistema na transição do mês 10/21 para 11/21 ), agora é: <br>
 * 
 * OBS: Somente para as saídas
 * <b> BC PIS/COFINS = (Quantidade Item * Valor Unitario) - ICMS VALOR <\b> Antes era <br>
 * 
 * BC PIS/COFINS = (Quantidade Item * Valor Unitario) 
 * 
 * @author robson.costa
 * 
 */
@Service
public class CalculoPisCofins {

	private static final Logger LOG = Logger.getLogger(CalculoPisCofins.class.getName());
	
	public CalculoImposto calculaPis(DocumentoFiscal documentoFiscal, DocumentoFiscalItem di, TributacaoFederal tributacao) {
		LOG.log(Level.INFO, "Calculando o PIS para o ITEM: {0} ", di);
		CalculoImposto pis = new CalculoImposto(PIS);
		BigDecimal valorTotal = di.getQuantidade().multiply(di.getValorUnitario());
		BigDecimal valorPisBase = tributacao.getPisBase().multiply(valorTotal);
		if (isDocumentoDeSaida(documentoFiscal)) {
				valorPisBase = valorPisBase.subtract(di.getIcmsValor());					// Nova regra
		}
		BigDecimal valorPis = valorPisBase.multiply(tributacao.getPisAliquota());
		pis.setAliquota(tributacao.getPisAliquota());
		pis.setBaseDeCalculo(valorPisBase);
//		pis.setOrdem(di.getId().intValue()); // -> mudar
		pis.setQuantidade(di.getQuantidade());
		pis.setValorUnitario(di.getValorUnitario());
		pis.setValor(valorPis);
		di.setPisAliquota(tributacao.getPisAliquota());
		di.setPisBase(valorPisBase);
		di.setPisValor(valorPis);
		di.setPisCst(tributacao.getPisCst());
		return pis;
	}

	private boolean isDocumentoDeSaida(DocumentoFiscal documentoFiscal) {
		return IndicadorDeOperacao.SAIDA.equals(documentoFiscal.getTipoOperacao());
	}

	public CalculoImposto calculaCofins(DocumentoFiscal documentoFiscal, DocumentoFiscalItem di, TributacaoFederal tributacao) {
		LOG.log(Level.INFO, "Calculando o COFINS para o ITEM: {0} ", di);
		CalculoImposto cofins = new CalculoImposto(COFINS);
		BigDecimal valorTotal = di.getQuantidade().multiply(di.getValorUnitario());
		BigDecimal valorCofinsBase = tributacao.getCofinsBase().multiply(valorTotal);
		if (isDocumentoDeSaida(documentoFiscal)) {
			valorCofinsBase = valorCofinsBase.subtract(di.getIcmsValor());			// Nova regra
		}
		BigDecimal valorCofins = valorCofinsBase.multiply(tributacao.getCofinsAliquota());
		cofins.setAliquota(tributacao.getCofinsAliquota());
		cofins.setBaseDeCalculo(valorCofinsBase);
//		cofins.setOrdem(di.getId().intValue()); // -> mudar
		cofins.setQuantidade(di.getQuantidade());
		cofins.setValorUnitario(di.getValorUnitario());
		cofins.setValor(valorCofins);
		di.setCofinsAliquota(tributacao.getCofinsAliquota());
		di.setCofinsBase(valorCofinsBase);
		di.setCofinsValor(valorCofins);
		di.setCofinsCst(tributacao.getCofinsCst());
		return cofins;
	}
}
