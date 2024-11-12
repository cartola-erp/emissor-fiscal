package net.cartola.emissorfiscal.emissao;

import autogeral.emissorfiscal.vo.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.cartola.emissorfiscal.ncm.Ncm;
import net.cartola.emissorfiscal.ncm.NcmService;
import net.cartola.emissorfiscal.recalculo.RecalculoRepository;
import net.cartola.emissorfiscal.tributacao.estadual.TributacaoEstadual;
import net.cartola.emissorfiscal.tributacao.federal.TributacaoFederal;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.time.LocalDateTime;
import java.util.*;


import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("dev")
@RunWith(SpringRunner.class)
@SpringBootTest
public class EmissaoNfceTestPreenchimentoNotaAndTestValidacoesCampos {

    @Autowired
    EmissaoPrenchimentoDadosFiscaisService emissaoPrenchimentoDadosFiscaisService;

    @Autowired
    NcmService ncmService;

    @Autowired
    RecalculoRepository recalculoRepository;

    @Test
    public void testPreenchimentoDadosAndEmissaoNfceAndValidacaoAndGeracaoXmlAndGeracaoDanfe() throws JAXBException {

        List<PaymentsDetailsModel> detailsPayments = new ArrayList();
        List<PaymentModel> payments = new ArrayList();
        List<ItemModel> itensList = new ArrayList();
        List<DuplicateModel> duplicateList = new ArrayList<>();

        // Make payment model
        PaymentModel payment  = new PaymentModel();
        payment.setPaymentDetail(detailsPayments);
        payment.setPayBack(0);
        payments.add(payment);

        // Make details payment
        PaymentsDetailsModel detailsPayment = new PaymentsDetailsModel();
        detailsPayment.setMethod("A vista");
        detailsPayment.setMethodDescription("Dinheiro a vista");
        detailsPayment.setPaymentType("Em dinheiro");
        detailsPayment.setAmount(32.20);

        CardModel card = new CardModel(); // set card beelong a details payment || information for payment card
        card.setFederalTaxNumber("");
        card.setFlag("");
        card.setAuthorization("");
        card.setIntegrationPaymentType("");
        card.setFederalTaxNumberRecipient("");
        card.setIdPaymentTerminal("");

        detailsPayment.setCard(card);
        detailsPayment.setPaymentDate(LocalDateTime.of(2024, 11, 9, 9, 3, 58));
        detailsPayment.setFederalTaxNumberPag("63728890000172"); // Possivel cnpj do pagante
        detailsPayment.setStatePag("SP");
        detailsPayments.add(detailsPayment);

        // Make information for buyer
        BuyerModel buyer = new BuyerModel();
        buyer.setAccountId("10"); // id da conta do comprador
        buyer.setId("1");
        buyer.setName("Comprador Teste ");
        buyer.setFederalTaxNumber(1569621012);
        buyer.setEmail("comprador@teste.com");

        AddressModel address = new AddressModel(); // Vamos comecar setar o endereco do ocmprador
        address.setState("SP");

        CityModel city = new CityModel(); // Vamos comecar setar as informacoes de cidade
        city.setCode("3550308");
        city.setCode("Sao Paulo");
        address.setCity(city);

        address.setDistrict("Centro");
        address.setAdditionalInformation("Casa");
        address.setStreet("Av. otaviano");
        address.setNumber("1234");
        address.setPostalCode("01001000");
        address.setCountry("SP");
        address.setPhone("+5511999999999");
        buyer.setAddress(address);

        buyer.setType("Pessoa fisica");
        buyer.setStateTaxNumberIndicator("Nao contribuente");
        buyer.setTradeName("Cliente teste");
        buyer.setTaxRegime(false);
        buyer.setStateTaxNumber("");

        // Make a transport
        TransportModel transport = new TransportModel();
        transport.setFreightModality("");

        TransportGroupModel groupTransport = new TransportGroupModel(); // Information for transport group
        groupTransport.setAccountId("");
        groupTransport.setId("");
        groupTransport.setName("");
        groupTransport.getFederalTaxNumber();
        groupTransport.setEmail("");
        groupTransport.setAddress(address);
        groupTransport.setTransportRetention("");
        groupTransport.setStateTaxNumber("");
        groupTransport.setTransportRetention("");

        transport.setTransportGroup(groupTransport);


        ReboqueModel reboque  = new ReboqueModel(); // Make a transport reboque  information
        reboque.setPlate("");
        reboque.setUf("");
        reboque.setRntc("");
        reboque.setWagon("");
        reboque.setFerry("");

        transport.setReboque(reboque);

        VolumeModel volume = new VolumeModel(); // make a volume for transport
        volume.setVolumeQuantity(0);
        volume.setSpecies("");
        volume.setBrand("");
        volume.setVolumeNumeration("");
        volume.setNetWeight(0);
        volume.setGrossWeight(0);
        transport.setVolume(volume);

        TransportVehicleModel transportVehicle = new TransportVehicleModel();
        transportVehicle.setPlate("");
        transportVehicle.setState("");
        transportVehicle.setRntc("");
        transport.setTransportVehicle(transportVehicle);

        transport.setSealNumber("");
        TranspRateModel transpRate = new TranspRateModel();
        transpRate.setServiceAmount(0);
        transpRate.setBcRetentionAmount(0);
        transpRate.setIcmsRetentionRate(0);
        transpRate.setIcmsRetentionAmount(0);
        transpRate.setCfop(0);
        transpRate.setCityGeneratorFactCode(0);
        transport.setTranspRate(transpRate);

        // Make the Additional Information for nfce
        AdditionalInformationModel additionalInformation = new AdditionalInformationModel();
        additionalInformation.setFisco("Nota Fiscal de Consumidor Eletrônica");
        additionalInformation.setTaxpayer("ocumento emitido em contingência");
        List<Integer> xmlAuth = new ArrayList<>();
        Integer xml = 1233456;
        xmlAuth.add(xml);
        additionalInformation.setEffort("Nenhum");
        additionalInformation.setOrder("Pedido 1");
        additionalInformation.setContract("");
        additionalInformation.setTaxDocumentsReference(new ArrayList<>());
        TaxpayerCommentsModel taxpayerComments = new TaxpayerCommentsModel();
        taxpayerComments.setField("Observacao");
        taxpayerComments.setText("Sem informacoes adicionais");
        additionalInformation.setXmlAuthorized(xmlAuth); // Essa informacaoc so sera setada depois que o xml for autorizado
        additionalInformation.setReferencedProcess(new ArrayList<>());

        // Itens nfce note
        ItemModel itens = new ItemModel();
        itens.setCode("1");
        itens.setCodeGTIN("7891234567890");
        itens.setDescription("PRoduto de exemplo");
        itens.setNcm("12345678");
        itens.setNve(new ArrayList<>());
        itens.setExTipi("0");
        itens.setCfop(1533);
        itens.setUnit("UN");
        itens.setQuantity(1);
        itens.setUnitAmount(20.00);
        itens.setTotalAmount(20.00);
        itens.setCodeTaxGTIN("");
        itens.setUnitTax("");
        itens.setQuantityTax(1);
        itens.setTaxUnitAmount(10);
        itens.setFreightAmount(0);
        itens.setInsuranceAmount(0);
        itens.setDiscountAmount(0);
        itens.setOthersAmount(0);
        itens.setTotalIndicator(true);
        itens.setCest("");
        itensList.add(itens);

        TaxModel tax = new TaxModel();
        tax.setTotalTax(0);
        IcmsModel icms = new IcmsModel();
        icms.setOrigin("0");
        icms.setCst("00");
        icms.setCsosn("");
        icms.setBaseTaxModality("Margem valor agragado");
        icms.setBaseTax(0);
        icms.setBaseTaxSTModality("");
        icms.setBaseTaxSTReduction("");
        icms.setBaseTaxST(0);
        icms.setBaseTaxReduction(0);
        icms.setStRate(0);
        icms.setStAmount(0);
        icms.setStMarginAmount(0);
        icms.setRate(0);
        icms.setAmount(0);
        icms.setPercentual(0);
        icms.setSnCreditAmount(0);
        icms.setStMarginAddedAmount("");
        icms.setStRetentionAmount("");
        icms.setBaseSTRetentionAmount("");
        icms.setBaseTaxOperationPercentual("");
        icms.setUfst("SP");
        icms.setAmountSTReason("");
        icms.setBaseSNRetentionAmount("");
        icms.setSnRetentionAmount("");
        icms.setAmountOperation("");
        icms.setPercentualDeferment("");
        icms.setBaseDeferred("");
        icms.setExemptAmountST(0);
        icms.setExemptReasonST("");
        icms.setFcpRate(2);
        icms.setFcpAmount(2);
        icms.setFcpstRate(0);
        icms.setFcpstAmount(2);
        icms.setBaseTaxFCPSTAmount(0);
        icms.setSubstituteAmount(0);
        icms.setStFinalConsumerRate(0);
        icms.setEffectiveBaseTaxReductionRate(0);
        icms.setEffectiveBaseTaxAmount(0);
        icms.setEffectiveRate(0);
        icms.setEffectiveAmount(0);
        icms.setDeductionIndicator("Nao deduzido");
        tax.setIcms(icms);

        IpiModel ipi = new IpiModel();
        ipi.setCst("99");
        ipi.setClassificationCode("12345");
        ipi.setClassification("Bebidas");
        ipi.setProducerCNPJ("");
        ipi.setStampCode("");
        ipi.setStampQuantity(0);
        ipi.setBase(0);
        ipi.setRate(0);
        ipi.setUnitQuantity(0);
        ipi.setUnitAmount(0);
        ipi.setAmount(0);
        tax.setIpi(ipi);

        IiModel ii = new IiModel();
        ii.setBaseTax("");
        ii.setCustomsExpenditureAmount("");
        ii.setAmount(0);
        ii.setIofAmount(0);
        ii.setvEnqCamb(0);
        tax.setIi(ii);

        PisModel pis = new PisModel();
        pis.setCst("01");
        pis.setBaseTax(100.00);
        pis.setRate(1.65);
        pis.setAmount(1.65);
        pis.setBaseTaxProductQuantity(0);
        pis.setProductRate(0);
        tax.setPis(pis);

        CofinsModel cofins = new CofinsModel();
        cofins.setCst("01");
        cofins.setBaseTaxProductQuantity(0);
        cofins.setRate(0);
        cofins.setAmount(0);
        cofins.setBaseTaxProductQuantity(0);
        cofins.setProductRate(0);
        tax.setCofins(cofins);

        itens.setTax(tax);
        itens.setAdditionalInformation("");
        itens.setNumberOrderBuy("1");
        itens.setImportControlSheetNumber("");

        FuelDetailModel fuelDetail = new FuelDetailModel(); // Aqui sera setado caso o item possua codigo anp apenas ou seja se ele seja oleo
        fuelDetail.setCodeANP("");
        fuelDetail.setDescriptionANP("");
        fuelDetail.setPercentageGLP(0);
        fuelDetail.setPercentageNGn(0);
        fuelDetail.setPercentageGNi(0);
        fuelDetail.setStartingAmount(0);
        fuelDetail.setCodif("");
        fuelDetail.setAmountTemp(0);
        fuelDetail.setStateBuyer("");

        CideModel cide = new CideModel();
        cide.setBc(0);
        cide.setRate(0);
        cide.setCideAmount(0);
        fuelDetail.setCide(cide);

        PumpModel pump = new PumpModel();
        pump.setNumber(0);
        pump.setBeginningAmount(0);
        pump.setEndAmount(0);
        pump.setPercentageBio(0);
        pump.setTankNumber(0);
        pump.setSpoutNumber(0);
        fuelDetail.setPump(pump);

        FuelOriginModel fuelOrigin = new FuelOriginModel();
        fuelOrigin.setcUFOrig(0);
        fuelOrigin.setpOrig(0);
        fuelOrigin.setIndImport(0);
        fuelDetail.setFuelOrigin(fuelOrigin);
        itens.setFuelDetail(fuelDetail);
        itens.setBenefit("");
        itens.setImportDeclarations(new ArrayList<ImportDeclarationModel>());

        // Verficiar na hora do envio se esses 3 campos nao teram problemas por estarem vazios
        itens.setExportDetails(new ArrayList<ExportDetailModel>());
        TaxDeterminationModel taxDetermination = new TaxDeterminationModel();
        itens.setTaxDetermination(taxDetermination);


        // Make totals
        TotalsModel totals = new TotalsModel();
        totals.setIcms(icms);
        totals.setIssqn(new IssqnModel()); // Verificar se isso e importante

        // Make billing
        BillingModel billing = new BillingModel();
        BillModel bill = new BillModel();
        bill.setNumber("");
        bill.setOriginalAmount(0);
        bill.setDiscountAmount(0);
        bill.setNetAmount(0);
        billing.setBill(bill);

        DuplicateModel duplicate = new DuplicateModel();
        duplicate.setNumber("");
        //duplicate.setExpirationOn();
        duplicate.setAmount(0);

        duplicateList.add(duplicate);
        billing.setDuplicates(duplicateList);

        // Make a issuer nfce
        IssuerModel issuer = new IssuerModel();
        issuer.setStStateTaxNumber("");

        // Make transactionIntermediate
        TransactionIntermediateModel transactionIntermediate =  new TransactionIntermediateModel();
        transactionIntermediate.setFederalTaxNumber(0);
        transactionIntermediate.setIdentifier("");

        // Make a nfce
        InvoiceModel a = new InvoiceModel();
        a.setId("1"); // nfce indentification
        a.setPayment(payments);
        a.setSerie(1);
        a.setNumber(145097);
        a.setOperationOn(LocalDateTime.now()); // Date and time the operation was completed
        a.setOperationNature("Venda");
        a.setOperationType("Saida"); // Type operation : out or in
        a.setDestination("");
        a.setPrintType("");
        a.setPurposeType("");
        a.setConsumerType("Consumidor final");
        //a.setPresenceType();
        //a.setContingencyOn();
        //a.setContingencyJustification();
        a.setBuyer(buyer);
        a.setTransport(transport);
        a.setAdditionalInformation(additionalInformation);
        a.setItems(itensList);
        a.setTotals(totals);
        a.setBilling(billing);
        a.setIssuer(issuer);
        a.setTransactionIntermediate(transactionIntermediate);

        ObjectMapper om = new ObjectMapper();
        String json;
        if(a.getItems().isEmpty() || payments.isEmpty()){
            fail("A nota nao possui itens");
        }

        try{
            json = om.writeValueAsString(a);
            System.out.println("Esse foi o resultado do json gerado: " + json);
        }catch(JsonProcessingException e){
            fail("Errou ao serializar");
        }

        //emissaoPrenchimentoDadosFiscaisService.preencherDadosFiscais(a);
    }


