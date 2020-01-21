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
	
	
	public void test03_TentaCadastrarUmNcmRepetido() {
		NcmPage ncmPage = new NcmPage(driver);
		testHelper.limpaBanco();
		ncmPage.tentaCadastrarUmNcmRepetido();
		System.out.println("\n"+ this.getClass().getName() + " test03_TentaCadastrarUmNcmRepetido, Ok");
	}
	
	@Test
	public void test04_TentaConsultarUmNcmVazio() {
		NcmPage ncm = new NcmPage(driver);
		testHelper.limpaBanco();
		ncm.tentaConsultarUmNcmVazio();
		System.out.println("\n" + this.getClass().getName() + " test04_TentaConsultarUmNcmVazio, Ok");
	}
	
	@Test
	public void test05_TentaConsultarUmNcmExistente() {
		NcmPage ncm = new NcmPage(driver);
		testHelper.limpaBanco();
		ncm.tentaCadastrarUmNcmCorretamente();
		ncm.tentaConsultarUmNcmExistente();
		System.out.println("\n" + this.getClass().getName() + " test05_TentaConsultarUmNcmExistente, Ok");
	}

	@Test
	public void test06_TentaConsultarUmNcmInexistente() {
		NcmPage ncm = new NcmPage(driver);
		testHelper.limpaBanco();
		ncm.tentaConsultaUmNcmInexistente();
		System.out.println("\n" + this.getClass().getName() + " test06_TentaConsultarUmNcmInexistente, Ok");
	}
	
	
	@Test
	public void test07_TentaEditarUmNcm() {
		NcmPage ncmPage = new NcmPage(this.driver);
		testHelper.limpaBanco();
		ncmPage.tentaCadastrarUmNcmCorretamente();
		ncmPage.tentaEditarOPrimeiroRegistroDeUmNcm();
		System.out.println("\n"+ this.getClass().getName() + " test07_TentaEditarUmNcm, Ok");
	}
	
	
//	@Test 
//	public void test08_TentaEditarUmNcmSemMudarNada() {
//		NcmPage ncm = new NcmPage(driver);
//		
////		int i = 0;
//
////		while (i < 30) {
//		testHelper.limpaBanco();
//		
////		i++;
////		System.out.println("test03_TentaEditarUmNCM: De NÚMERO == " + i);
////	}
//		System.out.println("\n"+ this.getClass().getName() + " test07_TentaEditarUmNcmSemMudarNada, Ok");
//	}
	
	@Test
	public void test09_TentaDeletarUmNcm() {
		NcmPage ncmPage = new NcmPage(this.driver);
//		int i = 0
		testHelper.limpaBanco();
		ncmPage.tentaCadastrarUmNcmCorretamente();
		ncmPage.tentaDeletarOPrimeiroRegistroDeNcm();
		System.out.println("\n"+ this.getClass().getName() + " test09_TentaDeletarUmNcm, Ok");
	}
	
	
	@Test
	public void test10_TentaCadastrarUmaOperacaoIncompleta() {
		OperacaoPage operacaoPage = new OperacaoPage(this.driver);
		testHelper.limpaBanco();
		operacaoPage.tentaCadastrarUmaOperacaoSemADescricao();
		System.out.println("\n"+ this.getClass().getName() + " test10_TentaCadastrarUmaOperacaoIncompleta, Ok");

	}
	
	@Test
	public void test11_TentaCadastrarUmaOperacaoCorretamente() {
		OperacaoPage operacaoPage = new OperacaoPage(this.driver);
		testHelper.limpaBanco();
		operacaoPage.tentaCadastrarUmaOperacaoCorretamente();
		System.out.println("\n"+ this.getClass().getName() + " test11_TentaCadastrarUmaOperacaoCorretamente, Ok");
	}
	
	@Test
	public void test12_TentaConsultarUmaOperacaoVazia() {
		OperacaoPage operacaoPage = new OperacaoPage(this.driver);
		testHelper.limpaBanco();
		operacaoPage.tentaConsultarUmaOperacaoVazia();
		System.out.println("\n"+ this.getClass().getName() + " test12_TentaConsultarUmaOperacaoVazia, Ok");
	}
	
}


