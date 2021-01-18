package net.cartola.emissorfiscal.controller;

import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.google.gson.Gson;

import net.cartola.emissorfiscal.documento.DocumentoFiscal;
import net.cartola.emissorfiscal.documento.DocumentoFiscalItem;
import net.cartola.emissorfiscal.emissorfiscal.service.NcmServiceLogicTest;
import net.cartola.emissorfiscal.emissorfiscal.service.TestHelper;
import net.cartola.emissorfiscal.estado.EstadoSigla;
import net.cartola.emissorfiscal.ncm.Ncm;
import net.cartola.emissorfiscal.operacao.Operacao;
import net.cartola.emissorfiscal.pessoa.Pessoa;
import net.cartola.emissorfiscal.pessoa.PessoaEndereco;
import net.cartola.emissorfiscal.pessoa.PessoaTipo;
import net.cartola.emissorfiscal.pessoa.RegimeTributario;

/**
 * 29 de jan de 2020
 * @author robson.costa
 */
public class DocumentoEmissaoControllerTest {
	
	private static final String DOCUMENTO_FISCAL_TIPO = "NFE";
	private static final Long DOCUMENTO_FISCAL_SERIE = 1L;
	private static final Long DOCUMENTO_FISCAL_NUMERO = 30L;
	
	
	private static final BigDecimal DOCUMENTO_FISCAL_ITEM_QUANTIDADE = new BigDecimal(5);
	private static final BigDecimal DOCUMENTO_FISCAL_ITEM_VALOR_UNITARIO = new BigDecimal(12);
	
	private static final int DOCUMENTO_FISCAL_ITEM_CFOP = 5655;
	private static final BigDecimal DOCUMENTO_FISCAL_ITEM_ICMS_CEST = new BigDecimal(100010);
	
//	@Autowired
//	private CalculoFiscalEstadual calcFiscalEstadual;
	
	// Isso é o que o EMISSOR-FISCAL, irá receber de informação do ERPJ
	@Test
	public void testSerializacaoGsonVenda() {
		DocumentoFiscal docFiscal = new DocumentoFiscal();
		Operacao operacao = new Operacao();
		Pessoa emitente = new Pessoa();
		PessoaEndereco enderecoEmitente = new PessoaEndereco();
		Pessoa destinatario = new Pessoa();
		PessoaEndereco enderecoDestinatario = new PessoaEndereco();

		Ncm ncm = new Ncm();
		List<DocumentoFiscalItem> listItens = new ArrayList<>();
		DocumentoFiscalItem item = new DocumentoFiscalItem(); 

		operacao.setDescricao(TestHelper.OPERACAO_VENDA);
		
		// EMITENTE
		emitente.setCnpj(Long.parseLong(TestHelper.PESSOA_EMITENTE_CNPJ));
//		emitente.setUf(EstadoSigla.SP);
		emitente.setRegimeTributario(RegimeTributario.NORMAL);
		emitente.setPessoaTipo(PessoaTipo.JURIDICA);
		
		enderecoEmitente.setUf(EstadoSigla.SP);
		emitente.setEndereco(enderecoEmitente);
		// DESTINATARIO
		destinatario.setCnpj(Long.parseLong(TestHelper.PESSOA_DEST_CNPJ_SP));
//		destinatario.setUf(EstadoSigla.SP);
		destinatario.setRegimeTributario(RegimeTributario.NORMAL);
		destinatario.setPessoaTipo(PessoaTipo.FISICA);
		
		enderecoDestinatario.setUf(EstadoSigla.SP);
		destinatario.setEndereco(enderecoDestinatario);
		
		// NCM
		ncm.setNumero(NcmServiceLogicTest.NCM_NUMERO_REGISTRO_1);
		ncm.setExcecao(NcmServiceLogicTest.NCM_EXCECAO_REGISTRO_1);
		ncm.setDescricao(NcmServiceLogicTest.NCM_DESCRICAO_REGISTRO_1);
		
		// ITEM
		item.setQuantidade(DOCUMENTO_FISCAL_ITEM_QUANTIDADE);
		item.setValorUnitario(DOCUMENTO_FISCAL_ITEM_VALOR_UNITARIO);
		item.setNcm(ncm);
//		item.setCfop(DOCUMENTO_FISCAL_ITEM_CFOP);
		listItens.add(item);
		
		// Documento Fiscal
		docFiscal.setOperacao(operacao);
		docFiscal.setTipo(DOCUMENTO_FISCAL_TIPO);
		docFiscal.setSerie(DOCUMENTO_FISCAL_SERIE);
		docFiscal.setNumero(DOCUMENTO_FISCAL_NUMERO);
		docFiscal.setEmitente(emitente);
		docFiscal.setDestinatario(destinatario);
		docFiscal.setItens(listItens);
		
//		calcFiscalEstadual.calculaImposto(docFiscal);
		
		Gson gson = new Gson();
		String gsonDocFiscal = gson.toJson(docFiscal);
		assertNotNull(gsonDocFiscal);
		
		System.out.println(gsonDocFiscal);
	}
	
	
	
}

