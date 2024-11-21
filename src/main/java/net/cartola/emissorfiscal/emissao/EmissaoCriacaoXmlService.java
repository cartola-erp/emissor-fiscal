/*
package net.cartola.emissorfiscal.emissao;

import autogeral.emissorfiscal.vo.BuyerModel;
import autogeral.emissorfiscal.vo.EmitModel;
import autogeral.emissorfiscal.vo.InvoiceModel;
import autogeral.emissorfiscal.vo.ItemModel;
import br.com.swconsultoria.certificado.Certificado;
import br.com.swconsultoria.certificado.CertificadoService;
import br.com.swconsultoria.nfe.Nfe;
import br.com.swconsultoria.nfe.dom.ConfiguracoesNfe;
import br.com.swconsultoria.nfe.dom.enuns.*;
import br.com.swconsultoria.nfe.exception.NfeException;
import br.com.swconsultoria.nfe.schema_4.enviNFe.*;
import br.com.swconsultoria.nfe.schema_4.retConsReciNFe.TRetConsReciNFe;
import br.com.swconsultoria.nfe.util.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.cartola.emissorfiscal.recalculo.RecalculoService;
import net.cartola.emissorfiscal.tributacao.estadual.TributacaoEstadual;
import net.cartola.emissorfiscal.tributacao.federal.TributacaoFederal;
import org.springframework.http.codec.xml.Jaxb2XmlDecoder;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import java.io.File;
import java.io.StringWriter;
import java.nio.file.Files;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.*;

import static br.com.swconsultoria.nfe.dom.enuns.DocumentoEnum.NFE;

*/
/**
 * Essa classe devera preencher o xml da nota fiscal eletronica ou da nota nota fiscal consumidor eletronico (NFCE)
 * Essa e a etapa 3 do processo de emissao do documento fiscal
 *//*


@Service
public class EmissaoCriacaoXmlService {

    public void criaXmlNota(InvoiceModel invoice) {

        String codigoOperacao = invoice.getOperationType();
        String codUfDestino = invoice.getBuyer().getAddress().getCountry();
        String codUfOrigem = "SP";
        String codloja = invoice.getCodigoLoja();
        String finalidade = invoice.getFinalidadeNota();


        //Emitente e dest infos
        EmitModel emitente = invoice.getEmitente();
        BuyerModel destinatario = invoice.getBuyer();

        //Produtos infos
        List<ItemModel> itens =  invoice.getItems();

        Map<String, String> resultado = new HashMap<>();
        try {
            File file = new File("/Users/wesleymendonca/.DBF/dist/20250220A1.pfx");
            byte[] bytes = Files.readAllBytes(file.toPath());
            String senha = "V552289";

            Certificado certificado = CertificadoService.certificadoPfxBytes(bytes, senha);
            ConfiguracoesNfe config = ConfiguracoesNfe.criarConfiguracoes(EstadosEnum.SP, AmbienteEnum.HOMOLOGACAO, certificado, "/Users/wesleymendonca/Documents/schemas");
            config.setEncode("UTF-8");

            //Informe o Numero da NFCe
            int numeroNFCe = invoice.getNumber();

            //Informe o CNPJ do Emitente da NFCe
            String cnpj = invoice.getEmitente().getCpnjEmitente();

            //Informe a data de Emissao da NFCe
            LocalDateTime dataEmissao = invoice.getOperationOn();

            //Informe o cnf da NFCe com 8 digitos
            String cnf = ChaveUtil.completarComZerosAEsquerda(String.valueOf(numeroNFCe), 8);

            //Informe o modelo da NFCe
            String modelo;
            if(!invoice.getModeloNota().equals("55")) {
                modelo = NFE.getModelo();
            }
            modelo = DocumentoEnum.NFCE.getModelo();

            //Informe a Serie da NFCe
            int serie = invoice.getSerie();

            //Informe o tipo de Emissao da NFCe
            String tipoEmissao = "1";

            //Informe o idToken
            String idToken = "000001";

            //Informe o CSC da NFCe
            String csc = "536fd53c-9091-4b0f-8f5b-bddf416b3bea";

            // MontaChave a NFCe
            ChaveUtil chaveUtil = new ChaveUtil(config.getEstado(), cnpj, modelo, serie, numeroNFCe, tipoEmissao, cnf, dataEmissao);
            String chave = chaveUtil.getChaveNF();
            String cdv = chaveUtil.getDigitoVerificador();

            TNFe.InfNFe infNFe = new TNFe.InfNFe();
            infNFe.setId(chave);
            infNFe.setVersao(ConstantesUtil.VERSAO.NFE);

            //Preenche IDE
            infNFe.setIde(preencheIde(config, cnf, numeroNFCe, tipoEmissao, modelo, serie, cdv, dataEmissao, codigoOperacao, codUfOrigem, codUfDestino, codloja, finalidade, emitente));

            //Preenche Emitente
            infNFe.setEmit(preencheEmitente(config, cnpj, emitente));

            //Preenche o Destinatario
            infNFe.setDest(preencheDestinatario(destinatario));

            //Preenche os dados do Produto da NFCe e adiciona a Lista
            infNFe.getDet().addAll(preencheDet(itens));

            //Preenche totais da NFCe
            infNFe.setTotal(preencheTotal());

            //Preenche os dados de Transporte
            infNFe.setTransp(preencheTransporte());

            // Preenche dados Pagamento
            infNFe.setPag(preenchePag());

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

                //Define Numero de tentativas que irá tentar a Consulta
                while (tentativa < 15) {
                    retornoNfe = Nfe.consultaRecibo(config, recibo, NFE);
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
        return resultado;
    }

    */
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
     *//*

    private static TNFe.InfNFe.Ide preencheIde(ConfiguracoesNfe config, String cnf, int numeroNFCe, String tipoEmissao, String modelo, int serie, String cDv, LocalDateTime dataEmissao, String operacaoCodigo, String ufOrigem, String ufDest, String codLoja, String finalidade, EmitModel emitente) throws NfeException {
        TNFe.InfNFe.Ide ide = new TNFe.InfNFe.Ide();
        ide.setCUF(config.getEstado().getCodigoUF());
        ide.setCNF(cnf);

        if(!modelo.equals("NFE")) {
            ide.setNatOp("NOTA FISCAL ELETRONICA");
        }
        ide.setNatOp("NOTA FISCAL CONSUMIDOR ELETRONICA");

        ide.setMod(modelo);
        ide.setSerie(String.valueOf(serie));

        ide.setNNF(String.valueOf(numeroNFCe));
        ide.setDhEmi(XmlNfeUtil.dataNfe(dataEmissao));

        ide.setTpNF("1"); // Sempre vai ser 1 pois a nfce sempre vai ser uma nota de saida
        ide.setIdDest("1"); // sempre vai ser 1 pois a nfce sempre vai ser uma nota utilizada dentro do proprio estado
        ide.setCMunFG(emitente.getCodIbgeMunicipioEmitente());
        ide.setTpImp("4"); // padrao para nfce
        ide.setTpEmis(tipoEmissao);
        ide.setCDV(cDv);
        ide.setTpAmb(config.getAmbiente().getCodigo());
        ide.setFinNFe(finalidade);
        ide.setIndFinal("1"); // Padrao
        ide.setIndPres("0"); // Padrao
        ide.setProcEmi("0"); // Padrao
        ide.setVerProc("1.0"); // Padrao

        return ide;
    }

    */
