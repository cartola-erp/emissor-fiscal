package net.cartola.emissorfiscal.sped.fiscal.blocoE.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.sped.fiscal.MovimentoMensalIcmsIpi;
import net.cartola.emissorfiscal.sped.fiscal.blocoE.RegE110;
import net.cartola.emissorfiscal.sped.fiscal.blocoE.RegE116;
import net.cartola.emissorfiscal.sped.fiscal.enums.ObrigacaoIcmsARecolher;

/**
 * @date 10 de ago. de 2021
 * @author robson.costa
 */
@Service
class RegE116Service {

	
	public List<RegE116> montarGrupoDeRegistro(MovimentoMensalIcmsIpi movimentosIcmsIpi, RegE110 regE110) {
		List<RegE116> listRegE116 = new ArrayList<>();
		RegE116 regE116 = new RegE116();
		
		regE116.setCodOr(ObrigacaoIcmsARecolher.ICMS_RECOLHER);
		BigDecimal vlObrigacaoRecolher = regE110.getVlIcmsRecolher().add(regE110.getDebEsp());
		regE116.setVlOr(vlObrigacaoRecolher);
		
		regE116.setDtVcto(LocalDate.now().withDayOfMonth(20));
		regE116.setCodRec("046-2");
		
		LocalDate dataInicio = movimentosIcmsIpi.getDataInicio();
		final String mesRef = Integer.toString(dataInicio.getMonthValue()) + (Integer.toString(dataInicio.getYear()));
		
		regE116.setMesRef(mesRef);
		listRegE116.add(regE116);
		return listRegE116;
	}

	
	
}
