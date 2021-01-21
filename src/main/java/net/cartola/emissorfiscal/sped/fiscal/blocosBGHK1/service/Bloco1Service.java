package net.cartola.emissorfiscal.sped.fiscal.blocosBGHK1.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.sped.fiscal.MontaBloco;
import net.cartola.emissorfiscal.sped.fiscal.MovimentoMensalIcmsIpi;
import net.cartola.emissorfiscal.sped.fiscal.bloco1.Bloco1;

/**
 * 21/09/2020
 * @author robson.costa
 */
@Service
public class Bloco1Service implements MontaBloco<Bloco1, MovimentoMensalIcmsIpi> {

	private static final Logger LOG = Logger.getLogger(Bloco1Service.class.getName());

	@Override
	public Bloco1 criarBloco(MovimentoMensalIcmsIpi movimentacoesMensalIcmsIpi) {
		// TODO Auto-generated method stub
		LOG.log(Level.INFO, "Montando o BLOCO 1, com INICIO em: {0} e TERMINO: {1} ", movimentacoesMensalIcmsIpi.getDataInicio());

		return null;
	}


}
