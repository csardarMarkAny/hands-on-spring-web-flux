package com.csardar.spring.webflux.server.anotation;

import com.csardar.spring.webflux.common.ProgressMessage;
import com.csardar.spring.webflux.common.ProgressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@Slf4j
@RestController
public class ProgressController {

    @Autowired
    private ProgressService progressService;

    @GetMapping(value = "/progress-percentages/{info_id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ProgressMessage> getProgressPercentage(@PathVariable final Long info_id, ServerHttpRequest serverHttpRequest) {

        log.info("getProgressPercentage() - info_id = {}", info_id);

        // Extra: Not mandatory --->
        HttpHeaders httpHeaders = serverHttpRequest.getHeaders();
        List<String> authHeaderList = httpHeaders.get("Authorization");
        if (authHeaderList != null && authHeaderList.size() > 0) {
            log.info("helloWebFluxAnnotation() - Header = {}", authHeaderList.get(0));
        }
        // Extra: Not mandatory <---

        return progressService.streamProgress(info_id);
    }
}
