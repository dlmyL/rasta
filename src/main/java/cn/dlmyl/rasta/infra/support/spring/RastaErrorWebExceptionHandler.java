package cn.dlmyl.rasta.infra.support.spring;

import cn.dlmyl.rasta.infra.exception.RedirectToErrorPageException;
import cn.dlmyl.rasta.infra.util.IpUtils;
import cn.dlmyl.rasta.infra.util.JacksonUtils;
import cn.dlmyl.rasta.infra.util.StringLimitUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.lang.NonNull;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * WebFlux 异常处理器
 *
 * @author <a href="https://github.com/dlmyL">dlmyL</a>
 * @version 1.0
 */
@Slf4j
public class RastaErrorWebExceptionHandler implements ErrorWebExceptionHandler {

    private WebFluxServerResponseWriter webFluxServerResponseWriter;

    private static final int LIMIT_LEN = 128;

    private String errorPageUrl;

    public void setErrorPageUrl(String url) {
        this.errorPageUrl = url;
    }

    public void setWebFluxServerResponseWriter(WebFluxServerResponseWriter webFluxServerResponseWriter) {
        this.webFluxServerResponseWriter = webFluxServerResponseWriter;
    }

    @Override
    @NonNull
    public Mono<Void> handle(ServerWebExchange serverWebExchange, @NonNull Throwable throwable) {
        ServerHttpRequest request = serverWebExchange.getRequest();
        String clientIp = IpUtils.X.extractClientIp(request);
        log.error("处理URI:{}请求发生异常,客户端IP:{},", request.getURI(), clientIp, throwable);
        if (throwable instanceof RedirectToErrorPageException) {
            return webFluxServerResponseWriter.redirect(
                    serverWebExchange,
                    errorPageUrl
            );
        } else {
            ControllerResponse controllerResponse = new ControllerResponse();
            String message = StringLimitUtils.X.limitString(throwable.getMessage(), LIMIT_LEN);
            controllerResponse.setCode(ControllerResponse.ERROR);
            controllerResponse.setMessage(message);
            return webFluxServerResponseWriter.write(
                    serverWebExchange,
                    JacksonUtils.X.format(controllerResponse),
                    throwable
            );
        }
    }

    @Data
    private static class ControllerResponse {
        public static final Long ERROR = 500L;
        private Long code;
        private String message;
    }

}
