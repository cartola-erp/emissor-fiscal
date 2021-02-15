package net.cartola.emissorfiscal.sped.fiscal.blocosBGHK1.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.sped.fiscal.MontaBloco;
import net.cartola.emissorfiscal.sped.fiscal.MovimentoMensalIcmsIpi;
import net.cartola.emissorfiscal.sped.fiscal.blocoH.BlocoH;
import net.cartola.emissorfiscal.sped.fiscal.blocoH.RegH990EncerramentoDoBlocoH;
import net.cartola.emissorfiscal.util.RecordCounter;

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
		BlocoH blocoH = new BlocoH();
		
		
		blocoH.setRegH990(montarEncerramentoDoBlocoH(blocoH));

		LOG.log(Level.INFO, "Montagem do BLOCO H, TEMINADA! {0} " ,blocoH);
		return blocoH;
	}

	private RegH990EncerramentoDoBlocoH montarEncerramentoDoBlocoH(BlocoH blocoH) {
		LOG.log(Level.INFO, "Montando o Registro H990 (Encerramento do Bloco H) ");

		long qtdLinH = RecordCounter.count(blocoH);
		RegH990EncerramentoDoBlocoH regH990 = new RegH990EncerramentoDoBlocoH(qtdLinH + 1);
		
		LOG.log(Level.INFO, "Encerramento do BLOCO H (RegH990), terminada: {0} " ,regH990);
		return regH990;
	}


}
