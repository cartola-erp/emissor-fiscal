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
	public void test01_TentaCadastrarUmNCMIncompleto() {
		NcmPage ncm = new NcmPage(driver);
		int i = 0;
		
		while (i < 1) {
			testHelper.limpaBanco();
			ncm.tentaCadastrarUmNcmIncompleto();
			i++;
			System.out.println("test01_TentaCadastrarUmNCMVazio: De NÚMERO == " +i);
		}
		System.out.println("\n"+ this.getClass().getName() + " test01_TentaCadastrarUmNCMIncompleto, Ok");
	}
	
	@Test
	public void test02_TentaCadastrarUmNcmCorretamente() {
		NcmPage ncm = new NcmPage(driver);
		testHelper.limpaBanco();
		ncm.tentaCadastrarUmNcmCorretamente();
		System.out.println("\n"+ this.getClass().getName() + " test02_TentaCadastrarUmNcmCorretamente, Ok");
	}
	
	@Test
	public void test03_TentaEditarUmNCM() {
		NcmPage ncmPage = new NcmPage(this.driver);
		testHelper.limpaBanco();
		ncmPage.tentaCadastrarUmNcmCorretamente();
		ncmPage.tentaEditarUmNcm();
	}
	
}


