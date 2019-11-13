package net.cartola.emissorfiscal.emissorfiscal.service;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
import net.cartola.emissorfiscal.documento.Finalidade;
import net.cartola.emissorfiscal.estado.Estado;
import net.cartola.emissorfiscal.estado.EstadoService;
import net.cartola.emissorfiscal.estado.EstadoSigla;
import net.cartola.emissorfiscal.ncm.Ncm;
import net.cartola.emissorfiscal.ncm.NcmService;
import net.cartola.emissorfiscal.operacao.Operacao;
import net.cartola.emissorfiscal.operacao.OperacaoService;
import net.cartola.emissorfiscal.tributacao.estadual.CalculoFiscalEstadual;
import net.cartola.emissorfiscal.tributacao.estadual.TributacaoEstadual;
import net.cartola.emissorfiscal.tributacao.estadual.TributacaoEstadualService;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TributacaoEstadualLogicTest {
	
	@Autowired
	private CalculoFiscalEstadual calculoFiscalEstadual;

	@Autowired
	private EstadoService estadoService;
	
	@Autowired
	private OperacaoService operacaoService;
	
	@Autowired
	private NcmService ncmService;
	
	@Autowired
	private TributacaoEstadualService icmsService;
	
	@Autowired
	private TestHelper testHelper;
	
	
	// TRIBUTAÇÃO ESTADUAL (icms)
	public static int TRIBUTACAO_ESTADUAL_ICMS_CST = 321;
	public static BigDecimal TRIBUTACAO_ESTADUAL_ICMS_BASE = new BigDecimal(9.0);
	public static BigDecimal TRIBUTACAO_ESTADUAL_ICMS_ALIQUOTA = new BigDecimal(5.0);
	public static BigDecimal TRIBUTACAO_ESTADUAL_ICMS_IVA = new BigDecimal(2);
	public static BigDecimal TRIBUTACAO_ESTADUAL_ICMS_ALIQUOTA_DESTINO = new BigDecimal(7.0);
	public static int TRIBUTACAO_ESTADUAL_ICMS_CEST = 123;
	public static String TRIBUTACAO_ESTADUAL_ICMS_MENSAGEM = " Essa é uma mensagem fake";
	
	
//	ITEM
	private static long ITEM_ID = 1L;
	private static Finalidade ITEM_FINALIDADE = Finalidade.CONSUMO;
	private static BigDecimal ITEM_QUANTIDADE = new BigDecimal(2);
	private static BigDecimal ITEM_VALOR_UNITARIO = new BigDecimal(5);
	private static int ITEM_CFOP = 1234;
	private static BigDecimal ITEM_ICMS_BASE = new BigDecimal(2);
	private static BigDecimal ITEM_ICMS_ALIQUOTA = new BigDecimal(5);
	private static BigDecimal ITEM_ICMS_VALOR = new BigDecimal(10);
	
//	private static BigDecimal ITEM_VALOR_UNITARIO = new BigDecimal(100);
//	private static BigDecimal ITEM_QUANTIDADE = new 
	
	
	@Test
	public void test00_CleanUp() {
		testHelper.cleanUp();
		testHelper.criarEstados();
		testHelper.criarDocumentoFiscal();
	}
	
	
	// Venda ESTADUAL
	@Test
	public void test01_DeveCalcularOIcmsDeSPparaSP() {
		// Buscando o Estado Origem inserido previamente
		Optional<Estado> opEstadoOrigem = estadoService.findBySigla(EstadoSigla.SP);
		assertTrue(opEstadoOrigem.isPresent());

		Optional<Ncm> opNcm = ncmService.findByNumero(NcmServiceLogicTest.NCM_NUMERO_REGISTRO_1);
		assertTrue(opNcm.isPresent());

		Optional<Operacao> opOperacao = operacaoService.findOperacaoByDescricao(TestHelper.OPERACAO_VENDA);
		assertTrue(opOperacao.isPresent());

		DocumentoFiscalItem item = new DocumentoFiscalItem();
		item.setId(ITEM_ID);
		item.setFinalidade(ITEM_FINALIDADE);
		item.setQuantidade(ITEM_QUANTIDADE);
		item.setValorUnitario(ITEM_VALOR_UNITARIO);
		item.setCfop(ITEM_CFOP);
		item.setIcmsBase(ITEM_ICMS_BASE);
		item.setIcmsAliquota(ITEM_ICMS_ALIQUOTA);
		item.setIcmsValor(ITEM_ICMS_VALOR);
		
		item.setNcm(opNcm.get());
//		item.setValorUnitario(new BigDecimal(100));
//		item.setQuantidade(new BigDecimal(2));
		List<DocumentoFiscalItem> itens = Arrays.asList(item,item);
		
		DocumentoFiscal doc = new DocumentoFiscal();
		doc.setDestinatarioUf(opEstadoOrigem.get().getSigla());
		doc.setEmitenteUf(opEstadoOrigem.get().getSigla());
		doc.setItens(itens);
		
		TributacaoEstadual icms = new TributacaoEstadual();
		icms.setEstadoOrigem(opEstadoOrigem.get());
		icms.setEstadoDestino(opEstadoOrigem.get());
		icms.setOperacao(opOperacao.get());
		icms.setNcm(opNcm.get());
		icms.setIcmsCst(TRIBUTACAO_ESTADUAL_ICMS_CST);
		icms.setIcmsBase(TRIBUTACAO_ESTADUAL_ICMS_BASE);
		icms.setIcmsAliquota(TRIBUTACAO_ESTADUAL_ICMS_ALIQUOTA);
		icms.setIcmsIva(TRIBUTACAO_ESTADUAL_ICMS_IVA);
		icms.setIcmsAliquotaDestino(TRIBUTACAO_ESTADUAL_ICMS_ALIQUOTA_DESTINO);
		icms.setCest(TRIBUTACAO_ESTADUAL_ICMS_CEST);
		icms.setMensagem(TRIBUTACAO_ESTADUAL_ICMS_MENSAGEM);
		Optional<TributacaoEstadual> opIcms = icmsService.save(icms);
		assertTrue(opIcms.isPresent());
		
		System.out.println("|ANTES DO CALCULO| doc.getIcmsValor() "+ doc.getIcmsValor());

		
		calculoFiscalEstadual.calculaImposto(doc);

//		assertNotNull(calcIcms);
		
		System.out.println("doc.getIcmsBase() "+ doc.getIcmsBase());
		System.out.println("doc.getIcmsValor() "+ doc.getIcmsValor());
		
		System.out.println("doc.getItens().get(0).getIcmsBase() "+ doc.getItens().get(0).getIcmsBase());
		System.out.println("doc.getItens().get(0).getIcmsValor() "+ doc.getItens().get(0).getIcmsValor());
		System.out.println("doc.getItens().get(0).getIcmsAliquota() "+ doc.getItens().get(0).getIcmsAliquota());

		
//		System.out.println(calcIcms);
//		System.out.println(calcIcms.getValor());
		
		System.out.println(this.getClass().getName() + " test01_DeveCalcularOIcmsDeSPparaSP, Ok");

	}
	
	
	
}