/**
     * Preenche o Emitente da NFCe
     * @param config
     * @param cnpj
     * @return
     *//*

    private static TNFe.InfNFe.Emit preencheEmitente(ConfiguracoesNfe config, String cnpj, EmitModel emitente) {
        TNFe.InfNFe.Emit emit = new TNFe.InfNFe.Emit();
        emit.setCNPJ(cnpj);
        emit.setXNome(emitente.getNomeEmitente());

        TEnderEmi enderEmit = new TEnderEmi();
        enderEmit.setXLgr(emitente.getLogradouroEmitente());
        enderEmit.setNro(emitente.getNumeroLogradouroEmitente());
        enderEmit.setXCpl(emitente.getComplementoEdenrecoEmitente());
        enderEmit.setXBairro(emitente.getBairroEmitente());
        enderEmit.setCMun(emitente.getMunicipioEmitente());
        enderEmit.setXMun(emitente.getBairroEmitente());
        enderEmit.setUF(TUfEmi.valueOf(config.getEstado().toString()));
        enderEmit.setCEP(emitente.getBairroEmitente());
        enderEmit.setCPais("1058"); // Padrao
        enderEmit.setXPais("Brasil"); // Padrao
        enderEmit.setFone(""); // Emitente tem telefone ?
        emit.setEnderEmit(enderEmit);

        emit.setIE(emitente.getInscricaoEstadual());
        emit.setCRT(emitente.getRegimeTributario());

        return emit;
    }

    */
