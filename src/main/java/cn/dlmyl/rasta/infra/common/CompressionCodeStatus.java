package cn.dlmyl.rasta.infra.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 短码状态
 *
 * @author <a href="https://github.com/dlmyL">dlmyL</a>
 * @version 1.0
 */
@Getter
@RequiredArgsConstructor
public enum CompressionCodeStatus {

    /**
     * 可用
     */
    AVAILABLE(1),

    /**
     * 已经使用
     */
    USED(2),

    /**
     * 非法
     */
    INVALID(3),

    ;

    private final Integer value;

}
