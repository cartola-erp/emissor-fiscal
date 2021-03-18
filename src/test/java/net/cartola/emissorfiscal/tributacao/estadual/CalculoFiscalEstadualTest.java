package net.cartola.emissorfiscal.tributacao.estadual;

import static net.cartola.emissorfiscal.emissorfiscal.service.TestHelper.PESSOA_DEST_CNPJ_RJ;
import static net.cartola.emissorfiscal.emissorfiscal.service.TestHelper.PESSOA_DEST_CNPJ_SP;
import static net.cartola.emissorfiscal.emissorfiscal.service.TestHelper.PESSOA_EMITENTE_CNPJ;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import net.cartola.emissorfiscal.documento.DocumentoFiscal;
import net.cartola.emissorfiscal.documento.DocumentoFiscalItem;
import net.cartola.emissorfiscal.documento.DocumentoFiscalService;
import net.cartola.emissorfiscal.documento.IndicadorDeOperacao;
import net.cartola.emissorfiscal.documento.Finalidade;
import net.cartola.emissorfiscal.documento.ProdutoOrigem;
import net.cartola.emissorfiscal.emissorfiscal.service.NcmServiceLogicTest;
import net.cartola.emissorfiscal.emissorfiscal.service.TestHelper;
import net.cartola.emissorfiscal.estado.EstadoService;
import net.cartola.emissorfiscal.estado.EstadoSigla;
import net.cartola.emissorfiscal.ncm.Ncm;
import net.cartola.emissorfiscal.ncm.NcmService;
import net.cartola.emissorfiscal.operacao.Operacao;
import net.cartola.emissorfiscal.operacao.OperacaoService;
import net.cartola.emissorfiscal.pessoa.Pessoa;
import net.cartola.emissorfiscal.pessoa.PessoaService;

