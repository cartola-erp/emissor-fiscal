package net.cartola.emissorfiscal.tributacao.federal;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import net.cartola.emissorfiscal.documento.DocumentoFiscal;
import net.cartola.emissorfiscal.documento.DocumentoFiscalItem;
import net.cartola.emissorfiscal.ncm.Ncm;
import net.cartola.emissorfiscal.tributacao.CalculoFiscal;
import net.cartola.emissorfiscal.tributacao.CalculoImposto;
import net.cartola.emissorfiscal.tributacao.Imposto;

public class CalculoFiscalFederal implements CalculoFiscal {

	@Autowired
	private TributacaoFederalRepository tributacaoFederalRepository;

	@Autowired
	private CalculoPisCofins calculoPisCofins;

	@Autowired
	private CalculoIpi calculoIpi;

	@Override
	public void calculaImposto(DocumentoFiscal documentoFiscal) {
		List<CalculoImposto> listaImpostos = new LinkedList<>();
		Set<Ncm> ncms = documentoFiscal.getItens().stream().map(DocumentoFiscalItem::getNcm)
				.collect(Collectors.toSet());

		Map<Ncm, TributacaoFederal> mapaTributacoesPorNcm = ncms.stream()
				.collect(Collectors.toMap(ncm -> ncm, ncm -> tributacaoFederalRepository.findByNcm(ncm).get(0)));

		documentoFiscal.getItens().stream().forEach(di -> {
			TributacaoFederal tributacao = mapaTributacoesPorNcm.get(di.getNcm());
			listaImpostos.add(calculoPisCofins.calculaPis(di, tributacao));
			listaImpostos.add(calculoPisCofins.calculaCofins(di, tributacao));
		});
		
		documentoFiscal.setIpiBase(totaliza(listaImpostos.stream().filter(ipi -> ipi.getImposto().equals(Imposto.IPI)).collect(Collectors.toList())));
	}

	private BigDecimal totaliza(List<CalculoImposto> collect) {
		// TODO Auto-generated method stub
		return null;
	}
}
