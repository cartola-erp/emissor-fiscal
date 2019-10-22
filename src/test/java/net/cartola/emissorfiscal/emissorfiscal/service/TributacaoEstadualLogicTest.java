package net.cartola.emissorfiscal.emissorfiscal.service;


import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.print.Doc;

import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import net.cartola.emissorfiscal.documento.DocumentoFiscal;
import net.cartola.emissorfiscal.documento.DocumentoFiscalItem;
import net.cartola.emissorfiscal.estado.Estado;
import net.cartola.emissorfiscal.ncm.Ncm;
import net.cartola.emissorfiscal.tributacao.estadual.CalculoFiscalEstadual;
import net.cartola.emissorfiscal.tributacao.estadual.TributacaoEstadual;


@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TributacaoEstadualLogicTest {
	
	Estado estadoOrigem = new Estado();
	Estado estadoDestino = new Estado();
	
	@Autowired
	CalculoFiscalEstadual calculoFiscalEstadual;

	
	
	@Test
	public void test01_DeveCalcularOIcmsDeSPparaSP() {
//		Estado estadoOrigem = new Estado();
		estadoOrigem.setId(1L);
		estadoOrigem.setSigla("SP");
		estadoOrigem.setNome("São Paulo");
		
//		Estado estadoDestino = new Estado();
		estadoDestino.setId(2L);
		estadoDestino.setSigla("SP");
		estadoDestino.setNome("São Paulo");
		
		Ncm ncm = new Ncm();
		ncm.setId(1L);
//		ncm.setExcecao(excecao);
		ncm.setNumero(31025011);
		
		
		TributacaoEstadual icms = new TributacaoEstadual();
		icms.setEstadoOrigem(estadoOrigem);
		icms.setEstadoDestino(estadoDestino);
		icms.setNcm(ncm);
		
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
