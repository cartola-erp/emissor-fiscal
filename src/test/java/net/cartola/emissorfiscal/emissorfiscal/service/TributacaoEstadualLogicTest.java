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
	private DocumentoFiscalRepository docFiscalRepository;
	
	@Autowired
	private TestHelper testHelper;

	
	// TRIBUTAÇÃO ESTADUAL (icms)
	public static int TRIBUTACAO_ESTADUAL_ICMS_CST = 00;
	public static BigDecimal TRIBUTACAO_ESTADUAL_ICMS_BASE = new BigDecimal(0.09);
	public static BigDecimal TRIBUTACAO_ESTADUAL_ICMS_ALIQUOTA = new BigDecimal(0.12D);
	public static BigDecimal TRIBUTACAO_ESTADUAL_ICMS_IVA = new BigDecimal(2);
	public static BigDecimal TRIBUTACAO_ESTADUAL_ICMS_ALIQUOTA_DESTINO = new BigDecimal(0.18D);
	public static int TRIBUTACAO_ESTADUAL_ICMS_CEST = 100100;
	public static String TRIBUTACAO_ESTADUAL_ICMS_MENSAGEM = " Essa é uma mensagem fake";
	
	
	@Test
	public void test00_CleanUp() {
		testHelper.limpaBanco();
		testHelper.criarEstados();
		testHelper.criarDocumentoFiscal();
	}
	
	
	// Venda ESTADUAL
	@Test
	public void test01_DeveCalcularOIcmsDeSPparaSP() {
		// Buscando o Estado Origem inserido previamente
		Optional<Estado> opEstadoOrigem = estadoService.findBySigla(EstadoSigla.SP);
		assertTrue(opEstadoOrigem.isPresent());
		
		List<Ncm> listNcms = new ArrayList<Ncm>();
		listNcms.addAll(ncmService.findAll());
		
		assertNotNull(listNcms);
		assertTrue(listNcms.size() == 3);
		
		Optional<Operacao> opOperacao = operacaoService.findOperacaoByDescricao(TestHelper.OPERACAO_VENDA);
		assertTrue(opOperacao.isPresent());
		
		Optional<DocumentoFiscal> opDocFiscal = docFiscalRepository.findById(1L);
		assertTrue(opDocFiscal.isPresent());
		
		List<TributacaoEstadual> listTributacoes = new ArrayList<>();
		listNcms.stream().forEach(ncm -> {
			TributacaoEstadual icms = new TributacaoEstadual();
			icms.setEstadoOrigem(opEstadoOrigem.get());
			icms.setEstadoDestino(opEstadoOrigem.get());
			icms.setOperacao(opOperacao.get());
			icms.setNcm(ncm);
			icms.setIcmsCst(TRIBUTACAO_ESTADUAL_ICMS_CST);
			icms.setIcmsBase(TRIBUTACAO_ESTADUAL_ICMS_BASE);
			icms.setIcmsAliquota(TRIBUTACAO_ESTADUAL_ICMS_ALIQUOTA);
			icms.setIcmsIva(TRIBUTACAO_ESTADUAL_ICMS_IVA);
			icms.setIcmsAliquotaDestino(TRIBUTACAO_ESTADUAL_ICMS_ALIQUOTA_DESTINO);
			icms.setCest(TRIBUTACAO_ESTADUAL_ICMS_CEST);
			icms.setMensagem(TRIBUTACAO_ESTADUAL_ICMS_MENSAGEM);
			listTributacoes.add(icms);
		});
		
		List<TributacaoEstadual> listIcms = icmsService.saveAll(listTributacoes);
		assertTrue(listIcms.size() == 3);
		
//		System.out.println("|ANTES DO CALCULO| doc.getIcmsValor() "+ doc.getIcmsValor());
		
		calculoFiscalEstadual.calculaImposto(opDocFiscal.get());
		
		System.out.println("opDocFiscal.get().getIcmsBase() = " + opDocFiscal.get().getIcmsBase());
		System.out.println("opDocFiscal.get().getIcmsValor() = " + opDocFiscal.get().getIcmsValor());
//		
		System.out.println("opDocFiscal.get().getItens().get(0).getIcmsBase() = " + opDocFiscal.get().getItens().get(0).getIcmsBase());
		System.out.println("opDocFiscal.get().getItens().get(0).getIcmsValor() = " + opDocFiscal.get().getItens().get(0).getIcmsValor());
		System.out.println("opDocFiscal.get().getItens().get(0).getIcmsAliquota() = " + opDocFiscal.get().getItens().get(0).getIcmsAliquota());

		
		System.out.println(this.getClass().getName() + " test01_DeveCalcularOIcmsDeSPparaSP, Ok");

	}
	
	
	
}
