package cn.dlmyl.rasta.filter;

import cn.dlmyl.rasta.infra.common.TransformStatus;
import com.alibaba.ttl.TransmittableThreadLocal;
import com.google.common.collect.Maps;
import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * 事件转换上下文
 *
 * @author <a href="https://github.com/dlmyL">dlmyL</a>
 * @version 1.0
 */
@Data
public class TransformContext {

    public static final String PARAM_CLIENT_ID_KEY = "CLIENT_ID";
    public static final String PARAM_UA_KEY = "UA";
    public static final String PARAM_COOKIE_KEY = "COOKIE";
    public static final String PARAM_SHORT_URL_KEY = "SL";
    public static final String PARAM_LONG_URL_KEY = "LL";
    public static final String PARAM_TARGET_LONG_URL_KEY = "RLL";
    public static final String PARAM_REMOTE_HOST_NAME_KEY = "RHN";
    public static final String PARAM_SERVER_WEB_EXCHANGE_KEY = "__SWE__";

    final ThreadLocal<Runnable> redirectAction = new TransmittableThreadLocal<>();

    private String compressionCode;

    private TransformStatus transformStatus;

    private Map<String, String> headers = Maps.newHashMap();

    private Map<String, Object> params = Maps.newHashMap();

    public static TransformContext assemble(String code, ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        TransformContext context = new TransformContext();
        context.setCompressionCode(code);
        context.setParam(TransformContext.PARAM_SERVER_WEB_EXCHANGE_KEY, exchange);
        if (Objects.nonNull(request.getRemoteAddress())) {
            context.setParam(TransformContext.PARAM_REMOTE_HOST_NAME_KEY, request.getRemoteAddress().getHostName());
        }
        HttpHeaders httpHeaders = request.getHeaders();
        Set<String> headerNames = httpHeaders.keySet();
        if (!CollectionUtils.isEmpty(headerNames)) {
            headerNames.forEach(headerName -> {
                String headerValue = httpHeaders.getFirst(headerName);
                context.setHeader(headerName, headerValue);
            });
        }
        return context;
    }

    public void setTransformStatus(TransformStatus transformStatus) {
        this.transformStatus = transformStatus;
    }

    public Integer getTransformStatusValue() {
        return transformStatus.getValue();
    }

    public void addHeader(String key, String value) {
        headers.putIfAbsent(key, value);
    }

    public void setHeader(String key, String value) {
        headers.put(key, value);
    }

    public String getHeader(String key) {
        return headers.get(key);
    }

    public void releaseHeaders() {
        headers = null;
    }

    public void addParam(String key, Object value) {
        params.putIfAbsent(key, value);
    }

    public void setParam(String key, Object value) {
        params.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public <T> T getParam(String key) {
        return (T) params.get(key);
    }

    public void setRedirectAction(Runnable redirectAction) {
        this.redirectAction.set(redirectAction);
    }

    public Runnable getRedirectAction() {
        Runnable redirectAction = this.redirectAction.get();
        return Objects.nonNull(redirectAction) ? redirectAction : () -> {
        };
    }

    public void release() {
        headers = null;
        params = null;
    }

}
