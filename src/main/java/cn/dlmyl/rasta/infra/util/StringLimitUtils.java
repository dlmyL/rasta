package cn.dlmyl.rasta.infra.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 字符串截取工具
 *
 * @author <a href="https://github.com/dlmyL">dlmyL</a>
 * @version 1.0
 */
public enum StringLimitUtils {

    /**
     * 表示单例
     */
    X;

    public String limitString(String value, int limit) {
        if (StringUtils.isBlank(value)) {
            return value;
        }
        int len = value.length();
        if (len > limit) {
            return value.substring(0, limit);
        }
        return value;
    }

}
