package net.cartola.emissorfiscal.sped.fiscal.blocosBGHK19.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.sped.fiscal.MontaBloco;
import net.cartola.emissorfiscal.sped.fiscal.MovimentoMensalIcmsIpi;
import net.cartola.emissorfiscal.sped.fiscal.blocoB.BlocoB;
import net.cartola.emissorfiscal.sped.fiscal.blocoB.RegB990EncerramentoDoBlocoB;
import net.cartola.emissorfiscal.util.RecordCounter;

/**
 * 18/09/2020
 * @author robson.costa
 */
@Service
public class BlocoBService implements MontaBloco<BlocoB, MovimentoMensalIcmsIpi> {
	
	private static final Logger LOG = Logger.getLogger(BlocoBService.class.getName());

	@Autowired
	private RegB001Service regB001Service;
	
	
	@Override
	public BlocoB criarBloco(MovimentoMensalIcmsIpi movimentosIcmsIpi) {
		LOG.log(Level.INFO, "Montando o BLOCO B, com INICIO em: {0} e TERMINO: {1} ", movimentosIcmsIpi.getDataInicio());
		BlocoB blocoB = new BlocoB();

		blocoB.setRegB001(regB001Service.montarGrupoDeRegistroSimples(movimentosIcmsIpi));
		blocoB.setRegB990(montarEncerramentoDoBlocoB(blocoB));
		
		LOG.log(Level.INFO, "Montagem do BLOCO B, TERMINADA! {0} " ,blocoB);
		return blocoB;
	}


	private RegB990EncerramentoDoBlocoB montarEncerramentoDoBlocoB(BlocoB blocoB) {
		LOG.log(Level.INFO, "Montando o Registro 0990 (Encerramento do Bloco 0) ");

		long qtdLinB = RecordCounter.count(blocoB);
		RegB990EncerramentoDoBlocoB regB990 = new RegB990EncerramentoDoBlocoB(qtdLinB + 1);
		
		LOG.log(Level.INFO, "Encerramento do BLOCO B (RegB990), terminada: {0} " ,regB990);
		return regB990;
	}


}
