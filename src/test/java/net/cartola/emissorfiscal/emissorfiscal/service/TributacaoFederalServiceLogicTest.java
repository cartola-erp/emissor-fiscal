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
	public static final BigDecimal PIS_ALIQUOTA = new BigDecimal(1.65D);
	public static final BigDecimal COFINS_ALIQUOTA = new BigDecimal(7.6D);
	public static final BigDecimal IPI_ALIQUOTA = new BigDecimal(5D); // valor imaginário -> ver como definir esse valor
	public static final BigDecimal PIS_BASE = new BigDecimal(0.3D);
	public static final BigDecimal COFINS_BASE = new BigDecimal(0.2D);
	public static final BigDecimal IPI_BASE = new BigDecimal(0.1D);

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
			System.out.println(docFiscal.getPisBase());
			System.out.println(docFiscal.getCofinsBase());
			System.out.println(docFiscal.getPisValor());
			System.out.println(docFiscal.getCofinsValor());
		});
	}
}
