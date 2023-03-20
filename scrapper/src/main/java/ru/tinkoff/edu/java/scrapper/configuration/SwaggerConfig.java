package ru.tinkoff.edu.java.scrapper.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Scrapper API")
                        .description("Endpoints implemented according to " +
                                "<a href=\"https://gist.github.com/sanyarnd/52a78a01fa9ec234c8ad50fbc5ecc9e4\" target=\"_blank\">specification</a>")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Roman Olontsev")
                                .email("rs.olontsev@gmail.com")));
    }
}