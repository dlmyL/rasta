package cn.dlmyl.rasta.mapper;

import cn.dlmyl.rasta.model.entity.TransformEventRecord;
import cn.dlmyl.rasta.model.entity.VisitStatistics;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * 转换事件记录 Mapper 层
 *
 * @author <a href="https://github.com/dlmyL">dlmyL</a>
 * @version 1.0
 */
@Mapper
public interface TransformEventMapper extends BaseMapper<TransformEventRecord> {

    String VISIT_STATISTICS_DURATION_SQL = "SELECT COUNT(1) AS `pvCount`, " +
            "         IFNULL(COUNT(DISTINCT unique_identity), 0) AS `uvCount`, " +
            "         IFNULL(COUNT(DISTINCT client_ip), 0) AS `ipCount`, " +
            "         SUM(CASE WHEN transform_status = 3 THEN 1 ELSE 0 END) AS `effectiveRedirectionCount`, " +
            "         SUM(CASE WHEN transform_status != 3 THEN 1 ELSE 0 END) AS `ineffectiveRedirectionCount`, " +
            "         DATE_FORMAT(record_time, '%Y-%m-%d') AS `statisticsDate`, " +
            "         compression_code AS `compressionCode`, " +
            "         short_url_digest AS `shortUrlDigest`, " +
            "         long_url_digest AS `longUrlDigest` " +
            "    FROM r_transform_event_record " +
            "   WHERE deleted = 0 " +
            "     AND record_time >= #{start,jdbcType=TIMESTAMP} " +
            "     AND record_time <= #{end,jdbcType=TIMESTAMP} " +
            "GROUP BY compression_code, DATE_FORMAT(record_time, '%Y-%m-%d'), " +
            "         short_url_digest, long_url_digest ";

    @Select(VISIT_STATISTICS_DURATION_SQL)
    List<VisitStatistics> queryVisitStatisticsDuration(@Param("start") OffsetDateTime start,
                                                       @Param("end") OffsetDateTime end);

}