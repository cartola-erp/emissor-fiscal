package net.cartola.emissorfiscal.emissorfiscal.view;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 *	9 de jan de 2020
 *	@author robson.costa
 */
public class SeleniumConfig {
	
	protected WebDriver driver;
	
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
//		ChromeOptions options = new ChromeOptions();
//        options.addArguments("--headless");
//        driver = new ChromeDriver(options);
		driver = new ChromeDriver();
		driver.manage().window().maximize();
	}
	
	@After
	public void tearDown() {
		if (driver != null) {
			driver.close();
		}
	}
	
	public WebDriver getDriver() {
		return driver;
	}
	
	
}


