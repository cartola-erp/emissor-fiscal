package net.cartola.emissorfiscal.emissorfiscal.service;


import org.junit.FixMethodOrder;
import org.junit.Test;
//import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import net.cartola.emissorfiscal.estado.EstadoService;
import net.cartola.emissorfiscal.operacao.OperacaoService;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OperacaoServiceLogicTest {
	
	@Autowired
	private OperacaoService operacaoService;
	
//	OPERAÇÃO
	public static long OPERACAO_ID = 1L;
	public static String OPERACAO_DESCRICAO = "Essa é uma DESCRIÇÃO da OPERAÇÃO para o teste";		
	
	
	@Test
	public void test01_InsertOperacao_1() {
		
		System.out.println(this.getClass().getName() + " test01_InsertNcm_1, Ok");
	}

	
}
