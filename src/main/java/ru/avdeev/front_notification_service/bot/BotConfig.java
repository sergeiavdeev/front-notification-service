package ru.avdeev.front_notification_service.bot;

import lombok.AllArgsConstructor;
//import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.BotSession;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Configuration
@AllArgsConstructor
@Slf4j
public class BotConfig {

    @Bean
    public TelegramClient telegramClient(@Value("${bot.token}")String token) {
        return new OkHttpTelegramClient(token);
    }

    @Bean
    public TelegramBotsLongPollingApplication telegramBotsLongPollingApplication() {
        return new TelegramBotsLongPollingApplication();
    }

    @Bean
    public BotSession botSession(TelegramBotsLongPollingApplication application, Bot bot, @Value("${bot.token}")String token) {
        try {
            return application.registerBot(token, bot);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}