package net.cartola.emissorfiscal.emissao;

import autogeral.emissorfiscal.vo.*;
import com.fasterxml.jackson.core.JsonProcessingException;
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

    @Test
    public void testaUmaNotaTotalmentePreenchida() throws JsonProcessingException {
        InvoiceModel nota = new InvoiceModel();
        List<ItemModel> itensList = new ArrayList<>();
        List<PaymentModel> payments = new ArrayList<>();

        /* EMITENTE NOTA */
        EmitModel emit = new EmitModel();
        emit.setInscricaoEstadual("387034155115");
        emit.setNomeEmitente("Emiente Teste");
        emit.setCpnjEmitente("05437537000137");
        emit.setLogradouroEmitente("Casa da mae Joana");
        emit.setNumeroLogradouroEmitente("777");
        emit.setComplementoEdenrecoEmitente("Rua abandonada");
        emit.setBairroEmitente("Bairro da casa do chapeu");
        emit.setMunicipioEmitente("Itu");
        emit.setUfEmitente("SP"); // Na nota tem q ser o codigo do estado
        emit.setCepEmitente("13315000");
        emit.setCodigoPaisEmitente("55");
        emit.setCodIbgeMunicipioEmitente("3523909");
        nota.setUfOrigem(emit.getUfEmitente());
        nota.setEmitente(emit);

        /* PAGAMENTO NOTA */
        PaymentModel pagamento = new PaymentModel();
        nota.setModoPagamento("1");
        pagamento.setPayBack(0);

        /* COMPRADOR */
        BuyerModel buyer = new BuyerModel();
        buyer.setId("1");
        buyer.setName("Comprador Teste Inexistente");
        buyer.setFederalTaxNumber(24840634000110l);
        AddressModel address = new AddressModel();
        address.setState("Sao Paulo");
        CityModel city = new CityModel();
        city.setCode("13315000");
        address.setCity(city);
        address.setDistrict("Rua imaginaria");
        address.setNumber("90");
        address.setCountry("Otaviano");
        address.setCodMunicipioIbge("3523909");
        address.setMunicipioNome("Itu");
        address.setAdditionalInformation("SP");
        address.setPostalCode("13315000");
        buyer.setAddress(address);

        /* ITEM NOTA */
        ItemModel item = new ItemModel();
        item.setCode(String.valueOf(1));
        item.setCodeGTIN("SEM GTIN");
        item.setDescription("Item teste");
        item.setNcm("1");
        item.setExTipi("0");
        if (item.getExTipi() == null) {
            item.setExTipi("0"); // Se nao tiver excessao eu vou definir como default 0
        }
        item.setUnit("1");
        item.setQuantity(1.0);
        item.setUnitAmount(10.00);
        item.setDiscountAmount(0);
        item.setTotalAmount(item.getUnitAmount() * item.getQuantity() - item.getDiscountAmount());
        item.setAdditionalInformation("");
        itensList.add(item);

        /* NOTA PARTE DE CIMA */
        nota.setPayment(payments);
        nota.setSerie(1);
        nota.setNumber(10);
        nota.setOperationOn(LocalDateTime.parse("2024-11-25T18:41:22.234300"));
        nota.setModeloNota("65");
        nota.setFinalidadeNota("");
        nota.setOperationNature("Venda");
        nota.setOperationType("1");
        //nota.setConsumerType();
        nota.setBuyer(buyer);
        nota.setItems(itensList);
        nota.setEmitente(emit);

        emissaoPrenchimentoDadosFiscaisService.preencherDadosFiscais(nota);
    }
}
