package org.example.msproduct.model.queue.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscriptionProductDto {
    private Long productId;
    private boolean subscribed;
}
