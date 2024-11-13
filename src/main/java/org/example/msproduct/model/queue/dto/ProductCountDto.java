package org.example.msproduct.model.queue.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class ProductCountDto {
    private Long productId;
    private Integer soldQuantity;
}