/**
     * Preenche o Destinatario da NFCe
     * @return
     *//*

    private static TNFe.InfNFe.Dest preencheDestinatario(BuyerModel destinatario) {
        TNFe.InfNFe.Dest dest = new TNFe.InfNFe.Dest();
        Long cnpjDestinatario = destinatario.getFederalTaxNumber();
        String cnpjDestinatarioString = cnpjDestinatario.toString();
        dest.setCNPJ(cnpjDestinatarioString);
        dest.setXNome(destinatario.getName());

        TEndereco enderDest = new TEndereco();
        enderDest.setXLgr(destinatario.getAddress().getDistrict());
        enderDest.setNro(destinatario.getAddress().getNumber());
        enderDest.setXBairro(destinatario.getAddress().getCountry());
        enderDest.setCMun(destinatario.getAddress().getCodMunicipioIbge());
        enderDest.setXMun(destinatario.getAddress().getMunicipioNome());
        enderDest.setUF(TUf.valueOf(destinatario.getAddress().getAdditionalInformation()));
        enderDest.setCEP(destinatario.getAddress().getPostalCode());
        enderDest.setCPais("1058"); // Padrao
        enderDest.setXPais("Brasil"); // Padrao
        enderDest.setFone(""); // Destinatario tem telefone ?
        dest.setEnderDest(enderDest);
        dest.setEmail(""); // Destinatario tem email ?
        dest.setIndIEDest("9"); // Padrao para consumidor final
        return dest;
    }

    */
/**
     * Preenche Det NFCe
     *//*

    private static List<TNFe.InfNFe.Det> preencheDet(List<ItemModel> itens) {

        //O Preenchimento deve ser feito por produto, Então deve ocorrer uma LIsta
        List<TNFe.InfNFe.Det> detalhes = new ArrayList<>();

        //O numero do Item deve seguir uma sequencia
        int numeroItem = 1;
        for(ItemModel item : itens){
            TNFe.InfNFe.Det det = new TNFe.InfNFe.Det();
            det.setNItem(String.valueOf(numeroItem));

            // Preenche dados do Produto
            det.setProd(preencheProduto(item));

            //Preenche dados do Imposto
            det.setImposto(preencheImposto(item));

            detalhes.add(det);

            numeroItem++;
        }

        //Retorna a Lista de Det
        return detalhes;
    }

    */
