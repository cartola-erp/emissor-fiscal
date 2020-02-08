package net.cartola.emissorfiscal.emissorfiscal.view;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

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
	
	public static String TRIBUTACAO_ESTADUAL_TITLE_PAGE_CADASTRO = "Cadastro de tributação federal";
	public static String TRIBUTACAO_ESTADUAL_PAGE_CONSULTA = "Consulta de federal";
	// As msgs abaixo, é uma parte da msg que aparece para o usuário, e não a msg completa
	public static String TRIBUTACAO_ESTADUAL_MSG_ALTERADA_CADASTRADA = "alterado/cadastrado";
	
	public static String TRIBUTACAO_ESTADUAL_MSG_CADASTRADO_ERRO = "preencha todos os campos";
	public static String TRIBUTACAO_ESTADUAL_MSG_DELETADO_SUCESSO = "deletada com sucesso";

//	public static String OPERACAO_MSG_CADASTRO_REPETIDO_ERRO = "Já existe essa combinação"; 

	public static String TRIBUTACAO_ESTADUAL_ICMS_CST = "00";
	public static String TRIBUTACAO_ESTADUAL_ICMS_BASE = "1";
	public static String TRIBUTACAO_ESTADUAL_ICMS_ALIQ = "1";
	public static String TRIBUTACAO_ESTADUAL_ICMS_IVA = "2";
	public static String TRIBUTACAO_ESTADUAL_ICMS_ALIQ_DESTINO = "9";
	public static String TRIBUTACAO_ESTADUAL_ICMS_CEST = "100100";
	public static String TRIBUTACAO_ESTADUAL_MENSAGEM = "Essa é o primeiro cadastro de teste";
	public static String TRIBUTACAO_ESTADUAL_MENSAGEM_EDITADA = "Essa é uma mensagem EDITADA";

	
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
	
	
	
	
	
	
	
	
	
	
	// ======================================= GETTERs And SETTERs ============================================
		public void setTxt(WebElement txtIcms, String txtValueIcms, Long timeInMillis) {
			txtIcms.clear();
			PageUtil.preencheTxt(txtIcms, txtValueIcms, timeInMillis);
			assertEquals(txtValueIcms, txtIcms.getAttribute("value"));
		}
}

