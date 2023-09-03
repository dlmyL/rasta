package cn.dlmyl.rasta.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 短链域名实体
 *
 * @author <a href="https://github.com/dlmyL">dlmyL</a>
 * @version 1.0
 */
@Data
@TableName("r_domain")
public class Domain extends BaseEntity {

    @TableId
    private Long id;

    private String domainValue;

    private String protocol;

    private Integer domainStatus;

}