package cn.dlmyl.rasta.handler.event;

import lombok.Data;

/**
 * 转换事件
 *
 * @author <a href="https://github.com/dlmyL">dlmyL</a>
 * @version 1.0
 */
@Data
public class TransformEvent {

    private String clientIp;

    private String compressionCode;

    private String userAgent;

    private String cookieValue;

    private Long timestamp;

    private String shortUrlString;

    private String longUrlString;

    private Integer transformStatusValue;

}
