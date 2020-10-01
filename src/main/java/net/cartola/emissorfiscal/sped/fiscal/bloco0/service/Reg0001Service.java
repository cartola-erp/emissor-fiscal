package net.cartola.emissorfiscal.sped.fiscal.bloco0.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.sped.fiscal.MontaGrupoRegistroSimples;
import net.cartola.emissorfiscal.sped.fiscal.MovimentacoesMensalIcmsIpi;
import net.cartola.emissorfiscal.sped.fiscal.bloco0.Reg0001AberturaDoBloco;

/**
 * 21/09/2020
 * @author robson.costa
 */
@Service
class Reg0001Service implements MontaGrupoRegistroSimples<Reg0001AberturaDoBloco, MovimentacoesMensalIcmsIpi> {

	private static final Logger LOG = Logger.getLogger(Reg0001Service.class.getName());
	
	@Override
	public Reg0001AberturaDoBloco montarGrupoDeRegistroSimples(MovimentacoesMensalIcmsIpi movimentacoesMensalIcmsIpi) {
		// TODO Auto-generated method stub
		LOG.log(Level.INFO, "Montando a abertura do BLOCO 0 (Reg0001) ");
		
		return null;
	}

}
