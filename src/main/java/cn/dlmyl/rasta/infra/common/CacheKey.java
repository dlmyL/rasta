package cn.dlmyl.rasta.infra.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 缓存键
 *
 * @author <a href="https://github.com/dlmyL">dlmyL</a>
 * @version 1.0
 */
@Getter
@RequiredArgsConstructor
public enum CacheKey {

    /**
     * 客户端黑名单列表
     */
    BLOCK_IP_SET("rasta:block:ip:set", "禁用的客户端IP", -1L),

    /**
     * 可访问短链域名白名单列表
     */
    ACCESS_DOMAIN_SET("rasta:access:domain:set", "启用的短链域名", -1L),

    /**
     * 可访问的压缩码映射
     */
    ACCESS_CODE_HASH("rasta:access:code:hash", "可访问的压缩码映射", -1L),

    ;

    private final String key;
    private final String description;
    private final long expireSeconds;

}