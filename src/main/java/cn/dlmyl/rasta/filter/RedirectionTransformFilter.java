package cn.dlmyl.rasta.filter;

import cn.dlmyl.rasta.infra.common.TransformStatus;
import cn.dlmyl.rasta.infra.support.spring.WebFluxServerResponseWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 重定向转换过滤器
 *
 * @author <a href="https://github.com/dlmyL">dlmyL</a>
 * @version 1.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RedirectionTransformFilter implements TransformFilter {

    private final WebFluxServerResponseWriter webFluxServerResponseWriter;

    @Override
    public void init(TransformContext context) {

    }

    @Override
    public void doFilter(TransformFilterChain chain, TransformContext context) {
        if (TransformStatus.TRANSFORM_SUCCESS == context.getTransformStatus()) {
            String longUrlString = context.getParam(TransformContext.PARAM_LONG_URL_KEY);
            try {
                if (StringUtils.isNotBlank(longUrlString)) {
                    Runnable redirectAction = webFluxServerResponseWriter.redirectAction(
                            context.getParam(TransformContext.PARAM_SERVER_WEB_EXCHANGE_KEY),
                            longUrlString
                    );
                    context.setRedirectAction(redirectAction);
                    context.setParam(TransformContext.PARAM_TARGET_LONG_URL_KEY, longUrlString);
                    context.setTransformStatus(TransformStatus.REDIRECTION_SUCCESS);
                } else {
                    context.setTransformStatus(TransformStatus.REDIRECTION_FAIL);
                    log.warn("重定向到长链接失败,长链值为空,压缩码:{}", context.getCompressionCode());
                }
            } catch (Exception e) {
                log.error("重定向到长链接[{}]失败,压缩码:{}", longUrlString, context.getCompressionCode(), e);
                context.setTransformStatus(TransformStatus.REDIRECTION_FAIL);
            }
        }
        chain.doFilter(context);
    }

    @Override
    public int order() {
        return 3;
    }

}
