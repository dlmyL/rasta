package cn.dlmyl.rasta.filter;

import cn.dlmyl.rasta.infra.common.RabbitQueue;
import cn.dlmyl.rasta.infra.util.JacksonUtils;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 转换事件处理过滤器
 *
 * @author <a href="https://github.com/dlmyL">dlmyL</a>
 * @version 1.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TransformEventProcessTransformFilter implements TransformFilter {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void init(TransformContext context) {

    }

    @Override
    public void doFilter(TransformFilterChain chain, TransformContext context) {
        UrlTransformRecordEvent event = UrlTransformRecordEvent.builder()
                .clientIp(context.getParam(TransformContext.PARAM_CLIENT_ID_KEY))
                .timestamp(System.currentTimeMillis())
                .compressionCode(context.getCompressionCode())
                .userAgent(context.getParam(TransformContext.PARAM_UA_KEY))
                .cookieValue(context.getParam(TransformContext.PARAM_COOKIE_KEY))
                .shortUrlString(context.getParam(TransformContext.PARAM_SHORT_URL_KEY))
                .longUrlString(context.getParam(TransformContext.PARAM_TARGET_LONG_URL_KEY))
                .transformStatusValue(context.getTransformStatusValue())
                .build();
        rabbitTemplate.convertAndSend(
                RabbitQueue.TRANSFORM_EVENT_QUEUE.getExchangeName(),
                RabbitQueue.TRANSFORM_EVENT_QUEUE.getRoutingKey(),
                JacksonUtils.X.format(event)
        );
        chain.doFilter(context);
    }

    @Override
    public int order() {
        return 4;
    }

    @Data
    @Builder
    private static class UrlTransformRecordEvent {
        private String clientIp;
        private String compressionCode;
        private String userAgent;
        private String cookieValue;
        private Long timestamp;
        private String shortUrlString;
        private String longUrlString;
        private Integer transformStatusValue;
    }

}
