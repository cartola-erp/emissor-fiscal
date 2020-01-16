package net.cartola.emissorfiscal.emissorfiscal.view;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *	13 de jan de 2020
 *	@author robson.costa
 */
public class PageUtil {
	
protected static String PATH = "localhost:8080/";

	protected void goToHome(WebDriver driver) {
		driver.get(PATH);
		System.out.println("Entrando no link (home): " +PATH);
	}
	
	protected void goToTelaDeCadastro(WebDriver driver, String nomeEntidade) {
		WebElement navBarDropDownCadastro = driver.findElement(By.id("navbarDropdownCadastro"));
		WebElement itemNavBarDropDown = driver.findElement(By.id("dropDownCadastro" +nomeEntidade));

		WebDriverWait wait = new WebDriverWait(driver, 5);
		ExpectedCondition<Boolean> navBarDropDownCadastroIsDisplayed = displayed -> navBarDropDownCadastro.isDisplayed();
	   	wait.until(navBarDropDownCadastroIsDisplayed);
		navBarDropDownCadastro.click();
		
		ExpectedCondition<Boolean> itemNavBarDropDownIsDisplayed = displayed -> itemNavBarDropDown.isDisplayed();
	   	wait.until(itemNavBarDropDownIsDisplayed);
		itemNavBarDropDown.click();
		
		System.out.println("Entrando na tela de CADASTRO DE: " +nomeEntidade);
	}
	
	protected void goToTelaDeConsulta(WebDriver driver, String nomeEntidade) {
		WebElement navBarDropDownConsulta = driver.findElement(By.id("navbarDropdownConsulta"));
		WebElement itemNavBarDropDown = driver.findElement(By.id("dropDownConsulta" +nomeEntidade));

		WebDriverWait wait = new WebDriverWait(driver, 3);
		ExpectedCondition<Boolean> navBarDropDownConsultaIsDisplayed = displayed -> navBarDropDownConsulta.isDisplayed();
		wait.until(navBarDropDownConsultaIsDisplayed);
		navBarDropDownConsulta.click();
		
		ExpectedCondition<Boolean> itemNavBarDropDownIsDisplayed = displayed ->  navBarDropDownConsulta.isDisplayed();
		wait.until(itemNavBarDropDownIsDisplayed);
		itemNavBarDropDown.click();
		System.out.println("Entrando na tela de CONSULTA DE: " +nomeEntidade);
	}
	
}


