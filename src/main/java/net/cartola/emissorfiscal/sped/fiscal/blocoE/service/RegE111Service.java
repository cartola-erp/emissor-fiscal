package net.cartola.emissorfiscal.sped.fiscal.blocoE.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.model.sped.fiscal.difal.SpedFiscalRegE310;
import net.cartola.emissorfiscal.model.sped.fiscal.icms.propria.SpedFiscalRegE110;
import net.cartola.emissorfiscal.model.sped.fiscal.icms.propria.SpedFiscalRegE110Service;
import net.cartola.emissorfiscal.model.sped.fiscal.icms.propria.SpedFiscalRegE111;
import net.cartola.emissorfiscal.model.sped.fiscal.icms.propria.SpedFiscalRegE111Service;
import net.cartola.emissorfiscal.sped.fiscal.MovimentoMensalIcmsIpi;
import net.cartola.emissorfiscal.sped.fiscal.blocoE.RegE111;

/**
 * @date 6 de jul. de 2021
 * @author robson.costa
 */
@Service
class RegE111Service {

	
	@Autowired // usarei para buscar se tem registros gerardos/preenchidos pelo usuarioo no periodo
	private SpedFiscalRegE110Service spedFiscRegE110Service;
	
	
	@Autowired // (salvos os registros gerados usando a service abaixo)
	private SpedFiscalRegE111Service spedFiscRegE111Service;
	
	/**
	 * Aqui é onde, de fato que eu tenho que tentar "prever" as situações que acontece/aconteceu no mês:
	 * 	
	 * - Ao menos as mais comuns acredito que eu consigo montar a lógica Ex.::
	 * 
	 * 	- Compra de consumo interestadual (SP000207);
	 * 	- Recebimento de saldo devedor (SP000219)
	 *  - 
	 *  
	 * @param movimentosIcmsIpi
	 * @return
	 */
	
	
	public List<RegE111> montarGrupoRegE111(MovimentoMensalIcmsIpi movimentosIcmsIpi) {
		// TODO Auto-generated method stub
		Set<SpedFiscalRegE110> setSpedFiscRegE110ApuracaoPropria = movimentosIcmsIpi.getSetSpedFiscRegE110ApuracaoPropria();
		
		Set<SpedFiscalRegE110> opRegE110preenchidoPeloUser = spedFiscRegE110Service.findRegE110ByPeriodoAndLoja(movimentosIcmsIpi.getDataInicio(), movimentosIcmsIpi.getDataFim(), movimentosIcmsIpi.getLoja());
		
		
		// 
		return null;
	}

}
