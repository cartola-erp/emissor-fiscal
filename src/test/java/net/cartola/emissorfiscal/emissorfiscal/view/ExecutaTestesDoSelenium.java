package net.cartola.emissorfiscal.emissorfiscal.view;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import net.cartola.emissorfiscal.emissorfiscal.service.TestHelper;


/**
 *	13 de jan de 2020
 *	@author robson.costa
 */

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ExecutaTestesDoSelenium extends SeleniumConfig {
	 
	@Autowired 
	private TestHelper testHelper;
	
	@Test
	public void test01_TentaCadastrarUmNcmIncompleto() {
		NcmPage ncm = new NcmPage(driver);
		testHelper.limpaBanco();
		ncm.tentaCadastrarUmNcmIncompleto();
		System.out.println("\n" + this.getClass().getName() + " test01_TentaCadastrarUmNCMIncompleto, Ok");
	}
	
	@Test
	public void test02_TentaCadastrarUmNcmCorretamente() {
		NcmPage ncm = new NcmPage(driver);
		testHelper.limpaBanco();
		ncm.tentaCadastrarUmNcmCorretamente();
		System.out.println("\n"+ this.getClass().getName() + " test02_TentaCadastrarUmNcmCorretamente, Ok");
	}
	
	@Test
	public void test03_TentaConsultarUmNcmVazio() {
		NcmPage ncm = new NcmPage(driver);
//		testHelper.limpaBanco();
		ncm.tentaConsultarUmNcmVazio();
		System.out.println("\n" + this.getClass().getName() + " test03_TentaConsultarUmNCMVazio, Ok");
	}

	@Test
	public void test04_TentaConsultarUmNcmExistente() {
		NcmPage ncm = new NcmPage(driver);
		testHelper.limpaBanco();
		ncm.tentaCadastrarUmNcmCorretamente();
		ncm.tentaConsultarUmNcmExistente();
		System.out.println("\n" + this.getClass().getName() + " test04_TentaConsultarUmNcmExistente, Ok");
	}

	@Test
	public void test05_TentaConsultarUmNcmInexistente() {
		NcmPage ncm = new NcmPage(driver);
		testHelper.limpaBanco();
		ncm.tentaConsultaUmNcmInexistente();
		System.out.println("\n" + this.getClass().getName() + " test05_TentaConsultarUmNcmInexistente, Ok");
	}
	
	
	@Test
	public void test06_TentaEditarUmNCM() {
		NcmPage ncmPage = new NcmPage(this.driver);
		int i = 0;

//		while (i < 30) {
		testHelper.limpaBanco();
		ncmPage.tentaCadastrarUmNcmCorretamente();
		ncmPage.tentaEditarOPrimeiroRegistroDeUmNcm();
//			i++;
//			System.out.println("test03_TentaEditarUmNCM: De NÚMERO == " + i);
//		}
		System.out.println("\n"+ this.getClass().getName() + " test06_TentaEditarUmNCM, Ok");
	}
	
	
//	@Test 
//	public void test07_TentaEditarUmNcmSemMudarNada() {
//		NcmPage ncm = new NcmPage(driver);
//		testHelper.limpaBanco();
//		
//		
//		System.out.println("\n"+ this.getClass().getName() + " test07_TentaEditarUmNcmSemMudarNada, Ok");
//	}
	
	@Test
	public void test08_TentaDeletarUmNcm() {
		
	}
	
	
	
}


