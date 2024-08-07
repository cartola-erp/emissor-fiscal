package net.cartola.emissorfiscal.sped.fiscal.blocoC.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.sped.fiscal.MontaBloco;
import net.cartola.emissorfiscal.sped.fiscal.MovimentoMensalIcmsIpi;
import net.cartola.emissorfiscal.sped.fiscal.blocoC.BlocoC;
import net.cartola.emissorfiscal.sped.fiscal.blocoC.RegC990EncerramentoDoBlocoC;
import net.cartola.emissorfiscal.util.RecordCounter;

/**
 * 18/09/2020
 * @author robson.costa
 */
@Service
public class BlocoCService implements MontaBloco<BlocoC, MovimentoMensalIcmsIpi> {

	private static final Logger LOG = Logger.getLogger(BlocoCService.class.getName());
	
	@Autowired
	private RegC001Service regC001Service;
	
	@Autowired
	private RegC100Service regC100Service;
	
	@Autowired
	private RegC350Service regC350Service;
	
	@Autowired
	private RegC400Service regC400Service;
	
	@Autowired
	private RegC500Service regC500Service;
	
	@Autowired
	private RegC800Service regC800Service;
	
	@Override
	public BlocoC criarBloco(MovimentoMensalIcmsIpi movimentoMensalIcmsIpi) {
		// TODO Auto-generated method stub
		LOG.log(Level.INFO, "Montando o BLOCO C, com INICIO em: {0} e TERMINO: {1} ", movimentoMensalIcmsIpi.getDataInicio());
		BlocoC blocoC = new BlocoC();
		
		/**
		 * TODO (Para ver/pensar melhor)
		 * Ver se eu tenho que fazer alguma validação aqui. Ex.: irá preecher registro X para aquele Mês?
		 * Tentar capturar possiveis erros.: Ex o famosão -> @NullPointerException
		 */
		
		blocoC.setRegC001(regC001Service.montarGrupoDeRegistroSimples(movimentoMensalIcmsIpi));
		blocoC.setRegC100(regC100Service.montarGrupoDeRegistro(movimentoMensalIcmsIpi));
//		blocoC.setRegC350(regC350Service.montarGrupoDeRegistro(movimentoMensalIcmsIpi));
//		blocoC.setRegC400(regC400Service.montarGrupoDeRegistro(movimentoMensalIcmsIpi));
		blocoC.setRegC500(regC500Service.montarGrupoDeRegistro(movimentoMensalIcmsIpi));
		blocoC.setRegC800(regC800Service.montarGrupoDeRegistro(movimentoMensalIcmsIpi));
		
		blocoC.setRegC990(montarEncerramentoDoBloco0(blocoC));
		
		LOG.log(Level.INFO, "Montagem do BLOCO C, TEMINADA! {0} " ,blocoC);
		return blocoC;
	}


	private RegC990EncerramentoDoBlocoC montarEncerramentoDoBloco0(BlocoC blocoC) {
		LOG.log(Level.INFO, "Montando o encerramento do bloco C ");

		long qtdLinC = RecordCounter.count(blocoC);
		RegC990EncerramentoDoBlocoC regC990 = new RegC990EncerramentoDoBlocoC(qtdLinC + 1);
		
		LOG.log(Level.INFO, "Encerramento do BLOCO C (RegC990), terminada: {0} " ,regC990);
		return regC990;
	}


	
}
