package com.csardar.spring.webflux.common;

import com.csardar.spring.webflux.common.MyMessage;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class MyService {

    public Flux<MyMessage> streamProgress(final int number) {

        Flux<MyMessage> events = Flux.fromStream(getMessageStream(number));
        Flux<Long> interval = Flux.interval(Duration.ofSeconds(1));
        return Flux.zip(events, interval, (key, value) -> key);
    }

    private Stream<MyMessage> getMessageStream(final int number) {

        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(new Iterator<>() {

            int counter = 0;

            @Override
            public boolean hasNext() {
                return counter < number;
            }

            @Override
            public MyMessage next() {

                counter++;

                return MyMessage.builder()
                        .message("Hello Chitta!")
                        .timeStamp(LocalDateTime.now())
                        .build();
            }
        }, Spliterator.IMMUTABLE), false);
    }
}
