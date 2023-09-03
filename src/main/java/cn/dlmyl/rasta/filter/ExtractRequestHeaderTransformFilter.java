package cn.dlmyl.rasta.filter;

import cn.dlmyl.rasta.infra.util.IpUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 提取请求头过滤器
 *
 * @author <a href="https://github.com/dlmyL">dlmyL</a>
 * @version 1.0
 */
@Component
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ExtractRequestHeaderTransformFilter implements TransformFilter {

    private static final String UA_KEY = "User-Agent";
    private static final String COOKIE_KEY = "Cookie";

    @Override
    public void init(TransformContext context) {

    }

    @Override
    public void doFilter(TransformFilterChain chain, TransformContext context) {
        try {
            String ua = context.getHeader(UA_KEY);
            String cookie = context.getHeader(COOKIE_KEY);
            context.setParam(TransformContext.PARAM_UA_KEY, ua);
            context.setParam(TransformContext.PARAM_COOKIE_KEY, cookie);
            String clientHostName = context.getParam(TransformContext.PARAM_REMOTE_HOST_NAME_KEY);
            context.setParam(TransformContext.PARAM_CLIENT_ID_KEY, IpUtils.X.extractClientIp(context.getHeaders(), clientHostName));
        } finally {
            context.releaseHeaders();
        }
        chain.doFilter(context);
    }

    @Override
    public int order() {
        return 1;
    }

}
