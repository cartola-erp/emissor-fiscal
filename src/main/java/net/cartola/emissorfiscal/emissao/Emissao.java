package net.cartola.emissorfiscal.emissao;


import java.util.logging.Level;
import java.util.logging.Logger;
import br.com.autogeral.emissorfiscal.vo.InvoiceModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.cartola.emissorfiscal.produto.ProdutoAlteradoSpedApiController;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Emissao {

    private static final Logger LOG = Logger.getLogger(Emissao.class.getName());

    @Autowired
    private EmissaoPrenchimentoDadosFiscaisService emissaoPrenchimentoDadosFiscaisService;

    @Autowired
    private EmissaoCriacaoXmlService emissaoCriacaoXmlService;

//    @RabbitListener(queues = "hello")
    public void processMessage(String content) {
        InvoiceModel invoice = new InvoiceModel();

        if (!content.isEmpty()) {
            ObjectMapper om = new ObjectMapper();
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
        montaXml(invoiceTratada);
    }

    public void montaXml(InvoiceModel invoice){
        emissaoCriacaoXmlService.montaXmlNota(invoice);
    }

    public void transmissao(){

        //Nota Emitida com sucesso ?
        //Retorno uma mensagem para o dbf
        //retorna para o dbf s
    }
}
