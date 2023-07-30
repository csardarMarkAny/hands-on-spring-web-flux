package com.csardar.spring.webflux.server.router;

import com.csardar.spring.webflux.server.router.MyRequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@Configuration(proxyBeanMethods = false)
public class MyWebFluxRouter {

    @Autowired
    private MyRequestHandler myRequestHandler;

    @Bean
    public RouterFunction<ServerResponse> route() {

        return RouterFunctions.route(GET("/hello"), myRequestHandler::getProgress);
    }
}
