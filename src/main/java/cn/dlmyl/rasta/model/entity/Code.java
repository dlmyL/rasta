package cn.dlmyl.rasta.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 短链码实体
 *
 * @author <a href="https://github.com/dlmyL">dlmyL</a>
 * @version 1.0
 */
@Data
@TableName("r_code")
public class Code extends BaseEntity {

    @TableId
    private Long id;

    private String compressionCode;

    private Integer codeStatus;

    private String sequenceValue;

    private String strategy;

}