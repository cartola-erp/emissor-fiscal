package net.cartola.emissorfiscal.sped.fiscal.blocoE.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.sped.fiscal.MontaGrupoRegistroSimples;
import net.cartola.emissorfiscal.sped.fiscal.MovimentoMensalIcmsIpi;
import net.cartola.emissorfiscal.sped.fiscal.blocoE.RegE001AberturaDoBlocoE;
import net.cartola.emissorfiscal.sped.fiscal.enums.IndicadorDeMovimento;

/**
 * @autor robson.costa
 * @data 5 de mai. de 2021
 */
@Service
class RegE001Service implements MontaGrupoRegistroSimples<RegE001AberturaDoBlocoE, MovimentoMensalIcmsIpi> {

	private static final Logger LOG = Logger.getLogger(RegE001Service.class.getName());

	
	@Override
	public RegE001AberturaDoBlocoE montarGrupoDeRegistroSimples(MovimentoMensalIcmsIpi movimentosIcmsIpi) {
		LOG.log(Level.INFO, "Montando a abertura do bloco E ");
		RegE001AberturaDoBlocoE regE001 = new RegE001AberturaDoBlocoE(IndicadorDeMovimento.BLOCO_COM_DADOS_INFORMADOS);
		LOG.log(Level.INFO, "Abertura do bloco E   (RegE001), terminada {0} " ,regE001);
		return regE001;
	}

	
}
