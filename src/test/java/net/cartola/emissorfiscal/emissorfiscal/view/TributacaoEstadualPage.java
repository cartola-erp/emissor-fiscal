package net.cartola.emissorfiscal.emissorfiscal.view;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
	
	// VERIFICAR OS DAQUI PARA BAIXO (TENHO QUE VERIFICAR SE ESTÂO TODOS CORRETOS)
	
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

	// REPETIDOS PARA TODAS AS PAGINAS (TENHO QUE VERIFICAR SE ESTÂO TODOS CORRETOS)
	
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
	
	// ====================
	
	public static String TRIBUTACAO_ESTADUAL_TITLE_PAGE_CADASTRO = "Cadastro de tributação estadual - (ICMS)";
	public static String TRIBUTACAO_ESTADUAL_PAGE_CONSULTA = "Consulta de tributação estadual - (ICMS)";
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
	
	// ====================
	
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
		setTxtIcmsCst(txtIcmsCst, TRIBUTACAO_ESTADUAL_ICMS_CST, 600L);
		setTxtIcmsBase(txtIcmsBase, TRIBUTACAO_ESTADUAL_ICMS_BASE, 600L);
		setTxtIcmsAliquota(txtIcmsAliquota, TRIBUTACAO_ESTADUAL_ICMS_ALIQ, 600L);
		setTxtIcmsIva(txtIcmsIva, TRIBUTACAO_ESTADUAL_ICMS_IVA, 600L);
		setTxtIcmsAliquotaDestino(txtIcmsAliquotaDestino, TRIBUTACAO_ESTADUAL_ICMS_ALIQ_DESTINO, 600L);
		setTxtCest(txtCest, TRIBUTACAO_ESTADUAL_ICMS_CEST, 600L);
		setTxtMensagem(txtMensagem, TRIBUTACAO_ESTADUAL_MENSAGEM, 600L);
		
//		btnCadastrarAlterar.click();
	}
	
	public void tentaCadastrarUmaTributacaoEstadualIncompleta() {
		preencheOCadastroDeTributacaoEstadualDeSPparaSPSemONcm();
		btnCadastrarAlterar.click();
		PageUtil.verificaSeApareceuMsgDeErro(TRIBUTACAO_ESTADUAL_TITLE_PAGE_CADASTRO, this.driver, TRIBUTACAO_ESTADUAL_MSG_CADASTRADO_ERRO);
	}

//	public void preencheOCadastroDeTributacaoEstadualDoMesmoEstado() {
//		estadoPage.tentaCadastrarEstadoSPCorretamente();
//		operacaoPage.tentaCadastrarUmaOperacaoDeVendaEstadual();
//		ncmPage.tentaCadastrarUmNcmCorretamente();
//		
//		
//		// Preenchimento da TRIBUTAÇÃO ESTADUAL DE FATO
//		PageUtil.goToHome(driver);
//		PageUtil.goToTelaDeCadastro(driver, "TributacaoEstadual");
//		PageUtil.espereOWebElementAparecer(btnCadastrarAlterar, this.driver, 3L);
//		PageUtil.espereUmTempo(1000L);
//		
//		Select itemUfOrigem = new Select(selectUfOrigemId);
//		itemUfOrigem.selectByVisibleText(EstadoPage.ESTADO_SIGLA_SP);
//		
//		Select itemUfDestino = new Select(selectUfDestinoId);
//		itemUfDestino.selectByVisibleText(EstadoPage.ESTADO_SIGLA_SP);
//		
//		Select itemOperacao = new Select(selectOperacao);
//		itemOperacao.selectByVisibleText(OperacaoPage.OPERACAO_DESCRICAO_01);
//		
//		Select itemNcm = new Select(selectNcm);
//		itemNcm.selectByVisibleText(NcmPage.NCM_NUMERO_01);
//		
//		// PREENCHENDO DE FATO os TXTS
//		PageUtil.preencheTxt(txtIcmsCst, TRIBUTACAO_ESTADUAL_ICMS_CST, 600L);
//		
//		PageUtil.preencheTxt(txtIcmsBase, TRIBUTACAO_ESTADUAL_ICMS_BASE, 600L);
//		PageUtil.preencheTxt(txtIcmsAliquota, TRIBUTACAO_ESTADUAL_ICMS_ALIQ, 600L);
//		PageUtil.preencheTxt(txtIcmsIva, TRIBUTACAO_ESTADUAL_ICMS_IVA, 600L);
//		
//		PageUtil.preencheTxt(txtIcmsAliquotaDestino, TRIBUTACAO_ESTADUAL_ICMS_ALIQ_DESTINO, 600L);
//		PageUtil.preencheTxt(txtCest, TRIBUTACAO_ESTADUAL_ICMS_CEST, 600L);
//		PageUtil.preencheTxt(txtMensagem, TRIBUTACAO_ESTADUAL_MENSAGEM, 600L);
//		btnCadastrarAlterar.click();
//	}
	
	// ======================================= GETTERs And SETTERs ============================================

	
	public void setTxtIcmsCst(WebElement txtIcmsCst, String txtValueIcmsCst, Long timeInMillis) {
		txtIcmsCst.clear();
		PageUtil.preencheTxt(txtIcmsCst, txtValueIcmsCst, timeInMillis);
		assertEquals(txtValueIcmsCst, txtIcmsCst.getAttribute("value"));
	}

	public void setTxtIcmsBase(WebElement txtIcmsBase, String txtValueIcmsBase, Long timeInMillis) {
		txtIcmsBase.clear();
		PageUtil.preencheTxt(txtIcmsBase, txtValueIcmsBase, timeInMillis);
		assertEquals(txtValueIcmsBase, txtIcmsBase.getAttribute("value"));
	}

	public void setTxtIcmsAliquota(WebElement txtIcmsAliquota, String txtValueIcmsAliq, Long timeInMillis) {
		txtIcmsAliquota.clear();
		PageUtil.preencheTxt(txtIcmsAliquota, txtValueIcmsAliq, timeInMillis);
		assertEquals(txtValueIcmsAliq, txtIcmsAliquota.getAttribute("value"));
	}

	public void setTxtIcmsIva(WebElement txtIcmsIva, String txtValueIcmsIva, Long timeInMillis) {
		txtIcmsIva.clear();
		PageUtil.preencheTxt(txtIcmsIva, txtValueIcmsIva, timeInMillis);
		assertEquals(txtValueIcmsIva, txtIcmsIva.getAttribute("value"));
	}

	public void setTxtIcmsAliquotaDestino(WebElement txtIcmsAliquotaDestino, String txtValueIcmsAliqDest, Long timeInMillis) {
		txtIcmsAliquotaDestino.clear();
		PageUtil.preencheTxt(txtIcmsAliquotaDestino, txtValueIcmsAliqDest, timeInMillis);
		assertEquals(txtValueIcmsAliqDest, txtIcmsAliquotaDestino.getAttribute("value"));
	}

	public void setTxtCest(WebElement txtCest, String txtValueCest, Long timeInMillis) {
		txtCest.clear();
		PageUtil.preencheTxt(txtCest, txtValueCest, timeInMillis);
		assertEquals(txtValueCest, txtCest.getAttribute("value"));
	}

	public void setTxtMensagem(WebElement txtMensagem, String txtValueMensagem, Long timeInMillis) {
		txtMensagem.clear();
		PageUtil.preencheTxt(txtMensagem, txtValueMensagem, timeInMillis);
		assertEquals(txtValueMensagem, txtMensagem.getAttribute("value"));
	}
	
}

