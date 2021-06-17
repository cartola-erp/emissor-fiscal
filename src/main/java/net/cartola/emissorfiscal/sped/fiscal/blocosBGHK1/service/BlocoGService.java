package net.cartola.emissorfiscal.sped.fiscal.blocosBGHK1.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.sped.fiscal.MontaBloco;
import net.cartola.emissorfiscal.sped.fiscal.MovimentoMensalIcmsIpi;
import net.cartola.emissorfiscal.sped.fiscal.bloco0.Reg0001AberturaDoBloco;
import net.cartola.emissorfiscal.sped.fiscal.blocoG.BlocoG;
import net.cartola.emissorfiscal.sped.fiscal.blocoG.RegG001AberturaDoBlocoG;
import net.cartola.emissorfiscal.sped.fiscal.blocoG.RegG990EncerramentoDoBlocoG;
import net.cartola.emissorfiscal.sped.fiscal.enums.IndicadorDeMovimento;
import net.cartola.emissorfiscal.util.RecordCounter;

/**
 * 21/09/2020
 * @author robson.costa
 */
@Service
public class BlocoGService implements MontaBloco<BlocoG, MovimentoMensalIcmsIpi> {
	
	private static final Logger LOG = Logger.getLogger(BlocoGService.class.getName());

	@Override
	public BlocoG criarBloco(MovimentoMensalIcmsIpi movimentoMensalIcmsIpi) {
		// TODO Auto-generated method stub
		LOG.log(Level.INFO, "Montando o BLOCO G, com INICIO em: {0} e TERMINO: {1} ", movimentoMensalIcmsIpi.getDataInicio());
		BlocoG blocoG = new BlocoG();
		
		RegG001AberturaDoBlocoG regG001 = new RegG001AberturaDoBlocoG(IndicadorDeMovimento.BLOCO_SEM_DADOS_INFORMADOS);
		blocoG.setRegG001(regG001);
		blocoG.setRegG990(montarEncerramentoDoBlocoG(blocoG));
		
		LOG.log(Level.INFO, "Montagem do BLOCO G, TEMINADA! {0} " ,blocoG);
		return blocoG;
	}

	private RegG990EncerramentoDoBlocoG montarEncerramentoDoBlocoG(BlocoG blocoG) {
		LOG.log(Level.INFO, "Montando o Registro G990 (Encerramento do Bloco G) ");

		long qtdLinG = RecordCounter.count(blocoG);
		RegG990EncerramentoDoBlocoG regG990 = new RegG990EncerramentoDoBlocoG(qtdLinG + 1);
		
		LOG.log(Level.INFO, "Encerramento do BLOCO G (RegG990), terminada: {0} " ,regG990);
		return regG990;
	}
	
	
	

}
