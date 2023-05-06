package ru.tinkoff.edu.java.bot.configuration;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import java.net.URI;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Configuration
public class ClientConfiguration {
    public static final int TIMEOUT = 5000;
    @Value("${scrapper.webclient.base-url}")
    private URI baseUrl;

    @Bean
    public WebClient webClientWithTimeout() {
        final HttpClient httpClient = HttpClient
            .create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, TIMEOUT)
            .responseTimeout(Duration.ofMillis(TIMEOUT))
            .doOnConnected(connection -> {
                connection.addHandlerLast(new ReadTimeoutHandler(TIMEOUT, TimeUnit.MILLISECONDS));
                connection.addHandlerLast(new WriteTimeoutHandler(TIMEOUT, TimeUnit.MILLISECONDS));
            });

        return WebClient.builder()
                        .baseUrl(baseUrl.toString())
                        .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                        .clientConnector(new ReactorClientHttpConnector(httpClient))
                        .build();
    }
}
