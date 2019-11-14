package net.cartola.emissorfiscal.emissorfiscal.service;

import static org.junit.Assert.assertTrue;

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
import net.cartola.emissorfiscal.documento.DocumentoFiscalRepository;
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

	@Autowired
	private CalculoFiscalFederal calculoFiscalFederal;

	@Autowired
	private TributacaoFederalService tributacaoFederalService;

	// temporário, até fazer a service
	@Autowired
	private DocumentoFiscalRepository documentoFiscalRepository;

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
		TributacaoFederalBuilder tributFedBuilder = new TributacaoFederalBuilder().withNcm(ncmService.findOne(1L).get())
				.withOperacao(operacaoService.findOne(1L).get()).withPisCst(PIS_CST).withCofinsCst(COFINS_CST)
				.withIpiCst(IPI_CST);
		TributacaoFederal tributacaoFederal = tributFedBuilder.build();
		Optional<TributacaoFederal> opTribFed = tributacaoFederalService.save(tributacaoFederal);
		assertTrue(opTribFed.isPresent());

		DocumentoFiscal docFiscal = documentoFiscalRepository.findById(1L).get();
		calculoFiscalFederal.calculaImposto(docFiscal);
		System.out.println(docFiscal.getPisBase());
		System.out.println(docFiscal.getCofinsBase());
		System.out.println(docFiscal.getPisValor());
		System.out.println(docFiscal.getCofinsValor());
	}
}
