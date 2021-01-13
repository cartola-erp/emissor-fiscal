package net.cartola.emissorfiscal.sped.fiscal.blocoC.service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.sped.fiscal.MontaGrupoDeRegistroList;
import net.cartola.emissorfiscal.sped.fiscal.MovimentacoesMensalIcmsIpi;
import net.cartola.emissorfiscal.sped.fiscal.blocoC.RegC800;

/**
 * 18/09/2020
 * @author robson.costa
 */
@Service
class RegC800Service implements MontaGrupoDeRegistroList<RegC800, MovimentacoesMensalIcmsIpi> {

	private static final Logger LOG = Logger.getLogger(RegC800Service.class.getName());


	@Override
	public List<RegC800> montarGrupoDeRegistro(MovimentacoesMensalIcmsIpi movimentosIcmsIpi) {
		// TODO Auto-generated method stub
		LOG.log(Level.INFO, "Montando o Registro C800");
		
		
		return null;
	}
	
	

}
