package net.cartola.emissorfiscal.emissorfiscal.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.LinkedList;
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
import net.cartola.emissorfiscal.documento.DocumentoFiscalItem;
import net.cartola.emissorfiscal.documento.DocumentoFiscalService;
import net.cartola.emissorfiscal.emissorfiscal.model.TributacaoFederalBuilder;
import net.cartola.emissorfiscal.tributacao.federal.CalculoFiscalFederal;
import net.cartola.emissorfiscal.tributacao.federal.TributacaoFederal;
import net.cartola.emissorfiscal.tributacao.federal.TributacaoFederalService;

/**
 * 8 de nov de 2019
 * 
 * @author gregory.feijon
 */

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TributacaoFederalServiceLogicTest {

	public static final int PIS_CST = 1;
	public static final int COFINS_CST = 1;
	public static final int IPI_CST = 1;
	public static final BigDecimal PIS_ALIQUOTA = new BigDecimal(0.0165D);
	public static final BigDecimal COFINS_ALIQUOTA = new BigDecimal(0.076D);
	public static final BigDecimal IPI_ALIQUOTA = new BigDecimal(0.05D); // valor arbitrário
	public static final BigDecimal PIS_BASE = new BigDecimal(1D);		// 1, pois é "TRIBUTADA COM ALIQ. BÁSICA". (100/100 = 1)
	public static final BigDecimal COFINS_BASE = new BigDecimal(1D);	// 1, pois é "TRIBUTADA COM ALIQ. BÁSICA". (100/100 = 1)
	public static final BigDecimal IPI_BASE = new BigDecimal(0.9D);

	@Autowired
	private CalculoFiscalFederal calculoFiscalFederal;

	@Autowired
	private TributacaoFederalService tributacaoFederalService;

	@Autowired
	private DocumentoFiscalService documentoFiscalService;

	@Autowired
	private TestHelper testHelper;

	@Test
	public void test00_CleanUp() {
		testHelper.limpaBanco();
		testHelper.criarEstados();
		testHelper.criarDocumentoFiscal();
	}

	private List<TributacaoFederal> criaTributacaoFederal(List<DocumentoFiscal> documentosFiscais) {
		List<TributacaoFederal> listTribFederal = new LinkedList<>();
		documentosFiscais.stream().forEach(docFiscal -> {
			docFiscal.getItens().stream().forEach(docFiscalItem -> {
				boolean existeTribFedNcmOper = false;
				if (listTribFederal != null && !listTribFederal.isEmpty()) {
					existeTribFedNcmOper = verificaListaTribFederal(listTribFederal, docFiscal, docFiscalItem);
				}
				if (!existeTribFedNcmOper) {
					TributacaoFederalBuilder tributFedBuilder = new TributacaoFederalBuilder()
							.withNcm(docFiscalItem.getNcm()).withOperacao(docFiscal.getOperacao()).withPisCst(PIS_CST)
							.withPisBase(PIS_BASE).withPisAliquota(PIS_ALIQUOTA).withCofinsCst(COFINS_CST)
							.withCofinsBase(COFINS_BASE).withCofinsAliquota(COFINS_ALIQUOTA).withIpiCst(IPI_CST)
							.withIpiBase(IPI_BASE).withIpiAliquota(IPI_ALIQUOTA);
					listTribFederal.add(tributFedBuilder.build());
				}
			});
		});
		return listTribFederal;
	}

	private boolean verificaListaTribFederal(List<TributacaoFederal> listTribFederal, DocumentoFiscal docFiscal,
			DocumentoFiscalItem docFiscalItem) {
		return listTribFederal.stream()
				.filter(tribFed -> tribFed.getNcm().getId().equals(docFiscalItem.getNcm().getId())
						&& tribFed.getOperacao().getId().equals(docFiscal.getOperacao().getId()))
				.findAny().isPresent();
	}

	@Test
	public void test01_CalcularPisCofinsIPI() {
		List<TributacaoFederal> tributacoesFederais = new LinkedList<>();
		List<DocumentoFiscal> documentosFiscais = documentoFiscalService.findAll();
		tributacoesFederais.addAll(criaTributacaoFederal(documentosFiscais));
		tributacaoFederalService.saveAll(tributacoesFederais);

		documentosFiscais.stream().forEach(docFiscal -> {
			calculoFiscalFederal.calculaImposto(docFiscal);
		});

		DocumentoFiscal documentoFiscal = documentosFiscais.get(0);
		assertNotNull(documentoFiscal);
		assertEquals(documentoFiscal.getPisBase(), new BigDecimal("125.0000000000"));
		assertEquals(documentoFiscal.getCofinsBase(), new BigDecimal("125.0000000000"));
		assertEquals(documentoFiscal.getPisValor(), new BigDecimal("2.0625000000000000"));
		assertEquals(documentoFiscal.getCofinsValor(), new BigDecimal("9.5000000000000000"));

//		System.out.println("\n\n");
		
//		System.out.println("documentoFiscal.getPisBase() == " +documentoFiscal.getPisBase());
//		System.out.println("documentoFiscal.getCofinsBase() == " + documentoFiscal.getCofinsBase());
//		System.out.println("documentoFiscal.getPisValor() == " +documentoFiscal.getPisValor());
//		System.out.println("documentoFiscal.getCofinsValor() == " +documentoFiscal.getCofinsValor());	
		
	}
}