/**
     * Preenche dados do Produto
     * @return
     *//*

    private static TNFe.InfNFe.Det.Prod preencheProduto(ItemModel item) {
        TNFe.InfNFe.Det.Prod prod = new TNFe.InfNFe.Det.Prod();
        prod.setCProd(item.getCode());
        prod.setCEAN(item.getCodeGTIN());
        prod.setXProd(item.getDescription());
        prod.setNCM(item.getNcm());
        prod.setCEST(item.getCest());
        //prod.setIndEscala("S");
        prod.setCFOP(String.valueOf(item.getCfop()));
        prod.setUCom(item.getUnit());
        prod.setQCom(String.valueOf(item.getQuantity()));
        prod.setVUnCom(String.valueOf(item.getUnitAmount()));
        prod.setVProd(String.valueOf(item.getQuantity() * item.getUnitAmount()));
        prod.setCEANTrib(item.getCodeGTIN());
        prod.setUTrib(item.getUnit());
        prod.setQTrib(String.valueOf(item.getQuantity()));
        prod.setVUnTrib(String.valueOf(item.getQuantity()));
        prod.setIndTot("1");

        return prod;
    }

    */
/**
     * Preenche dados do Imposto da NFCe
     * @return
     *//*

    private static TNFe.InfNFe.Det.Imposto preencheImposto(ItemModel item) {
        TNFe.InfNFe.Det.Imposto imposto = new TNFe.InfNFe.Det.Imposto();
        TNFe.InfNFe.Det.Imposto.ICMS icms = new TNFe.InfNFe.Det.Imposto.ICMS();
        TNFe.InfNFe.Det.Imposto.ICMS.ICMS00 icms00 = new TNFe.InfNFe.Det.Imposto.ICMS.ICMS00();
        RecalculoService calcularImposto = new RecalculoService();

        List<TributacaoEstadual> tributacaoEstadual = calcularImposto.TributacaoEstadualForNfce(item);
        if(!tributacaoEstadual.isEmpty()){
            for(TributacaoEstadual tributacaoEstadualSet : tributacaoEstadual){
                icms00.setCST(String.valueOf(tributacaoEstadualSet.getIcmsCst()));
                icms00.setOrig(item.getAdditionalInformation());
                icms00.setModBC("3");
                icms00.setVBC(String.valueOf(tributacaoEstadualSet.getIcmsBase()));
                icms00.setPICMS(String.valueOf(tributacaoEstadualSet.getIcmsAliquota()));

                Integer vbc = Integer.parseInt(icms00.getVBC());
                Integer picms = Integer.parseInt(icms00.getPICMS());
                icms00.setVICMS(String.valueOf((vbc * picms) / 100));
            }
        }
        icms.setICMS00(icms00);


        TNFe.InfNFe.Det.Imposto.PIS pis = new TNFe.InfNFe.Det.Imposto.PIS();
        TNFe.InfNFe.Det.Imposto.PIS.PISAliq pisAliq = new TNFe.InfNFe.Det.Imposto.PIS.PISAliq();

        List<TributacaoFederal> tributacaoFederal = calcularImposto.TributacaoFederalForNfce(item);
        if(!tributacaoFederal.isEmpty()){
            for(TributacaoFederal tributacaoFederalSet : tributacaoFederal){
                pisAliq.setCST(String.valueOf(tributacaoFederalSet.getPisCst()));
                pisAliq.setVBC(String.valueOf(tributacaoFederalSet.getPisBase()));
                pisAliq.setPPIS(String.valueOf(tributacaoFederalSet.getPisAliquota()));

                Integer vbc = Integer.parseInt(pisAliq.getVBC());
                Integer picms = Integer.parseInt(pisAliq.getPPIS());
                pisAliq.setVPIS(String.valueOf((vbc * picms) / 100));
            }
        }
        pis.setPISAliq(pisAliq);

        TNFe.InfNFe.Det.Imposto.COFINS cofins = new TNFe.InfNFe.Det.Imposto.COFINS();
        TNFe.InfNFe.Det.Imposto.COFINS.COFINSAliq cofinsAliq = new TNFe.InfNFe.Det.Imposto.COFINS.COFINSAliq();
        for(TributacaoFederal tributacaoFederalSet : tributacaoFederal){
            cofinsAliq.setCST(String.valueOf(tributacaoFederalSet.getCofinsCst()));
            cofinsAliq.setVBC(String.valueOf(tributacaoFederalSet.getCofinsBase()));
            cofinsAliq.setPCOFINS(String.valueOf(tributacaoFederalSet.getCofinsAliquota()));

            Integer vbc = Integer.parseInt(cofinsAliq.getVBC());
            Integer picms = Integer.parseInt(cofinsAliq.getPCOFINS());
            pisAliq.setVPIS(String.valueOf((vbc * picms) / 100));
        }
        cofins.setCOFINSAliq(cofinsAliq);

        JAXBElement<TNFe.InfNFe.Det.Imposto.ICMS> icmsElement = new JAXBElement<TNFe.InfNFe.Det.Imposto.ICMS>(new QName("ICMS"), TNFe.InfNFe.Det.Imposto.ICMS.class, icms);
        imposto.getContent().add(icmsElement);

        JAXBElement<TNFe.InfNFe.Det.Imposto.PIS> pisElement = new JAXBElement<TNFe.InfNFe.Det.Imposto.PIS>(new QName("PIS"), TNFe.InfNFe.Det.Imposto.PIS.class, pis);
        imposto.getContent().add(pisElement);

        JAXBElement<TNFe.InfNFe.Det.Imposto.COFINS> cofinsElement = new JAXBElement<TNFe.InfNFe.Det.Imposto.COFINS>(new QName("COFINS"), TNFe.InfNFe.Det.Imposto.COFINS.class, cofins);
        imposto.getContent().add(cofinsElement);

        return imposto;
    }

    */
