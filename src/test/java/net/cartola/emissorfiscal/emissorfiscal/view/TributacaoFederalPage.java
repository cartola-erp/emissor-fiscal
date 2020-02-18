package net.cartola.emissorfiscal.emissorfiscal.view;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

/**
 * 27 de jan de 2020
 * @author robson.costa
 */
public class TributacaoFederalPage {
	
	@FindBy(name = "operacaoId")
	private WebElement selectOperacao;
	
	@FindBy(name = "ncmId")
	private WebElement selectNcm;
	
	// VERIFICAR OS DAQUI PARA BAIXO (TENHO QUE VERIFICAR SE ESTÂO TODOS CORRETOS)
	
	@FindBy(id = "txtPisCst")
	private WebElement txtPisCst;
	
	@FindBy(id = "txtPisBase")
	private WebElement txtPisBase;
	
	@FindBy(id = "txtPisAliquota")
	private WebElement txtPisAliquota;
	
	@FindBy(id = "txtCofinsCst")
	private WebElement txtCofinsCst;
	
	@FindBy(id = "txtCofinsBase")
	private WebElement txtCofinsBase;
	
	@FindBy(id = "txtCofinsAliq")
	private WebElement txtCofinsAliq;
	
	@FindBy(id = "txtIpiCst")
	private WebElement txtIpiCst;

	@FindBy(id = "txtIpiBase")
	private WebElement txtIpiBase;
	
	@FindBy(id = "txtIpiAliq")
	private WebElement txtIpiAliq;
	
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
	
	// =========== VERIFICAR ESSAS MSGS ABAIXO ==========
	public static String TRIBUTACAO_FEDERAL_TITLE_PAGE_CADASTRO = "Cadastro de tributação federal";
	public static String TRIBUTACAO_FEDERAL_TITLE_PAGE_CONSULTA = "Consulta de tributação federal";
	// As msgs abaixo, é uma parte da msg que aparece para o usuário, e não a msg completa
	public static String TRIBUTACAO_FEDERAL_MSG_ALTERADA_CADASTRADA = "alterado/cadastrado";
	
	public static String TRIBUTACAO_FEDERAL_MSG_CADASTRADO_ERRO = "erro ao tentar cadastrar";
//	public static String TRIBUTACAO_FEDERAL_MSG_CADASTRADO_ERRO = "preencha todos os campos";
	public static String TRIBUTACAO_FEDERAL_MSG_DELETADO_SUCESSO = "deletada com sucesso";
	// =========== VERIFICAR ESSAS MSGS ACIMA ==========
	
//	public static String OPERACAO_MSG_CADASTRO_REPETIDO_ERRO = "Já existe essa combinação"; 

	public static String TRIBUTACAO_FEDERAL_PIS_CST = "00";
	public static String TRIBUTACAO_FEDERAL_PIS_BASE = "1";
	public static String TRIBUTACAO_FEDERAL_PIS_ALIQ = "1";
	
	public static String TRIBUTACAO_FEDERAL_COFINS_CST = "2";
	public static String TRIBUTACAO_FEDERAL_COFINS_BASE = "9";
	public static String TRIBUTACAO_FEDERAL_COFINS_ALIQ = "7.5";
	
	public static String TRIBUTACAO_FEDERAL_IPI_CST = "2";
	public static String TRIBUTACAO_FEDERAL_IPI_BASE = "2";
	public static String TRIBUTACAO_FEDERAL_IPI_ALIQ = "1.65";
	
	public static String TRIBUTACAO_FEDERAL_MENSAGEM = "Essa é o primeiro cadastro de teste";
	public static String TRIBUTACAO_FEDERAL_MENSAGEM_EDITADA = "Essa é uma mensagem EDITADA";

	
	// ====================
	
	private WebDriver driver;
	private OperacaoPage operacaoPage;
	private NcmPage ncmPage;
	
	public TributacaoFederalPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		operacaoPage  = new OperacaoPage(driver);
		ncmPage = new NcmPage(driver);
	}
	
	
//	public void preencheOCadastro

	// ======================================= CADASTROS ============================================
	
	private void preencheOCadastroDeTributacaoFederalSemONcm() {
		operacaoPage.tentaCadastrarUmaOperacaoDeVendaEstadual();
		ncmPage.tentaCadastrarUmNcmCorretamente();
		
		// Preenchimento da TRIBUTAÇÃO ESTADUAL DE FATO
		PageUtil.goToHome(driver);
		PageUtil.goToTelaDeCadastro(driver, "TributacaoFederal");
		PageUtil.espereOWebElementAparecer(selectOperacao, this.driver, 3L);
		PageUtil.espereUmTempo(1000L);
		
		Select opOperacao = new Select(selectOperacao);
		opOperacao.selectByVisibleText(OperacaoPage.OPERACAO_DESCRICAO_01);
		
//		Select opNcm = new Select(selectNcm);
//		opNcm.selectByVisibleText(NcmPage.NCM_NUMERO_01);
		
		// PREENCHENDO DE FATO os TXTS
		setTxt(txtPisCst, TRIBUTACAO_FEDERAL_PIS_CST, 600L);
		setTxt(txtPisBase, TRIBUTACAO_FEDERAL_PIS_BASE, 600L);
		setTxt(txtPisAliquota, TRIBUTACAO_FEDERAL_PIS_ALIQ, 600L);
		
		setTxt(txtCofinsCst, TRIBUTACAO_FEDERAL_COFINS_CST, 600L);
		setTxt(txtCofinsBase, TRIBUTACAO_FEDERAL_COFINS_BASE, 600L);
		setTxt(txtCofinsAliq, TRIBUTACAO_FEDERAL_COFINS_ALIQ, 600L);
		
		setTxt(txtIpiCst, TRIBUTACAO_FEDERAL_IPI_CST, 600L);
		setTxt(txtIpiBase, TRIBUTACAO_FEDERAL_IPI_BASE, 600L);
		setTxt(txtIpiAliq, TRIBUTACAO_FEDERAL_IPI_ALIQ, 600L);
		
		setTxt(txtMensagem, TRIBUTACAO_FEDERAL_MENSAGEM, 600L);
//		btnCadastrarAlterar.click();
	}
	
	public void tentaCadastrarUmaTributacaoFederalSemONcm() {
		preencheOCadastroDeTributacaoFederalSemONcm();
		btnCadastrarAlterar.click();
		PageUtil.verificaSeApareceuMsgDeErro(TRIBUTACAO_FEDERAL_TITLE_PAGE_CADASTRO, this.driver, TRIBUTACAO_FEDERAL_MSG_CADASTRADO_ERRO);
	}
	
	public void tentaCadastrarUmaTributacaoFederalCorretamente() {
		preencheOCadastroDeTributacaoFederalSemONcm();
		Select opNcm = new Select(selectNcm);
		opNcm.selectByVisibleText(NcmPage.NCM_NUMERO_01);
//		PageUtil.verificaSeApareceuMsgDeSucesso(TRIBUTACAO_ESTADUAL_TITLE_PAGE_CADASTRO, driver);
		btnCadastrarAlterar.click();
	}
	
	// ======================================= CONSULTAS ============================================

	
	
	// ======================================= GETTERs And SETTERs ============================================
		public void setTxt(WebElement txtTributacaoFedereal, String txtValueTributacao, Long timeInMillis) {
			txtTributacaoFedereal.clear();
			PageUtil.preencheTxt(txtTributacaoFedereal, txtValueTributacao, timeInMillis);
			assertEquals(txtValueTributacao, txtTributacaoFedereal.getAttribute("value"));
		}
}

