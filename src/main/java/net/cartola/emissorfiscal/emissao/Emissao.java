package net.cartola.emissorfiscal.emissao;


import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Base64;
import java.util.logging.Logger;

import br.com.autogeral.emissorfiscal.response.vo.InvoiceResponse;
import br.com.autogeral.emissorfiscal.vo.InvoiceModel;
import br.com.swconsultoria.impressao.model.Impressao;
import br.com.swconsultoria.impressao.service.ImpressaoService;
import br.com.swconsultoria.impressao.util.ImpressaoUtil;
import br.com.swconsultoria.nfe.exception.NfeException;
import br.com.swconsultoria.nfe.schema_4.enviNFe.TNFe;
import br.com.swconsultoria.nfe.schema_4.retConsReciNFe.TRetConsReciNFe;
import br.com.swconsultoria.nfe.util.XmlNfeUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import net.sf.jasperreports.engine.JRException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

@Service
public class Emissao {

    private static final Logger LOG = Logger.getLogger(Emissao.class.getName());

    @Autowired
    private EmissaoPrenchimentoDadosFiscaisService emissaoPrenchimentoDadosFiscaisService;

    @Autowired
    private EmissaoCriacaoXmlService emissaoCriacaoXmlService;

    @Autowired
    private TransmissaoService transmissaoService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "hello")
    public void processMessageHello(String content) {
        System.out.println(content);
    }

    @RabbitListener(queues = "emissorFiscal.emissao.emitir")
    public void processMessageEmitir(String content) {
        InvoiceModel invoice = new InvoiceModel();

        if (!content.isEmpty()) {
            ObjectMapper om = new ObjectMapper();
            om.registerModule(new JavaTimeModule());
            try {
                invoice = om.readValue(content, InvoiceModel.class);
            } catch (JsonProcessingException e) {
                LOG.severe("Erro no processamento da mensagem: " + e.getMessage()); ;
            }
        }

        ResultadoUtil retornaErrosOuTributacaoPreenchida = emissaoPrenchimentoDadosFiscaisService.preencherDadosFiscais(invoice);

        if (!retornaErrosOuTributacaoPreenchida.getErros().isEmpty()) {
            System.out.println("Ocorreram erros: " + retornaErrosOuTributacaoPreenchida.getErros());
            return;
        }

        InvoiceModel invoiceTratada = retornaErrosOuTributacaoPreenchida.getInvoice();
        try {
            TNFe nfe = emissaoCriacaoXmlService.montaXmlNota(invoiceTratada);
            TRetConsReciNFe recibo = transmissaoService.enviar(nfe);
            String respondeQueue = invoice.getReponseQueue();
            if(null != respondeQueue && !respondeQueue.isEmpty()) {
                InvoiceResponse invoiceResponse = new InvoiceResponse();
                TNFe.InfNFe.Ide ide = nfe.getInfNFe().getIde();
                String chave = nfe.getInfNFe().getId();
                invoiceResponse.setChave(nfe.getInfNFe().getId());
                invoiceResponse.setSerie(Integer.parseInt(ide.getSerie()));
                invoiceResponse.setNumero(Integer.parseInt(ide.getNNF()));
                String xml = XmlNfeUtil.objectToXml(recibo, Charset.forName("UTF-8"));

                File file = new File(chave + ".xml");
                try {
                    Files.write(file.toPath(), xml.getBytes(StandardCharsets.UTF_8), java.nio.file.StandardOpenOption.CREATE, java.nio.file.StandardOpenOption.APPEND);
                } catch (IOException e) {
                    String msg = String.format("Erro ao gravar o arquivo %s , mensagem %s", file.getAbsolutePath(), e.getMessage());
                    LOG.severe(msg);
                    e.printStackTrace(System.err);
                }
                invoiceResponse.setXml(xml);
                if(invoice.isIncludePdf()) {
                    try {
                        gerarImpressao(invoiceResponse, xml);
                        file = new File(chave + ".pdf");
                        Files.write(file.toPath(), xml.getBytes(StandardCharsets.UTF_8), java.nio.file.StandardOpenOption.CREATE, java.nio.file.StandardOpenOption.APPEND);
                    } catch (IOException e) {
                        String msg = String.format("Erro gerar o arquivo %s , mensagem %s", file.getAbsolutePath(), e.getMessage());
                        LOG.severe(msg);
                        e.printStackTrace(System.err);
                    } catch (JRException e) {
                        String msg = String.format("Erro gerar a impressão %s , mensagem %s", file.getAbsolutePath(), e.getMessage());
                        LOG.severe(msg);
                        e.printStackTrace(System.err);
                    } catch (ParserConfigurationException e) {
                        String msg = String.format("Erro de configuração para a impressão %s , mensagem %s", file.getAbsolutePath(), e.getMessage());
                        LOG.severe(msg);
                        e.printStackTrace(System.err);
                    } catch (SAXException e) {
                        String msg = String.format("Erro no tratamento do XML para o arquivo %s , mensagem %s", file.getAbsolutePath(), e.getMessage());
                        LOG.severe(msg);
                        e.printStackTrace(System.err);
                    }
                }
                rabbitTemplate.convertAndSend(respondeQueue, invoiceResponse);
            }
        } catch (NfeException e) {
            LOG.severe("Erro no processamento da mensagem: " + e.getMessage()); ;
        } catch (JAXBException e) {
            LOG.severe("Erro na conversao do XML : " + e.getMessage()); ;
        }
    }

    private void gerarImpressao(InvoiceResponse invoiceResponse, String xml) throws JRException, ParserConfigurationException, IOException, SAXException {
        Impressao impressao = ImpressaoUtil.impressaoPadraoNFe(xml);

        //Faz a impressão em pdf retornando o byte[]
        byte[] bytes = ImpressaoService.impressaoPdfByte(impressao);
        byte[] pdfBase64 = Base64.getEncoder().encode(bytes);
        String pdf = new String(pdfBase64, StandardCharsets.UTF_8);
        invoiceResponse.setPdf(pdf);
    }


}
