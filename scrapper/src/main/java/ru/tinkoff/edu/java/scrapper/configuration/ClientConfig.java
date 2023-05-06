package ru.tinkoff.edu.java.scrapper.configuration;

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
public class ClientConfig {
    public static final int TIMEOUT = 5000;
    @Value("${github.webclient.base-url}")
    private String gitHubBaseUrl;
    @Value("${stackoverflow.webclient.base-url}")
    private String stackOverflowBaseUrl;
    @Value("${bot.webclient.base-url}")
    private URI botBaseUrl;

    private static final String APPLICATION_JSON = "application/json";

    @Bean
    public WebClient gitHubClientWithTimeout() {
        final HttpClient httpClient = HttpClient
            .create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, TIMEOUT)
            .responseTimeout(Duration.ofMillis(TIMEOUT))
            .doOnConnected(connection -> {
                connection.addHandlerLast(new ReadTimeoutHandler(TIMEOUT, TimeUnit.MILLISECONDS));
                connection.addHandlerLast(new WriteTimeoutHandler(TIMEOUT, TimeUnit.MILLISECONDS));
            });
        return WebClient.builder()

                        .baseUrl(gitHubBaseUrl)
                        .defaultHeader(HttpHeaders.ACCEPT, "application/vnd.github+json")
                        .defaultHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON)
                        .clientConnector(new ReactorClientHttpConnector(httpClient))
                        .build();
    }

    @Bean
    public WebClient stackOverflowClientWithTimeout() {
        final HttpClient httpClient = HttpClient
            .create()
            .compress(true)
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, TIMEOUT)
            .responseTimeout(Duration.ofMillis(TIMEOUT))
            .doOnConnected(connection -> {
                connection.addHandlerLast(new ReadTimeoutHandler(TIMEOUT, TimeUnit.MILLISECONDS));
                connection.addHandlerLast(new WriteTimeoutHandler(TIMEOUT, TimeUnit.MILLISECONDS));
            });

        return WebClient.builder()
                        .baseUrl(stackOverflowBaseUrl)
                        .defaultHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON)
                        .defaultHeader(HttpHeaders.ACCEPT_ENCODING, "gzip")
                        .clientConnector(new ReactorClientHttpConnector(httpClient))
                        .build();
    }

    @Bean
    public WebClient botClientWithTimeout() {
        final HttpClient httpClient = HttpClient
            .create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, TIMEOUT)
            .responseTimeout(Duration.ofMillis(TIMEOUT))
            .doOnConnected(connection -> {
                connection.addHandlerLast(new ReadTimeoutHandler(TIMEOUT, TimeUnit.MILLISECONDS));
                connection.addHandlerLast(new WriteTimeoutHandler(TIMEOUT, TimeUnit.MILLISECONDS));
            });

        return WebClient.builder()
                        .baseUrl(botBaseUrl.toString())
                        .defaultHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON)
                        .clientConnector(new ReactorClientHttpConnector(httpClient))
                        .build();
    }
}
