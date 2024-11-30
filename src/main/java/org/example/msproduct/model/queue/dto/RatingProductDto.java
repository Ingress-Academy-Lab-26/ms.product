package org.example.msproduct.model.queue.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingProductDto {
    private Long productId;
    private BigDecimal ratingAverage;
}
