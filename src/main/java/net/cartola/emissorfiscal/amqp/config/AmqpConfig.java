package net.cartola.emissorfiscal.amqp.config;

import net.cartola.emissorfiscal.amqp.documentosfiscais.model.AmqpDocumentosFiscaisDestinos;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmqpConfig {

    @Bean
    public CachingConnectionFactory amqpConncetinFactory(
             @Value("${amqp.host}") String host,
             @Value("${amqp.port}") int port,
             @Value("${amqp.username}") String username,
             @Value("${amqp.password}") String password
    ) {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host, port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        return connectionFactory;
    }

    // Para enviar e receber mensagens
    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        return rabbitTemplate;
    }

    // Para gerenciar filas, exchanges e bindings
    @Bean
    public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public TopicExchange documentoFiscalPrincipalExchange() {
        return new TopicExchange(AmqpDocumentosFiscaisDestinos.EXCHANGE_DOCUMENTOS_FISCAIS_PRINCIPAL,
                true, false);
    }

    // Declaracoes de Filas e Bidings para solicitacao de emissao de documentos fiscais

    // NFe - Fila para receber requisicoes de emissao
    @Bean
    public Queue nfeEmissaoRequisicaoQueue() {
        return new Queue(AmqpDocumentosFiscaisDestinos.NFE.QUEUE_NFE_EMISSAO_REQUISICAO_NAME,
                true, false, false);
    }

    // Binding para a fila de requisicao de emissao de NFe
    @Bean
    public Binding nfeEmissaoRequisicaoBiding() {
        return BindingBuilder.bind(nfeEmissaoRequisicaoQueue())
                .to(documentoFiscalPrincipalExchange())
                .with(AmqpDocumentosFiscaisDestinos.NFE.ROUTING_KEY_NFE_EMISSAO_REQUSICAO);
    }

    // NFCe - Fila para receber requisicoes de emissao
    @Bean
    public Queue nfceEmissaoRequisicaoQueue() {
        return new Queue(AmqpDocumentosFiscaisDestinos.NFCE.QUEUE_NFCE_EMISSAO_REQUISICAO_NAME,
                true, false, false);
    }

    // NFCe - Binding para a fila de requisicoes de emissao de NFCe
    @Bean
    public Binding nfceEmissaoRequisicaoBiding() {
        return BindingBuilder.bind(nfceEmissaoRequisicaoQueue())
                .to(documentoFiscalPrincipalExchange())
                .with(AmqpDocumentosFiscaisDestinos.NFCE.ROUTING_KEY_NFCE_EMISSAO_REQUISICAO);
    }

}
