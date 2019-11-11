package net.cartola.emissorfiscal.emissorfiscal.service;


import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Optional;

import org.junit.FixMethodOrder;
import org.junit.Test;
//import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import net.cartola.emissorfiscal.ncm.Ncm;
import net.cartola.emissorfiscal.ncm.NcmController;
import net.cartola.emissorfiscal.ncm.NcmService;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NcmServiceLogicTest {
	
	@Autowired
	private NcmService ncmService;
	
	public static int NCM_NUMERO_REGISTRO_1 = 12345678;
	public static int NCM_EXCECAO_REGISTRO_1 = 43;
	public static String NCM_DESCRICAO_REGISTRO_1 = "Essa é uma DESCRIÇÃO do PRIMEIRO NCM para o teste";
	
	public static int NCM_NUMERO_REGISTRO_2 = 89101112;
	public static int NCM_EXCECAO_REGISTRO_2 = 32;
	public static String NCM_DESCRICAO_REGISTRO_2 = "Essa é uma DESCRIÇÃO do SEGUNDO NCM para o teste";
	
	public static int NCM_NUMERO_ERRADO = 1234;
	public static String NCM_DESCRICAO_REGISTRO_2_ALTERADA = "A descrição desse NCM foi ALTERADA";
	
	
	@Test
	public void test01_InsertNcm_1() {
		Ncm ncm = new Ncm();
		ncm.setNumero(NCM_NUMERO_REGISTRO_1);
		ncm.setExcecao(NCM_EXCECAO_REGISTRO_1);
		ncm.setDescricao(NCM_DESCRICAO_REGISTRO_1);	
		
		Optional<Ncm> opNcm = ncmService.save(ncm);
		assertTrue(opNcm.isPresent());
		
		System.out.println(this.getClass().getName() + " test01_InsertNcm_1, Ok");
	}

	@Test
	public void test02_InsertNcm_2() {
		Ncm ncm = new Ncm();
		ncm.setNumero(NCM_NUMERO_REGISTRO_2);
		ncm.setExcecao(NCM_EXCECAO_REGISTRO_2);
		ncm.setDescricao(NCM_DESCRICAO_REGISTRO_2);

		Optional<Ncm> opNcm = ncmService.save(ncm);
		assertTrue(opNcm.isPresent());
		
		System.out.println(this.getClass().getName() + " test02_InsertNcm_2, Ok");
	}
	
	@Test
	public void test03_UpdateNcm_2() {
		Optional<Ncm> opNcm = ncmService.findOne(2L);
		assertTrue(opNcm.isPresent());
		
		opNcm.get().setDescricao(NCM_DESCRICAO_REGISTRO_2_ALTERADA);
		opNcm = ncmService.save(opNcm.get());
		assertTrue(opNcm.isPresent());
		
		System.out.println(this.getClass().getName() + " test03_UpdateNcm_2, Ok");
	}
	
	
	@Test
	public void test04_DeveFalharAoTentarSalvarNcmComMenosDeOitoDigitos() {
		Ncm ncm = new Ncm();
		ncm.setNumero(NCM_NUMERO_ERRADO);
		ncm.setExcecao(NCM_EXCECAO_REGISTRO_1);
		ncm.setDescricao(NCM_DESCRICAO_REGISTRO_1);
		
		Optional<Ncm> opNcm = ncmService.save(ncm);
		
		assertFalse(opNcm.isPresent());
		
		System.out.println(this.getClass().getName() + " test04_DeveFalharAoTentarSalvarNcmComMenosDeOitoDigitos, Ok");
	}
	
	@Test
	public void test05_DeveFalharAoTentarSalvarUmNcmRepetido() {
		Ncm ncm = new Ncm();
		ncm.setNumero(NCM_NUMERO_REGISTRO_1);
		ncm.setExcecao(NCM_EXCECAO_REGISTRO_1);
		ncm.setDescricao(NCM_DESCRICAO_REGISTRO_1);	
		
//		Optional<Ncm> opNcm = ncmService.existeNumeroEExcecao(ncm);
		boolean isNcmRepetido = ncmService.existeNumeroEExcecao(ncm);

		assertTrue(isNcmRepetido);
		
		System.out.println(this.getClass().getName() + " test05_DeveFalharAoTentarSalvarUmNcmRepetido, Ok");
	}
	
	
}