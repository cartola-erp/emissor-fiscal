package net.cartola.emissorfiscal.tributacao.estadual;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.documento.DocumentoFiscal;
import net.cartola.emissorfiscal.documento.DocumentoFiscalItem;
import net.cartola.emissorfiscal.ncm.Ncm;
import net.cartola.emissorfiscal.tributacao.CalculoFiscal;
import net.cartola.emissorfiscal.tributacao.CalculoImposto;
import net.cartola.emissorfiscal.tributacao.Imposto;

@Service
public class CalculoFiscalEstadual implements CalculoFiscal {
	
	@Autowired
	TributacaoEstadualRepository tributacaoEstadualRepository;
	
	@Autowired
	CalculoIcms calculoIcms;

	
	/**
	 * O calculo de imposto retornado aqui é do TOTAL DA NFE (aparentemente)
	 */
	@Override
	public void calculaImposto(DocumentoFiscal documentoFiscal) {
		List<CalculoImposto> listImpostos = new ArrayList<>();
		Set<Ncm> ncms = documentoFiscal.getItens().stream().map(DocumentoFiscalItem::getNcm).collect(Collectors.toSet());
//		List<TributacaoEstadual> listTributacoes = tributacaoEstadualRepository.findByNcmIn(ncms);
		
		Map<Ncm, TributacaoEstadual> mapaTributacoes = ncms.stream()
				.collect(Collectors.toMap(ncm -> ncm, ncm -> tributacaoEstadualRepository.findByNcm(ncm).get(0)));
//		.collect(Collectors.toMap(ncm -> ncm, ncm -> listTributacoes.stream().filter(icms -> icms.getNcm().getId().equals(ncm.)));

		documentoFiscal.getItens().forEach(docItem -> {
			TributacaoEstadual tributacao = mapaTributacoes.get(docItem.getNcm());
			listImpostos.add(calculoIcms.calculaIcms(docItem, tributacao));
		
		});
		
		setaIcmsBaseEValor(documentoFiscal, listImpostos);
	}
	
	private void setaIcmsBaseEValor(DocumentoFiscal documentoFiscal, List<CalculoImposto> listImpostos) {
		documentoFiscal.setIcmsBase(documentoFiscal.getItens().stream().map(DocumentoFiscalItem::getValorUnitario)
				.reduce(BigDecimal.ZERO, BigDecimal::add));
		documentoFiscal.setIcmsValor(totaliza(listImpostos.stream().filter(icms -> icms.getImposto().equals(Imposto.ICMS))
				.collect(Collectors.toList())));
	}
	
	/**
	 * Calcula a soma do ICMS para os itens
	 * @param documentoFiscal
	 */
	private BigDecimal totaliza(List<CalculoImposto> listImpostos) {
		BigDecimal[] icmsTotal = {BigDecimal.ZERO};
		listImpostos.stream().forEach(icms -> {
			icmsTotal[0] = icmsTotal[0].add(icms.getValorUnitario());
		});
		return icmsTotal[0];
	}
	

}
