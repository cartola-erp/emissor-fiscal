package net.cartola.emissorfiscal.emissao;


import java.nio.charset.Charset;
import java.util.logging.Logger;

import br.com.autogeral.emissorfiscal.response.vo.InvoiceResponse;
import br.com.autogeral.emissorfiscal.vo.InvoiceModel;
import br.com.swconsultoria.nfe.exception.NfeException;
import br.com.swconsultoria.nfe.schema_4.enviNFe.TNFe;
import br.com.swconsultoria.nfe.schema_4.retConsReciNFe.TRetConsReciNFe;
import br.com.swconsultoria.nfe.util.XmlNfeUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import net.cartola.emissorfiscal.produto.ProdutoAlteradoSpedApiController;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.apache.commons.lang3.CharSet;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;

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
            TNFe nfe = emissaoCriacaoXmlService.montaXmlNota(invoice);
            TRetConsReciNFe recibo = transmissaoService.enviar(nfe);
            String respondeQueue = invoice.getReponseQueue();
            if(null != respondeQueue && !respondeQueue.isEmpty()) {
                InvoiceResponse invoiceResponse = new InvoiceResponse();
                TNFe.InfNFe.Ide ide = nfe.getInfNFe().getIde();
                invoiceResponse.setChave(nfe.getInfNFe().getId());
                invoiceResponse.setSerie(Integer.parseInt(ide.getSerie()));
                invoiceResponse.setNumero(Integer.parseInt(ide.getNNF()));
                String xml = XmlNfeUtil.objectToXml(recibo, Charset.forName("UTF-8"));
                invoiceResponse.setXml(xml);
                rabbitTemplate.convertAndSend(respondeQueue, invoiceResponse);
            }
        } catch (NfeException e) {
            LOG.severe("Erro no processamento da mensagem: " + e.getMessage()); ;
        } catch (JAXBException e) {
            LOG.severe("Erro na conversao do XML : " + e.getMessage()); ;
        }
    }


}
