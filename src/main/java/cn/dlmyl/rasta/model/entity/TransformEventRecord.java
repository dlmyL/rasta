package cn.dlmyl.rasta.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.OffsetDateTime;

/**
 * 转换事件记录实体
 *
 * @author <a href="https://github.com/dlmyL">dlmyL</a>
 * @version 1.0
 */
@Data
@TableName("r_transform_event_record")
public class TransformEventRecord extends BaseEntity {

    @TableId
    private Long id;

    private String uniqueIdentity;

    private String clientIp;

    private String shortUrl;

    private String longUrl;

    private String shortUrlDigest;

    private String longUrlDigest;

    private String compressionCode;

    private OffsetDateTime recordTime;

    private String userAgent;

    private String cookieValue;

    private String queryParam;

    private String province;

    private String city;

    private String phoneType;

    private String browserType;

    private String browserVersion;

    private String osType;

    private String deviceType;

    private String osVersion;

    private Integer transformStatus;

}