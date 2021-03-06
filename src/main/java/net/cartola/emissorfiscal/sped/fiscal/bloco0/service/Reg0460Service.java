
package net.cartola.emissorfiscal.sped.fiscal.bloco0.service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.sped.fiscal.MontaGrupoDeRegistroList;
import net.cartola.emissorfiscal.sped.fiscal.MovimentoMensalIcmsIpi;
import net.cartola.emissorfiscal.sped.fiscal.bloco0.Reg0460;

/**
 * 21/09/2020
 * @author robson.costa
 */
@Service
class Reg0460Service implements MontaGrupoDeRegistroList<Reg0460, MovimentoMensalIcmsIpi> {

	private static final Logger LOG = Logger.getLogger(Reg0460Service.class.getName());
	
	@Override
	public List<Reg0460> montarGrupoDeRegistro(MovimentoMensalIcmsIpi movimentosIcmsIpi) {
		// TODO Auto-generated method stub
		LOG.log(Level.INFO, "Montando o Grupo de Registro 0460 ");

		return null;
	}

}
