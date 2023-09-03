package cn.dlmyl.rasta.repository;

import cn.dlmyl.rasta.mapper.VisitStatisticsMapper;
import cn.dlmyl.rasta.model.entity.VisitStatistics;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

/**
 * 访问统计持久层
 *
 * @author <a href="https://github.com/dlmyL">dlmyL</a>
 * @version 1.0
 */
@Repository
public class VisitStatisticsRepository extends BaseRepository<VisitStatisticsMapper, VisitStatistics> {

    public VisitStatistics selectByUniqueKey(LocalDate statisticsDate, String compressionCode,
                                             String shortUrlDigest, String longUrlDigest) {
        return this.baseMapper.selectOne(
                new LambdaQueryWrapper<VisitStatistics>()
                        .eq(VisitStatistics::getStatisticsDate, statisticsDate)
                        .eq(VisitStatistics::getCompressionCode, compressionCode)
                        .eq(VisitStatistics::getShortUrlDigest, shortUrlDigest)
                        .eq(VisitStatistics::getLongUrlDigest, longUrlDigest)
        );
    }

    public void insertSelective(VisitStatistics visitStatistics) {
        this.baseMapper.insert(visitStatistics);
    }

    public void updateByPrimaryKeySelective(VisitStatistics visitStatistics) {
        this.baseMapper.updateById(visitStatistics);
    }

}
