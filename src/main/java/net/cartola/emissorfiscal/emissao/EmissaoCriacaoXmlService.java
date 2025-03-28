package net.cartola.emissorfiscal.emissao;

import br.com.autogeral.emissorfiscal.vo.*;
import br.com.swconsultoria.certificado.exception.CertificadoException;
import br.com.swconsultoria.nfe.dom.ConfiguracoesNfe;
import br.com.swconsultoria.nfe.dom.enuns.AmbienteEnum;
import br.com.swconsultoria.nfe.dom.enuns.DocumentoEnum;
import br.com.swconsultoria.nfe.dom.enuns.ServicosEnum;
import br.com.swconsultoria.nfe.exception.NfeException;
import br.com.swconsultoria.nfe.schema_4.enviNFe.*;
import br.com.swconsultoria.nfe.schema_4.enviNFe.TNFe.InfNFe;
import br.com.swconsultoria.nfe.schema_4.enviNFe.TNFe.InfNFe.*;
import br.com.swconsultoria.nfe.schema_4.enviNFe.TNFe.InfNFe.Det.Imposto;
import br.com.swconsultoria.nfe.schema_4.enviNFe.TNFe.InfNFe.Det.Imposto.COFINS;
import br.com.swconsultoria.nfe.schema_4.enviNFe.TNFe.InfNFe.Det.Imposto.COFINS.COFINSAliq;
import br.com.swconsultoria.nfe.schema_4.enviNFe.TNFe.InfNFe.Det.Imposto.ICMS;
import br.com.swconsultoria.nfe.schema_4.enviNFe.TNFe.InfNFe.Det.Imposto.PIS;
import br.com.swconsultoria.nfe.schema_4.enviNFe.TNFe.InfNFe.Det.Imposto.PIS.PISAliq;
import br.com.swconsultoria.nfe.schema_4.enviNFe.TNFe.InfNFe.Det.Prod;
import br.com.swconsultoria.nfe.schema_4.enviNFe.TNFe.InfNFe.Total.ICMSTot;
import br.com.swconsultoria.nfe.util.*;
import org.apache.xalan.xsltc.runtime.BasisLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

@Service
public class EmissaoCriacaoXmlService {

    private static final NumberFormat DECIMAL_FORMAT;
    private static final NumberFormat CPF_FORMAT;
    private static final NumberFormat CNPJ_FORMAT;

    static {
        DECIMAL_FORMAT = NumberFormat.getInstance(Locale.US);
        DECIMAL_FORMAT.setMaximumFractionDigits(2);
        DECIMAL_FORMAT.setMinimumFractionDigits(2);
        DECIMAL_FORMAT.setGroupingUsed(false);

        CPF_FORMAT = NumberFormat.getInstance(Locale.US);
        CPF_FORMAT.setMaximumFractionDigits(0);
        CPF_FORMAT.setMinimumFractionDigits(0);
        CPF_FORMAT.setMaximumIntegerDigits(11);
        CPF_FORMAT.setMinimumIntegerDigits(11);
        CPF_FORMAT.setGroupingUsed(false);

        CNPJ_FORMAT = NumberFormat.getInstance(Locale.US);
        CNPJ_FORMAT.setMaximumFractionDigits(0);
        CNPJ_FORMAT.setMinimumFractionDigits(0);
        CNPJ_FORMAT.setMaximumIntegerDigits(14);
        CNPJ_FORMAT.setMinimumIntegerDigits(14);
        CNPJ_FORMAT.setGroupingUsed(false);
    }

    private static final Logger LOG = Logger.getLogger(EmissaoCriacaoXmlService.class.getName());

    private ConfiguracoesNfe config;

