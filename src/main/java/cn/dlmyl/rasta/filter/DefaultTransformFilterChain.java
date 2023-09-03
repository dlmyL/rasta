package cn.dlmyl.rasta.filter;

/**
 * 默认的事件转换过滤器链
 *
 * @author <a href="https://github.com/dlmyL">dlmyL</a>
 * @version 1.0
 */
public class DefaultTransformFilterChain implements TransformFilterChain {

    private int pos = 0;
    private int n = 0;
    private TransformFilter[] filters = new TransformFilter[0];

    @Override
    public void doFilter(TransformContext context) {
        if (this.pos < this.n) {
            TransformFilter transformFilter = this.filters[this.pos++];
            transformFilter.doFilter(this, context);
        }
    }

    void addTransformFilter(TransformFilter filter) {
        TransformFilter[] newFilters = this.filters;
        for (TransformFilter newFilter : newFilters) {
            if (newFilter == filter) {
                return;
            }
        }
        if (this.n == this.filters.length) {
            newFilters = new TransformFilter[this.n + 10];
            System.arraycopy(this.filters, 0, newFilters, 0, this.n);
            this.filters = newFilters;
        }
        this.filters[this.n++] = filter;
    }

    @Override
    public void release() {
        for (int i = 0; i < this.n; ++i) {
            this.filters[i] = null;
        }
        this.pos = 0;
        this.n = 0;
    }

}
