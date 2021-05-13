package net.cartola.emissorfiscal.sped.fiscal.blocoE.service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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

	private static final Logger LOG = Logger.getLogger(RegE100Service.class.getName());
	
	@Override
	public List<RegE100> montarGrupoDeRegistro(MovimentoMensalIcmsIpi movimentosIcmsIpi) {
		LOG.log(Level.INFO, "Montando o Registro E100");
		List<RegE100> listE100 = new ArrayList<>();
		RegE100 regE100 = new RegE100();
		regE100.setDtIni(movimentosIcmsIpi.getDataInicio());
		regE100.setDtFin(movimentosIcmsIpi.getDataFim());
		listE100.add(regE100);
		
		LOG.log(Level.INFO, "Registro E100, terminado. REG E100: ");
		return listE100;
	}

}
