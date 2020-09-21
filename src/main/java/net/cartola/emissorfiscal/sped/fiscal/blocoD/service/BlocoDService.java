package net.cartola.emissorfiscal.sped.fiscal.blocoD.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.sped.fiscal.MontaBloco;
import net.cartola.emissorfiscal.sped.fiscal.MovimentacoesMensalIcmsIpi;
import net.cartola.emissorfiscal.sped.fiscal.bloco0.service.Bloco0Service;
import net.cartola.emissorfiscal.sped.fiscal.blocoD.BlocoD;

/**
 * 18/09/2020
 * @author robson.costa
 */
@Service
public class BlocoDService implements MontaBloco<BlocoD, MovimentacoesMensalIcmsIpi> {

	private static final Logger LOG = Logger.getLogger(BlocoDService.class.getName());
	
	@Override
	public BlocoD criarBloco(MovimentacoesMensalIcmsIpi movimentacoesMensalIcmsIpi) {
		// TODO Auto-generated method stub
		LOG.log(Level.INFO, "Montando o bloco D, com INICIO em: {0} e TERMINO: {1} ", movimentacoesMensalIcmsIpi.getDataInicio());

		return null;
	}

		
}
