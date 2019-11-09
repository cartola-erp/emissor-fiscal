package net.cartola.emissorfiscal.emissorfiscal.service;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import net.cartola.emissorfiscal.tributacao.federal.CalculoFiscalFederal;

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

	@Autowired
	private CalculoFiscalFederal calculoFiscalFederal;
	
	@Autowired
	private TestHelper testHelper;
	
	@Test
	public void test00_CleanUp() {
		testHelper.cleanUp();
		testHelper.criarEstados();
		testHelper.criarOperacoes();
		testHelper.criarNcm();
	}
	
	@Test
	public void test01_CalcularPisCofinsIPI() {
		
	}
}
