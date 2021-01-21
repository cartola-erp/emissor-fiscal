package net.cartola.emissorfiscal.sped.fiscal.bloco0.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.sped.fiscal.MontaGrupoRegistroSimples;
import net.cartola.emissorfiscal.sped.fiscal.MovimentoMensalIcmsIpi;
import net.cartola.emissorfiscal.sped.fiscal.bloco0.Reg0001AberturaDoBloco;
import net.cartola.emissorfiscal.sped.fiscal.enums.IndicadorDeMovimento;

/**
 * 21/09/2020
 * @author robson.costa
 */
@Service
class Reg0001Service implements MontaGrupoRegistroSimples<Reg0001AberturaDoBloco, MovimentoMensalIcmsIpi> {

	private static final Logger LOG = Logger.getLogger(Reg0001Service.class.getName());
	
	@Override
	public Reg0001AberturaDoBloco montarGrupoDeRegistroSimples(MovimentoMensalIcmsIpi movimentacoesMensalIcmsIpi) {
		LOG.log(Level.INFO, "Montando a abertura do BLOCO 0 (Reg0001) ");
		Reg0001AberturaDoBloco reg0001 = new Reg0001AberturaDoBloco();
		reg0001.setIndMov(IndicadorDeMovimento.BLOCO_COM_DADOS_INFORMADOS);
		
		LOG.log(Level.INFO, "Abertura do BLOCO 0 (Reg0001), terminada: {0} " ,reg0001);
		return reg0001;
	}

}
