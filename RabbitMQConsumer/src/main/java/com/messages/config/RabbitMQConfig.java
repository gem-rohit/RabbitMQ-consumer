package com.messages.config;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.interceptor.RetryOperationsInterceptor;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE="rabbit_mq_queue";
    public static final String EXCHANGE="rabbit_mq_exchange";
    public static final String ROUTING_KEY="rabbit_mq_r_key";

    @Bean
    public Queue queue()
    {
        return new Queue(QUEUE);
    }
    @Bean
    public DirectExchange directExchange()
    {
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    public Binding binding(Queue queue,DirectExchange directExchange)
    {
        return BindingBuilder.bind(queue).to(directExchange).with(ROUTING_KEY);

    }

    // Dead Letter Queue ----------------------------------------------------------
/*

    @Bean
    Queue queue() {
        return QueueBuilder.durable(QUEUE).withArgument("x-dead-letter-exchange", "deadLetterExchange")
                .withArgument("x-dead-letter-routing-key", "deadLetter").build();
    }

    //-------------------------------------------
    @Bean
    DirectExchange deadLetterExchange() {
        return new DirectExchange("deadLetterExchange");
    }

    @Bean
    Queue dlq() {
        return QueueBuilder.durable("deadLetter.queue").build();
    }

    @Bean
    Binding DLQbinding() {
        return BindingBuilder.bind(dlq()).to(deadLetterExchange()).with("deadLetter");
    }
*/

    // *******************************************************************************

    @Bean
    public MessageConverter messageConverter()
    {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate getTemplete(ConnectionFactory connectionFactory)
    {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }


}
