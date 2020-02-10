package net.cartola.emissorfiscal.emissorfiscal.view;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

/**
 * 22 de jan de 2020
 * @author robson.costa
 */
public class TributacaoEstadualPage {
	
	@FindBy(name = "ufOrigemId")
	private WebElement selectUfOrigemId;
	
	@FindBy(name = "ufDestinoId")
	private WebElement selectUfDestinoId;
	
	@FindBy(name = "operacaoId")
	private WebElement selectOperacao;
	
	@FindBy(name = "ncmId")
	private WebElement selectNcm;
	
	@FindBy(id = "txtIcmsCst")
	private WebElement txtIcmsCst;
	
	@FindBy(id = "txtIcmsBase")
	private WebElement txtIcmsBase;
	
	@FindBy(id = "txtIcmsAliquota")
	private WebElement txtIcmsAliquota;
	
	@FindBy(id = "txtIcmsIva")
	private WebElement txtIcmsIva;
	
	@FindBy(id = "txtIcmsAliquotaDestino")
	private WebElement txtIcmsAliquotaDestino;
	
	@FindBy(id = "txtCest")
	private WebElement txtCest;
	
	@FindBy(id = "txtMensagem")
	private WebElement txtMensagem;

	@FindBy(id = "spanMensagemErro")
	private WebElement spanMensagemErro;
	
	@FindBy(id = "spanMensagemSucesso")
	private WebElement spanMensagemSucesso;

	@FindBy(id = "btnCadastrarAlterar")
	private WebElement btnCadastrarAlterar;
	
	@FindBy(id = "txtNcmTributacaoEstadual")
	private WebElement txtNcmTributacaoEstadual;	// Campo usado para consultar uma tributação especifica
	
	@FindBy(id = "btnPesquisar")
	private WebElement btnPesquisar;
	
	@FindBy(xpath = "/html/body/main/section/div/div/div/div/div/div/div/table/tbody/tr/td[12]/a/button")
	private WebElement btnEditarPrimeiroRegistro;
	
	@FindBy(xpath = "/html/body/main/section/div/div/div/div/div/div/div/table/tbody/tr/td[13]/form/button")
	private WebElement btnDeletarPrimeiroRegistro;
	
	// ====================
	
	public static String TRIBUTACAO_ESTADUAL_TITLE_PAGE_CADASTRO = "Cadastro de tributação estadual - (ICMS)";
	public static String TRIBUTACAO_ESTADUAL_PAGE_CONSULTA = "Consulta de tributação estadual - (ICMS)";
	// As msgs abaixo, é uma parte da msg que aparece para o usuário, e não a msg completa
	public static String TRIBUTACAO_ESTADUAL_MSG_ALTERADA_CADASTRADA = "alterado/cadastrado";
	public static String TRIBUTACAO_ESTADUAL_MSG_CADASTRADO_ERRO = "preencha todos os campos";
	public static String TRIBUTACAO_ESTADUAL_MSG_DELETADO_SUCESSO = "deletada com sucesso";
//	public static String TRIBUTACAO_ESTADUAL_MSG_CADASTRO_REPETIDO_ERRO = "Já existe essa combinação"; 

	public static String TRIBUTACAO_ESTADUAL_ICMS_CST = "00";
	public static String TRIBUTACAO_ESTADUAL_ICMS_BASE = "1";
	public static String TRIBUTACAO_ESTADUAL_ICMS_ALIQ = "1";
	public static String TRIBUTACAO_ESTADUAL_ICMS_IVA = "2";
	public static String TRIBUTACAO_ESTADUAL_ICMS_ALIQ_DESTINO = "9";
	public static String TRIBUTACAO_ESTADUAL_ICMS_CEST = "100100";
	public static String TRIBUTACAO_ESTADUAL_MENSAGEM = "Essa é o primeiro cadastro de teste";
	public static String TRIBUTACAO_ESTADUAL_MENSAGEM_EDITADA = "Essa é uma mensagem EDITADA";

	
	private WebDriver driver;
	private EstadoPage estadoPage;
	private OperacaoPage operacaoPage;
	private NcmPage ncmPage;

