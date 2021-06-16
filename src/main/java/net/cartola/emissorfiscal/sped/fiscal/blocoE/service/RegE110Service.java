package net.cartola.emissorfiscal.sped.fiscal.blocoE.service;

import static net.cartola.emissorfiscal.documento.IndicadorDeOperacao.ENTRADA;
import static net.cartola.emissorfiscal.documento.IndicadorDeOperacao.SAIDA;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.documento.IndicadorDeOperacao;
import net.cartola.emissorfiscal.sped.fiscal.MovimentoMensalIcmsIpi;
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
		
		regE110.setVlTotDebitos(calcularVlTotalDebitos(movimentosIcmsIpi.getMapRegistroAnaliticoPorTipoOperacao()));
		
		
		
		
		// campo 06
		regE110.setVlTotCreditos(calcularVlTotalCreditos(movimentosIcmsIpi.getMapRegistroAnaliticoPorTipoOperacao()));
		
//		movimentosIcmsIpi.getSetRegistroAnalitico().stream().forEach();
		return regE110;
	}

	private BigDecimal calcularVlTotalDebitos(Map<IndicadorDeOperacao, Set<RegistroAnalitico>> map) {
		if (!isMapPopulado(map, SAIDA)) {
			return BigDecimal.ZERO;
		}
		Set<RegistroAnalitico> setRegistroAnalitico = map.get(SAIDA);
		BigDecimal vlTotalDebitos = setRegistroAnalitico.stream().map(RegistroAnalitico::getVlIcms).reduce(BigDecimal.ZERO, BigDecimal::add);
		return vlTotalDebitos;
	}

	private BigDecimal calcularVlTotalCreditos(Map<IndicadorDeOperacao, Set<RegistroAnalitico>> map) {
		if (!isMapPopulado(map, ENTRADA)) {
			return BigDecimal.ZERO;
		}
		Set<RegistroAnalitico> setRegistroAnalitico = map.get(ENTRADA);
		BigDecimal vlTotalCreditos = setRegistroAnalitico.stream().map(RegistroAnalitico::getVlIcms).reduce(BigDecimal.ZERO, BigDecimal::add);
		return vlTotalCreditos;
	}

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
	
}
