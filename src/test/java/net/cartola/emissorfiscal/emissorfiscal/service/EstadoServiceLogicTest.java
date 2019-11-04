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

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EstadoServiceLogicTest {
	
	@Autowired
	private EstadoService estadoService;
	
//	ESTADO ORIGEM
//	public static Long ESTADO_ID = 1L;
	public static String ESTADO_SIGLA = "SP";
	public static String ESTADO_NOME = "São Paulo";
	
	
	@Test
	public void test01_InsertEstado_1() {
		
		System.out.println(this.getClass().getName() + " test01_InsertNcm_1, Ok");
	}

	
}
