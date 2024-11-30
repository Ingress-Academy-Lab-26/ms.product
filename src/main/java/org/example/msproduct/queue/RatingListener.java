package org.example.msproduct.queue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.msproduct.exception.QueueException;
import org.example.msproduct.model.queue.dto.RatingProductDto;
import org.example.msproduct.service.abstraction.ProductQueueService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static org.example.msproduct.model.constants.ErrorConstants.QUEUE_EXCEPTION;

@Component
@Slf4j
@RequiredArgsConstructor
public class RatingListener {
    private final ProductQueueService queueService;
    private final ObjectMapper objectMapper;
    private final QueueSender queueSender;

    @Value("${rabbitmq.queue.rating-service.queue}")
    private String mainQueue;

    @RabbitListener(queues = "${rabbitmq.queue.rating-service.queue}")
    @CircuitBreaker(name = "productService", fallbackMethod = "fallBackMethodQueue")
    public void consumer(String message) throws JsonProcessingException {
        var ratingDto = objectMapper.readValue(message, RatingProductDto.class);
        queueService.ratingUpdate(ratingDto);
    }

    @RabbitListener(queues = "${rabbitmq.queue.rating-service.queue_dlq}")
    @CircuitBreaker(name = "productService", fallbackMethod = "fallBackMethodQueue")
    public void deadLetterQueue(String message) {
        queueSender.sendMessageToQueue(mainQueue, message);
    }

    public void fallBackMethodQueue(String message, Throwable exception) {
        log.info("ActionLog.SubscriptionListener.Error: {}", exception.getMessage());
        throw new QueueException(QUEUE_EXCEPTION.getCode(), QUEUE_EXCEPTION.getMessage());
    }
}
