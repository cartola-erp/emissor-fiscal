package net.cartola.emissorfiscal.sped.fiscal.blocosBGHK19.service;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.sped.fiscal.MontaGrupoDeRegistroList;
import net.cartola.emissorfiscal.sped.fiscal.MovimentoMensalIcmsIpi;
import net.cartola.emissorfiscal.sped.fiscal.blocoH.RegH005;

/**
 * @date 10 de nov. de 2021
 * @author robson.costa
 */
@Service
public class RegH005Service implements MontaGrupoDeRegistroList<RegH005, MovimentoMensalIcmsIpi> {

	private static final Logger LOG = Logger.getLogger(RegH005Service.class.getName());
	
	@Override
	public List<RegH005> montarGrupoDeRegistro(MovimentoMensalIcmsIpi movimentosIcmsIpi) {
		// TODO Auto-generated method stub
		LOG.log(Level.INFO, "Montando o Registro H005");
		List<RegH005> listRegH005 = new LinkedList<>();
		
		
		return listRegH005;
	}

}
