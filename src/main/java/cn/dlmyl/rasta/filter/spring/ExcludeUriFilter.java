package cn.dlmyl.rasta.filter.spring;

import cn.dlmyl.rasta.infra.common.CommonConstant;
import cn.dlmyl.rasta.infra.config.RastaConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * 非法 URI 过滤器
 *
 * @author <a href="https://github.com/dlmyL">dlmyL</a>
 * @version 1.0
 */
@Slf4j
@Component
@Order(value = Integer.MIN_VALUE + 3)
public class ExcludeUriFilter extends AbstractWebFilter {

    @Override
    public Mono<Void> doFilter(ServerWebExchange exchange, WebFilterChain chain) {
        String uri = exchange.getRequest().getURI().toString();
        if (CommonConstant.FAVICON.equals(uri)) {
            return Mono.empty();
        }
        if (Objects.nonNull(RastaConfig.EXCLUDE_URIS) && !RastaConfig.EXCLUDE_URIS.contains(uri)) {
            return chain.filter(exchange);
        }
        log.warn("URI异常,命中非法URI[i:{}],跳转到404页面", uri);
        return super.redirect(exchange);
    }

}