    @Test
    public void testMapErros() {
        Map<String, List<String>> mapErros = new HashMap<String, List<String>>();

        InvoiceModel nfce = new InvoiceModel();
        ItemModel item = new ItemModel();
        List<ItemModel> itens = new ArrayList<>();

        item.setDescription("Item 1");
        item.setNcm("");
        item.setExTipi("");
        itens.add(item);

        nfce.setItems(itens);

        for(ItemModel itemEach : itens){
            if(itemEach.getNcm().isEmpty()){
                mapErros.computeIfAbsent("item", i -> new ArrayList<>()).add("Nao foi encontrado ncm do item.");
            }

            if(itemEach.getExTipi() == null && itemEach.getExTipi().isEmpty()){
                mapErros.computeIfAbsent("item", i -> new ArrayList<>()).add("Nao foi encontrada a ex do ncm.");
            }
        }

        if(mapErros.isEmpty()){
            fail("O mapa esta vazio");
        }

        System.out.println("Erros: " + mapErros);
    }

    @Test
    public void testSeNcmExiste(){

        InvoiceModel a = new InvoiceModel();
        ItemModel item = new ItemModel();
        List<ItemModel> listItem  = new ArrayList<>();

        item.setNcm("1012100");
        item.setExTipi("0");
        listItem.add(item);
        a.setItems(listItem);

        String ncm =  a.getItems().get(0).getNcm();
        String tipi = a.getItems().get(0).getExTipi();


        int ncmParse = Integer.parseInt(ncm);
        int tipiParse = Integer.parseInt(tipi);

        Ncm opNcm = ncmService.findNcmByNumeroAndExcecao(ncmParse,tipiParse).get();

        if(opNcm.getNumero() < 0 ){
            fail("Nao foi encontrado o ncm em questao");
        }
        System.out.println("O ncm existe: " + opNcm.getDescricao());
    }

