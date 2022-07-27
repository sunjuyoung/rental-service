package com.example.apigateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class PreCustomFilter extends AbstractGatewayFilterFactory<PreCustomFilter.Config> {

    public PreCustomFilter() {
        super(Config.class);
    }

    public static class Config{

    }

    @Override
    public GatewayFilter apply(Config config) {
        //custom pre filter
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            log.info("Custom pre filter : request id : {}", request.getId());

            return chain.filter(exchange.mutate().request(request).build());
        });
    }
}
