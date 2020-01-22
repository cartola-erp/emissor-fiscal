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
 * 21 de jan de 2020
 * @author robson.costa
 */
public class OperacaoPage {
 
	@FindBy(id = "txtDescricao")
	private WebElement txtDescricao;
	
	@FindBy(id = "txtDescricaoOperacao") // Descrição informada na tela de consulta
	private WebElement txtDescricaoOperacao;
	
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
	
	@FindBy(xpath = "/html/body/main/section/div/div/div/div/div/div/div/table/tbody/tr/td[4]/form/button")
	private WebElement btnDeletarPrimeiroRegistro;
	
	public static String OPERACAO_TITLE_PAGE_CADASTRO = "Cadastro de operação";
	public static String OPERACAO_TITLE_PAGE_CONSULTA = "Consulta de operação";
	// As msgs abaixo, é uma parte da msg que aparece para o usuário, e não a msg completa
	public static String OPERACAO_MSG_ALTERADA_CADASTRADA = "alterado/cadastrado";
	public static String OPERACAO_MSG_DELETADO_SUCESSO = "deletada com sucesso";

//	public static String OPERACAO_MSG_CADASTRO_REPETIDO_ERRO = "Já existe essa combinação"; 

	public static String OPERACAO_DESCRICAO_01 = "Venda Estadual";
	public static String OPERACAO_DESCRICAO_01_EDITADA = "A descrição dessa operação foi ALTERADA";
	
	private WebDriver driver;
	
	public OperacaoPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	// ======================================= CADASTROS ============================================

	public void tentaCadastrarUmaOperacaoSemADescricao() {
		PageUtil.goToHome(driver);
		PageUtil.goToTelaDeCadastro(driver, "Operacao");
		PageUtil.espereOWebElementAparecer(txtDescricao, driver, 3L);
		PageUtil.espereUmTempo(600L);
		btnCadastrarAlterar.click();
		PageUtil.espereUmTempo(750L);
		PageUtil.verificaSeApareceuMsgDeErro(OPERACAO_TITLE_PAGE_CADASTRO, this.driver, "DESCRIÇÃO");
	}
	
	public void tentaCadastrarUmaOperacaoCorretamente() {
		PageUtil.goToHome(driver);
		PageUtil.goToTelaDeCadastro(this.driver, "Operacao");
		PageUtil.espereOWebElementAparecer(txtDescricao, this.driver, 3L);
		PageUtil.preencheTxt(txtDescricao, OPERACAO_DESCRICAO_01, 500L);
		assertEquals(OPERACAO_DESCRICAO_01, txtDescricao.getAttribute("value"));
		btnCadastrarAlterar.click();
		PageUtil.verificaSeApareceuMsgDeSucesso(OPERACAO_TITLE_PAGE_CADASTRO, this.driver);
	}
	
	// ======================================= CONSULTAS ============================================

	public void tentaConsultarUmaOperacaoVazia() {
		PageUtil.goToHome(this.driver);
		PageUtil.goToTelaDeConsulta(this.driver, "Operacao");
		PageUtil.espereOWebElementAparecer(btnPesquisar, this.driver, 3L);
		PageUtil.espereUmTempo(600L);
		btnPesquisar.click();
		assertTrue(btnPesquisar.isDisplayed());
		assertTrue(btnPesquisar.isEnabled());
	}
	
	public void tentaConsultarUmaOperacaoExistente() {
		PageUtil.goToHome(this.driver);
		PageUtil.goToTelaDeConsulta(this.driver, "Operacao");
		PageUtil.espereOWebElementAparecer(btnPesquisar, this.driver, 3L);
		PageUtil.preencheTxt(txtDescricaoOperacao, OPERACAO_DESCRICAO_01, 750L);
		btnPesquisar.click();
		PageUtil.espereUmTempo(600L);
		btnEditarPrimeiroRegistro.click();
		assertEquals(OPERACAO_DESCRICAO_01, txtDescricao.getAttribute("value"));
	}
	
	public void tentaConsultarUmaOperacaoInexistente() {
		PageUtil.goToHome(this.driver);
		PageUtil.goToTelaDeConsulta(this.driver, "Operacao");
		PageUtil.espereOWebElementAparecer(btnPesquisar, this.driver, 3L);
		PageUtil.preencheTxt(txtDescricaoOperacao, "R0B$0n", 750L);
		btnPesquisar.click();
		PageUtil.espereUmTempo(600L);
		WebElement elementTxtDescricaoOperacao = driver.findElement(By.id("txtDescricaoOperacao"));
		assertThat(elementTxtDescricaoOperacao.isDisplayed());
		assertThat(elementTxtDescricaoOperacao.isEnabled());
	}
	
	
	// ======================================= EDITAR ============================================
	
	public void tentaEditarOPrimeiroRegistroDeUmaOperacao() {
		PageUtil.goToHome(this.driver);
		PageUtil.goToTelaDeConsulta(this.driver, "Operacao");
		PageUtil.espereOWebElementAparecer(btnEditarPrimeiroRegistro, this.driver, 3L);
		PageUtil.espereUmTempo(700L);
		btnEditarPrimeiroRegistro.click();
		
		txtDescricao.clear();
		PageUtil.preencheTxt(txtDescricao, OPERACAO_DESCRICAO_01_EDITADA, 500L);
		btnCadastrarAlterar.click();
		PageUtil.espereUmTempo(750L);
		PageUtil.verificaSeApareceuMsgDeSucesso(OPERACAO_TITLE_PAGE_CADASTRO, driver);
		PageUtil.espereUmTempo(500L);
		PageUtil.goToTelaDeConsulta(this.driver, "Operacao");
		PageUtil.espereUmTempo(1000L);
		PageUtil.espereOWebElementAparecer(btnEditarPrimeiroRegistro, this.driver, 3L);
		btnEditarPrimeiroRegistro.click();
		PageUtil.espereUmTempo(500L);
		assertEquals(OPERACAO_DESCRICAO_01_EDITADA, txtDescricao.getAttribute("value"));
	}
	
	// ======================================= DELETES ============================================
	
	public void tentaDeletarOPrimeiroRegistroDeOperacao() {
		PageUtil.goToHome(this.driver);
		PageUtil.goToTelaDeConsulta(this.driver, "Operacao");
		PageUtil.espereOWebElementAparecer(btnEditarPrimeiroRegistro, this.driver, 3L);
		PageUtil.espereUmTempo(700L);
		btnDeletarPrimeiroRegistro.click();
		PageUtil.verificaSeApareceuMsgDeSucesso(OPERACAO_TITLE_PAGE_CONSULTA, this.driver, OPERACAO_MSG_DELETADO_SUCESSO);
	}
	
}