    @Test
    public void searchTaxForItem() {

        InvoiceModel a = new InvoiceModel();
        ItemModel item = new ItemModel();
        List<ItemModel> listItem  = new ArrayList<>();

        item.setNcm("87089990");
        item.setExTipi("0");
        listItem.add(item);
        a.setItems(listItem);
        a.setOperationNature("Venda");

        List<Integer> listNcmsItemsNota = new ArrayList<>();
        for(ItemModel itemNcm : listItem) {
            String ncm = itemNcm.getNcm();
            listNcmsItemsNota.add(Integer.parseInt(ncm));
        }

        List<TributacaoEstadual> tributacaoEstadual = recalculoRepository.findImpostoEstadualByNcmAndOperacao(listNcmsItemsNota,1L);
        List<TributacaoFederal> tributacaoFederal = recalculoRepository.findImpostoFederalByNcmAndOperacao(listNcmsItemsNota,1L);

        if (!tributacaoEstadual.isEmpty() && !tributacaoFederal.isEmpty()) {
            System.out.println("Foram encontradas tributacoes para os ncm");
        }
    }

    @Test
    public void testGeracaoDoXmlDaNota() throws JAXBException {

        List<PaymentsDetailsModel> detailsPayments = new ArrayList();
        List<PaymentModel> payments = new ArrayList();
        List<ItemModel> itensList = new ArrayList();
        List<DuplicateModel> duplicateList = new ArrayList<>();

        // Make payment model
        PaymentModel payment  = new PaymentModel();
        payment.setPaymentDetail(detailsPayments);
        payment.setPayBack(0);
        payments.add(payment);

        // Make details payment
        PaymentsDetailsModel detailsPayment = new PaymentsDetailsModel();
        detailsPayment.setMethod("A vista");
        detailsPayment.setMethodDescription("Dinheiro a vista");
        detailsPayment.setPaymentType("Em dinheiro");
        detailsPayment.setAmount(32.20);

        CardModel card = new CardModel(); // set card beelong a details payment || information for payment card
        card.setFederalTaxNumber("");
        card.setFlag("");
        card.setAuthorization("");
        card.setIntegrationPaymentType("");
        card.setFederalTaxNumberRecipient("");
        card.setIdPaymentTerminal("");

        detailsPayment.setCard(card);
        detailsPayment.setPaymentDate(LocalDateTime.of(2024, 11, 9, 9, 3, 58));
        detailsPayment.setFederalTaxNumberPag("63728890000172"); // Possivel cnpj do pagante
        detailsPayment.setStatePag("SP");
        detailsPayments.add(detailsPayment);

        // Make information for buyer
        BuyerModel buyer = new BuyerModel();
        buyer.setAccountId("10"); // id da conta do comprador
        buyer.setId("1");
        buyer.setName("Comprador Teste ");
        buyer.setFederalTaxNumber(1569621012);
        buyer.setEmail("comprador@teste.com");

        AddressModel address = new AddressModel(); // Vamos comecar setar o endereco do ocmprador
        address.setState("SP");

        CityModel city = new CityModel(); // Vamos comecar setar as informacoes de cidade
        city.setCode("3550308");
        city.setCode("Sao Paulo");
        address.setCity(city);

        address.setDistrict("Centro");
        address.setAdditionalInformation("Casa");
        address.setStreet("Av. otaviano");
        address.setNumber("1234");
        address.setPostalCode("01001000");
        address.setCountry("SP");
        address.setPhone("+5511999999999");
        buyer.setAddress(address);

        buyer.setType("Pessoa fisica");
        buyer.setStateTaxNumberIndicator("Nao contribuente");
        buyer.setTradeName("Cliente teste");
        buyer.setTaxRegime(false);
        buyer.setStateTaxNumber("");

        // Make a transport
        TransportModel transport = new TransportModel();
        transport.setFreightModality("");

        TransportGroupModel groupTransport = new TransportGroupModel(); // Information for transport group
        groupTransport.setAccountId("");
        groupTransport.setId("");
        groupTransport.setName("");
        groupTransport.getFederalTaxNumber();
        groupTransport.setEmail("");
        groupTransport.setAddress(address);
        groupTransport.setTransportRetention("");
        groupTransport.setStateTaxNumber("");
        groupTransport.setTransportRetention("");

        transport.setTransportGroup(groupTransport);


        ReboqueModel reboque  = new ReboqueModel(); // Make a transport reboque  information
        reboque.setPlate("");
        reboque.setUf("");
        reboque.setRntc("");
        reboque.setWagon("");
        reboque.setFerry("");

        transport.setReboque(reboque);

        VolumeModel volume = new VolumeModel(); // make a volume for transport
        volume.setVolumeQuantity(0);
        volume.setSpecies("");
        volume.setBrand("");
        volume.setVolumeNumeration("");
        volume.setNetWeight(0);
        volume.setGrossWeight(0);
        transport.setVolume(volume);

        TransportVehicleModel transportVehicle = new TransportVehicleModel();
        transportVehicle.setPlate("");
        transportVehicle.setState("");
        transportVehicle.setRntc("");
        transport.setTransportVehicle(transportVehicle);

        transport.setSealNumber("");
        TranspRateModel transpRate = new TranspRateModel();
        transpRate.setServiceAmount(0);
        transpRate.setBcRetentionAmount(0);
        transpRate.setIcmsRetentionRate(0);
        transpRate.setIcmsRetentionAmount(0);
        transpRate.setCfop(0);
        transpRate.setCityGeneratorFactCode(0);
        transport.setTranspRate(transpRate);

        // Make the Additional Information for nfce
        AdditionalInformationModel additionalInformation = new AdditionalInformationModel();
        additionalInformation.setFisco("Nota Fiscal de Consumidor Eletrônica");
        additionalInformation.setTaxpayer("ocumento emitido em contingência");
        List<Integer> xmlAuth = new ArrayList<>();
        Integer xml = 1233456;
        xmlAuth.add(xml);
        additionalInformation.setEffort("Nenhum");
        additionalInformation.setOrder("Pedido 1");
        additionalInformation.setContract("");
        additionalInformation.setTaxDocumentsReference(new ArrayList<>());
        TaxpayerCommentsModel taxpayerComments = new TaxpayerCommentsModel();
        taxpayerComments.setField("Observacao");
        taxpayerComments.setText("Sem informacoes adicionais");
        additionalInformation.setXmlAuthorized(xmlAuth); // Essa informacaoc so sera setada depois que o xml for autorizado
        additionalInformation.setReferencedProcess(new ArrayList<>());

        // Itens nfce note
        ItemModel itens = new ItemModel();
        itens.setCode("1");
        itens.setCodeGTIN("7891234567890");
        itens.setDescription("PRoduto de exemplo");
        itens.setNcm("12345678");
        itens.setNve(new ArrayList<>());
        itens.setExTipi("0");
        itens.setCfop(1533);
        itens.setUnit("UN");
        itens.setQuantity(1);
        itens.setUnitAmount(20.00);
        itens.setTotalAmount(20.00);
        itens.setCodeTaxGTIN("");
        itens.setUnitTax("");
        itens.setQuantityTax(1);
        itens.setTaxUnitAmount(10);
        itens.setFreightAmount(0);
        itens.setInsuranceAmount(0);
        itens.setDiscountAmount(0);
        itens.setOthersAmount(0);
        itens.setTotalIndicator(true);
        itens.setCest("");
        itensList.add(itens);

        TaxModel tax = new TaxModel();
        tax.setTotalTax(0);
        IcmsModel icms = new IcmsModel();
        icms.setOrigin("0");
        icms.setCst("00");
        icms.setCsosn("");
        icms.setBaseTaxModality("Margem valor agragado");
        icms.setBaseTax(0);
        icms.setBaseTaxSTModality("");
        icms.setBaseTaxSTReduction("");
        icms.setBaseTaxST(0);
        icms.setBaseTaxReduction(0);
        icms.setStRate(0);
        icms.setStAmount(0);
        icms.setStMarginAmount(0);
        icms.setRate(0);
        icms.setAmount(0);
        icms.setPercentual(0);
        icms.setSnCreditAmount(0);
        icms.setStMarginAddedAmount("");
        icms.setStRetentionAmount("");
        icms.setBaseSTRetentionAmount("");
        icms.setBaseTaxOperationPercentual("");
        icms.setUfst("SP");
        icms.setAmountSTReason("");
        icms.setBaseSNRetentionAmount("");
        icms.setSnRetentionAmount("");
        icms.setAmountOperation("");
        icms.setPercentualDeferment("");
        icms.setBaseDeferred("");
        icms.setExemptAmountST(0);
        icms.setExemptReasonST("");
        icms.setFcpRate(2);
        icms.setFcpAmount(2);
        icms.setFcpstRate(0);
        icms.setFcpstAmount(2);
        icms.setBaseTaxFCPSTAmount(0);
        icms.setSubstituteAmount(0);
        icms.setStFinalConsumerRate(0);
        icms.setEffectiveBaseTaxReductionRate(0);
        icms.setEffectiveBaseTaxAmount(0);
        icms.setEffectiveRate(0);
        icms.setEffectiveAmount(0);
        icms.setDeductionIndicator("Nao deduzido");
        tax.setIcms(icms);

        IpiModel ipi = new IpiModel();
        ipi.setCst("99");
        ipi.setClassificationCode("12345");
        ipi.setClassification("Bebidas");
        ipi.setProducerCNPJ("");
        ipi.setStampCode("");
        ipi.setStampQuantity(0);
        ipi.setBase(0);
        ipi.setRate(0);
        ipi.setUnitQuantity(0);
        ipi.setUnitAmount(0);
        ipi.setAmount(0);
        tax.setIpi(ipi);

        IiModel ii = new IiModel();
        ii.setBaseTax("");
        ii.setCustomsExpenditureAmount("");
        ii.setAmount(0);
        ii.setIofAmount(0);
        ii.setvEnqCamb(0);
        tax.setIi(ii);

        PisModel pis = new PisModel();
        pis.setCst("01");
        pis.setBaseTax(100.00);
        pis.setRate(1.65);
        pis.setAmount(1.65);
        pis.setBaseTaxProductQuantity(0);
        pis.setProductRate(0);
        tax.setPis(pis);

        CofinsModel cofins = new CofinsModel();
        cofins.setCst("01");
        cofins.setBaseTaxProductQuantity(0);
        cofins.setRate(0);
        cofins.setAmount(0);
        cofins.setBaseTaxProductQuantity(0);
        cofins.setProductRate(0);
        tax.setCofins(cofins);

        itens.setTax(tax);
        itens.setAdditionalInformation("");
        itens.setNumberOrderBuy("1");
        itens.setImportControlSheetNumber("");

        FuelDetailModel fuelDetail = new FuelDetailModel(); // Aqui sera setado caso o item possua codigo anp apenas ou seja se ele seja oleo
        fuelDetail.setCodeANP("");
        fuelDetail.setDescriptionANP("");
        fuelDetail.setPercentageGLP(0);
        fuelDetail.setPercentageNGn(0);
        fuelDetail.setPercentageGNi(0);
        fuelDetail.setStartingAmount(0);
        fuelDetail.setCodif("");
        fuelDetail.setAmountTemp(0);
        fuelDetail.setStateBuyer("");

        CideModel cide = new CideModel();
        cide.setBc(0);
        cide.setRate(0);
        cide.setCideAmount(0);
        fuelDetail.setCide(cide);

        PumpModel pump = new PumpModel();
        pump.setNumber(0);
        pump.setBeginningAmount(0);
        pump.setEndAmount(0);
        pump.setPercentageBio(0);
        pump.setTankNumber(0);
        pump.setSpoutNumber(0);
        fuelDetail.setPump(pump);

        FuelOriginModel fuelOrigin = new FuelOriginModel();
        fuelOrigin.setcUFOrig(0);
        fuelOrigin.setpOrig(0);
        fuelOrigin.setIndImport(0);
        fuelDetail.setFuelOrigin(fuelOrigin);
        itens.setFuelDetail(fuelDetail);
        itens.setBenefit("");
        itens.setImportDeclarations(new ArrayList<ImportDeclarationModel>());

        // Verficiar na hora do envio se esses 3 campos nao teram problemas por estarem vazios
        itens.setExportDetails(new ArrayList<ExportDetailModel>());
        TaxDeterminationModel taxDetermination = new TaxDeterminationModel();
        itens.setTaxDetermination(taxDetermination);


        // Make totals
        TotalsModel totals = new TotalsModel();
        totals.setIcms(icms);
        totals.setIssqn(new IssqnModel()); // Verificar se isso e importante

        // Make billing
        BillingModel billing = new BillingModel();
        BillModel bill = new BillModel();
        bill.setNumber("");
        bill.setOriginalAmount(0);
        bill.setDiscountAmount(0);
        bill.setNetAmount(0);
        billing.setBill(bill);

        DuplicateModel duplicate = new DuplicateModel();
        duplicate.setNumber("");
        //duplicate.setExpirationOn();
        duplicate.setAmount(0);

        duplicateList.add(duplicate);
        billing.setDuplicates(duplicateList);

        // Make a issuer nfce
        IssuerModel issuer = new IssuerModel();
        issuer.setStStateTaxNumber("");

        // Make transactionIntermediate
        TransactionIntermediateModel transactionIntermediate =  new TransactionIntermediateModel();
        transactionIntermediate.setFederalTaxNumber(0);
        transactionIntermediate.setIdentifier("");

        // Make a nfce
        InvoiceModel a = new InvoiceModel();
        a.setId("1"); // nfce indentification
        a.setPayment(payments);
        a.setSerie(1);
        a.setNumber(145097);
        a.setOperationOn(LocalDateTime.now()); // Date and time the operation was completed
        a.setOperationNature("Venda");
        a.setOperationType("Saida"); // Type operation : out or in
        a.setDestination("");
        a.setPrintType("");
        a.setPurposeType("");
        a.setConsumerType("Consumidor final");
        //a.setPresenceType();
        //a.setContingencyOn();
        //a.setContingencyJustification();
        a.setBuyer(buyer);
        a.setTransport(transport);
        a.setAdditionalInformation(additionalInformation);
        a.setItems(itensList);
        a.setTotals(totals);
        a.setBilling(billing);
        a.setIssuer(issuer);
        a.setTransactionIntermediate(transactionIntermediate);

        try {
            JAXBContext context = JAXBContext.newInstance(a.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.marshal(a, System.out);
        }catch (JAXBException e){
            e.printStackTrace();
        }
    }
}