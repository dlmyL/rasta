package cn.dlmyl.rasta.infra.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * RabbitMQ队列枚举
 *
 * @author <a href="https://github.com/dlmyL">dlmyL</a>
 * @version 1.0
 */
@Getter
@RequiredArgsConstructor
public enum RabbitQueue {

    TRANSFORM_EVENT_QUEUE(
            RabbitQueueRaw.TRANSFORM_EVENT_QUEUE,
            RabbitQueueRaw.TRANSFORM_EVENT_QUEUE,
            RabbitQueueRaw.TRANSFORM_EVENT_QUEUE,
            "fanout"
    ),

    ;

    private final String queueName;
    private final String exchangeName;
    private final String routingKey;
    private final String exchangeType;

}
