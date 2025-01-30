package net.cartola.emissorfiscal.emissao;

import br.com.autogeral.emissorfiscal.vo.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@ActiveProfiles("dev")
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestPreenchimentoDadosFiscais {

    @Autowired
    private EmissaoPrenchimentoDadosFiscaisService emissaoPrenchimentoDadosFiscaisService;

    @Autowired
    private EmissaoCriacaoXmlService emissaoCriacaoXmlService;

    @Test
    public void testaValidacoesDoPrenchimentoDasTributacoesNotaSemItem(){

        InvoiceModel nota = new InvoiceModel();
        ResultadoUtil resultado = emissaoPrenchimentoDadosFiscaisService.preencherDadosFiscais(nota);

        Map<String, Map<String, List<String>>> erros = resultado.getErros();
        assertFalse(erros.isEmpty());
        assertTrue(erros.containsKey("itensNotaEstaVazio"));
        assertTrue(erros.get("itensNotaEstaVazio").get("erro").contains("A nota esta sem itens cadastrados"));

        System.out.println("Erro encontrado: " + erros);
    }

    @Test
    public void testaValidacoesDoPrenchimentoDasTributacoesNotaComItemMasSemNcm(){

        InvoiceModel nota = new InvoiceModel();

        ItemModel item = new ItemModel();
        item.setDescription("Descricao do item sem ncm");
        List<ItemModel> itens = new ArrayList<>();

        item.setCode(String.valueOf(1));
        itens.add(item);
        nota.setItems(itens);

        ResultadoUtil resultado = emissaoPrenchimentoDadosFiscaisService.preencherDadosFiscais(nota);

        Map<String, Map<String, List<String>>> erros = resultado.getErros();
        assertFalse(erros.isEmpty());
        assertTrue(erros.containsKey("falha"));
        assertTrue(erros.get("falha").get("ItensInvalidosSemNcm").contains("Descricao do item sem ncm"));

        System.out.println("Erro encontrado: " + erros);
    }

    @Test
    public void testaValidacoesDoPrenchimentoDasTributacoesNotaComItemComNcmMasSemInformacoesNecessariasParaValidarATributacao(){

        InvoiceModel nota = new InvoiceModel();

        ItemModel item = new ItemModel();
        item.setDescription("Descricao do item sem ncm");
        item.setNcm("87082999");
        List<ItemModel> itens = new ArrayList<>();
        item.setCode(String.valueOf(1));
        itens.add(item);
        nota.setItems(itens);

        ResultadoUtil resultado = emissaoPrenchimentoDadosFiscaisService.preencherDadosFiscais(nota);

        Map<String, Map<String, List<String>>> erros = resultado.getErros();
        assertFalse(erros.isEmpty());
        assertTrue(erros.containsKey("ErroNoPreenchimentoDaTributacao"));
        assertTrue(erros.get("ErroNoPreenchimentoDaTributacao").get("IdDaOperacao").contains("Id da operação não pode estar nulo / vazio"));
        assertTrue(erros.get("ErroNoPreenchimentoDaTributacao").get("UfOrigem").contains("Uf de origem da nota não pode estar nulo / vazio"));;
        assertTrue(erros.get("ErroNoPreenchimentoDaTributacao").get("Cliente").contains("Os dados do cliente nao podem estar nulo / vazio"));;

        System.out.println("Erro encontrado: " + erros);
    }

    @Test
    public void testaValidacoesDoPrenchimentoDasTributacoesNotaComItemComNcmComInformacoesNecessariasParaValidarATributacaoMasComNcmNaoCadastradoNoEmissor(){

        InvoiceModel nota = new InvoiceModel();
        nota.setUfOrigem("SP");
        nota.setCodUf("35");
        nota.setOperationType("1");

        ItemModel item = new ItemModel();
        item.setDescription("Descricao do item sem ncm");
        item.setNcm("1");
        List<ItemModel> itens = new ArrayList<>();
        item.setCode(String.valueOf(1));
        itens.add(item);
        nota.setItems(itens);

        BuyerModel buyer = new BuyerModel();
        buyer.setName("Comprador Teste");
        nota.setBuyer(buyer);

        AddressModel address = new AddressModel();
        address.setAdditionalInformation("SP");
        buyer.setAddress(address);

        ResultadoUtil resultado = emissaoPrenchimentoDadosFiscaisService.preencherDadosFiscais(nota);

        Map<String, Map<String, List<String>>> erros = resultado.getErros();
        assertFalse(erros.isEmpty());
        assertTrue(erros.containsKey("ErroNoPreenchimentoDaTributacao"));
        assertTrue(erros.get("ErroNoPreenchimentoDaTributacao").get("ncmsSemTributacaoEstadual").contains("1"));
        assertTrue(erros.get("ErroNoPreenchimentoDaTributacao").get("ncmsSemTributacaoFederal").contains("1"));;

        System.out.println("Erro encontrado: " + erros);
    }

    @Test
    public void testaValidacoesDoPrenchimentoDasTributacoesNotaCom2itens1comNcmCadastradoNoEmissorOutroNao(){

        InvoiceModel nota = new InvoiceModel();
        List<ItemModel> itens = new ArrayList<>();

        nota.setUfOrigem("SP");
        nota.setOperationType("1");

        ItemModel item = new ItemModel();
        item.setDescription("Descricao do item sem ncm");
        item.setNcm("1");
        item.setCode(String.valueOf(1));
        itens.add(item);

        ItemModel item2 = new ItemModel();
        item2.setDescription("Descricao do item cadastrado");
        item2.setNcm("87082999");
        item2.setCode(String.valueOf(2));
        itens.add(item2);

        nota.setItems(itens);

        BuyerModel buyer = new BuyerModel();
        buyer.setName("Comprador Teste");
        nota.setBuyer(buyer);

        AddressModel address = new AddressModel();
        address.setAdditionalInformation("SP");
        buyer.setAddress(address);

        ResultadoUtil resultado = emissaoPrenchimentoDadosFiscaisService.preencherDadosFiscais(nota);

        Map<String, Map<String, List<String>>> erros = resultado.getErros();
        assertFalse(erros.isEmpty());
        assertTrue(erros.containsKey("ErroNoPreenchimentoDaTributacao"));
        assertTrue(erros.get("ErroNoPreenchimentoDaTributacao").get("ncmsSemTributacaoEstadual").contains("1"));
        assertTrue(erros.get("ErroNoPreenchimentoDaTributacao").get("ncmsSemTributacaoFederal").contains("1"));;

        System.out.println("Erro encontrado: " + erros);
    }

    @Test
    public void testaValidacoesDoPrenchimentoDasTributacoesNotaCom2itens1comNcmEoOutroNao(){

        InvoiceModel nota = new InvoiceModel();
        List<ItemModel> itens = new ArrayList<>();

        nota.setUfOrigem("SP");
        nota.setOperationType("1");

        ItemModel item = new ItemModel();
        item.setDescription("Descricao do item sem ncm");
        item.setNcm(null);
        item.setCode(String.valueOf(1));
        itens.add(item);

        ItemModel item2 = new ItemModel();
        item2.setDescription("Descricao do item cadastrado");
        item2.setNcm("87082999");
        item2.setCode(String.valueOf(2));
        itens.add(item2);

        nota.setItems(itens);

        BuyerModel buyer = new BuyerModel();
        buyer.setName("Comprador Teste");
        nota.setBuyer(buyer);

        AddressModel address = new AddressModel();
        address.setAdditionalInformation("SP");
        buyer.setAddress(address);

        ResultadoUtil resultado = emissaoPrenchimentoDadosFiscaisService.preencherDadosFiscais(nota);

        Map<String, Map<String, List<String>>> erros = resultado.getErros();
        assertFalse(erros.isEmpty());
        assertTrue(erros.containsKey("falha"));
        assertTrue(erros.get("falha").get("ItensInvalidosSemNcm").contains("Descricao do item sem ncm"));;

        //log
        System.out.println("Erro encontrado: " + erros);
    }
}
