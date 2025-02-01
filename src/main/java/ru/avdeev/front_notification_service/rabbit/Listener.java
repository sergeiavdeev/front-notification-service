package ru.avdeev.front_notification_service.rabbit;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import ru.avdeev.front_notification_service.config.QueueConfig;
import ru.avdeev.front_notification_service.dto.Booking;
import ru.avdeev.front_notification_service.repository.EventRepository;

import java.util.Objects;

@Component
@EnableRabbit
@AllArgsConstructor
public class Listener {

    private final Logger log = LoggerFactory.getLogger(Listener.class);
    private final EventRepository eventRepository;
    private final QueueConfig queueConfig;

    //@RabbitListener(queues = "front.in")
    @RabbitListener(queues = "#{queueConfig.queueFrontIn}")
    public void onMessage(Message<Booking> message) {

        String type = Objects.requireNonNull(message.getHeaders().get("type")).toString();
        Booking booking = message.getPayload();
        log.info("Received message\ntype: {}\nbody: {}", type, booking);
        eventRepository.put(booking)
                .subscribe();
    }
}
