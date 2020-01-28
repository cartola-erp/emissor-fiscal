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
	
	// ======================================= NCM ============================================
	
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
		testHelper.limpaBanco();
		ncmPage.tentaCadastrarUmNcmCorretamente();
		ncmPage.tentaDeletarOPrimeiroRegistroDeNcm();
		System.out.println("\n"+ this.getClass().getName() + " test09_TentaDeletarUmNcm, Ok");
	}
	
	// ======================================= OPERACAO ============================================
	
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
		operacaoPage.tentaCadastrarUmaOperacaoDeVendaEstadual();
		System.out.println("\n"+ this.getClass().getName() + " test11_TentaCadastrarUmaOperacaoCorretamente, Ok");
	}
	
	@Test
	public void test12_TentaConsultarUmaOperacaoVazia() {
		OperacaoPage operacaoPage = new OperacaoPage(this.driver);
		testHelper.limpaBanco();
		operacaoPage.tentaConsultarUmaOperacaoVazia();
		System.out.println("\n"+ this.getClass().getName() + " test12_TentaConsultarUmaOperacaoVazia, Ok");
	}
	
	@Test
	public void test13_TentaConsultarUmaOperacaoExistente() {
		OperacaoPage operacaoPage = new OperacaoPage(this.driver);
		testHelper.limpaBanco();
		operacaoPage.tentaCadastrarUmaOperacaoDeVendaEstadual();
		operacaoPage.tentaConsultarUmaOperacaoExistente();
		System.out.println("\n"+ this.getClass().getName() + " test13_TentaConsultaUmaOperacaoExistente, Ok");
	}
	
	@Test
	public void test14_TentaConsultarUmaOperacaoInexistente() {
		OperacaoPage operacaoPage = new OperacaoPage(this.driver);
		testHelper.limpaBanco();
		operacaoPage.tentaConsultarUmaOperacaoInexistente();
		System.out.println("\n"+ this.getClass().getName() + " test14_TentaConsultaUmaOperacaoInexistente, Ok");
	}
	
	@Test
	public void test15_TentaEditarUmaOperacao() {
		OperacaoPage operacaoPage = new OperacaoPage(this.driver);
		testHelper.limpaBanco();
		operacaoPage.tentaCadastrarUmaOperacaoDeVendaEstadual();
		operacaoPage.tentaEditarOPrimeiroRegistroDeUmaOperacao();
		System.out.println("\n"+ this.getClass().getName() + " test15_TentaEditarUmaOperacao, Ok");
	}
	
	@Test
	public void test16_TentaDeletarUmaOperacao() {
		OperacaoPage operacaoPage = new OperacaoPage(this.driver);
		testHelper.limpaBanco();
		operacaoPage.tentaCadastrarUmaOperacaoDeVendaEstadual();
		operacaoPage.tentaDeletarOPrimeiroRegistroDeOperacao();
		System.out.println("\n"+ this.getClass().getName() + " test16_TentaDeletarUmaOperacao, Ok");
	}
	
	// ======================================= ESTADOS ============================================
	
	@Test
	public void test17_TentaCadastrarUmEstadoIncompleto() {
		EstadoPage estadoPage = new EstadoPage(this.driver);
		testHelper.limpaBanco();
		estadoPage.tentaCadastrarUmEstadoSemONome();
		System.out.println("\n"+ this.getClass().getName() + " test17_TentaCadastrarUmEstadoIncompleto, Ok");
	}
	
	@Test
	public void test18_TentaCadastrarUmEstadoCorretamente() {
		EstadoPage estadoPage = new EstadoPage(this.driver);
		testHelper.limpaBanco();
		estadoPage.tentaCadastrarEstadoSPCorretamente();
		System.out.println("\n"+ this.getClass().getName() + " test18_TentaCadastrarUmEstadoCorretamente, Ok");
	}
	
	@Test
	public void test19_TentaCadastrarUmEstadoQueNaoExista() {
		EstadoPage estadoPage = new EstadoPage(this.driver);
		testHelper.limpaBanco();
		estadoPage.tentaCadastrarEstadoQueNaoExista();
		System.out.println("\n"+ this.getClass().getName() + " test19_TentaCadastrarUmEstadoQueNaoExista, Ok");
	}
	
	@Test
	public void test20_TentaCadastrarUmEstadoRepetido() {
		EstadoPage estadoPage = new EstadoPage(this.driver);
		testHelper.limpaBanco();
		estadoPage.tentaCadastrarEstadoSPDuasVezes();
		System.out.println("\n"+ this.getClass().getName() + " test20_TentaCadastrarUmEstadoRepetido, Ok");
	}
	
	@Test
	public void test21_TentaConsultarUmEstadoVazio() {
		EstadoPage estadoPage = new EstadoPage(this.driver);
		testHelper.limpaBanco();
		estadoPage.tentaConsultarUmEstadoVazio();
		System.out.println("\n"+ this.getClass().getName() + " test21_TentaConsultarUmEstadoVazio, Ok");
	}
	
	@Test
	public void test22_TentaConsultarUmEstadoExistente() {
		EstadoPage estadoPage = new EstadoPage(this.driver);
		testHelper.limpaBanco();
		estadoPage.tentaCadastrarEstadoSPCorretamente();
		estadoPage.tentaConsultarEstadoSPExistente();
		System.out.println("\n"+ this.getClass().getName() + " test22_TentaConsultarUmEstadoExistente, Ok");
	}
	
	@Test
	public void test23_TentaConsultarUmEstadoNaoCadastrado() {
		EstadoPage estadoPage = new EstadoPage(this.driver);
		testHelper.limpaBanco();
		estadoPage.tentaConsultarUmEstadoNaoCadastrado();
		System.out.println("\n"+ this.getClass().getName() + " test23_TentaConsultarUmEstadoNaoCadastrado, Ok");
	}
	
	
	@Test
	public void test24_TentaConsultarUmEstadoInexistente() {
		EstadoPage estadoPage = new EstadoPage(this.driver);
		testHelper.limpaBanco();
		estadoPage.tentaConsultarUmEstadoInexistente();
		System.out.println("\n"+ this.getClass().getName() + " test24_TentaConsultarUmEstadoInexistente, Ok");
	}
	
	@Test
	public void test25_TentaEditarUmEstado() {
		EstadoPage estadoPage = new EstadoPage(this.driver);
		testHelper.limpaBanco();
		estadoPage.tentaCadastrarEstadoSPCorretamente();
		estadoPage.tentaEditarOPrimeiroRegistroDeUmEstado();
		System.out.println("\n"+ this.getClass().getName() + " test25_TentaEditarUmEstado, Ok");
	}
	
	@Test
	public void test26_TentaDeletarUmEstado() {
		EstadoPage estadoPage = new EstadoPage(this.driver);
		testHelper.limpaBanco();
		estadoPage.tentaCadastrarEstadoSPCorretamente();
		estadoPage.tentaDeletarOPrimeiroRegistroDeEstado();
		System.out.println("\n"+ this.getClass().getName() + " test26_TentaDeletarUmEstado, Ok");
	}
	
	// ======================================= TRIBUTACAO ESTADUAL ============================================

	@Test
	public void test27_TentaCadastrarUmaTributacaoEstadualIncompleta() {
		TributacaoEstadualPage icmsPage = new TributacaoEstadualPage(this.driver);
		testHelper.limpaBanco();
		icmsPage.tentaCadastrarUmaTributacaoEstadualSemONcm();
		System.out.println("\n"+ this.getClass().getName() + " test27_TentaCadastrarUmaTributacaoEstadualIncompleta, Ok");
	}
	
	@Test
	public void test28_TentaCadastrarUmaTributacaoEstadualDeSP() {
		TributacaoEstadualPage icmsPage = new TributacaoEstadualPage(this.driver);
		testHelper.limpaBanco();
		icmsPage.tentaCadastrarUmaTributacaoEstadualDeSPparaSP();
		System.out.println("\n"+ this.getClass().getName() + " test28_TentaCadastrarUmaTributacaoEstadualDeSP, Ok");
	}
	
	
	@Test
	public void test29_TentaConsultarUmaTributacaoEstadualVazio() {
		TributacaoEstadualPage icmsPage = new TributacaoEstadualPage(this.driver);
		testHelper.limpaBanco();
		icmsPage.tentaConsultarUmaTributacaoEstadualVazio();
		System.out.println("\n"+ this.getClass().getName() + " test29_TentaConsultarUmaTributacaoEstadualVazio, Ok");
	}


	@Test
	public void test30_TentaConsultarUmaTributacaoEstadualExistente() {
		TributacaoEstadualPage icmsPage = new TributacaoEstadualPage(this.driver);
		testHelper.limpaBanco();
		icmsPage.tentaCadastrarUmaTributacaoEstadualDeSPparaSP();
		icmsPage.tentaConsultarUmaTributacaoEstadualExistente();
		System.out.println("\n"+ this.getClass().getName() + " test30_TentaConsultarUmaTributacaoEstadualExistente, Ok");
	}
	
	@Test
	public void test31_TentaConsultarUmaTributacaoEstadualInexistente() {
		TributacaoEstadualPage icmsPage = new TributacaoEstadualPage(this.driver);
		testHelper.limpaBanco();
		icmsPage.tentaConsultaUmaTributacaoEstadualInexistente();
		System.out.println("\n"+ this.getClass().getName() + " test31_TentaConsultarUmaTributacaoEstadualInexistente, Ok");
	}
	
	@Test
	public void test32_TentaEditarUmaTributacaoEstadual() {
		TributacaoEstadualPage icmsPage = new TributacaoEstadualPage(this.driver);
		testHelper.limpaBanco();
		icmsPage.tentaCadastrarUmaTributacaoEstadualDeSPparaSP();
		icmsPage.tentaEditarOPrimeiroRegistroDeUmaTributacaoEstadual();
		System.out.println("\n"+ this.getClass().getName() + " test32_TentaEditarUmaTributacaoEstadual, Ok");
	}
	
