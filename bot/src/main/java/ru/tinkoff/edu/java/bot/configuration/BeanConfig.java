package ru.tinkoff.edu.java.bot.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {
    @Bean
    public TelegramConfig telegramConfig(ApplicationConfig config) {
        return config.bot();
    }
}
