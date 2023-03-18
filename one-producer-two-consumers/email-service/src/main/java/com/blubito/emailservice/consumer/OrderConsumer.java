package com.blubito.emailservice.consumer;

import com.blubito.emailservice.dto.OrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderConsumer {

    @RabbitListener(queues = "${rabbitmq.queue.email.name}")
    public void consume(OrderEvent event) {
        log.info(String.format("Order event received in email service => %s", event.toString()));

        //email service needs to email customer
    }
}
