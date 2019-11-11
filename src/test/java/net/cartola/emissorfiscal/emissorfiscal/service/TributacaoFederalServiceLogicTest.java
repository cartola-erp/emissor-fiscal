package net.cartola.emissorfiscal.emissorfiscal.service;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

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

	@Autowired
	private NcmService ncmService;

	@Autowired
	private OperacaoService operacaoService;

	@Autowired
	private TestHelper testHelper;

	@Test
	public void test00_CleanUp() {
		testHelper.cleanUp();
		testHelper.criarEstados();
		testHelper.criarOperacoes();
		testHelper.criarDocumentoFiscal();
	}

	@Test
	public void test01_CalcularPisCofinsIPI() {
		TributacaoFederal tributacaoFederal = new TributacaoFederal();
		tributacaoFederal.setPisCst(PIS_CST);
		tributacaoFederal.setCofinsCst(COFINS_CST);
		tributacaoFederal.setIpiCst(IPI_CST);
		tributacaoFederal.setNcm(ncmService.findOne(1L).get());
		tributacaoFederal.setOperacao(operacaoService.findOne(1L).get());
		
		
	}
}
