package net.cartola.emissorfiscal.sped.fiscal.blocoE.service;

import java.math.BigDecimal;
import java.util.Set;

import org.springframework.stereotype.Service;

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
		
		regE110.setVlTotDebitos(calcularVlTotalDebitos(movimentosIcmsIpi.getSetRegistroAnalitico()));
		
//		movimentosIcmsIpi.getSetRegistroAnalitico().stream().forEach();
		return regE110;
	}

	
	private BigDecimal calcularVlTotalDebitos(Set<RegistroAnalitico> setRegistroAnalitico) {
		if (setRegistroAnalitico == null || setRegistroAnalitico.isEmpty()) {
			return BigDecimal.ZERO;
		}
		return setRegistroAnalitico.stream().map(RegistroAnalitico::getVlIcms).reduce(BigDecimal.ZERO, BigDecimal::add);
	}
	
	
	
}
