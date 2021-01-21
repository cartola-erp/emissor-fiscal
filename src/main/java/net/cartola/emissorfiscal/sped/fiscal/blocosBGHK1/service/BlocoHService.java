package net.cartola.emissorfiscal.sped.fiscal.blocosBGHK1.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.sped.fiscal.MontaBloco;
import net.cartola.emissorfiscal.sped.fiscal.MovimentoMensalIcmsIpi;
import net.cartola.emissorfiscal.sped.fiscal.blocoH.BlocoH;

/**
 * 21/09/2020
 * @author robson.costa
 */
@Service
public class BlocoHService implements MontaBloco<BlocoH, MovimentoMensalIcmsIpi> {
	
	private static final Logger LOG = Logger.getLogger(BlocoHService.class.getName());

	@Override
	public BlocoH criarBloco(MovimentoMensalIcmsIpi movimentoMensalIcmsIpi) {
		// TODO Auto-generated method stub
		LOG.log(Level.INFO, "Montando o BLOCO H, com INICIO em: {0} e TERMINO: {1} ", movimentoMensalIcmsIpi.getDataInicio());

		return null;
	}


}
