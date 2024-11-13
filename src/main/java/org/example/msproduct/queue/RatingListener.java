package org.example.msproduct.queue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.msproduct.exception.QueueException;
import org.example.msproduct.model.queue.dto.RatingProductDto;
import org.example.msproduct.service.abstraction.ProductService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static org.example.msproduct.constant.ErrorConstants.QUEUE_EXCEPTION;

@Component
@Slf4j
@RequiredArgsConstructor
public class RatingListener {
    private final ProductService productService;
    private final ObjectMapper objectMapper;
    private final QueueSender queueSender;

    @RabbitListener(queues = "${rabbitmq.queue.rating-service.queue}")
    public void consumer(String message) {
        try {
            var ratingDto = objectMapper.readValue(message, RatingProductDto.class);
            productService.ratingUpdate(ratingDto);
        } catch (JsonProcessingException e) {
            log.error("Error.Parsing.JSON", e);
        } catch (Exception e) {
            throw new QueueException(QUEUE_EXCEPTION.getCode(), QUEUE_EXCEPTION.getMessage());
        }
    }

    @RabbitListener(queues = "${rabbitmq.queue.rating-service.queue_dlq}")
    public void deadLetterQueue(String message){
        queueSender.sendMessageToQueue("${rabbitmq.queue.rating-service.queue}", message);
    }
}
