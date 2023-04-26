package ru.tinkoff.edu.java.scrapper.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory("localhost");
        cachingConnectionFactory.setPort(5672);
        cachingConnectionFactory.setUsername("guest");
        cachingConnectionFactory.setPassword("guest");
//        cachingConnectionFactory.setVirtualHost("cpp");
        return cachingConnectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(MessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

    @Bean
    public DirectExchange directExchange(ApplicationConfig config) {
        return new DirectExchange(config.exchangeName(), true, false);
    }

    @Bean
    public Queue queue(ApplicationConfig config) {
        return new Queue(config.queryName());
    }

    @Bean
    public Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue)
                             .to(exchange)
                             .withQueueName();
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
