package cn.dlmyl.rasta.service;

import cn.dlmyl.rasta.model.entity.VisitStatistics;
import cn.dlmyl.rasta.repository.TransformEventRecordRepository;
import cn.dlmyl.rasta.repository.VisitStatisticsRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * 通过定时任务统计短链访问的数据
 *
 * @author <a href="https://github.com/dlmyL">dlmyL</a>
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final TransformEventRecordRepository transformEventRecordRepository;
    private final VisitStatisticsRepository visitStatisticsRepository;

    public void processVisitStatisticsDuration(OffsetDateTime start, OffsetDateTime end) {
        List<VisitStatistics> selective = transformEventRecordRepository.queryVisitStatisticsDuration(start, end);
        for (VisitStatistics visitStatistics : selective) {
            VisitStatistics selectiveVisitStatistics = visitStatisticsRepository.selectByUniqueKey(
                    visitStatistics.getStatisticsDate(),
                    visitStatistics.getCompressionCode(),
                    visitStatistics.getShortUrlDigest(),
                    visitStatistics.getLongUrlDigest()
            );
            if (ObjectUtils.isEmpty(selectiveVisitStatistics)) {
                visitStatisticsRepository.insertSelective(visitStatistics);
            } else {
                visitStatistics.setId(selectiveVisitStatistics.getId());
                visitStatisticsRepository.updateByPrimaryKeySelective(visitStatistics);
            }
        }
    }

}
