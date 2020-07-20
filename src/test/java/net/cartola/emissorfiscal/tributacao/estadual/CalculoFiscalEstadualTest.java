package net.cartola.emissorfiscal.tributacao.estadual;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
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
import net.cartola.emissorfiscal.documento.Finalidade;
import net.cartola.emissorfiscal.documento.ProdutoOrigem;
import net.cartola.emissorfiscal.emissorfiscal.service.NcmServiceLogicTest;
import net.cartola.emissorfiscal.emissorfiscal.service.TestHelper;
import net.cartola.emissorfiscal.estado.EstadoService;
import net.cartola.emissorfiscal.ncm.Ncm;
import net.cartola.emissorfiscal.ncm.NcmService;
import net.cartola.emissorfiscal.operacao.Operacao;
import net.cartola.emissorfiscal.operacao.OperacaoService;
import net.cartola.emissorfiscal.pessoa.Pessoa;
import net.cartola.emissorfiscal.pessoa.PessoaService;

import static net.cartola.emissorfiscal.emissorfiscal.service.TestHelper.PESSOA_EMITENTE_CNPJ;
import static net.cartola.emissorfiscal.emissorfiscal.service.TestHelper.PESSOA_DEST_CNPJ_SP;


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
	private CalculoFiscalEstadual calcFiscalEstadual;
	
	@Autowired
	private TestHelper testHelper;
	
	public static final String OPERACAO_DESCRICAO = "VENDA";
	
	public static final String DOC_FISC_TIPO = "NFE";
	public static final Long DOC_FISC_SERIE = 1L;
	
	public static final BigDecimal DOC_FISC_ITEM_VALOR_UNITARIO = new BigDecimal(100D);
	public static final BigDecimal DOC_FISC_ITEM_QUANTIDADE = BigDecimal.ONE;
	// VALORES REFERENTES a última compra !?
	private static final BigDecimal ITEM_ICMS_ST_BASE_ULTIM_COMPR = BigDecimal.ZERO;
	private static final BigDecimal ITEM_ICMS_ST_VLR_ULTIM_COMPR = BigDecimal.ZERO;
	private static final BigDecimal QTD_ULTIM_COMP = BigDecimal.ZERO;
	private static final BigDecimal ITEM_ICMS_ST_ALIQ_ULTIM_COMPRA = BigDecimal.ZERO;

	
	private static final BigDecimal IVA_ZERO = BigDecimal.ZERO;
	private static final BigDecimal ICMS_BASE_CEM = new BigDecimal(100D);
	private static final BigDecimal FCP_ALIQ_ZERO = BigDecimal.ZERO;
	private static final BigDecimal ICMS_ST_ALIQ_ZERO = BigDecimal.ZERO;

	
	@Test
	public void test00_CleanUp() {
		testHelper.limpaBanco();
		testHelper.criarPessoa();
		testHelper.criarNcms();
		testHelper.criarOperacoes();
		testHelper.criarEstados();
	}
	
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
		
	// set()
	
	private static final BigDecimal ICMS_BASE_DOC_FISC_EXPECTED = new BigDecimal(100D);
	private static final BigDecimal ICMS_VLR_DOC_FISC_EXPECTED = new BigDecimal(18D);
