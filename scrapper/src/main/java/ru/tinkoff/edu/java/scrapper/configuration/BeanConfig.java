package ru.tinkoff.edu.java.scrapper.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {
    @Bean
    public long schedulingIntervalMillis(ApplicationConfig config) {
        return config.scheduler().interval().toMillis();
    }
}
