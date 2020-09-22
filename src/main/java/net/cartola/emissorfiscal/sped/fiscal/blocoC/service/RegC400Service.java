package net.cartola.emissorfiscal.sped.fiscal.blocoC.service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.sped.fiscal.MontaGrupoDeRegistroList;
import net.cartola.emissorfiscal.sped.fiscal.MovimentacoesMensalIcmsIpi;
import net.cartola.emissorfiscal.sped.fiscal.blocoC.RegC400;

/**
 * 18/09/2020
 * @author robson.costa
 */
@Service
class RegC400Service implements MontaGrupoDeRegistroList<RegC400, MovimentacoesMensalIcmsIpi> {

	private static final Logger LOG = Logger.getLogger(RegC400Service.class.getName());

	
	@Override
	public List<RegC400> montarGrupoDeRegistro(MovimentacoesMensalIcmsIpi movimentacoesMensalIcmsIpi) {
		// TODO Auto-generated method stub
		LOG.log(Level.INFO, "Montando o Registro C400");
		
		
		return null;
	}
	
	

}
