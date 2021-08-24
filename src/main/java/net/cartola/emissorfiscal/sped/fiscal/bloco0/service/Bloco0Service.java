package net.cartola.emissorfiscal.sped.fiscal.bloco0.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.sped.fiscal.MontaBloco;
import net.cartola.emissorfiscal.sped.fiscal.MovimentoMensalIcmsIpi;
import net.cartola.emissorfiscal.sped.fiscal.bloco0.Bloco0;
import net.cartola.emissorfiscal.sped.fiscal.bloco0.Reg0990EncerramentoDoBloco;
import net.cartola.emissorfiscal.util.RecordCounter;

/**
 * 18/09/2020
 * @author robson.costa
 */
@Service
public class Bloco0Service implements MontaBloco<Bloco0, MovimentoMensalIcmsIpi> {
	
	private static final Logger LOG = Logger.getLogger(Bloco0Service.class.getName());
	
	@Autowired
	private Reg0000Service reg0000Service;

	@Autowired
	private Reg0001Service reg0001Service;

	@Autowired
	private Reg0005Service reg0005Service;
	
	@Autowired
	private Reg0100Service reg0100Service;
	
	@Autowired
	private Reg0150Service reg0150Service;
	
	@Autowired
	private Reg0190Service reg0190Service;
	
	@Autowired
	private Reg0200Service reg0200Service;
	
	@Autowired
	private Reg0400Service reg0400Service;
	
	@Autowired
	private Reg0450Service reg0450Service;
	
	@Autowired
	private Reg0460Service reg0460Service;
	
	@Override
	public Bloco0 criarBloco(MovimentoMensalIcmsIpi movimentoMensalIcmsIpi) {
		// TODO Auto-generated method stub
		LOG.log(Level.INFO, "Montando o BLOCO 0, com INICIO em: {0} e TERMINO: {1} ", movimentoMensalIcmsIpi.getDataInicio());
		Bloco0 bloco0 = new Bloco0();
		
		/**
		 * TODO (Para ver/pensar melhor)
		 * Ver se eu tenho que fazer alguma validação aqui. Ex.: irá preecher registro X para aquele Mês?
		 * Tentar capturar possiveis erros.: Ex o famosão -> @NullPointerException
		 */
		
		bloco0.setReg0000(reg0000Service.montarGrupoDeRegistroSimples(movimentoMensalIcmsIpi));
		bloco0.setReg0001(reg0001Service.montarGrupoDeRegistroSimples(movimentoMensalIcmsIpi));
		bloco0.setReg0005(reg0005Service.montarGrupoDeRegistroSimples(movimentoMensalIcmsIpi));
		bloco0.setReg0100(reg0100Service.montarGrupoDeRegistroSimples(movimentoMensalIcmsIpi));
		bloco0.setReg0150(reg0150Service.montarGrupoDeRegistro(movimentoMensalIcmsIpi));
		bloco0.setReg0190(reg0190Service.montarGrupoDeRegistro(movimentoMensalIcmsIpi));
		bloco0.setReg0200(reg0200Service.montarGrupoDeRegistro(movimentoMensalIcmsIpi));
		bloco0.setReg0400(reg0400Service.montarGrupoDeRegistro(movimentoMensalIcmsIpi));
		bloco0.setReg0450(reg0450Service.montarGrupoDeRegistro(movimentoMensalIcmsIpi));
//		bloco0.setReg0460(reg0460Service.montarGrupoDeRegistro(movimentoMensalIcmsIpi));
//		bloco0.setReg0990(montarEncerramentoDoBloco0(bloco0));
		
		LOG.log(Level.INFO, "Montagem do BLOCO 0, TEMINADA! {0} " ,bloco0);
		return bloco0;
	}
	
	
	private Reg0990EncerramentoDoBloco montarEncerramentoDoBloco0(Bloco0 bloco0) {
		LOG.log(Level.INFO, "Montando o Registro 0990 (Encerramento do Bloco 0) ");

		long qtdLin0 = RecordCounter.count(bloco0);
		Reg0990EncerramentoDoBloco reg0990 = new Reg0990EncerramentoDoBloco(qtdLin0 + 1);
		
		LOG.log(Level.INFO, "Encerramento do BLOCO B (Reg0990), terminada: {0} " ,reg0990);
		return reg0990;
	}


	/**
	 * Irá montar o REG 0460 (que depende de informações dos BLOCO C e D);
	 * E o Encerramento do Bloco 0
	 * 
	 * @param bloco0
	 * @param movimentacoesMensalIcmsIpi
	 */
	public void mountReg0460(Bloco0 bloco0, MovimentoMensalIcmsIpi movimentacoesMensalIcmsIpi) {
		bloco0.setReg0460(reg0460Service.montarGrupoDeRegistro(movimentacoesMensalIcmsIpi));
		bloco0.setReg0990(montarEncerramentoDoBloco0(bloco0));
	}
}
