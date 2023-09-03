package cn.dlmyl.rasta.filter.spring;

import cn.dlmyl.rasta.infra.util.IdUtils;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * TRACE_ID 追踪
 *
 * @author <a href="https://github.com/dlmyL">dlmyL</a>
 * @version 1.0
 */
@Component
@Order(value = Integer.MIN_VALUE)
public class MappedDiagnosticContextFilter extends AbstractWebFilter {

    @Override
    public Mono<Void> doFilter(ServerWebExchange exchange, WebFilterChain chain) {
        MDC.put("TRACE_ID", IdUtils.X.simpleUUID());
        return chain.filter(exchange).then(Mono.fromRunnable(() -> MDC.remove("TRACE_ID")));
    }

}
