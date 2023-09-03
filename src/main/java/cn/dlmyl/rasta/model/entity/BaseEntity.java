package cn.dlmyl.rasta.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.OffsetDateTime;

/**
 * Base Entity
 *
 * @author <a href="https://github.com/dlmyL">dlmyL</a>
 * @version 1.0
 */
@Data
public abstract class BaseEntity implements Serializable {

    private OffsetDateTime createTime;

    private OffsetDateTime editTime;

    private String creator;

    private String editor;

    private Integer deleted;

}
