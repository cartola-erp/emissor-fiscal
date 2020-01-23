package net.cartola.emissorfiscal.emissorfiscal.view;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * 22 de jan de 2020
 * @author robson.costa
 */
public class EstadoPage {
	
	@FindBy(id = "txtSigla")
	private WebElement txtSigla;
	
	@FindBy(id = "txtNome")
	private WebElement txtNome;
	
	@FindBy(id = "spanMensagemErro")
	private WebElement spanMensagemErro;
	
	@FindBy(id = "spanMensagemSucesso")
	private WebElement spanMensagemSucesso;

	@FindBy(id = "btnCadastrarAlterar")
	private WebElement btnCadastrarAlterar;
	
	@FindBy(id = "btnPesquisar")
	private WebElement btnPesquisar;
	
	@FindBy(xpath = "/html/body/main/section/div/div/div/div/div/div/div/table/tbody/tr/td[3]/a/button")
	private WebElement btnEditarPrimeiroRegistro;
	
	@FindBy(xpath = "/html/body/main/section/div/div/div/div/div/div/div/table/tbody/tr/td[4]/form")
	private WebElement btnDeletarPrimeiroRegistro;
	
	public static String ESTADO_TITLE_PAGE_CADASTRO = "Cadastro de estado";
	public static String ESTADO_TITLE_PAGE_CONSULTA = "Consulta de Estado";
	// As msgs abaixo, é uma parte da msg que aparece para o usuário, e não a msg completa
	public static String ESTADO_MSG_ALTERADA_CADASTRADA = "alterado/cadastrado";
	public static String ESTADO_MSG_DELETADO_SUCESSO = "deletado com sucesso";
	public static String ESTADO_MSG_CONSULTA_ERRO = "A sigla informada";
	
	public static String ESTADO_MSG_CADASTRO_REPETIDO_ERRO = "já está cadastrado"; 

	public static String ESTADO_SIGLA_SP = "SP";
	public static String ESTADO_NOME_SP = "São Paulo";
	
	public static String ESTADO_NOME_SP_EDITADA = "UF São Pavio edit";
	
	private WebDriver driver;
	
	
	public EstadoPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	private void preencheOCadastroDoEstadoDeSP() {
		PageUtil.goToHome(driver);
		PageUtil.goToTelaDeCadastro(this.driver, "Estado");
		PageUtil.espereOWebElementAparecer(txtSigla, this.driver, 3L);
		txtSigla.clear();
		PageUtil.preencheTxt(txtSigla, ESTADO_SIGLA_SP, 500L);
		assertEquals(ESTADO_SIGLA_SP, txtSigla.getAttribute("value"));
		PageUtil.preencheTxt(txtNome, ESTADO_NOME_SP, 750L);
		assertEquals(ESTADO_NOME_SP, txtNome.getAttribute("value"));
		btnCadastrarAlterar.click();
	}
	
	// ======================================= CADASTROS ============================================

	public void tentaCadastrarUmEstadoSemONome() {
		PageUtil.goToHome(driver);
		PageUtil.goToTelaDeCadastro(driver, "Estado");
		PageUtil.espereOWebElementAparecer(txtSigla, driver, 3L);
		PageUtil.espereUmTempo(600L);
		btnCadastrarAlterar.click();
		PageUtil.espereUmTempo(750L);
		assertThat(txtSigla.isDisplayed());
		assertThat(txtSigla.isEnabled());
		assertThat(txtNome.isDisplayed());
		assertThat(txtNome.isEnabled());
		assertThat(btnCadastrarAlterar.isDisplayed());
		assertThat(btnCadastrarAlterar.isEnabled());
	}
	
	public void tentaCadastrarEstadoSPCorretamente() {
		preencheOCadastroDoEstadoDeSP();
		PageUtil.verificaSeApareceuMsgDeSucesso(ESTADO_TITLE_PAGE_CADASTRO, this.driver);
	}
	
	public void tentaCadastrarEstadoQueNaoExista() {
		PageUtil.goToHome(driver);
		PageUtil.goToTelaDeCadastro(this.driver, "Estado");
		PageUtil.espereOWebElementAparecer(txtSigla, this.driver, 3L);
		txtSigla.clear();
		PageUtil.preencheTxt(txtSigla, "NY", 500L);
		assertEquals("NY", txtSigla.getAttribute("value"));
		PageUtil.preencheTxt(txtNome, "O ESTADO Xie Xie NÃO Existe", 750L);
//		assertEquals(ESTADO_NOME_SP, txtNome.getAttribute("value"));
		btnCadastrarAlterar.click();
		PageUtil.verificaSeApareceuMsgDeErro(ESTADO_TITLE_PAGE_CADASTRO, this.driver, "Failed");
	}
	
