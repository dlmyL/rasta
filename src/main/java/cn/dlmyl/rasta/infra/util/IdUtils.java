package cn.dlmyl.rasta.infra.util;

import java.util.UUID;

/**
 * UUID 生成工具
 *
 * @author <a href="https://github.com/dlmyL">dlmyL</a>
 * @version 1.0
 */
public enum IdUtils {

    /**
     * 表示单例
     */
    X;

    public String randomUUID() {
        return UUID.randomUUID().toString();

    }

    public String simpleUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

}
