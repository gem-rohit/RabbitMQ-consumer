package com.messages.consumer;

import com.messages.config.RabbitMQConfig;
import com.messages.entity.OrderDTO;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqConsumer {

//    @RabbitListener(queues = {RabbitMQConfig.QUEUE}, containerFactory = "registrationListenerContainerFactory")
@RabbitListener(queues = RabbitMQConfig.QUEUE)
public void consume(OrderDTO orderDTO)
{
    System.out.println("--->inside msg listenner");

//    if(orderDTO.getOrder()!=null)
//    throw new RuntimeException("run time exception");

    System.out.println("queue data "+orderDTO);


}
}
