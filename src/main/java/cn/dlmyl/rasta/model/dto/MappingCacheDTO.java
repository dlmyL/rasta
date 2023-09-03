package cn.dlmyl.rasta.model.dto;

import lombok.Data;

/**
 * 短链映射缓存 DTO
 *
 * @author <a href="https://github.com/dlmyL">dlmyL</a>
 * @version 1.0
 */
@Data
public class MappingCacheDTO {

    private Long id;

    private String shortUrl;

    private String longUrl;

    private String compressionCode;

    private String description;

    private Boolean enable;

}
