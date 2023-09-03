package cn.dlmyl.rasta.filter.spring;

import cn.dlmyl.rasta.infra.support.spring.WebFluxServerResponseWriter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.lang.NonNull;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import static cn.dlmyl.rasta.infra.config.RastaConfig.ERROR_PAGE_URL;

/**
 * WebFilter 抽象
 *
 * @author <a href="https://github.com/dlmyL">dlmyL</a>
 * @version 1.0
 */
public abstract class AbstractWebFilter implements WebFilter, BeanFactoryAware {

    private WebFluxServerResponseWriter webFluxServerResponseWriter;

    @NonNull
    @Override
    public void setBeanFactory(@NonNull BeanFactory beanFactory) throws BeansException {
        webFluxServerResponseWriter = beanFactory.getBean(WebFluxServerResponseWriter.class);
    }

    @NonNull
    @Override
    public Mono<Void> filter(@NonNull ServerWebExchange exchange, @NonNull WebFilterChain chain) {
        return doFilter(exchange, chain);
    }

    public Mono<Void> redirect(@NonNull ServerWebExchange exchange) {
        return webFluxServerResponseWriter.redirect(exchange, ERROR_PAGE_URL);
    }

    public abstract Mono<Void> doFilter(ServerWebExchange exchange, WebFilterChain chain);

}
