package cn.dlmyl.rasta.filter;

import cn.dlmyl.rasta.infra.common.TransformStatus;
import cn.dlmyl.rasta.infra.exception.RedirectToErrorPageException;
import cn.dlmyl.rasta.manager.cache.MappingCacheManager;
import cn.dlmyl.rasta.model.entity.Mapping;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 短链转换过滤器
 *
 * @author <a href="https://github.com/dlmyL">dlmyL</a>
 * @version 1.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MappingTransformFilter implements TransformFilter {

    private final MappingCacheManager mappingCacheManager;

    @Override
    public void init(TransformContext context) {

    }

    @Override
    public void doFilter(TransformFilterChain chain, TransformContext context) {
        String compressionCode = context.getCompressionCode();
        Mapping mapping = mappingCacheManager.loadUrlMapCacheByCompressCode(compressionCode);
        context.setTransformStatus(TransformStatus.TRANSFORM_FAIL);
        if (Objects.nonNull(mapping)) {
            context.setTransformStatus(TransformStatus.TRANSFORM_SUCCESS);
            context.setParam(TransformContext.PARAM_LONG_URL_KEY, mapping.getLongUrl());
            context.setParam(TransformContext.PARAM_SHORT_URL_KEY, mapping.getShortUrl());
            chain.doFilter(context);
        } else {
            log.warn("压缩码[{}]不存在或异常,终止TransformFilterChain执行,并且重定向到404页面......", compressionCode);
            throw new RedirectToErrorPageException(String.format("[c:%s]", compressionCode));
        }
    }

    @Override
    public int order() {
        return 2;
    }

}
