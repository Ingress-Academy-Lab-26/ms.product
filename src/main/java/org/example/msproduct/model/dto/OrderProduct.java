package org.example.msproduct.model.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class OrderProduct {
    private Long productId;
    private Integer quantity;
    private BigDecimal price;
}
