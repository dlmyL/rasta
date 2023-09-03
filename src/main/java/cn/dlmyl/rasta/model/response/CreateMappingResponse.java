package cn.dlmyl.rasta.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 创建短链映射请求响应
 *
 * @author <a href="https://github.com/dlmyL">dlmyL</a>
 * @version 1.0
 */
@Getter
@Builder
@RequiredArgsConstructor
public class CreateMappingResponse {

    private final String shortUrl;

}