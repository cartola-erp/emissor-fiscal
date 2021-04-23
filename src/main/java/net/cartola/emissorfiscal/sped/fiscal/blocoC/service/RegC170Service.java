package net.cartola.emissorfiscal.sped.fiscal.blocoC.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.documento.DocumentoFiscal;
import net.cartola.emissorfiscal.sped.fiscal.blocoC.RegC170;

@Service
//class RegC170Service implements MontaGrupoDeRegistroList<RegC170, RegC170> {
class RegC170Service  {

	
	public List<RegC170> montarGrupoRegC170(DocumentoFiscal docFisc) {
		List<RegC170> listRegC170 = new ArrayList<>();
		
		docFisc.getItens().stream().forEachOrdered(item -> {
			RegC170 regC170 = new RegC170(docFisc, item);
			listRegC170.add(regC170);
		});
		return listRegC170;
	}

	
}
