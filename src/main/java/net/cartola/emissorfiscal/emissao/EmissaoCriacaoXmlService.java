package net.cartola.emissorfiscal.emissao;

import autogeral.emissorfiscal.vo.InvoiceModel;
import autogeral.emissorfiscal.vo.ItemModel;
import autogeral.emissorfiscal.vo.TotalsModel;
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
import javax.xml.namespace.QName;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Samuel Oliveira
 *
 */

@Service
public class EmissaoCriacaoXmlService {

    @Autowired
    private CertificadoPfxModel certificadoPfx;

    public void montaXmlNota(InvoiceModel invoice) {

        try {
            // Inicia As Configurações - ver https://github.com/Samuel-Oliveira/Java_NFe/wiki/1-:-Configuracoes
            ConfiguracoesNfe config = NfceEmissaoConfig.iniciaConfiguracoes();

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

            // Conversão do objeto enviNFe para JSON e exibição no console
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(enviNFe);
                System.out.println("# JSON da NFC-e:");
                System.out.println(json);
            } catch (Exception e) {
                System.err.println("Erro ao converter para JSON: " + e.getMessage());
            }

            // Monta e Assina o XML
            enviNFe = Nfe.montaNfe(config, enviNFe, true);

            //Monta QRCode
            String qrCode = preencheQRCode(enviNFe,config,idToken,csc);

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

        } catch (Exception e) {
            System.err.println();
            System.err.println("# Erro: " + e.getMessage());
        }

    }

    /**
     * Preenche o IDE
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
        ide.setCUF(invoice.getEmitente().getUfEmitente());
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
        enderEmit.setXMun(invoice.getEmitente().getCodIbgeMunicipioEmitente());
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
     * @return
     */
    private static Dest preencheDestinatario(InvoiceModel invoice) {
        Dest dest = new Dest();
        dest.setCNPJ(String.valueOf(invoice.getBuyer().getFederalTaxNumber()));
        dest.setXNome(invoice.getBuyer().getName());

        TEndereco enderDest = new TEndereco();
        enderDest.setXLgr(invoice.getBuyer().getAddress().getDistrict());
        enderDest.setNro(invoice.getBuyer().getAddress().getNumber());
        enderDest.setXBairro(invoice.getBuyer().getAddress().getCountry());
        enderDest.setCMun(invoice.getBuyer().getAddress().getCodMunicipioIbge());
        enderDest.setXMun(invoice.getBuyer().getAddress().getMunicipioNome());
        enderDest.setUF(TUf.valueOf(invoice.getBuyer().getAddress().getAdditionalInformation()));
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
        for(ItemModel item : itemInvoice){
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
     * @return
     */
    private static Prod preencheProduto(ItemModel item, InvoiceModel invoice) {
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        Prod prod = new Prod();
        prod.setCProd(item.getCode());
        prod.setCEAN(item.getCodeGTIN());
        prod.setXProd(item.getDescription());
        prod.setNCM(item.getNcm());
        prod.setCEST(item.getCest());
        //prod.setIndEscala("S");
        prod.setCFOP(item.getTax().getCfop());
        prod.setUCom(item.getUnit());
        prod.setQCom(String.valueOf(item.getQuantity()));
        prod.setVUnCom(String.valueOf(item.getUnitAmount()));
        prod.setVProd(decimalFormat.format(item.getUnitAmount()));
        prod.setCEANTrib(item.getCodeGTIN());
        prod.setUTrib(item.getUnit());
        prod.setQTrib(String.valueOf(item.getQuantity()));
        prod.setVUnTrib(String.valueOf(item.getUnitAmount()));
        prod.setIndTot("1");

        return prod;
    }
    /**
     * Preenche dados do Imposto da NFCe
     * @return
     */
    private static Imposto preencheImposto(ItemModel item) {
        Imposto imposto = new Imposto();
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        ICMS icms = new ICMS();

        if(item.getTax().getIcms().getCst().equals("60")){
            ICMS.ICMS60 icms60 = new ICMS.ICMS60();
            icms60.setOrig("0");
            icms60.setCST(item.getTax().getIcms().getCst());
            icms60.setVBCSTRet(decimalFormat.format(item.getTotalAmount()));
            icms60.setPST(decimalFormat.format(item.getTax().getIcms().getRate()));

            BigDecimal valorNf = new BigDecimal(String.valueOf(item.getTotalAmount()));
            BigDecimal aliqBig = new BigDecimal(String.valueOf(item.getTax().getIcms().getRate()));

            BigDecimal valorf = valorNf.multiply(aliqBig).setScale(2, RoundingMode.HALF_UP);

            icms60.setVICMSSubstituto(decimalFormat.format(valorf));
            icms60.setVICMSSTRet(decimalFormat.format(valorf));

            icms.setICMS60(icms60);
        }

        if(item.getTax().getIcms().equals("00")){
            ICMS.ICMS00 icms00 = new ICMS.ICMS00();
            icms00.setOrig("0"); // Produto nacional ou importado ?
            icms00.setCST(item.getTax().getIcms().getCst());
            icms00.setModBC("3"); // valor da operacao
            icms00.setVBC(decimalFormat.format(item.getTotalAmount()));
            icms00.setPICMS(decimalFormat.format(item.getTax().getIcms().getRate()));

            double valorProd = item.getTotalAmount();
            BigDecimal valorProdFormat = new BigDecimal(valorProd);

            double aliqProd = item.getTax().getIcms().getRate();
            BigDecimal aliqProdFormat = new BigDecimal(aliqProd);

            BigDecimal valorVIcms = valorProdFormat.multiply(aliqProdFormat).setScale(2, RoundingMode.HALF_UP);

            icms00.setVICMS(decimalFormat.format(valorVIcms));

            icms.setICMS00(icms00);
        }

        PIS pis = new PIS();
        PISAliq pisAliq = new PISAliq();

        String cst = item.getTax().getPis().getCst();

        // Verifica se o CST é um número de um dígito e adiciona o zero à esquerda se necessário
        String cstFormatted = cst.length() == 1 ? String.format("%02d", Integer.parseInt(cst)) : cst;

        pisAliq.setCST(cstFormatted);

        pisAliq.setVBC(decimalFormat.format(item.getTax().getPis().getBaseTax()));
        pisAliq.setPPIS(decimalFormat.format(item.getTax().getPis().getRate()));
        pisAliq.setVPIS(decimalFormat.format(item.getTax().getPis().getAmount()));
        pis.setPISAliq(pisAliq);

        COFINS cofins = new COFINS();
        COFINSAliq cofinsAliq = new COFINSAliq();

        String cstCofins = item.getTax().getCofins().getCst();

        String cstFormattedCofins = cstCofins.length() == 1 ? String.format("%02d", Integer.parseInt(cst)) : cst;

        cofinsAliq.setCST(cstFormattedCofins);

        cofinsAliq.setVBC(decimalFormat.format(item.getTax().getCofins().getBaseTax()));
        cofinsAliq.setPCOFINS(decimalFormat.format(item.getTax().getCofins().getRate()));
        cofinsAliq.setVCOFINS(decimalFormat.format(item.getTax().getCofins().getAmount()));
        cofins.setCOFINSAliq(cofinsAliq);

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
        icmstot.setVPIS(String.valueOf(totals.getvPis()));
        icmstot.setVCOFINS(String.valueOf(totals.getvCofins()));
        icmstot.setVOutro(String.valueOf(totals.getvOutro()));
        icmstot.setVNF(String.valueOf(totals.getvNf()));
        icmstot.setVTotTrib(String.valueOf(totals.getvTotTrib()));
        total.setICMSTot(icmstot);

        return total;
    }

    /**
     * Preenche Transporte
     * @return
     */
    private static Transp preencheTransporte() {
        Transp transp = new Transp();
        transp.setModFrete("9"); // Sem frete, comum para nfces
        return transp;
    }

    /**
     * Preenche dados Pagamento
     * @return
     */
    private static Pag preenchePag(InvoiceModel invoice, TotalsModel totals) {
        Pag pag = new Pag();
        Pag.DetPag detPag = new Pag.DetPag();
        detPag.setTPag("0" + invoice.getModoPagamento());
        detPag.setVPag(totals.getvNf());
        detPag.setXPag(invoice.getModoDePagamentoDescricao());
        pag.getDetPag().add(detPag);

        return pag;
    }

    /**
     * Preenche QRCode
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
                WebServiceUtil.getUrl(config,DocumentoEnum.NFCE, ServicosEnum.URL_QRCODE));

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