package net.cartola.emissorfiscal.tributacao.estadual;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.documento.DocumentoFiscal;
import net.cartola.emissorfiscal.documento.DocumentoFiscalItem;
import net.cartola.emissorfiscal.ncm.Ncm;
import net.cartola.emissorfiscal.tributacao.CalculoFiscal;

@Service
public class CalculoFiscalEstadual implements CalculoFiscal {
	
	@Autowired
	TributacaoEstadualRepository tributacaoEstadualRepository;

	@Override
	public Map<String, String> calculaImposto(DocumentoFiscal documentoFiscal) {
		Map<String, String> resultMap = new HashMap<>();
		Set<Ncm> ncms = documentoFiscal.getItens().stream().map(DocumentoFiscalItem::getNcm).collect(Collectors.toSet());
		return resultMap;
	}
	
	

}
