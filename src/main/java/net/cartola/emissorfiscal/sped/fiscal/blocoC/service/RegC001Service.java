package net.cartola.emissorfiscal.sped.fiscal.blocoC.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.sped.fiscal.MontaGrupoRegistroSimples;
import net.cartola.emissorfiscal.sped.fiscal.MovimentacoesMensalIcmsIpi;
import net.cartola.emissorfiscal.sped.fiscal.blocoC.RegC001AberturaDoBlocoC;

/**
 * 18/09/2020
 * @author robson.costa
 */
@Service
class RegC001Service implements MontaGrupoRegistroSimples<RegC001AberturaDoBlocoC, MovimentacoesMensalIcmsIpi> {

	private static final Logger LOG = Logger.getLogger(RegC001Service.class.getName());
	
	
	@Override
	public RegC001AberturaDoBlocoC montarGrupoDeRegistroSimples(MovimentacoesMensalIcmsIpi movimentacoesMensalIcmsIpi) {
		// TODO Auto-generated method stub
		LOG.log(Level.INFO, "Montando a abertura do bloco C ");

		return null;
	}


}
