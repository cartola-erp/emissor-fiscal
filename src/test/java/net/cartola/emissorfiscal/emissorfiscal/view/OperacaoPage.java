package net.cartola.emissorfiscal.emissorfiscal.view;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
	
	public static String OPERACAO_TITLE_PAGE_CADASTRO = "Cadastro de operação";
	public static String OPERACAO_TITLE_PAGE_CONSULTA = "Consulta de operação";
	// As msgs abaixo, é uma parte da msg que aparece para o usuário, e não a msg completa
	public static String OPERACAO_MSG_ALTERADA_CADASTRADA = "alterado/cadastrado";
	public static String OPERACAO_MSG_DELETADO_SUCESSO = "deletado com sucesso";
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
		
		btnCadastrarAlterar.click();
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
}

