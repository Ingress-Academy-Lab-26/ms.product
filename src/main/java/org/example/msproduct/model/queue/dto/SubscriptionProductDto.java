package org.example.msproduct.model.queue.dto;

import lombok.Data;

@Data
public class SubscriptionProductDto {
    private Long productId;
    private boolean subscribed;
}
