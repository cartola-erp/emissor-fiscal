package net.cartola.emissorfiscal.sped.fiscal.blocoC.service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.sped.fiscal.MontaGrupoDeRegistroList;
import net.cartola.emissorfiscal.sped.fiscal.MovimentoMensalIcmsIpi;
import net.cartola.emissorfiscal.sped.fiscal.blocoC.RegC500;

/**
 * 18/09/2020
 * @author robson.costa
 */
@Service
class RegC500Service implements MontaGrupoDeRegistroList<RegC500, MovimentoMensalIcmsIpi> {

	private static final Logger LOG = Logger.getLogger(RegC500Service.class.getName());
	
	
	@Override
	public List<RegC500> montarGrupoDeRegistro(MovimentoMensalIcmsIpi movimentosIcmsIpi) {
		// TODO Auto-generated method stub
		LOG.log(Level.INFO, "Montando o Registro C500");
		
		
		return null;
	}
	
	

}
