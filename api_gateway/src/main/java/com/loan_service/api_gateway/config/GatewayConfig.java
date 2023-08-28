package com.loanservice.apigateway.config;

import com.loanservice.apigateway.auth.JwtAuthenticationFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

    @Configuration
    public class GatewayConfig {


        /*@Bean
        public JwtAuthenticationFilter jwtAuthenticationFilter() {
            return new JwtAuthenticationFilter();
        }*/


        @Bean
        public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
            return builder.routes()
                    .route("secure_route", r -> r
                            .path("/secure/**")
                            .filters(f -> f
                                    .filter(new JwtAuthenticationFilter()) 
                            )
                            .uri("lb://secure-service") // actual service URI
                    )
                    .build();
        }
    }


