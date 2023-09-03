package cn.dlmyl.rasta.controller;

import cn.dlmyl.rasta.filter.TransformContext;
import cn.dlmyl.rasta.infra.common.MappingStatus;
import cn.dlmyl.rasta.infra.config.RastaConfig;
import cn.dlmyl.rasta.infra.util.ValidatorUtils;
import cn.dlmyl.rasta.model.entity.Mapping;
import cn.dlmyl.rasta.model.request.CreateMappingRequest;
import cn.dlmyl.rasta.model.response.CreateMappingResponse;
import cn.dlmyl.rasta.model.response.Response;
import cn.dlmyl.rasta.service.MappingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 短链服务控制器
 *
 * @author <a href="https://github.com/dlmyL">dlmyL</a>
 * @version 1.0
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class RastaController {

    private final MappingService mappingService;

    @PostMapping("/create")
    public Mono<Response<CreateMappingResponse>> create(@RequestBody CreateMappingRequest request) {
        Mapping mapping = Mapping.builder()
                .urlStatus(MappingStatus.AVAILABLE.getValue())
                .longUrl(request.getLongUrl())
                .description(request.getDescription())
                .build();
        String shortUrl = mappingService.createMapping(RastaConfig.DEFAULT_DOMAIN, mapping);
        log.info("创建短链接成功，生成的短链码: {}", shortUrl);
        return Mono.just(Response.succeed(CreateMappingResponse.builder().shortUrl(shortUrl).build()));
    }

    @GetMapping("/{code}")
    @ResponseStatus(HttpStatus.FOUND)
    public Mono<Void> dispatch(@PathVariable(name = "code") String code, ServerWebExchange exchange) {
        ValidatorUtils.X.check(code);
        TransformContext context = TransformContext.assemble(code, exchange);
        mappingService.processTransform(context);
        return Mono.fromRunnable(context.getRedirectAction());
    }

}