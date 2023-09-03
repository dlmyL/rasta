package cn.dlmyl.rasta.handler.event;

import cn.dlmyl.rasta.infra.MappedDiagnosticContextProvider;
import cn.dlmyl.rasta.infra.common.RabbitQueueRaw;
import cn.dlmyl.rasta.infra.common.TimeZoneConstant;
import cn.dlmyl.rasta.infra.util.BeanCopierUtils;
import cn.dlmyl.rasta.infra.util.JacksonUtils;
import cn.dlmyl.rasta.model.entity.TransformEventRecord;
import cn.dlmyl.rasta.service.TransformEventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.OffsetDateTime;

/**
 * 通过消费MQ中的短链访问事件进行消息转换
 *
 * @author <a href="https://github.com/dlmyL">dlmyL</a>
 * @version 1.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TransformEventHandler {

    private final TransformEventService transformEventService;
    private final MappedDiagnosticContextProvider mappedDiagnosticContextProvider;

    @RabbitListener(queues = {RabbitQueueRaw.TRANSFORM_EVENT_QUEUE}, concurrency = "1-10")
    public void onTransformEvent(String content) {
        mappedDiagnosticContextProvider.process(() -> {
            log.info("接收到URL转换事件,内容:{}......", content);
            TransformEvent event = JacksonUtils.X.parse(content, TransformEvent.class);
            TransformEventRecord record = new TransformEventRecord();
            BeanCopierUtils.X.copy(event, record);
            record.setRecordTime(OffsetDateTime.ofInstant(Instant.ofEpochMilli(event.getTimestamp()), TimeZoneConstant.CHINA.getZoneId()));
            record.setTransformStatus(event.getTransformStatusValue());
            record.setShortUrl(event.getShortUrlString());
            record.setLongUrl(event.getLongUrlString());
            transformEventService.recordTransformEvent(record);
            log.info("记录URL转换事件完成......");
        });
    }

}
