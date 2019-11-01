package net.cartola.emissorfiscal.emissorfiscal.service;


import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import net.cartola.emissorfiscal.documento.DocumentoFiscal;
import net.cartola.emissorfiscal.documento.DocumentoFiscalItem;
import net.cartola.emissorfiscal.documento.Finalidade;
import net.cartola.emissorfiscal.estado.Estado;
import net.cartola.emissorfiscal.ncm.Ncm;
import net.cartola.emissorfiscal.operacao.Operacao;
import net.cartola.emissorfiscal.tributacao.estadual.CalculoFiscalEstadual;
import net.cartola.emissorfiscal.tributacao.estadual.TributacaoEstadual;


@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TributacaoEstadualLogicTest {
	
	
	@Autowired
	CalculoFiscalEstadual calculoFiscalEstadual;

	Estado estadoOrigem = new Estado();
	Estado estadoDestino = new Estado();
	
	Ncm ncm = new Ncm();
	Operacao operacao = new Operacao();
	
	DocumentoFiscalItem item = new DocumentoFiscalItem();
	
	TributacaoEstadual icms = new TributacaoEstadual();
	
//	ESTADO ORIGEM
	private static Long ESTADO_ID = 1L;
	private static String ESTADO_SIGLA = "SP";
	private static String ESTADO_NOME = "São Paulo";
	
//	DESTINO
	
//	NCM
	private static long NCM_ID = 1L;
	private static int NCM_NUMERO = 1234;
	private static int NCM_EXCECAO = 4321;
	private static String NCM_DESCRICAO = "Essa é uma DESCRIÇÃO do NCM para o teste";
	
	
//	OPERAÇÃO
	private static long OPERACAO_ID = 1L;
	private static String OPERACAO_DESCRICAO = "Essa é uma DESCRIÇÃO da OPERAÇÃO para o teste";		
	
//	ITEM
	private static long ITEM_ID = 1L;
	private static Finalidade ITEM_FINALIDADE = Finalidade.CONSUMO;
	private static BigDecimal ITEM_QUANTIDADE = new BigDecimal(2);
	private static BigDecimal ITEM_VALOR_UNITARIO = new BigDecimal(5);
	private static int ITEM_CFOP = 12345678;
	private static BigDecimal ITEM_ICMS_BASE = new BigDecimal(2);
	private static BigDecimal ITEM_ICMS_ALIQUOTA = new BigDecimal(5);
	private static BigDecimal ITEM_ICMS_VALOR = new BigDecimal(10);
	

	
	@Test
	public void test01_DeveCalcularOIcmsDeSPparaSP() {
//		Estado estadoOrigem = new Estado();
		estadoOrigem.setId(ESTADO_ID);
		estadoOrigem.setSigla(ESTADO_SIGLA);
		estadoOrigem.setNome(ESTADO_NOME);
		
//		Estado estadoDestino = new Estado();
		estadoDestino.setId(ESTADO_ID);
		estadoDestino.setSigla(ESTADO_SIGLA);
		estadoDestino.setNome(ESTADO_NOME);
		
//		Ncm ncm = new Ncm();
		ncm.setId(NCM_ID);
		ncm.setNumero(NCM_NUMERO);
		ncm.setExcecao(NCM_EXCECAO);
		ncm.setDescricao(NCM_DESCRICAO);
		
//		Operacao operacao = new Operacao();
		operacao.setId(OPERACAO_ID);
		operacao.setDescricao(OPERACAO_DESCRICAO);
		
//		DocumentoFiscalItem item = new DocumentoFiscalItem();
		item.setId(ITEM_ID);
		item.setFinalidade(ITEM_FINALIDADE);
		item.setQuantidade(ITEM_QUANTIDADE);
		item.setValorUnitario(ITEM_VALOR_UNITARIO);
		item.setCfop(ITEM_CFOP);
		item.setIcmsBase(ITEM_ICMS_BASE);
		item.setIcmsAliquota(ITEM_ICMS_ALIQUOTA);
		item.setIcmsValor(ITEM_ICMS_VALOR);
		
		TributacaoEstadual icms = new TributacaoEstadual();
		icms.setEstadoOrigem(estadoOrigem);
		icms.setEstadoDestino(estadoDestino);
		icms.setOperacao(operacao);
		icms.setNcm(ncm);
//		icms.
		
		
		System.out.println("Teste executado: " );
		
		DocumentoFiscal doc = new DocumentoFiscal();
		doc.setDestinatarioUf("UF");
		doc.setEmitenteUf("UF");
		
		DocumentoFiscalItem item = new DocumentoFiscalItem();
		item.setNcm(ncm);
		item.setValorUnitario(new BigDecimal(100));
		item.setQuantidade(new BigDecimal(2));
		
		List<DocumentoFiscalItem> itens = Arrays.asList(item);
		doc.setItens(itens);
		
		Map<String, String> calculo = calculoFiscalEstadual.calculaImposto(doc);
		System.out.println(calculo);
	}
}
