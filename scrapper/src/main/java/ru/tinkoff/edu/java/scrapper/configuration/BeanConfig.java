package ru.tinkoff.edu.java.scrapper.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.tinkoff.edu.java.parser.Parser;
import ru.tinkoff.edu.java.parser.links.GitHubLinkParse;
import ru.tinkoff.edu.java.parser.links.LinkParse;
import ru.tinkoff.edu.java.parser.links.StackOverflowLinkParse;

@Configuration
@EnableScheduling
public class BeanConfig {
    @Bean
    public long schedulingIntervalMillis(ApplicationConfig config) {
        return config.scheduler()
                     .interval()
                     .toMillis();
    }

    @Bean
    public Parser linkParser() {
        Parser parser = new Parser();
        parser.setLinks(LinkParse.link(new GitHubLinkParse(),
                new StackOverflowLinkParse()));
        return parser;
    }
}
