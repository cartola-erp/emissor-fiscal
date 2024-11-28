package net.cartola.emissorfiscal.emissao;

import autogeral.emissorfiscal.vo.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestPreenchimentoDadosFiscaisAndXmlNotaNfceAndEmissaoEmHomologacao {

    @Autowired
    private EmissaoCriacaoXmlService emissaoCriacaoXmlService;

    @Autowired
    private EmissaoPrenchimentoDadosFiscaisService emissaoPrenchimentoDadosFiscaisService;

    @Test
    public void testaPreenchimentoDadosFiscaisExmlDaNotaEaEmissaoEmHomologacao() {
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
        nota.setCodUf("35");
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
        buyer.setName("NF-E EMITIDA EM AMBIENTE DE HOMOLOGACAO - SEM VALOR FISCAL");
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
        item.setDescription("NOTA FISCAL EMITIDA EM AMBIENTE DE HOMOLOGACAO - SEM VALOR FISCAL");
        item.setNcm("27101932");

        if (item.getExTipi() == null) {
            item.setExTipi("0"); // Se nao tiver excessao eu vou definir como default 0
        }
        FuelDetailModel c = new FuelDetailModel();
        c.setCodeANP("620101008");
        c.setDescriptionANP("Oleo lubrificante");

        item.setFuelDetail(c);

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
        nota.setOperationOn(LocalDateTime.now());
        nota.setModeloNota("65");
        nota.setFinalidadeNota("");
        nota.setOperationNature("Venda");
        nota.setOperationType("1");
        //nota.setConsumerType();
        nota.setBuyer(buyer);
        nota.setItems(itensList);
        nota.setEmitente(emit);

        emissaoPrenchimentoDadosFiscaisService.preencherDadosFiscais(nota);
        emissaoCriacaoXmlService.montaXmlNota(nota);

    }
}