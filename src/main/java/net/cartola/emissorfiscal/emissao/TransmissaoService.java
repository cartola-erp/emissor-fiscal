package net.cartola.emissorfiscal.emissao;

import br.com.swconsultoria.certificado.exception.CertificadoException;
import br.com.swconsultoria.nfe.Nfe;
import br.com.swconsultoria.nfe.dom.ConfiguracoesNfe;
import br.com.swconsultoria.nfe.dom.enuns.DocumentoEnum;
import br.com.swconsultoria.nfe.dom.enuns.ServicosEnum;
import br.com.swconsultoria.nfe.dom.enuns.StatusEnum;
import br.com.swconsultoria.nfe.exception.NfeException;
import br.com.swconsultoria.nfe.schema_4.enviNFe.TEnviNFe;
import br.com.swconsultoria.nfe.schema_4.enviNFe.TNFe;
import br.com.swconsultoria.nfe.schema_4.enviNFe.TRetEnviNFe;
import br.com.swconsultoria.nfe.schema_4.retConsReciNFe.TRetConsReciNFe;
import br.com.swconsultoria.nfe.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class TransmissaoService {

    private static final Logger LOG = Logger.getLogger(TransmissaoService.class.getName());

    @Autowired
    private CertificadoPfxModel certificadoPfx;

    private ConfiguracoesNfe config;

    public TransmissaoService() {
        try {
            config = ConfigNfceService.iniciaConfiguracoes();
        } catch (CertificadoException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public TRetConsReciNFe enviar(TNFe nfe) {
        TRetConsReciNFe retornoNfe = null;
        try {
            // Monta EnviNfe
            TEnviNFe enviNFe = new TEnviNFe();
            enviNFe.setVersao(ConstantesUtil.VERSAO.NFE);
            String idLote = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmmssS"));
            enviNFe.setIdLote(idLote);
            enviNFe.setIndSinc("1");
            enviNFe.getNFe().add(nfe);

            // Monta e Assina o XML
            enviNFe = Nfe.montaNfe(config, enviNFe, true);

            //Informe o idToken
            String idToken = certificadoPfx.getTokenSefaz();

            //Informe o CSC da NFCe
            String csc = certificadoPfx.getCscSefaz();

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
//        } catch (IOException e) {
//            e.printStackTrace(System.err);
//            LOG.log(Level.ALL, "Erro com rede ou arquivo", e);
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
        return retornoNfe;
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
    private String preencheQRCode(TEnviNFe enviNFe, ConfiguracoesNfe config, String idToken, String csc) throws NfeException, NoSuchAlgorithmException {

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
