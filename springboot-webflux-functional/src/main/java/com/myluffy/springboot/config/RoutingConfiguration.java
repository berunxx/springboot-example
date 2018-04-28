package com.myluffy.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RoutingConfiguration {

    @Bean
    public RouterFunction<ServerResponse> monoRouterFunction(UserHandler userHandler) {
        return route(GET("/api/user/{id}"), userHandler::getUser)
                .andRoute(GET("/api/user/index").and(accept(APPLICATION_JSON)), userHandler::getUsers)
                .andRoute(POST("/api/user/post").and(accept(APPLICATION_JSON)), userHandler::postUser)
                .andRoute(PUT("/api/user/put/{id}").and(accept(APPLICATION_JSON)), userHandler::putUser)
                .andRoute(DELETE("/api/user/delete/{id}").and(accept(APPLICATION_JSON)), userHandler::deleteUser);
    }

}