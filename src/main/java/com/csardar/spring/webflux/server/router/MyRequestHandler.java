package com.csardar.spring.webflux.server.router;

import com.csardar.spring.webflux.common.MyMessage;
import com.csardar.spring.webflux.common.MyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Slf4j
@Component
public class MyRequestHandler {

    @Autowired
    private MyService myService;

    public Mono<ServerResponse> getProgress(ServerRequest serverRequest){

        Optional<String> numberOption = serverRequest.queryParam("number");

        int number = numberOption.map(Integer::parseInt).orElse(10);

        log.info("getProgress() - number = {}", number);

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(myService.streamProgress(number), MyMessage.class);
    }
}
