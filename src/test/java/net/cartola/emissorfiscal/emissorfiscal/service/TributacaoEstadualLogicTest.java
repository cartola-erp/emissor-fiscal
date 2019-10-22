package net.cartola.emissorfiscal.emissorfiscal.service;


import java.math.BigDecimal;
import java.util.Map;

import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import net.cartola.emissorfiscal.estado.Estado;
import net.cartola.emissorfiscal.ncm.Ncm;
import net.cartola.emissorfiscal.tributacao.estadual.TributacaoEstadual;


@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TributacaoEstadualLogicTest {
	
	Estado estadoOrigem = new Estado();
	Estado estadoDestino = new Estado();

	
	
	@Test
	public void test01_DeveCalcularOIcmsDeSPparaSP() {
//		Estado estadoOrigem = new Estado();
		estadoOrigem.setId(1L);
		estadoOrigem.setSigla("SP");
		estadoOrigem.setNome("São Paulo");
		
//		Estado estadoDestino = new Estado();
		estadoDestino.setId(2L);
		estadoDestino.setSigla("SP");
		estadoDestino.setNome("São Paulo");
		
		Ncm ncm = new Ncm();
		ncm.setId(1L);
//		ncm.setExcecao(excecao);
		ncm.setNumero(31025011);
		
		
		TributacaoEstadual icms = new TributacaoEstadual();
		icms.setEstadoOrigem(estadoOrigem);
		icms.setEstadoDestino(estadoDestino);
		icms.setNcm(ncm);
		
		System.out.println("Teste executado: " );
	}
}
