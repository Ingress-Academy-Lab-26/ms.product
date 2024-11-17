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
    public DirectExchange orderDLQExchange() {
        return new DirectExchange(orderDLQExchange);
    }

    @Bean
    public DirectExchange subscriptionDLQExchange() {
        return new DirectExchange(subscriptionDLQExchange);
    }

    @Bean
    public DirectExchange ratingDLQExchange() {
        return new DirectExchange(ratingDLQExchange);
    }

    @Bean
    public DirectExchange orderQExchange() {
        return new DirectExchange(orderQExchange);
    }

    @Bean
    public DirectExchange subscriptionQExchange() {
        return new DirectExchange(subscriptionQExchange);
    }

    @Bean
    public DirectExchange ratingQExchange() {
        return new DirectExchange(ratingQExchange);
    }

    @Bean
    public Queue orderDLQ() {
        return QueueBuilder.durable(orderDLQ).build();
    }

    @Bean
    public Queue subscriptionDLQ() {
        return QueueBuilder.durable(subscriptionDLQ).build();
    }

    @Bean
    public Queue ratingDLQ() {
        return QueueBuilder.durable(ratingDLQ).build();
    }

    @Bean
    public Queue orderQ() {
        return QueueBuilder.durable(orderQ)
                .withArgument("x-dead-letter-exchange", orderDLQExchange)
                .withArgument("x-dead-letter-routing-key", orderDLQKey)
                .build();
    }

    @Bean
    public Queue subscriptionQ() {
        return QueueBuilder.durable(subscriptionQ)
                .withArgument("x-dead-letter-exchange", subscriptionDLQExchange)
                .withArgument("x-dead-letter-routing-key", subscriptionDLQKey)
                .build();
    }

    @Bean
    public Queue ratingQ() {
        return QueueBuilder.durable(ratingQ)
                .withArgument("x-dead-letter-exchange", ratingDLQExchange)
                .withArgument("x-dead-letter-routing-key", ratingDLQKey)
                .build();
    }

    @Bean
    public Binding orderDLQBinding() {
        return BindingBuilder.bind(orderDLQ())
                .to(orderDLQExchange()).with(orderDLQKey);
    }

    @Bean
    public Binding subscriptionDLQBinding() {
        return BindingBuilder.bind(subscriptionDLQ())
                .to(orderDLQExchange()).with(subscriptionDLQKey);
    }

    @Bean
    public Binding ratingDLQBinding() {
        return BindingBuilder.bind(ratingDLQ())
                .to(orderDLQExchange()).with(ratingDLQKey);
    }

    @Bean
    public Binding orderQBinding() {
        return BindingBuilder.bind(orderQ())
                .to(orderQExchange()).with(orderQKey);
    }

    @Bean
    public Binding subscriptionQBinding() {
        return BindingBuilder.bind(subscriptionQ())
                .to(orderQExchange()).with(subscriptionQKey);
    }

    @Bean
    public Binding ratingQBinding() {
        return BindingBuilder.bind(ratingQ())
                .to(orderQExchange()).with(ratingQKey);
    }
}
