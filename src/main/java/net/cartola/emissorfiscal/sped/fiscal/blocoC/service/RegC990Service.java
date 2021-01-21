package net.cartola.emissorfiscal.sped.fiscal.blocoC.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.sped.fiscal.MontaGrupoRegistroSimples;
import net.cartola.emissorfiscal.sped.fiscal.MovimentoMensalIcmsIpi;
import net.cartola.emissorfiscal.sped.fiscal.blocoC.RegC990EncerramentoDoBlocoC;

/**
 * 18/09/2020
 * @author robson.costa
 */
@Service
class RegC990Service implements MontaGrupoRegistroSimples<RegC990EncerramentoDoBlocoC, MovimentoMensalIcmsIpi> {

	private static final Logger LOG = Logger.getLogger(RegC990Service.class.getName());
	
	
	@Override
	public RegC990EncerramentoDoBlocoC montarGrupoDeRegistroSimples(MovimentoMensalIcmsIpi movimentosIcmsIpi) {
		// TODO Auto-generated method stub
		LOG.log(Level.INFO, "Montando o encerramento do bloco C ");

		return null;
	}


}
