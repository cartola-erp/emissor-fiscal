package net.cartola.emissorfiscal.amqp.documentosfiscais.service;

import br.com.autogeral.emissorfiscal.response.vo.InvoiceResponse;
import br.com.autogeral.emissorfiscal.vo.InvoiceModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import net.cartola.emissorfiscal.amqp.documentosfiscais.model.AmqpDocumentoFiscalServiceInterface;
import net.cartola.emissorfiscal.amqp.documentosfiscais.model.AmqpDocumentosFiscaisDestinos;
import net.cartola.emissorfiscal.amqp.service.AmqpSenderService;
import net.cartola.emissorfiscal.emissao.EmissaoService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class AmqpNfeService implements AmqpDocumentoFiscalServiceInterface {

    private final EmissaoService emissaoService;
    private final AmqpSenderService amqpSenderService;

    private final ObjectMapper objectMapper;

    @Autowired
    public AmqpNfeService(
            EmissaoService emissaoService,
            AmqpSenderService amqpSenderService
    ) {
        this.emissaoService = emissaoService;
        this.amqpSenderService = amqpSenderService;

        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")));
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(javaTimeModule);
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }


    @Override
    @RabbitListener(queues = AmqpDocumentosFiscaisDestinos.NFE.QUEUE_NFE_EMISSAO_REQUISICAO_NAME)
    public void processarRequisicaoEmissao(String jsonRequest) {
        InvoiceModel invoiceModel;
        try {
            invoiceModel = objectMapper.readValue(jsonRequest, InvoiceModel.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao parsear Json de requisicao de emissao NFCe: " + e.getMessage(), e);
        }

        emissaoService.processMessageEmitir(invoiceModel);
    }

    @Override
    public void enviarRespostaRequisicaoEmissao(InvoiceResponse invoiceModel) {
        String jsonResponse;
        try {
            jsonResponse = objectMapper.writeValueAsString(invoiceModel);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao serializar resposta de emissao NFCe: " + e.getMessage(), e);
        }

        amqpSenderService.sendMessage(
                AmqpDocumentosFiscaisDestinos.EXCHANGE_DOCUMENTOS_FISCAIS_PRINCIPAL,
                AmqpDocumentosFiscaisDestinos.NFE.ROUTING_KEY_NFE_EMISSAO_RESPOSTA,
                jsonResponse
        );
    }

}
