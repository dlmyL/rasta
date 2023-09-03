package cn.dlmyl.rasta.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 短链映射实体
 *
 * @author <a href="https://github.com/dlmyL">dlmyL</a>
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("r_mapping")
public class Mapping extends BaseEntity {

    @TableId
    private Long id;

    private String shortUrl;

    private String longUrl;

    private String shortUrlDigest;

    private String longUrlDigest;

    private String compressionCode;

    private String description;

    private Integer urlStatus;

}
