package net.cartola.emissorfiscal.sped.fiscal.blocoD.service;

import static net.cartola.emissorfiscal.util.SpedFiscalUtil.getMapaRegistroAnaliticoPorCfopECst;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.documento.DocumentoFiscal;
import net.cartola.emissorfiscal.sped.fiscal.MovimentoMensalIcmsIpi;
import net.cartola.emissorfiscal.sped.fiscal.blocoD.RegD190;
import net.cartola.emissorfiscal.sped.fiscal.blocoD.RegD195;

/**
 * @date 6 de ago. de 2021
 * @author robson.costa
 */
@Service
class RegD195Service {

	@Autowired
	private RegD197Service regD197Service;
	
	/**
	 * 
	 * @param listRegD190
	 * @param servicoTransporte
	 * @param movimentosIcmsIpi
	 * @return
	 */
	public List<RegD195> montarGrupoRegD195PortariaCat66De2018(List<RegD190> listRegD190, DocumentoFiscal servicoTransporte, MovimentoMensalIcmsIpi movimentosIcmsIpi) {
		List<RegD195> listRegD195 = new ArrayList<>();
//		Map<Integer, Map<String, List<RegD190>>> mapRegistroAnaliticoPorCfopECst = listRegD190.stream().
//		collect(groupingBy(RegD190::getCfop, 
//				groupingBy(regD190 -> getCstIcmsSemOrigem(regD190.getCstIcms()))));
		Map<Integer, Map<String, List<RegD190>>> mapRegistroAnaliticoPorCfopECst = getMapaRegistroAnaliticoPorCfopECst(listRegD190);
		
		for (Integer cfop : mapRegistroAnaliticoPorCfopECst.keySet()) {
			Map<String, List<RegD190>> mapRegistroAnaliticoPorCst = mapRegistroAnaliticoPorCfopECst.get(cfop);
			
			RegD195 regD195 = new RegD195();
			regD195.setCodObs(Integer.toString(cfop));
			regD195.setRegD197(regD197Service.montarGrupoRegD197PortariaCat66De2018(cfop, mapRegistroAnaliticoPorCst, servicoTransporte, movimentosIcmsIpi));
			listRegD195.add(regD195);
		}
		
		return listRegD195;
	}


}
