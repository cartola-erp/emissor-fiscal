package net.cartola.emissorfiscal.sped.fiscal.blocoD.service;

import java.util.List;

import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.documento.DocumentoFiscal;
import net.cartola.emissorfiscal.sped.fiscal.blocoD.RegD190;

/**
 * @autor robson.costa
 * @data 6 de mai. de 2021
 */
@Service
class RegD190Service {

	// CFOP POSSIVEIS: "DE -> PARA"
	// 6353 -> 2353 (CTE Fora do estado)
	// 5353 -> 1353 (cte dentro do estado) 
	
	
	
	
	public List<RegD190> montarGrupoRegC190(DocumentoFiscal servicoTransporte) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
	
}
