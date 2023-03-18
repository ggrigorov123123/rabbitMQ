package com.blubito.orderservice.publisher;

import com.blubito.orderservice.dto.OrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderProducer {

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.binding.stock.routing.key}")
    private String stockRoutingKey;

    @Value("${rabbitmq.binding.email.routing.key}")
    private String emailRoutingKey;

    private RabbitTemplate rabbitTemplate;

    public OrderProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(OrderEvent orderEvent) {
        log.info(String.format("Order event sent to RabbitMq => %s", orderEvent.toString()));
        //send an order to order queue
        rabbitTemplate.convertAndSend(exchange, stockRoutingKey, orderEvent);

//        //send an order to email queue
        rabbitTemplate.convertAndSend(exchange, emailRoutingKey, orderEvent);
    }
}
