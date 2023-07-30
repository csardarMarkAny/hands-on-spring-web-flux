package com.csardar.spring.webflux.server.anotation;

import com.csardar.spring.webflux.common.MyMessage;
import com.csardar.spring.webflux.common.MyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@Slf4j
@RestController
public class MyRestController {

    @Autowired
    private MyService myService;

    @GetMapping(value = "/hello-web-flux-annotation", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<MyMessage> helloWebFluxAnnotation(@RequestParam final Integer number, ServerHttpRequest serverHttpRequest) {

        log.info("helloWebFluxAnnotation() - number = {}", number);

        // Extra: Not mandatory --->
        HttpHeaders httpHeaders = serverHttpRequest.getHeaders();
        List<String> authHeaderList = httpHeaders.get("Authorization");
        if (authHeaderList != null && authHeaderList.size() > 0) {
            log.info("helloWebFluxAnnotation() - Header = {}", authHeaderList.get(0));
        }
        // Extra: Not mandatory <---

        return myService.streamProgress(number);
    }

}
