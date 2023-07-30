package com.csardar.spring.webflux.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class ProgressService {

    public Flux<ProgressMessage> streamProgress(final Long info_id) {

        Flux<ProgressMessage> events = Flux.fromStream(getMessageStream(info_id));
        Flux<Long> interval = Flux.interval(Duration.ofSeconds(1));
        return Flux.zip(events, interval, (key, value) -> key);
    }

    private Stream<ProgressMessage> getMessageStream(Long info_id) {

        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(new Iterator<>() {

            final int numberOfProgress = getRandomNumber(5, 10);
            int counter = 0;
            final float fraction = ((float)100)/numberOfProgress;

            @Override
            public boolean hasNext() {
                return counter < numberOfProgress;
            }

            @Override
            public ProgressMessage next() {

                log.info("numberOfProgress = {}, fraction = {}", numberOfProgress, fraction);

                counter++;

                return ProgressMessage.builder()
                        .info_id(info_id)
                        .percent(counter*fraction)
                        .build();
            }
        }, Spliterator.IMMUTABLE), false);
    }

    private int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
