package net.cartola.emissorfiscal.sped.fiscal.blocosBGHK1.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.sped.fiscal.MontaGrupoRegistroSimples;
import net.cartola.emissorfiscal.sped.fiscal.MovimentoMensalIcmsIpi;
import net.cartola.emissorfiscal.sped.fiscal.blocoB.RegB001AberturaDoBloco;
import net.cartola.emissorfiscal.sped.fiscal.enums.IndicadorDeMovimento;

@Service
public class RegB001Service implements MontaGrupoRegistroSimples<RegB001AberturaDoBloco, MovimentoMensalIcmsIpi> {

	private static final Logger LOG = Logger.getLogger(RegB001Service.class.getName());
	
	@Override
	public RegB001AberturaDoBloco montarGrupoDeRegistroSimples(MovimentoMensalIcmsIpi movimentosIcmsIpi) {
		LOG.log(Level.INFO, "Montando a abertura do BLOCO B (RegB001) ");
		
		RegB001AberturaDoBloco regB001 = new RegB001AberturaDoBloco();
		regB001.setIndDad(IndicadorDeMovimento.BLOCO_SEM_DADOS_INFORMADOS);
		
		LOG.log(Level.INFO, "Abertura do BLOCO 0 (RegB001), terminada: {0} " ,regB001);
		return regB001;
	}

}
