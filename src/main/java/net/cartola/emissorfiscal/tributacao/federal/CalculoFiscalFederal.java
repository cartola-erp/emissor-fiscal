package net.cartola.emissorfiscal.tributacao.federal;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.documento.DocumentoFiscal;
import net.cartola.emissorfiscal.documento.DocumentoFiscalItem;
import net.cartola.emissorfiscal.documento.Finalidade;
import net.cartola.emissorfiscal.ncm.Ncm;
import net.cartola.emissorfiscal.tributacao.CalculoFiscal;
import net.cartola.emissorfiscal.tributacao.CalculoImposto;
import net.cartola.emissorfiscal.tributacao.Imposto;

@Service
public class CalculoFiscalFederal implements CalculoFiscal {

	private static final Logger LOG = Logger.getLogger(CalculoFiscalFederal.class.getName());
	
	@Autowired
	private TributacaoFederalService tributacaoFederalService;

	@Autowired
	private CalculoPisCofins calculoPisCofins;

	@Autowired
	private CalculoIpi calculoIpi;
	
	@Override
	public void calculaImposto(DocumentoFiscal documentoFiscal) {
		LOG.log(Level.INFO, "Fazendo o calculo das TRIBUTAÇÕES FEDERAIS, para o Documento = {0} ", documentoFiscal.getDocumento());
		List<CalculoImposto> listaImpostos = new LinkedList<>();
		Set<Ncm> ncms = documentoFiscal.getItens().stream().map(DocumentoFiscalItem::getNcm)
				.collect(Collectors.toSet());
		Set<Finalidade> finalidades = documentoFiscal.getItens().stream().map(DocumentoFiscalItem::getFinalidade).collect(Collectors.toSet());

		Set<TributacaoFederal> tributacoesFederais = tributacaoFederalService
				.findTributacaoFederalByVariosNcmsEOperacaoEFinalidadeERegimeTributario(documentoFiscal.getOperacao(), documentoFiscal.getEmitente().getRegimeTributario(), finalidades, ncms);
		
		Map<Ncm, TributacaoFederal> mapaTributacoesPorNcm = ncms.stream()
				.collect(Collectors.toMap(ncm -> ncm,
						ncm -> tributacoesFederais.stream()
								.filter(tributacaoFederal -> tributacaoFederal.getNcm().getId().equals(ncm.getId()))
								.findAny().get()));

		documentoFiscal.getItens().stream().forEach(di -> {
			TributacaoFederal tributacao = mapaTributacoesPorNcm.get(di.getNcm());
			listaImpostos.add(calculoPisCofins.calculaPis(di, tributacao));
			listaImpostos.add(calculoPisCofins.calculaCofins(di, tributacao));
			listaImpostos.add(calculoIpi.calculaIpi(di, tributacao));
		});

		setaPisBaseValor(documentoFiscal, listaImpostos);
		setaCofinsBaseValor(documentoFiscal, listaImpostos);
		setaIpiBaseValor(documentoFiscal, listaImpostos);
	}

	private void setaPisBaseValor(DocumentoFiscal documentoFiscal, List<CalculoImposto> listaImpostos) {
		LOG.log(Level.INFO, "Totalizando o PIS BASE e o VALOR para o DocumentoFiscal: {0} ", documentoFiscal);
		documentoFiscal.setPisBase(documentoFiscal.getItens().stream().map(DocumentoFiscalItem::getPisBase)
				.reduce(BigDecimal.ZERO, BigDecimal::add));
		documentoFiscal.setPisValor(totaliza(listaImpostos.stream()
				.filter(pis -> pis.getImposto().equals(Imposto.PIS)).collect(Collectors.toList())));
	}

	private void setaCofinsBaseValor(DocumentoFiscal documentoFiscal, List<CalculoImposto> listaImpostos) {
		LOG.log(Level.INFO, "Totalizando o COFINS BASE e o VALOR para o DocumentoFiscal: {0} ", documentoFiscal);
		documentoFiscal.setCofinsBase(documentoFiscal.getItens().stream().map(DocumentoFiscalItem::getCofinsBase)
				.reduce(BigDecimal.ZERO, BigDecimal::add));
		documentoFiscal.setCofinsValor(totaliza(listaImpostos.stream()
				.filter(cofins -> cofins.getImposto().equals(Imposto.COFINS)).collect(Collectors.toList())));
	}
	
	private void setaIpiBaseValor(DocumentoFiscal documentoFiscal, List<CalculoImposto> listaImpostos) {
		LOG.log(Level.INFO, "Totalizando o IPI BASE e o VALOR para o DocumentoFiscal: {0} ", documentoFiscal);
		documentoFiscal.setIpiBase(documentoFiscal.getItens().stream().map(DocumentoFiscalItem::getIpiBase)
				.reduce(BigDecimal.ZERO, BigDecimal::add));
		documentoFiscal.setIpiValor(totaliza(listaImpostos.stream()
				.filter(ipi -> ipi.getImposto().equals(Imposto.IPI)).collect(Collectors.toList())));
	}
	
	private BigDecimal totaliza(List<CalculoImposto> listaImposto) {
		return listaImposto.stream().map(CalculoImposto::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);
	}
	
}
