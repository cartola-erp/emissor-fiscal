package net.cartola.emissorfiscal.view;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.TimeUnit;

import org.awaitility.Awaitility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 *	9 de jan de 2020
 *	@author robson.costa
 */

public class NcmPage {
		
	@FindBy(id = "txtNumero")
	private WebElement txtNumero;

	@FindBy(id = "txtExcecao")
	private WebElement txtExcecao;

	@FindBy(id = "txtDescricao")
	private WebElement txtDescricao;

	@FindBy(id = "spanMensagemErro")
	private WebElement spanMensagemErro;
	
	@FindBy(id = "spanMensagemSucesso")
	private WebElement spanMensagemSucesso;

	@FindBy(id = "btnCadastrarAlterar")
	private WebElement btnCadastrarAlterar;
	
	@FindBy(id = "btnPesquisar")
	private WebElement btnPesquisar;
	
	@FindBy(xpath = "/html/body/main/section/div/div/div/div/div/div/div/table/tbody/tr/td[4]/a/button")
	private WebElement btnEditarPrimeiroRegistro;
	
	@FindBy(xpath = "/html/body/main/section/div/div/div/div/div/div/div/table/tbody/tr/td[5]/form/button")
	private WebElement btnDeletarPrimeiroRegistro;
	
	public static String NCM_TITLE_PAGE_CADASTRO = "Cadastro de NCM";
	public static String NCM_TITLE_PAGE_CONSULTA = "Consulta de NCM";
	// As msgs abaixo, é uma parte da msg que aparece para o usuário, e não a msg completa
	public static String NCM_MSG_ALTERADA_CADASTRADA = "alterado/cadastrado";
	public static String NCM_MSG_DELETADO_SUCESSO = "deletado com sucesso";
	public static String NCM_MSG_CADASTRO_REPETIDO_ERRO = "Já existe essa combinação"; 
	
	public static String NCM_NUMERO_01 = "12345678";
	public static String NCM_EXCECAO_01 = "11";
	public static String NCM_DESCRICAO_01 = "Produtos para as empresas de Auto Peças";
	public static String NCM_DESCRICAO_01_EDITADA = "A descrição desse NCM foi ALTERADA";
	
	private WebDriver driver;

	
	public NcmPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	// ======================================= CADASTROS ============================================
	
	private void preencheOCadastroDeUmNCMSemADescricao() {
		PageUtil.goToHome(driver);
		PageUtil.goToTelaDeCadastro(driver, "Ncm");
		
		PageUtil.espereOWebElementAparecer(txtNumero, this.driver, 3L);
	   	txtNumero.clear();
	   	PageUtil.preencheTxt(txtNumero, NCM_NUMERO_01, 1000L);
		assertEquals(NCM_NUMERO_01, txtNumero.getAttribute("value"));
		PageUtil.espereOWebElementAparecer(txtExcecao, this.driver, 3L);
		txtExcecao.clear();
	   	PageUtil.preencheTxt(txtExcecao, NCM_EXCECAO_01, 100L);
	   	assertEquals(NCM_EXCECAO_01, txtExcecao.getAttribute("value"));
	}
	
	
	public void tentaCadastrarUmNcmIncompleto() {
		preencheOCadastroDeUmNCMSemADescricao();
		PageUtil.espereOWebElementAparecer(btnCadastrarAlterar, this.driver, 3L);
		btnCadastrarAlterar.click();
		
		Awaitility.await().atMost(30, TimeUnit.SECONDS).untilAsserted(() -> {
			assertThat(this.driver.getTitle()).isEqualTo(NCM_TITLE_PAGE_CADASTRO);
			assertThat(this.driver.findElement(By.xpath("//*[@id=\"spanMensagemErro\"]/div[1]"))
					.getText()).contains("É obrigatória uma DESCRIÇÃO");
		});
		System.out.println("\n"+ this.getClass().getName() + " test01_TentaCadastrarUmNCMVazio, Ok");
	}
	
	public void tentaCadastrarUmNcmCorretamente() {
		preencheOCadastroDeUmNCMSemADescricao();
		PageUtil.espereUmTempo(750L);
		PageUtil.preencheTxt(txtDescricao, NCM_DESCRICAO_01, 500L);
		PageUtil.espereUmTempo(750L);
		assertEquals(NCM_DESCRICAO_01, txtDescricao.getAttribute("value"));
		btnCadastrarAlterar.click();
		PageUtil.verificaSeApareceuMsgDeSucesso(NCM_TITLE_PAGE_CADASTRO, driver);
		PageUtil.espereUmTempo(750L);
		PageUtil.deslogar(driver);
		System.out.println("\n"+ this.getClass().getName() + " test02_TentaCadastrarUmNCMCorretamente, Ok");
	}
	
