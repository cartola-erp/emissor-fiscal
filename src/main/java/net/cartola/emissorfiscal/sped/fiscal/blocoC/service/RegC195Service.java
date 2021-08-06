package net.cartola.emissorfiscal.sped.fiscal.blocoC.service;

import static net.cartola.emissorfiscal.util.SpedFiscalUtil.getMapaRegistroAnaliticoPorCfopECst;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.documento.DocumentoFiscal;
import net.cartola.emissorfiscal.sped.fiscal.MovimentoMensalIcmsIpi;
import net.cartola.emissorfiscal.sped.fiscal.blocoC.RegC190;
import net.cartola.emissorfiscal.sped.fiscal.blocoC.RegC195;


/**
 * 
 * REGISTRO C195: OBSERVAÇÕES DO LANÇAMENTO FISCAL (CÓDIGO 01, 1B, 04, 55 E 65)
 * @author robson.costa
 *
 */
@Service
class RegC195Service {
	
	@Autowired
	private RegC197Service regC197Service;

	
	/**
	 *  TODO
	 *  
	 *  O registro C195/C197 Deverá ser preenchido Nas situações abaixo: (Não sei ainda a GABI, ficou de ver a regra corretamente)
	 *  	-> Mas é usados o(s) código(s), da tabela --> 5.3 - TABELA DE AJUSTES E INFORMAÇÕES DE VALORES PROVENIENTES DE DOCUMENTO FISCAL <--
	 *			-> SP90090104|Valor correspondente à coluna Isentas/Não tributadas e Outras (artigos 214 e 215 do RICMS/00)|01072018|
	 * 		
	 *  
	 *  
	 *  
	 *  REGISTRO E110 (Vários ajustes, são feitos manualmente) E os códigos usados é da tabela abaixo:
	 *  	(É usado a tabela: : 5.1. 1- Tabela de Códigos de Ajustes da Apuração do ICMS - SP) 
	 *  	|E111|SP000207|
	 * 		|E111|SP000219|
	 *		|E111|SP000299|
	 *		|E111|SP010309|
	 *		|E111|SP020713|
	 *		|E111|SP020718|
	 *		|E111|SP020799|
	 *		|E111|SP030899|
	 *  	
	 *  	-> SEMPRE que tiver uma Operação, de DEVOLUÇÃO PARA O FORNECEDOR TRIBUTADA.
	 *  		-> E algum ITEM  dessa DEVOLUÇÃO. Que nas entrada A Autogeral NÃO, obteve CRÉDITO de ICMS. (Pois realmente NÃO tinhamos esse direito) 
	 *  			PORÉM, na SAÍDA tivemos que DEVOLVER o IMPOSTO, que veio na ENTRADA.
	 * 		->	
	 * 	 
	 * 
	 */
	
	
	
	/**
	 * 
	 * @param listRegC190 
	 * @param docFisc
	 * @param movimentosIcmsIpi
	 * @return
	 */
	public List<RegC195> montarGrupoRegC195PortariaCat66De2018(List<RegC190> listRegC190, DocumentoFiscal docFisc, MovimentoMensalIcmsIpi movimentosIcmsIpi) {
		// TODO Auto-generated method stub
		List<RegC195> listRegC195 = new ArrayList<>();
		
//		basicamente o preenchimento será feito usando um dos dois codigos abaixo (ps: um é ipi e outro icms st)
//		PS: Os codigos que vão no REG C197 é da tabela 5.3 (referentes a ajustes no DOCUMENTO, e não na APURACAO)		
//		E o codiog do REG C195 (deverá já estar, lançado/ (ou no caso lançar) no  REG 0460)
//		
//		Portanto é preciso de pensar em uma forma de salvar esses códigos numa lista do OBJETO movimentosIcmsIpi (para serem escriturados), 

		
//		Map<Integer, Map<String, List<RegC190>>> mapRegistroAnaliticoPorCfopECst = listRegC190.stream().
//				collect(groupingBy(RegC190::getCfop, 
//							groupingBy(regC190 -> getCstIcmsSemOrigem(regC190.getCstIcms()))));
		Map<Integer, Map<String, List<RegC190>>> mapRegistroAnaliticoPorCfopECst = getMapaRegistroAnaliticoPorCfopECst(listRegC190);
		
		for (Integer cfop : mapRegistroAnaliticoPorCfopECst.keySet()) {
			Map<String, List<RegC190>> mapRegistroAnaliticoPorCst = mapRegistroAnaliticoPorCfopECst.get(cfop);

			RegC195 regC195 = new RegC195();
			regC195.setCodObs(Integer.toString(cfop));
			regC195.setRegC197(regC197Service.montarGrupoRegC197PortariaCat66De2018(cfop, mapRegistroAnaliticoPorCst, docFisc, movimentosIcmsIpi));
			
			listRegC195.add(regC195);
		}
		
		return listRegC195;
	}

	
}
