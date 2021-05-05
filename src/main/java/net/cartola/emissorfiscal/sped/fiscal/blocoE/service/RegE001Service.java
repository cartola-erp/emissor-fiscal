package net.cartola.emissorfiscal.sped.fiscal.blocoE.service;

import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.sped.fiscal.MontaGrupoRegistroSimples;
import net.cartola.emissorfiscal.sped.fiscal.MovimentoMensalIcmsIpi;
import net.cartola.emissorfiscal.sped.fiscal.blocoE.RegE001AberturaDoBlocoE;

/**
 * @autor robson.costa
 * @data 5 de mai. de 2021
 */
@Service
class RegE001Service implements MontaGrupoRegistroSimples<RegE001AberturaDoBlocoE, MovimentoMensalIcmsIpi> {

	@Override
	public RegE001AberturaDoBlocoE montarGrupoDeRegistroSimples(MovimentoMensalIcmsIpi movimentosIcmsIpi) {
		// TODO Auto-generated method stub
		
		return null;
	}

	
}
