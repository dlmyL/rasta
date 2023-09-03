package cn.dlmyl.rasta.infra.util;

import com.google.common.collect.Maps;
import org.springframework.cglib.beans.BeanCopier;

import java.util.Map;

/**
 * BEAN 属性拷贝工具
 *
 * @author <a href="https://github.com/dlmyL">dlmyL</a>
 * @version 1.0
 */
public enum BeanCopierUtils {

    /**
     * 表示单例
     */
    X;

    private static final Map<String, BeanCopier> CACHE = Maps.newConcurrentMap();

    public void copy(Object source, Object target) {
        String key = String.format("%s-%s", source.getClass().getName(), target.getClass().getName());
        CACHE.computeIfAbsent(key,
                x -> BeanCopier.create(source.getClass(), target.getClass(), false)
        ).copy(source, target, null);
    }

}
