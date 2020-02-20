package net.cartola.emissorfiscal.emissorfiscal.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import net.cartola.emissorfiscal.documento.DocumentoFiscal;
import net.cartola.emissorfiscal.documento.DocumentoFiscalService;
import net.cartola.emissorfiscal.ncm.NcmService;
import net.cartola.emissorfiscal.operacao.OperacaoService;
import net.cartola.emissorfiscal.tributacao.federal.CalculoFiscalFederal;
import net.cartola.emissorfiscal.tributacao.federal.TributacaoFederal;

/**
 * 8 de nov de 2019
 * 
 * @author gregory.feijon
 */

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
//@Component
public class TributacaoFederalServiceLogicTest {

	public static final int PIS_CST = 1;
	public static final int COFINS_CST = 1;
	public static final int IPI_CST = 1;
	public static final BigDecimal PIS_ALIQUOTA = new BigDecimal(1.65D);
	public static final BigDecimal COFINS_ALIQUOTA = new BigDecimal(7.6D);
	public static final BigDecimal IPI_ALIQUOTA = new BigDecimal(5D); // valor arbitrário
	public static final BigDecimal PIS_BASE = new BigDecimal(1D);		// 1, pois é "TRIBUTADA COM ALIQ. BÁSICA". (100/100 = 1)
	public static final BigDecimal COFINS_BASE = new BigDecimal(1D);	// 1, pois é "TRIBUTADA COM ALIQ. BÁSICA". (100/100 = 1)
	public static final BigDecimal IPI_BASE = new BigDecimal(9D);

	@Autowired
	private CalculoFiscalFederal calculoFiscalFederal;

	@Autowired
	private DocumentoFiscalService documentoFiscalService;

	@Autowired
	private NcmService ncmService;
	
	@Autowired
	private OperacaoService operacaoService;
	
	@Autowired
	private TestHelper testHelper;

	@Test
	public void test00_CleanUp() {
		testHelper.limpaBanco();
		testHelper.criarEstados();
		testHelper.criarDocumentoFiscal();
	}

//	public List<TributacaoFederal> criaTributacaoFederal(List<DocumentoFiscal> documentosFiscais) {
//		List<TributacaoFederal> listTribFederal = new LinkedList<>();
//		documentosFiscais.stream().forEach(docFiscal -> {
//			docFiscal.getItens().stream().forEach(docFiscalItem -> {
//				boolean existeTribFedNcmOper = false;
//				if (listTribFederal != null && !listTribFederal.isEmpty()) {
//					existeTribFedNcmOper = verificaListaTribFederal(listTribFederal, docFiscal, docFiscalItem);
//				}
//				if (!existeTribFedNcmOper) {
//					TributacaoFederalBuilder tributFedBuilder = new TributacaoFederalBuilder()
//							.withNcm(docFiscalItem.getNcm()).withOperacao(docFiscal.getOperacao()).withPisCst(PIS_CST)
//							.withPisBase(PIS_BASE).withPisAliquota(PIS_ALIQUOTA).withCofinsCst(COFINS_CST)
//							.withCofinsBase(COFINS_BASE).withCofinsAliquota(COFINS_ALIQUOTA).withIpiCst(IPI_CST)
//							.withIpiBase(IPI_BASE).withIpiAliquota(IPI_ALIQUOTA);
//					listTribFederal.add(tributFedBuilder.build());
//				}
//			});
//		});
//		return listTribFederal;
//	}

//	private boolean verificaListaTribFederal(List<TributacaoFederal> listTribFederal, DocumentoFiscal docFiscal,
//			DocumentoFiscalItem docFiscalItem) {
//		return listTribFederal.stream()
//				.filter(tribFed -> tribFed.getNcm().getId().equals(docFiscalItem.getNcm().getId())
//						&& tribFed.getOperacao().getId().equals(docFiscal.getOperacao().getId()))
//				.findAny().isPresent();
//	}

	@Test
	public void test01_CalcularPisCofinsIPI() {
		List<DocumentoFiscal> documentosFiscais = documentoFiscalService.findAll();
		
		List<TributacaoFederal> tributacoesFederais = testHelper.criarTributacaoFederal(ncmService.findAll(), operacaoService.findOperacaoByDescricao(TestHelper.OPERACAO_VENDA).get());
		
		calculoFiscalFederal.calculaImposto(documentosFiscais.get(0));

		DocumentoFiscal documentoFiscal = documentosFiscais.get(0);
		assertNotNull(documentoFiscal);
		assertEquals( new BigDecimal("1.2500000000"), documentoFiscal.getPisBase());
		assertEquals(new BigDecimal("1.2500000000"), documentoFiscal.getCofinsBase());
		assertEquals(new BigDecimal("0.0206250000000000"), documentoFiscal.getPisValor());
		assertEquals(new BigDecimal("0.0950000000000000"), documentoFiscal.getCofinsValor());

//		System.out.println("\n\n");
		
//		System.out.println("documentoFiscal.getPisBase() == " +documentoFiscal.getPisBase());
//		System.out.println("documentoFiscal.getCofinsBase() == " + documentoFiscal.getCofinsBase());
//		System.out.println("documentoFiscal.getPisValor() == " +documentoFiscal.getPisValor());
//		System.out.println("documentoFiscal.getCofinsValor() == " +documentoFiscal.getCofinsValor());	
		
	}
}
