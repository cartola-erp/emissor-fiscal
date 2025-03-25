package net.cartola.emissorfiscal.emissao;

import br.com.autogeral.emissorfiscal.vo.BuyerModel;
import br.com.autogeral.emissorfiscal.vo.InvoiceModel;
import br.com.autogeral.emissorfiscal.vo.ItemModel;
import br.com.autogeral.emissorfiscal.vo.TotalsModel;
import br.com.swconsultoria.certificado.exception.CertificadoException;
import br.com.swconsultoria.nfe.Nfe;
import br.com.swconsultoria.nfe.dom.ConfiguracoesNfe;
import br.com.swconsultoria.nfe.dom.enuns.*;
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
import br.com.swconsultoria.nfe.schema_4.retConsReciNFe.TRetConsReciNFe;
import br.com.swconsultoria.nfe.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Samuel Oliveira
 */

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

    @Autowired
    private CertificadoPfxModel certificadoPfx;

    public void montaXmlNota(InvoiceModel invoice) {

        try {
            // Inicia As Configurações - ver https://github.com/Samuel-Oliveira/Java_NFe/wiki/1-:-Configuracoes
            ConfiguracoesNfe config = ConfigNfceService.iniciaConfiguracoes();

            //Informe o Numero da NFCe
            int numeroNFCe = invoice.getNumber();

            //Informe o CNPJ do Emitente da NFCe
            String cnpj = invoice.getEmitente().getCpnjEmitente();

            //Informe a data de Emissao da NFCe
            LocalDateTime dataEmissao = invoice.getOperationOn();

            //Informe o cnf da NFCe com 8 digitos
            String cnf = ChaveUtil.completarComZerosAEsquerda(String.valueOf(numeroNFCe), 8);

            //Informe o modelo da NFCe
            String modelo = invoice.getModeloNota();

            //Informe a Serie da NFCe
            int serie = invoice.getSerie();

            //Informe o tipo de Emissao da NFCe
            String tipoEmissao = "1";

            //Informe o idToken
            String idToken = certificadoPfx.getTokenSefaz();

            //Informe o CSC da NFCe
            String csc = certificadoPfx.getCscSefaz();

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

            // Monta EnviNfe
            TEnviNFe enviNFe = new TEnviNFe();
            enviNFe.setVersao(ConstantesUtil.VERSAO.NFE);
            enviNFe.setIdLote("1");
            enviNFe.setIndSinc("1");
            enviNFe.getNFe().add(nfe);

            // Monta e Assina o XML
            enviNFe = Nfe.montaNfe(config, enviNFe, true);

            // Conversão do objeto enviNFe para JSON e exibição no console
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(enviNFe);
                System.out.println("# JSON da NFC-e:");
                System.out.println(json);
            } catch (Exception e) {
                System.err.println("Erro ao converter para JSON: " + e.getMessage());
            }

            //Monta QRCode
            String qrCode = preencheQRCode(enviNFe, config, idToken, csc);

            TNFe.InfNFeSupl infNFeSupl = new TNFe.InfNFeSupl();
            infNFeSupl.setQrCode(qrCode);
            infNFeSupl.setUrlChave(WebServiceUtil.getUrl(config, DocumentoEnum.NFCE, ServicosEnum.URL_CONSULTANFCE));
            enviNFe.getNFe().get(0).setInfNFeSupl(infNFeSupl);

            // Envia a Nfe para a Sefaz
            TRetEnviNFe retorno = Nfe.enviarNfe(config, enviNFe, DocumentoEnum.NFCE);

            //Valida se o Retorno é Assincrono
            if (RetornoUtil.isRetornoAssincrono(retorno)) {
                //Pega o Recibo
                String recibo = retorno.getInfRec().getNRec();
                int tentativa = 0;
                TRetConsReciNFe retornoNfe = null;

                //Define Numero de tentativas que iráx tentar a Consulta
                while (tentativa < 15) {
                    retornoNfe = Nfe.consultaRecibo(config, recibo, DocumentoEnum.NFE);
                    if (retornoNfe.getCStat().equals(StatusEnum.LOTE_EM_PROCESSAMENTO.getCodigo())) {
                        System.out.println("INFO: Lote Em Processamento, vai tentar novamente apos 1 Segundo.");
                        Thread.sleep(1000);
                        tentativa++;
                    } else {
                        break;
                    }
                }

                RetornoUtil.validaAssincrono(retornoNfe);
                System.out.println();
                System.out.println("# Status: " + retornoNfe.getProtNFe().get(0).getInfProt().getCStat() + " - " + retornoNfe.getProtNFe().get(0).getInfProt().getXMotivo());
                System.out.println("# Protocolo: " + retornoNfe.getProtNFe().get(0).getInfProt().getNProt());
                System.out.println("# XML Final: " + XmlNfeUtil.criaNfeProc(enviNFe, retornoNfe.getProtNFe().get(0)));

            } else {
                //Se for else o Retorno é Sincrono

                //Valida Retorno Sincrono
                RetornoUtil.validaSincrono(retorno);
                System.out.println();
                System.out.println("# Status: " + retorno.getProtNFe().getInfProt().getCStat() + " - " + retorno.getProtNFe().getInfProt().getXMotivo());
                System.out.println("# Protocolo: " + retorno.getProtNFe().getInfProt().getNProt());
                System.out.println("# Xml Final :" + XmlNfeUtil.criaNfeProc(enviNFe, retorno.getProtNFe()));
            }
        } catch (NfeException e) {
            e.printStackTrace(System.err);
            LOG.log(Level.ALL, "Erro na geração do XML", e);
        } catch (CertificadoException e) {
            e.printStackTrace(System.err);
            LOG.log(Level.ALL, "Erro com o certificado", e);
        } catch (IOException e) {
            e.printStackTrace(System.err);
            LOG.log(Level.ALL, "Erro com rede ou arquivo", e);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace(System.err);
            LOG.log(Level.ALL, "Erro de criptografia", e);
        } catch (InterruptedException e) {
            e.printStackTrace(System.err);
            LOG.log(Level.ALL, "Erro de threads", e);
        } catch (JAXBException e) {
            e.printStackTrace(System.err);
            LOG.log(Level.ALL, "Erro com o JSON/XML", e);
        }
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
    private static Ide preencheIde(ConfiguracoesNfe config, String cnf, int numeroNFCe, String tipoEmissao, String modelo, int serie, String cDv, LocalDateTime dataEmissao, InvoiceModel invoice) throws NfeException {
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
    private static Emit preencheEmitente(ConfiguracoesNfe config, String cnpj, InvoiceModel invoice) {
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
    private static Dest preencheDestinatario(InvoiceModel invoice) {
        BuyerModel buyer = invoice.getBuyer();
        Dest dest = new Dest();
        if (99999999999L < buyer.getFederalTaxNumber()) {
            dest.setCNPJ(CNPJ_FORMAT.format(buyer.getFederalTaxNumber()));
        } else {
            dest.setCPF(CPF_FORMAT.format(buyer.getFederalTaxNumber()));
        }
        dest.setXNome(buyer.getName());

        TEndereco enderDest = new TEndereco();
        enderDest.setXLgr(invoice.getBuyer().getAddress().getDistrict());
        enderDest.setNro(invoice.getBuyer().getAddress().getNumber());
        enderDest.setXBairro(invoice.getBuyer().getAddress().getCountry());
        enderDest.setCMun(invoice.getBuyer().getAddress().getCodMunicipioIbge());
        enderDest.setXMun(invoice.getBuyer().getAddress().getMunicipioNome());
        enderDest.setUF(TUf.valueOf(invoice.getBuyer().getAddress().getState()));
        enderDest.setCEP(invoice.getBuyer().getAddress().getPostalCode());
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
    private static List<Det> preencheDet(InvoiceModel invoice) {

        //O Preenchimento deve ser feito por produto, Então deve ocorrer uma LIsta
        List<Det> listDeDet = new ArrayList<>();

        List<ItemModel> itemInvoice = invoice.getItems();
        Integer numeroItem = 1;
        for (ItemModel item : itemInvoice) {
            Det det = new Det();

            //O numero do Item deve seguir uma sequencia
            det.setNItem(String.valueOf(numeroItem));

            // Preenche dados do Produto
            det.setProd(preencheProduto(item, invoice));

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
    private static Prod preencheProduto(ItemModel item, InvoiceModel invoice) {
        Prod prod = new Prod();
        prod.setCProd(item.getCode());
        prod.setCEAN(item.getCodeGTIN());
        prod.setXProd(item.getDescription());
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
        prod.setVProd(DECIMAL_FORMAT.format(item.getUnitAmount()));
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
    private static Imposto preencheImposto(ItemModel item) {
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
    private static Total preencheTotal(TotalsModel totals) {
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
    private static Transp preencheTransporte() {
        Transp transp = new Transp();
        transp.setModFrete("9"); // Sem frete, comum para nfces
        return transp;
    }

    /**
     * Preenche dados Pagamento
     *
     * @return
     */
    private static Pag preenchePag(InvoiceModel invoice, TotalsModel totals) {
        Pag pag = new Pag();
        Pag.DetPag detPag = new Pag.DetPag();
        detPag.setTPag(invoice.getModoPagamento());
        detPag.setVPag(totals.getvNf());
        detPag.setXPag(invoice.getModoDePagamentoDescricao());
        pag.getDetPag().add(detPag);

        return pag;
    }

    /**
     * Preenche QRCode
     *
     * @param enviNFe
     * @param config
     * @param idToken
     * @param csc
     * @return
     * @throws NfeException
     * @throws NoSuchAlgorithmException
     */
    private static String preencheQRCode(TEnviNFe enviNFe, ConfiguracoesNfe config, String idToken, String csc) throws NfeException, NoSuchAlgorithmException {

        //QRCODE EMISAO ONLINE
        return NFCeUtil.getCodeQRCode(
                enviNFe.getNFe().get(0).getInfNFe().getId().substring(3),
                config.getAmbiente().getCodigo(),
                idToken,
                csc,
                WebServiceUtil.getUrl(config, DocumentoEnum.NFCE, ServicosEnum.URL_QRCODE));

        //QRCODE EMISSAO OFFLINE
//        return NFCeUtil.getCodeQRCodeContingencia(
//                enviNFe.getNFe().get(0).getInfNFe().getId().substring(3),
//                config.getAmbiente().getCodigo(),
//                enviNFe.getNFe().get(0).getInfNFe().getIde().getDhEmi(),
//                enviNFe.getNFe().get(0).getInfNFe().getTotal().getICMSTot().getVNF(),
//                Base64.getEncoder().encodeToString(enviNFe.getNFe().get(0).getSignature().getSignedInfo().getReference().getDigestValue()),
//                idToken,
//                csc,
//                WebServiceUtil.getUrl(config, DocumentoEnum.NFCE, ServicosEnum.URL_QRCODE));
    }

}