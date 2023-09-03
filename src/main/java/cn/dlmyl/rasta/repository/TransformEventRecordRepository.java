package cn.dlmyl.rasta.repository;

import cn.dlmyl.rasta.mapper.TransformEventMapper;
import cn.dlmyl.rasta.model.entity.TransformEventRecord;
import cn.dlmyl.rasta.model.entity.VisitStatistics;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * 转换事件记录持久层
 *
 * @author <a href="https://github.com/dlmyL">dlmyL</a>
 * @version 1.0
 */
@Repository
public class TransformEventRecordRepository extends BaseRepository<TransformEventMapper, TransformEventRecord> {

    public List<VisitStatistics> queryVisitStatisticsDuration(OffsetDateTime start, OffsetDateTime end) {
        return this.baseMapper.queryVisitStatisticsDuration(start, end);
    }

    public void insertSelective(TransformEventRecord record) {
        this.baseMapper.insert(record);
    }

}