//	private static final BigDecimal ICMS_FCP_VLR_DOC_FISC_EXPECTED = new BigDecimal(null);
//	private static final BigDecimal ICMS_FCP_BASE_DOC_FISC_EXPECTED = new BigDecimal(null);

	// ITEM 1 
	private static final BigDecimal ICMS_ITEM_ALIQ_EXPECTED = new BigDecimal(18D);
	private static final BigDecimal ICMS_ITEM_VLR_BASE_EXPECTED = new BigDecimal(100D);
	private static final BigDecimal ICMS_ITEM_VLR_EXPECTED = new BigDecimal(18D);
	
	// colocar os valores do SEGUNDO ITEM aqui
	
	
	private TributacaoEstadual criaTributaEstaVenda(int ncm, int cst, BigDecimal iva, BigDecimal icmsBase, BigDecimal fcpAliq, BigDecimal icmsStAliq) {
		Optional<Ncm> opNcm = ncmService.findNcmByNumeroAndExcecao(ncm, 0);
		assertTrue(opNcm.isPresent());
//		Ncm ncm = opNcm.get();
		TributacaoEstadual tribEstaICMS = testHelper.criarTribEstaVenda(opNcm.get(), cst, iva, icmsBase, fcpAliq, icmsStAliq);
		assertNotNull(tribEstaICMS);
		return tribEstaICMS;
	}
	
	
	@Test
	public void test01_DeveCalcularVendaICMS00() {
		TributacaoEstadual tribEstaICMS00 = criaTributaEstaVenda(NcmServiceLogicTest.NCM_NUMERO_REGISTRO_1, 00, IVA_ZERO, ICMS_BASE_CEM, FCP_ALIQ_ZERO, ICMS_ST_ALIQ_ZERO);
		DocumentoFiscal docFiscal = criaDocumentoFiscalVenda(tribEstaICMS00.getNcm(), ITEM_ICMS_ST_BASE_ULTIM_COMPR, ITEM_ICMS_ST_VLR_ULTIM_COMPR, QTD_ULTIM_COMP, ITEM_ICMS_ST_ALIQ_ULTIM_COMPRA);

		List<String> erros = docFiscalService.validaDadosESetaValoresNecessarios(docFiscal, true, false);
		LOG.log(Level.WARNING, "ERROS: {0} ", erros);
		assertTrue(erros.isEmpty());
	
		calcFiscalEstadual.calculaImposto(docFiscal);

		// DocumentoFiscal
		assertTrue(docFiscal.getIcmsBase().compareTo(ICMS_BASE_DOC_FISC_EXPECTED) == 0);
		assertTrue(docFiscal.getIcmsValor().compareTo(ICMS_VLR_DOC_FISC_EXPECTED) == 0);
		
		// DocumentoFiscalItem
		DocumentoFiscalItem docFiscalItem = docFiscal.getItens().get(0);
		assertEquals(00, docFiscalItem.getIcmsCst());
		assertTrue(docFiscalItem.getIcmsAliquota().multiply(new BigDecimal(100D)).compareTo(ICMS_ITEM_ALIQ_EXPECTED) == 0);
		assertTrue(docFiscalItem.getIcmsBase().compareTo(ICMS_ITEM_VLR_BASE_EXPECTED) == 0);
		assertTrue(docFiscalItem.getIcmsValor().compareTo(ICMS_ITEM_VLR_EXPECTED) == 0);
		
		LOG.log(Level.INFO, "DocFiscal calculado: {0} ", docFiscal);
	}
	
	@Test
	public void test02_DeveCalcularVendaICMS10() {
		TributacaoEstadual tribEstaICMS10 = criaTributaEstaVenda(NcmServiceLogicTest.NCM_NUMERO_REGISTRO_2, 10, IVA_ZERO, ICMS_BASE_CEM, FCP_ALIQ_ZERO, ICMS_ST_ALIQ_ZERO);
		DocumentoFiscal docFiscal = criaDocumentoFiscalVenda(tribEstaICMS10.getNcm(), ITEM_ICMS_ST_BASE_ULTIM_COMPR, ITEM_ICMS_ST_VLR_ULTIM_COMPR, QTD_ULTIM_COMP, ITEM_ICMS_ST_ALIQ_ULTIM_COMPRA);

		List<String> erros = docFiscalService.validaDadosESetaValoresNecessarios(docFiscal, true, false);
		LOG.log(Level.WARNING, "ERROS: {0} ", erros);
		
		assertTrue(erros.isEmpty());
		
		calcFiscalEstadual.calculaImposto(docFiscal);
		
		// DocumentoFiscal
		assertTrue(docFiscal.getIcmsBase().compareTo(ICMS_BASE_DOC_FISC_EXPECTED) == 0);
		assertTrue(docFiscal.getIcmsValor().compareTo(ICMS_VLR_DOC_FISC_EXPECTED) == 0);
		
		// DocumentoFiscalItem
		DocumentoFiscalItem docFiscalItem = docFiscal.getItens().get(0);
		assertEquals(10, docFiscalItem.getIcmsCst());
		assertTrue(docFiscalItem.getIcmsAliquota().multiply(new BigDecimal(100D)).compareTo(ICMS_ITEM_ALIQ_EXPECTED) == 0);
		assertTrue(docFiscalItem.getIcmsBase().compareTo(ICMS_ITEM_VLR_BASE_EXPECTED) == 0);
		assertTrue(docFiscalItem.getIcmsValor().compareTo(ICMS_ITEM_VLR_EXPECTED) == 0);
	}
	
	public void test03_DeveCalcularVendaICMS20() {
		
	}
	
	

	
	private DocumentoFiscal criaDocumentoFiscalVenda(Ncm ncm, BigDecimal icmsStBaseUltimaCompra, BigDecimal icmsStValorUltimaCompra, 
			BigDecimal itemQtdCompradaUltimaCompra, BigDecimal icmsStAliqUltimaCompra) {
		
		DocumentoFiscal docFiscal = new DocumentoFiscal();
		List<DocumentoFiscalItem> listItens = new ArrayList<>();
		DocumentoFiscalItem docFiscalItem = new DocumentoFiscalItem();

		Pessoa emitente = pessoaService.findByCnpj(Long.valueOf(PESSOA_EMITENTE_CNPJ)).get(0);
		Pessoa destinatario = pessoaService.findByCnpj(Long.valueOf(PESSOA_DEST_CNPJ_SP)).get(0);
				
		Operacao operacao = new Operacao();
		operacao.setDescricao("VENDA");

		docFiscal.setOperacao(operacao);
		docFiscal.setTipo(DOC_FISC_TIPO);
		docFiscal.setSerie(DOC_FISC_SERIE);
		docFiscal.setNumero(2L);
		
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
}

