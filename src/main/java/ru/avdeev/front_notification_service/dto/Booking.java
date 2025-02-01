package ru.avdeev.front_notification_service.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record Booking(
        UUID id,
        UUID resourceId,
        UUID userId,
        LocalDate bookingDate,
        LocalTime startTime,
        LocalTime endTime,
        Double count
) {
}