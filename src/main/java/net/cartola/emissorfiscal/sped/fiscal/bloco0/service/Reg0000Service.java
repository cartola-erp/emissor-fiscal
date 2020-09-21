package net.cartola.emissorfiscal.sped.fiscal.bloco0.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.sped.fiscal.MontaGrupoRegistroSimples;
import net.cartola.emissorfiscal.sped.fiscal.MovimentacoesMensalIcmsIpi;
import net.cartola.emissorfiscal.sped.fiscal.bloco0.Reg0000AberturaArquivoDigital;

/**
 * 18/09/2020
 * @author robson.costa
 */
@Service
class Reg0000Service implements MontaGrupoRegistroSimples<Reg0000AberturaArquivoDigital, MovimentacoesMensalIcmsIpi> {

	private static final Logger LOG = Logger.getLogger(Reg0000Service.class.getName());
	
	@Override
	public Reg0000AberturaArquivoDigital montarGrupoDeRegistroSimples(MovimentacoesMensalIcmsIpi movimentacoesMensalIcmsIpi) {
		// TODO Auto-generated method stub
		LOG.log(Level.INFO, "Montando a abertura do arquivo digital (Reg0000) ");
		
		return null;
	}

	
	
}
