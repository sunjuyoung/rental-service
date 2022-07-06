package com.example.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class FilterConfig {

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder){
        return builder.routes()
                .route(r -> r.path("/user-service/**")
                        .filters(f -> f.addRequestHeader("user-service","request-value")
                                        .addResponseHeader("user-service","response-value"))
                        .uri("http://127.0.0.1:8081"))
                .route(r -> r.path("/book-service/**")
                        .filters(f -> f.addRequestHeader("book-service","request-value")
                                .addResponseHeader("book-service","response-value"))
                        .uri("http://127.0.0.1:8082"))
                .build();
    }
}
