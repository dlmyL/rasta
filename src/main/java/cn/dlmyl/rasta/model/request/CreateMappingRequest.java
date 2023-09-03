package cn.dlmyl.rasta.model.request;

import lombok.Data;

/**
 * 创建短链映射请求
 *
 * @author <a href="https://github.com/dlmyL">dlmyL</a>
 * @version 1.0
 */
@Data
public class CreateMappingRequest {

    private String longUrl;

    private String description;

}
