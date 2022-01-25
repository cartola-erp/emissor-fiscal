package net.cartola.emissorfiscal.sped.fiscal.blocoC.service;

import static net.cartola.emissorfiscal.util.NumberUtilRegC100.getBigDecimalDuasCasas;
import static net.cartola.emissorfiscal.util.NumberUtilRegC100.getBigDecimalNullSafe;
import static net.cartola.emissorfiscal.util.NumberUtilRegC100.isBigDecimalZero;
import static net.cartola.emissorfiscal.util.SpedFiscalUtil.isIcmsCstIsentaOuNaoTributada;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.documento.DocumentoFiscal;
import net.cartola.emissorfiscal.documento.DocumentoFiscalItem;
import net.cartola.emissorfiscal.sped.fiscal.MovimentoMensalIcmsIpi;
import net.cartola.emissorfiscal.sped.fiscal.blocoC.RegC190;
import net.cartola.emissorfiscal.sped.fiscal.blocoC.RegC197;

/**
 *  * Portaria CAT 66 de 2018
 * 
 * https://portal.fazenda.sp.gov.br/servicos/sped/Downloads/ManualPcat662018.pdf
 * https://portal.fazenda.sp.gov.br/servicos/sped/Paginas/Orienta%C3%A7%C3%B5es%20Portaria%20CAT%2066%20de%202018.aspx
 * 
 * @author robson.costa
 *
 */
@Service
class RegC197Service {

	
	/**
	 * 
	 * @param cfop -> CFOP, referente ao: "mapRegistroAnaliticoPorCst"
	 * @param mapRegistroAnaliticoPorCst -> mapa da atual cfop, separado por CST
	 * @param docFisc 
	 * @param movimentosIcmsIpi	->
	 * @return 
	 */
	public List<RegC197> montarGrupoRegC197PortariaCat66De2018(Integer cfop, Map<String, List<RegC190>> mapRegistroAnaliticoPorCst, DocumentoFiscal docFisc, MovimentoMensalIcmsIpi movimentosIcmsIpi) {
		// TODO Auto-generated method stub
		List<RegC197> listRegC197 = new ArrayList<>();
		
 //		basicamente o preenchimento será feito usando um dos dois codigos abaixo (ps: um é ipi e outro icms st)
//		PS: Os codigos que vão no REG C197 é da tabela 5.3 (referentes a ajustes no DOCUMENTO, e não na APURACAO)		
//		E o codiog do REG C195 (deverá já estar, lançado/ (ou no caso lançar) no  REG 0460)
//		Portanto é preciso de pensar em uma forma de salvar esses códigos numa lista do OBJETO movimentosIcmsIpi (para serem escriturados), 
		
		
		//	SP90090104 	-> Valor correspondente à coluna Isentas/Não tributadas e Outras (artigos 214 e 215 do RICMS/00)|01072018|
		RegC197 regC197VlIcmsIsentasNtOutras = gerarRegC197ParaOValorIcmsIsentasNaoTributadasOutras(cfop, mapRegistroAnaliticoPorCst);
		
		// 	SP90090278 	-> Valor correspondente ao ICMS ST na condição de substituído (artigo 278, § 1º, do RICMS/00)|01072018|
		RegC197 regC197VlIcmsSt = gerarRegC197IcmsStPagoPeloFornecedor(cfop, mapRegistroAnaliticoPorCst, docFisc);
		
		if(regC197VlIcmsIsentasNtOutras.getVlOutros() != null &&  !isBigDecimalZero(regC197VlIcmsIsentasNtOutras.getVlOutros())) {
			listRegC197.add(regC197VlIcmsIsentasNtOutras);
		}
		
		if (regC197VlIcmsSt.getVlIcms() != null && !isBigDecimalZero(regC197VlIcmsSt.getVlIcms())) {
			listRegC197.add(regC197VlIcmsSt);
		}
		
//		|C195|1403||
//		|C197|SP90090104|1403||||0,00|2756,13|
//		|C197|SP90090278|1403||||354,53||

		return listRegC197;
	}


