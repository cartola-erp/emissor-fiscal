package net.cartola.emissorfiscal.emissao;

import autogeral.emissorfiscal.vo.InvoiceModel;
import autogeral.emissorfiscal.vo.ItemModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import net.cartola.emissorfiscal.documento.Documento;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class Emissao {
    private static final Logger logger = LoggerFactory.getLogger(Emissao.class);

    @Autowired
    EmissaoPrenchimentoDadosFiscaisService emissaoPrenchimentoDadosFiscaisService;

    /**
     *     @Autowired
     *     EmissaoCriacaoXmlService emissaoCriacaoXmlService;
     */

    @RabbitListener(queues = "hello")
    public void processMessage(String content) {
        System.out.println("Recebeu o Json: " + content);

        if(!content.isEmpty()){
            ObjectMapper om = new ObjectMapper();
            try {
                InvoiceModel invoice = om.readValue(content, InvoiceModel.class);
                ResultadoUtil fullInvoice = emissaoPrenchimentoDadosFiscaisService.preencherDadosFiscais(invoice);
                System.out.println("Resultado: " + fullInvoice.getErros());

                //Validacoes importantes para o xml da nfce ser preenchido corretamente
                 // *------ AS VALIDACOES VIRAM AQUI -----*

                //*----- APOS AS VALIDACOES COMECO MONTAR O XML ---*
                //MontaXml(fullInvoice);

            }catch (JsonProcessingException e) {
                logger.error("Erro ao processar JSON: {}", e.getMessage(), e);

            }
        }
    }

    public String MontaXml(InvoiceModel invoice){

        //emissaoCriacaoXmlService.criaXmlNota(invoice); // RETORNA O XML // inf Importantes
        return "ok";
    }

    public void transmissao(){

        //Nota Emitida com sucesso ?
        //Retorno uma mensagem para o dbf
        //retorna para o dbf s
    }
}