//	@Test
//	public void test33_TentaDeletarUmaTributatacaoEstadual() {
//		TributacaoEstadualPage icmsPage = new TributacaoEstadualPage(this.driver);
//		testHelper.limpaBanco();
//		icmsPage.tentaCadastrarUmaTributacaoEstadualDeSPparaSP();
//		icmsPage.tentaDeletarOPrimeiroRegistroDeUmaTributacaoEstadual();
//		System.out.println("\n"+ this.getClass().getName() + " test33_TentaDeletarUmaTributatacaoEstadual, Ok");
//	}

	// ===========================================================================================================
	// ======================================= TRIBUTACAO FEDERAL ============================================
	// ===========================================================================================================

	//	EstadoPage estadoPage = new EstadoPage(this.driver);
//	testHelper.limpaBanco();
//	estadoPage.tentaCadastrarEstadoSPCorretamente();
//	estadoPage.tentaDeletarOPrimeiroRegistroDeEstado();
//	System.out.println("\n"+ this.getClass().getName() + " test26_TentaDeletarUmEstado, Ok");
//}
	
//	@Test
//	public void test24_TentaConsultarUmEstadoInexistente() {
//		EstadoPage estadoPage = new EstadoPage(this.driver);
//		testHelper.limpaBanco();
//		estadoPage.tentaConsultarUmEstadoInexistente();
//		System.out.println("\n"+ this.getClass().getName() + " test24_TentaConsultarUmEstadoInexistente, Ok");
//	}

	//	@Test
//	public void test12_TentaConsultarUmaOperacaoVazia() {
//		OperacaoPage operacaoPage = new OperacaoPage(this.driver);
//		testHelper.limpaBanco();
//		operacaoPage.tentaConsultarUmaOperacaoVazia();
//		System.out.println("\n"+ this.getClass().getName() + " test12_TentaConsultarUmaOperacaoVazia, Ok");
//	}
//	
//	@Test
//	public void test13_TentaConsultarUmaOperacaoExistente() {
//		OperacaoPage operacaoPage = new OperacaoPage(this.driver);
//		testHelper.limpaBanco();
//		operacaoPage.tentaCadastrarUmaOperacaoCorretamente();
//		operacaoPage.tentaConsultarUmaOperacaoExistente();
//		System.out.println("\n"+ this.getClass().getName() + " test13_TentaConsultaUmaOperacaoExistente, Ok");
//	}
//	
//	
	
	// ======================================= TRIBUTACAO FEDERAL ============================================

}


