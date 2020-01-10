package net.cartola.emissorfiscal.emissorfiscal.view;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;
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
	
	protected void goToHome() {
		driver.get(PATH);
		System.out.println("Abrindo o link (home): " +PATH);
	}
	
}


