package net.cartola.emissorfiscal.sped.fiscal.blocoD.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.sped.fiscal.MontaBloco;
import net.cartola.emissorfiscal.sped.fiscal.MovimentoMensalIcmsIpi;
import net.cartola.emissorfiscal.sped.fiscal.blocoD.BlocoD;
import net.cartola.emissorfiscal.sped.fiscal.blocoD.RegD990EncerramentoDoBlocoD;
import net.cartola.emissorfiscal.util.RecordCounter;

/**
 * 18/09/2020
 * @author robson.costa
 */
@Service
public class BlocoDService implements MontaBloco<BlocoD, MovimentoMensalIcmsIpi> {

	private static final Logger LOG = Logger.getLogger(BlocoDService.class.getName());
	
	@Override
	public BlocoD criarBloco(MovimentoMensalIcmsIpi movimentoMensalIcmsIpi) {
		// TODO Auto-generated method stub
		LOG.log(Level.INFO, "Montando o bloco D, com INICIO em: {0} e TERMINO: {1} ", movimentoMensalIcmsIpi.getDataInicio());
		BlocoD blocoD = new BlocoD();
//		blocoD.setRegD001(regD001);
		
		blocoD.setRegD990(montarEncerramentoDoBlocoD(blocoD));
		
		LOG.log(Level.INFO, "Montagem do BLOCO D, TEMINADA! {0} " ,blocoD);
		return blocoD;
	}

	
	private RegD990EncerramentoDoBlocoD montarEncerramentoDoBlocoD(BlocoD blocoD) {
		LOG.log(Level.INFO, "Montando o encerramento do bloco D ");

		long qtdLinD = RecordCounter.count(blocoD);
		RegD990EncerramentoDoBlocoD regD990 = new RegD990EncerramentoDoBlocoD(qtdLinD + 1);
		
		LOG.log(Level.INFO, "Encerramento do BLOCO D (RegD990), terminada: {0} " ,regD990);
		return regD990;
	}

		
}