/**
 * DISCLAIMER: Apesar de estar usando na maioria dos casos, NCM, ALIQ etc que existem de verdade.
 * Não necessariamente, é para sair dessa forma num cenário REAL, de ENVIO da NFE para a SEFAZ.
 * Quem deverá cadastrar (tela TRIBUTACAO ESTADUAL) as informações corretas que serão usadas, é a contabilidade;
 * Ou seja, é basicamente para vermos se está realizando o calculo de forma correta para VEND/VENDA INTERESTADUAL etc...
 * @author robson.costa
 *
 */
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CalculoFiscalEstadualTest {
	
	private static final Logger LOG = Logger.getLogger(CalculoFiscalEstadualTest.class.getName());
	
	@Autowired
	private DocumentoFiscalService docFiscalService;
	
	@Autowired
	private EstadoService estadoService;
	
	@Autowired
	private PessoaService pessoaService;
	
	@Autowired
	private OperacaoService operacaoService;
	
	@Autowired
	private NcmService ncmService;
	
	@Autowired
	private TributacaoEstadualService tribuEstaService;
	
	@Autowired
	private CalculoFiscalEstadual calcFiscalEstadual;
	
	@Autowired
	private TestHelper testHelper;
	
	// criar aqui nessa classe;
	// 1 - tributacoes estaduais !?
	// 2 - item (smp será o msm)
	// 3 - Documento Fiscal
		
		// já ter previameente no banco
		// 1 - estado (origem e destino)
		// 2 - pessoa
		// 3 - finalidade
		// 4 - operacao
		// 5 - ncms
		
	
	public static final String OPERACAO_DESCRICAO = "VENDA";
	
	public static final String DOC_FISC_TIPO = "NFE";
	public static final Long DOC_FISC_SERIE = 1L;
	
	public static final BigDecimal DOC_FISC_ITEM_VALOR_UNITARIO = new BigDecimal(100D);
	public static final BigDecimal DOC_FISC_ITEM_QUANTIDADE = BigDecimal.ONE;
	// VALORES REFERENTES a última compra !?
	private static final BigDecimal ITEM_ICMS_ST_BASE_ULTIM_COMPR_0 = BigDecimal.ZERO;
	private static final BigDecimal ITEM_ICMS_ST_VLR_ULTIM_COMPR_0 = BigDecimal.ZERO;
	private static final BigDecimal QTD_ULTIM_COMP_0 = BigDecimal.ZERO;
	private static final BigDecimal ITEM_ICMS_ST_ALIQ_ULTIM_COMPRA_0 = BigDecimal.ZERO;

	
	private static final BigDecimal IVA_ZERO = BigDecimal.ZERO;
	private static final BigDecimal ICMS_BASE_CEM = new BigDecimal(100D);
	private static final BigDecimal FCP_ALIQ_ZERO = BigDecimal.ZERO;
	private static final BigDecimal ICMS_ST_ALIQ_ZERO = BigDecimal.ZERO;
	private static final int ICMS_CFOP_ITEM = 5405;
	
	private static final BigDecimal IVA_70 = new BigDecimal(70D);
	private static final BigDecimal FCP_ALIQ_2_PERC = new BigDecimal(2D);
	private static final BigDecimal ICMS_ST_ALIQ_12_PERC = new BigDecimal(12D);
	private static final BigDecimal ICMS_PERC_60_RED_BC = new BigDecimal(60D);
	
	// ICMS ST EXPECTED
	private static final BigDecimal ICMS_ST_BASE_ITEM_EXPECTED_70 = new BigDecimal(70D);
	private static final BigDecimal ICMS_ST_VLR_ITEM_EXPECTED = new BigDecimal(8.40D);
	
	// ICMS ST EXPECTED
	private static final BigDecimal ICMS_ST_BASE_DOC_EXPECTED = new BigDecimal(70D);
	private static final BigDecimal ICMS_ST_VLR_DOC_EXPECTED = new BigDecimal(8.40D);

	private static final BigDecimal ICMS_BASE_DOC_FISC_EXPECTED_CEM = new BigDecimal(100D);
	private static final BigDecimal ICMS_VLR_DOC_FISC_EXPECTED_18 = new BigDecimal(18D);
	
	// ITEM 1 
	private static final BigDecimal ICMS_ITEM_ALIQ_EXPECTED_18 = new BigDecimal(18D);
	private static final BigDecimal ICMS_ST_ITEM_ALIQ_12_EXPECTED = new BigDecimal(12D);
	private static final BigDecimal ICMS_ITEM_VLR_BASE_EXPECTED = new BigDecimal(100D);
	private static final BigDecimal ICMS_ITEM_VLR_EXPECTED = new BigDecimal(18D);


	
	private static final BigDecimal ICMS_ITEM_VLR_BASE_60_RED_EXPECTED = new BigDecimal(60D);
	private static final BigDecimal ICMS_ITEM_VLR_RED_EXPECTED = new BigDecimal(10.8D);
	
	private static final BigDecimal ICMS_ST_ITEM_VLR_BASE_42_RED_EXPECTED = new BigDecimal(42D);
	private static final BigDecimal ICMS_ST_ITEM_VLR_RED_EXPECTED = new BigDecimal(5.04D);
	
	//FCP
//	private static final BigDecimal ICMS_ITEM_FCP_VLR_BASE_EXPECTED = new BigDecimal(2D);
	private static final BigDecimal ICMS_ITEM_FCP_ALIQ_EXPECTED = new BigDecimal(2D);
	private static final BigDecimal ICMS_ITEM_FCP_VLR_EXPECTED = new BigDecimal(2D);
	
	// colocar os valores do SEGUNDO ITEM aqui
	
	@Test
	public void test00_CleanUp() {
		testHelper.limpaBanco();
		testHelper.criarPessoa();
		testHelper.criarNcms();
		testHelper.criarOperacoes();
		testHelper.criarEstados();
	}
	
	
	private TributacaoEstadual criaTributaEstaVenda(int ncm, int cst, EstadoSigla ufDestino, BigDecimal iva, BigDecimal icmsBase, 
			BigDecimal fcpAliq, BigDecimal icmsStAliq, int cfop) {
		Optional<Ncm> opNcm = ncmService.findNcmByNumeroAndExcecao(ncm, 0);
		assertTrue(opNcm.isPresent());
//		Ncm ncm = opNcm.get();
		TributacaoEstadual tribEstaICMS = testHelper.criarTribEstaVenda(opNcm.get(), cst, ufDestino, iva, icmsBase, fcpAliq, icmsStAliq, cfop);
		assertNotNull(tribEstaICMS);
		return tribEstaICMS;
	}
	
	
	@Test
	public void test01_DeveCalcularVendaICMS00() {
		TributacaoEstadual tribEstaICMS00 = criaTributaEstaVenda(NcmServiceLogicTest.NCM_NUMERO_REGISTRO_1, 00, EstadoSigla.SP, IVA_ZERO, ICMS_BASE_CEM, FCP_ALIQ_ZERO, ICMS_ST_ALIQ_ZERO, ICMS_CFOP_ITEM);
		DocumentoFiscal docFiscal = criaDocumentoFiscalVenda(tribEstaICMS00.getNcm(), ITEM_ICMS_ST_BASE_ULTIM_COMPR_0, ITEM_ICMS_ST_VLR_ULTIM_COMPR_0, QTD_ULTIM_COMP_0, ITEM_ICMS_ST_ALIQ_ULTIM_COMPRA_0);

		List<String> erros = docFiscalService.validaDadosESetaValoresNecessarios(docFiscal, true, false);
		LOG.log(Level.WARNING, "ERROS: {0} ", erros);
		assertTrue(erros.isEmpty());
	
		calcFiscalEstadual.calculaImposto(docFiscal);

		// DocumentoFiscal
		assertTrue(docFiscal.getIcmsBase().compareTo(ICMS_BASE_DOC_FISC_EXPECTED_CEM) == 0);
		assertTrue(docFiscal.getIcmsValor().compareTo(ICMS_VLR_DOC_FISC_EXPECTED_18) == 0);
		assertTrue(docFiscal.getVlrTotalProduto().compareTo(DOC_FISC_ITEM_VALOR_UNITARIO) == 0);
		
		// DocumentoFiscalItem
		DocumentoFiscalItem docFiscalItem = docFiscal.getItens().get(0);
		assertEquals(00, docFiscalItem.getIcmsCst());
		assertTrue(docFiscalItem.getIcmsAliquota().multiply(new BigDecimal(100D)).compareTo(ICMS_ITEM_ALIQ_EXPECTED_18) == 0);
		assertTrue(docFiscalItem.getIcmsBase().compareTo(ICMS_ITEM_VLR_BASE_EXPECTED) == 0);
		assertTrue(docFiscalItem.getIcmsValor().compareTo(ICMS_ITEM_VLR_EXPECTED) == 0);
		assertEquals(5405, docFiscalItem.getCfop());
		assertEquals(101010, docFiscalItem.getIcmsCest().intValue());
		/**
		 * TENHO QUE CALCULAR e VERIFICAR o FCP, POIS no CASO da AG essa é a UNICA CST que terá
		 */
		LOG.log(Level.INFO, "DocFiscal calculado: {0} ", docFiscal);
	}
	
	@Test
	public void test02_DeveCalcularVendaICMS10() {
		TributacaoEstadual tribEstaICMS10 = criaTributaEstaVenda(NcmServiceLogicTest.NCM_NUMERO_REGISTRO_2, 10, EstadoSigla.SP, IVA_70, ICMS_BASE_CEM, FCP_ALIQ_2_PERC, ICMS_ST_ALIQ_12_PERC, ICMS_CFOP_ITEM);
		DocumentoFiscal docFiscal = criaDocumentoFiscalVenda(tribEstaICMS10.getNcm(), ITEM_ICMS_ST_BASE_ULTIM_COMPR_0, ITEM_ICMS_ST_VLR_ULTIM_COMPR_0, QTD_ULTIM_COMP_0, ITEM_ICMS_ST_ALIQ_ULTIM_COMPRA_0);

		List<String> erros = docFiscalService.validaDadosESetaValoresNecessarios(docFiscal, true, false);
		LOG.log(Level.WARNING, "ERROS: {0} ", erros);
		
		assertTrue(erros.isEmpty());
		
		calcFiscalEstadual.calculaImposto(docFiscal);
		
		// DocumentoFiscal
		assertTrue(docFiscal.getIcmsBase().compareTo(ICMS_BASE_DOC_FISC_EXPECTED_CEM) == 0);
		assertTrue(docFiscal.getIcmsValor().compareTo(ICMS_VLR_DOC_FISC_EXPECTED_18) == 0);
		assertTrue(docFiscal.getIcmsStBase().compareTo(ICMS_ST_BASE_DOC_EXPECTED) == 0);
		assertTrue(docFiscal.getIcmsStValor().compareTo(ICMS_ST_VLR_DOC_EXPECTED.setScale(2, BigDecimal.ROUND_HALF_UP)) == 0);
		assertTrue(docFiscal.getVlrTotalProduto().compareTo(DOC_FISC_ITEM_VALOR_UNITARIO) == 0);

		// DocumentoFiscalItem
		DocumentoFiscalItem docFiscalItem = docFiscal.getItens().get(0);
		assertEquals(10, docFiscalItem.getIcmsCst());
		assertTrue(docFiscalItem.getIcmsAliquota().multiply(new BigDecimal(100D)).compareTo(ICMS_ITEM_ALIQ_EXPECTED_18) == 0);
		assertTrue(docFiscalItem.getIcmsBase().compareTo(ICMS_ITEM_VLR_BASE_EXPECTED) == 0);
		assertTrue(docFiscalItem.getIcmsValor().compareTo(ICMS_ITEM_VLR_EXPECTED) == 0);
		
		assertTrue(docFiscalItem.getIcmsStAliquota().multiply(new BigDecimal(100D)).compareTo(ICMS_ST_ALIQ_12_PERC) == 0);
		assertTrue(docFiscalItem.getIcmsIva().multiply(new BigDecimal(100D)).compareTo(IVA_70) == 0);
		assertTrue(docFiscalItem.getIcmsStBase().compareTo(ICMS_ST_BASE_ITEM_EXPECTED_70) == 0);
		assertTrue(docFiscalItem.getIcmsStValor().compareTo(ICMS_ST_VLR_ITEM_EXPECTED.setScale(2, BigDecimal.ROUND_HALF_UP)) == 0);
	}
	
	@Test
	public void test03_DeveCalcularVendaICMS20() {
		TributacaoEstadual tribEstaICMS20 = criaTributaEstaVenda(NcmServiceLogicTest.NCM_NUMERO_REGISTRO_3, 20, EstadoSigla.SP, IVA_ZERO, ICMS_PERC_60_RED_BC, FCP_ALIQ_ZERO, ICMS_ST_ALIQ_ZERO, ICMS_CFOP_ITEM);
		DocumentoFiscal docFiscal = criaDocumentoFiscalVenda(tribEstaICMS20.getNcm(), ITEM_ICMS_ST_BASE_ULTIM_COMPR_0, ITEM_ICMS_ST_VLR_ULTIM_COMPR_0, QTD_ULTIM_COMP_0, ITEM_ICMS_ST_ALIQ_ULTIM_COMPRA_0);

		List<String> erros = docFiscalService.validaDadosESetaValoresNecessarios(docFiscal, true, false);
		LOG.log(Level.WARNING, "ERROS: {0} ", erros);
		assertTrue(erros.isEmpty());
	
		calcFiscalEstadual.calculaImposto(docFiscal);

		// DocumentoFiscal
		assertTrue(docFiscal.getIcmsBase().compareTo(ICMS_ITEM_VLR_BASE_60_RED_EXPECTED) == 0);
		assertTrue(docFiscal.getIcmsValor().compareTo(ICMS_ITEM_VLR_RED_EXPECTED.setScale(2, BigDecimal.ROUND_HALF_UP)) == 0);
		assertTrue(docFiscal.getVlrTotalProduto().compareTo(DOC_FISC_ITEM_VALOR_UNITARIO) == 0);

		// DocumentoFiscalItem
		DocumentoFiscalItem docFiscalItem = docFiscal.getItens().get(0);
		assertEquals(20, docFiscalItem.getIcmsCst());
		assertTrue(docFiscalItem.getIcmsAliquota().multiply(new BigDecimal(100D)).compareTo(ICMS_ITEM_ALIQ_EXPECTED_18) == 0);
		assertTrue(docFiscalItem.getIcmsBase().compareTo(ICMS_ITEM_VLR_BASE_60_RED_EXPECTED) == 0);
		assertTrue(docFiscalItem.getIcmsValor().compareTo(ICMS_ITEM_VLR_RED_EXPECTED.setScale(2, BigDecimal.ROUND_HALF_UP)) == 0);
		
		LOG.log(Level.INFO, "DocFiscal calculado: {0} ", docFiscal);
	}
	
	@Test
	public void test03_DeveCalcularVendaICMS30() {
		TributacaoEstadual tribEstaICMS20 = criaTributaEstaVenda(NcmServiceLogicTest.NCM_NUMERO_REGISTRO_4, 30, EstadoSigla.SP, IVA_70, ICMS_PERC_60_RED_BC, FCP_ALIQ_ZERO, ICMS_ST_ALIQ_12_PERC, ICMS_CFOP_ITEM);
		DocumentoFiscal docFiscal = criaDocumentoFiscalVenda(tribEstaICMS20.getNcm(), ITEM_ICMS_ST_BASE_ULTIM_COMPR_0, ITEM_ICMS_ST_VLR_ULTIM_COMPR_0, QTD_ULTIM_COMP_0, ITEM_ICMS_ST_ALIQ_ULTIM_COMPRA_0);

		List<String> erros = docFiscalService.validaDadosESetaValoresNecessarios(docFiscal, true, false);
		LOG.log(Level.WARNING, "ERROS: {0} ", erros);
		assertTrue(erros.isEmpty());
		
		calcFiscalEstadual.calculaImposto(docFiscal);
		
		// DocumentoFiscal
		assertTrue(docFiscal.getIcmsStBase().compareTo(ICMS_ST_ITEM_VLR_BASE_42_RED_EXPECTED) == 0);
		assertTrue(docFiscal.getIcmsStValor().compareTo(ICMS_ST_ITEM_VLR_RED_EXPECTED.setScale(2, BigDecimal.ROUND_HALF_UP)) == 0);
		assertTrue(docFiscal.getVlrTotalProduto().compareTo(DOC_FISC_ITEM_VALOR_UNITARIO) == 0);

		// DocumentoFiscalItem
		DocumentoFiscalItem docFiscalItem = docFiscal.getItens().get(0);
		assertEquals(30, docFiscalItem.getIcmsCst());
		assertTrue(docFiscalItem.getIcmsStAliquota().multiply(new BigDecimal(100D)).compareTo(ICMS_ST_ITEM_ALIQ_12_EXPECTED) == 0);
		assertTrue(docFiscalItem.getIcmsIva().multiply(new BigDecimal(100D)).compareTo(IVA_70) == 0);
		assertTrue(docFiscalItem.getIcmsStBase().compareTo(ICMS_ST_ITEM_VLR_BASE_42_RED_EXPECTED.setScale(2, BigDecimal.ROUND_HALF_UP)) == 0);
		assertTrue(docFiscalItem.getIcmsStValor().compareTo(ICMS_ST_ITEM_VLR_RED_EXPECTED.setScale(2, BigDecimal.ROUND_HALF_UP)) == 0);
	}
	
//	@Test
//	public void test04_DeveCalcularVendaICMS51() {
//		
//	}

	@Test
	public void test05_DeveCalcularVendaICMS60SemVendaAnterior() {
		TributacaoEstadual tribEstaICMS60 = criaTributaEstaVenda(NcmServiceLogicTest.NCM_NUMERO_REGISTRO_6, 60, EstadoSigla.SP, IVA_ZERO, ICMS_BASE_CEM, FCP_ALIQ_ZERO, ICMS_ST_ALIQ_ZERO, ICMS_CFOP_ITEM);
		DocumentoFiscal docFiscal = criaDocumentoFiscalVenda(tribEstaICMS60.getNcm(), ITEM_ICMS_ST_BASE_ULTIM_COMPR_0, ITEM_ICMS_ST_VLR_ULTIM_COMPR_0, QTD_ULTIM_COMP_0, ITEM_ICMS_ST_ALIQ_ULTIM_COMPRA_0);

		List<String> erros = docFiscalService.validaDadosESetaValoresNecessarios(docFiscal, true, false);
		LOG.log(Level.WARNING, "ERROS: {0} ", erros);
		assertTrue(erros.isEmpty());
		
		calcFiscalEstadual.calculaImposto(docFiscal);
		
		// DocumentoFiscal
		assertTrue(docFiscal.getVlrTotalProduto().compareTo(DOC_FISC_ITEM_VALOR_UNITARIO) == 0);

		// DocumentoFiscalItem
		DocumentoFiscalItem docFiscalItem = docFiscal.getItens().get(0);
		assertEquals(60, docFiscalItem.getIcmsCst());
		assertTrue(docFiscalItem.getIcmsStBaseRetido().compareTo(new BigDecimal(100D)) == 0);
		assertTrue(docFiscalItem.getIcmsStValorRetido().compareTo(ICMS_ITEM_VLR_EXPECTED) == 0);
		assertTrue(docFiscalItem.getIcmsAliquota().multiply(new BigDecimal(100D)).compareTo(ICMS_ITEM_ALIQ_EXPECTED_18) == 0);
//		O valor, abaixo é o "vICMSSubstituto"
		assertTrue(docFiscalItem.getIcmsValor().compareTo(ICMS_ITEM_VLR_EXPECTED) == 0);
	}

	@Test
	public void test06_DeveCalcularVendaICMS60ComVendaAnterior() {
		Optional<Ncm> opNcm = ncmService.findNcmByNumeroAndExcecao(NcmServiceLogicTest.NCM_NUMERO_REGISTRO_6, 0);
		TributacaoEstadual tribEstaICMS60 = tribuEstaService.findTributacaoEstadualByNcm(opNcm.get()).get(0);

		final BigDecimal ITEM_ICMS_ST_BASE_ULTIM_COMPRA_50 =  new BigDecimal(50D);
		final BigDecimal ITEM_ICMS_ST_VLR_ULTIM_COMPR_9 = new BigDecimal(9D);
		final BigDecimal ITEM_ICMS_ST_QTD_ULTIM_COMPR_1 = BigDecimal.ONE;
		final BigDecimal ITEM_ICMS_ST_ALIQ_ULTIM_COMPR_18 = new BigDecimal(18D);

		DocumentoFiscal docFiscal = criaDocumentoFiscalVenda(tribEstaICMS60.getNcm(), 
				ITEM_ICMS_ST_BASE_ULTIM_COMPRA_50, 
				ITEM_ICMS_ST_VLR_ULTIM_COMPR_9, ITEM_ICMS_ST_QTD_ULTIM_COMPR_1, ITEM_ICMS_ST_ALIQ_ULTIM_COMPR_18);

		List<String> erros = docFiscalService.validaDadosESetaValoresNecessarios(docFiscal, true, false);
		LOG.log(Level.WARNING, "ERROS: {0} ", erros);
		assertTrue(erros.isEmpty());
		
		calcFiscalEstadual.calculaImposto(docFiscal);
		// DocumentoFiscal
		assertTrue(docFiscal.getVlrTotalProduto().compareTo(DOC_FISC_ITEM_VALOR_UNITARIO) == 0);

		// DocumentoFiscalItem
		DocumentoFiscalItem docFiscalItem = docFiscal.getItens().get(0);
		assertEquals(60, docFiscalItem.getIcmsCst());
		assertTrue(docFiscalItem.getIcmsStBaseRetido().compareTo(ITEM_ICMS_ST_BASE_ULTIM_COMPRA_50) == 0);
		assertTrue(docFiscalItem.getIcmsStValorRetido().compareTo(ITEM_ICMS_ST_VLR_ULTIM_COMPR_9) == 0);
		assertTrue(docFiscalItem.getIcmsAliquota().multiply(new BigDecimal(100D)).compareTo(ICMS_ITEM_ALIQ_EXPECTED_18) == 0);
//		O valor, abaixo é o "vICMSSubstituto"
		assertTrue(docFiscalItem.getIcmsValor().compareTo(ICMS_ITEM_VLR_EXPECTED) == 0);
	}
	
	@Test
	public void test07_DeveCalcularVendaICMS70() {
		final BigDecimal ICMS_ITEM_VLR_RED_E_IVA_EXPECTED = new BigDecimal(5.04D);
		final BigDecimal ICMS_ITEM_VLR_BASE_RED_E_IVA_EXPECTED = new BigDecimal(42D);
		TributacaoEstadual tribEstaICMS70 = criaTributaEstaVenda(NcmServiceLogicTest.NCM_NUMERO_REGISTRO_7, 70, EstadoSigla.SP, IVA_70, ICMS_PERC_60_RED_BC, FCP_ALIQ_ZERO, ICMS_ST_ALIQ_12_PERC, ICMS_CFOP_ITEM);
		DocumentoFiscal docFiscal = criaDocumentoFiscalVenda(tribEstaICMS70.getNcm(), ITEM_ICMS_ST_BASE_ULTIM_COMPR_0, ITEM_ICMS_ST_VLR_ULTIM_COMPR_0, QTD_ULTIM_COMP_0, ITEM_ICMS_ST_ALIQ_ULTIM_COMPRA_0);

		List<String> erros = docFiscalService.validaDadosESetaValoresNecessarios(docFiscal, true, false);
		LOG.log(Level.WARNING, "ERROS: {0} ", erros);
		assertTrue(erros.isEmpty());
		calcFiscalEstadual.calculaImposto(docFiscal);
		
		// DocumentoFiscal
		assertTrue(docFiscal.getIcmsBase().compareTo(ICMS_ITEM_VLR_BASE_60_RED_EXPECTED) == 0);
		assertTrue(docFiscal.getIcmsValor().compareTo(ICMS_ITEM_VLR_RED_EXPECTED.setScale(2, BigDecimal.ROUND_HALF_UP)) == 0);
		//ICMS ST
		assertTrue(docFiscal.getIcmsStBase().compareTo(ICMS_ST_ITEM_VLR_BASE_42_RED_EXPECTED) == 0);
		assertTrue(docFiscal.getIcmsStValor().compareTo(ICMS_ST_ITEM_VLR_RED_EXPECTED.setScale(2, BigDecimal.ROUND_HALF_UP)) == 0);
		assertTrue(docFiscal.getVlrTotalProduto().compareTo(DOC_FISC_ITEM_VALOR_UNITARIO) == 0);

		// DocumentoFiscalItem
		DocumentoFiscalItem docFiscalItem = docFiscal.getItens().get(0);
		assertEquals(70, docFiscalItem.getIcmsCst());
		assertTrue(docFiscalItem.getIcmsAliquota().multiply(new BigDecimal(100D)).compareTo(ICMS_ITEM_ALIQ_EXPECTED_18) == 0);
		assertTrue(docFiscalItem.getIcmsBase().compareTo(ICMS_ITEM_VLR_BASE_60_RED_EXPECTED) == 0);
		assertTrue(docFiscalItem.getIcmsValor().compareTo(ICMS_ITEM_VLR_RED_EXPECTED.setScale(2, BigDecimal.ROUND_HALF_UP)) == 0);

		assertTrue(docFiscalItem.getIcmsStAliquota().multiply(new BigDecimal(100D)).compareTo(ICMS_ST_ALIQ_12_PERC) == 0);
		assertTrue(docFiscalItem.getIcmsIva().multiply(new BigDecimal(100D)).compareTo(IVA_70) == 0);
		assertTrue(docFiscalItem.getIcmsStBase().compareTo(ICMS_ITEM_VLR_BASE_RED_E_IVA_EXPECTED) == 0);
		assertTrue(docFiscalItem.getIcmsStValor().compareTo(ICMS_ITEM_VLR_RED_E_IVA_EXPECTED.setScale(2, BigDecimal.ROUND_HALF_UP)) == 0);
	}
	
	@Test
	public void test08_DeveCalcularVendaICMS90() {
		final BigDecimal ICMS_ITEM_VLR_RED_E_IVA_EXPECTED = new BigDecimal(5.04D);
		final BigDecimal ICMS_ITEM_VLR_BASE_RED_E_IVA_EXPECTED = new BigDecimal(42D);
		TributacaoEstadual tribEstaICMS90 = criaTributaEstaVenda(NcmServiceLogicTest.NCM_NUMERO_REGISTRO_8, 90, EstadoSigla.SP, IVA_70, ICMS_PERC_60_RED_BC, FCP_ALIQ_ZERO, ICMS_ST_ALIQ_12_PERC, ICMS_CFOP_ITEM);
		DocumentoFiscal docFiscal = criaDocumentoFiscalVenda(tribEstaICMS90.getNcm(), ITEM_ICMS_ST_BASE_ULTIM_COMPR_0, ITEM_ICMS_ST_VLR_ULTIM_COMPR_0, QTD_ULTIM_COMP_0, ITEM_ICMS_ST_ALIQ_ULTIM_COMPRA_0);
		
		List<String> erros = docFiscalService.validaDadosESetaValoresNecessarios(docFiscal, true, false);
		LOG.log(Level.WARNING, "ERROS: {0} ", erros);
		assertTrue(erros.isEmpty());
		
		calcFiscalEstadual.calculaImposto(docFiscal);
		
		// DocumentoFiscal
		assertTrue(docFiscal.getIcmsBase().compareTo(ICMS_ITEM_VLR_BASE_60_RED_EXPECTED) == 0);
		assertTrue(docFiscal.getIcmsValor().compareTo(ICMS_ITEM_VLR_RED_EXPECTED.setScale(2, BigDecimal.ROUND_HALF_UP)) == 0);
		//ICMS ST
		assertTrue(docFiscal.getIcmsStBase().compareTo(ICMS_ST_ITEM_VLR_BASE_42_RED_EXPECTED) == 0);
		assertTrue(docFiscal.getIcmsStValor().compareTo(ICMS_ST_ITEM_VLR_RED_EXPECTED.setScale(2, BigDecimal.ROUND_HALF_UP)) == 0);
		assertTrue(docFiscal.getVlrTotalProduto().compareTo(DOC_FISC_ITEM_VALOR_UNITARIO) == 0);

		// DocumentoFiscalItem
		DocumentoFiscalItem docFiscalItem = docFiscal.getItens().get(0);
		assertEquals(90, docFiscalItem.getIcmsCst());
		assertTrue(docFiscalItem.getIcmsAliquota().multiply(new BigDecimal(100D)).compareTo(ICMS_ITEM_ALIQ_EXPECTED_18) == 0);
		assertTrue(docFiscalItem.getIcmsBase().compareTo(ICMS_ITEM_VLR_BASE_60_RED_EXPECTED) == 0);
		assertTrue(docFiscalItem.getIcmsValor().compareTo(ICMS_ITEM_VLR_RED_EXPECTED.setScale(2, BigDecimal.ROUND_HALF_UP)) == 0);
		

		assertTrue(docFiscalItem.getIcmsStAliquota().multiply(new BigDecimal(100D)).compareTo(ICMS_ST_ALIQ_12_PERC) == 0);
		assertTrue(docFiscalItem.getIcmsIva().multiply(new BigDecimal(100D)).compareTo(IVA_70) == 0);
		assertTrue(docFiscalItem.getIcmsStBase().compareTo(ICMS_ITEM_VLR_BASE_RED_E_IVA_EXPECTED) == 0);
		assertTrue(docFiscalItem.getIcmsStValor().compareTo(ICMS_ITEM_VLR_RED_E_IVA_EXPECTED.setScale(2, BigDecimal.ROUND_HALF_UP)) == 0);
	}

	
	
	@Test
	public void test09_DeveCalcularVendaInterestadualRjICMS00ComDIFALeFCP() {
		final BigDecimal ICMS_DIFAL_DOC_UF_DEST_EXPECTED_6 = new BigDecimal(6D);
		final BigDecimal ICMS_FCP_VLR_DOC_EXPECTED_2 = new BigDecimal(2D);
		final BigDecimal ICMS_ITEM_ALIQ_DEST_EXPECTED_12 = new BigDecimal(12D);
		
		TributacaoEstadual tribEstaVendaInterICMS00 = criaTributaEstaVenda(Integer.parseInt(TestHelper.NCM_ICMS_00_DIFAL_FCP), 00, EstadoSigla.RJ, IVA_ZERO, ICMS_BASE_CEM, FCP_ALIQ_2_PERC, ICMS_ST_ALIQ_ZERO, ICMS_CFOP_ITEM);
		DocumentoFiscal docFiscalRJ = criaDocumentoFiscalVenda(tribEstaVendaInterICMS00.getNcm(), ITEM_ICMS_ST_BASE_ULTIM_COMPR_0, ITEM_ICMS_ST_VLR_ULTIM_COMPR_0, QTD_ULTIM_COMP_0, ITEM_ICMS_ST_ALIQ_ULTIM_COMPRA_0);
	
		Pessoa destinatarioRj = pessoaService.findByCnpj(PESSOA_DEST_CNPJ_RJ).get();
		docFiscalRJ.setDestinatario(destinatarioRj);
		
		List<String> erros = docFiscalService.validaDadosESetaValoresNecessarios(docFiscalRJ, true, false);
		LOG.log(Level.WARNING, "ERROS: {0} ", erros);
		assertTrue(erros.isEmpty());
		
		calcFiscalEstadual.calculaImposto(docFiscalRJ);

		// DocumentoFiscal
		assertTrue(docFiscalRJ.getIcmsBase().compareTo(ICMS_BASE_DOC_FISC_EXPECTED_CEM) == 0);
		assertTrue(docFiscalRJ.getIcmsValor().compareTo(ICMS_VLR_DOC_FISC_EXPECTED_18) == 0);
		assertTrue(docFiscalRJ.getIcmsValorUfDestino().compareTo(ICMS_DIFAL_DOC_UF_DEST_EXPECTED_6) == 0);
		assertTrue(docFiscalRJ.getIcmsFcpValor().compareTo(ICMS_FCP_VLR_DOC_EXPECTED_2) == 0);
		assertTrue(docFiscalRJ.getVlrTotalProduto().compareTo(DOC_FISC_ITEM_VALOR_UNITARIO) == 0);
		
		// DocumentoFiscalItem
		DocumentoFiscalItem docFiscalItem = docFiscalRJ.getItens().get(0);
		assertEquals(00, docFiscalItem.getIcmsCst());
		assertTrue(docFiscalItem.getIcmsAliquota().multiply(new BigDecimal(100D)).compareTo(ICMS_ITEM_ALIQ_EXPECTED_18) == 0);
		assertTrue(docFiscalItem.getIcmsAliquotaDestino().multiply(new BigDecimal(100)).compareTo(ICMS_ITEM_ALIQ_DEST_EXPECTED_12) == 0);
		assertTrue(docFiscalItem.getIcmsBase().compareTo(ICMS_ITEM_VLR_BASE_EXPECTED) == 0);
		assertTrue(docFiscalItem.getIcmsValor().compareTo(ICMS_ITEM_VLR_EXPECTED) == 0);
		
		assertTrue(docFiscalItem.getIcmsValorUfDestino().compareTo(ICMS_DIFAL_DOC_UF_DEST_EXPECTED_6) == 0);
		assertTrue(docFiscalItem.getIcmsFcpValor().compareTo(ICMS_FCP_VLR_DOC_EXPECTED_2) == 0);

//		LOG.log(Level.INFO, "DocFiscal calculado: {0} ", docFiscal);
	}
	
	
	@Test
	public void test10_DeveCalcularVendaComQuatroItens() {
		final BigDecimal ICMS_BASE_DOC_FISC_EXPECTED_360 = new BigDecimal(360D);
		final BigDecimal ICMS_VLR_DOC_FISC_EXPECTED = new BigDecimal(64.8D);
		
		final BigDecimal ICMS_ST_BASE_DOC_FISC_EXPECTED_112 = new BigDecimal(112D);
		final BigDecimal ICMS_ST_VLR_DOC_FISC_EXPECTED = new BigDecimal(13.44D);
		final BigDecimal DOC_FISC_VLR_TOTAL_PROD_EXPECTED = new BigDecimal(400D);
		
		DocumentoFiscal docFiscal = criaDocumentoFiscalVendaComQUATROItens(ITEM_ICMS_ST_BASE_ULTIM_COMPR_0, ITEM_ICMS_ST_VLR_ULTIM_COMPR_0, QTD_ULTIM_COMP_0, ITEM_ICMS_ST_ALIQ_ULTIM_COMPRA_0);
		List<String> erros = docFiscalService.validaDadosESetaValoresNecessarios(docFiscal, true, false);
		LOG.log(Level.WARNING, "ERROS: {0} ", erros);
		assertTrue(erros.isEmpty());
		
		calcFiscalEstadual.calculaImposto(docFiscal);
		
		// DocumentoFiscal
		assertTrue(docFiscal.getIcmsBase().compareTo(ICMS_BASE_DOC_FISC_EXPECTED_360) == 0);
		assertTrue(docFiscal.getIcmsValor().compareTo(ICMS_VLR_DOC_FISC_EXPECTED.setScale(2, BigDecimal.ROUND_HALF_UP)) == 0);
		//ICMS ST
		assertTrue(docFiscal.getIcmsStBase().compareTo(ICMS_ST_BASE_DOC_FISC_EXPECTED_112) == 0);
		assertTrue(docFiscal.getIcmsStValor().compareTo(ICMS_ST_VLR_DOC_FISC_EXPECTED.setScale(2, BigDecimal.ROUND_HALF_UP)) == 0);
		assertTrue(docFiscal.getVlrTotalProduto().compareTo(DOC_FISC_VLR_TOTAL_PROD_EXPECTED) == 0);
		// DocumentoFiscalItem
			/**
			 * Como os Testes acima já testam esses valores referentes ao ITEM, só testei os TOTAIS do DocumentoFiscal, com QUATRO itens,
			 * De CSTs diferentes (00, 10, 30 e 60);
			 * 
			 */
	}
	
	private DocumentoFiscal criaDocumentoFiscalVenda(Ncm ncm, BigDecimal icmsStBaseUltimaCompra, BigDecimal icmsStValorUltimaCompra, 
			BigDecimal itemQtdCompradaUltimaCompra, BigDecimal icmsStAliqUltimaCompra) {
		
		DocumentoFiscal docFiscal = new DocumentoFiscal();
		List<DocumentoFiscalItem> listItens = new ArrayList<>();
		DocumentoFiscalItem docFiscalItem = new DocumentoFiscalItem();

		Pessoa emitente = pessoaService.findByCnpj(PESSOA_EMITENTE_CNPJ).get();
		Pessoa destinatario = pessoaService.findByCnpj(PESSOA_DEST_CNPJ_SP).get();
				
		Operacao operacao = new Operacao();
		operacao.setDescricao("VENDA");

		docFiscal.setOperacao(operacao);
		docFiscal.setTipoOperacao(IndicadorDeOperacao.SAIDA);
		docFiscal.setSerie(DOC_FISC_SERIE);
		docFiscal.setNumeroNota(2L);
		
		// falta setar esses abaixo
		docFiscal.setEmitente(emitente);
		docFiscal.setDestinatario(destinatario);
		
		docFiscalItem.setFinalidade(Finalidade.CONSUMO);
		docFiscalItem.setOrigem(ProdutoOrigem.NACIONAL);
		docFiscalItem.setQuantidade(DOC_FISC_ITEM_QUANTIDADE);
		docFiscalItem.setValorUnitario(DOC_FISC_ITEM_VALOR_UNITARIO);
		docFiscalItem.setNcm(ncm);
		
		docFiscalItem.setIcmsStBaseUltimaCompra(icmsStBaseUltimaCompra);
		docFiscalItem.setIcmsStValorUltimaCompra(icmsStValorUltimaCompra);
		docFiscalItem.setItemQtdCompradaUltimaCompra(itemQtdCompradaUltimaCompra);
		docFiscalItem.setIcmsStAliqUltimaCompra(icmsStAliqUltimaCompra);
		
		listItens.add(docFiscalItem);
		docFiscal.setItens(listItens);
		return docFiscal;
	}
	
	private DocumentoFiscal criaDocumentoFiscalVendaComQUATROItens(BigDecimal icmsStBaseUltimaCompra, BigDecimal icmsStValorUltimaCompra, 
			BigDecimal itemQtdCompradaUltimaCompra, BigDecimal icmsStAliqUltimaCompra) {
		Ncm ncm00 = ncmService.findNcmByNumeroAndExcecao(Integer.parseInt(TestHelper.NCM_ICMS_00), 0).get();
		Ncm ncm10 = ncmService.findNcmByNumeroAndExcecao(Integer.parseInt(TestHelper.NCM_ICMS_10), 0).get();
		Ncm ncm30 = ncmService.findNcmByNumeroAndExcecao(Integer.parseInt(TestHelper.NCM_ICMS_30), 0).get();
		Ncm ncm60 = ncmService.findNcmByNumeroAndExcecao(Integer.parseInt(TestHelper.NCM_ICMS_60), 0).get();
		List<Ncm> listNcms = Arrays.asList(ncm10, ncm30, ncm60);
		
		DocumentoFiscal docFiscal = criaDocumentoFiscalVenda(ncm00, icmsStBaseUltimaCompra, icmsStValorUltimaCompra, itemQtdCompradaUltimaCompra, icmsStAliqUltimaCompra);
		List<DocumentoFiscalItem>  listItens = docFiscal.getItens();
		
		listNcms.stream().forEach(ncm -> {
			DocumentoFiscal documentoFiscal = criaDocumentoFiscalVenda(ncm, icmsStBaseUltimaCompra, icmsStValorUltimaCompra, itemQtdCompradaUltimaCompra, icmsStAliqUltimaCompra);
			listItens.add(documentoFiscal.getItens().get(0));
		});
		
		docFiscal.setItens(listItens);
		return docFiscal;
	}
		
}

