package cn.dlmyl.rasta.infra.config;

import cn.dlmyl.rasta.infra.common.RabbitQueue;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.annotation.Configuration;

import java.util.stream.Stream;

/**
 * RabbitMQ 自动装配
 *
 * @author <a href="https://github.com/dlmyL">dlmyL</a>
 * @version 1.0
 */
@Configuration
@RequiredArgsConstructor
public class RabbitAutoConfiguration implements SmartInitializingSingleton {

    private final AmqpAdmin amqpAdmin;

    @Override
    public void afterSingletonsInstantiated() {
        Stream.of(RabbitQueue.values()).forEach(queue -> {
            String queueName = queue.getQueueName();
            Queue queueToUse = new Queue(queueName);
            amqpAdmin.declareQueue(queueToUse);
            CustomExchange exchange = new CustomExchange(queue.getExchangeName(), queue.getExchangeType());
            amqpAdmin.declareExchange(exchange);
            Binding binding = BindingBuilder.bind(queueToUse).to(exchange).with(queue.getRoutingKey()).noargs();
            amqpAdmin.declareBinding(binding);
        });
    }

}
