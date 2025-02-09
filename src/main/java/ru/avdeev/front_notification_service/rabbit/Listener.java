package ru.avdeev.front_notification_service.rabbit;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import ru.avdeev.front_notification_service.bot.Bot;
import ru.avdeev.front_notification_service.bot.BotProperties;
import ru.avdeev.front_notification_service.dto.Booking;
import ru.avdeev.front_notification_service.dto.Event;
import ru.avdeev.front_notification_service.repository.EventRepository;

import java.util.Objects;

@Component
@EnableRabbit
@AllArgsConstructor
@Slf4j
public class Listener {
    private final BotProperties botProperties;
    private final EventRepository eventRepository;
    private final Bot bot;

    @RabbitListener(queuesToDeclare = @Queue(name = "${queues.frontIn}", durable = "true"))
    public void frontInListener(Message<Object> message) {

        String type = Objects.requireNonNull(message.getHeaders().get("type")).toString();
        Object data = message.getPayload();

        Event event = new Event();
        event.setType(type);
        event.setData(data);

        receiveLog("frontInListener" ,type, event);

        eventRepository.put(event)
                .subscribe(msg -> log.info("Message save to EventRepository: {}", msg));
    }

    @RabbitListener(queuesToDeclare = @Queue(name = "${queues.telegramIn}", durable = "true"))
    public void telegramInListener(Message<Booking> message) {

        String type = Objects.requireNonNull(message.getHeaders().get("type")).toString();
        if (!type.equals("BookingCreated")) {
            return;
        }
        Booking booking = message.getPayload();
        receiveLog("telegramInListener", type, booking);

        String msgText = String.format("Забронирован стол 2. Дата: %1$td-%1$tm-%1$tY. Время начала: %2$s. Продолжительность: %3$1.1f ч.",
                booking.bookingDate(),
                booking.startTime(),
                booking.count()
        );
        botProperties.getReceivers().forEach(chatId -> bot.sendMessage(chatId, msgText));
        log.info("Message sent to telegram: {}", msgText);
    }

    private void receiveLog(String qName, String type, Object data) {
        log.info("{}: received message\ntype: {}\nbody: {}", qName, type, data);
    }
}
