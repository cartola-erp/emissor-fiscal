package net.cartola.emissorfiscal.sped.fiscal.blocoC.service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.sped.fiscal.MontaGrupoDeRegistroList;
import net.cartola.emissorfiscal.sped.fiscal.MovimentoMensalIcmsIpi;
import net.cartola.emissorfiscal.sped.fiscal.blocoC.RegC350;

/**
 * 18/09/2020
 * @author robson.costa
 */
@Service
class RegC350Service implements MontaGrupoDeRegistroList<RegC350, MovimentoMensalIcmsIpi> {

	private static final Logger LOG = Logger.getLogger(RegC350Service.class.getName());


	@Override
	public List<RegC350> montarGrupoDeRegistro(MovimentoMensalIcmsIpi movimentosIcmsIpi) {
		// TODO Auto-generated method stub
		LOG.log(Level.INFO, "Montando o Registro C350");
		
		
		return null;
	}
	
	

}
