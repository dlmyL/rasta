package cn.dlmyl.rasta.infra.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 事件转换状态
 *
 * @author <a href="https://github.com/dlmyL">dlmyL</a>
 * @version 1.0
 */
@Getter
@RequiredArgsConstructor
public enum TransformStatus {

    /**
     * 转换成功
     */
    TRANSFORM_SUCCESS(1),

    /**
     * 转换失败
     */
    TRANSFORM_FAIL(2),

    /**
     * 重定向成功
     */
    REDIRECTION_SUCCESS(3),

    /**
     * 重定向失败
     */
    REDIRECTION_FAIL(4),

    ;

    private final Integer value;

}
