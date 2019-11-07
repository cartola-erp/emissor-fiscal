package net.cartola.emissorfiscal.tributacao.federal;

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
	public List<CalculoImposto> calculaImposto(DocumentoFiscal documentoFiscal) {
		List<CalculoImposto> listaImpostos = new LinkedList<>();
		CalculoImposto pis = new CalculoImposto();
		pis.setImposto(Imposto.PIS);
		Set<Ncm> ncms = documentoFiscal.getItens().stream().map(DocumentoFiscalItem::getNcm)
				.collect(Collectors.toSet());

		Map<Ncm, TributacaoFederal> mapaTributacoesPorNcm = ncms.stream()
				.collect(Collectors.toMap(ncm -> ncm, ncm -> tributacaoFederalRepository.findByNcm(ncm).get(0)));
		
		documentoFiscal.getItens().stream().forEach(di -> {
			TributacaoFederal tributacao = mapaTributacoesPorNcm.get(di.getNcm());
			calculoPisCofins.calculaPis(di, tributacao);
		});
		
		return listaImpostos;
	}
}
