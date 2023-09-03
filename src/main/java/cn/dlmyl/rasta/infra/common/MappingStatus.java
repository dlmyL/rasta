package cn.dlmyl.rasta.infra.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 短链映射状态
 *
 * @author <a href="https://github.com/dlmyL">dlmyL</a>
 * @version 1.0
 */
@Getter
@RequiredArgsConstructor
public enum MappingStatus {

    /**
     * 可用状态
     */
    AVAILABLE(1),

    /**
     * 非法状态
     */
    INVALID(2),

    ;

    private final Integer value;

}
