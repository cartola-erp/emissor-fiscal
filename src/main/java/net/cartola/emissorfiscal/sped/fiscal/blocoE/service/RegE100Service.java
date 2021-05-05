package net.cartola.emissorfiscal.sped.fiscal.blocoE.service;

import java.util.List;

import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.sped.fiscal.MontaGrupoDeRegistroList;
import net.cartola.emissorfiscal.sped.fiscal.MovimentoMensalIcmsIpi;
import net.cartola.emissorfiscal.sped.fiscal.blocoE.RegE100;

/**
 * @autor robson.costa
 * @data 5 de mai. de 2021
 */
@Service
class RegE100Service implements MontaGrupoDeRegistroList<RegE100, MovimentoMensalIcmsIpi> {

	@Override
	public List<RegE100> montarGrupoDeRegistro(MovimentoMensalIcmsIpi movimentosIcmsIpi) {
		// TODO Auto-generated method stub
		
		return null;
	}

}
