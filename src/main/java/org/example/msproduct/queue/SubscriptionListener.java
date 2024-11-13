package org.example.msproduct.queue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.msproduct.exception.QueueException;
import org.example.msproduct.model.queue.dto.SubscriptionProductDto;
import org.example.msproduct.service.abstraction.ProductService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static org.example.msproduct.constant.ErrorConstants.QUEUE_EXCEPTION;

@Component
@Slf4j
@RequiredArgsConstructor
public class SubscriptionListener {
    private final ProductService productService;
    private final ObjectMapper objectMapper;
    private final QueueSender queueSender;

    @RabbitListener(queues = "${rabbitmq.queue.subscription-service.queue}")
    public void consumer(final String message) {
        try {
            var subscriptionDto = objectMapper.readValue(message, SubscriptionProductDto.class);
            productService.subscriptionUpdate(subscriptionDto);
        } catch (JsonProcessingException e) {
            log.error("Error.Parsing.JSON", e);
        } catch (Exception e) {
            throw new QueueException(QUEUE_EXCEPTION.getCode(), QUEUE_EXCEPTION.getMessage());
        }
    }

    @RabbitListener(queues = "${rabbitmq.queue.subscription-service.queue_dlq}")
    public void deadLetterQueue(String message){
        queueSender.sendMessageToQueue("${rabbitmq.queue.subscription-service.queue}}", message);
    }
}
