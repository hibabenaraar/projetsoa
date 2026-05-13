package com.example.gateway_service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;
import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.uri;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;
import static org.springframework.web.servlet.function.RequestPredicates.path;

@Configuration
public class GatewayConfig {

    @Bean
    public RouterFunction<ServerResponse> gatewayRoutes() {
        return route("r4")
                .route(path("/shipping/**"), http())
                .before(uri("http://localhost:8088/"))
                .build()
                .and(route("r2")
                        .route(path("/inventory/**"), http())
                        .before(uri("http://localhost:8081/"))
                        .build())
                .and(route("r1")
                        .route(path("/api/payments/**"), http())
                        .before(uri("http://localhost:3001/"))
                        .build())
                .and(route("r3")
                        .route(path("/orders/**").or(path("/orders")), http())
                        .before(uri("http://localhost:8085/"))
                        .build())
                .and(route("r5")
                        .route(path("/api/currency/**"), http())
                        .before(uri("http://localhost:8082/"))
                        .build())
                .and(route("r6")
                        .route(path("/billing/**").or(path("/billing")), http())
                        .before(uri("http://localhost:8092/"))
                        .build());
    }
    
}
