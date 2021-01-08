package net.cartola.emissorfiscal.sped.fiscal.blocosBGHK1.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.sped.fiscal.MontaBloco;
import net.cartola.emissorfiscal.sped.fiscal.MovimentacoesMensalIcmsIpi;
import net.cartola.emissorfiscal.sped.fiscal.blocoB.BlocoB;

/**
 * 18/09/2020
 * @author robson.costa
 */
@Service
public class BlocoBService implements MontaBloco<BlocoB, MovimentacoesMensalIcmsIpi> {
	
	private static final Logger LOG = Logger.getLogger(BlocoBService.class.getName());

	@Override
	public BlocoB criarBloco(MovimentacoesMensalIcmsIpi movimentacoesMensalIcmsIpi) {
		// TODO Auto-generated method stub
		LOG.log(Level.INFO, "Montando o BLOCO B, com INICIO em: {0} e TERMINO: {1} ", movimentacoesMensalIcmsIpi.getDataInicio());

		return null;
	}


}