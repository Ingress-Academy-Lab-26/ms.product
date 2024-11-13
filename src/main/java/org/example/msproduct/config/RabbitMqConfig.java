package org.example.msproduct.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    private final String orderQ;
    private final String orderDLQ;
    private final String orderQExchange;
    private final String orderDLQExchange;
    private final String orderQKey;
    private final String orderDLQKey;

    private final String ratingQ;
    private final String ratingDLQ;
    private final String ratingQExchange;
    private final String ratingDLQExchange;
    private final String ratingQKey;
    private final String ratingDLQKey;

    private final String subscriptionQ;
    private final String subscriptionDLQ;
    private final String subscriptionQExchange;
    private final String subscriptionDLQExchange;
    private final String subscriptionQKey;
    private final String subscriptionDLQKey;

    public RabbitMqConfig(@Value("${rabbitmq.queue.order-service.queue}") String orderQ,
                          @Value("${rabbitmq.queue.order-service.queue_dlq}") String orderDLQ,
                          @Value("${rabbitmq.queue.rating-service.queue}") String ratingQ,
                          @Value("${rabbitmq.queue.rating-service.queue_dlq}") String ratingDLQ,
                          @Value("${rabbitmq.queue.subscription-service.queue}") String subscriptionQ,
                          @Value("${rabbitmq.queue.subscription-service.queue_dlq}") String subscriptionDLQ
                          ) {

        this.orderQ = orderQ;
        this.orderDLQ = orderDLQ;
        this.orderQExchange = orderQ + "_EXCHANGE";
        this.orderDLQExchange = orderDLQ + "_EXCHANGE";
        this.orderQKey = orderQ + "_KEY";
        this.orderDLQKey = orderDLQ + "_KEY";

        this.ratingQ = ratingQ;
        this.ratingDLQ = ratingDLQ;
        this.ratingQExchange = ratingQ + "_EXCHANGE";
        this.ratingDLQExchange = ratingDLQ + "_EXCHANGE";
        this.ratingQKey = ratingQ + "_KEY";
        this.ratingDLQKey = ratingDLQ + "_KEY";

        this.subscriptionQ = subscriptionQ;
        this.subscriptionDLQ = subscriptionDLQ;
        this.subscriptionQExchange = subscriptionQ + "_EXCHANGE";
        this.subscriptionDLQExchange = subscriptionDLQ + "_EXCHANGE";
        this.subscriptionQKey = subscriptionQ + "_KEY";
        this.subscriptionDLQKey = subscriptionDLQ + "_KEY";

    }

    @Bean
    DirectExchange orderDLQExchange() {
        return new DirectExchange(orderDLQExchange);
    }

    @Bean
    DirectExchange subscriptionDLQExchange() {
        return new DirectExchange(subscriptionDLQExchange);
    }

    @Bean
    DirectExchange ratingDLQExchange() {
        return new DirectExchange(ratingDLQExchange);
    }

    @Bean
    DirectExchange orderQExchange() {
        return new DirectExchange(orderQExchange);
    }

    @Bean
    DirectExchange subscriptionQExchange() {
        return new DirectExchange(subscriptionQExchange);
    }

    @Bean
    DirectExchange ratingQExchange() {
        return new DirectExchange(ratingQExchange);
    }

    @Bean
    Queue orderDLQ() {
        return QueueBuilder.durable(orderDLQ).build();
    }

    @Bean
    Queue subscriptionDLQ() {
        return QueueBuilder.durable(subscriptionDLQ).build();
    }

    @Bean
    Queue ratingDLQ() {
        return QueueBuilder.durable(ratingDLQ).build();
    }

    @Bean
    Queue orderQ() {
        return QueueBuilder.durable(orderQ)
                .withArgument("x-dead-letter-exchange", orderDLQExchange)
                .withArgument("x-dead-letter-routing-key", orderDLQKey)
                .build();
    }

    @Bean
    Queue subscriptionQ() {
        return QueueBuilder.durable(subscriptionQ)
                .withArgument("x-dead-letter-exchange", subscriptionDLQExchange)
                .withArgument("x-dead-letter-routing-key", subscriptionDLQKey)
                .build();
    }

    @Bean
    Queue ratingQ() {
        return QueueBuilder.durable(ratingQ)
                .withArgument("x-dead-letter-exchange", ratingDLQExchange)
                .withArgument("x-dead-letter-routing-key", ratingDLQKey)
                .build();
    }

    @Bean
    Binding orderDLQBinding() {
        return BindingBuilder.bind(orderDLQ())
                .to(orderDLQExchange()).with(orderDLQKey);
    }

    @Bean
    Binding subscriptionDLQBinding() {
        return BindingBuilder.bind(subscriptionDLQ())
                .to(orderDLQExchange()).with(subscriptionDLQKey);
    }

    @Bean
    Binding ratingDLQBinding() {
        return BindingBuilder.bind(ratingDLQ())
                .to(orderDLQExchange()).with(ratingDLQKey);
    }

    @Bean
    Binding orderQBinding() {
        return BindingBuilder.bind(orderQ())
                .to(orderQExchange()).with(orderQKey);
    }

    @Bean
    Binding subscriptionQBinding() {
        return BindingBuilder.bind(subscriptionQ())
                .to(orderQExchange()).with(subscriptionQKey);
    }

    @Bean
    Binding ratingQBinding() {
        return BindingBuilder.bind(ratingQ())
                .to(orderQExchange()).with(ratingQKey);
    }
}
