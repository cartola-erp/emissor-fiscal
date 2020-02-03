package net.cartola.emissorfiscal.emissorfiscal.api.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import net.cartola.emissorfiscal.documento.DocumentoFiscal;
import net.cartola.emissorfiscal.documento.DocumentoFiscalItem;
import net.cartola.emissorfiscal.emissorfiscal.service.NcmServiceLogicTest;
import net.cartola.emissorfiscal.emissorfiscal.service.TestHelper;
import net.cartola.emissorfiscal.estado.Estado;
import net.cartola.emissorfiscal.estado.EstadoService;
import net.cartola.emissorfiscal.estado.EstadoSigla;
import net.cartola.emissorfiscal.ncm.Ncm;
import net.cartola.emissorfiscal.ncm.NcmService;
import net.cartola.emissorfiscal.operacao.Operacao;
import net.cartola.emissorfiscal.operacao.OperacaoService;
import net.cartola.emissorfiscal.pessoa.Pessoa;
import net.cartola.emissorfiscal.pessoa.PessoaService;
import net.cartola.emissorfiscal.response.Response;

/**
 * 3 de fev de 2020
 * @author robson.costa
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DocumentoFiscalAPIControllerTest {
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private TestHelper testHelper;
	
	@Autowired
	private NcmService ncmService;
	
	@Autowired
	private EstadoService estadoService;
	
	@Autowired 
	private OperacaoService operacaoService;
	
	@Autowired
	private PessoaService pessoaService;
	
	
	private static final String DOCUMENTO_FISCAL_TIPO = "NFE";
	private static final Long DOCUMENTO_FISCAL_SERIE = 1L;
	private static final Long DOCUMENTO_FISCAL_NUMERO = 30L;
	
	
	private static final BigDecimal DOCUMENTO_FISCAL_ITEM_QUANTIDADE = new BigDecimal(5);
	private static final BigDecimal DOCUMENTO_FISCAL_ITEM_VALOR_UNITARIO = new BigDecimal(12);
	
	private static final int DOCUMENTO_FISCAL_ITEM_CFOP = 5655;
	private static final BigDecimal DOCUMENTO_FISCAL_ITEM_ICMS_CEST = new BigDecimal(100010);
	
	@Test
	public void test00_CleanUp() {
		testHelper.limpaBanco();
		testHelper.criarEstados();
		testHelper.criarPessoa();
		testHelper.defineOperacoes();
		testHelper.defineNcms();
	}
	
	@Test
	public void test01_insereDocumentoFiscalVendaInterEstadual() {
		// popula obj
		DocumentoFiscal docFiscal = new DocumentoFiscal();
		Operacao operacao = operacaoService.findOperacaoByDescricao(TestHelper.OPERACAO_VENDA).get();
		Pessoa emitente = pessoaService.findByCnpj(Long.parseLong(TestHelper.PESSOA_CNPJ)).get(0);
		Pessoa destinatario = pessoaService.findByCnpj(Long.parseLong(TestHelper.PESSOA_CNPJ_2)).get(0);
		Ncm ncm = new Ncm();
		
		List<DocumentoFiscalItem> listItens = new ArrayList<>();
		DocumentoFiscalItem item = new DocumentoFiscalItem(); 

//		 NCM
		ncm = ncmService.findNcmByNumeroAndExcecao(NcmServiceLogicTest.NCM_NUMERO_REGISTRO_1, NcmServiceLogicTest.NCM_EXCECAO_REGISTRO_1).get();
//		ncm.setNumero(NcmServiceLogicTest.NCM_NUMERO_REGISTRO_1);
//		ncm.setExcecao(NcmServiceLogicTest.NCM_EXCECAO_REGISTRO_1);
//		ncm.setDescricao(NcmServiceLogicTest.NCM_DESCRICAO_REGISTRO_1);
		
		// ITEM
		item.setQuantidade(DOCUMENTO_FISCAL_ITEM_QUANTIDADE);
		item.setValorUnitario(DOCUMENTO_FISCAL_ITEM_VALOR_UNITARIO);
		item.setNcm(ncm);
		listItens.add(item);
		
		// Documento Fiscal
		docFiscal.setOperacao(operacao);
		docFiscal.setTipo(DOCUMENTO_FISCAL_TIPO);
		docFiscal.setSerie(DOCUMENTO_FISCAL_SERIE);
		docFiscal.setNumero(DOCUMENTO_FISCAL_NUMERO);
		docFiscal.setEmitente(emitente);
		docFiscal.setDestinatario(destinatario);
		docFiscal.setItens(listItens);
		
		// CRIANDO TRIBUTACOES
		List<Ncm> listNcms = ncmService.findAll();
		Estado estadoOrigem = estadoService.findBySigla(EstadoSigla.SP).get();
		
		testHelper.criarTributacoesEstaduaisDentroDeSP(listNcms, estadoOrigem, operacao);
		testHelper.criaTributacaoFederal(listNcms, operacao);

		HttpEntity<DocumentoFiscal> httpEntity = new HttpEntity<DocumentoFiscal>(docFiscal);

//		HttpEntity<Object> httpEntity
		ParameterizedTypeReference<Response<DocumentoFiscal>> tipoRetorno = new ParameterizedTypeReference<Response<DocumentoFiscal>>() {
		};
		ResponseEntity<Response<DocumentoFiscal>> response = restTemplate.exchange("/api/v1/documento-fiscal", HttpMethod.POST,httpEntity, tipoRetorno);

		System.err.println("ERRORS: " +response.getBody().getErrors());
		System.out.println("BODY: " +response.getBody());
		System.out.println("DATA: " +response.getBody().getData());
		
		docFiscal = response.getBody().getData();
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(docFiscal);
		
		System.out.println("\n" +this.getClass() + "test01_insereDocumentoFiscalVendaInterEstadual, ok");
	
	}
	
}

