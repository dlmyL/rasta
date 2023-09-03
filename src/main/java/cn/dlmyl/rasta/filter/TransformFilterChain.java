package cn.dlmyl.rasta.filter;

/**
 * 短链转换过滤器链接口
 *
 * @author <a href="https://github.com/dlmyL">dlmyL</a>
 * @version 1.0
 */
public interface TransformFilterChain {

    void doFilter(TransformContext context);

    void release();

}
