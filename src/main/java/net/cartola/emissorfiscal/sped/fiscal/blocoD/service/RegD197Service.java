package net.cartola.emissorfiscal.sped.fiscal.blocoD.service;

import static net.cartola.emissorfiscal.util.NumberUtilRegC100.getBigDecimalNullSafe;
import static net.cartola.emissorfiscal.util.SpedFiscalUtil.isIcmsCstIsentaOuNaoTributada;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.documento.DocumentoFiscal;
import net.cartola.emissorfiscal.sped.fiscal.MovimentoMensalIcmsIpi;
import net.cartola.emissorfiscal.sped.fiscal.blocoD.RegD190;
import net.cartola.emissorfiscal.sped.fiscal.blocoD.RegD197;

/**
 * 
 * Portaria CAT 66 de 2018
 * 
 * https://portal.fazenda.sp.gov.br/servicos/sped/Downloads/ManualPcat662018.pdf
 * https://portal.fazenda.sp.gov.br/servicos/sped/Paginas/Orienta%C3%A7%C3%B5es%20Portaria%20CAT%2066%20de%202018.aspx
 * 
 * 
 * @date 6 de ago. de 2021
 * @author robson.costa
 */
@Service
class RegD197Service {

	
	
	public List<RegD197> montarGrupoRegD197PortariaCat66De2018(Integer cfop, Map<String, List<RegD190>> mapRegistroAnaliticoPorCst, 
			DocumentoFiscal servicoTransporte, MovimentoMensalIcmsIpi movimentosIcmsIpi) {
		List<RegD197> listRegD197 = new ArrayList<>();
		
		//	SP90090104 
		RegD197 regD197VlIcmsIsentasNtOutras = gerarRegD197ValorICmsIsentasNaoTributadasOutras(cfop, mapRegistroAnaliticoPorCst);
		listRegD197.add(regD197VlIcmsIsentasNtOutras);
		
		return listRegD197;
	}

	private RegD197 gerarRegD197ValorICmsIsentasNaoTributadasOutras(Integer cfop, Map<String, List<RegD190>> mapRegistroAnaliticoPorCst) {
		RegD197 regD197 = new RegD197();

		BigDecimal vlIcmsIsentaNaoTributadaOutrasPorCfop = funcao3ValorIsentasNaoTributadasPorCfop(mapRegistroAnaliticoPorCst).add(funcao4TotalVlRedBc(mapRegistroAnaliticoPorCst));
		
		regD197.setCodAj("SP90090104");
		regD197.setDescrComplAj(Integer.toString(cfop));
		regD197.setCodItem(null);
		regD197.setVlBcIcms(null);
		regD197.setAliqIcms(null);
//		regD197.setVlIcms(vlIcmsIsentaNaoTributadaOutrasPorCfop);
		regD197.setVlIcms(null);
		regD197.setVlOutros(vlIcmsIsentaNaoTributadaOutrasPorCfop);
		
		return regD197;
	}


	private BigDecimal funcao3ValorIsentasNaoTributadasPorCfop(Map<String, List<RegD190>> mapRegistroAnaliticoPorCst) {
		List<RegD190> listRegD190IsentaOuNT = new ArrayList<>();
		
		mapRegistroAnaliticoPorCst.forEach( (icmsCst, regD190NoMapa) -> {
			if (isIcmsCstIsentaOuNaoTributada(icmsCst)) {
				listRegD190IsentaOuNT.addAll(regD190NoMapa);
			}
		});
		
		Function<RegD190, BigDecimal> funcao3 = regD190 -> getBigDecimalNullSafe(regD190.getVlOpr())
							.subtract(regD190.getVlBcIcms())
							.subtract(regD190.getVlRedBc());
		
		List<BigDecimal> listVlIcmsOutros = new ArrayList<>();
		listRegD190IsentaOuNT.stream().forEach(regD190Isenta -> listVlIcmsOutros.add(funcao3.apply(regD190Isenta)));
		BigDecimal vlIcmsOutros = listVlIcmsOutros.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
		
		return (vlIcmsOutros.compareTo(BigDecimal.ZERO) <= 0) ? BigDecimal.ZERO : vlIcmsOutros;
	}
	
	private BigDecimal funcao4TotalVlRedBc(Map<String, List<RegD190>> mapRegistroAnaliticoPorCst) {
		List<RegD190> listRegD190ToBeCalculeted = new ArrayList<>();
		
		mapRegistroAnaliticoPorCst.forEach( (icmsCst, regD190NoMapa) -> {
			if (!isIcmsCstIsentaOuNaoTributada(icmsCst)) {
				listRegD190ToBeCalculeted.addAll(regD190NoMapa);
			}
		});
		
		BigDecimal totalVlRedBc = listRegD190ToBeCalculeted.stream().map(RegD190::getVlRedBc).reduce(BigDecimal.ZERO, BigDecimal::add);
		return totalVlRedBc;
	}
	
	
	
}
