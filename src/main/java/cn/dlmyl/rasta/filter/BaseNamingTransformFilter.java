package cn.dlmyl.rasta.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 抽象命名过滤器
 *
 * @author <a href="https://github.com/dlmyL">dlmyL</a>
 * @version 1.0
 */
@Slf4j
@RequiredArgsConstructor
public abstract class BaseNamingTransformFilter implements TransformFilter {

    private final TransformFilter delegate;

    @Override
    public void init(TransformContext context) {
        delegate.init(context);
    }

    @Override
    public void doFilter(TransformFilterChain chain, TransformContext context) {
        if (log.isDebugEnabled()) {
            log.debug("Entry TransformFilter {}...", filterName());
        }
        long start = System.nanoTime();
        try {
            delegate.doFilter(chain, context);
        } finally {
            if (log.isDebugEnabled()) {
                log.debug("Exit TransformFilter {},execution cost {} ms...", filterName(), TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start));
            }
        }
    }

    @Override
    public int order() {
        return delegate.order();
    }

    public abstract String filterName();

}
