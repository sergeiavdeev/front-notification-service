package ru.avdeev.front_notification_service.controller;

import io.rsocket.RSocket;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import ru.avdeev.front_notification_service.dto.Booking;
import ru.avdeev.front_notification_service.repository.EventRepository;

@Slf4j
@Controller
@MessageMapping("ttc-tops")
@AllArgsConstructor
public class RSocketController {

    private final EventRepository eventRepository;

    @MessageMapping("stream")
    Flux<Booking> stream(RSocket rSocket) {

        return eventRepository.findAll()
                .doOnNext(booking -> log.info("Send event: {}", booking));
    }
}
