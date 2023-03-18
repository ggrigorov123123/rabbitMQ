package com.blubito.orderservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    //Order
    @Value("${rabbitmq.queue.stock.name}")
    private String stockQueue;

    @Value("${rabbitmq.binding.stock.routing.key}")
    private String stockRoutingKey;

    //Email
    @Value("${rabbitmq.queue.email.name}")
    private String emailQueue;

    @Value("${rabbitmq.binding.email.routing.key}")
    private String emailRoutingKey;

    //Spring bean for exchange
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(exchange);
    }

    //Spring bean for queue - order
    @Bean
    public Queue stockQueue() {
        return new Queue(stockQueue);
    }

//    Spring bean for queue - order
    @Bean
    public Queue emailQueue() {
        return new Queue(emailQueue);
    }

    //Spring bean for binding between exchange and queue using routing key
    @Bean
    public Binding stockBinding() {
        return BindingBuilder
                .bind(stockQueue())
                .to(exchange())
                .with(stockRoutingKey);
    }

    //Spring bean for binding between exchange and queue using routing key
    @Bean
    public Binding emailBinding() {
        return BindingBuilder
                .bind(emailQueue())
                .to(exchange())
                .with(emailRoutingKey);
    }

    //Message converter
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    //Configure RabbitTemplate
    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }
}
