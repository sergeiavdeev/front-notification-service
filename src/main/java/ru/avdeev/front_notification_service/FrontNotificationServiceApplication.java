package ru.avdeev.front_notification_service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class FrontNotificationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FrontNotificationServiceApplication.class, args);

		log.error("Error: {}", "Кусок говна!");
	}
}
