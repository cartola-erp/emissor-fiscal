package net.cartola.emissorfiscal.amqp.service;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AmqpSenderService {

    private final RabbitTemplate rabbitTemplate;
    private final AmqpAdmin amqpAdmin;

    @Autowired
    public AmqpSenderService(
        RabbitTemplate rabbitTemplate,
        AmqpAdmin amqpAdmin
    ) {
        this.rabbitTemplate = rabbitTemplate;
        this.amqpAdmin = amqpAdmin;
    }

    public void sendMessage(String exchangeName, String routingKey, String jsonMessage) {
        rabbitTemplate.convertAndSend(exchangeName, routingKey, jsonMessage);
    }

}
