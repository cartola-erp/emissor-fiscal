package net.cartola.emissorfiscal.api.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.gson.Gson;

import net.cartola.emissorfiscal.documento.DocumentoFiscal;
import net.cartola.emissorfiscal.documento.DocumentoFiscalItem;
import net.cartola.emissorfiscal.emissorfiscal.service.NcmServiceLogicTest;
import net.cartola.emissorfiscal.emissorfiscal.service.TestHelper;
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
	private OperacaoService operacaoService;
	
	@Autowired
	private PessoaService pessoaService;

	AuthorizationTestHelper<Object> auth = new AuthorizationTestHelper<Object>();
	
	private static final BigDecimal DOCUMENTO_FISCAL_ITEM_QUANTIDADE = new BigDecimal(5);
	private static final BigDecimal DOCUMENTO_FISCAL_ITEM_VALOR_UNITARIO = new BigDecimal(12);
	
//	private static final int DOCUMENTO_FISCAL_ITEM_CFOP = 5655;
//	private static final BigDecimal DOCUMENTO_FISCAL_ITEM_ICMS_CEST = new BigDecimal(100010);
	
	private static final String PATH = "/api/v1/documento-fiscal";
	
	private Gson gson = new Gson();

	// ========= MÉTODO"auxiliar" =========
	private ResponseEntity<Response<DocumentoFiscal>> buscarUmDocumentoFiscal() {
		DocumentoFiscal docFiscal = new DocumentoFiscal();
		docFiscal.setEmitente(new Pessoa());
		docFiscal.getEmitente().setCnpj(TestHelper.PESSOA_EMITENTE_CNPJ);
		docFiscal.setTipo(TestHelper.DOC_FISCAL_TIPO_NFE);
		docFiscal.setSerie(Long.parseLong(TestHelper.DOC_FISCAL_SERIE_1));
		docFiscal.setNumero(Long.parseLong(TestHelper.DOC_FISCAL_NUMERO_1));
		
		ParameterizedTypeReference<Response<DocumentoFiscal>> tipoRetorno = new ParameterizedTypeReference<Response<DocumentoFiscal>>() {
		};
		HttpEntity<Object> httpEntity = auth.autorizar(docFiscal, restTemplate);
		
		ResponseEntity<Response<DocumentoFiscal>> response = restTemplate.exchange(PATH + "/buscar", HttpMethod.POST, httpEntity, tipoRetorno);
		String gsonResponse = gson.toJson(response);
		System.out.println("\n\nRESPONSE JSON BUSCA: " +gsonResponse);
		
		return response;
	}
	
	@Test
	public void test00_CleanUp() {
		testHelper.limpaBanco();
		testHelper.criarEstados();
		testHelper.criarPessoa();
		testHelper.criarOperacoes();
		testHelper.criarNcms();
		testHelper.criarUsuarioRoot();
	}
	
	@Test
	public void test01_espera404NotFoundAoBuscarUmDocFiscalInexistente() {
		ResponseEntity<Response<DocumentoFiscal>> response = buscarUmDocumentoFiscal();
		assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
		System.out.println("\n" +this.getClass() + " test01_espera404NotFoundAoBuscarUmDocFiscalInexistente, ok");
	}
	
	@Test
	public void test02_tentaInserirDocumentoFiscalVendaInterEstadual() {
		// POPULANDO OBJ
		Operacao operacao = operacaoService.findOperacaoByDescricao(TestHelper.OPERACAO_VENDA_INTERESTADUAL).get();
		Pessoa emitente = pessoaService.findByCnpj(TestHelper.PESSOA_EMITENTE_CNPJ).get();
		Pessoa destinatario = pessoaService.findByCnpj(TestHelper.PESSOA_DEST_CNPJ_SP).get();
		Ncm ncm = ncmService.findNcmByNumeroAndExcecao(NcmServiceLogicTest.NCM_NUMERO_REGISTRO_1, NcmServiceLogicTest.NCM_EXCECAO_REGISTRO_1).get();
		
		List<Ncm> listNcms = ncmService.findAll();
		// CRIANDO TRIBUTACOES
		testHelper.criarTribEstaPorNcmsEOperDentroDeSP(listNcms, operacao);
		testHelper.criarTributacaoFederal(listNcms, operacao);
		
		// ITEM
		List<DocumentoFiscalItem> listItens = new ArrayList<>();
		DocumentoFiscalItem item = new DocumentoFiscalItem(); 
		item.setQuantidade(DOCUMENTO_FISCAL_ITEM_QUANTIDADE);
		item.setValorUnitario(DOCUMENTO_FISCAL_ITEM_VALOR_UNITARIO);
		item.setNcm(ncm);
		listItens.add(item);
		
		// DOCUMENTO FISCAL
		DocumentoFiscal docFiscal = new DocumentoFiscal();
		docFiscal.setOperacao(operacao);
		docFiscal.setTipo(TestHelper.DOC_FISCAL_TIPO_NFE);
		docFiscal.setSerie(Long.parseLong(TestHelper.DOC_FISCAL_SERIE_1));
		docFiscal.setNumero(Long.parseLong(TestHelper.DOC_FISCAL_NUMERO_1));
		docFiscal.setEmitente(emitente);
		docFiscal.setDestinatario(destinatario);
		docFiscal.setItens(listItens);

		HttpEntity<Object> httpEntity = auth.autorizar(docFiscal, restTemplate);
		ParameterizedTypeReference<Response<DocumentoFiscal>> tipoRetorno = new ParameterizedTypeReference<Response<DocumentoFiscal>>() {
		};
		ResponseEntity<Response<DocumentoFiscal>> response = restTemplate.exchange(PATH, HttpMethod.POST,httpEntity, tipoRetorno);

		String gsonResponse = gson.toJson(response);
		System.out.println("\n\nRESPONSE JSON SAVE: " +gsonResponse);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		docFiscal = response.getBody().getData();
		assertNotNull(docFiscal);
		
		System.out.println("\n" +this.getClass() + " test02_tentaInserirDocumentoFiscalVendaInterEstadual, ok");
	}
	
	@Test
	public void test03_buscarUmDocumentoFiscal() {
		ResponseEntity<Response<DocumentoFiscal>> response = buscarUmDocumentoFiscal();
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		DocumentoFiscal docFiscal = response.getBody().getData();
		assertNotNull(docFiscal);
		assertEquals(docFiscal.getEmitente().getCnpj().toString(), TestHelper.PESSOA_EMITENTE_CNPJ);
		assertTrue(response.getHeaders().getContentType().equals(MediaType.APPLICATION_JSON));
		
		System.out.println("\n" +this.getClass() + " test03_buscarUmDocumentoFiscal, ok");
	}
	
	@Test
	public void test04_tentaEditarDocFiscalVendaInterEstadualParaVenda() {
		ResponseEntity<Response<DocumentoFiscal>> response = buscarUmDocumentoFiscal();
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		DocumentoFiscal docFiscal = response.getBody().getData();
		assertNotNull(docFiscal);
		assertEquals(docFiscal.getEmitente().getCnpj().toString(), TestHelper.PESSOA_EMITENTE_CNPJ);
		assertTrue(response.getHeaders().getContentType().equals(MediaType.APPLICATION_JSON));
		
		Operacao operacao = operacaoService.findOperacaoByDescricao(TestHelper.OPERACAO_VENDA).get();
		docFiscal.setOperacao(operacao);
		// CRIANDO TRIBUTACOES
		testHelper.criarTribEstaPorNcmsEOperDentroDeSP(ncmService.findAll(), operacao);
		testHelper.criarTributacaoFederal(ncmService.findAll(), operacao);
		
		HttpEntity<Object> httpEntity = auth.autorizar(docFiscal, restTemplate);
		ParameterizedTypeReference<Response<DocumentoFiscal>> tipoRetorno = new ParameterizedTypeReference<Response<DocumentoFiscal>>() {
		};
		ResponseEntity<Response<DocumentoFiscal>> responsePut = restTemplate.exchange(PATH, HttpMethod.PUT, httpEntity, tipoRetorno);
		
		String gsonResponsePut = gson.toJson(responsePut);
		System.out.println("\n\nRESPONSE JSON EDITAR: " +gsonResponsePut);
		
		assertEquals(HttpStatus.OK, responsePut.getStatusCode());
		docFiscal = responsePut.getBody().getData();
		assertNotNull(docFiscal);
		assertEquals(TestHelper.OPERACAO_VENDA, responsePut.getBody().getData().getOperacao().getDescricao());
		
		System.out.println("\n" +this.getClass() + " test04_tentaEditarDocFiscalVendaParaVendaInterEstadual, ok");
	}
	
	

}

