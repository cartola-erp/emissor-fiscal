package net.cartola.emissorfiscal.emissorfiscal.view;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.awaitility.Awaitility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

/**
 *	13 de jan de 2020
 *	@author robson.costa
 */
public class PageUtil {
	
	private static String PATH = "localhost:8080/";
	
	public static void goToHome(WebDriver driver) {
		driver.get(PATH);
		login(driver);
		System.out.println("Entrando no link (home): " +PATH);
	}
	
	private static void login(WebDriver driver) {
		WebElement txtUsername = driver.findElement(By.id("username"));
		WebElement txtPassword = driver.findElement(By.id("password"));
		WebElement bEntrar = driver.findElement(By.id("bEntrar"));
		
		PageUtil.espereOWebElementAparecer(txtUsername, driver, 3L);
		txtUsername.sendKeys("root");
		txtPassword.sendKeys("root");
		bEntrar.click();
	}
	
	public static void deslogar(WebDriver driver) {
		WebElement navbarDropdownLogout = driver.findElement(By.id("navbarDropdownLogout"));
		
		PageUtil.espereOWebElementAparecer(navbarDropdownLogout, driver, 3L);
		navbarDropdownLogout.click();
	}
	
	public static void goToTelaDeCadastro(WebDriver driver, String nomeEntidade) {
		WebElement navBarDropDownCadastro = driver.findElement(By.id("navbarDropdownCadastro"));
		WebElement itemNavBarDropDown = driver.findElement(By.id("dropDownCadastro" +nomeEntidade));
		
		PageUtil.espereOWebElementAparecer(navBarDropDownCadastro, driver, 5L);
		navBarDropDownCadastro.click();
		
		PageUtil.espereOWebElementAparecer(itemNavBarDropDown, driver, 5L);
		itemNavBarDropDown.click();
		
		System.out.println("Entrando na tela de CADASTRO DE: " +nomeEntidade);
	}
	
	public static void goToTelaDeConsulta(WebDriver driver, String nomeEntidade) {
		WebElement navBarDropDownConsulta = driver.findElement(By.id("navbarDropdownConsulta"));
		WebElement itemNavBarDropDown = driver.findElement(By.id("dropDownConsulta" +nomeEntidade));
		
		PageUtil.espereOWebElementAparecer(navBarDropDownConsulta, driver, 3L);
		navBarDropDownConsulta.click();
		
		PageUtil.espereOWebElementAparecer(itemNavBarDropDown, driver, 3L);
		itemNavBarDropDown.click();
		System.out.println("Entrando na tela de CONSULTA DE: " +nomeEntidade);
	}
	
	/**
	 * Método criado para esperar sempre um tempo, antes de começar a preencher o txt, pois assim irá preencher
	 * corretamente o campo (ao invés de preencher apenas uma parte da STRING, "mandada".
	 * 
	 * @see https://stackoverflow.com/questions/42891515/how-to-slow-down-the-speed-of-send-keys-action-in-a-selenium-script-using-python
	 * @param txtName
	 * @param txtValue
	 * @param timeInMillis
	 * 
	 */
	public static void preencheTxt(WebElement txtName, String txtValue, Long timeInMillis) {
	   	// Quando estou mandando uma STRING direto para o campo, as vezes falta alguns números (por que vai muito rápido) e a solução no momento é essa abaixo
		espereUmTempo(timeInMillis);
		txtName.sendKeys(txtValue);
	}
	
	/**
	 * @param timeInMillis - the length of time to sleep in milliseconds
	 */
	public static void espereUmTempo(Long timeInMillis) {
		if (timeInMillis == null || timeInMillis == 0) {
			timeInMillis = 1000L;
		}
		try {
			Thread.sleep(timeInMillis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void espereOWebElementAparecer(WebElement element, WebDriver driver, Long timeOutInSeconds) {
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		ExpectedCondition<Boolean> elementIsDisplayed = displayed -> element.isDisplayed();
		wait.until(elementIsDisplayed);
	}
	
	public static void verificaSeApareceuMsgDeSucesso(String pageTitle, WebDriver driver ) {
		boolean isMsgSucess = driver.findElement(By.id("spanMensagemSucesso")).getText().contains("alterado/cadastrado");
		Awaitility.await().atMost(30, TimeUnit.SECONDS).untilAsserted(() -> {
			assertThat(driver.getTitle()).isEqualTo(pageTitle);
//			assertThat(driver.findElement(By.id("spanMensagemSucesso")).getText().contains("alterado/cadastrado"));
			assertTrue(isMsgSucess);
		});
	}
	
	public static void verificaSeApareceuMsgDeSucesso(String pageTitle, WebDriver driver, String message ) {
		boolean isMsgSucess = driver.findElement(By.id("spanMensagemSucesso")).getText().contains(message);
		Awaitility.await().atMost(30, TimeUnit.SECONDS).untilAsserted(() -> {
			assertThat(driver.getTitle()).isEqualTo(pageTitle);
			assertTrue(isMsgSucess);
		});
	}
	
	// "Colocar em todas as mensagens de de Erro, a palavra "Erro"
	public static void verificaSeApareceuMsgDeErro(String pageTitle, WebDriver driver) {
		boolean isMsgError = driver.findElement(By.id("spanMensagemErro")).getText().contains("Erro");
		Awaitility.await().atMost(30, TimeUnit.SECONDS).untilAsserted(() -> {
			assertThat(driver.getTitle()).isEqualTo(pageTitle);
			assertTrue(isMsgError);
		});
	}
	
	public static void verificaSeApareceuMsgDeErro(String pageTitle, WebDriver driver, String message) {
		boolean isMsgError = driver.findElement(By.id("spanMensagemErro")).getText().contains(message);
		Awaitility.await().atMost(30, TimeUnit.SECONDS).untilAsserted(() -> {
			assertThat(driver.getTitle()).isEqualTo(pageTitle);
			assertTrue(isMsgError);
		});
	}

	
}


