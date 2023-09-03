package cn.dlmyl.rasta.filter;

/**
 * 短链转换过滤器接口
 *
 * @author <a href="https://github.com/dlmyL">dlmyL</a>
 * @version 1.0
 */
public interface TransformFilter {

    default void init(TransformContext context) {

    }

    void doFilter(TransformFilterChain chain, TransformContext context);

    default int order() {
        return 1;
    }

}