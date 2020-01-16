package net.cartola.emissorfiscal.emissorfiscal.view;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.awaitility.Awaitility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *	9 de jan de 2020
 *	@author robson.costa
 */

//@ActiveProfiles("test")
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
//@PageObject
public class NcmPage extends PageUtil {

	@FindBy(id = "txtNumero")
	private WebElement txtNumero;

	@FindBy(id = "txtExcecao")
	private WebElement txtExcecao;

	@FindBy(id = "txtDescricao")
	private WebElement txtDescricao;
//
	@FindBy(id = "spanMensagemErro")
	private WebElement spanMensagemErro;
	
//	@FindBy(xpath = "/html/body/main/div/div")
//	private WebElement spanMensagemErro;

	@FindBy(id = "spanMensagemSucesso")
	private WebElement spanMensagemSucesso;

	@FindBy(id = "btnCadastrarAlterar")
	private WebElement btnCadastrarAlterar;
	
	private WebDriver driver;

	public NcmPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	private void preencheOCadastroDeUmNCMIncompleto() {
		goToHome(driver);
		goToTelaDeCadastro(driver, "Ncm");

		WebDriverWait wait = new WebDriverWait(driver, 5);
	   	ExpectedCondition<Boolean> txtNumeroIsDisplayed = displayed -> txtNumero.isDisplayed();
	   	wait.until(txtNumeroIsDisplayed);
	   	
	   	txtNumero.clear();
	   	char[] numeroText = "12345678".toCharArray();
	   	// Quando estou mandando uma STRING direto para o campo, as vezes falta alguns números (por que vai muito rápido) e a solução no momento é essa abaixo
	   	for (Character letra : numeroText) {
	   		try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
//			txtNumero.sendKeys("12345678");
			txtNumero.sendKeys(letra.toString());
		}
		
		String valueTxtNumero = txtNumero.getAttribute("value");
		System.out.println("ASSERT do NUMERO: " +valueTxtNumero );
		assertTrue(valueTxtNumero.equalsIgnoreCase("12345678"));
		
	   	ExpectedCondition<Boolean> txtExcecaoIsDisplayed = displayed -> txtExcecao.isDisplayed();
	   	wait.until(txtExcecaoIsDisplayed);
		txtExcecao.clear();
	   	char[] excecaoText = "11".toCharArray();
	   	for (Character letra : excecaoText ) {
	   		try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
//			txtExcecao.sendKeys("11");
	   		txtExcecao.sendKeys(letra.toString());
		}
	   	assertTrue(txtExcecao.getAttribute("value").equalsIgnoreCase("11"));
//		System.out.println("ASSERT da EXCECAO" );
//		btnCadastrarAlterar.click();
	}
	
	
	public void preencheOCadastroDeUmNCMSemADescricao() {
//		goToHome(driver);
//		goToTelaDeCadastro(driver, "Ncm");
		preencheOCadastroDeUmNCMIncompleto();
		
		WebDriverWait wait = new WebDriverWait(driver, 10);
		ExpectedCondition<Boolean> btnCadastrarAlterarIsDisplayed = displayed -> btnCadastrarAlterar.isDisplayed();
		wait.until(btnCadastrarAlterarIsDisplayed);
		btnCadastrarAlterar.click();
		
		System.out.println("\nCliquei no BOTÃO DE CADASTRAR/ALTERAR" );
		
		Awaitility.await().atMost(30, TimeUnit.SECONDS).untilAsserted(() -> {
			assertThat(this.driver.getTitle()).isEqualTo("Cadastro de NCM");
			assertThat(this.driver.findElement(By.xpath("//*[@id=\"spanMensagemErro\"]/div[1]"))
					.getText()).contains("É obrigatória uma DESCRIÇÃO");
		});
		System.out.println("\n"+ this.getClass().getName() + " test01_TentaCadastrarUmNCMVazio, Ok");
	}
	
	
	
//	@Test
//	public void test02_DeveCadastrarUmNCMCorretamente() {
//		goToHome();
//		preencheOCadastroDeUmNCM();
//		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
//		WebElement element = driver.findElement(By.id("spanMensagemSucesso"));
//		
//		System.out.println("\n"+ this.getClass().getName() + " test02_TentaCadastrarUmNCMCorretamente, Ok");
//	}
//	
//	@Test
//	public void test03_DeveFalharAoTentarCadastrarUmNCMRepetido() {
//		goToHome();
//		preencheOCadastroDeUmNCM();
////		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
//		WebElement element = driver.findElement(By.id("spanMensagemSucesso"));
//		
//		System.out.println("\n"+ this.getClass().getName() + " test02_DeveFalharAoTentarCadastrarONCMAnterior, Ok");
//	}
	

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


