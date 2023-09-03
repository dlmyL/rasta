package cn.dlmyl.rasta.handler.task;

import cn.dlmyl.rasta.infra.common.LockKey;
import cn.dlmyl.rasta.infra.common.TimeZoneConstant;
import cn.dlmyl.rasta.infra.support.lock.DistributedLock;
import cn.dlmyl.rasta.infra.support.lock.DistributedLockFactory;
import cn.dlmyl.rasta.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.concurrent.TimeUnit;

/**
 * 定时任务统计数据
 *
 * @author <a href="https://github.com/dlmyL">dlmyL</a>
 * @version 1.0
 */
@Component
@RequiredArgsConstructor
public class VisitStatisticsTaskHandler {

    private final StatisticsService statisticsService;
    private final DistributedLockFactory distributedLockFactory;

    @Scheduled(cron = "0 1 0 * * ?")
    public void processVisitStatistics() {
        DistributedLock lock = distributedLockFactory.provideDistributedLock(LockKey.VISITOR_STATS_TASK.getCode());
        boolean tryLock = lock.tryLock(LockKey.VISITOR_STATS_TASK.getWaitTime(), LockKey.VISITOR_STATS_TASK.getReleaseTime(), TimeUnit.SECONDS);
        if (tryLock) {
            try {
                OffsetDateTime now = OffsetDateTime.now(TimeZoneConstant.CHINA.getZoneId());
                OffsetDateTime start = now.minusDays(1L).withNano(0).withSecond(0).withMinute(0).withHour(0);
                OffsetDateTime end = start.withNano(0).withSecond(59).withMinute(59).withHour(23);
                statisticsService.processVisitStatisticsDuration(start, end);
            } finally {
                lock.unlock();
            }
        }
    }

}
