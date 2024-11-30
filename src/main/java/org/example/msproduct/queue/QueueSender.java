package org.example.msproduct.queue;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QueueSender {
    private final AmqpTemplate amqpTemplate;

    public void sendMessageToQueue(final String queue, String message) {
        amqpTemplate.convertAndSend(queue, message);
    }
}
