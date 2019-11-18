package net.cartola.emissorfiscal.emissorfiscal.service;

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
import net.cartola.emissorfiscal.documento.DocumentoFiscalService;
import net.cartola.emissorfiscal.emissorfiscal.model.TributacaoFederalBuilder;
import net.cartola.emissorfiscal.ncm.NcmService;
import net.cartola.emissorfiscal.operacao.OperacaoService;
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
	public static final BigDecimal PIS_ALIQUOTA = new BigDecimal(1.65D);
	public static final BigDecimal COFINS_ALIQUOTA = new BigDecimal(7.6D);
	public static final BigDecimal IPI_ALIQUOTA = new BigDecimal(5D); // valor imaginário -> ver como definir esse valor

	@Autowired
	private CalculoFiscalFederal calculoFiscalFederal;

	@Autowired
	private TributacaoFederalService tributacaoFederalService;

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

	@Test
	public void test01_CalcularPisCofinsIPI() {
		List<TributacaoFederal> tributacoesFederais = new LinkedList<>();
		List<DocumentoFiscal> documentosFiscais = documentoFiscalService.findAll();
		documentosFiscais.stream().forEach(docFiscal -> tributacoesFederais.add(criaTributacaoFederal(docFiscal)));
		tributacaoFederalService.saveAll(tributacoesFederais);

		documentosFiscais.stream().forEach(docFiscal -> {
			calculoFiscalFederal.calculaImposto(docFiscal);
			System.out.println(docFiscal.getPisBase());
			System.out.println(docFiscal.getCofinsBase());
			System.out.println(docFiscal.getPisValor());
			System.out.println(docFiscal.getCofinsValor());
		});
	}

	private TributacaoFederal criaTributacaoFederal(DocumentoFiscal docFiscal) {
		TributacaoFederalBuilder tributFedBuilder = new TributacaoFederalBuilder().withNcm(ncmService.findOne(1L).get())
				.withOperacao(operacaoService.findOne(1L).get()).withPisCst(PIS_CST).withPisBase()
				.withPisAliquota(PIS_ALIQUOTA).withCofinsCst(COFINS_CST).withCofinsBase()
				.withCofinsAliquota(COFINS_ALIQUOTA).withIpiCst(IPI_CST).withIpiBase().withIpiAliquota(IPI_ALIQUOTA);
		TributacaoFederal tributacaoFederal = tributFedBuilder.build();
	}
}