	/**
	 * Irá calcular o VL_OUTROS, para o ICMS; para o Codigo ajuste == "SP90090104", da tabela 5.3
	 * 
	 * @param cfop
	 * @param mapRegistroAnaliticoPorCst
	 * @return
	 */
	private RegC197 gerarRegC197ParaOValorIcmsIsentasNaoTributadasOutras(Integer cfop, Map<String, List<RegC190>> mapRegistroAnaliticoPorCst) {
		RegC197 regC197 = new RegC197();
		// Uma lista para CST icms isenta/nt (quando CST = 30, 40 ou 41)
		// Uma lista para CST != isenta 
		BigDecimal vlIcmsIsentaNaoTributadaOutrasPorCfop = funcao1ValorIsentasNaoTributadasPorCfop(mapRegistroAnaliticoPorCst).add(funcao2TotalVlRedBc(mapRegistroAnaliticoPorCst));

		regC197.setCodAj("SP90090104");
		regC197.setDescrComplAj(Integer.toString(cfop));
		regC197.setCodItem(null);
		regC197.setVlBcIcms(null);
		regC197.setAliqIcms(null);
//		regC197.setVlIcms(vlIcmsIsentaNaoTributadaOutrasPorCfop);
//		regC197.setVlIcms(null);
		regC197.setVlIcms(BigDecimal.ZERO);
		regC197.setVlOutros(getBigDecimalDuasCasas(vlIcmsIsentaNaoTributadaOutrasPorCfop));

		return regC197;
	}

	/**
	 * - Será aplicado a <b> funcao 1 <\b>, da Portaria CAT 66 de 2018. <\br> 
	 * - <b> funcao1(cfop) =  <\b> VL_OPR - VL_BC_ICMS - VL_ICMS_ST - VL_RED_BC - VL_IPI , apenas nos casos que a CST do REG C190 forem: <\br>
	 * - CST == 30, 40 e/ou 41
	 * 
	 * @param mapRegistroAnaliticoPorCst -> Mapa de registros analiticos, separados por CST;
	 * @return vlIcmsOutros 
	 */
	private BigDecimal funcao1ValorIsentasNaoTributadasPorCfop(Map<String, List<RegC190>> mapRegistroAnaliticoPorCst) {
		// QUANDO a CST do REG C190 for == 30, 40 e 41
//		mapRegistroAnaliticoPorCst.keySet();
		List<RegC190> listRegC190IsentaOuNT = new ArrayList<>();
//		for (String icmsCst : mapRegistroAnaliticoPorCst.keySet()) {
//			if (isIcmsCstIsentaOuNaoTributada(icmsCst)) {
//				List<RegC190> regC190NoMapa = mapRegistroAnaliticoPorCst.get(icmsCst);
//				listRegC190IsentaOuNT.addAll(regC190NoMapa);
//			}
//		}
		mapRegistroAnaliticoPorCst.forEach( (icmsCst, regC190NoMapa) -> {
			if (isIcmsCstIsentaOuNaoTributada(icmsCst)) {
				listRegC190IsentaOuNT.addAll(regC190NoMapa);
			}
		});

		Function<RegC190, BigDecimal> funcao1 = regC190 -> getBigDecimalNullSafe(regC190.getVlOpr())
								.subtract(getBigDecimalNullSafe(regC190.getVlBcIcms()))
								.subtract(getBigDecimalNullSafe(regC190.getVlIcmsSt()))
								.subtract(getBigDecimalNullSafe(regC190.getVlRedBc()))
								.subtract(getBigDecimalNullSafe(regC190.getVlIpi()));

		List<BigDecimal> listVlIcmsOutros = new ArrayList<>();
		listRegC190IsentaOuNT.stream().forEach(regC190Isenta -> listVlIcmsOutros.add(funcao1.apply(regC190Isenta)));
		BigDecimal vlIcmsOutros = listVlIcmsOutros.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
		
		return (vlIcmsOutros.compareTo(BigDecimal.ZERO) <= 0) ? BigDecimal.ZERO : vlIcmsOutros;
	}
	
	
	/**
	 * Irá somar o CAMPO: <b>VL RED BC<\b>, de todos os registros analiticos do mapa, que as CSTs SEJAM diferentes de:
	 * 30, 40 e 41
	 * @param mapRegistroAnaliticoPorCst
	 * @return
	 */
	private BigDecimal funcao2TotalVlRedBc(Map<String, List<RegC190>> mapRegistroAnaliticoPorCst) {
		List<RegC190> listRegC190ToBeCalculeted = new ArrayList<>();

		mapRegistroAnaliticoPorCst.forEach( (icmsCst, rec190NoMapa) -> {
			if (!isIcmsCstIsentaOuNaoTributada(icmsCst)) {
				listRegC190ToBeCalculeted.addAll(rec190NoMapa);
			}
		});
		
//		mapRegistroAnaliticoPorCst.forEach((icmsCst, listRegC190NoMapa) -> listRegC190ToBeCalculeted.addAll(listRegC190ToBeCalculeted));
		BigDecimal totalVlRedBc = listRegC190ToBeCalculeted.stream().map(regC190 -> getBigDecimalNullSafe(regC190.getVlRedBc())).reduce(BigDecimal.ZERO, BigDecimal::add);
		return totalVlRedBc;
	}
	
	
	
