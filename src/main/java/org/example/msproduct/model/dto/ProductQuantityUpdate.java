package org.example.msproduct.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductQuantityUpdate {
    private Long productId;
    private int quantity;
}