	public void tentaCadastrarEstadoSPDuasVezes() {
		preencheOCadastroDoEstadoDeSP();
		preencheOCadastroDoEstadoDeSP();
		PageUtil.verificaSeApareceuMsgDeErro(ESTADO_TITLE_PAGE_CADASTRO, this.driver, ESTADO_MSG_CADASTRO_REPETIDO_ERRO);
	}
	
	// ======================================= CONSULTAS ============================================

	public void tentaConsultarUmEstadoVazio() {
		PageUtil.goToHome(this.driver);
		PageUtil.goToTelaDeConsulta(this.driver, "Estado");
		PageUtil.espereOWebElementAparecer(btnPesquisar, this.driver, 3L);
		PageUtil.espereUmTempo(600L);
		btnPesquisar.click();
		assertTrue(btnPesquisar.isDisplayed());
		assertTrue(btnPesquisar.isEnabled());
	}
	
	public void tentaConsultarEstadoSPExistente() {
		PageUtil.goToHome(this.driver);
		PageUtil.goToTelaDeConsulta(this.driver, "Estado");
		PageUtil.espereOWebElementAparecer(btnPesquisar, this.driver, 3L);
		PageUtil.preencheTxt(txtSigla, ESTADO_SIGLA_SP, 750L);
		btnPesquisar.click();
		PageUtil.espereUmTempo(600L);
		btnEditarPrimeiroRegistro.click();
		assertEquals(ESTADO_SIGLA_SP, txtSigla.getAttribute("value"));
	}
	
	public void tentaConsultarUmEstadoNaoCadastrado() {
		PageUtil.goToHome(this.driver);
		PageUtil.goToTelaDeConsulta(this.driver, "Estado");
		PageUtil.espereOWebElementAparecer(btnPesquisar, this.driver, 3L);
		PageUtil.preencheTxt(txtSigla, "ac0", 750L);
		btnPesquisar.click();
		PageUtil.espereUmTempo(600L);
		WebElement elementTxtSigla = driver.findElement(By.id("txtSigla"));
		assertThat(elementTxtSigla.isDisplayed());
		assertThat(elementTxtSigla.isEnabled());
	}

	public void tentaConsultarUmEstadoInexistente() {
		PageUtil.goToHome(this.driver);
		PageUtil.goToTelaDeConsulta(this.driver, "Estado");
		PageUtil.espereOWebElementAparecer(btnPesquisar, this.driver, 3L);
		PageUtil.preencheTxt(txtSigla, "NaO eXiste esse estado", 750L);
		btnPesquisar.click();
		PageUtil.espereUmTempo(600L);
		WebElement elementTxtSigla = driver.findElement(By.id("txtSigla"));
		assertThat(elementTxtSigla.isDisplayed());
		assertThat(elementTxtSigla.isEnabled());
		PageUtil.verificaSeApareceuMsgDeErro(ESTADO_TITLE_PAGE_CONSULTA, this.driver, ESTADO_MSG_CONSULTA_ERRO);
	}
	
	// ======================================= EDITAR ============================================
	
	public void tentaEditarOPrimeiroRegistroDeUmEstado() {
		PageUtil.goToHome(this.driver);
		PageUtil.goToTelaDeConsulta(this.driver, "Estado");
		PageUtil.espereOWebElementAparecer(btnEditarPrimeiroRegistro, this.driver, 3L);
		PageUtil.espereUmTempo(700L);
		btnEditarPrimeiroRegistro.click();
		txtNome.clear();
		PageUtil.preencheTxt(txtNome, ESTADO_NOME_SP_EDITADA, 500L);
		btnCadastrarAlterar.click();
		
		PageUtil.espereUmTempo(750L);
		PageUtil.verificaSeApareceuMsgDeSucesso(ESTADO_TITLE_PAGE_CADASTRO, driver);
		PageUtil.espereUmTempo(500L);
		PageUtil.goToTelaDeConsulta(this.driver, "Estado");
		PageUtil.espereUmTempo(1000L);
		PageUtil.espereOWebElementAparecer(btnEditarPrimeiroRegistro, this.driver, 3L);
		btnEditarPrimeiroRegistro.click();
		PageUtil.espereUmTempo(500L);
		assertEquals(ESTADO_NOME_SP_EDITADA, txtNome.getAttribute("value"));
	}
	
	// ======================================= DELETES ============================================
	
	public void tentaDeletarOPrimeiroRegistroDeEstado() {
		PageUtil.goToHome(this.driver);
		PageUtil.goToTelaDeConsulta(this.driver, "Estado");
		PageUtil.espereOWebElementAparecer(btnEditarPrimeiroRegistro, this.driver, 3L);
		PageUtil.espereUmTempo(700L);
		btnDeletarPrimeiroRegistro.click();
		PageUtil.verificaSeApareceuMsgDeSucesso(ESTADO_TITLE_PAGE_CONSULTA, this.driver, ESTADO_MSG_DELETADO_SUCESSO);
	}
}

