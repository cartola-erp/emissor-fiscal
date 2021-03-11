package net.cartola.emissorfiscal.sped.fiscal.blocoC.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.documento.DocumentoFiscal;
import net.cartola.emissorfiscal.sped.fiscal.blocoC.RegC195;

@Service
class RegC195Service {
	
	@Autowired
	private RegC197Service regC197Service;
	

	public List<RegC195> montarGrupoRegC195(DocumentoFiscal docFisc) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
