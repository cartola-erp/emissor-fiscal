package net.cartola.emissorfiscal.sped.fiscal.blocoC.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.sped.fiscal.MontaBloco;
import net.cartola.emissorfiscal.sped.fiscal.MovimentacoesMensalIcmsIpi;
import net.cartola.emissorfiscal.sped.fiscal.bloco0.service.Bloco0Service;
import net.cartola.emissorfiscal.sped.fiscal.blocoC.BlocoC;

/**
 * 18/09/2020
 * @author robson.costa
 */
@Service
public class BlocoCService implements MontaBloco<BlocoC, MovimentacoesMensalIcmsIpi> {

	private static final Logger LOG = Logger.getLogger(BlocoCService.class.getName());

	
	@Override
	public BlocoC criarBloco(MovimentacoesMensalIcmsIpi movimentacoesMensalIcmsIpi) {
		// TODO Auto-generated method stub
		LOG.log(Level.INFO, "Montando o BLOCO C, com INICIO em: {0} e TERMINO: {1} ", movimentacoesMensalIcmsIpi.getDataInicio());

		return null;
	}


	
}
