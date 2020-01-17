package net.cartola.emissorfiscal.emissorfiscal.view;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.TimeUnit;

import org.awaitility.Awaitility;
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
	
	private static String PATH = "localhost:8080/";

	public static void goToHome(WebDriver driver) {
		driver.get(PATH);
		System.out.println("Entrando no link (home): " +PATH);
	}
	
	public static void goToTelaDeCadastro(WebDriver driver, String nomeEntidade) {
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
	
	public static void goToTelaDeConsulta(WebDriver driver, String nomeEntidade) {
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
	
	/**
	 * Método criado para esperar sempre um tempo, antes de começar a preencher o txt, pois assim irá preencher
	 * corretamente o campo (ao invés de preencher apenas uma parte da STRING, "mandada".
	 * 
	 * @param txtName
	 * @param txtValue
	 * @param timeInMillis
	 */
	public static void preencheTxt(WebElement txtName, String txtValue, Long timeInMillis) {
//	   	char[] numeroText = "12345678".toCharArray();
	   	// Quando estou mandando uma STRING direto para o campo, as vezes falta alguns números (por que vai muito rápido) e a solução no momento é essa abaixo
//	   	for (Character letra : numeroText) {
		try {
			Thread.sleep(timeInMillis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		txtName.sendKeys(txtValue);
//		txtName.sendKeys(letra.toString());
	}
	
	public static void verificaSeApareceuMsgDeSucesso(String pageTitle, WebDriver driver ) {
		Awaitility.await().atMost(30, TimeUnit.SECONDS).untilAsserted(() -> {
			assertThat(driver.getTitle()).isEqualTo(pageTitle);
			assertThat(driver.findElement(By.id("spanMensagemSucesso")).getText().contains("alterado/cadastrado"));
		});
	}
	
}


