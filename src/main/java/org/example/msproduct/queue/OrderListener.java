package org.example.msproduct.queue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.msproduct.exception.QueueException;
import org.example.msproduct.model.request.OrderRequest;
import org.example.msproduct.service.abstraction.ProductService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static org.example.msproduct.model.constants.ErrorConstants.QUEUE_EXCEPTION;

@Component
@Slf4j
@RequiredArgsConstructor

public class OrderListener {
    private final ProductService productService;
    private final ObjectMapper objectMapper;
    private final QueueSender queueSender;

    @Value("${rabbitmq.queue.order-service.queue}")
    private String mainQueue;

    @RabbitListener(queues = "${rabbitmq.queue.order-service.queue}")
    public void consume(String message) {
        try {

            var orders = objectMapper.readValue(message, OrderRequest.class);
            productService.productQuantityUpdate(orders);
        } catch (JsonProcessingException e) {
            log.error("Error.Parsing.JSON", e);
        } catch (Exception e) {
            throw new QueueException(QUEUE_EXCEPTION.getCode(), QUEUE_EXCEPTION.getMessage());
        }
    }

    @RabbitListener(queues = "${rabbitmq.queue.order-service.queue_dlq}")
    public void deadLetterQueue(String message) {
        try {
            queueSender.sendMessageToQueue(mainQueue, message);
        } catch (Exception e) {
            throw new QueueException(QUEUE_EXCEPTION.getCode(), QUEUE_EXCEPTION.getMessage());
        }
    }
}
