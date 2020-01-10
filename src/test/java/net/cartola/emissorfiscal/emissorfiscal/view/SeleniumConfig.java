package net.cartola.emissorfiscal.emissorfiscal.view;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 *	9 de jan de 2020
 *	@author robson.costa
 */
public class SeleniumConfig {
	
	protected WebDriver driver;
	protected static String PATH = "localhost:8080/";
	
	/**
	 * Configurando o <b> "WEB DRIVER" </b> do navegador (no caso o CHROME) usando a lib "WEB DRIVER MANAGER"
	 * pois assim não será necessário baixar os webDriverDeUmBrowserQualquer.EXE no PC
	 * 
	 * @see https://github.com/bonigarcia/webdrivermanager/
	 */
	@BeforeClass
	public static void setupClass() {
		WebDriverManager.chromedriver().setup();
	}
	
	@Before
	public void setupChromeTest() {
		driver = new ChromeDriver();
	}
	
	@After
	public void tearDown() {
		if (driver != null) {
			driver.close();
		}
	}
	
	// Métodos Auxiliares
	
	protected void goToHome() {
		driver.get(PATH);
		System.out.println("Entrando no link (home): " +PATH);
	}
	
	protected void goToTelaDeCadastro(String nomeEntidade) {
		WebElement element = driver.findElement(By.id("navbarDropdownCadastro"));
		element.click();
		element = driver.findElement(By.id("dropDownCadastro" +nomeEntidade));
		element.click();
		System.out.println("Entrando na tela de CADASTRO DE: " +nomeEntidade);
	}
	
	protected void goToTelaDeConsulta(String nomeEntidade) {
		WebElement element = driver.findElement(By.id("navbarDropdownConsulta"));
		element.click();
		element = driver.findElement(By.id("dropDownConsulta" +nomeEntidade));
		element.click();
		System.out.println("Entrando na tela de CONSULTA DE: " +nomeEntidade);
	}
	
}