    public EmissaoCriacaoXmlService() {
        try {
            config = ConfigNfceService.iniciaConfiguracoes();
        } catch (CertificadoException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public TNFe montaXmlNota(InvoiceModel invoice) throws NfeException {

        //Informe o Numero da NFCe
        int numeroNFCe = invoice.getNumber();

        //Informe o CNPJ do Emitente da NFCe
        String cnpj = invoice.getEmitente().getCpnjEmitente();

        //Informe o cnf da NFCe com 8 digitos
        String cnf = ChaveUtil.completarComZerosAEsquerda(String.valueOf(numeroNFCe), 8);

        //Informe o modelo da NFCe
        String modelo = invoice.getModeloNota();

        //Informe a Serie da NFCe
        int serie = invoice.getSerie();

        //Informe o tipo de Emissao da NFCe
        String tipoEmissao = "1";


        LocalDateTime dataEmissao = LocalDateTime.now();
        // MontaChave a NFCe
        ChaveUtil chaveUtil = new ChaveUtil(config.getEstado(), cnpj, modelo, serie, numeroNFCe, tipoEmissao, cnf, dataEmissao);
        String chave = chaveUtil.getChaveNF();
        String cdv = chaveUtil.getDigitoVerificador();

        InfNFe infNFe = new InfNFe();
        infNFe.setId(chave);
        infNFe.setVersao(ConstantesUtil.VERSAO.NFE);

        //Preenche IDE
        infNFe.setIde(preencheIde(config, cnf, numeroNFCe, tipoEmissao, modelo, serie, cdv, dataEmissao, invoice));

        //Preenche Emitente
        infNFe.setEmit(preencheEmitente(config, cnpj, invoice));

        //Preenche o Destinatario
        infNFe.setDest(preencheDestinatario(invoice));

        //Preenche os dados do Produto da NFCe e adiciona a Lista
        infNFe.getDet().addAll(preencheDet(invoice));

        //Preenche totais da NFCe
        infNFe.setTotal(preencheTotal(invoice.getTotals()));

        //Preenche os dados de Transporte
        infNFe.setTransp(preencheTransporte());

        // Preenche dados Pagamento
        infNFe.setPag(preenchePag(invoice, invoice.getTotals()));

        TNFe nfe = new TNFe();
        nfe.setInfNFe(infNFe);

        return nfe;
    }

    /**
     * Preenche o IDE
     *
     * @param config
     * @param cnf
     * @param numeroNFCe
     * @param tipoEmissao
     * @param cDv
     * @param dataEmissao
     * @return
     * @throws NfeException
     */
    private Ide preencheIde(ConfiguracoesNfe config, String cnf, int numeroNFCe, String tipoEmissao, String modelo, int serie, String cDv, LocalDateTime dataEmissao, InvoiceModel invoice) throws NfeException {
        Ide ide = new Ide();
        ide.setCUF(invoice.getCodUf());
        ide.setCNF(cnf);
        ide.setNatOp("NOTA FISCAL CONSUMIDOR ELETRONICA");
        ide.setMod(modelo);
        ide.setSerie(String.valueOf(serie));

        ide.setNNF(String.valueOf(numeroNFCe));
        ide.setDhEmi(XmlNfeUtil.dataNfe(dataEmissao));
        ide.setTpNF("1"); // SEMPRE SERA 1 POR SE TRATAR DE NOTAS DE SAIDAS
        ide.setIdDest("1"); // SEMPRE VAI SER 1 POR SE TRATAR DE NOTAS PARA O MESMO ESTADO
        ide.setCMunFG(invoice.getEmitente().getCodIbgeMunicipioEmitente());
        ide.setTpImp("4"); // NFCE COM DANFE
        ide.setTpEmis(tipoEmissao);
        ide.setCDV(cDv);
        ide.setTpAmb(config.getAmbiente().getCodigo());
        ide.setFinNFe("1"); // PADRAO
        ide.setIndFinal("1"); // PADRAO
        ide.setIndPres("1"); // PADRAO
        ide.setProcEmi("0"); // PADRAO
        ide.setVerProc("1.0"); // PADRAO
        return ide;
    }

    /**
     * Preenche o Emitente da NFCe
     *
     * @param config
     * @param cnpj
     * @return
     */
    private Emit preencheEmitente(ConfiguracoesNfe config, String cnpj, InvoiceModel invoice) {
        Emit emit = new Emit();
        emit.setCNPJ(cnpj);
        emit.setXNome(invoice.getEmitente().getNomeEmitente());

        TEnderEmi enderEmit = new TEnderEmi();
        enderEmit.setXLgr(invoice.getEmitente().getLogradouroEmitente());
        enderEmit.setNro(invoice.getEmitente().getNumeroLogradouroEmitente());
        enderEmit.setXCpl(invoice.getEmitente().getComplementoEdenrecoEmitente());
        enderEmit.setXBairro(invoice.getEmitente().getBairroEmitente());
        enderEmit.setCMun(invoice.getEmitente().getCodIbgeMunicipioEmitente());
        enderEmit.setXMun(invoice.getEmitente().getMunicipioEmitente());
        enderEmit.setUF(TUfEmi.valueOf(config.getEstado().toString()));
        enderEmit.setCEP(invoice.getEmitente().getCepEmitente());
        enderEmit.setCPais("1058");
        enderEmit.setXPais("Brasil");
        enderEmit.setFone(invoice.getEmitente().getTelefoneEmitente());
        emit.setEnderEmit(enderEmit);

        emit.setIE(invoice.getEmitente().getInscricaoEstadual());
        emit.setCRT("3");

        return emit;
    }

    /**
     * Preenche o Destinatario da NFCe
     *
     * @return
     */
    private Dest preencheDestinatario(InvoiceModel invoice) {
        BuyerModel buyer = invoice.getBuyer();
        Dest dest = new Dest();
        if (99999999999L < buyer.getFederalTaxNumber()) {
            dest.setCNPJ(CNPJ_FORMAT.format(buyer.getFederalTaxNumber()));
        } else {
            dest.setCPF(CPF_FORMAT.format(buyer.getFederalTaxNumber()));
        }
        if (AmbienteEnum.HOMOLOGACAO.equals(config.getAmbiente())) {
            dest.setXNome("NF-E EMITIDA EM AMBIENTE DE HOMOLOGACAO - SEM VALOR FISCAL");
        } else {
            dest.setXNome(buyer.getName());
        }


        TEndereco enderDest = new TEndereco();
        AddressModel address = invoice.getBuyer().getAddress();
        enderDest.setXLgr(address.getDistrict());
        enderDest.setNro(address.getNumber());
        enderDest.setXBairro(address.getCountry());
        enderDest.setCMun(address.getCity().getCode());
        enderDest.setXMun(address.getCity().getName());
        enderDest.setUF(TUf.valueOf(address.getState()));
        enderDest.setCEP(address.getPostalCode());
        enderDest.setCPais("1058");
        enderDest.setXPais("Brasil");
        //enderDest.setFone(invoice.getBuyer().getN);
        dest.setEnderDest(enderDest);
        //dest.setEmail("teste@test");
        dest.setIndIEDest("9");
        return dest;
    }

    /**
     * Preenche Det NFCe
     */
    private List<Det> preencheDet(InvoiceModel invoice) {

        //O Preenchimento deve ser feito por produto, Então deve ocorrer uma LIsta
        List<Det> listDeDet = new ArrayList<>();

        List<ItemModel> itemInvoice = invoice.getItems();
        int numeroItem = 1;
        for (ItemModel item : itemInvoice) {
            Det det = new Det();

            //O numero do Item deve seguir uma sequencia
            det.setNItem(String.valueOf(numeroItem));

            // Preenche dados do Produto
            det.setProd(preencheProduto(item, numeroItem));

            //Preenche dados do Imposto
            det.setImposto(preencheImposto(item));

            listDeDet.add(det);
            numeroItem++;

        }

        //Retorna a Lista de Det
        return listDeDet;
    }

    /**
     * Preenche dados do Produto
     *
     * @return
     */
    private Prod preencheProduto(ItemModel item, int numeroItem) {
        Prod prod = new Prod();
        prod.setCProd(item.getCode());
        prod.setCEAN(item.getCodeGTIN());

        if (AmbienteEnum.HOMOLOGACAO.equals(config.getAmbiente()) && numeroItem == 1) {
            prod.setXProd("NOTA FISCAL EMITIDA EM AMBIENTE DE HOMOLOGACAO - SEM VALOR FISCAL");
        } else {
            prod.setXProd(item.getDescription());
        }
        prod.setNCM(item.getNcm());
        prod.setCEST(item.getCest());

        if (null != item.getFuelDetail() &&
                null != item.getFuelDetail().getCodeANP() &&
                !item.getFuelDetail().getCodeANP().isEmpty()) {
            Prod.Comb c = new Prod.Comb();
            c.setCProdANP(item.getFuelDetail().getCodeANP());
            c.setDescANP(item.getFuelDetail().getDescriptionANP());
            c.setUFCons(TUf.SP);
            prod.setComb(c);
        }

        //prod.setIndEscala("S");
        prod.setCFOP(item.getTax().getCfop());
        prod.setUCom(item.getUnit());
        prod.setQCom(String.valueOf(item.getQuantity()));
        prod.setVUnCom(String.valueOf(item.getUnitAmount()));
        prod.setVProd(DECIMAL_FORMAT.format(item.getTotalAmount()));
        prod.setCEANTrib(item.getCodeGTIN());
        prod.setUTrib(item.getUnit());
        prod.setQTrib(String.valueOf(item.getQuantity()));
        prod.setVUnTrib(String.valueOf(item.getUnitAmount()));
        prod.setIndTot("1");

        return prod;
    }

    /**
     * Preenche dados do Imposto da NFCe
     *
     * @return
     */
    private Imposto preencheImposto(ItemModel item) {
        Imposto imposto = new Imposto();

//        NumberFormat percentFormat = NumberFormat.getPercentInstance();
//        percentFormat.setMaximumFractionDigits(2);
//        percentFormat.setMinimumFractionDigits(2);

        ICMS icms = new ICMS();

        if (item.getTax().getIcms().getCst().equals("60")) {
            ICMS.ICMS60 icms60 = new ICMS.ICMS60();
            icms60.setOrig("0");
            icms60.setCST(item.getTax().getIcms().getCst());
            icms60.setVBCSTRet(DECIMAL_FORMAT.format(item.getTax().getIcms().getBaseTax()));
            icms60.setPST(DECIMAL_FORMAT.format(item.getTax().getIcms().getRate() * 100));
            icms60.setVICMSSubstituto(DECIMAL_FORMAT.format(item.getTax().getIcms().getAmount()));
            icms60.setVICMSSTRet(DECIMAL_FORMAT.format(item.getTax().getIcms().getAmount()));
            icms.setICMS60(icms60);
        }

        if (item.getTax().getIcms().equals("00")) {
            ICMS.ICMS00 icms00 = new ICMS.ICMS00();
            icms00.setOrig("0"); // Produto nacional ou importado ?
            icms00.setCST(item.getTax().getIcms().getCst());
            icms00.setModBC("3"); // valor da operacao
            icms00.setVBC(DECIMAL_FORMAT.format(item.getTax().getIcms().getBaseTax()));
            icms00.setPICMS(DECIMAL_FORMAT.format(item.getTax().getIcms().getRate() * 100));
            icms00.setVICMS(DECIMAL_FORMAT.format(item.getTax().getIcms().getAmount()));
            icms.setICMS00(icms00);
        }

        PIS pis = new PIS();

        String cst = item.getTax().getPis().getCst();

        int iPisCst = Integer.parseInt(cst);
        // Verifica se o CST é um número de um dígito e adiciona o zero à esquerda se necessário
        String cstFormatted = String.format("%02d", iPisCst);
        switch (iPisCst) {
            case 1: {
                PISAliq pisAliq = new PISAliq();
                pisAliq.setCST(cstFormatted);
                pisAliq.setVBC(DECIMAL_FORMAT.format(item.getTax().getPis().getBaseTax()));
                pisAliq.setPPIS(DECIMAL_FORMAT.format(item.getTax().getPis().getRate() * 100));
                pisAliq.setVPIS(DECIMAL_FORMAT.format(item.getTax().getPis().getAmount()));
                pis.setPISAliq(pisAliq);
            } break;
            case 4: {
                PIS.PISNT pisNt = new PIS.PISNT();
                pisNt.setCST(cstFormatted);
                pis.setPISNT(pisNt);
            }
        }

        COFINS cofins = new COFINS();
//        COFINSAliq cofinsAliq = new COFINSAliq();

        String cstCofins = item.getTax().getCofins().getCst();

        int iCofinsCst = Integer.parseInt(cstCofins);
        // Verifica se o CST é um número de um dígito e adiciona o zero à esquerda se necessário
        String cofinsCstFormatted = String.format("%02d", iCofinsCst);
        switch (iCofinsCst) {
            case 1: {
                COFINSAliq cofinsAliq = new COFINSAliq();
                cofinsAliq.setCST(cofinsCstFormatted);
                cofinsAliq.setVBC(DECIMAL_FORMAT.format(item.getTax().getCofins().getBaseTax()));
                cofinsAliq.setPCOFINS(DECIMAL_FORMAT.format(item.getTax().getCofins().getRate() * 100));
                cofinsAliq.setVCOFINS(DECIMAL_FORMAT.format(item.getTax().getCofins().getAmount()));
                cofins.setCOFINSAliq(cofinsAliq);
            } break;
            case 4: {
                COFINS.COFINSNT cofinsNt = new COFINS.COFINSNT();
                cofinsNt.setCST(cofinsCstFormatted);
                cofins.setCOFINSNT(cofinsNt);
            }
        }

        JAXBElement<ICMS> icmsElement = new JAXBElement<ICMS>(new QName("ICMS"), ICMS.class, icms);
        imposto.getContent().add(icmsElement);

        JAXBElement<PIS> pisElement = new JAXBElement<PIS>(new QName("PIS"), PIS.class, pis);
        imposto.getContent().add(pisElement);

        JAXBElement<COFINS> cofinsElement = new JAXBElement<COFINS>(new QName("COFINS"), COFINS.class, cofins);
        imposto.getContent().add(cofinsElement);

        return imposto;
    }

    /**
     * Prenche Total NFCe
     *
     * @return
     */
    private Total preencheTotal(TotalsModel totals) {
        Total total = new Total();
        ICMSTot icmstot = new ICMSTot();
        icmstot.setVBC(totals.getvBc());
        icmstot.setVICMS(totals.getvIcms());
        icmstot.setVICMSDeson(totals.getvIcmsDeson());
        icmstot.setVFCP(totals.getvFcp());
        icmstot.setVFCPST(totals.getvFcpSt());
        icmstot.setVFCPSTRet(totals.getvFcpStRet());
        icmstot.setVBCST(totals.getvBcst());
        icmstot.setVST(totals.getvSt());
        icmstot.setVProd(totals.getvProd());
        icmstot.setVFrete(totals.getvFrete());
        icmstot.setVSeg(totals.getvSeg());
        icmstot.setVDesc(totals.getvDesc());
        icmstot.setVII(totals.getvII());
        icmstot.setVIPI(totals.getvIpi());
        icmstot.setVIPIDevol(totals.getvIpiDevol());
        //icmstot.setVPIS(DECIMAL_FORMAT.format(totals.getvPis()));
        icmstot.setVPIS(totals.getvPis());
        icmstot.setVCOFINS(totals.getvCofins());
        icmstot.setVOutro((totals.getvOutro()));
        icmstot.setVNF((totals.getvNf()));
        //icmstot.setVTotTrib(String.valueOf(totals.getvTotTrib()));
        total.setICMSTot(icmstot);

        return total;
    }

    /**
     * Preenche Transporte
     *
     * @return
     */
    private Transp preencheTransporte() {
        Transp transp = new Transp();
        transp.setModFrete("9"); // Sem frete, comum para nfces
        return transp;
    }

    /**
     * Preenche dados Pagamento
     *
     * @return
     */
    private Pag preenchePag(InvoiceModel invoice, TotalsModel totals) {
        Pag pag = new Pag();
        Pag.DetPag detPag = new Pag.DetPag();
        detPag.setTPag(invoice.getModoPagamento());
        detPag.setVPag(totals.getvNf());
        if ("99".equals(detPag.getTPag())) {
            detPag.setXPag(invoice.getModoDePagamentoDescricao());
        }

        pag.getDetPag().add(detPag);
        return pag;
    }

}