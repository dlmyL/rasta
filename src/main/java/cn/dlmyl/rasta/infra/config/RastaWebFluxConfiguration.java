package cn.dlmyl.rasta.infra.config;

import cn.dlmyl.rasta.infra.support.spring.RastaErrorWebExceptionHandler;
import cn.dlmyl.rasta.infra.support.spring.WebFluxServerResponseWriter;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.result.view.ViewResolver;

import java.util.Collections;
import java.util.List;

/**
 * WebFlux 自动装配
 *
 * @author <a href="https://github.com/dlmyL">dlmyL</a>
 * @version 1.0
 */
@Configuration
public class RastaWebFluxConfiguration {

    @Primary
    @Bean
    public ErrorWebExceptionHandler errorWebExceptionHandler(WebFluxServerResponseWriter webFluxServerResponseWriter) {
        RastaErrorWebExceptionHandler errorWebExceptionHandler = new RastaErrorWebExceptionHandler();
        errorWebExceptionHandler.setWebFluxServerResponseWriter(webFluxServerResponseWriter);
        errorWebExceptionHandler.setErrorPageUrl(RastaConfig.ERROR_PAGE_URL);
        return errorWebExceptionHandler;
    }

    @Bean
    public WebFluxServerResponseWriter webFluxServerResponseWriter(ObjectProvider<List<ViewResolver>> viewResolversProvider,
                                                                   ServerCodecConfigurer serverCodecConfigurer) {
        WebFluxServerResponseWriter webFluxServerResponseWriter = new WebFluxServerResponseWriter();
        webFluxServerResponseWriter.setMessageReaders(serverCodecConfigurer.getReaders());
        webFluxServerResponseWriter.setMessageWriters(serverCodecConfigurer.getWriters());
        webFluxServerResponseWriter.setViewResolvers(viewResolversProvider.getIfAvailable(Collections::emptyList));
        return webFluxServerResponseWriter;
    }

}
