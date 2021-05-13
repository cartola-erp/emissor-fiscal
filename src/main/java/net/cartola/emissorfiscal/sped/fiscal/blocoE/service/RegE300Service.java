package net.cartola.emissorfiscal.sped.fiscal.blocoE.service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.sped.fiscal.MontaGrupoDeRegistroList;
import net.cartola.emissorfiscal.sped.fiscal.MovimentoMensalIcmsIpi;
import net.cartola.emissorfiscal.sped.fiscal.blocoE.RegE100;
import net.cartola.emissorfiscal.sped.fiscal.blocoE.RegE300;

/**
 * @autor robson.costa
 * @data 5 de mai. de 2021
 */
@Service
class RegE300Service implements MontaGrupoDeRegistroList<RegE300, MovimentoMensalIcmsIpi> {

	private static final Logger LOG = Logger.getLogger(RegE300Service.class.getName());

	
	@Override
	public List<RegE300> montarGrupoDeRegistro(MovimentoMensalIcmsIpi movimentosIcmsIpi) {
		// TODO Auto-generated method stub
		LOG.log(Level.INFO, "Montando o Registro E300");
		List<RegE300> listE300 = new ArrayList<>();
//		RegE100 regE100 = new RegE100();
//		regE100.setDtIni(movimentosIcmsIpi.getDataInicio());
//		regE100.setDtFin(movimentosIcmsIpi.getDataFim());
//		listE100.add(regE100);
		
		LOG.log(Level.INFO, "Registro E300, terminado. REG E300: ");
		return null;
	}
	
	
	
}
