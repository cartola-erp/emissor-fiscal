
package net.cartola.emissorfiscal.sped.fiscal.bloco0.service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.sped.fiscal.MontaGrupoDeRegistroList;
import net.cartola.emissorfiscal.sped.fiscal.MovimentacoesMensalIcmsIpi;
import net.cartola.emissorfiscal.sped.fiscal.bloco0.Reg0450;

/**
 * 21/09/2020
 * @author robson.costa
 */
@Service
class Reg0450Service implements MontaGrupoDeRegistroList<Reg0450, MovimentacoesMensalIcmsIpi> {

	private static final Logger LOG = Logger.getLogger(Reg0450Service.class.getName());
	
	@Override
	public List<Reg0450> montarGrupoDeRegistro(MovimentacoesMensalIcmsIpi movimentacoesMensalIcmsIpi) {
		// TODO Auto-generated method stub
		LOG.log(Level.INFO, "Montando o Grupo de Registro 0450 ");

		return null;
	}

}