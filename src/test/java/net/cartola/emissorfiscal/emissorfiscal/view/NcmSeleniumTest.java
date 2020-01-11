package net.cartola.emissorfiscal.emissorfiscal.view;

import java.util.concurrent.TimeUnit;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *	9 de jan de 2020
 *	@author robson.costa
 */

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NcmSeleniumTest extends SeleniumConfig {
	
	
	@FindBy(id="txtNumero")
	WebElement txtNumero;
	
	@FindBy(id="txtExcecao")
	WebElement txtExcecao;
	
	@FindBy(id="txtDescricao")
	WebElement txtDescricao;

	@FindBy(id="btnCadastrarAlterar")
	WebElement btnCadastrarAlterar;

	@FindBy(id="spanMensagemErro")
	WebElement spanMensagemErro;
	
	private void preencheOCadastroDeUmNCM() {
		goToTelaDeCadastro("Ncm");
		
//		WebElement element = driver.findElement(By.id("txtNumero"));
		txtNumero.sendKeys("12345678");
//		element = driver.findElement(By.id("txtExcecao"));
		txtExcecao.sendKeys("11");
//		element = driver.findElement(By.id("txtDescricao"));
		txtDescricao.sendKeys("Essa é a descrição 1 de um TESTE realizado pelo SELENIUM");

		btnCadastrarAlterar = driver.findElement(By.id("btnCadastrarAlterar"));
		btnCadastrarAlterar.click();
	}
	
	
	@Test
	public void test01_TentaCadastrarUmNCMVazio() {
		goToHome();
		goToTelaDeCadastro("Ncm");
		
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		WebElement element = driver.findElement(By.id("btnCadastrarAlterar"));
		element.click();
//		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		
		WebDriverWait wait = new WebDriverWait(driver, 5);
	   	ExpectedCondition<Boolean> spanMensagemErroIsDisplayed = displayed -> spanMensagemErro.isDisplayed();
	   	wait.until(spanMensagemErroIsDisplayed);
		
//		element = driver.findElement(By.id("spanMensagemErro"));
		
	   System.out.println("\n"+ this.getClass().getName() + " test01_TentaCadastrarUmNCMVazio, Ok");
	}
	
	@Test
	public void test02_DeveCadastrarUmNCMCorretamente() {
		goToHome();
		preencheOCadastroDeUmNCM();
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		WebElement element = driver.findElement(By.id("spanMensagemSucesso"));
		
		System.out.println("\n"+ this.getClass().getName() + " test02_TentaCadastrarUmNCMCorretamente, Ok");
	}
	
	@Test
	public void test03_DeveFalharAoTentarCadastrarUmNCMRepetido() {
		goToHome();
		preencheOCadastroDeUmNCM();
//		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		WebElement element = driver.findElement(By.id("spanMensagemSucesso"));
		
		System.out.println("\n"+ this.getClass().getName() + " test02_DeveFalharAoTentarCadastrarONCMAnterior, Ok");
	}
	

	// CONSULTAS 
//	@Test
//	public void test02_TentaConsultarUmNCMVazio() {
//		driver.get(PATH + "ncm/consulta");
//		
////		WebElement element = driver.findElement(By.xpath("/html/body/nav/div/div[2]/a"));
////		element.click();
////		
////		element = driver.findElement(By.xpath("/html/body/nav/div/div[2]/div/a[1]"));
////		element.click();
//
//		WebElement element = driver.findElement(By.id("btnPesquisar"));
//		element.click();
//		
//		element = driver.findElement(By.id("spanMensagemErro"));
//		
//		System.out.println("cheguei aqui manolo");
//	}

}


