package net.cartola.emissorfiscal.sped.fiscal.blocosBGHK1.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.sped.fiscal.MontaBloco;
import net.cartola.emissorfiscal.sped.fiscal.MovimentoMensalIcmsIpi;
import net.cartola.emissorfiscal.sped.fiscal.blocoK.BlocoK;
import net.cartola.emissorfiscal.sped.fiscal.blocoK.RegK990EncerramentoDoBlocoK;
import net.cartola.emissorfiscal.util.RecordCounter;

/**
 * 21/09/2020
 * @author robson.costa
 */
@Service
public class BlocoKService implements MontaBloco<BlocoK, MovimentoMensalIcmsIpi> {

	private static final Logger LOG = Logger.getLogger(BlocoKService.class.getName());

	@Override
	public BlocoK criarBloco(MovimentoMensalIcmsIpi movimentoMensalIcmsIpi) {
		// TODO Auto-generated method stub
		LOG.log(Level.INFO, "Montando o BLOCO K, com INICIO em: {0} e TERMINO: {1} ", movimentoMensalIcmsIpi.getDataInicio());

		BlocoK blocoK = new BlocoK();
		
		blocoK.setRegK990(montarEncerramentoDoBlocoK(blocoK));
		
		LOG.log(Level.INFO, "Montagem do BLOCO K, TEMINADA! {0} " ,blocoK);
		return blocoK;
	}

	private RegK990EncerramentoDoBlocoK montarEncerramentoDoBlocoK(BlocoK blocoK) {
		LOG.log(Level.INFO, "Montando o Registro K990 (Encerramento do Bloco K) ");

		long qtdLinK = RecordCounter.count(blocoK);
		RegK990EncerramentoDoBlocoK regK990 = new RegK990EncerramentoDoBlocoK(qtdLinK + 1);
		
		LOG.log(Level.INFO, "Encerramento do BLOCO K (RegK990), terminada: {0} " ,regK990);
		return regK990;
	}

}
