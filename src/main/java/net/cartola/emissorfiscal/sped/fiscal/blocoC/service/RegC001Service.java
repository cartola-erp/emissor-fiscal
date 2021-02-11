package net.cartola.emissorfiscal.sped.fiscal.blocoC.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.sped.fiscal.MontaGrupoRegistroSimples;
import net.cartola.emissorfiscal.sped.fiscal.MovimentoMensalIcmsIpi;
import net.cartola.emissorfiscal.sped.fiscal.blocoC.RegC001AberturaDoBlocoC;
import net.cartola.emissorfiscal.sped.fiscal.enums.IndicadorDeMovimento;

/**
 * 18/09/2020
 * @author robson.costa
 */
@Service
class RegC001Service implements MontaGrupoRegistroSimples<RegC001AberturaDoBlocoC, MovimentoMensalIcmsIpi> {

	private static final Logger LOG = Logger.getLogger(RegC001Service.class.getName());
	
	
	@Override
	public RegC001AberturaDoBlocoC montarGrupoDeRegistroSimples(MovimentoMensalIcmsIpi movimentosIcmsIpi) {
		LOG.log(Level.INFO, "Montando a abertura do bloco C ");
		RegC001AberturaDoBlocoC regC001 = new RegC001AberturaDoBlocoC(IndicadorDeMovimento.BLOCO_COM_DADOS_INFORMADOS);
		LOG.log(Level.INFO, "Abertura do bloco C   (RegC001), terminada {0} " ,regC001);
		return regC001;
	}


}
