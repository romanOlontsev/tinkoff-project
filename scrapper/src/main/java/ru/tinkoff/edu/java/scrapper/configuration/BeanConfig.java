package ru.tinkoff.edu.java.scrapper.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class BeanConfig {
    @Bean
    public long schedulingIntervalMillis(ApplicationConfig config) {
        return config.scheduler().interval().toMillis();
    }
}
