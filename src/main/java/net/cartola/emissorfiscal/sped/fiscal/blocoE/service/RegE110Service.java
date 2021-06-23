package net.cartola.emissorfiscal.sped.fiscal.blocoE.service;

import static net.cartola.emissorfiscal.documento.IndicadorDeOperacao.ENTRADA;
import static net.cartola.emissorfiscal.documento.IndicadorDeOperacao.SAIDA;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.documento.IndicadorDeOperacao;
import net.cartola.emissorfiscal.sped.fiscal.MovimentoMensalIcmsIpi;
import net.cartola.emissorfiscal.sped.fiscal.OutrasObrigacoesEAjustes;
import net.cartola.emissorfiscal.sped.fiscal.RegistroAnalitico;
import net.cartola.emissorfiscal.sped.fiscal.blocoE.RegE110;

/**
 * @data 11 de jun. de 2021
 * @author robson.costa
 */
@Service
class RegE110Service {

	public RegE110 montaGrupoRegE110(MovimentoMensalIcmsIpi movimentosIcmsIpi) {
		// TODO Auto-generated method stub
		RegE110 regE110 = new RegE110();
		
		regE110.setVlTotDebitos(calcularVlTotalDebitos(movimentosIcmsIpi.getMapRegistroAnaliticoPorTipoOperacao()));	/** CAMPO 02  **/ 
		regE110.setVlAjDebitos(calcularVlAjusteDebitos(movimentosIcmsIpi.getSetOutrasObrigacoesEAjustes()));			/** CAMPO 03  **/ 
		
		
		
		
		regE110.setVlTotCreditos(calcularVlTotalCreditos(movimentosIcmsIpi.getMapRegistroAnaliticoPorTipoOperacao()));	/** CAMPO 06  **/ 
		regE110.setVlAjCreditos(calcularVlAjusteCreditos(movimentosIcmsIpi.getSetOutrasObrigacoesEAjustes()));			/** CAMPO 07  **/ 
//		movimentosIcmsIpi.getSetRegistroAnalitico().stream().forEach();
		return regE110;
	}


	// Campo 02 
	private BigDecimal calcularVlTotalDebitos(Map<IndicadorDeOperacao, Set<RegistroAnalitico>> map) {
		if (!isMapPopulado(map, SAIDA)) {
			return BigDecimal.ZERO;
		}
		Set<RegistroAnalitico> setRegistroAnalitico = map.get(SAIDA);
		BigDecimal vlTotalDebitos = setRegistroAnalitico.stream().map(RegistroAnalitico::getVlIcms).reduce(BigDecimal.ZERO, BigDecimal::add);
		return vlTotalDebitos;
	}
	
	// Campo 03
	private BigDecimal calcularVlAjusteDebitos(Set<OutrasObrigacoesEAjustes> setOutrasObrigacoesEAjustes) {
		BigDecimal vlAjusteDebitos = setOutrasObrigacoesEAjustes.stream().filter(outraObrigacao -> isValorAjusteDebitos(outraObrigacao))
				.map(OutrasObrigacoesEAjustes::getVlIcms).reduce(BigDecimal.ZERO, BigDecimal::add);
		return (vlAjusteDebitos == null) ? BigDecimal.ZERO : vlAjusteDebitos;
	}


	// Campo 06
	private BigDecimal calcularVlTotalCreditos(Map<IndicadorDeOperacao, Set<RegistroAnalitico>> map) {
		if (!isMapPopulado(map, ENTRADA)) {
			return BigDecimal.ZERO;
		}
		Set<RegistroAnalitico> setRegistroAnalitico = map.get(ENTRADA);
		BigDecimal vlTotalCreditos = setRegistroAnalitico.stream().map(RegistroAnalitico::getVlIcms).reduce(BigDecimal.ZERO, BigDecimal::add);
		return vlTotalCreditos;
	}

	// CAMPO 07
	private BigDecimal calcularVlAjusteCreditos(Set<OutrasObrigacoesEAjustes> setOutrasObrigacoesEAjustes) {
		BigDecimal vlAjusteCreditos = setOutrasObrigacoesEAjustes.stream().filter(outraObrigacao -> isValorAjusteCredito(outraObrigacao))
				.map(OutrasObrigacoesEAjustes::getVlIcms)
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		return (vlAjusteCreditos == null) ? BigDecimal.ZERO : vlAjusteCreditos;
	}

	
	// =========================================================================== Validacoes ==============================================================================

	/**
	 * Irá verificar se tem algo no Mapa de Registro analitico, referente a uma chave (ENTRADA ou SAIDA)
	 * 
	 * @param map
	 * @param indOperacao
	 * @return
	 */
	private boolean isMapPopulado(Map<IndicadorDeOperacao, Set<RegistroAnalitico>> map, IndicadorDeOperacao indOperacao) {
		if (map == null || map.isEmpty() || !map.containsKey(indOperacao)) {
			return false;
		}
		return true;
	}
	
	// CAMPO 03
	private boolean isValorAjusteDebitos(OutrasObrigacoesEAjustes outraObrigacoes) {
		List<String> listTerceiroChar = Arrays.asList("3", "4", "5");
		List<String> listQuartoChar = Arrays.asList("0", "3", "4", "5", "6", "7", "8");
		return isValorAjusteCreditoOrDebito(outraObrigacoes, listTerceiroChar, listQuartoChar);
	}
	
	// CAMPO 06
	private boolean isValorAjusteCredito(OutrasObrigacoesEAjustes outraObrigacao) {
		List<String> listTerceiroChar = Arrays.asList("0", "1", "2");
		List<String> listQuartoChar = Arrays.asList("0", "3", "4", "5", "6", "7", "8");
		return isValorAjusteCreditoOrDebito(outraObrigacao, listTerceiroChar, listQuartoChar);
	}

	/**
	 * 
	 * @param outraObrigacoes	- Obj: Usado para escriturar os registros C197, D197 e C597 (validação será feita dentro do codAj)
	 * @param listTerceiroChar - Lista dos TERCEIRO caracteres válidos! Para o AJUSTE de CREDITO  ou DÉBITO (CAMPOS:  03 e 06) </br>
	 * @param listQuartoChar - Lista dos QUARTO caracteres válidos ! Para o AJUSTE de CREDITO  ou DÉBITO (CAMPOS:  03 e 06)
	 * @return
	 */
	private boolean isValorAjusteCreditoOrDebito(OutrasObrigacoesEAjustes outraObrigacoes, List<String> listTerceiroChar, List<String> listQuartoChar) {
		String terceiroChar = outraObrigacoes.getCodAj().substring(2, 3);
		String quartoChar = outraObrigacoes.getCodAj().substring(3, 4);
		if (listTerceiroChar.contains(terceiroChar) && listQuartoChar.contains(quartoChar)) {
			return true;
		}
		return false;
	}

	
}
