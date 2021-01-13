
package net.cartola.emissorfiscal.sped.fiscal.bloco0.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.sped.fiscal.MontaGrupoRegistroSimples;
import net.cartola.emissorfiscal.sped.fiscal.MovimentacoesMensalIcmsIpi;
import net.cartola.emissorfiscal.sped.fiscal.bloco0.Reg0990EncerramentoDoBloco;

/**
 * 21/09/2020
 * @author robson.costa
 */
@Service
class Reg0990Service implements MontaGrupoRegistroSimples<Reg0990EncerramentoDoBloco, MovimentacoesMensalIcmsIpi> {

	private static final Logger LOG = Logger.getLogger(Reg0990Service.class.getName());
	
	@Override
	public Reg0990EncerramentoDoBloco montarGrupoDeRegistroSimples(MovimentacoesMensalIcmsIpi movimentosIcmsIpi) {
		// TODO Auto-generated method stub
		LOG.log(Level.INFO, "Montando o Grupo de Registro 0990 (Encerramento do Bloco 0) ");

		return null;
	}

}
