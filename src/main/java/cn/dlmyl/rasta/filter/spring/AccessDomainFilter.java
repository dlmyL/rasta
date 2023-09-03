package cn.dlmyl.rasta.filter.spring;

import cn.dlmyl.rasta.manager.cache.AccessDomainCacheManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * 域名白名单过滤器
 *
 * @author <a href="https://github.com/dlmyL">dlmyL</a>
 * @version 1.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
@Order(value = Integer.MIN_VALUE + 2)
public class AccessDomainFilter extends AbstractWebFilter {

    private final AccessDomainCacheManager accessDomainCacheManager;

    @Override
    public Mono<Void> doFilter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String domain = request.getURI().getHost();
        if (accessDomainCacheManager.checkDomainValid(domain)) {
            return chain.filter(exchange);
        } else {
            log.warn("请求异常,命中非法域名[d:{}],跳转到404页面", domain);
            return super.redirect(exchange);
        }
    }

}
