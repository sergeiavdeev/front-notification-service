package ru.avdeev.front_notification_service.dto;

import lombok.Data;

@Data
public class Event {
    private String type;
    private Object data;
}
