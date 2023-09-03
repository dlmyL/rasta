package cn.dlmyl.rasta.infra.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 分布式锁键
 *
 * @author <a href="https://github.com/dlmyL">dlmyL</a>
 * @version 1.0
 */
@Getter
@RequiredArgsConstructor
public enum LockKey {

    /**
     * 创建短链映射场景
     */
    CREATE_URL_MAP("rasta:url:map:create", "创建URL映射", 0L, 10000L),

    /**
     * 编辑短链映射场景
     */
    EDIT_URL_MAP("rasta:url:map:edit", "修改URL映射", 0L, 10000L),

    /**
     * 访问统计任务
     */
    VISITOR_STATS_TASK("rasta:visitor:stats:task", "访问统计任务", 0L, 10000L),

    ;

    private final String code;
    private final String desc;
    private final long waitTime;
    private final long releaseTime;

}
