package net.cartola.emissorfiscal.emissorfiscal.view;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.WebDriver;
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
//		int i = 0;
		
//		while (i <= 5) {
			testHelper.limpaBanco();
			ncm.preencheOCadastroDeUmNCMSemADescricao();
//			i++;
//			System.out.println("test01_TentaCadastrarUmNCMVazio: De NÚMERO == " +i);
//		}
	}
	
}