	private RegC197 gerarRegC197IcmsStPagoPeloFornecedor(Integer cfop, Map<String, List<RegC190>> mapRegistroAnaliticoPorCst, DocumentoFiscal docFisc) {
		RegC197 regC197 = new RegC197();
		
		BigDecimal vlIcmsOuIcmsSt = funcao5ValorColunaOutras(cfop, mapRegistroAnaliticoPorCst, docFisc);
		
		regC197.setCodAj("SP90090278");
		regC197.setDescrComplAj(Integer.toString(cfop));
		regC197.setCodItem(null);
		regC197.setVlBcIcms(null);
		regC197.setAliqIcms(null);
		regC197.setVlIcms(getBigDecimalDuasCasas(vlIcmsOuIcmsSt));
//		regC197.setVlIcms(null);
//		regC197.setVlOutros(vlIcmsIsentaNaoTributadaOutrasPorCfop);
		regC197.setVlOutros(null);
				
		return regC197;
	}
	
	/**
	 * Para todas as CSTs diferentes (!=) de 30, 40  e 41
	 * @param cfop 
	 * 
	 * @param mapRegistroAnaliticoPorCst
	 * @param docFisc -> DocumentoFiscal, que será filtrado os itens por CFOP, e totalizar o VST (ICMS ST SUBSTITUIDO)
	 * @return
	 */
	private BigDecimal funcao5ValorColunaOutras(Integer cfop, Map<String, List<RegC190>> mapRegistroAnaliticoPorCst, DocumentoFiscal docFisc) {
		Function<RegC190, BigDecimal> funcao5 = regC190 -> getBigDecimalNullSafe(regC190.getVlOpr())
				.subtract(getBigDecimalNullSafe(regC190.getVlBcIcms()))
				.subtract(getBigDecimalNullSafe(regC190.getVlIcmsSt()))
				.subtract(getBigDecimalNullSafe(regC190.getVlRedBc()))
				.subtract(getBigDecimalNullSafe(regC190.getVlIpi()));
//				.subtract(getBigDecimalNullSafe(totalVlIcmsStPorCfop));
		
		List<RegC190> listRegC190ToBeCalculeted = new ArrayList<>();
		mapRegistroAnaliticoPorCst.forEach( (icmsCst, rec190NoMapa) -> {
			if (!isIcmsCstIsentaOuNaoTributada(icmsCst)) {
				listRegC190ToBeCalculeted.addAll(rec190NoMapa);
			}
		});
		
		List<BigDecimal> listResultFuncao5 = new ArrayList<>();
		listRegC190ToBeCalculeted.stream().forEach(regC190ToBeCalc -> listResultFuncao5.add(funcao5.apply(regC190ToBeCalc)));
		
		// Total do ICMS ST, que veio no DocumentoFiscal, (totalizado, pela CFOP)
		BigDecimal totalVlIcmsStPorCfop = docFisc.getItens().stream().filter(item -> item.getCfop() == cfop)
					.map(DocumentoFiscalItem::getIcmsStValor)
					.reduce(BigDecimal.ZERO, BigDecimal::add);
		
		BigDecimal vlIcmsOuIcmsSt = listResultFuncao5.stream().reduce(BigDecimal.ZERO, BigDecimal::add).subtract(totalVlIcmsStPorCfop);
		
		return (vlIcmsOuIcmsSt.compareTo(BigDecimal.ZERO) <= 0) ? BigDecimal.ZERO : vlIcmsOuIcmsSt;
	}

	
	
	
	
	
}
