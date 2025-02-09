package ru.avdeev.front_notification_service.repository;

import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import ru.avdeev.front_notification_service.dto.Booking;
import ru.avdeev.front_notification_service.dto.Event;

@Repository
public class EventRepository {

    final Sinks.Many<Event> repo = Sinks.many().multicast().directBestEffort();

    public Flux<Event> findAll() {
        return repo.asFlux();
    }

    public Mono<Event> put(Event event) {
        repo.emitNext(event, (signalType, emission) -> emission == Sinks.EmitResult.FAIL_NON_SERIALIZED);
        return Mono.just(event);
    }
}
