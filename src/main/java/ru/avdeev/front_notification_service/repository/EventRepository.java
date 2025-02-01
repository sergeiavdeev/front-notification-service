package ru.avdeev.front_notification_service.repository;

import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import ru.avdeev.front_notification_service.dto.Booking;

@Repository
public class EventRepository {

    final Sinks.Many<Booking> repo = Sinks.many().multicast().directBestEffort();

    public Flux<Booking> findAll() {
        return repo.asFlux();
    }

    public Mono<Booking> put(Booking event) {
        repo.emitNext(event, (signalType, emission) -> emission == Sinks.EmitResult.FAIL_NON_SERIALIZED);
        return Mono.just(event);
    }
}