	public TributacaoEstadualPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		estadoPage = new EstadoPage(driver);
		operacaoPage  = new OperacaoPage(driver);
		ncmPage = new NcmPage(driver);
	}
	
	// ======================================= CADASTROS ============================================
	
	private void preencheOCadastroDeTributacaoEstadualDeSPparaSPSemONcm() {
		estadoPage.tentaCadastrarEstadoSPCorretamente();
		operacaoPage.tentaCadastrarUmaOperacaoDeVendaEstadual();
		ncmPage.tentaCadastrarUmNcmCorretamente();
		
		// Preenchimento da TRIBUTAÇÃO ESTADUAL DE FATO
		PageUtil.goToHome(driver);
		PageUtil.goToTelaDeCadastro(driver, "TributacaoEstadual");
		PageUtil.espereOWebElementAparecer(selectUfOrigemId, this.driver, 3L);
		PageUtil.espereUmTempo(1000L);
		
		Select opUfOrigem = new Select(selectUfOrigemId);
		opUfOrigem.selectByVisibleText(EstadoPage.ESTADO_SIGLA_SP);
		
		Select opUfDestino = new Select(selectUfDestinoId);
		opUfDestino.selectByVisibleText(EstadoPage.ESTADO_SIGLA_SP);
		
		Select opOperacao = new Select(selectOperacao);
		opOperacao.selectByVisibleText(OperacaoPage.OPERACAO_DESCRICAO_01);
		
//		Select opNcm = new Select(selectNcm);
//		opNcm.selectByVisibleText(NcmPage.NCM_NUMERO_01);
		
		// PREENCHENDO DE FATO os TXTS
		setTxt(txtIcmsCst, TRIBUTACAO_ESTADUAL_ICMS_CST, 600L);
		setTxt(txtIcmsBase, TRIBUTACAO_ESTADUAL_ICMS_BASE, 600L);
		setTxt(txtIcmsAliquota, TRIBUTACAO_ESTADUAL_ICMS_ALIQ, 600L);
		setTxt(txtIcmsIva, TRIBUTACAO_ESTADUAL_ICMS_IVA, 600L);
		setTxt(txtIcmsAliquotaDestino, TRIBUTACAO_ESTADUAL_ICMS_ALIQ_DESTINO, 600L);
		setTxt(txtCest, TRIBUTACAO_ESTADUAL_ICMS_CEST, 600L);
		setTxt(txtMensagem, TRIBUTACAO_ESTADUAL_MENSAGEM, 600L);
//		btnCadastrarAlterar.click();
	}
	
	public void tentaCadastrarUmaTributacaoEstadualSemONcm() {
		preencheOCadastroDeTributacaoEstadualDeSPparaSPSemONcm();
		btnCadastrarAlterar.click();
		PageUtil.verificaSeApareceuMsgDeErro(TRIBUTACAO_ESTADUAL_TITLE_PAGE_CADASTRO, this.driver, TRIBUTACAO_ESTADUAL_MSG_CADASTRADO_ERRO);
		PageUtil.deslogar(driver);
	}

	public void tentaCadastrarUmaTributacaoEstadualDeSPparaSP() {
		preencheOCadastroDeTributacaoEstadualDeSPparaSPSemONcm();
		Select opNcm = new Select(selectNcm);
		opNcm.selectByVisibleText(NcmPage.NCM_NUMERO_01);
		btnCadastrarAlterar.click();
//		PageUtil.verificaSeApareceuMsgDeSucesso(TRIBUTACAO_ESTADUAL_TITLE_PAGE_CADASTRO, driver);
		PageUtil.espereUmTempo(750L);
		PageUtil.deslogar(driver);
	}
	
	// ======================================= CONSULTAS ============================================

	public void tentaConsultarUmaTributacaoEstadualVazio() {
		PageUtil.goToHome(this.driver);
		PageUtil.goToTelaDeConsulta(this.driver, "TributacaoEstadual");
		PageUtil.espereOWebElementAparecer(btnPesquisar, this.driver, 3L);
		PageUtil.espereUmTempo(600L);
		btnPesquisar.click();
		PageUtil.espereUmTempo(600L);
		PageUtil.verificaSeApareceuMsgDeErro(TRIBUTACAO_ESTADUAL_PAGE_CONSULTA, this.driver);
	}
	
	public void tentaConsultarUmaTributacaoEstadualExistente() {
		PageUtil.goToHome(this.driver);
		PageUtil.goToTelaDeConsulta(this.driver, "TributacaoEstadual");
		PageUtil.espereOWebElementAparecer(txtNcmTributacaoEstadual, this.driver, 3L);
		PageUtil.preencheTxt(txtNcmTributacaoEstadual, NcmPage.NCM_NUMERO_01, 600L);
		btnPesquisar.click();
		PageUtil.espereUmTempo(600L);
		btnEditarPrimeiroRegistro.click();
		assertEquals(NcmPage.NCM_NUMERO_01, selectNcm.getText().trim());
	}
	
	public void tentaConsultaUmaTributacaoEstadualInexistente() {
		PageUtil.goToHome(this.driver);
		PageUtil.goToTelaDeConsulta(this.driver, "TributacaoEstadual");
		PageUtil.espereOWebElementAparecer(txtNcmTributacaoEstadual, this.driver, 3L);
		
		PageUtil.preencheTxt(txtNcmTributacaoEstadual, "h@ir87654321", 600L);
		PageUtil.espereUmTempo(600L);
		btnPesquisar.click();
		WebElement element = driver.findElement(By.id("txtNcmTributacaoEstadual"));
		assertThat(element.isDisplayed());
		assertThat(element.isEnabled());
	}
	
	// ======================================= EDITAR ============================================

	public void tentaEditarOPrimeiroRegistroDeUmaTributacaoEstadual() {
		PageUtil.goToHome(this.driver);
		PageUtil.goToTelaDeConsulta(this.driver, "TributacaoEstadual");
		PageUtil.espereOWebElementAparecer(btnEditarPrimeiroRegistro, this.driver, 3L);
		PageUtil.espereUmTempo(700L);
		btnEditarPrimeiroRegistro.click();
	
		setTxt(txtMensagem, TRIBUTACAO_ESTADUAL_MENSAGEM_EDITADA, 600L);
		btnCadastrarAlterar.click();
		PageUtil.verificaSeApareceuMsgDeSucesso(TRIBUTACAO_ESTADUAL_TITLE_PAGE_CADASTRO, driver);
		
		PageUtil.espereUmTempo(500L);
		PageUtil.goToTelaDeConsulta(this.driver, "TributacaoEstadual");
		PageUtil.espereUmTempo(1000L);
		PageUtil.espereOWebElementAparecer(btnEditarPrimeiroRegistro, this.driver, 3L);
		btnEditarPrimeiroRegistro.click();
		PageUtil.espereUmTempo(500L);
		assertEquals(TRIBUTACAO_ESTADUAL_MENSAGEM_EDITADA, txtMensagem.getAttribute("value"));
	}
	
	// ======================================= DELETES ============================================
	public void tentaDeletarOPrimeiroRegistroDeUmaTributacaoEstadual() {
		PageUtil.goToHome(this.driver);
		PageUtil.goToTelaDeConsulta(this.driver, "TributacaoEstadual");
		PageUtil.espereOWebElementAparecer(btnDeletarPrimeiroRegistro, this.driver, 3L);
		PageUtil.espereUmTempo(700L);
		btnDeletarPrimeiroRegistro.click();
		PageUtil.verificaSeApareceuMsgDeSucesso(TRIBUTACAO_ESTADUAL_PAGE_CONSULTA, this.driver, TRIBUTACAO_ESTADUAL_MSG_DELETADO_SUCESSO);
	}

	
	// ======================================= GETTERs And SETTERs ============================================
	public void setTxt(WebElement txtIcms, String txtValueIcms, Long timeInMillis) {
		txtIcms.clear();
		PageUtil.preencheTxt(txtIcms, txtValueIcms, timeInMillis);
		assertEquals(txtValueIcms, txtIcms.getAttribute("value"));
	}
	
	
}

