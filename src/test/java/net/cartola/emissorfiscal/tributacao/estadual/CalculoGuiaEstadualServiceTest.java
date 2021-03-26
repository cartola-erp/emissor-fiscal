package net.cartola.emissorfiscal.tributacao.estadual;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import net.cartola.emissorfiscal.documento.CompraDto;
import net.cartola.emissorfiscal.documento.DocumentoFiscal;
import net.cartola.emissorfiscal.documento.DocumentoFiscalItem;
import net.cartola.emissorfiscal.documento.ProdutoOrigem;
import net.cartola.emissorfiscal.engine.EmailEngine;
import net.cartola.emissorfiscal.engine.EmailModel;
import net.cartola.emissorfiscal.estado.EstadoSigla;
import net.cartola.emissorfiscal.loja.LojaService;
import net.cartola.emissorfiscal.ncm.Ncm;
import net.cartola.emissorfiscal.operacao.Operacao;
import net.cartola.emissorfiscal.pessoa.Pessoa;
import net.cartola.emissorfiscal.pessoa.PessoaEndereco;

/**
  * @date 10 de mar. de 2021
  * @author robson.costa
  */
//@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CalculoGuiaEstadualServiceTest {
	
	@Autowired
	private CalculoGuiaEstadualService calcGuiaEstaService;
	
	@Autowired
	private LojaService LojaService;
	
	@Autowired
	private EmailEngine emailEngine;
	
	private BigDecimal DOC_FISC_ITEM_UM_VALOR = new BigDecimal(10000);
	private BigDecimal DOC_FISC_ITEM_VALOR = new BigDecimal(100);
	private BigDecimal DOC_FISC_ITEM_QUANT = new BigDecimal(1);

	private static final Logger LOG = Logger.getLogger(CalculoGuiaEstadualServiceTest.class.getName()); 
	

	private DocumentoFiscal montaDocFiscParaCalcularGare() {
		DocumentoFiscal docFisc = new DocumentoFiscal();
		List<DocumentoFiscalItem> listItens =  new ArrayList<>();

		Pessoa emitente = new Pessoa();
		Pessoa destinatario =  new Pessoa();
		
		PessoaEndereco enderecoEmitente = new PessoaEndereco();
		enderecoEmitente.setUf(EstadoSigla.SC);
		
		PessoaEndereco enderecoDestinatario = new PessoaEndereco();
		enderecoDestinatario.setUf(EstadoSigla.SP);
		
		emitente.setEndereco(enderecoEmitente);
		destinatario.setEndereco(enderecoDestinatario);
		
		Operacao operacao = new Operacao();
		operacao.setId(33L);
		operacao.setDescricao("Compra InterEstadual para Comercializacao" );
		
		DocumentoFiscalItem docFiscItemUm = new DocumentoFiscalItem();
		Ncm ncmUm = new Ncm();
		ncmUm.setId(10002L);
		ncmUm.setNumero(87085099);
		
		docFiscItemUm.setValorUnitario(DOC_FISC_ITEM_UM_VALOR);
		docFiscItemUm.setQuantidade(DOC_FISC_ITEM_QUANT);
		docFiscItemUm.setNcm(ncmUm);
		docFiscItemUm.setOrigem(ProdutoOrigem.NACIONAL);
		
		DocumentoFiscalItem docFiscItemDois = new DocumentoFiscalItem();
		docFiscItemDois.setValorUnitario(DOC_FISC_ITEM_VALOR);
		docFiscItemDois.setQuantidade(DOC_FISC_ITEM_QUANT);
		docFiscItemDois.setNcm(ncmUm);
		docFiscItemDois.setOrigem(ProdutoOrigem.NACIONAL_CONTEUDO_IMPORTADO_MAIOR_40);
		
		Ncm ncmTres = new Ncm();
		ncmTres.setId(9992L);
		ncmTres.setNumero(87083090);
		
		DocumentoFiscalItem docFiscItemTres = new DocumentoFiscalItem();
		docFiscItemTres.setValorUnitario(DOC_FISC_ITEM_VALOR);
		docFiscItemTres.setQuantidade(DOC_FISC_ITEM_QUANT);
		docFiscItemTres.setNcm(ncmTres);
		docFiscItemTres.setOrigem(ProdutoOrigem.NACIONAL);
		
		DocumentoFiscalItem docFiscItemQuatro = new DocumentoFiscalItem();
		docFiscItemQuatro.setValorUnitario(DOC_FISC_ITEM_VALOR);
		docFiscItemQuatro.setQuantidade(DOC_FISC_ITEM_QUANT);
		docFiscItemQuatro.setNcm(ncmTres);
		docFiscItemQuatro.setOrigem(ProdutoOrigem.NACIONAL_CONTEUDO_IMPORTADO_MAIOR_40);
		
		listItens.add(docFiscItemUm);
		listItens.add(docFiscItemDois);
		listItens.add(docFiscItemTres);
		listItens.add(docFiscItemQuatro);
		
		docFisc.setOperacao(operacao);
		docFisc.setEmitente(emitente);
		docFisc.setDestinatario(destinatario);
		docFisc.setItens(listItens);
		docFisc.setNumeroNota(1234L);
//		docFisc.setEmitente(emitente);
		docFisc.setNfeChaveAcesso("31210304156194000412550010004160171100808200");
		return docFisc;
	}

	
	@Test
	public void test01_DeveCalcularGuiaGareCompraSc() {
		LOG.log(Level.INFO, "Calculando GUIA GARE de SC ");
		DocumentoFiscal docFisc = new DocumentoFiscal();
		
		docFisc = montaDocFiscParaCalcularGare();
		
		CompraDto compraDto = calcGuiaEstaService.calculaGuiaGareIcmsStEntrada(docFisc);
		LojaService.findOne(1L).ifPresent(loja -> {
			compraDto.getTotalCalcGareCompra().setLoja(loja);
		});
//		String htmlEmail = calcGuiaEstaService.gerarEmail(compraDto);
//		
//		
//		Mail emailSendGrid = calcGuiaEstaService.getMailSendgridWithContent(htmlEmail);
//		emailEngine.enviarEmail(emailSendGrid);
		calcGuiaEstaService.enviarEmail(compraDto);

	}



}
