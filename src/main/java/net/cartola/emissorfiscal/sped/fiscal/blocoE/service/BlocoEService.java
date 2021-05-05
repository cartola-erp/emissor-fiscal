package net.cartola.emissorfiscal.sped.fiscal.blocoE.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.sped.fiscal.MontaBloco;
import net.cartola.emissorfiscal.sped.fiscal.MovimentoMensalIcmsIpi;
import net.cartola.emissorfiscal.sped.fiscal.blocoE.BlocoE;
import net.cartola.emissorfiscal.sped.fiscal.blocoE.RegE100;
import net.cartola.emissorfiscal.sped.fiscal.blocoE.RegE300;
import net.cartola.emissorfiscal.sped.fiscal.blocoE.RegE990EncerramentoDoBlocoE;
import net.cartola.emissorfiscal.util.RecordCounter;

/**
 * 21/09/2020
 * @author robson.costa
 */
@Service
public class BlocoEService implements MontaBloco<BlocoE, MovimentoMensalIcmsIpi> {

	private static final Logger LOG = Logger.getLogger(BlocoEService.class.getName());
	
	@Autowired
	private RegE001Service regE001Service;

	@Autowired
	private RegE100Service regE100Service;
	
	@Autowired
	private RegE300Service regE300Service;
	
//	@Autowired
//	private RegE
	
	@Override
	public BlocoE criarBloco(MovimentoMensalIcmsIpi movimentoMensalIcmsIpi) {
		// TODO Auto-generated method stub
		LOG.log(Level.INFO, "Montando o bloco E, com INICIO em: {0} e TERMINO: {1} ", movimentoMensalIcmsIpi.getDataInicio());
		
		BlocoE blocoE = new BlocoE();
		
		blocoE.setRegE001(regE001Service.montarGrupoDeRegistroSimples(movimentoMensalIcmsIpi));
		blocoE.setRegE100(regE100Service.montarGrupoDeRegistro(movimentoMensalIcmsIpi));
		blocoE.setRegE300(regE300Service.montarGrupoDeRegistro(movimentoMensalIcmsIpi));
		blocoE.setRegE990(montarEncerramentoDoBlocoE(blocoE));
		
		
		LOG.log(Level.INFO, "Montagem do BLOCO E, TEMINADA! {0} " ,blocoE);
		return blocoE;
	}

	private RegE990EncerramentoDoBlocoE montarEncerramentoDoBlocoE(BlocoE blocoE) {
		LOG.log(Level.INFO, "Montando o encerramento do bloco E ");

		long qtdLinE = RecordCounter.count(blocoE);
		RegE990EncerramentoDoBlocoE regE990 = new RegE990EncerramentoDoBlocoE(qtdLinE + 1);
		
		LOG.log(Level.INFO, "Encerramento do BLOCO E (RegE990), terminada: {0} " ,regE990);
		return regE990;
	}

	
}
