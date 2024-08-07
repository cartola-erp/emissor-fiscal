package net.cartola.emissorfiscal.sped.fiscal.blocosBGHK19.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.sped.fiscal.MontaBloco;
import net.cartola.emissorfiscal.sped.fiscal.MovimentoMensalIcmsIpi;
import net.cartola.emissorfiscal.sped.fiscal.bloco1.Bloco1;
import net.cartola.emissorfiscal.sped.fiscal.bloco1.Reg1001AberturaDoBloco1;
import net.cartola.emissorfiscal.sped.fiscal.bloco1.Reg1010;
import net.cartola.emissorfiscal.sped.fiscal.bloco1.Reg1990EncerramentoDoBloco1;
import net.cartola.emissorfiscal.sped.fiscal.enums.IndicadorDeMovimento;
import net.cartola.emissorfiscal.util.RecordCounter;

/**
 * 21/09/2020
 * @author robson.costa
 */
@Service
public class Bloco1Service implements MontaBloco<Bloco1, MovimentoMensalIcmsIpi> {

	private static final Logger LOG = Logger.getLogger(Bloco1Service.class.getName());

	@Override
	public Bloco1 criarBloco(MovimentoMensalIcmsIpi movimentacoesMensalIcmsIpi) {
		LOG.log(Level.INFO, "Montando o BLOCO 1, com INICIO em: {0} e TERMINO: {1} ", movimentacoesMensalIcmsIpi.getDataInicio());

		Bloco1 bloco1 = new Bloco1();
		bloco1.setReg1001(new Reg1001AberturaDoBloco1(IndicadorDeMovimento.BLOCO_COM_DADOS_INFORMADOS));
		bloco1.setReg1010(montarGrupoDeRegistroSimples());
		bloco1.setReg1990(montarEncerramentoDoBloco0(bloco1));
		
		LOG.log(Level.INFO, "Montagem do BLOCO 1, TEMINADA! {0} " ,bloco1);
		return bloco1;
	}

	private Reg1010 montarGrupoDeRegistroSimples() {
		Reg1010 reg1010 = new Reg1010();
		return reg1010;
	}

	private Reg1990EncerramentoDoBloco1 montarEncerramentoDoBloco0(Bloco1 bloco1) {
		LOG.log(Level.INFO, "Montando o Registro 1990 (Encerramento do Bloco 1) ");

		long qtdLin1 = RecordCounter.count(bloco1);
		Reg1990EncerramentoDoBloco1 reg1990 = new Reg1990EncerramentoDoBloco1(qtdLin1 + 1);
		
		LOG.log(Level.INFO, "Encerramento do BLOCO 1 (Reg1990), terminada: {0} " ,reg1990);
		return reg1990;
	}
	


}
