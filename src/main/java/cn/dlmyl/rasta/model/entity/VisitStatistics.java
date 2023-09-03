package cn.dlmyl.rasta.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * 访问统计实体
 *
 * @author <a href="https://github.com/dlmyL">dlmyL</a>
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("r_visit_statistics")
public class VisitStatistics extends BaseEntity {

    @TableId
    private Long id;

    private LocalDate statisticsDate;

    private Long pvCount;

    private Long uvCount;

    private Long ipCount;

    private Long effectiveRedirectionCount;

    private Long ineffectiveRedirectionCount;

    private String compressionCode;

    private String shortUrlDigest;

    private String longUrlDigest;

}