/**
     * Prenche Total NFCe
     * @return
     *//*

    private static TNFe.InfNFe.Total preencheTotal() {
        TNFe.InfNFe.Total total = new TNFe.InfNFe.Total();
        TNFe.InfNFe.Total.ICMSTot icmstot = new TNFe.InfNFe.Total.ICMSTot();
        icmstot.setVBC("13.00");
        icmstot.setVICMS();
        icmstot.setVICMSDeson("0.00");
        //icmstot.setVFCP("0.00");
        //icmstot.setVFCPST("0.00");
        icmstot.setVFCPSTRet("0.00");
        icmstot.setVBCST("0.00");
        icmstot.setVST("0.00");
        icmstot.setVProd("13.00"); --
                icmstot.setVFrete("0.00"); --
                icmstot.setVSeg("0.00"); --
                icmstot.setVDesc("0.00"); --
                //icmstot.setVII("0.00");
                //icmstot.setVIPI("0.00");
                //icmstot.setVIPIDevol("0.00");
                icmstot.setVPIS("0.21");
        icmstot.setVCOFINS("0.99");
        icmstot.setVOutro("0.00");
        icmstot.setVNF("13.00");
        total.setICMSTot(icmstot);

        return total;
    }

    */
/**
     * Preenche Transporte
     * @return
     *//*

    private static TNFe.InfNFe.Transp preencheTransporte() {
        TNFe.InfNFe.Transp transp = new TNFe.InfNFe.Transp();
        transp.setModFrete("9");
        return transp;
    }

    */
/**
     * Preenche dados Pagamento
     * @return
     *//*

    private static TNFe.InfNFe.Pag preenchePag() {
        TNFe.InfNFe.Pag pag = new TNFe.InfNFe.Pag();
        TNFe.InfNFe.Pag.DetPag detPag = new TNFe.InfNFe.Pag.DetPag();
        detPag.setTPag("01");
        detPag.setVPag("13.00");
        pag.getDetPag().add(detPag);

        return pag;
    }

    */
/**
     * Preenche QRCode
     * @param enviNFe
     * @param config
     * @param idToken
     * @param csc
     * @return
     * @throws NfeException
     * @throws NoSuchAlgorithmException
     *//*

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
*/
