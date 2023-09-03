package cn.dlmyl.rasta.filter.spring;

import cn.dlmyl.rasta.infra.util.IpUtils;
import cn.dlmyl.rasta.manager.cache.BlockIpCacheManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * IP 黑名单过滤器
 *
 * @author <a href="https://github.com/dlmyL">dlmyL</a>
 * @version 1.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
@Order(value = Integer.MIN_VALUE + 1)
public class BlockIpFilter extends AbstractWebFilter {

    private final BlockIpCacheManager blockIpCacheManager;

    @Override
    public Mono<Void> doFilter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String clientIp = IpUtils.X.extractClientIp(exchange.getRequest());
        if (!blockIpCacheManager.isInBlockList(clientIp)) {
            return chain.filter(exchange);
        }
        log.warn("请求异常,命中IP黑名单[i:{}:{}],跳转到404页面", clientIp, request.getURI());
        return super.redirect(exchange);
    }

}