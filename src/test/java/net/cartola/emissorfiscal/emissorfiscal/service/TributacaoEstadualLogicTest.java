package net.cartola.emissorfiscal.emissorfiscal.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
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
import net.cartola.emissorfiscal.documento.DocumentoFiscalRepository;
import net.cartola.emissorfiscal.documento.DocumentoFiscalService;
import net.cartola.emissorfiscal.estado.Estado;
import net.cartola.emissorfiscal.estado.EstadoService;
import net.cartola.emissorfiscal.estado.EstadoSigla;
import net.cartola.emissorfiscal.ncm.Ncm;
import net.cartola.emissorfiscal.ncm.NcmService;
import net.cartola.emissorfiscal.operacao.Operacao;
import net.cartola.emissorfiscal.operacao.OperacaoService;
import net.cartola.emissorfiscal.tributacao.estadual.TributacaoEstadual;
import net.cartola.emissorfiscal.tributacao.federal.TributacaoFederal;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TributacaoEstadualLogicTest {
	
	@Autowired
	private EstadoService estadoService;
	
	@Autowired
	private OperacaoService operacaoService;
	
	@Autowired
	private NcmService ncmService;
	
	@Autowired
	private DocumentoFiscalRepository docFiscalRepository;
	
	@Autowired
	private DocumentoFiscalService docFiscalService;
	
	@Autowired
	private TestHelper testHelper;
	
	// TRIBUTAÇÃO ESTADUAL (icms)
	public static int TRIBUTACAO_ESTADUAL_ICMS_CST = 00;
	public static BigDecimal TRIBUTACAO_ESTADUAL_ICMS_BASE = new BigDecimal(1);			// 1, pois é "TRIBUTADA INTEGRALMENTE". (100/100 = 1)
	public static BigDecimal TRIBUTACAO_ESTADUAL_ICMS_ALIQUOTA = new BigDecimal(18D);
	public static BigDecimal TRIBUTACAO_ESTADUAL_ICMS_IVA = new BigDecimal(2);
	public static BigDecimal TRIBUTACAO_ESTADUAL_ICMS_ALIQUOTA_DESTINO = new BigDecimal(18D);
	public static int TRIBUTACAO_ESTADUAL_ICMS_CEST = 100100;
	public static String TRIBUTACAO_ESTADUAL_ICMS_MENSAGEM = " Essa é uma mensagem fake";
	
	
	@Test
	public void test00_CleanUp() {
		testHelper.limpaBanco();
		testHelper.criarEstados();
		testHelper.criarDocumentoFiscal();
		testHelper.criarUsuarioRoot();
	}
	
	@Test 	// Venda ESTADUAL
	public void test01_DeveCalcularOIcmsDeSPparaSPVendaJuridicaParaFisica() {
		// Buscando o Estado Origem inserido previamente
		Optional<Estado> opEstadoOrigem = estadoService.findBySigla(EstadoSigla.SP);
		assertTrue(opEstadoOrigem.isPresent());
		
		List<Ncm> listNcms = new ArrayList<Ncm>();
		listNcms.addAll(ncmService.findAll());
		
		assertNotNull(listNcms);
		assertTrue(listNcms.size() == 3);
		
		Optional<Operacao> opOperacao = operacaoService.findOperacaoByDescricao(TestHelper.OPERACAO_VENDA);
		assertTrue(opOperacao.isPresent());
		
		Optional<DocumentoFiscal> opDocFiscal = docFiscalRepository.findDocumentoFiscalByEmitenteCnpjAndTipoAndSerieAndNumero(12345678901234L, "NFE", 262265758L, 82211429431055L);
		assertTrue(opDocFiscal.isPresent());
		
		// Criando TRIBUTAÇÃO ESTADUAL
		List<TributacaoEstadual> tributacoesEstaduais = testHelper.criarTribEstaPorNcmsEOperDentroDeSP(listNcms, opOperacao.get());
		assertTrue(tributacoesEstaduais.size() == 3);
		
		// Criando TRIBUTAÇÃO FEDERAL
		List<TributacaoFederal> tributacoesFederais = testHelper.criarTributacaoFederal(listNcms, opOperacao.get());
		assertTrue(tributacoesFederais.size() == 3);

		/* CALCULANDO O  ICMS do DOCUMENTO FISCAL DE ID =  1
		* PS provavelmente se tentar salvar um docFiscal, que NÃO tenha as TRIBUTACOES cadastradas previamente, dará erro
		* já que essas validações é feitas na API
		*/
		docFiscalService.save(opDocFiscal.get());
		
		// VERIFICANDO SE ESTÁ CERTO O CALCULO
		System.out.println("opDocFiscal.get().getIcmsBase() = " + opDocFiscal.get().getIcmsBase());
		System.out.println("opDocFiscal.get().getIcmsValor() = " + opDocFiscal.get().getIcmsValor());
//		
//		System.out.println("opDocFiscal.get().getItens().get(0).getIcmsBase() = " + opDocFiscal.get().getItens().get(0).getIcmsBase());
//		System.out.println("opDocFiscal.get().getItens().get(0).getIcmsValor() = " + opDocFiscal.get().getItens().get(0).getIcmsValor());
//		System.out.println("opDocFiscal.get().getItens().get(0).getIcmsAliquota() = " + opDocFiscal.get().getItens().get(0).getIcmsAliquota());
		
		System.out.println("\n"+ this.getClass().getName() + " test01_DeveCalcularOIcmsDeSPparaSP, Ok");
	}
	
	
	
}