	public void tentaCadastrarUmNcmRepetido() {
		tentaCadastrarUmNcmCorretamente();
		tentaCadastrarUmNcmCorretamente();
		PageUtil.verificaSeApareceuMsgDeErro(NCM_TITLE_PAGE_CADASTRO, this.driver, NCM_MSG_CADASTRO_REPETIDO_ERRO);
	}
	
	// ======================================= CONSULTAS ============================================

	public void tentaConsultarUmNcmVazio() {
		PageUtil.goToHome(this.driver);
		PageUtil.goToTelaDeConsulta(this.driver, "Ncm");
		PageUtil.espereOWebElementAparecer(btnPesquisar, this.driver, 3L);
		PageUtil.espereUmTempo(600L);
		btnPesquisar.click();
		PageUtil.espereUmTempo(600L);
		PageUtil.verificaSeApareceuMsgDeErro(NCM_TITLE_PAGE_CONSULTA, this.driver);
	}
	
	public void tentaConsultarUmNcmExistente() {
		PageUtil.goToHome(this.driver);
		PageUtil.goToTelaDeConsulta(this.driver, "Ncm");
		PageUtil.espereOWebElementAparecer(txtNumero, this.driver, 3L);
		PageUtil.preencheTxt(txtNumero, NCM_NUMERO_01, 600L);
		btnPesquisar.click();
		PageUtil.espereUmTempo(600L);
		btnEditarPrimeiroRegistro.click();
		assertEquals(NCM_NUMERO_01, txtNumero.getAttribute("value"));
	}
	
	public void tentaConsultaUmNcmInexistente() {
		PageUtil.goToHome(this.driver);
		PageUtil.goToTelaDeConsulta(this.driver, "Ncm");
		PageUtil.espereOWebElementAparecer(txtNumero, this.driver, 3L);
		PageUtil.preencheTxt(txtNumero, "h@ir87654321", 600L);
		PageUtil.espereUmTempo(600L);
		btnPesquisar.click();
		WebElement element = driver.findElement(By.id("txtNumero"));
		assertThat(element.isDisplayed());
		assertThat(element.isEnabled());
	}
	
	// ======================================= EDITAR ============================================
	
	public void tentaEditarOPrimeiroRegistroDeUmNcm() {
		PageUtil.goToHome(this.driver);
		PageUtil.goToTelaDeConsulta(this.driver, "Ncm");
		// USar o awailtility
//		PageUtil.verificaSeApareceuMsgDeSucesso(NCM_TITLE_PAGE_CONSULTA, driver);
		PageUtil.espereOWebElementAparecer(btnEditarPrimeiroRegistro, this.driver, 3L);
		PageUtil.espereUmTempo(700L);
		btnEditarPrimeiroRegistro.click();
		
		txtDescricao.clear();
		PageUtil.preencheTxt(txtDescricao, NCM_DESCRICAO_01_EDITADA, 500L);
		btnCadastrarAlterar.click();
		PageUtil.verificaSeApareceuMsgDeSucesso(NCM_TITLE_PAGE_CADASTRO, driver);
		
		PageUtil.espereUmTempo(500L);
		PageUtil.goToTelaDeConsulta(this.driver, "Ncm");
		PageUtil.espereUmTempo(1000L);
		PageUtil.espereOWebElementAparecer(btnEditarPrimeiroRegistro, this.driver, 3L);
		btnEditarPrimeiroRegistro.click();
		PageUtil.espereUmTempo(500L);
		assertEquals(NCM_DESCRICAO_01_EDITADA, txtDescricao.getAttribute("value"));
		
		System.out.println("\n"+ this.getClass().getName() + " test03_TentaEditarUmNCM, Ok");
	}
	
	// ======================================= DELETES ============================================
	
	public void tentaDeletarOPrimeiroRegistroDeNcm() {
		PageUtil.goToHome(this.driver);
		PageUtil.goToTelaDeConsulta(this.driver, "Ncm");
		PageUtil.espereOWebElementAparecer(btnEditarPrimeiroRegistro, this.driver, 3L);
		PageUtil.espereUmTempo(700L);
		btnDeletarPrimeiroRegistro.click();
		PageUtil.verificaSeApareceuMsgDeSucesso(NCM_TITLE_PAGE_CONSULTA, this.driver, NCM_MSG_DELETADO_SUCESSO);
	}


}


